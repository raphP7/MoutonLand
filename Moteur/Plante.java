package Moteur;
import java.awt.Point;
import java.util.Random;

import Affichage.Fenetre;
import Moteur.Terrain.Case;

public class Plante extends EtreVivant  implements FonctionsDeBasePlante{

	private int valeur;
	private int valmax;
	
	public Plante(int x, int y, boolean femelle, int esperenceDeVie,
			int nbToursPourDevenirPuber, int maxReproduction,
			int esperenceSansManger, int valeur) {
		super(x, y, femelle, esperenceDeVie, nbToursPourDevenirPuber, maxReproduction,
				esperenceSansManger, 0);
		
		//champ de vision a 0 pour les plantes
		this.setValeur(valeur);
		this.setValmax(valeur);
	}
	
	public Plante(Etre a, Etre b, Point position){
		super(a,b,position);
		
		if(((Plante)a).getValmax()>=((Plante)b).getValmax()){
			
			this.valmax=((Plante)a).getValmax();
		}
		else{
			this.valmax=((Plante)b).getValmax();
		}
		this.valeur=this.valmax;
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
	public Etre action(Case[][] map,int vistesseSimulation,Fenetre laFenetre) throws Exception {
		
		if(this.isMort(map)){
			//la plante est deja morte , aucune action a effectue
			laFenetre.miseAjoursCase(this.positionX, this.positionY);
			return null;}
		
		// la plante veilli d'une annee
		incrementeToursEnVie();
		if(!this.isPuber()){
			//durant leur enfance les plantes gagnent 1 de valeur par tour
			//VARIABLE
			this.valeur++;
		}
		// la plante mange le sel present sur la case et augmente sa propre Valeur
		manger(map[this.positionX][this.positionY].getValeurSel());
		
		return this.actionReproduction(map, laFenetre);
		
		
		
	}
	
	public boolean isMort(Case [][] map) throws Exception {
		if (valeur<1 || !toujourEnVie()){
			
			if(map[positionX][positionY].getPlante()==this){
				map[positionX][positionY].setPlante(null);
			}			
			return true;// Mort
		}
		return false;// en vie
	}

	public int getValmax() {
		return valmax;
	}

	public void setValmax(int valmax) {
		this.valmax = valmax;
	}
}
