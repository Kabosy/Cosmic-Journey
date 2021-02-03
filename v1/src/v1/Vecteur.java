package v1;

public class Vecteur {
	/**
	 * @param x
	 * @param y
	 */
	private double x;
	private double y;

	/**
	 * Constructeur d'un vecteur nul
	 * 
	 * @param x
	 * @param y
	 */
	Vecteur() {
		this.x = 0;
		this.y = 0;
	}

	/**
	 * Constructeur d'un vecteur non-nul
	 * 
	 * @return
	 */
	Vecteur(double x, double y) {
		this.x = x;
		this.y = y;
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
	 * change l'abscisse du vecteur
	 * 
	 * @param x
	 */
	public void setX(double x) {
		this.x = x;
	}

	/**
	 * change l'ordonnee
	 * 
	 * @param y
	 */
	public void setY(double y) {
		this.y = y;
	}

	public static double norme(double x, double y) {
		return Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2));
	}

	/**
	 * méthode euler explicite
	 * 
	 * @param v
	 * @param delta
	 * @return
	 */
	public Vecteur euler(Vecteur v, double delta) {
		Vecteur res = new Vecteur(this.x, this.y);
		res.x += delta * v.getX();
		res.y += delta * v.getY();
		return res;
	}

	public static Vecteur somme(Vecteur v1, Vecteur v2) {
		return new Vecteur(v1.getX() + v2.getX(), v1.getY() + v2.getY());
	}

}
