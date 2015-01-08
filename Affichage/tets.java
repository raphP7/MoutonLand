package Affichage;

import java.awt.Point;

import Moteur.*;
import Moteur.animauxCarnivores.Loup;
import Moteur.animauxHerbivores.Mouton;

public class tets {

	public static void main(String[] args) throws Exception {		
		long time=0;
		
		Moteur a = null;
		
		long c=0;
		
		for (int i=0 ; i<1 ; i++){
			c++;
			long startTime = System.currentTimeMillis();

			a= new Moteur(10,10,80);
			a.leTerrain.afficheShell();

			long endTime = System.currentTimeMillis();
			
			time=time+( endTime - startTime );
			
		}
		System.out.println("Cela prend " + (time/c) + " milliseconds en moyene");
		
		System.out.println();
		
		
		Etre m = new Mouton(0,0,true,100,100,100,100,100,100,100);
		Etre m2 = new Mouton(0,0,true,100,100,100,100,100,100,100);
		
		Etre bebe=((EtreVivant)m).bebe(m2,new Point(0,0));
		
		a.creerAlea("Loup", 1);
		//a.creerAlea("Mouton", 60);
		//a.creerAlea("Plante", 100);
		
		a.leTerrain.afficheShell();
		for (int i=0 ; i<10; i++){
			a.simulation();
			a.leTerrain.afficheShell();
		}
		
	}
}
