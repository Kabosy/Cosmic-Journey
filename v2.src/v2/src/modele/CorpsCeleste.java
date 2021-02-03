package v2.src.modele;

import java.util.List;
import java.util.Observable;

import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.shape.Shape;

public class CorpsCeleste extends Observable {
	private String nom;
	private double masse;
	private Vecteur vitesse;
	private Vecteur positionInit;
	private Image image;
	protected double rayon;
	protected boolean estSimule;
	protected Shape forme;

	/**
	 * instancie un corps Celeste
	 * 
	 * @param nom   Nom du corps celeste.
	 * @param masse Masse du corps celeste.
	 * @param posx  Abscisse du corps celeste.
	 * @param posy  Ordonnée du corps celeste.
	 * @param vitx  Abscisse du corps celeste.
	 * @param vity  Abscisse du corps celeste.
	 * 
	 */
	public CorpsCeleste(String nom, double masse, double posx, double posy, double vitx, double vity) {
		this.nom = nom;
		this.masse = masse;
		positionInit = new Vecteur(posx, posy);
		vitesse = new Vecteur(vitx, vity);
	}

	/**
	 * Modifie la vitesse et la position de l'objet courant
	 *
	 * @param systeme l'ensemble des CorpsCeleste de l'univers
	 * @param dt      le pas de temps
	 */
	public void deplacement(List<CorpsCeleste> systeme, double dt) {
		Physique.positionEuler(systeme, this, dt);
	}

	public boolean estSimule() {
		return estSimule;
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
	 * @param masse la nouvelle masse du Corps Celeste
	 */
	public void setMasse(double masse) {
		this.masse = masse;
		setChanged();
		notifyObservers(this.masse);
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
	 * @param vitesse nouvelle vitesse du corps celeste.
	 */
	public void setVitesse(Vecteur vitesse) {
		this.vitesse = vitesse;
		setChanged();
		notifyObservers(this.vitesse);
	}

	public Image getImage() {
		return image;
	}

	public void setImage(Image image) {
		this.image = image;
		setChanged();
		notifyObservers(this.image);
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
	 * @param positionInit la nouvelle position
	 */
	public void setPositionInit(Vecteur positionInit) {
		this.positionInit = positionInit;
		setChanged();
		notifyObservers(this.positionInit);
	}

	/**
	 * 
	 * @return Renvoie la couleurs de l'objet.
	 */
	public Color getCOLOR_DEFAULT() {
		return null;
	}

	public double getRayon() {
		return this.rayon;
	}

	public String toString() {
		return nom;
	}

	public Shape getShape() {
		return forme;
	}

	public Vecteur innerThrust() {
		return new Vecteur(0, 0);
	}
}
