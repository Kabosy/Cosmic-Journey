package v2.src.modele;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Observable;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.geometry.Point2D;
import javafx.scene.shape.Circle;
import javafx.util.Duration;
import v2.src.vue.VueGlobale;

public class Engine extends Observable {
	private List<CorpsCeleste> systeme;
	private List<Double> paramsValue;
	private List<String> paramsNames;
	private Vaisseau vaisseau;
	Timeline timeline = null;
	public static final double G = 0.01;
	public static double timeTick;
	public double dt;

	public CorpsCeleste corpFocus;

	public Engine() {
		paramsValue = new ArrayList<Double>();
		paramsNames = new ArrayList<String>();
		systeme = new ArrayList<CorpsCeleste>();
	}

	public Vaisseau getVaisseau() {
		return this.vaisseau;
	}

	private static double getValueInDouble(Iterator<String> it) {
		try {
			return Double.parseDouble(it.next().split("=")[1]);
		} catch (NullPointerException e) {
			// System.err.println(e.getMessage());
			throw e;
		} catch (NumberFormatException e) {
			// System.err.println(e.getMessage());
			throw e;
		} catch (NoSuchElementException e) {
			// System.err.println(e.getMessage());
			throw e;
		} catch (ArrayIndexOutOfBoundsException e) {
			// System.err.println(e.getMessage());
			throw e;
		}
	}

	public void stockParams(Iterator<String> it) {

	}

	/**
	 * Parseur de fichier et stockage des donnees pour une meilleure manipulation
	 * dans le code
	 * 
	 * @param file : fichier astro ou txt a parser
	 * @throws IOException        problème de fichier
	 * @throws SyntaxeException   probleme de syntaxe
	 * @throws NoSupportException problème de support
	 */
	public void stockParams(File file) throws IOException, SyntaxeException, NoSupportException {
		FileReader fileR = new FileReader(file);
		BufferedReader bufferedReader = new BufferedReader(fileR);
		String line = bufferedReader.readLine();

		try {
			boolean pass = false;
			boolean ship = false;
			while (line != null) {
				String[] stringVal = line.split(" ");
				List<String> param = new ArrayList<>();
				param.addAll(Arrays.asList(stringVal));
				Iterator<String> paramIt = param.iterator();
				if (line.contains("PARAM")) {
					if (pass) {
						throw new SyntaxeException();
					}
					while (paramIt.hasNext()) {
						if (!pass) {
							paramIt.next();
							pass = true;
						}
						stockParam(paramIt);
					}
				} else if (line.contains("Fixe")) {
					if (!pass) {
						throw new SyntaxeException();
					}
					stockFixe(paramIt);
				} else if (line.contains("Simule")) {
					if (!pass) {
						throw new SyntaxeException();
					}
					stockSimule(paramIt);
				} else if (line.contains("Ellipse")) {
					throw new NoSupportException();
				} else if (line.contains("Cercle")) {
					throw new NoSupportException();
				} else if (line.contains("Vaisseau")) {
					if (!pass) {
						throw new SyntaxeException();
					} else if (ship) { // s'il y a deja un vaisseau alors erreur de synthaxe
						throw new SyntaxeException();
					} else if (!ship) {
						ship = true;
					}
					stockVaisseau(paramIt);
				}
				line = bufferedReader.readLine();
			}
			if (!ship) {
				this.vaisseau = null;
				systeme.add(this.vaisseau);
			}

		} catch (ArrayIndexOutOfBoundsException e) {
			System.out.println(
					"erreur de syntaxe dans le fichier: vous avez peut etre omis une valeur ou mis une valeur en trop...");
			System.exit(1);
		} catch (NumberFormatException e) {
			System.out.println(
					"erreur de syntaxe dans le fichier: une des valeurs que vous avez saisie n'est pas correct...");
			System.exit(1);
		} catch (NullPointerException e) {
			System.out.println("erreur de syntaxe dans le fichier");
			System.exit(1);
		} catch (NoSuchElementException e) {
			System.out.println("erreur de syntaxe dans le fichier");
			System.exit(1);
		} catch (IllegalArgumentException e) {
			System.out.println("erreur de syntaxe dans le fichier");
			System.exit(1);
		} catch (SyntaxeException e) {
			System.out.println(e.getMessage());
			System.exit(1);
		} catch (NoSupportException e) {
			System.out.println(e.getMessage());
			System.exit(1);
		}
		timeTick = paramsValue.get(1);
		dt = paramsValue.get(1);
		bufferedReader.close();
	}

	public void stockParam(Iterator<String> it) {
		String currIt = it.next();
		paramsNames.add(currIt.split("=")[0]);
		paramsValue.add(Double.parseDouble(currIt.split("=")[1]));
	}

	public void stockFixe(Iterator<String> it) {
		String nom = it.next().split(":")[0];
		it.next();
		double masse = getValueInDouble(it);
		double posx = getValueInDouble(it);
		double posy = getValueInDouble(it);
		if (it.hasNext()) {
			String color = it.next().split("=")[1].toUpperCase();
			systeme.add(new CorpsFixe(nom, masse, posx, posy, color));
		} else {
			systeme.add(new CorpsFixe(nom, masse, posx, posy));
		}
	}

	public void stockSimule(Iterator<String> it) {
		String nom = it.next().split(":")[0];
		it.next();
		double masse = getValueInDouble(it);
		double posx = getValueInDouble(it);
		double posy = getValueInDouble(it);
		double vitx = getValueInDouble(it);
		double vity = getValueInDouble(it);
		if (it.hasNext()) {
			String color = it.next().split("=")[1].toUpperCase();
			systeme.add(new CorpsSimule(nom, masse, posx, posy, vitx, vity, color));
		} else {
			systeme.add(new CorpsSimule(nom, masse, posx, posy, vitx, vity));
		}
	}

	public void stockVaisseau(Iterator<String> it) {
		String nom = it.next().split(":")[0];
		it.next();
		double masse = getValueInDouble(it);
		double posx = getValueInDouble(it);
		double posy = getValueInDouble(it);
		double vitx = getValueInDouble(it);
		double vity = getValueInDouble(it);
		double pprincipal = getValueInDouble(it);
		double pretro = getValueInDouble(it);
		if (it.hasNext()) {
			String color = it.next().split("=")[1].toUpperCase();
			this.vaisseau = new Vaisseau(nom, masse, posx, posy, vitx, vity, pprincipal, pretro, color);
			systeme.add(this.vaisseau);
		} else {
			this.vaisseau = new Vaisseau(nom, masse, posx, posy, vitx, vity, pprincipal, pretro);
			systeme.add(this.vaisseau);
		}
	}

	public void show() {
		System.out.println("PARAMS :");
		for (int i = 0; i < paramsValue.size(); i++) {
			System.out.println(paramsNames.get(i) + " : " + paramsValue.get(i));
		}
		System.out.println("");
	}

	public List<Double> getParamsValues() {
		return paramsValue;
	}

	public List<String> getParamsNames() {
		return paramsNames;
	}

	public List<CorpsCeleste> getSys() {
		return systeme;
	}

	/**
	 * modifie la vitesse et la position de chacun des corps du systeme
	 */

	public void deplacement() {
		for (CorpsCeleste c : this.systeme) {
			if (c != null) {
				c.deplacement(systeme, this.dt);
			}
		}
	}

	public void timeline(VueGlobale vue) {
		timeline = new Timeline(new KeyFrame(Duration.millis(timeTick * 1000), e -> {
			deplacement();
			vue.resetTimeline(this);

		}));
		timeline.setCycleCount(Timeline.INDEFINITE);
		timeline.play();
	}

	/**
	 * met a jour la position du vaisseau selon la loi de Demeter
	 * 
	 * @param v position du vaisseau
	 */
	public void setPosVaisseau(Vecteur v) {
		vaisseau.setPositionInit(v);
	}

	public void changeObserverFlag() {
		this.setChanged();
	}

	public void setDt(double dt, VueGlobale vue) {
		this.paramsValue.set(1, dt);
		System.out.println("valeur de dt: " + this.paramsValue.get(1));
		this.dt = paramsValue.get(1);
		System.out.println("valeur de dt reel: " + this.dt);
		timeline.stop();
		this.timeline(vue);
	}

	public double getDt() {
		return dt;
	}

	public void designATarget(double ancienPosX, double ancienPosY, Map<CorpsCeleste, Circle> lesCercles) {
		boolean cerclTouche = false;
		for (CorpsCeleste c : lesCercles.keySet()) {
			if (lesCercles.get(c).contains(new Point2D(ancienPosX, ancienPosY))) {
				corpFocus = c;
				changeObserverFlag();
				notifyObservers(corpFocus);
				cerclTouche = true;
			}
		}

	}

	public void centerOnShip() {
		corpFocus = this.vaisseau;
		changeObserverFlag();
		notifyObservers(corpFocus);

	}
}
