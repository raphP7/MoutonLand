package Moteur;

import java.awt.Point;

import Moteur.Intelligence.Envie;
import Moteur.Terrain.Case;

public interface FonctionsDeBaseAnimal {
	
	public void manger(Etre etreManger, Envie envieTemporaire,Case [][] map)throws Exception;
	// actualise les variable de l'etre
}
