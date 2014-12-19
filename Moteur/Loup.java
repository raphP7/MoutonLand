package Moteur;

public class Loup extends Animal{



	public Loup(int x, int y, boolean femelle, int esperenceDeVie,
			int nbToursPourDevenirPuber, int maxReproduction,
			int esperenceSansManger, int champDeVision, int force, int vitesse) {
		super(x, y, femelle, esperenceDeVie, nbToursPourDevenirPuber, maxReproduction,
				esperenceSansManger, champDeVision, force, vitesse);

	}


	public Loup bebe(Animal b) {
		
		return null;
	}

	@Override
	public void manger() {
		// TODO Auto-generated method stub
		
	}


}
