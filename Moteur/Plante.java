package Moteur;
import java.awt.Point;

import Moteur.Terrain.Case;

public class Plante extends EtreVivant  implements FonctionsDeBasePlante{

	private int valeur;
	
	public Plante(int x, int y, boolean femelle, int esperenceDeVie,
			int nbToursPourDevenirPuber, int maxReproduction,
			int esperenceSansManger, int valeur) {
		super(x, y, femelle, esperenceDeVie, nbToursPourDevenirPuber, maxReproduction,
				esperenceSansManger, 0);
		
		//champ de vision a 0 pour les plantes
		this.setValeur(valeur);
	}
	
	public void manger(int valeurEnSel){
		//VARIABLE
		this.valeur=this.valeur+valeurEnSel;
	}
	public int getValeur() {
		return valeur;
	}
	public void setValeur(int valeur) {
		if (valeur<0){
			this.valeur=0;
		}
		this.valeur = valeur;
	}
	
	public void decrementerValeur(){
		this.setValeur((this.getValeur())-1);
	}

	@Override
	public Etre action(Case[][] map) throws Exception {
		
		if(this.isMort(map)){return null;}
		//la plante est deja morte , aucune action a effectue
		
		// la plante veilli d'une annee
		incrementeToursEnVie();
		if(!this.isPuber()){
			//durant leur enfance les plantes gagnent 1 de valeur par tour
			//VARIABLE
			this.valeur++;
		}
		
		// la plante mange le sel present sur la case et augmente sa propre Valeur
		manger(map[this.positionX][this.positionY].getValeurSel());
		
		return null;
	}

	@Override
	public boolean isMort(Case [][] map) throws Exception {
		if (valeur<1 || !toujourEnVie()){
			
			if(map[positionX][positionY].getPlante()==this){
				map[positionX][positionY].setPlante(null);
			}
			
			return true;// Mort
		}
		return false;// en vie
	}

	public void setMaxValeur() {
		this.valeur=Moteur.getValeurParDefautPlante();
	}

	@Override
	public Etre bebe(Point positionBebe) {
		
		return null;
	}
}
