import Affichage.Fenetre;
import Moteur.Moteur;
public class Demarrage {

	public static void main(String[] args) throws Exception {
		
		Moteur moteur=new Moteur(10,10,0);
		
		Fenetre fenetre=new Fenetre(moteur.leTerrain);
		
		Moteur.laFenetre=fenetre;
		
		moteur.creerAlea("Loup", 3);
		moteur.creerAlea("Plante", 100);
		moteur.creerAlea("Mouton", 20);
		
		int a=0;
		for (int i=0 ; i<10000; i++){
			moteur.simulation();
			a++;
			try{
				Thread.sleep(5000);

			}catch(Exception e){
				
			}
		fenetre.repaint();
		//System.out.println("NB : "+a);
		}
	}
	
	
}
