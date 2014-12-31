package Moteur;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Stack;

import Affichage.Fenetre;
import Moteur.Intelligence.Emotion;
import Moteur.Intelligence.Envie;
import Moteur.Intelligence.FileDeSouvenirs;
import Moteur.Intelligence.VisionEtDeplacement;
import Moteur.Terrain.Case;
import Moteur.Terrain.Terrain;

public abstract class Animal extends EtreVivant  implements FonctionsDeBaseAnimal {
	protected int immobile;
	int force;
	int vitesse;
	private FileDeSouvenirs mouvements;
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
		this.nombreDeReproduction=0;		
		
		//initialisation du tableau lesEnvies avec des objects Envie qui sont listé par la Enum Emotion
		
		this.lesEnvies=new Envie[Emotion.values().length];	
		int i=0;
		for (Emotion a : Emotion.values()){
			
			this.lesEnvies[i]=new Envie(a,0);
			
			if(a.equals(Emotion.REPRODUCTION)){//POUR TESTS
				this.lesEnvies[i].setValeur(100);
				
			}
			
			i++;
		}
		//initialisation de la file de souvenir avec la position actuel
		this.mouvements=(new FileDeSouvenirs(10, x , y , tableauVision));
		
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
					this.lesEnvies[i].setValeur(lesEnvies[i].getValeur() +5);
					
				}
				else if (lesEnvies[i].getEmotion().equals(Emotion.DEPLACEMENT)){
					
				}
		}
	}
	
	public boolean reproductionPossible(Etre b){
		
		if (((EtreVivant) b).isFemelle() != this.isFemelle()  && this.getClass().equals(b.getClass()) ){// les animaux sont de  Sexe Opposer et de meme Race
			System.out.println("return true");
			
			
			//les animaux peuvents encore se reproduire
			if(this.maxReproduction>this.nombreDeReproduction && ((EtreVivant)b).maxReproduction>((EtreVivant)b).nombreDeReproduction){// les animaux peuvent se reproduire
				
				//les animaux sont puberts
				if(this.isPuber() && ((Animal)b).isPuber()){
					return true;
				}
				
				else return false;
			}
			
			else return false;
		}
		
		else return false;
		
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
			
			//VARIABLE !!
			
			envieTemporaire.reduireValeur(5);
			this.toursSansManger=0;
		}
		else if(this instanceof Carnivore && etreManger instanceof Animal){
			
			((EtreVivant) etreManger).setaEteTuer(true);
			map[etreManger.positionX][etreManger.positionY].setAnimalPresent(null);
			envieTemporaire.reduireValeur(100);
			this.toursSansManger=0;
		}
		
	}
	
	public Etre action(Case [][] map) throws Exception{
		
		if(this.isMort(map)){return null;}
		
		System.out.println("ACTUALISER VARIABLES");
		
		this.actualiserVariables();
		//mettre a jour la vision de l'animal
		this.tableauVision=VisionEtDeplacement.miseAjourVision(new Point(this.positionX,this.positionY),this.getChampDeVision(),map);
		
		System.out.println("APRES VISION ANIMAL");
		
		
		//mettre a jour les emotions en fonction de la vision
		this.lesEnvies=VisionEtDeplacement.regarder(tableauVision,this.getChampDeVision());
			
		//Recupere l'envie la plus forte
		Envie envieTemporaire =Envie.envieLaPlusForte(lesEnvies);
		
		//System.out.println("envie du deplacement "+envieTemporaire);
		
		
		//recupere la liste du chemin parcouru par l'Animal
		Stack<Point> chemin =VisionEtDeplacement.deplacement(this.positionX,this.positionY,map,envieTemporaire.getEmotion());
		
		
		if(chemin.size()==0){
			throw new Exception("aucune case d'arriver n'a ete choisi par l'animal");
		}
		
		Point converteur=new Point (this.positionX-this.getChampDeVision(),this.positionY-this.getChampDeVision());
		
		//System.out.println("position actuelle "+this.positionX +" "+this.positionY);
		
		//System.out.println("taille pile recuperer "+chemin.size());
		
		
		int taillePile=chemin.size();
		
		for(int i =0; i<taillePile-1; i++){
			//appliquer les deplacement intermediaire
			
			//System.out.println("position actuelle "+this.positionX +" "+this.positionY);
			Point tmp=chemin.pop();
			
			map[this.positionX][this.positionY].setAnimalPresent(null);
			
			//Moteur.lafenetre.miseAjoursCase(this.positionX, this.positionY);
			
			this.positionX=tmp.x+converteur.x;
			this.positionY=tmp.y+converteur.y;
			
			//System.out.println("position ou aller "+this.positionX+" "+this.positionY);
			
			map[this.positionX][this.positionY].setAnimalPresent(this);
			try{
				Thread.sleep(1000);

			}catch(Exception e){
				
			}
			//Fenetre.affichegeTerrain.repaint();
			//Moteur.lafenetre.miseAjoursCase(this.positionX, this.positionY);
			//Fenetre.affichegeTerrain.repaint();
			
		}
		//appliquer le dernier deplacement
		Point arriver =chemin.pop();
		
		map[this.positionX][this.positionY].setAnimalPresent(null);
		
		Moteur.laFenetre.repaint();
		
		//System.out.println("fin de la pile "+chemin.size());
		
		this.positionX=arriver.x+converteur.x;
		this.positionY=arriver.x+converteur.y;
		
		//recupere l'Animal hypotethiquement present		
		Etre animalPresent=map[arriver.x+converteur.x][arriver.y+converteur.y].getAnimalPresent();
		
		//recupere la plante hypotethiquement present
		Etre plantePresente=map[arriver.x+converteur.x][arriver.y+converteur.y].getPlante();
		
		//applique une action en fonction de l'emotion
		
		switch (envieTemporaire.getEmotion()){
		
			case FAIM:
				
				if (animalPresent != null && this!=animalPresent){
					// il y a un autre annimal sur la case d'arriver
					
					if (this instanceof Carnivore && animalPresent instanceof Herbivore ){
						
						EtreVivant seFaitManger=map[(arriver.x+converteur.x)][(arriver.y+converteur.y)].getAnimalPresent();
						
						this.manger(seFaitManger,envieTemporaire,map);
						
						this.positionX=arriver.x+converteur.x;
						this.positionY=arriver.y+converteur.y;
						map[this.positionX][this.positionY].setAnimalPresent(this);
	
						Fenetre.minimap.repaint();
						return null;					
					}
					else if( this instanceof Carnivore && animalPresent instanceof Carnivore){
						
						//deux carnivor d'instance differente qui s'affronte
						EtreVivant seFaitAttaquer=map[(arriver.x+converteur.x)][(arriver.y+converteur.y)].getAnimalPresent();
						
						if(this.force>=((Animal)animalPresent).force){
							//this a gagner
							this.manger(seFaitAttaquer,envieTemporaire,map);
							this.positionX=arriver.x+converteur.x;
							this.positionY=arriver.y+converteur.y;
							map[this.positionX][this.positionY].setAnimalPresent(this);
	
	
							return null;
						}
						else{
							//seFaitAttaquer a gagner
							((Animal) seFaitAttaquer).manger(this,envieTemporaire,map);
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
				else if(animalPresent ==null || this==animalPresent ){
					
					if (this instanceof Herbivore &&  plantePresente!=null){
						this.manger(plantePresente,envieTemporaire,map);
					}
					
					
					this.positionX=arriver.x+converteur.x;
					this.positionY=arriver.y+converteur.y;
					map[this.positionX][this.positionY].setAnimalPresent(this);
					Fenetre.minimap.repaint();
					
					return null;
					
				}
				else{
					throw new Exception("PROBLEM ANNIMAL AVEC FAIM");
					
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
				
				
				// l'animal est arriver sur sa case et va essayer de se reproduire
				this.positionX=arriver.x+converteur.x;
				this.positionY=arriver.y+converteur.y;
				map[this.positionX][this.positionY].setAnimalPresent(this);
				
				//trouver les partenaires
				//trouver les cases Vides autour de la femelle ou peuvent apparaitre le bebe
				//definir si this ou le partenaire de this est la femelle
				//si un bebe peut etre creer mettre a jour variable des parents
				//placer le bebe sur la map
				//return le bebe pour le rajouter a la liste des etres dans le moteur
				
				Case[][] visionThis=VisionEtDeplacement.miseAjourVision(new Point(this.positionX,this.positionY),1,map);
				
				Terrain a =new Terrain(10,10,10);
				a.map=visionThis;
				a.afficheVisionShell();
				
				List<Etre> partenairesPossible = this.partenairePossible(visionThis);
				
				
				if (partenairesPossible.isEmpty()){
					//aucun partenaire donc pas de bebe
					return null;
				}
				
				Collections.shuffle(partenairesPossible);
				
				List<Point> caseVideAutourFemelle=new ArrayList<Point>();
				
				Etre partenaireChoisi=null;
				
				boolean thisEstLaFemelle = false;
				
				for(int i=0 ; i<partenairesPossible.size() ; i++){
					
					Etre PartenaireATester=partenairesPossible.get(i);
					
					if (PartenaireATester!=null){
			
						if(this.isFemelle()){
							
							caseVideAutourFemelle= casesVidesAutourFemelle(this,visionThis);
							
							if ( ! caseVideAutourFemelle.isEmpty()){
								partenaireChoisi=PartenaireATester;
								
								thisEstLaFemelle=true;
								break;
							}
						}
						else if (((Animal)PartenaireATester).isFemelle()){
							
							Case[][] visionPartenaireATester=VisionEtDeplacement.miseAjourVision(new Point(PartenaireATester.positionX,PartenaireATester.positionY),1,map);
							
							caseVideAutourFemelle= casesVidesAutourFemelle(PartenaireATester,visionPartenaireATester);
							
							if ( ! caseVideAutourFemelle.isEmpty()){
								partenaireChoisi=PartenaireATester;
								
								thisEstLaFemelle=false;
								break;
							}
						}
					
					}
				}
				
				if(partenaireChoisi!=null){
					
					Collections.shuffle(caseVideAutourFemelle);
					
					//coordonnes dans la vision de la femmelle
					Point coordonnesBebe=new Point(caseVideAutourFemelle.get(0).x,caseVideAutourFemelle.get(0).y);
					
					
					// remise a l'echelle de la map des coordonnes du bebe
					if(thisEstLaFemelle){
						
						Point converteurBEBE=new Point (this.positionX-1,this.positionY-1);
						
						coordonnesBebe.x=coordonnesBebe.x+converteurBEBE.x;
						coordonnesBebe.y=coordonnesBebe.y+converteurBEBE.y;
					}
					else{
						Point converteurBEBE=new Point (partenaireChoisi.positionX-1,partenaireChoisi.positionY-1);
						
						coordonnesBebe.x=coordonnesBebe.x+converteurBEBE.x;
						coordonnesBebe.y=coordonnesBebe.y+converteurBEBE.y;
					}
					
					System.out.println("coordonnes");
					System.out.println(coordonnesBebe);
					
					
					Etre bebe=this.bebe(partenaireChoisi,coordonnesBebe);
					
					//placement du bebe sur la map
					map[bebe.positionX][bebe.positionY].setAnimalPresent((Animal)bebe);
					
					// les variables des parents sont incrementer
					((EtreVivant)partenaireChoisi).nombreDeReproduction++;
					((EtreVivant)this).nombreDeReproduction++;
					
					return bebe;
				}
		}//fermeture du switch
		
		this.positionX=arriver.x+converteur.x;
		this.positionY=arriver.y+converteur.y;
		
		map[this.positionX][this.positionY].setAnimalPresent(this);
		
		Fenetre.affichegeTerrain.repaint();
		
		return null;
		
	}

	public void visionAutourDeThisIsGoodSize(Case[][] visionAutourDeThis,int champDeVision) throws Exception{
		
		int tailleVision=1+champDeVision*2;
		if(visionAutourDeThis.length!=tailleVision && visionAutourDeThis[0].length!=tailleVision){
			throw new Exception("\nla fonction visionAutourDeThisIsGoodSize a ete appeler \n"
					+ "avec un tableau de "+tailleVision+" case de coté \npour un tableau de "+visionAutourDeThis.length +" case de coté\n");
		}
	}
	
	private List<Etre> partenairePossible(Case[][] visionAutourDeThis) throws Exception{
		//renvoi la liste des partenairePossible dans le champ de reproduction de this
		//plus precisement dans les 8cases autour de this
		
		//Securite
		
		visionAutourDeThisIsGoodSize(visionAutourDeThis,1);
		
		List<Etre> partenairesPossible = new ArrayList<Etre>();
		
		
		for(int i=0 ; i<visionAutourDeThis.length;i++){
			for(int j=0 ; j<visionAutourDeThis[0].length;j++){
				
				if (visionAutourDeThis[i][j].getAnimalPresent()==this){
					// this ne peut se reproduire avec lui meme
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
			for(int j=0 ; j<visionAutourDeFemelle[0].length;j++){
				
				Case tmp=visionAutourDeFemelle[i][j];
				
				if( !tmp.isObstacle() && tmp.getAnimalPresent()==null && !(tmp.getAnimalPresent()==this) ){
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
