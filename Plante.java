
public class Plante extends EtreVivant {

	public Plante(int x, int y, boolean femelle, int esperenceDeVie,
			int nbToursPourDevenirPuber, int maxReproduction,
			int esperenceSansManger, int champDeVision) {
		super(x, y, femelle, esperenceDeVie, nbToursPourDevenirPuber, maxReproduction,
				esperenceSansManger, champDeVision);

	}
	int valeur;
	
	public boolean mortNaturel(){
		
		if (valeur<1 || super.mortNaturel()){
			return true;
		}
		
		return false;
		
	}
	public void manger() {
		// TODO Auto-generated method stub
		
	}


}
