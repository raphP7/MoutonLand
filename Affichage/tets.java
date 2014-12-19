package Affichage;


import Moteur.*;
import Moteur.Terrain.Case;

public class tets {

	public static void main(String[] args) throws Exception {
		
		Moteur a = new Moteur();
		
		a.creerAlea("Loup", 10);
		a.creerAlea("Mouton", 30);
		a.creerAlea("Plante", 10);
		
		a.supprimerAle("Loup",7);
		
		a.leTerrain.afficheShell();
		
		for (int i=0 ; i<150 ; i++){
			a.simulation();
		}
		a.leTerrain.afficheShell();
		
	}
	
	
	public static void afficher(Case[][] tab){
	
		
		
	}
	
}
