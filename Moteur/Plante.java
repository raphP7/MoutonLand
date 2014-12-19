package Moteur;

import Moteur.Terrain.Case;

public class Plante extends EtreVivant {

	public Plante(int x, int y, boolean femelle, int esperenceDeVie,
			int nbToursPourDevenirPuber, int maxReproduction,
			int esperenceSansManger) {
		super(x, y, femelle, esperenceDeVie, nbToursPourDevenirPuber, maxReproduction,
				esperenceSansManger, 0);
		
		//champ de vision a 0 pour les plantes

	}
	private int valeur;
	
	
	public boolean toujourEnVie(){
		if (valeur<1 || super.toujourEnVie()){
			return false;// Mort
		}
		
		return true;// en vie
		
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

	@Override
	public Animal bebe(Animal b) {
		// TODO Auto-generated method stub
		return null;
	} 

	@Override
	public boolean action(Case[][] map) {
		this.incrementeToursEnVie();
		return toujourEnVie();
	}


}
