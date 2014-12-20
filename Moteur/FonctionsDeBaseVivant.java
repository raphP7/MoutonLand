package Moteur;

import Moteur.Terrain.Case;

public interface FonctionsDeBaseVivant {
	public boolean toujourEnVie(); // renvoi false si l'etreVivant est mort a ce tour, true si l'etre est toujour en vie
	public void manger();// actualise les variable de l'etre
	public Etre bebe(Etre etre); // renvoi le nouveau ne d'un acouplement 
	
	
	public Etre action(Case [][] map) throws Exception; // applique une action , et renvoi la valeur de toujoursEnVie()
	//renvoi null si pas d'accouplement a ce tour , sinon le nouveau bebe;
	
	public boolean isMort();
	// renvoi True si l'etre MEURT
	
	
}
