package Moteur;

import Moteur.Terrain.Case;

public interface FonctionsDeBaseVivant {
	public boolean toujourEnVie(); // renvoi false si l'etreVivant est mort a ce tour, true si l'etre est toujour en vie
	public void manger();// actualise les variable de l'etre
	public Animal bebe(Animal b);// renvoi le nouveau ne d'un acouplement
	public boolean action(Case [][] map); // applique une action , et renvoi la valeur de toujoursEnVie()
	//public void actualiserChoix();
	
	
}
