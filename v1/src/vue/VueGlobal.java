package vue;

import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import v1.CorpsCeleste;
import v1.Engine;
import v1.Systeme;

public class VueGlobal extends Application {
	private Systeme sys;
	Timeline timeline;
	double canvasHeight;
	double canvasWidth;
	Canvas canvas;
	GraphicsContext gc;
	private Engine eng;

	public VueGlobal(Systeme s, Engine e) {
		sys = s;
		eng = e;
		canvasHeight = sys.getRadius() * 2 - sys.getRadius() / 3;
		canvasWidth = sys.getRadius() * 2;
		canvas = new Canvas(canvasWidth, canvasHeight);
		gc = canvas.getGraphicsContext2D();
	}

	public void start(Stage stage) {
		BorderPane root = new BorderPane();

		Text titre = new Text("Tableau de Bord");
		titre.setFill(Color.WHITE);
		TextArea tb = new TextArea();
		tb.setEditable(false);
		Text vitesseV = new Text("Ship velocity :");
		vitesseV.setFill(Color.WHITE);
		Text posV = new Text("Ship position :");
		posV.setFill(Color.WHITE);
		VBox tabBord = new VBox(titre, tb, vitesseV, posV);

		tabBord.setPadding(new Insets(10, 10, 10, 10));
		tabBord.setMaxWidth(canvas.getWidth() / 4);
		tabBord.setStyle("-fx-background-color: #9773e6");
		VBox systeme = new VBox(canvas);
		systeme.setStyle("-fx-background-color: #1b2631");

		// tabBord.setBackground(new Background(new BackgroundFill(Color.CADETBLUE,
		// CornerRadii.EMPTY, Insets.EMPTY)));

		setImages(); // charge le systeme
		/*
		 * Image de l'espace en arriere plan Image img=new Image("space.jpg"); ImageView
		 * imgV = new ImageView(img); imgV.setFitHeight(canvasHeight);
		 * imgV.setFitWidth(canvasWidth);
		 */
		/*
		 * Text infos = new Text("Data :"); infos.setFill(Color.WHITE);
		 * infos.setX(canvasWidth - canvasWidth / 4); infos.setY(20); Text infosVaisseau
		 * = new Text("Ship Status :"); infosVaisseau.setFill(Color.WHITE);
		 * infosVaisseau.setX(20); infosVaisseau.setY(20);
		 */
		root.setLeft(tabBord);
		root.setRight(systeme);

		Scene scene = new Scene(root);
		stage.setTitle("Cosmic Journey");
		stage.setScene(scene);
		stage.setResizable(false);
		stage.show();
	}

	public void reset() {
		gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
		setImages();
	}

	public void setImages() {
		// Boucle test de recup des images: non fonctionnels.
		// mettre "ressources" en fichier soource?
		// si une image est specifie dans le fichier texte, est elle mise dans
		// ressources? sinon ou est elle?
		int i = 0;
		/*
		 * while(i<sys.size()-1) { try {
		 * 
		 * Image img=new Image("space.jpg");
		 * System.out.println(img.getHeight()+" "+img.getWidth());
		 * gc.drawImage(img,0,0);
		 * 
		 * }catch(NullPointerException e){ System.out.println("null pointer");
		 * }catch(IllegalArgumentException e) { System.out.println("iegal arg"); }
		 * 
		 * i++; }
		 */
		// boucle de parcours du systeme solaire (sans le vaisseau), met en couleurs
		// chacun d'eux aux
		// bonnes coordonees avec la couleur specifie dans le fichier, sinon avec la
		// couleur par defaut du corps.
		gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
		while (i < sys.size() - 1) {
			CorpsCeleste corps = sys.getCorpsCeleste(i);
			gc.setFill(corps.getCOLOR_DEFAULT());

			gc.fillOval(corps.getPositionInit().getX() + corps.getRayon() + 200,
					corps.getPositionInit().getY() + corps.getRayon() + 200, corps.getRayon(), corps.getRayon());

			i++;
		}
		// recupere la position du vaisseau et draw autour de celui-ci
		double posShipX = sys.getCorpsCeleste(i).getPositionInit().getX();
		double posShipY = sys.getCorpsCeleste(i).getPositionInit().getY();
		double[] xPoints = { posShipX - 7.5, posShipX, posShipX + 2.5, posShipX };
		double[] yPoints = { posShipY, posShipY + 2.5, posShipY, posShipY - 2.5 };
		gc.setFill(sys.getCorpsCeleste(i).getCOLOR_DEFAULT());
		gc.fillPolygon(xPoints, yPoints, 4);

		// FUTUR PROBLEME:
		// Colision?
		// Taille des corps "ronds" non specifie, comment les fixes dynamiquement?

	}
}
