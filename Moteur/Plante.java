package Moteur;

class Plante extends EtreVivant {

	public Plante(int x, int y, boolean femelle, int esperenceDeVie,
			int nbToursPourDevenirPuber, int maxReproduction,
			int esperenceSansManger) {
		super(x, y, femelle, esperenceDeVie, nbToursPourDevenirPuber, maxReproduction,
				esperenceSansManger, 0);
		
		//champ de vision a 0 pour les plantes

	}
	private int valeur;
	
	public boolean unTour(){
		this.incrementeToursEnVie();
		return mort();
		
	}
	
	public boolean mort(){
		
		if (valeur<1 || super.mort()){
			return true;
		}
		
		return false;
		
	}
	public int getValeur() {
		return valeur;
	}
	public void setValeur(int valeur) {
		if (valeur<0){
			this.valeur=0;
			
		}
		this.valeur = valeur;
	}
	
	
	public void manger() {
		// TODO Auto-generated method stub
		
	}


}
