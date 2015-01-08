package Moteur;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

import Moteur.Intelligence.Emplacement;
import Moteur.Intelligence.Envie;
import Moteur.Intelligence.VisionEtDeplacement;
import Moteur.Terrain.Terrain;
import Moteur.animauxHerbivores.Mouton;
import Moteur.animauxHerbivores.MoutonNoir;


class TestsVisionAnimal {
	public static void main(String[] args) throws Exception {
	
		List<Etre> listAjout =new ArrayList<Etre>();
		Etre aa =new Plante(0,0,false,100,100,100,1000,10);
		Etre a1 =new Mouton(0,0,false,100,100,100,1000, 0, 0, 0);
		
		Etre a2 =new MoutonNoir(0,0,false,100,100,100,1000, 0, 0, 0);
		
		System.out.println(a2.getClass().toString());
		//System.out.println(a1.getClass().toString());
		System.out.println(a2.getClass().getSuperclass().toString());
		
		System.out.println(a2.getClass().toString().contains(a1.getClass().toString()));
		
		}
}