package Moteur;

import java.awt.Point;

public abstract class Herbivore extends Animal{

	public Herbivore(int x, int y, boolean femelle, int esperenceDeVie,
			int nbToursPourDevenirPuber, int maxReproduction,
			int esperenceSansManger, int champDeVision, int force, int vitesse) {
		super(x, y, femelle, esperenceDeVie, nbToursPourDevenirPuber, maxReproduction,
				esperenceSansManger, champDeVision, force, vitesse);
	}

	public Herbivore(Etre a, Etre b, Point position) {
		super(a,b,position);
	}

}
