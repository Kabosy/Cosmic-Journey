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

/*TOUTES LES PARTIES EN COMMENTAIRE SONT DES PARTIES EN ATTENTES DU MODELE*/

public class VueUnivers {
	private static Canvas canvas;
	private static GraphicsContext gc;
	//Récupération du radius dans le fichier texte via le model (à faire)
	final static double CANVAS_HEIGHT = 500 * 2 - 500 / 3;
	final static double CANVAS_WIDTH = 500 * 2;
	
	//Model pour récup les planètes
	private List<CorpsCeleste> sys;
	private static List<List<Vecteur>> listPath;
	
	public VueUnivers(Engine engine) {
		canvas = new Canvas(CANVAS_WIDTH, CANVAS_HEIGHT);
		gc = canvas.getGraphicsContext2D();
		sys = engine.getSys();
		listPath = new ArrayList<List<Vecteur>>();
		for(int i = 0; i < engine.getSys().size(); i++) {
			listPath.add(new ArrayList<Vecteur>());
		}
	}
	
	/*Obtention du canvas qu'on pourra rajouter dans VueGlobale avec la VueTableauBord*/
	public Canvas getCanvas() {
		return canvas;
	}
	
	public void reset(VueTableauBord vueTB) {
		clearCanvas();
		setUniverse(vueTB);
	}
	
	public void setUniverse(VueTableauBord vueTB) {
		//passe par là
		int i = 0;
		Vecteur v1 = new Vecteur(-7.5,0),v2 = new Vecteur(0,-2.5),v3 = new Vecteur(0,2.5),v4 = new Vecteur(2.5,0); // p1 le bout du vaisseau, p2 le coin a gauche et p3 le coin a droite, p4 l'arriere
		while (i < sys.size()-1) {
			//passe par là
			CorpsCeleste corps = sys.get(i);
			if(corps.estSimule()) {
				showPath(corps.getPositionInit(),corps.getRayon(),i,corps.getCOLOR_DEFAULT());
			}
			gc.setFill(corps.getCOLOR_DEFAULT());
			gc.fillOval(corps.getPositionInit().getX() - corps.getRayon()/2 + CANVAS_WIDTH/2, corps.getPositionInit().getY() - corps.getRayon()/2+ CANVAS_HEIGHT/2, corps.getRayon(), corps.getRayon());		 
			if(vueTB.buttonPressed) {
				VueTableauBord.handleButton(sys,canvas,0,0);
			}
			//handleMouseOnPlanets(corps.getShape(),vueTB,model);
			i++;
		}
		
		// recupere la position du vaisseau et draw autour de celui-ci
		if(sys.get(i)!=null) {
			
			double posShipX = sys.get(i).getPositionInit().getX()+CANVAS_WIDTH/2;
			double posShipY = sys.get(i).getPositionInit().getY()+CANVAS_HEIGHT/2;
			
			
			double dir = ((Vaisseau)(sys.get(i))).getDir();
			
			v1.setX(15*Math.cos(dir)); v1.setY(15*Math.sin(dir));
			v2.setX(5*Math.cos(dir+Math.PI/2.0)); v2.setY(5*Math.sin(dir+Math.PI/2.0));
			v3.setX(-5*Math.cos(dir+Math.PI/2.0)); v3.setY(-5*Math.sin(dir+Math.PI/2.0));
			v4.setX(5*Math.cos(dir+Math.PI)); v4.setY(5*Math.sin(dir+Math.PI));
			
			
			double[] xPoints = { posShipX + v1.getX(), posShipX + v3.getX(),  posShipX + v4.getX(),posShipX + v2.getX() };
			double[] yPoints = { posShipY + v1.getY(), posShipY + v3.getY(), posShipY + v4.getY(), posShipY + v2.getY() };
			gc.setFill(sys.get(i).getCOLOR_DEFAULT());
			gc.fillPolygon(xPoints, yPoints, 4);
			//v1.setX(0); v1.setY(0); v2.setX(0); v2.setY(0); v3.setX(0); v3.setY(0); v4.setX(0); v4.setY(0);
		}
	}
	
	public static void clearCanvas() {
		gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());	
	}
	
	public static void showPath(Vecteur v, double rayon, int i,Color c) {
		listPath.get(i).add(v); //garde en mémoire les endroits parcourus pour pouvoir les récuperer après le reset()
		for(Vecteur vecteur : listPath.get(i)) {
			gc.setFill(c);
			gc.fillOval(vecteur.getX()+ CANVAS_WIDTH/2, vecteur.getY() + CANVAS_HEIGHT/2, 1, 1);
		}
	}
}
