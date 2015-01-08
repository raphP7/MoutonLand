package Moteur;

import java.awt.Point;

import Moteur.Intelligence.Envie;
import Moteur.Terrain.Case;

public interface FonctionsDeBaseAnimal {
	/**
	 * Permet a un etre de manger un autre etre (animal ou plante selon Herbivore ou Carnivore
	 * 
	 * @param etreManger
	 * @param envieTemporaire
	 * @param map
	 * @throws Exception
	 */
	
	public void manger(Etre etreManger, Envie envieTemporaire,Case [][] map)throws Exception;

}
