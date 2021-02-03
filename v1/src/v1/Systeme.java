package v1;

import java.util.List;

public class Systeme {

	private double radius;
	private final List<CorpsCeleste> UNIVERS;
	/**
	 * 
	 * Constructeur du systeme
	 */
	
	public Systeme(List<CorpsCeleste> u){
		this.UNIVERS=u;
	}
	/**
	 * 
	 * @return l'univers
	 */
	public List<CorpsCeleste> getSysteme(){
		return UNIVERS;
	}
	
	/**
	 * 
	 * @return le radius
	 */
	public double getRadius() {
		return radius;
	}
	
	/**
	 * 
	 * @param rad
	 * change le radius
	 */
	public void setRadius(double rad) {
		radius = rad;
	}
	
	/**
	 * 
	 * @return la taille de l'univers
	 */
	public int size(){
		return UNIVERS.size();
	}
	
	/**
	 * 
	 * @param i
	 * @return le corps celeste a l'indice i
	 */
	public CorpsCeleste getCorpsCeleste(int i) {
		return UNIVERS.get(i);
	}
	
}
