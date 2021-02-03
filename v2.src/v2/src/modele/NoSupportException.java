package v2.src.modele;

public class NoSupportException extends Exception{
	public NoSupportException() {
		super("un ou plusieurs types de corps n'est pas supporté ");
	}
}
