package v2.src.controller;

import java.util.List;
import java.util.Map;

import javafx.geometry.Point2D;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Circle;
import v2.src.modele.CorpsCeleste;
import v2.src.modele.Engine;
import v2.src.vue.VueGlobale;
import v2.src.vue.VueTableauBord;

public class VueController  {
	
	private Engine engine;
	
	
	public VueController(Engine engine) {
		this.engine=engine;
	}
	
	public void onClickedCanvas(Map<CorpsCeleste,Circle> lesCercles,MouseEvent e) {
		//System.out.println("pressed"+e.getX()+e.getY());
		double AncienPosX=e.getX();
		double AncienPosY=e.getY();
		engine.designATarget(AncienPosX,AncienPosY,lesCercles);
		
		
	}
	
	public void modifCercle(Map<CorpsCeleste,Circle> cercles) {
		for(CorpsCeleste c: cercles.keySet()) {
			double r=c.getRayon();
			cercles.get(c).setCenterX(c.getPositionInit().getX() - r/2 + VueGlobale.CANVAS_WIDTH/2);
			cercles.get(c).setCenterY(c.getPositionInit().getY() - r/2 + VueGlobale.CANVAS_HEIGHT/2);
		}
		/* old
		for(int idx=0; idx < cercles.size();idx++) {
			double r=engine.getSys().get(idx).getRayon();
			cercles.get(idx).setCenterX(engine.getSys().get(idx).getPositionInit().getX() - r/2 + VueGlobale.CANVAS_WIDTH/2);
			cercles.get(idx).setCenterY(engine.getSys().get(idx).getPositionInit().getY() - r/2 + VueGlobale.CANVAS_HEIGHT/2);
		}
		*/
	}
	
	/*
	public void onActionPlus(VueGlobale vue, VueTableauBord vueTabBoard) {
		engine.setDt(engine.getDt()+10,vue);
		//System.out.println("\nENtree");
	}
	
	public void onActionMoins(VueGlobale vue, VueTableauBord vueTabBoard) {
		engine.setDt(engine.getDt()-10,vue);
		//System.out.println("\nENtree");
	}
	*/
	
	public void modifDtValue(VueGlobale vue, double d) {
		engine.setDt(d,vue);
	}
	
	public void resetTimeline(VueTableauBord tabBord, Engine engine) {
		if(engine.getVaisseau()!=null) {
			tabBord.resetWithShip(engine);
			}else {
				tabBord.resetWithoutShip(engine);
			}
	}

	public void center() {
		if(engine.getVaisseau()!=null) {
			engine.centerOnShip();
		}
		
		
	}
}
