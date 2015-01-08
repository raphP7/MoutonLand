package Moteur.animauxHerbivores;

import java.awt.Point;
import Moteur.Etre;
import Moteur.interfaceNoir;

public class MoutonNoir extends Mouton implements interfaceNoir {

	public MoutonNoir(int x, int y, boolean femelle, int esperenceDeVie,
			int nbToursPourDevenirPuber, int maxReproduction,
			int esperenceSansManger, int champDeVision, int force, int vitesse) {
		super(x, y, femelle, esperenceDeVie, nbToursPourDevenirPuber, maxReproduction,
				esperenceSansManger, champDeVision, force, vitesse);
		// TODO Auto-generated constructor stub
	}
	public MoutonNoir(Etre a, Etre b, Point position){
		super(a,b,position);
	}
}