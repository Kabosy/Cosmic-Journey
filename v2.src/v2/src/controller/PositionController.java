package v2.src.controller;


import v2.src.modele.CorpsCeleste;
import v2.src.modele.Vecteur;

public class PositionController {
	private CorpsCeleste model;
	
	public PositionController(CorpsCeleste corps) {
		model = corps;
	}
	
	public void updatePosition(Vecteur nvlPos) {
		model.setPositionInit(new Vecteur(nvlPos.getX(),nvlPos.getY()));
	}
	
}
