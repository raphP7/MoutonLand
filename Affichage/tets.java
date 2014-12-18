package Affichage;

import Moteur.*;

public class tets {

	public static void main(String[] args) throws Exception {
		Moteur a = new Moteur();
		
		
		a.creerAlea("Loup", 10);
		a.creerAlea("Mouton", 3);
		a.creerAlea("Plante", 1);
		//a.simulation();
		
		
		a.leTerrain.afficheShell();
		//a.leTerrain.map[0][0].animalPresent=null;
		
		a.supprimerAle("Loup",7);
		
		a.leTerrain.afficheShell();
		
	}
	
	
	public static void afficher(Case[][] tab){
	
		
		
	}
	
}
