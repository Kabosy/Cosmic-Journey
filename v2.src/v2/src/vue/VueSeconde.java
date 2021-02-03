package v2.src.vue;

import java.util.ArrayList;
import java.util.List;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import v2.src.modele.CorpsCeleste;
import v2.src.modele.Engine;
import v2.src.modele.Vaisseau;
import v2.src.modele.Vecteur;

public class VueSeconde {

	private static Canvas canvas;
	private static GraphicsContext gc;
	// Récupération du radius dans le fichier texte via le model (à faire)
	final static double CANVAS_HEIGHT = 100 * 2 - 100 / 3;
	final static double CANVAS_WIDTH = 100 * 2;

	// Model pour récup les planètes
	private List<CorpsCeleste> sys;
	private static List<List<Vecteur>> listPath;

	private CorpsCeleste corpFocus;

	double posCorpCentreX = 0;
	double posCorpCentreY = 0;
	boolean vueSurVais = true;

	public VueSeconde(Engine engine) {
		canvas = new Canvas(CANVAS_WIDTH, CANVAS_HEIGHT);
		gc = canvas.getGraphicsContext2D();
		sys = engine.getSys();
		listPath = new ArrayList<List<Vecteur>>();
		for (int i = 0; i < engine.getSys().size(); i++) {
			listPath.add(new ArrayList<Vecteur>());
		}
		corpFocus = sys.get(0);
	}

	/*
	 * Obtention du canvas qu'on pourra rajouter dans VueGlobale avec la
	 * VueTableauBord
	 */
	public Canvas getCanvas() {
		return canvas;
	}

	public void reset(VueTableauBord vueTB) {
		clearCanvas();
		setUniverse(vueTB);
	}

	/**
	 * dessine les corps dans la petite map ( centre par d�faut sur le vaisseau), ne
	 * gere pas encore les traces des planetes sur celles ci. ==vue relative
	 * 
	 * @param vueTB vue tableau de bord
	 */
	public void setUniverse(VueTableauBord vueTB) {
		// passe par là
		int i = sys.size() - 1;

		Vecteur v1 = new Vecteur(-7.5, 0), v2 = new Vecteur(0, -2.5), v3 = new Vecteur(0, 2.5),
				v4 = new Vecteur(2.5, 0); // p1 le bout du vaisseau, p2 le coin a gauche et p3 le coin a droite, p4
											// l'arriere

		// recupere la position du vaisseau et draw autour de celui-ci
		if (i == sys.size() - 1 && sys.get(i) instanceof Vaisseau) {
			// System.out.println("posVais= "+sys.get(i).getPositionInit().getX()+";
			// "+sys.get(i).getPositionInit().getY());
			if (vueSurVais) {

				corpFocus = sys.get(i);
				posCorpCentreX = sys.get(i).getPositionInit().getX();
				posCorpCentreY = sys.get(i).getPositionInit().getY();

			}

			double posShipX = (sys.get(i).getPositionInit().getX() - posCorpCentreX);
			double posShipY = (sys.get(i).getPositionInit().getY() - posCorpCentreY);

			double dir = ((Vaisseau) (sys.get(i))).getDir();

			v1.setX(15 * Math.cos(dir));
			v1.setY(15 * Math.sin(dir));
			v2.setX(5 * Math.cos(dir + Math.PI / 2.0));
			v2.setY(5 * Math.sin(dir + Math.PI / 2.0));
			v3.setX(-5 * Math.cos(dir + Math.PI / 2.0));
			v3.setY(-5 * Math.sin(dir + Math.PI / 2.0));
			v4.setX(5 * Math.cos(dir + Math.PI));
			v4.setY(5 * Math.sin(dir + Math.PI));

			double[] xPoints = { posShipX + CANVAS_WIDTH / 2 + v1.getX(), posShipX + CANVAS_WIDTH / 2 + v3.getX(),
					posShipX + CANVAS_WIDTH / 2 + v4.getX(), posShipX + CANVAS_WIDTH / 2 + v2.getX() };
			double[] yPoints = { posShipY + CANVAS_HEIGHT / 2 + v1.getY(), posShipY + CANVAS_HEIGHT / 2 + v3.getY(),
					posShipY + CANVAS_HEIGHT / 2 + v4.getY(), posShipY + CANVAS_HEIGHT / 2 + v2.getY() };
			gc.setFill(sys.get(i).getCOLOR_DEFAULT());
			gc.fillPolygon(xPoints, yPoints, 4);
			// v1.setX(0); v1.setY(0); v2.setX(0); v2.setY(0); v3.setX(0); v3.setY(0);
			// v4.setX(0); v4.setY(0);
			i--;
		} else {
			i--;
		}

		while (i >= 0) {
			// passe par là
			CorpsCeleste corps = sys.get(i);
			Vecteur v = new Vecteur(corpFocus.getPositionInit());
			posCorpCentreX = v.getX();
			posCorpCentreY = v.getY();

			if (corps.estSimule()) {
				showPath(corps.getPositionInit(), corps.getRayon(), i, corps.getCOLOR_DEFAULT(), posCorpCentreX,
						posCorpCentreY);
			}
			gc.setFill(corps.getCOLOR_DEFAULT());
			double positionX = corps.getPositionInit().getX() - posCorpCentreX;
			double positionY = corps.getPositionInit().getY() - posCorpCentreY;
			System.out.println(posCorpCentreX + ";" + posCorpCentreY);
			gc.fillOval(positionX - corps.getRayon() / 2 + CANVAS_WIDTH / 2,
					positionY - corps.getRayon() / 2 + CANVAS_HEIGHT / 2, corps.getRayon(), corps.getRayon());
			if (vueTB.buttonPressed) {
				VueTableauBord.handleButton(sys, canvas, posCorpCentreX, posCorpCentreY);
			}
			// handleMouseOnPlanets(corps.getShape(),vueTB,model);
			i--;
		}

	}

	public static void clearCanvas() {
		gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
	}

	public static void showPath(Vecteur v, double rayon, int i, Color c, double shiftX, double shiftY) {
		listPath.get(i).add(v); // garde en mémoire les endroits parcourus pour pouvoir les récuperer après
								// le reset()
		for (Vecteur vecteur : listPath.get(i)) {
			gc.setFill(c);
			gc.fillOval(vecteur.getX() - shiftX + CANVAS_WIDTH / 2, vecteur.getY() - shiftY + CANVAS_HEIGHT / 2, 1, 1);
		}
	}

	public void setContent(CorpsCeleste c) {
		corpFocus = c;
		if (c instanceof Vaisseau) {
			vueSurVais = true;
		} else {
			vueSurVais = false;
		}

	}
}
