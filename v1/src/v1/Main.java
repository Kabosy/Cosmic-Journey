package v1;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.util.Duration;
import vue.VueGlobal;

public class Main extends Application {
	Engine engine = new Engine();
	Timeline timeline;

	// Le fichier texte de description doit etre passé en parametre de compilation.
	// EVITEZ LES ACCENTS (n'est-ce pas flo)
	public static void main(String[] args) {
		Application.launch(args);
	}

	public void start(Stage primaryStage) throws IOException {
		File donnee = new File(getParameters().getRaw().get(0));
		Systeme univers = bigbang(donnee);
		VueGlobal vue = new VueGlobal(univers, engine);

		// Affichage des données de chaque Corps Celeste:
		for (CorpsCeleste c : univers.getSysteme()) {
			if (c instanceof Vaisseau) {
				Vaisseau v = (Vaisseau) c;
				System.out.println("NOM : " + v.getNom() + "\nMASSE :" + v.getMasse() + "\nPOSX :"
						+ v.getPositionInit().getX() + "\nPOSY :" + v.getPositionInit().getY() + "\nVITX :"
						+ v.getVitesse().getX() + "\nVITY :" + v.getVitesse().getY() + "\nPPRINCIP :"
						+ v.getPoussPrincip() + "\nPRETRO :" + v.getPoussRetro());
			} else if (c instanceof CorpsFixe) {
				System.out.println("NOM : " + c.getNom() + "\nMASSE :" + c.getMasse() + "\nPOSX :"
						+ c.getPositionInit().getX() + "\nPOSY :" + c.getPositionInit().getY() + "\n");
			} else {
				System.out.println("NOM : " + c.getNom() + "\nMASSE :" + c.getMasse() + "\nPOSX :"
						+ c.getPositionInit().getX() + "\nPOSY :" + c.getPositionInit().getY() + "\nVITX :"
						+ c.getVitesse().getX() + "\nVITY :" + c.getVitesse().getY() + "\n\n");
			}
		}
		vue.start(primaryStage);
		timeline = new Timeline(new KeyFrame(Duration.millis(45), e -> {
			for (CorpsCeleste c : univers.getSysteme()) {
				Vecteur acc = Engine.VecteurAcc(univers, c);
				c.deplacement(acc);
				// c.setPositionInit(Engine.NouvellePosition(c, 0.5, univers));
				// System.out.println(c.getPositionInit().getX() + " ////// " +
				// c.getPositionInit().getY());

			}
			vue.reset();
		}));
		timeline.setCycleCount(Timeline.INDEFINITE);
		timeline.play();

	}

	private Systeme bigbang(File loiDeLunivers) throws IOException {
		// Creation des corps celestes (ajout dans la liste univers ducoup dans le
		// canvas dans le main)
		List<CorpsCeleste> univers = new ArrayList<CorpsCeleste>();

		Systeme s = new Systeme(univers);

		engine.stockParam(loiDeLunivers, s);
		s.setRadius(engine.getParamsValues().get(3));
		engine.show();

		return s;

		/*
		 * if(loiDeLunivers.getName().substring(loiDeLunivers.getName().lastIndexOf(".")
		 * ).equals("txt")) { try { throw new WrongFileException(); }catch(Exception e)
		 * { e.printStackTrace(); bin/.gitignore } } String fileName =
		 * loiDeLunivers.getAbsolutePath(); String line = null; try {
		 * 
		 * /* } catch (FileNotFoundException e) { System.err.println("File not Found");
		 * } catch (IOException i) { System.err.println("IO Exception"); }
		 */
	}

	// methode qui affiche les donnees du fichiers
	private void showData(File fichier) {
		String fileName = fichier.getAbsolutePath();
		String line = null;
		try {
			FileReader file = new FileReader(fileName);
			BufferedReader bufferedReader = new BufferedReader(file);
			while ((line = bufferedReader.readLine()) != null) {
				System.out.println(line);
			}
		} catch (FileNotFoundException e) {
			System.err.println("File not Found");
		} catch (IOException i) {
			System.err.println("IO Exception");
		}
	}
}
