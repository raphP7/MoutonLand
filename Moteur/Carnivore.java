package Moteur;

import java.awt.Point;
/**
 * La class des Carnivores , ils peuvent manger d'autre carnivore que de leur Race et les herbivore
 * @author Raph
 *
 */
public abstract class Carnivore extends Animal{

	public Carnivore(int x, int y, boolean femelle, int esperenceDeVie,
			int nbToursPourDevenirPuber, int maxReproduction,
			int esperenceSansManger, int champDeVision, int force, int vitesse) {
		super(x, y, femelle, esperenceDeVie, nbToursPourDevenirPuber, maxReproduction,
				esperenceSansManger, champDeVision, force, vitesse);
	}

	public Carnivore(Etre a, Etre b, Point position) {
		super(a,b,position);
	}
}
