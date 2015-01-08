package Moteur;

import java.awt.Point;

import Affichage.Fenetre;
import Moteur.Terrain.Case;

public interface FonctionsDeBaseVivant {

	
	/**
	 * applique une action , et renvoi la valeur de toujoursEnVie()
	 * 
	 * @param map
	 * @param vistesseSimulation
	 * @param laFenetre
	 * @return renvoi null si pas d'accouplement a ce tour , sinon le nouveau bebe;
	 * @throws Exception 
	 */
	public Etre action(Case [][] map, int vistesseSimulation,Fenetre laFenetre) throws Exception; 
	
	/**
	 * Permet de tester si l'etre est en vie ou a été tué juste avant
	 * il sera supprimer de la liste des etres principal au prochain tour
	 * @param map
	 * @return renvoi True si l'etre MEURT
	 * @throws Exception
	 */
	public boolean isMort(Case[][] map) throws Exception;

	/**
	 * Permet de creer le bebe ( animal ou plante ) d'une reproduction
	 * 
	 * @param a l'etre qui avec this donne naissance au bebe ( plante ou animal )
	 * @param positionBebe position du bebe sur la map
	 * @return le bebe creer
	 */
	public Etre bebe(Etre a,Point positionBebe);
	// renvoi le nouveau ne d'un acouplement 
}
