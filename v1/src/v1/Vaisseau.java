package v1;

import javafx.scene.paint.Color;

public class Vaisseau extends CorpsCeleste{
	private final double PRINCIPAL;
	private final double RETRO;
	private MoveType mov;
	private Color Color_Default;
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
	 * @param retro
	 * Constructeur d'un vaisseau en precisant la couleur
	 */
	Vaisseau(String nom, double masse, double posx, double posy, double vitx, double vity, double principal, double retro,String c){
		super(nom,masse,posx,posy,vitx,vity);
		this.PRINCIPAL=principal;
		this.RETRO=retro;
		this.mov = MoveType.SIMULE;
		this.Color_Default=Color.valueOf(c);
	}
	
	/**
	 * Constructeur d'un vaisseau sans preciser la couleur
	 * @param nom
	 * @param masse
	 * @param posx
	 * @param posy
	 * @param vitx
	 * @param vity
	 * @param principal
	 * @param retro
	 */
	Vaisseau(String nom, double masse, double posx, double posy, double vitx, double vity, double principal, double retro){
		this(nom, masse, posx, posy, vitx, vity, principal, retro,"MEDIUMVIOLETRED" );
	}
	/**
	 * active le propulseur arriere
	 * @param vit
	 */
	public void PropA(Vecteur vit) {
		//this.setVitesse(new Vecteur(this.getVitesse().getX()*vit.getX(), this.getVitesse().getY()*vit.getY()));
	}
	
	/**
	 * active le propulseur avant
	 * @param vit
	 */
	public void PropR(Vecteur vit) {
		
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

}