package Moteur;

import Moteur.Intelligence.Envie;
import Moteur.Terrain.Case;

public class Plante extends EtreVivant {

	private int valeur;
	
	public Plante(int x, int y, boolean femelle, int esperenceDeVie,
			int nbToursPourDevenirPuber, int maxReproduction,
			int esperenceSansManger, int valeur) {
		super(x, y, femelle, esperenceDeVie, nbToursPourDevenirPuber, maxReproduction,
				esperenceSansManger, 0);
		
		//champ de vision a 0 pour les plantes

		
		this.setValeur(valeur);
	}
	
	public void manger(Etre etreManger, Envie envieTemporaire){
		
		
		
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
		System.out.println("decrementer");
		this.setValeur((this.getValeur())-1);
	}
	public void manger() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Etre action(Case[][] map) {
		incrementeToursEnVie();
		return null;
		
	}

	@Override
	public boolean isMort() {
		if (valeur<1 || !toujourEnVie()){
			return true;// Mort
		}
		return false;// en vie
		

	}
}
