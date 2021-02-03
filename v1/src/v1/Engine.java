package v1;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public class Engine {
	private List<Double> paramsValue;
	private List<String> paramsNames;
	public static final double G = 0.01;
	private Vaisseau vaisseau;

	public Engine() {
		paramsValue = new ArrayList<Double>();
		paramsNames = new ArrayList<String>();
	}

	private static double getValueInDouble(Iterator<String> it) {
		return Double.parseDouble(it.next().split("=")[1]);
	}

	public void stockParam(File file, Systeme s) throws IOException {
		FileReader fileR = new FileReader(file);
		BufferedReader bufferedReader = new BufferedReader(fileR);

		String line = bufferedReader.readLine();

		while (line != null) {
			String[] stringVal = line.split(" ");
			int cpt1 = 0;

			List<String> param = new ArrayList<>();
			param.addAll(Arrays.asList(stringVal));
			Iterator<String> paramIt = param.iterator();
			boolean pass = false;
			boolean notDone = true;
			while (paramIt.hasNext() && notDone) {
				// System.out.println("bonjour");
				if (line.contains("PARAM")) {
					if (!pass) {
						paramIt.next();
						pass = true;
					}
					String currIt = paramIt.next();
					paramsNames.add(currIt.split("=")[0]);
					paramsValue.add(Double.parseDouble(currIt.split("=")[1]));
				} else if (line.contains("Fixe")) {
					String nom = paramIt.next().split(":")[0];
					paramIt.next();
					double masse = getValueInDouble(paramIt);
					double posx = getValueInDouble(paramIt);
					double posy = getValueInDouble(paramIt);
					if (paramIt.hasNext()) {
						String color = paramIt.next().split("=")[1].toUpperCase();
						s.getSysteme().add(new CorpsFixe(nom, masse, posx, posy, color));
					} else {
						s.getSysteme().add(new CorpsFixe(nom, masse, posx, posy));
					}

				} else if (line.contains("Simule")) {
					String nom = paramIt.next().split(":")[0];
					paramIt.next();
					double masse = getValueInDouble(paramIt);
					double posx = getValueInDouble(paramIt);
					double posy = getValueInDouble(paramIt);
					double vitx = getValueInDouble(paramIt);
					double vity = getValueInDouble(paramIt);
					if (paramIt.hasNext()) {
						String color = paramIt.next().split("=")[1].toUpperCase();
						s.getSysteme().add(new CorpsSimule(nom, masse, posx, posy, vitx, vity, color));
					} else {
						s.getSysteme().add(new CorpsSimule(nom, masse, posx, posy, vitx, vity));
					}

				} else if (line.contains("Vaisseau")) {
					String nom = paramIt.next().split(":")[0];
					paramIt.next();
					double masse = getValueInDouble(paramIt);
					double posx = getValueInDouble(paramIt);
					double posy = getValueInDouble(paramIt);
					double vitx = getValueInDouble(paramIt);
					double vity = getValueInDouble(paramIt);
					double pprincipal = getValueInDouble(paramIt);
					double pretro = getValueInDouble(paramIt);
					if (paramIt.hasNext()) {
						String color = paramIt.next().split("=")[1].toUpperCase();
						s.getSysteme().add(new Vaisseau(nom, masse, posx, posy, vitx, vity, pprincipal, pretro, color));
						this.vaisseau = new Vaisseau(nom, masse, posx, posy, vitx, vity, pprincipal, pretro, color);
					} else {
						s.getSysteme().add(new Vaisseau(nom, masse, posx, posy, vitx, vity, pprincipal, pretro));
						this.vaisseau = new Vaisseau(nom, masse, posx, posy, vitx, vity, pprincipal, pretro);
					}

				} else {
					notDone = false;
				}
			}
			line = bufferedReader.readLine();
		}
	}

	public void show() {
		System.out.println("PARAMS :");
		for (int i = 0; i < paramsValue.size(); i++) {
			System.out.println(paramsNames.get(i) + " : " + paramsValue.get(i));
		}
		System.out.println("");
	}

	public List<Double> getParamsValues() {
		return paramsValue;
	}

	public List<String> getParamsNames() {
		return paramsNames;
	}

	/**
	 * Calcul de la force exerce entre deux corps.
	 * 
	 * @param masseA
	 *            masse du premier corps
	 * @param masseB
	 *            masse du seconds corps
	 * @param distance
	 *            distance entre ces deux corps
	 * @return la force qui s'exerce
	 */
	public static double attraction(double masseA, double masseB, double distance) {
		return (G * masseA * masseB) / (distance * distance);
	}

	/**
	 * Calcul les composantes d'un vecteur force a partir de la formule suivante:
	 * Composante x=NormeVecteurForce * cos(theta) Composante y=NormeVecteurForce *
	 * sin(theta)
	 * 
	 * Avec theta l'angle entre l'axe des abcisse et le vecteur force
	 * 
	 * @param a
	 *            le corps celeste A
	 * @param b
	 *            le corps Celeste B
	 * @return Un vecteur Force
	 */
	public static Vecteur VecteurForce(CorpsCeleste a, CorpsCeleste b) {
		double x, y;
		x = b.getPositionInit().getX() - a.getPositionInit().getX();
		y = b.getPositionInit().getY() - a.getPositionInit().getY();

		Vecteur v = new Vecteur(x, y);
		double normeV = Vecteur.norme(x, y);
		Vecteur unitaire = new Vecteur(x / normeV, y / normeV);

		double attraction = attraction(a.getMasse(), b.getMasse(), normeV);

		return new Vecteur(unitaire.getX() * attraction, unitaire.getY() * attraction);
	}

	/**
	 * 
	 * @param a
	 *            Corps celeste A
	 * @param b
	 *            Corps celeste B
	 * @return la distance entre A et B
	 * 
	 */
	public static double CalculDistance(CorpsCeleste a, CorpsCeleste b) {
		return Math.sqrt(Math.pow((b.getPositionInit().getX() - a.getPositionInit().getX()), 2)
				+ Math.pow((b.getPositionInit().getY() - a.getPositionInit().getY()), 2));
	}

	/**
	 * calcul le vecteur acc�lration d'un corps celeste pass� en param�tre
	 * 
	 * @param univers
	 * @param subit
	 * @return le vecteur acc�lration d'un corps celeste pass� en param�tre
	 */
	public static Vecteur VecteurAcc(Systeme univers, CorpsCeleste subit) {
		int idx = 0;
		Vecteur laForce = new Vecteur(0, 0);
		Vecteur sommeForce = new Vecteur(0, 0);
		while (idx < univers.size()) {
			if (!univers.getCorpsCeleste(idx).equals(subit)) {
				laForce = VecteurForce(subit, univers.getCorpsCeleste(idx));
				sommeForce.setX(sommeForce.getX() + laForce.getX());
				sommeForce.setY(sommeForce.getY() + laForce.getY());

			}
			idx++;
		}
		Vecteur acceleration = new Vecteur(sommeForce.getX() / subit.getMasse(), sommeForce.getY() / subit.getMasse());
		return acceleration;
	}

	/**
	 * calcul du nouveau vecteur vitesse.
	 * 
	 * @param c
	 *            le Corps Celeste sur lequel on travaille
	 * @param deltaT
	 *            le pas de temps
	 * @param univers
	 * @return le vecteur vitesse qui a �t� calcul�
	 */
	public static Vecteur VecteurVitesse(CorpsCeleste c, double deltaT, Systeme univers) {
		Vecteur vitesse = new Vecteur();
		Vecteur oldVitesse = c.getVitesse();
		vitesse.setX(oldVitesse.getX() + deltaT * VecteurAcc(univers, c).getX());
		vitesse.setY(oldVitesse.getY() + deltaT * VecteurAcc(univers, c).getY());
		return vitesse;

	}

	/**
	 * calcul la nouvelle position en x et y du corps Celeste
	 * 
	 * @param c
	 *            le Corps celeste sur lequel on travaille
	 * @param deltaT
	 *            le pas de temps
	 * @param univers
	 * @return la nouvelle position calcul�
	 */
	public static Vecteur NouvellePosition(CorpsCeleste c, double deltaT, Systeme univers) {
		Vecteur nouvelle = new Vecteur(0, 0);
		Vecteur oldPosition = c.getPositionInit();
		nouvelle.setX(oldPosition.getX() + deltaT * VecteurVitesse(c, deltaT, univers).getX());
		nouvelle.setY(oldPosition.getY() + deltaT * VecteurVitesse(c, deltaT, univers).getY());
		return nouvelle;

	}

}
