package v2.src.vue;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import javafx.geometry.Insets;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import v2.src.controller.VueController;
import v2.src.modele.CorpsCeleste;
import v2.src.modele.Engine;

public class VueTableauBord {
	
	private VBox tabBord;
	private Button augment = new Button("+");
	private Button reduce = new Button("-");
	private Button done = new Button("Done");
	Button change = new Button("Unlock");
	private TextField dtValues = new TextField();
	Text setTime = new Text();
	Text timeStep = new Text("   0 s");
	Text time = new Text(" ");
	Text vitesseV = new Text(" ");
	Text posV = new Text(" ");
	Text infosPlanetes = new Text("\nInformations Detaillees : \n");
	private List<String> infos = new ArrayList<String>();
	public boolean buttonPressed = false;
	public boolean resetOK = false;

	private double tempsEcoule = 0;
	
	private VueController vc;

	private DecimalFormat df1 = new DecimalFormat("#.######"); // format pour la vitesse
	private DecimalFormat df2 = new DecimalFormat("#.###"); // format pour le temps
	private DecimalFormat df3 = new DecimalFormat("####.####"); // format pour la position
	private DecimalFormat df4 = new DecimalFormat("####.###");
	
	public VueTableauBord(Canvas canvas, Engine model) {
		TextArea tb = new TextArea("               Tableau de Bord");
		tb.setEditable(false);
		tb.setMaxHeight(1);
		vc=new VueController(model);
		//Slider slider = new Slider();
		Button showNames = new Button("Show Names");
		Text espace = new Text("   ");
		Button viewOnShip = new Button("Center on Ship");
		HBox options = new HBox(showNames, espace, viewOnShip);
			
		rempliInfosPlanetes(infosPlanetes, model);
		if(model.getVaisseau()!=null) {
			resetWithShip(model);
		}
		//HBox buttons = new HBox(augment,reduce,timeStep);
		HBox buttons = new HBox(dtValues, done, change);
		showNames.setOnAction(e -> {
			if (buttonPressed) {
				buttonPressed = false;
			} else {
				buttonPressed = true;
			}
			handleButton(model.getSys(), canvas,0,0);
		});
		
		viewOnShip.setOnAction(e->{
			vc.center();
		});
		tabBord = new VBox(tb, time,setTime,buttons,vitesseV, posV, infosPlanetes, options);
		tabBord.setPadding(new Insets(10, 10, 10, 10));
		tabBord.setMaxWidth(canvas.getWidth() / 4);
		tabBord.setStyle("-fx-background-color: #1b2631");
	}

	public VBox getTableauBord() {
		return tabBord;
	}

	public void resetWithShip(Engine model) {
		tempsEcoule += model.getDt();
		setTime.setText("\nSaisir une nouvelle valeur de pas de temps ici :" );
		setTime.setFill(Color.WHITE);
		timeStep.setText("   "+df4.format(model.getDt())+" s");
		timeStep.setFill(Color.WHITE);
		time.setText("\nTime since launch : \n" + df2.format(tempsEcoule / 1000.0) + " s");
		time.setFill(Color.WHITE);
		vitesseV.setText(("\nShip velocity : " + df1.format(model.getVaisseau().getVitesse().norme())));
		vitesseV.setFill(Color.WHITE);
		posV.setText("\nShip position : \nPos X : " + df3.format(model.getVaisseau().getPositionInit().getX()) + "\nPos Y : " + df3.format(model.getVaisseau().getPositionInit().getY()));
		posV.setFill(Color.WHITE);
		infos.clear();
		infosPlanetes.setText("\nInformations Detaillees : \n");
		rempliInfosPlanetes(infosPlanetes, model);
	}	
	
	public void resetWithoutShip(Engine model) {
		tempsEcoule += Engine.timeTick;
		
		setTime.setText("\nSaisir une nouvelle valeur de pas de temps ici :" );
		setTime.setFill(Color.WHITE);
		timeStep.setText("   "+df4.format(model.getDt())+" s");
		timeStep.setFill(Color.WHITE);
		time.setText("\nTime since launch : \n" + df2.format(tempsEcoule / 1000.0) + " s");
		time.setFill(Color.WHITE);
		
		infos.clear();
		infosPlanetes.setText("\nInformations Detaillees : \n");
		rempliInfosPlanetes(infosPlanetes, model);
	}
	

	public void rempliInfosPlanetes(Text infosPlanetes, Engine model) {
		// Récupere les Corps Simules non vaisseau (de [1] à [size-1]
		for (int i = 1; i < model.getSys().size() - 1; i++) {
			CorpsCeleste c = model.getSys().get(i);
			if(c!=null) {
				infos.add("\n" + c.getNom() + " : \nPos X : " + df3.format(c.getPositionInit().getX()) + "\nPos Y : "
						+ df3.format(c.getPositionInit().getY()) + "\nVitesse : " + df1.format(c.getVitesse().norme())
						+ "\n");
			}	
		}
		for (String s : infos) {
			infosPlanetes.setText(infosPlanetes.getText() + s);
		}
		infosPlanetes.setFill(Color.WHITE);
	}
	
	public void effaceInfosPlanetes(Text infosPlanetes) {
		infosPlanetes.setText("");
	}

	//Button handling show names
	public static void handleButton(List<CorpsCeleste> sys, Canvas canvas,double shiftX,double shiftY) {
		GraphicsContext gc = canvas.getGraphicsContext2D();
		for (CorpsCeleste c : sys) {
			if(c!=null) {
			gc.setStroke(Color.WHITE);
			gc.strokeText(c.getNom(), (c.getPositionInit().getX()-shiftX) - c.getRayon()/2 + canvas.getWidth() / 2,
					(c.getPositionInit().getY()-shiftY) - c.getRayon()/2 + canvas.getHeight() / 2 - 7);
			}
		}
	}
	public Button getDoneButton() {
		return done;
	}
	
	public Button getChangeButton() {
		return change;
	}
	
	public TextField getDtValue() {
		return dtValues;
	}
	/*
	public Button getAugment() {
		return augment;
	}
	
	public Button getReduce() {
		return reduce;
	}
	*/
}
