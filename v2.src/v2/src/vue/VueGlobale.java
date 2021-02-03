package v2.src.vue;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;

import com.sun.prism.paint.Color;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import v2.src.controller.VaisseauController;
import v2.src.controller.VueController;
import v2.src.modele.CorpsCeleste;
import v2.src.modele.CorpsFixe;
import v2.src.modele.CorpsSimule;
import v2.src.modele.Engine;
import v2.src.modele.Vecteur;

public class VueGlobale extends Application  implements Observer{
	//Vues
	private VueUnivers vueUnivers;
	private VueTableauBord vueTabBord;
	private VueSeconde vueSeconde;
	
	//Controllers
    private VaisseauController vaisCon;
    private VueController vuCon;
    
    private Scene scene;

	private Map<CorpsCeleste,Circle> lesCercles;
	public final static double CANVAS_WIDTH = 500 * 2;
	public final static double CANVAS_HEIGHT = 500 * 2 - 500 / 3;
	
	private CorpsCeleste corpFocus;
	
	//Constructeur
	public VueGlobale(Engine engine) {
		vueUnivers = new VueUnivers(engine);
		vueSeconde = new VueSeconde(engine);
		vueTabBord = new VueTableauBord(vueUnivers.getCanvas(), engine);
		vaisCon = new VaisseauController(engine);
		this.vuCon =new VueController(engine);
		lesCercles=createCircle(engine);
		System.out.println(engine.getSys());
	}

	public void resetTimeline(Engine engine) {
		vuCon.resetTimeline(vueTabBord, engine);
		reset();
	}
	@Override
	public void start(Stage stage) throws Exception {
		BorderPane root = new BorderPane();
		VBox canvas = new VBox(vueUnivers.getCanvas());
		root.setLeft(vueTabBord.getTableauBord());
		root.setRight(canvas);
		canvas.setStyle("-fx-background-color: #000000");
		canvas.setOnMouseClicked(new OnClicked());
		/*
		vueTabBord.getAugment().setOnAction(e->{
			vuCon.onActionPlus(this,this.vueTabBord);
		});
		vueTabBord.getReduce().setOnAction(e->{
			vuCon.onActionMoins(this,this.vueTabBord);
		});
		 */
		vueTabBord.getDoneButton().setOnAction(e->{
			if(!vueTabBord.getDtValue().getText().equals("")) {
				vuCon.modifDtValue(this, Double.parseDouble(vueTabBord.getDtValue().getText()));
				vueTabBord.getDtValue().setEditable(false);
				vueTabBord.getDoneButton().setDisable(true);
				vueTabBord.getChangeButton().setDisable(false);
				vueTabBord.getDoneButton().setStyle("-fx-background-color: black");
				vueTabBord.getDoneButton().setTextFill(Paint.valueOf("white"));
				vueTabBord.getChangeButton().setStyle("-fx-background-color: white");
				vueTabBord.getChangeButton().setTextFill(Paint.valueOf("black"));
			}
		});

		vueTabBord.getChangeButton().setOnAction(e->{
			if(!vueTabBord.getDtValue().getText().equals("")) {
				vueTabBord.getDtValue().setEditable(true);
				vueTabBord.getDoneButton().setDisable(false);
				vueTabBord.getChangeButton().setDisable(true);
				vueTabBord.getDoneButton().setStyle("-fx-background-color: white");
				vueTabBord.getDoneButton().setTextFill(Paint.valueOf("black"));
				vueTabBord.getChangeButton().setStyle("-fx-background-color: black");
				vueTabBord.getChangeButton().setTextFill(Paint.valueOf("white"));
			}
		});




		Scene scene = new Scene(root);
		this.scene = scene;
		scene.addEventFilter(KeyEvent.KEY_PRESSED, e -> {
			double angleStep = Math.toRadians(5);
			if (e.getCode().equals(KeyCode.X)) {
				//vaisCon.ActivateRightProp();
				vaisCon.setDirection(vaisCon.getDirection() + angleStep);	
				//System.out.println("Right key was pressed");
			} else if (e.getCode().equals(KeyCode.Z)) {
				//vaisCon.ActivateLewftProp();
				vaisCon.setDirection(vaisCon.getDirection() - angleStep);	
				//System.out.println("Left key was pressed");
			} else if (e.getCode().equals(KeyCode.SPACE)) {
				vaisCon.ActivateBackwardProp();		
				//System.out.println("Down arrow key was pressed");
			} else if (e.getCode().equals(KeyCode.UP)) {
				vaisCon.ActivateBackwardProp();
				//System.out.println("Up arrow key was pressed");
			} else if(e.getCode().equals(KeyCode.LEFT)) {
				vaisCon.ActivateLeftProp();
			} else if(e.getCode().equals(KeyCode.RIGHT)) {
				vaisCon.ActivateRightProp();
			} else if(e.getCode().equals(KeyCode.DOWN)) {
				vaisCon.ActivateForwardProp();
			} else {
				System.out.println("Another key was pressed");
			}
		});
		
		stage.setTitle("Cosmic Journey");
		stage.setScene(scene);
		stage.setResizable(false);
		stage.show();

		
		VBox canvas2 = new VBox(vueSeconde.getCanvas());
	
		Scene sceneSeconde= new Scene(canvas2);
		Stage stageSeconde =new Stage();
		stage.setOnCloseRequest(e->{
			stageSeconde.close();
		});
		
		canvas2.setStyle("-fx-background-color: #101010");
		stageSeconde.setScene(sceneSeconde);
		stageSeconde.setResizable(false);
		stageSeconde.initStyle(StageStyle.UTILITY);
		stageSeconde.setAlwaysOnTop(true);
		stageSeconde.setWidth(200);
		stageSeconde.setHeight(200);
		stageSeconde.setX((stage.getX()+stage.getWidth())-stageSeconde.getWidth());
		stageSeconde.setY((stage.getY()+stage.getHeight())-stageSeconde.getHeight());
		stageSeconde.setOnCloseRequest(e->{
			e.consume();
		});
		stageSeconde.show();
		
		
		
	}

	
	/**
	 * 
	 * Instancie des Circle pour chaque objet du systeme solaire sauf le vaisseau;
	 * 
	 * @param sys
	 * @return
	 * renvoie la liste des cercles cree. 
	 */
		private Map<CorpsCeleste, Circle> createCircle(Engine sys) {
			Map<CorpsCeleste,Circle> res=new HashMap<CorpsCeleste, Circle>();
			Vecteur vTmp;
			Circle tmp;
			for(CorpsCeleste c: sys.getSys()) {
				if(c instanceof CorpsSimule || c instanceof CorpsFixe) {
					vTmp=c.getPositionInit();
					tmp= new Circle(vTmp.getX()- c.getRayon()/2 + CANVAS_WIDTH/2, vTmp.getY()- c.getRayon()/2 + CANVAS_HEIGHT/2, c.getRayon());
					res.put(c, tmp);
				}
				
			}
		//	System.out.println(res.get(1).toString());
			return res;
		}
	
	public Scene getScene() {
		return this.scene;
	}

	public VueTableauBord getTabBord() {
		return vueTabBord;
	}

	public VueUnivers getVueUnivers() {
		return vueUnivers;
	}

	public class OnClicked implements EventHandler <MouseEvent> {
		public void handle(MouseEvent e) {
			vuCon.onClickedCanvas(lesCercles,e);
		}
	}

	public void update(Observable arg0, Object arg1) {
		//ce qu'on veut faire quand on clique sur la planète
		//this.vueTabBord.setContent();
		//this.vueUnivers.setContent();
		corpFocus=(CorpsCeleste)arg1;
		this.vueSeconde.setContent(corpFocus);	
	}
	
	public void reset() {
		vuCon.modifCercle(lesCercles);
		vueUnivers.reset(vueTabBord);
		vueSeconde.reset(vueTabBord);
	}
}
