package v2.src.modele;

import javafx.scene.paint.Color;

public class Vaisseau extends CorpsCeleste {
	private final double PRINCIPAL;
	private final double RETRO;
	private Color Color_Default;
	public static final double MAX_VELOCITY = 10;

	private double dir = 0;
	private Vecteur sumProp = new Vecteur(0,0);
	private Vecteur frontProp = new Vecteur(0,0);
	private Vecteur leftProp = new Vecteur(0,0);
	private Vecteur rightProp = new Vecteur(0,0);
	private Vecteur backProp = new Vecteur(0,0);
	
	/**
	 * 
	 * @param nom
	 * @param masse
	 * @param posx
	 * @param posy
	 * @param vitx
	 * @param vity
	 * @param principal
	 * @param retro
	 * @param c
	 */

	/**
	 * 
	 * @param nom
	 * @param masse
	 * @param posx
	 * @param posy
	 * @param vitx
	 * @param vity
	 * @param principal
	 * @param retro     Constructeur d'un vaisseau en precisant la couleur
	 */
	Vaisseau(String nom, double masse, double posx, double posy, double vitx, double vity, double principal,
			double retro, String c) {
		super(nom, masse, posx, posy, vitx, vity);
		this.PRINCIPAL = principal;
		this.RETRO = retro;
		this.Color_Default = Color.valueOf(c);
		super.estSimule = true;
	}

	/**
	 * Constructeur d'un vaisseau sans preciser la couleur
	 * 
	 * @param nom
	 * @param masse
	 * @param posx
	 * @param posy
	 * @param vitx
	 * @param vity
	 * @param principal
	 * @param retro
	 */
	Vaisseau(String nom, double masse, double posx, double posy, double vitx, double vity, double principal,
			double retro) {
		this(nom, masse, posx, posy, vitx, vity, principal, retro, "MEDIUMVIOLETRED");
		super.estSimule = true;
	}

	/**
	 * active le propulseur arriere
	 * 
	 * 
	 */
	public void propArriere() {
		backProp = new Vecteur(RETRO*Math.cos(dir+Math.PI),RETRO*Math.sin(dir+Math.PI));
		//this.setVitesse(new Vecteur(this.getVitesse().getX() * 1.05, this.getVitesse().getY() * 1.05));
	}

	public void propMain() {
		backProp = new Vecteur(PRINCIPAL*Math.cos(dir+Math.PI),PRINCIPAL*Math.sin(dir+Math.PI));
		//this.setVitesse(new Vecteur(this.getVitesse().getX() * 1.05, this.getVitesse().getY() * 1.05));
	}
	
	/**
	 * active le propulseur avant
	 * 
	 * 
	 */
	public void propAvant() {
		frontProp = new Vecteur(RETRO*Math.cos(dir),RETRO*Math.sin(dir));
		//this.setVitesse(new Vecteur(this.getVitesse().getX() / 1.05, this.getVitesse().getY() / 1.05));
	}

	/**
	 * active le propulseur gauche
	 */
	public void propGauche() {
		leftProp = new Vecteur(RETRO*Math.cos(dir+Math.PI/2.0),RETRO*Math.sin(dir+Math.PI/2.0));
		//this.setVitesse(new Vecteur(this.getVitesse().getX() * 1.05, this.getVitesse().getY()));
	}

	/**
	 * active le propulseur droit
	 */
	public void propDroit() {
		rightProp = new Vecteur(RETRO*Math.cos(dir-Math.PI/2.0),RETRO*Math.sin(dir-Math.PI/2.0));
		//this.setVitesse(new Vecteur(this.getVitesse().getX() / 1.05, this.getVitesse().getY()));
	}
	
	@Override
	public Vecteur innerThrust() {
		sumProp = new Vecteur(0,0);
		sumProp.add(backProp);
		sumProp.add(frontProp);
		sumProp.add(leftProp);
		sumProp.add(rightProp);
		return sumProp;
	}

	/**
	 * 
	 * @return le propulseur arriere
	 */
	public double getPoussPrincip() {
		return PRINCIPAL;
	}

	/**
	 * 
	 * @return le propulseur avant
	 */
	public double getPoussRetro() {
		return RETRO;
	}

	/**
	 * @return la couleur du vaisseau
	 */
	public Color getCOLOR_DEFAULT() {
		return Color_Default;
	}
	
	public void setDirection(double d) {
		dir = d;
	}
	
	public double getDir() {
		return dir;
	}
}
