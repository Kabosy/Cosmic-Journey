package v2.src.controller;

import v2.src.modele.Engine;
import v2.src.modele.Vecteur;

public class VaisseauController {

	private Engine engine;

	public VaisseauController(Engine engine) {
		this.engine = engine;
	}

	/**
	 * met a jour la position du vaisseau selon la loi de Demeter
	 * 
	 * @param nvlPos vecteur nouvelle position
	 */
	public void updatePosition(Vecteur nvlPos) {
		if (engine.getVaisseau() != null) {
			engine.setPosVaisseau(new Vecteur(nvlPos.getX(), nvlPos.getY()));
		}

	}// est ce qu'on ne pourrait pas utiliser directement "nvlPos" plutot que
		// d'instancier un nouveau vecteur qui sera identique a nvlPos?

	/**
	 * active le propulseur droit
	 */
	public void ActivateRightProp() {
		if (engine.getVaisseau() != null) {
			engine.getVaisseau().propDroit();
		}
	}

	/**
	 * active le propulseur gauche
	 */
	public void ActivateLeftProp() {
		if (engine.getVaisseau() != null) {
			engine.getVaisseau().propGauche();
		}
	}

	/**
	 * active le propulseur avant
	 */
	public void ActivateForwardProp() {
		if (engine.getVaisseau() != null) {
			engine.getVaisseau().propAvant();
		}
	}

	/**
	 * active le propulseur arri�re
	 */
	public void ActivateBackwardProp() {
		if (engine.getVaisseau() != null) {
			engine.getVaisseau().propArriere();
		}
	}

	/**
	 * active le propulseur principal
	 */
	public void ActivateMainProp() {
		if (engine.getVaisseau() != null) {
			engine.getVaisseau().propMain();
		}
	}

	public void setDirection(double d) {
		if (engine.getVaisseau() != null) {
			engine.getVaisseau().setDirection(d);
		}
	}

	public double getDirection() {
		if (engine.getVaisseau() != null) {
			return engine.getVaisseau().getDir();
		} else {
			return 0;
		}
	}
}
