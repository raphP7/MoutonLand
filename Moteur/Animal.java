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
	private FileDeSouvenirs mouvements;
	Case[][] tableauVision;
	
	private Envie [] lesEnvies;// voir enum Emotion

	public Animal(Etre a, Etre b,Point position) {
		super(a,b, position);
	}
	
	public Animal(int x, int y, boolean femelle, int esperenceDeVie,
			int nbToursPourDevenirPuber, int maxReproduction,
			int esperenceSansManger, int champDeVision, int force, int vitesse) {
		
		super(x, y, femelle, esperenceDeVie, nbToursPourDevenirPuber,
				maxReproduction, esperenceSansManger, champDeVision);
		
		this.force=force;
		this.vitesse=vitesse;
		this.toursSansManger=0;
		this.immobile=0;
		this.nombreDeReproduction=0;		
		
		//initialisation du tableau lesEnvies avec des objects Envie qui sont listé par la Enum Emotion
		
		this.lesEnvies=new Envie[Emotion.values().length];
		int i=0;
		for (Emotion a : Emotion.values()){
			
			this.lesEnvies[i]=new Envie(a,0);
			
			if(a.equals(Emotion.DEPLACEMENT)){//POUR TESTS
				System.out.println("deplacement =100");
				this.lesEnvies[i].setValeur(100);
				
			}
			i++;
		}
		//initialisation de la file de souvenir avec la position actuel
		this.mouvements=(new FileDeSouvenirs(10, x , y , tableauVision));
		
		this.setaEteTuer(false);
		
	}
	
	public Envie[] getLesEnvies() {
		return lesEnvies;
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
			return false;
		}
		return false;
		
	}
	
	public Etre action(Case [][] map) throws Exception{
		
		this.actualiserVariables();
		
		this.tableauVision=new VisionEtDeplacement().miseAjourVision(new Point(this.positionX,this.positionY),this.getChampDeVision(),map);
		
		this.lesEnvies=new VisionEtDeplacement().regarder(tableauVision,this.getChampDeVision());
		
		Emotion emotionTemporaire =new Envie().envieLaPlusForte(lesEnvies);
		
		System.out.println(emotionTemporaire);
		VisionEtDeplacement choixAction = new VisionEtDeplacement(emotionTemporaire);
		
		LinkedList<Point> chemin =choixAction.deplacement(this.positionX,this.positionY,map);
		
		if(chemin.size()==0){
			throw new Exception("aucune case d'arriver n'a ete choisi par l'animal");
		}
		
		// l'animal est retirer de sa case actuel
		map[this.positionX][this.positionY].setAnimalPresent(null);
		
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
			
		case DEPLACEMENT:
			map[arriver.x][arriver.y].setAnimalPresent(this);

			return null;
			
		case REPRODUCTION:
			
			//trouver les partenaires
			//trouver les cases Vides autour de la femelle ou peuvent apparaitre le bebe
			//definir si this ou le partenaire de this est la femelle
			//si un bebe peut etre creer mettre a jour variable des parents
			//placer le bebe sur la map
			//return le bebe pour le rajouter a la liste des etres dans le moteur
			
			Case[][] tmp=new VisionEtDeplacement().miseAjourVision(new Point(this.positionX,this.positionY),1,map);
			
			List<Etre> partenairesPossible = this.partenairePossible(tmp);
			
			if (partenairesPossible.isEmpty()){
				//aucun partenaire donc pas de bebe
				return null;
			}
			
			Collections.shuffle(partenairesPossible);
			
			List<Point> caseVideAutourFemelle=new ArrayList<Point>();
			
			Etre partenaireChoisi=null;
			
			for(int i=0 ; i<partenairesPossible.size() ; i++){
				
				Etre PartenaireATester=partenairesPossible.get(i);
				
				if (PartenaireATester!=null){
		
					if(this.isFemelle() && partenaireChoisi==null){
						
						caseVideAutourFemelle= casesVidesAutourFemelle(this,tmp);
						
						if ( ! caseVideAutourFemelle.isEmpty()){
							partenaireChoisi=PartenaireATester;
						}
						break;
					}
					else if (partenaireChoisi==null){
						
						caseVideAutourFemelle= casesVidesAutourFemelle(PartenaireATester,tmp);
						
						if ( ! caseVideAutourFemelle.isEmpty()){
							partenaireChoisi=partenairesPossible.get(i);
							break;
						}
					}
				
				}
			}
			
			if(partenaireChoisi!=null){
				Collections.shuffle(caseVideAutourFemelle);
				
				Point coordonnesBebe=new Point(caseVideAutourFemelle.get(0).x,caseVideAutourFemelle.get(0).y);
				
				Etre bebe=this.bebe(partenaireChoisi,coordonnesBebe);
				
				map[bebe.positionX][bebe.positionY].setAnimalPresent((Animal)bebe);
				
				((EtreVivant)partenaireChoisi).nombreDeReproduction++;
				((EtreVivant)this).nombreDeReproduction++;
				
				return bebe;
			}
		}//fermeture du switch
		
		return null;
		
	}

	public void visionAutourDeThisIsGoodSize(Case[][] visionAutourDeThis,int champDeVision) throws Exception{
		
		int tailleVision=new VisionEtDeplacement().calculTailleVision(champDeVision);
		
		if(visionAutourDeThis.length!=tailleVision && visionAutourDeThis[0].length!=tailleVision){
			throw new Exception("\nla fonction visionAutourDeThisIsGoodSize a ete appeler \n"
					+ "avec un tableau de "+tailleVision+" case de coté \npour un tableau de "+visionAutourDeThis.length +" case de coté\n");
		}
	}
	
	private List<Etre> partenairePossible(Case[][] visionAutourDeThis) throws Exception{
		//renvoi la liste des partenairePossible dans le champ de reproduction de this
		//plus precisement dans les 8cases autour de this
		
		//Securite
		visionAutourDeThisIsGoodSize(visionAutourDeThis,3);
		
		List<Etre> partenairesPossible = new ArrayList<Etre>();
		
		
		for(int i=0 ; i<visionAutourDeThis.length;i++){
			for(int j=0 ; i<visionAutourDeThis[0].length;j++){
				if (visionAutourDeThis[i][j].getAnimalPresent()==this){
					
				}
				else if (visionAutourDeThis[i][j].getAnimalPresent() !=null){
					if (this.reproductionPossible(visionAutourDeThis[i][j].getAnimalPresent())){
						partenairesPossible.add(visionAutourDeThis[i][j].getAnimalPresent());
						
					}
				}
			}
		}
		
		return partenairesPossible;
	}
	
	private List<Point> casesVidesAutourFemelle(Etre femelle,Case[][] visionAutourDeFemelle) throws Exception{
		
		//SECURITER
		visionAutourDeThisIsGoodSize(visionAutourDeFemelle,1);
		
		if(	!((EtreVivant)femelle).isFemelle() ){
			throw new Exception("Erreur la fonction casesVidesAutourFemelle a ete appeler avec un Male");
		}
		
		List<Point> listeCaseVidePourBebe = new ArrayList<Point>();
		
		for(int i=0 ; i<visionAutourDeFemelle.length;i++){
			for(int j=0 ; i<visionAutourDeFemelle[0].length;j++){
				
				Case tmp=visionAutourDeFemelle[i][j];
				
				if(tmp.isObstacle() && tmp.getAnimalPresent()==null && !(tmp.getAnimalPresent()==this) ){
					listeCaseVidePourBebe.add(new Point(i,j));// IL FAUT RECUPERER LES VRAI VALEUR DE LA MAP PAR RAPPORT A 
				}
		
		
			}
	
		}
		return listeCaseVidePourBebe;
	}

	public FileDeSouvenirs getMouvements() {
		return mouvements;
	}
}
