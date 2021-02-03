package v2.src.modele;

import java.text.DecimalFormat;

public class Vecteur {

	private DecimalFormat df2 = new DecimalFormat("###.####");
	/**
	 * @param x
	 * @param y
	 */
	private double x;
	private double y;

	/**
	 * Constructeur d'un vecteur nul
	 * 
	 */
	public Vecteur() {
		this.x = 0;
		this.y = 0;
	}

	/**
	 * Constructeur d'un vecteur non-nul
	 * 
	 * @param x la composante x
	 * @param y la composante y
	 */
	public Vecteur(double x, double y) {
		this.x = x;
		this.y = y;
	}

	/**
	 * Construit un vecteur depuis un vecteur v donne en parametre
	 * 
	 * @param v vecteur v
	 */
	public Vecteur(Vecteur v) {
		this.x = v.x;
		this.y = v.y;
	}

	public double norme() {
		return Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2));
	}

	/**
	 * 
	 * @return l'abscisse
	 */
	public double getX() {
		return this.x;
	}

	/**
	 * 
	 * @return l'ordonnee
	 */
	public double getY() {
		return this.y;
	}

	/**
	 * change la composante x du vecteur
	 * 
	 * @param x la nouvelle composante x
	 */
	public void setX(double x) {
		this.x = x;
	}

	/**
	 * change la composante y
	 * 
	 * @param y la nouvelle composante y
	 */
	public void setY(double y) {
		this.y = y;
	}

	public Vecteur add(Vecteur v) {
		return new Vecteur(this.x + v.getX(), this.y + v.getY());
	}

	/**
	 * @return l'affichage du vecteur sous la forme (x,y)
	 * 
	 */
	public String toString() {
		return "(" + df2.format(x) + "," + df2.format(y) + ")";
	}
}
