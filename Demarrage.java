import Affichage.Fenetre;
import Moteur.Moteur;
public class Demarrage {

	public static void main(String[] args) throws Exception {
		
		Moteur moteur=new Moteur(40,40,7);
		moteur.vistesseSimulation=1;
		moteur.creerAlea("Plante", 100);
		moteur.creerAlea("MoutonNoir",40);
		moteur.creerAlea("Mouton", 30);
		moteur.creerAlea("Loup",3 );
		moteur.creerAlea("LoupNoir",3 );
		/*
		moteur.creerAlea("MoutonNoir", 10);
		moteur.creerAlea("Mouton", 10);
		moteur.creerAlea("LoupNoir", 10);
		moteur.creerAlea("Loup",10 );
		*/
		Fenetre fenetre=new Fenetre(moteur.leTerrain);
		moteur.laFenetre=fenetre;
		int a=0;
		for (int i=0 ; i<1000000; i++){
			moteur.simulation();
			System.out.println(a);
			a++;
			try{
				Thread.sleep(moteur.vistesseSimulation);
			}catch(Exception e){
			}
		fenetre.repaint();
		}
	}
}
