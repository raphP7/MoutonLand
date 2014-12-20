package Moteur;

public abstract class Carnivore extends Animal{

	public Carnivore(int x, int y, boolean femelle, int esperenceDeVie,
			int nbToursPourDevenirPuber, int maxReproduction,
			int esperenceSansManger, int champDeVision, int force, int vitesse) {
		super(x, y, femelle, esperenceDeVie, nbToursPourDevenirPuber, maxReproduction,
				esperenceSansManger, champDeVision, force, vitesse);
	}

	public Carnivore(Etre a, Etre b) {
		super(a,b);
	}
}
