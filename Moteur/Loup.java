package Moteur;

public class Loup extends Carnivore{



	public Loup(int x, int y, boolean femelle, int esperenceDeVie,
			int nbToursPourDevenirPuber, int maxReproduction,
			int esperenceSansManger, int champDeVision, int force, int vitesse) {
		super(x, y, femelle, esperenceDeVie, nbToursPourDevenirPuber, maxReproduction,
				esperenceSansManger, champDeVision, force, vitesse);

	}


	@Override
	public void manger() {
		// TODO Auto-generated method stub
		
	}


	@Override
	public Animal bebe(Etre etre) {
		// TODO Auto-generated method stub
		return null;
	}


}
