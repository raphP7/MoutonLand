public class Mouton extends Animal {
	
	public Mouton(int x, int y, boolean femelle, int esperenceDeVie,
			int nbToursPourDevenirPuber, int maxReproduction,
			int esperenceSansManger, int champDeVision, int force, int vitesse) {
		super(x, y, femelle, esperenceDeVie, nbToursPourDevenirPuber, maxReproduction,
				esperenceSansManger, champDeVision, force, vitesse);
	}

	public Mouton bebe(Animal b) {

		if (super.reproduction(b) && b instanceof Mouton) {
			return null;
		}
		return null;
	}

	public boolean mort(){
		if(super.mort()){
			return true;
		}
		else {
			return false;
		}
		
	}
	public void manger() {
		// TODO Auto-generated method stub
		
	}


}
