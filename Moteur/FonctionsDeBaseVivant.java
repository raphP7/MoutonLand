package Moteur;

import java.awt.Point;

import Moteur.Intelligence.Envie;
import Moteur.Terrain.Case;

public interface FonctionsDeBaseVivant {
	
	public void manger(Etre etreManger, Envie envieTemporaire);// actualise les variable de l'etre
	
	public Etre action(Case [][] map) throws Exception; // applique une action , et renvoi la valeur de toujoursEnVie()
	//renvoi null si pas d'accouplement a ce tour , sinon le nouveau bebe;
	
	public boolean isMort();
	// renvoi True si l'etre MEURT
	
	public Etre bebe(Etre a,Point positionBebe);
	// renvoi le nouveau ne d'un acouplement 
}
