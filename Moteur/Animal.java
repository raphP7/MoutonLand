package Moteur;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.Stack;

import Affichage.Fenetre;
import Moteur.Intelligence.Emotion;
import Moteur.Intelligence.Emplacement;
import Moteur.Intelligence.Envie;
import Moteur.Intelligence.VisionEtDeplacement;
import Moteur.Terrain.Case;
import Moteur.Terrain.Terrain;

public abstract class Animal extends EtreVivant  implements FonctionsDeBaseAnimal {
	protected int immobile;
	int force;
	int vitesse;
	private LinkedList<Emplacement> souvenirs;
	private Envie [] lesEnvies;// voir l'enumeration Emotion
	public Case[][] tableauVision;
	
	/**
	 * 
	 * @return Un string du tableau des envies stockant les Emotions et leur valeur respective en % d'importance
	 */
	public String toStringLesEnvies(){
		String retour="";
		for(int i=0 ; i<lesEnvies.length ; i++){
			retour=retour+lesEnvies[i].toString();
			retour=retour+"\n";
		}
		return retour;
	}
	
	/**
	 * 
	 * @param a le premier  parent
	 * @param b Le deuxieme parent
	 * @param position La position du bebe
	 */
	public Animal(Etre a, Etre b,Point position) {
		super(a,b, position);

		if( ((Animal)a).force >  ((Animal)b).force){
			
			this.force=((Animal)a).force;
		}
		else{
			this.force=((Animal)b).force;
		}	
		if( ((Animal)a).vitesse >  ((Animal)b).vitesse){
			
			this.vitesse=((Animal)a).vitesse;
		}
		else{
			this.vitesse=((Animal)b).vitesse;
		}	
		
		initialiserRaviablesAnimal(position.x,position.y);
		
	}
	
	/**
	 * 
	 * @param x Position X de l'animal
	 * @param y Position Y de l'animal
	 */
	public void initialiserRaviablesAnimal(int x , int y){
		this.toursSansManger=0;
		this.immobile=0;
		//initialisation du tableau lesEnvies avec des objects Envie qui sont listé par la Enum Emotion
		
		this.lesEnvies=new Envie[Emotion.values().length];	
		int i=0;
		for (Emotion a : Emotion.values()){
			
			this.lesEnvies[i]=new Envie(a,0);
			
			if(a.equals(Emotion.REPRODUCTION)){//POUR TESTS
				//this.lesEnvies[i].setValeur(100);
			}
			
			i++;
		}
		//initialisation de la file de souvenir avec la position actuel
		this.souvenirs=new LinkedList<Emplacement>();
		//this.mouvements.addFirst(new Emplacement( x , y , tableauVision));		
		this.setaEteTuer(false);
	}
	public Animal(int x, int y, boolean femelle, int esperenceDeVie,
			int nbToursPourDevenirPuber, int maxReproduction,
			int esperenceSansManger, int champDeVision, int force, int vitesse) {
		
		super(x, y, femelle, esperenceDeVie, nbToursPourDevenirPuber,
				maxReproduction, esperenceSansManger, champDeVision);
		
		this.force=force;
		this.vitesse=vitesse;
		initialiserRaviablesAnimal(x,y);
		
	}
	
	public Envie[] getLesEnvies() {
		return lesEnvies;
	}
	
	public void actualiserVariables(){
		this.incrementeToursEnVie();

		for (int i=0; i<this.lesEnvies.length ; i++){
			
			
			
				if (this.lesEnvies[i].getEmotion().equals(Emotion.PEUR)){
					// l'animal se calme 
					this.lesEnvies[i].setValeur(lesEnvies[i].getValeur() -5);
				}
				else if (this.lesEnvies[i].getEmotion().equals(Emotion.FAIM)){
					// l'animal prend faim
					
					if(this instanceof Carnivore){
						this.lesEnvies[i].setValeur(lesEnvies[i].getValeur() +1);
					}
					else{
						this.lesEnvies[i].setValeur(lesEnvies[i].getValeur() +3);
					}
					
				}
				else if (lesEnvies[i].getEmotion().equals(Emotion.DEPLACEMENT)){
					
				}
				else if (lesEnvies[i].getEmotion().equals(Emotion.REPRODUCTION)){
					if(this instanceof Carnivore){
						this.lesEnvies[i].setValeur(lesEnvies[i].getValeur() +1);
					}
					else{
						Random rando =new  Random();
						int a =rando.nextInt(3);
						if(a==0){
							this.lesEnvies[i].setValeur(lesEnvies[i].getValeur() +2);	
						}
						else{
							this.lesEnvies[i].setValeur(lesEnvies[i].getValeur() +1);
						}
						
					}
					
				}
				
		}
	}
	
	public boolean isMort(Case[][] map) throws Exception{
		
		if (this.toujourEnVie()){
			return false;
		}
		else{
			if(map[positionX][positionY].getAnimalPresent()==this){
				map[positionX][positionY].setAnimalPresent(null);
			}
			return true;
		}
		
	}
	
	
	public void manger(Etre etreManger, Envie envieTemporaire,Case [][] map) throws Exception{
		
		if(this instanceof Herbivore && etreManger instanceof Plante){
			
			if(((Plante)etreManger).isMort(map)){
				map[etreManger.positionX][etreManger.positionY].setPlante(null);
				((EtreVivant)etreManger).setaEteTuer(true);
			}
			else{
				((Plante)etreManger).decrementerValeur();
			}
			
			//VARIABLE !!
			
			envieTemporaire.reduireValeur(10);
			this.toursSansManger=0;
		}
		else if(this instanceof Carnivore && etreManger instanceof Animal){
			
			((EtreVivant) etreManger).setaEteTuer(true);
			envieTemporaire.reduireValeur(100);
			this.toursSansManger=0;
		}
		
	}
	
	public void NetoyerListSouvenir(){
		LinkedList<Emplacement> tmp =new LinkedList<Emplacement>();
		int nb=10;
		for(Emplacement a :this.souvenirs){
			if(nb>0){
				tmp.add(a);
				nb--;
			}
			else{
				break;
			}
		}
		this.souvenirs=tmp;
	}
	public Etre action(Case [][] map,int vistesseSimulation, Fenetre laFenetre) throws Exception{
		
		int nbtour=((EtreVivant)this).getToursEnVie();
		if(nbtour%20==0){
			this.NetoyerListSouvenir();
		}
		
		if(this.isMort(map)){
			this.souvenirs.clear();
			return null;}
		
		//System.out.println("ACTUALISER VARIABLES");
		
		this.actualiserVariables();
		//mettre a jour la vision de l'animal
		this.tableauVision=VisionEtDeplacement.miseAjourVision(new Point(this.positionX,this.positionY),this.getChampDeVision(),map);
		
		//enregistre la vu dans la memoire de l'animal
		this.souvenirs.addFirst(new Emplacement( this.positionX , this.positionY , tableauVision));
		
		//mettre a jour les emotions en fonction de la vision
		this.lesEnvies=VisionEtDeplacement.regarder(tableauVision,this.getChampDeVision());
			
		//Recupere l'envie la plus forte
		Envie envieTemporaire =Envie.envieLaPlusForte(lesEnvies);
		
		//System.out.println("envie du deplacement "+envieTemporaire);
		
		//recupere la liste du chemin parcouru par l'Animal
		Stack<Point> chemin =VisionEtDeplacement.deplacement(this.positionX,this.positionY,map,envieTemporaire.getEmotion());
		
		if(chemin.size()==0){
				Terrain a =new Terrain(10,10,10);
				a.map=tableauVision;
				a.afficheShell();
				throw new Exception("aucune case d'arriver n'a ete choisi par l'animal");	
			}
		
		//pour mettre a l'echelle du terrain les points de deplacement choisi par l'animal a l'echelle de sa vue du terrain
		Point converteur=new Point (this.positionX-this.getChampDeVision(),this.positionY-this.getChampDeVision());
		
		//System.out.println("position actuelle "+this.positionX +" "+this.positionY);
		
		//System.out.println("taille pile recuperer "+chemin.size());
		
		int taillePile=chemin.size();
		
		//appliquer les deplacement intermediaire
		
		for(int i =0; i<taillePile-1; i++){

			//System.out.println("position actuelle "+this.positionX +" "+this.positionY);
			Point tmp=chemin.pop();
			
			map[this.positionX][this.positionY].setAnimalPresent(null);
			
			laFenetre.miseAjoursCase(this.positionX, this.positionY);
			//
			
			this.positionX=tmp.x+converteur.x;
			this.positionY=tmp.y+converteur.y;
			
			//
			
			map[this.positionX][this.positionY].setAnimalPresent(this);
			//Moteur.laFenetre.repaint();
			laFenetre.miseAjoursCase(this.positionX, this.positionY);
			try{
				Thread.sleep(vistesseSimulation);
			}catch(Exception e){
			}
			
			
		}
		//appliquer le dernier deplacement
		Point arriver =chemin.pop();		
		map[this.positionX][this.positionY].setAnimalPresent(null);
		laFenetre.miseAjoursCase(this.positionX, this.positionY);
		
		//this.positionX=arriver.x+converteur.x;
		//this.positionY=arriver.x+converteur.y;
		
		//recupere l'Animal hypotethiquement present	
		Etre animalPresent=map[arriver.x+converteur.x][arriver.y+converteur.y].getAnimalPresent();
		
		//recupere la plante hypotethiquement present
		Etre plantePresente=map[arriver.x+converteur.x][arriver.y+converteur.y].getPlante();
		
		//applique une action en fonction de l'emotion
		
		switch (envieTemporaire.getEmotion()){
		
			case FAIM:
				
				actionFaim(animalPresent,map,converteur,envieTemporaire,arriver,plantePresente,laFenetre);
				return null;
			case PEUR:
				
				appliquerDeplacementFinal(arriver,converteur,map,laFenetre);
				
				return null;
				
			case DEPLACEMENT:
				
				appliquerDeplacementFinal(arriver,converteur,map,laFenetre);
				
				return null;
				
			case REPRODUCTION:
				appliquerDeplacementFinal(arriver,converteur,map,laFenetre);
				
				return actionReproduction(map,laFenetre);
				
		}//fermeture du switch
		
		appliquerDeplacementFinal(arriver,converteur,map,laFenetre);
		
		return null;
		
	}

	public void actionFaim(Etre animalPresent,Case [][] map ,Point converteur,Envie envieTemporaire,Point arriver ,Etre plantePresente,Fenetre laFenetre) throws Exception{
		
		if (animalPresent != null && this!=animalPresent){
			// il y a un autre annimal sur la case d'arriver
			
			if (this instanceof Carnivore && animalPresent instanceof Herbivore ){
				
				//System.out.println(animalPresent);
				
				this.manger(animalPresent,envieTemporaire,map);
				
				appliquerDeplacementFinal(arriver,converteur,map,laFenetre);
				
				return;					
			}
			else if( this instanceof Carnivore && animalPresent instanceof Carnivore){
				
				//deux carnivor d'instance differente qui s'affronte
				EtreVivant seFaitAttaquer=map[(arriver.x+converteur.x)][(arriver.y+converteur.y)].getAnimalPresent();
				
				if(this.force>=((Animal)animalPresent).force){
					//this a gagner
					this.manger(seFaitAttaquer,envieTemporaire,map);
					appliquerDeplacementFinal(arriver,converteur,map,laFenetre);


					return ;
				}
				else{
					//seFaitAttaquer a gagner
					((Animal) seFaitAttaquer).manger(this,envieTemporaire,map);
					return ;
				}
				
			}
			else if (this instanceof Herbivore){
				
				throw new Exception("un Herbivore ne peut aller sur la case d'un autre Animal");
			}
			else if (this instanceof Carnivore && this.getClass().equals(animalPresent.getClass())){
				throw new Exception("un Carnivore ne peut aller sur la case d'un autre Carnivore");
			}
			
		}
		else if(animalPresent ==null || this==animalPresent ){
			
			if (this instanceof Herbivore &&  plantePresente!=null){
				this.manger(plantePresente,envieTemporaire,map);
			}
			appliquerDeplacementFinal(arriver,converteur,map,laFenetre);
			return ;
			
		}
		else{
			throw new Exception("PROBLEM ANNIMAL AVEC FAIM");
		}
		return ;
		
	}
	
	/**
	 * Applique le deplacement final de l'animal et met a jour l'affichage de sa case d'arriver dans la fenetre Jframe
	 * 
	 * @param arriver le point d'arriver de l'animal
	 * @param converteur le point converteur entre la vue de l'animal et le terrain
	 * @param map object stockant le terrain
	 * @param laFenetre la fenetre Jframe pour mettre a jour precisement l'affichage
	 * @throws Exception
	 */
	public void appliquerDeplacementFinal(Point arriver , Point converteur , Case[][] map, Fenetre laFenetre) throws Exception{
		this.positionX=arriver.x+converteur.x;
		this.positionY=arriver.y+converteur.y;
		map[this.positionX][this.positionY].setAnimalPresent(this);
		laFenetre.miseAjoursCase(this.positionX, this.positionY);
		
	}
	public void visionAutourDeThisIsGoodSize(Case[][] visionAutourDeThis,int champDeVision) throws Exception{
		
		int tailleVision=1+champDeVision*2;
		if(visionAutourDeThis.length!=tailleVision && visionAutourDeThis[0].length!=tailleVision){
			throw new Exception("\nla fonction visionAutourDeThisIsGoodSize a ete appeler \n"
					+ "avec un tableau de "+tailleVision+" case de coté \npour un tableau de "+visionAutourDeThis.length +" case de coté\n");
		}
	}

	public LinkedList<Emplacement> getSouvenirs() {
		return souvenirs;
	}

	public void setSouvenirs(LinkedList<Emplacement> souvenirs) {
		this.souvenirs = souvenirs;
	}
}