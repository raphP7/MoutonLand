package Moteur;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

import Moteur.Intelligence.Emplacement;
import Moteur.Intelligence.Envie;
import Moteur.Intelligence.FileDeSouvenirs;
import Moteur.Intelligence.VisionEtDeplacement;
import Moteur.Terrain.Terrain;
import Moteur.animauxHerbivores.Mouton;


class TestsVisionAnimal {
	
	
	
	
	public static void main(String[] args) throws Exception {
	
		
			
		List<Etre> listAjout =new ArrayList<Etre>();
		Etre aa =new Plante(0,0,false,100,100,100,1000,10);
		Etre aaa =new Mouton(0,0,false,100,100,100,1000, 0, 0, 0);
		
		Envie[] a= ((Animal)aaa).getLesEnvies();
	
		
		a[0].setValeur(10);
		
		
		System.out.println(a[0].toString());
			
		}
}