package Affichage;


import Moteur.*;
import Moteur.Terrain.Case;

public class tets {

	public static void main(String[] args) throws Exception {
		
		Moteur a = new Moteur();
		
		Etre m = new Mouton(0,0,true,100,100,100,100,100,100,100);
		Etre m2 = new Mouton(0,0,true,100,100,100,100,100,100,100);
		
		Etre bebe=((EtreVivant)m).bebe(m2);
		
		a.creerAlea("Loup", 50);
		//a.creerAlea("Mouton", 30);
		//a.creerAlea("Plante", 10);
		
		
		a.leTerrain.afficheShell();
		
		for (int i=0 ; i<10 ; i++){
			a.simulation();
		}
		a.leTerrain.afficheShell();
		
	}
	
	
	public static void afficher(Case[][] tab){
	
		
		
	}
	
}
