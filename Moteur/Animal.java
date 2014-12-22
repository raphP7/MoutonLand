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
	private Envie [] lesEnvies;// voir l'enumeration Emotion
	public Case[][] tableauVision;
	
	public String toStringLesEnvies(){
		String retour="";
		for(int i=0 ; i<lesEnvies.length ; i++){
			retour=retour+lesEnvies[i].toString();
			retour=retour+"\n";
		}
		return retour;
	}
	
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
	
	public void manger(Etre etreManger, Envie envieTemporaire){
		if(this instanceof Herbivore && etreManger instanceof Plante){
			((Plante)etreManger).decrementerValeur();
			
			//VARIABLE !!
			
			envieTemporaire.reduireValeur(5);
			this.toursSansManger=0;
		}
		else if(this instanceof Carnivore && etreManger instanceof Animal){
			((EtreVivant) etreManger).setaEteTuer(true);
			envieTemporaire.reduireValeur(100);
			this.toursSansManger=0;
		}
		
	}
	
	public Etre action(Case [][] map) throws Exception{
		
		if(this.isaEteTuer()){return null;}
		
		this.actualiserVariables();
		
		//System.out.println(toStringLesEnvies());
		
		//mettre a jour la vision de l'animal
		this.tableauVision=new VisionEtDeplacement().miseAjourVision(new Point(this.positionX,this.positionY),this.getChampDeVision(),map);
		
		//mettre a jour les emotions en fonction de la vision
		this.lesEnvies=new VisionEtDeplacement().regarder(tableauVision,this.getChampDeVision());
		
		//Recupere l'envie la plus forte
		Envie envieTemporaire =new Envie().envieLaPlusForte(lesEnvies);

		//recupere la valeur de cette envie
		Emotion emotionTemporaire=envieTemporaire.getEmotion();
		
		//Instancie une intelligence avec cette emotion
		VisionEtDeplacement choixAction = new VisionEtDeplacement(emotionTemporaire);
		
		//recupere la liste du chemin parcouru par l'Animal
		LinkedList<Point> chemin =choixAction.deplacement(this.positionX,this.positionY,map);
		
		if(chemin.size()==0){
			throw new Exception("aucune case d'arriver n'a ete choisi par l'animal");
		}
		
		// l'animal est retirer de sa case actuel
		map[this.positionX][this.positionY].setAnimalPresent(null);
		
		Point converteur=new Point (this.positionX-this.getChampDeVision(),this.positionY-this.getChampDeVision());
		
		
		//appliquer les deplacement intermediaire
		for(int i =0; i<chemin.size()-1; i++){
			
			Point tmp=chemin.get(i);
			map[(tmp.x+converteur.x)][(tmp.y+converteur.y)].setAnimalPresent(this);
			
		}
		
		//appliquer le dernier deplacement
		Point arriver =chemin.getLast();
		
		//System.out.println("arriver X : "+(arriver.x+converteur.x));
		//System.out.println("arriver Y : "+(arriver.y+converteur.y));
		
		//recupere l'Animal hypotethiquement present		
		Etre animalPresent=map[arriver.x+converteur.x][arriver.y+converteur.y].getAnimalPresent();
		
		//recupere la plante hypotethiquement present
		Etre plantePresente=map[arriver.x+converteur.x][arriver.y+converteur.y].getPlante();
		//applique une action en fonction de l'emotion
		switch (choixAction.emotionChoisiPourLeDeplacement){
		
		case FAIM:
			if (animalPresent != null && this!=animalPresent){
				// il y a un autre annimal sur la case d'arriver
				
				if (this instanceof Carnivore && animalPresent instanceof Herbivore ){
					
					EtreVivant seFaitManger=map[(arriver.x+converteur.x)][(arriver.y+converteur.y)].getAnimalPresent();
					
					this.manger(seFaitManger,envieTemporaire);
					
					map[(arriver.x+converteur.x)][(arriver.y+converteur.y)].setAnimalPresent(this);

					this.positionX=arriver.x+converteur.x;
					this.positionY=arriver.y+converteur.y;
					return null;					
				}
				else if( this instanceof Carnivore && animalPresent instanceof Carnivore){
					
					//deux carnivor d'instance differente qui s'affronte
					EtreVivant seFaitAttaquer=map[(arriver.x+converteur.x)][(arriver.y+converteur.y)].getAnimalPresent();
					
					if(this.force>=((Animal)animalPresent).force){
						//this a gagner
						this.manger(seFaitAttaquer,envieTemporaire);
						map[(arriver.x+converteur.x)][(arriver.y+converteur.y)].setAnimalPresent(this);

						this.positionX=arriver.x+converteur.x;
						this.positionY=arriver.y+converteur.y;
						return null;
					}
					else{
						//seFaitAttaquer a gagner
						seFaitAttaquer.manger(this,envieTemporaire);
						return null;
					}
					
				}
				else if (this instanceof Herbivore){
					throw new Exception("un Herbivore ne peut aller sur la case d'un autre Animal");
				}
				else if (this instanceof Carnivore && this.getClass().equals(animalPresent.getClass())){
					throw new Exception("un Carnivore ne peut aller sur la case d'un autre Carnivore");
				}
				
			}
			else if(animalPresent ==null){
				if (this instanceof Herbivore &&  plantePresente!=null){
					this.manger(plantePresente,envieTemporaire);
				}
				map[(arriver.x+converteur.x)][(arriver.y+converteur.y)].setAnimalPresent(this);

				this.positionX=arriver.x+converteur.x;
				this.positionY=arriver.y+converteur.y;
				return null;
				
			}
		case PEUR:
			map[arriver.x+converteur.x][arriver.y+converteur.y].setAnimalPresent(this);
			

			this.positionX=arriver.x+converteur.x;
			this.positionY=arriver.y+converteur.y;
			
			return null;
			
		case DEPLACEMENT:
			map[arriver.x+converteur.x][arriver.y+converteur.y].setAnimalPresent(this);
			

			this.positionX=arriver.x+converteur.x;
			this.positionY=arriver.y+converteur.y;
			
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

				this.positionX=arriver.x+converteur.x;
				this.positionY=arriver.y+converteur.y;
				
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
				

				this.positionX=arriver.x+converteur.x;
				this.positionY=arriver.y+converteur.y;
				
				return bebe;
			}
		}//fermeture du switch
		
		
		this.positionX=arriver.x+converteur.x;
		this.positionY=arriver.y+converteur.y;
		
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
