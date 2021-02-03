package v2.src.modele;

import java.util.List;

public class Physique {

	/**
	 * Calcul de la force exerce entre deux corps.
	 * 
	 * @param masseA   masse du premier corps
	 * @param masseB   masse du seconds corps
	 * @param distance distance entre ces deux corps
	 * @return la force qui s'exerce
	 */
	public static double forceAttraction(double masseA, double masseB, double distance) {
		return (Engine.G * masseA * masseB) / (distance * distance);
	}

	/**
	 * 
	 * @param corpsA Corps celeste A
	 * @param corpsB Corps celeste B
	 * @return la distance entre A et B
	 * 
	 */
	public static double distanceEntreCorps(CorpsCeleste corpsA, CorpsCeleste corpsB) {
		double posX = corpsB.getPositionInit().getX() - corpsA.getPositionInit().getX();
		double posY = corpsB.getPositionInit().getY() - corpsA.getPositionInit().getY();

		return Math.sqrt(posX * posX + posY * posY);
	}

	/**
	 * Calcul les composantes d'un vecteur force a partir de la formule suivante:
	 * Composante x=NormeVecteurForce * cos(theta) Composante y=NormeVecteurForce *
	 * sin(theta)
	 * 
	 * Avec theta l'angle entre l'axe des abcisse et le vecteur force
	 * 
	 * @param a le corps celeste A
	 * @param b le corps Celeste B
	 * @return Un vecteur Force
	 */
	public static Vecteur vecteurAttraction(CorpsCeleste a, CorpsCeleste b) {
		double x, y;
		x = b.getPositionInit().getX() - a.getPositionInit().getX();
		y = b.getPositionInit().getY() - a.getPositionInit().getY();

		Vecteur v = new Vecteur(x, y);

		double normeV = v.norme();

		Vecteur unitaire = new Vecteur(x / normeV, y / normeV);

		double attraction = forceAttraction(a.getMasse(), b.getMasse(), normeV);

		return new Vecteur(unitaire.getX() * attraction, unitaire.getY() * attraction);

	}

	/**
	 * calcul le vecteur acceleration d'un corps celeste passe en parametre
	 * 
	 * @param univers la liste des corps celestes
	 * @param subit   le corps subissante la force des autres corps
	 * @return le vecteur accelration d'un corps celeste passe en parametre
	 */
	public static Vecteur vecteurAcceleration(List<CorpsCeleste> univers, CorpsCeleste subit) {

		Vecteur laForce = new Vecteur(0, 0);
		Vecteur sommeForce = new Vecteur(0, 0);
		for (CorpsCeleste c : univers) {
			if(c!=null ) {
				if (!c.equals(subit)) {
					laForce = vecteurAttraction(subit, c);
					sommeForce = sommeForce.add(laForce);
				}
			}
		}
		
		sommeForce = sommeForce.add(subit.innerThrust());
		
		return new Vecteur(sommeForce.getX() / subit.getMasse(), sommeForce.getY() / subit.getMasse());
	}

	/**
	 * calcule le vecteur a(t + deltaT) avec la methode d'euler explicite
	 * 
	 * @param a      le vecteur courant
	 * @param b      la derivee de a
	 * @param deltaT le decalage de temps
	 * @return un vecteur a avec un decalage de deltaT
	 */
	public static Vecteur eulerExplicite(Vecteur a, Vecteur b, double deltaT) {
		double x = a.getX() + deltaT * b.getX();
		double y = a.getY() + deltaT * b.getY();
		return new Vecteur(x, y);
	}

	/**
	 * met a jour les attributs du CorpsCeleste subit grace a la methode d'euler
	 * explicite
	 * 
	 * @param univers la liste des CorpsCeleste de l'univers
	 * @param subit   le CorpsCeleste a mettre a jour
	 * @param deltaT  le decalage de temps
	 */
	public static void positionEuler(List<CorpsCeleste> univers, CorpsCeleste subit, double deltaT) {
		Vecteur acceleration = vecteurAcceleration(univers, subit);
		subit.setVitesse(eulerExplicite(subit.getVitesse(), acceleration, deltaT));
		subit.setPositionInit(eulerExplicite(subit.getPositionInit(), subit.getVitesse(), deltaT));
	}
}
