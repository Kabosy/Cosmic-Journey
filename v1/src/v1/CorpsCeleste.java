package v1;

import javafx.scene.image.Image;
import javafx.scene.paint.Color;

public abstract class CorpsCeleste {
	private String nom;
	private double masse;
	private Vecteur vitesse;
	private Vecteur positionInit;
	private Image image;
	protected double rayon;

	/**
	 * instancie un corps Celeste
	 * 
	 * @param nom
	 *            Nom du corps celeste.
	 * @param masse
	 *            Masse du corps celeste.
	 * @param posx
	 *            Abscisse du corps celeste.
	 * @param posy
	 *            Ordonnée du corps celeste.
	 * @param vitx
	 *            Abscisse du corps celeste.
	 * @param vity
	 *            Abscisse du corps celeste.
	 * 
	 */
	public CorpsCeleste(String nom, double masse, double posx, double posy, double vitx, double vity) {
		this.nom = nom;
		this.masse = masse;
		positionInit = new Vecteur(posx, posy);
		vitesse = new Vecteur(vitx, vity);

	}

	/*
	 * public void setImage() { switch(nom) { case "Soleil": image = new
	 * Image("/ressources/sun.png"); case "Planete 1": image = new
	 * Image("/ressources/earth.png"); case "Planete 2": image = new
	 * Image("/ressources/jupiter.png"); } }
	 */

	public void deplacement(Vecteur acc) {
		this.setVitesse(this.getVitesse().euler(acc, 0.5));
		this.setPositionInit(this.getPositionInit().euler(this.getVitesse(), 0.5));
	}

	/**
	 * 
	 * 
	 * @return Renvoie la masse du corps celeste.
	 */
	public double getMasse() {
		return masse;
	}

	/**
	 * Change la masse du corps celeste courant.
	 * 
	 * @param masse
	 *            la nouvelle masse du Corps Celeste
	 */
	public void setMasse(double masse) {
		this.masse = masse;
	}

	/**
	 * 
	 * 
	 * @return Renvoie la vitesse du corps celeste.
	 */
	public Vecteur getVitesse() {
		return vitesse;
	}

	/**
	 * Change la vitesse du corps celeste courant.
	 * 
	 * @param nouvelle
	 *            vitesse du corps celeste.
	 */
	public void setVitesse(Vecteur vitesse) {
		this.vitesse = vitesse;
	}

	public Image getImage() {
		return image;
	}

	public void setImage(Image image) {
		this.image = image;
	}

	/**
	 * 
	 * @return renvoie le nom du corps celeste.
	 */
	public String getNom() {
		return nom;
	}

	/**
	 * 
	 * @return Renvoie la position de depart du corps celeste.
	 */
	public Vecteur getPositionInit() {
		return positionInit;
	}

	/**
	 * change la position du corps celeste par celle fourni
	 * 
	 * @param positionInit
	 */
	public void setPositionInit(Vecteur positionInit) {
		this.positionInit = positionInit;
	}

	/**
	 * 
	 * @return Renvoie la couleurs de l'objet.
	 */
	public Color getCOLOR_DEFAULT() {
		return null;
	}

	public double distance(CorpsCeleste corps) {
		double vitesseX = this.getPositionInit().getX() - corps.getPositionInit().getX();
		double vitesseY = this.getPositionInit().getY() - corps.getPositionInit().getY();

		return Math.sqrt(vitesseX * vitesseX + vitesseY * vitesseY);
	}

	public double acceleration(Systeme univers) {
		double res = 0;
		for (CorpsCeleste c : univers.getSysteme()) {
			res += Engine.attraction(this.getMasse(), c.getMasse(), distance(c));
		}
		return 0;
	}

	public double getRayon() {
		return this.rayon;
	}

}
