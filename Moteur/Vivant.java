package Moteur;

public enum Vivant {
	Loup ("Loup"),
	LoupNoir ("LoupNoir"),
	Mouton ("Mouton"),
	MoutonNoir ("MoutonNoir"),
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
