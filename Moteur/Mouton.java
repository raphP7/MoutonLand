package Moteur;
public class Mouton extends Herbivore {
	
	public Mouton(int x, int y, boolean femelle, int esperenceDeVie,
			int nbToursPourDevenirPuber, int maxReproduction,
			int esperenceSansManger, int champDeVision, int force, int vitesse) {
		super(x, y, femelle, esperenceDeVie, nbToursPourDevenirPuber, maxReproduction,
				esperenceSansManger, champDeVision, force, vitesse);
	}
	public Mouton(Etre a, Etre b){
		super(a,b);
	}
	public void manger() {
		// TODO Auto-generated method stub
		
	}

}
