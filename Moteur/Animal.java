package Moteur;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import Moteur.Intelligence.Emotion;
import Moteur.Intelligence.Envie;
import Moteur.Intelligence.FileDeSouvenirs;
import Moteur.Intelligence.VisionEtDeplacement;
import Moteur.Terrain.Case;

public abstract class Animal extends EtreVivant  {
	
	int force;
	int vitesse;
	FileDeSouvenirs mouvements;
	Case[][] visionActuel;
	
	private Envie [] lesEnvies;// voir enum Emotion
	
	
	public Envie[] getLesEnvies() {
		return lesEnvies;
	}
	
	public Animal(int x, int y, boolean femelle, int esperenceDeVie,
			int nbToursPourDevenirPuber, int maxReproduction,
			int esperenceSansManger, int champDeVision, int force, int vitesse) {
		
		super(x, y, femelle, esperenceDeVie, nbToursPourDevenirPuber,
				maxReproduction, esperenceSansManger, champDeVision);
		
		this.force=force;
		this.vitesse=vitesse;
		//this.deplacements = new <Point>();
		//int nombreCases = calculTailleVision();
		//this.visionActuel= new Case[nombreCases][nombreCases];
			
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
		
		this.setaEteTuer(false);
		
	}
	public Animal(Etre a, Etre b) {
		super(a,b);
	}

	public void actualiserVariables(){
		this.incrementeToursEnVie();
		
	}
	
	public boolean reproductionPossible(Etre b){
		if (((EtreVivant) b).isFemelle() != this.isFemelle() ){// les animaux sont de  Sexe Opposer et de meme Race
			if(this.maxReproduction>this.nombreDeReproduction && ((EtreVivant)b).maxReproduction>this.maxReproduction){// les animaux peuvent se reproduire
				return true;
			}
			else return false;
		}
		else return false;
		
	}
	
	public boolean isMort(){
		
		if (!	this.toujourEnVie()){
			System.out.println("ici");
			return false;
		}
		return false;
		
	}
	public Etre action(Case [][] map) throws Exception{
		
		this.actualiserVariables();
		
		this.visionActuel=new VisionEtDeplacement().miseAjourVision(new Point(this.positionX,this.positionY),this.getChampDeVision(),map);
		
		this.lesEnvies=new VisionEtDeplacement().regarder(this.positionX, this.positionY, map);
		
		new Envie().trierEnvies(lesEnvies);
		
		VisionEtDeplacement choixAction = new VisionEtDeplacement(lesEnvies[0].getEmotion());
		
		LinkedList<Point> chemin =choixAction.deplacement(this.positionX,this.positionY,map);
		
		if(chemin.size()==0){
			throw new Exception("aucune case d'arriver n'a ete choisi par l'animal");
		}
		
		//appliquer les deplacement intermediaire
		for(int i =0; i<chemin.size()-1; i++){
			
			Point tmp=chemin.get(i);
			
			map[tmp.x][tmp.y].setAnimalPresent(this);
			
		}
		
		//appliquer le dernier deplacement
		
		Point arriver =chemin.getLast();
		
		Etre animalPresent=map[arriver.x][arriver.y].getAnimalPresent();
		
		switch (choixAction.emotionChoisiPourLeDeplacement){
		
		case FAIM:
			if (animalPresent != null && this!=animalPresent){
				
				if (this instanceof Carnivore && animalPresent instanceof Herbivore ){
					
					EtreVivant seFaitManger=map[arriver.x][arriver.y].getAnimalPresent();
					
					seFaitManger.setaEteTuer(true);
					this.manger();
					
					map[arriver.x][arriver.y].setAnimalPresent(this);
					return null;					
				}
				else if (this instanceof Herbivore){
					throw new Exception("un Herbivore ne peut aller sur la case d'un autre Animal");
				}
				else if ( this instanceof Carnivore){
					throw new Exception("un Carnivore ne peut aller sur la case d'un autre Carnivore");
				}
			}
			else if(animalPresent !=null){
				
			}
			
			
		case PEUR:
			choixAction.emotionChoisiPourLeDeplacement.setEmotion("DEPLACEMENT");;// A TESTER
			
		case DECPLACEMENT:
			map[arriver.x][arriver.y].setAnimalPresent(this);
			return null;
			
		case REPRODUCTION:
			
			//trouver les partenaires
			//trouver les cases Vides autour de la femelle ou peuvent apparaitre le bebe
			//definir si this ou le partenaire de this est la femelle
			//si un bebe peut etre creer mettre a jour variable des parents
			//placer le bebe sur la map
			//return le bebe pour le rajouter a la liste des etres dans le moteur
			
			List<Etre> partenairesPossible = new ArrayList<Etre>();
			List<Point> caseVideAutourFemelle = new ArrayList<Point>();
			
			Case[][] tmp=new VisionEtDeplacement().miseAjourVision(new Point(this.positionX,this.positionY),1,map);

			
			for(int i=0 ; i<tmp.length;i++){
				for(int j=0 ; i<tmp[0].length;j++){
					if (tmp[i][j].getAnimalPresent()==this){}
					else if (tmp[i][j].getAnimalPresent() !=null){
						if (this.reproductionPossible(tmp[i][j].getAnimalPresent())){
							partenairesPossible.add(tmp[i][j].getAnimalPresent());
							
						}
					}
					else{
						if (this.isFemelle()){
							caseVideAutourFemelle.add(new Point(i,j));// ATTENTION METTRE LES VRAI I J DE la MAP !!
						}
					}
				}
			}
			
			Collections.shuffle(partenairesPossible);
			Collections.shuffle(caseVideAutourFemelle);
			
			for(int i=0 ; i<partenairesPossible.size() ; i++){
				
				if (partenairesPossible.get(i) !=null){

					if(this.isFemelle()){
						
						if(!caseVideAutourFemelle.isEmpty()){
							Etre bebe=this.bebe(partenairesPossible.get(i));
							map[caseVideAutourFemelle.get(0).x][caseVideAutourFemelle.get(0).y].setAnimalPresent((Animal)bebe);
							((EtreVivant)partenairesPossible.get(i)).nombreDeReproduction++;
							((EtreVivant)this).nombreDeReproduction++;
							break;
						}
						
					}
					else{
						
					}
				}
			}
			
			break;
		
			
		}
		
		Etre plantePresent =map[arriver.x][arriver.y].getPlante();
		if (plantePresent != null ){
			if (this instanceof Herbivore){
				
				((Plante) plantePresent).decrementerValeur();
				this.manger();
				
			}
		}
		
		return null;
	}
}
