package v2.src.modele;

import java.util.List;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class CorpsFixe extends CorpsCeleste {
	private Color COLOR_DEFAULT;
	/**
	 * Instancie un Corps fixe qui herite du corps celeste avec une couleur
	 * specifie.
	 * 
	 * @param nom   nom du corps celeste.
	 * @param masse masse du corps celeste.
	 * @param posx  position en x du corps celeste.
	 * @param posy  positin en y du corps celeste.
	 * @param c     couleur du corps celeste.
	 */

	public CorpsFixe(String nom, double masse, double posx, double posy, String c) {
		super(nom, masse, posx, posy, 0, 0);
		this.COLOR_DEFAULT = Color.valueOf(c);
		this.rayon = 70;
		super.estSimule=false;
		super.forme = new Circle(posx,posy,this.rayon);
	}

	/**
	 * Instancie un Corps fixe qui herite du corps celeste avec une couleur par
	 * defaut.
	 * 
	 * @param nom   nom du corps celeste.
	 * @param masse masse du corps celeste.
	 * @param posx  position en x du corps celeste.
	 * @param posy  position en y du corps celeste.
	 */
	public CorpsFixe(String nom, double masse, double posx, double posy) {
		this(nom, masse, posx, posy, "ORANGERED");
		this.rayon = 70;
		super.estSimule=false;
		super.forme = new Circle(posx,posy,this.rayon);
	}

	/**
	 * Sp�cification de la m�thode m�re : un CorpsFixe de se d�place pas
	 */
	@Override
	public void deplacement(List<CorpsCeleste> systeme,double dt) {
	}

	public Color getCOLOR_DEFAULT() {
		return COLOR_DEFAULT;
	}
	
	public Circle getCercle() {
		return (Circle)super.forme; 
	}

	public void deplacement(Vecteur acc) {
	}
}
