package Moteur;

import Moteur.Intelligence.Emotion;
import Moteur.Intelligence.Envie;
import Moteur.Intelligence.FileDeSouvenirs;
import Moteur.Intelligence.Vision;
import Moteur.Terrain.Case;


public abstract class Animal extends EtreVivant  {
	
	int force;
	int vitesse;
	FileDeSouvenirs mouvements;
	Case[][] visionActuel;
	
	private Envie [] lesEnvies;// voir enum Emotion
	
	public Animal(int x, int y, boolean femelle, int esperenceDeVie,
			int nbToursPourDevenirPuber, int maxReproduction,
			int esperenceSansManger, int champDeVision, int force, int vitesse) {
		
		super(x, y, femelle, esperenceDeVie, nbToursPourDevenirPuber,
				maxReproduction, esperenceSansManger, champDeVision);
		
		this.force=force;
		this.vitesse=vitesse;
		//this.deplacements = new <Point>();
		int nombreCases = calculTailleVision();
		this.visionActuel= new Case[nombreCases][nombreCases];
		
		this.toursSansManger=0;
		this.immobile=0;
		this.nombreDeReproduction=0;		
		
		//initialisation du tableau lesEnvies avec des objects Envie qui sont listé par la Enum Emotion
		
		this.lesEnvies=new Envie[Emotion.values().length];
		int i=0;
		for (Emotion a : Emotion.values()){
			this.lesEnvies[i]=new Envie(a,0);
			i++;
		}
		//initialisation de la file de souvenir avec la position actuel
		this.mouvements=new FileDeSouvenirs(10, x , y , visionActuel) {
		};
		
	}
	public int calculTailleVision(){
		return 1+ this.getChampDeVision()*2;
	}
	public void actualiserVariables(){
		this.incrementeToursEnVie();
		
	}

	public boolean reproduction(Animal b){
		if (b.isFemelle() == this.isFemelle()){// les animaux sont de meme sexe
			if(this.maxReproduction>this.nombreDeReproduction && b.maxReproduction>this.maxReproduction){// les animaux peuvent se reproduire
				return true;
			}
			else return false;
		}
		else return false;
		
	}
	
	public boolean action(Case [][] map){
		
		if (! this.toujourEnVie()){
			return false;
		}
		//this.miseAjourVision(map);
		
		actualiserVariables();
		
		//this.manger(map);
		return true;
	}
	
	public void manger(Case[][] map) {
		
		//map[positionX][positionY].plante.getValeur();
		
		toursSansManger=0;
		
	}
	public  Case[][] miseAjourVision(Case [][] map){
		
		visionActuel = new Case[calculTailleVision()][calculTailleVision()]; // redefinition de la taille de la visionActuel
		
		for (int i = 0; i < visionActuel.length; i++) {
			for (int j = 0; j < visionActuel[0].length; j++) {
				visionActuel[i][j]=new Case(); // cases remise par default (sans obstacles et visible)
			}
		}
		int parcour=0;
		while(parcour<2){
			for (int i=positionX-getChampDeVision() ; i<=positionX+getChampDeVision() ;i++){
				for (int j=positionY-getChampDeVision() ; j<=positionY+getChampDeVision() ;j++){
					
					
					//if (cases[i+champDeVision-positionX][j+champDeVision-positionY].getModif()==2){ // la case n'a pas encore été invalidé
					
					if (i<0 || j<0 || i>=map.length || j>=map[0].length){//hors du tableau

							visionActuel[i+getChampDeVision()-positionX][j+getChampDeVision()-positionY].setObstacle(true);
					}
					
					else if(parcour !=0 && map[i][j].isObstacle()){//obstacle de visibilité dans le terrain
						
						visionActuel[i+getChampDeVision()-positionX][j+getChampDeVision()-positionY].setObstacle(true);
							
						visionActuel=new Vision ().ligneObstacle(i+getChampDeVision()-positionX ,j+getChampDeVision()-positionY,visionActuel,this.getChampDeVision());
							
						}
					
						// juste pour tester les cases non visible
					else if (parcour !=0 && !map[i][j].isVisible()){
							visionActuel[i+getChampDeVision()-positionX][j+getChampDeVision()-positionY].setVisible(false);
							visionActuel=new Vision().ligneObstacle(i+getChampDeVision()-positionX ,j+getChampDeVision()-positionY , visionActuel,this.getChampDeVision());
						}
						}
					}
			parcour++;
				}
			
		//champObstacle(cases);
		
		return visionActuel;
		
	}

}
