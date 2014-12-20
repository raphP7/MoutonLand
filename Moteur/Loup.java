package Moteur;

import java.awt.Point;

public class Loup extends Carnivore{



	public Loup(int x, int y, boolean femelle, int esperenceDeVie,
			int nbToursPourDevenirPuber, int maxReproduction,
			int esperenceSansManger, int champDeVision, int force, int vitesse) {
		super(x, y, femelle, esperenceDeVie, nbToursPourDevenirPuber, maxReproduction,
				esperenceSansManger, champDeVision, force, vitesse);

	}

	public Loup(Etre a , Etre b, Point position){
		super(a,b,position);
	}

	@Override
	public void manger() {
		// TODO Auto-generated method stub
		
	}

}
