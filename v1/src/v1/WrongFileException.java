package v1;

public class WrongFileException extends Exception {
	/**
	 * affiche un message quand l'exception est declenchee
	 */
	public WrongFileException(){
		System.out.println("Fichier lu n'est pas un fichier .txt");
	}
}
