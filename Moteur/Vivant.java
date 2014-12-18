package Moteur;

public enum Vivant {
	Loup ("Loup"),
	Mouton ("Mouton"),
	Plante ("Plante");
	
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
