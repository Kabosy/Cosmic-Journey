package v1;

public enum MoveType {

	FIXE("Fixe"),SIMULE("Simule");
	
	private final String VALUE;
	
	private MoveType(String value) {
		this.VALUE=value;
	}
	
	public String getVal() {
		return this.VALUE;
	}
}
