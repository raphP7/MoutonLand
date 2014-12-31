package Moteur;

import Moteur.Terrain.Case;

public interface FonctionsDeBaseVivant {
	
	public Etre action(Case [][] map) throws Exception; // applique une action , et renvoi la valeur de toujoursEnVie()
	//renvoi null si pas d'accouplement a ce tour , sinon le nouveau bebe;
	
	public boolean isMort(Case[][] map) throws Exception;
	// renvoi True si l'etre MEURT
	
}
