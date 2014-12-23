package Moteur;

import java.awt.Point;

import Moteur.Intelligence.Envie;

public interface FonctionsDeBaseAnimal {

	public Etre bebe(Etre a,Point positionBebe);
	// renvoi le nouveau ne d'un acouplement 
	
	public void manger(Etre etreManger, Envie envieTemporaire);
	// actualise les variable de l'etre
}
