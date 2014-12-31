import Affichage.Fenetre;
import Moteur.Moteur;
public class Demarrage {

	public static void main(String[] args) throws Exception {
		
		Moteur moteur=new Moteur(6,6,0);
		
		Fenetre fenetre=new Fenetre(moteur.leTerrain);
		
		Moteur.laFenetre=fenetre;
		
		//moteur.creerAlea("Loup", 2);
		//moteur.creerAlea("Plante", 1);
		moteur.creerAlea("Mouton", 1);
		

		for (int i=0 ; i<10000; i++){
			moteur.simulation();
			
			try{
				Thread.sleep(2000);

			}catch(Exception e){
				
			}
		fenetre.repaint();
		}
	}
	
	
}
