package v2.src.main;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import javafx.application.Application;
import javafx.stage.Stage;
import v2.src.modele.CorpsCeleste;
import v2.src.modele.CorpsFixe;
import v2.src.modele.Engine;
import v2.src.modele.Vaisseau;
import v2.src.vue.VueGlobale;

public class Main extends Application {

	public static void main(String[] args) {
		Application.launch(args);
	}

	@Override
	public void start(Stage stage) throws Exception {
		try {
			File data = new File(getParameters().getRaw().get(0));
			Engine model = new Engine();
			model.stockParams(data);
			VueGlobale vue = new VueGlobale(model);
			model.addObserver(vue);
			vue.start(stage);
			showSystemData(model);
			System.out.println(model.getSys());
			model.timeline(vue);
		} catch (FileNotFoundException e) {
			System.out.println(e.getMessage());
		}

	}

	// Affiche les informations sur chaque corps dans le systeme
	public void showSystemData(Engine model) {
		for (CorpsCeleste c : model.getSys()) {
			if(c==null) {

			}
			else if (c instanceof Vaisseau) {
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
	}

	// Affiche le fichier sous forme brute
	public void showFileData(File fichier) {
		String fileName = fichier.getAbsolutePath();
		String line = null;
		try {
			FileReader file = new FileReader(fileName);
			BufferedReader bufferedReader = new BufferedReader(file);
			while ((line = bufferedReader.readLine()) != null) {
				System.out.println(line);
			}
			bufferedReader.close();
		} catch (FileNotFoundException e) {
			System.err.println("File not Found");
		} catch (IOException i) {
			System.err.println("IO Exception");
		}	
	}
}
