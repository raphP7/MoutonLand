package Moteur;

public enum Vivant {
	LOUP ("loup"),
	MOUTON ("mouton"),
	PLANTE ("plante");
	
	private String vivant;
	
	Vivant(String vivant){
		this.setVivant(vivant);
	}

	public String getVivant() {
		return vivant;
	}

	public void setVivant(String vivant) {
		this.vivant = vivant;
	}
	
}
