package v2.src.modele;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class CorpsSimule extends CorpsCeleste {
	private Color COLOR_DEFAULT;

	/**
	 * Instancie un Corps Simule qui herite du corps celeste avec une couleur
	 * specifie.
	 * 
	 * @param nom   nom du corps celeste.
	 * @param masse masse du corps celeste.
	 * @param posx  position en x du corps celeste.
	 * @param posy  positin en y du corps celeste.
	 * @param vitx  la composante x du vecteur vitesse
	 * @param vity  la composante y du vecteur vitesse
	 * @param c     couleur du corps celeste.
	 */

	public CorpsSimule(String nom, double masse, double posx, double posy, double vitx, double vity, String c) {
		super(nom, masse, posx, posy, vitx, vity);
		this.COLOR_DEFAULT = Color.valueOf(c);
		this.rayon = 20;
		super.estSimule = true;
		super.forme = new Circle(posx,posy,this.rayon);
	}

	/**
	 * Instancie un Corps Simule qui herite du corps celeste avec une couleur par
	 * defaut.
	 * 
	 * @param nom   nom du corps celeste.
	 * @param masse masse du corps celeste.
	 * @param posx  position en x du corps celeste.
	 * @param posy  position en y du corps celeste.
	 * @param vitx  la composante x du vecteur vitesse
	 * @param vity  la composante y du vecteur vitesse
	 */
	public CorpsSimule(String nom, double masse, double posx, double posy, double vitx, double vity) {
		this(nom, masse, posx, posy, vitx, vity, "CADETBLUE");
		this.rayon = 20;
		super.estSimule = true;
		super.forme = new Circle(posx,posy,this.rayon);
	}

	public Circle getCircle() {
		return (Circle)super.forme;
	}
	
	public Color getCOLOR_DEFAULT() {
		return COLOR_DEFAULT;
	}
}
