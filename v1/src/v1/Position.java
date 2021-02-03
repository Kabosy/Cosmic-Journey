package v1;

public class Position {
	private double posX;
	private double posY;
	/**
	 * @param x position en abscisse 
	 * @param y position en ordonnee
	 */
	
	/**
	 * Constructeur d'une Position
	 * 
	 */
	Position(double x, double y){
		this.posX=x;
		this.posY=y;	
	}

	/**
	 * 
	 * @return la position en abscisse
	 */
	public double getPosX() {
		return posX;
	}
	/** 
	 *change la position en bascisse
	 *@param posX
	 */
	public void setPosX(double posX) {
		this.posX = posX;
	}
	
	/**
	 * 
	 * @return la postition en ordonee
	 */
	public double getPosY() {
		return posY;
	}
	
	/**
	 *change la position en ordonee
	 *@param posY
	 */
	
	public void setPosY(double posY) {
		this.posY = posY;
	}
	
	
}
