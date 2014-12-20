package Moteur;

import Moteur.Terrain.Case;

public class Plante extends EtreVivant {

	private int valeur;
	
	public Plante(int x, int y, boolean femelle, int esperenceDeVie,
			int nbToursPourDevenirPuber, int maxReproduction,
			int esperenceSansManger, int Valeur) {
		super(x, y, femelle, esperenceDeVie, nbToursPourDevenirPuber, maxReproduction,
				esperenceSansManger, 0);
		
		//champ de vision a 0 pour les plantes

		this.setValeur(valeur);
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
	
	public void decrementerValeur(){
		this.setValeur(this.getValeur()-1);
	}
	public void manger() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Etre action(Case[][] map) {
		this.incrementeToursEnVie();
		return null;
		
	}

	@Override
	public boolean isMort() {

		if (valeur<1 || super.toujourEnVie()){
			return true;// Mort
		}
		
		return false;// en vie
		

	}
}
