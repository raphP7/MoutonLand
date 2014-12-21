package Affichage;

import Moteur.*;
import Moteur.Terrain.Case;

public class tets {

	public static void main(String[] args) throws Exception {
		
		int x=10;
		if(x==11){
			System.out.println(10);
		}
		else if(x==10){
			System.out.println(10);
		}
		else if(x==10){
			System.out.println(10);
		}
		else{
			System.out.println(10);
		}
		
		
		
		long time=0;
		
		Moteur a;
		
		long c=0;
		
		for (int i=0 ; i<1 ; i++){
			c++;
			long startTime = System.currentTimeMillis();

			a= new Moteur(30,30,1);
			a.leTerrain.afficheShell();

			long endTime = System.currentTimeMillis();
			
			time=time+( endTime - startTime );
			
		}
		System.out.println("Cela prend " + (time/c) + " milliseconds en moyene");
		
		System.out.println();
		
		
		Etre m = new Mouton(0,0,true,100,100,100,100,100,100,100);
		Etre m2 = new Mouton(0,0,true,100,100,100,100,100,100,100);
		
		//Etre bebe=((EtreVivant)m).bebe(m2,new Point(0,0));
		
		//a.creerAlea("Loup", 1);
		//a.creerAlea("Mouton", 60);
		//a.creerAlea("Plante", 100);
		
		
		//for (int i=0 ; i<50; i++){
			//a.simulation();
		
		//a.leTerrain.afficheShell();
	}
}
