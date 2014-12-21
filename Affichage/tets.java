package Affichage;

import Moteur.*;
import Moteur.Terrain.Case;

public class tets {

	public static void main(String[] args) throws Exception {
		
		Moteur a = new Moteur(10,10,5);
		
		a.leTerrain.afficheShell();
		
		Etre m = new Mouton(0,0,true,100,100,100,100,100,100,100);
		Etre m2 = new Mouton(0,0,true,100,100,100,100,100,100,100);
		
		//Etre bebe=((EtreVivant)m).bebe(m2,new Point(0,0));
		
		a.creerAlea("Loup", 30);
		a.creerAlea("Mouton", 60);
		a.creerAlea("Plante", 100);
		
		
		for (int i=0 ; i<10; i++){
			a.simulation();
		}
		a.leTerrain.afficheShell();
		
	}
	
	
	public static void afficher(Case[][] tab){
	
		
		
	}
	
}
