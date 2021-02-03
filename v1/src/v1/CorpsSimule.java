package v1;

import javafx.scene.paint.Color;

public class CorpsSimule extends CorpsCeleste {

	private Color COLOR_DEFAULT;

	/**
	 * Instancie un Corps Simule qui herite du corps celeste avec une couleur
	 * specifie.
	 * 
	 * @param nom
	 *            nom du corps celeste.
	 * @param masse
	 *            masse du corps celeste.
	 * @param posx
	 *            position en x du corps celeste.
	 * @param posy
	 *            positin en y du corps celeste.
	 * @param c
	 *            couleur du corps celeste.
	 */

	public CorpsSimule(String nom, double masse, double posx, double posy, double vitx, double vity, String c) {
		super(nom, masse, posx, posy, vitx, vity);
		this.COLOR_DEFAULT = Color.valueOf(c);
		this.rayon = 30;
	}

	/**
	 * Instancie un Corps Simule qui herite du corps celeste avec une couleur par
	 * defaut.
	 * 
	 * @param nom
	 *            nom du corps celeste.
	 * @param masse
	 *            masse du corps celeste.
	 * @param posx
	 *            position en x du corps celeste.
	 * @param posy
	 *            position en y du corps celeste.
	 */
	public CorpsSimule(String nom, double masse, double posx, double posy, double vitx, double vity) {
		this(nom, masse, posx, posy, vitx, vity, "CADETBLUE");
		this.rayon = 30;
	}

	public Color getCOLOR_DEFAULT() {
		return COLOR_DEFAULT;
	}

}
