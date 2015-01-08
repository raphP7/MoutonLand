package Moteur;

import java.awt.Point;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import Affichage.Fenetre;
import Moteur.Intelligence.Emotion;
import Moteur.Intelligence.Envie;
import Moteur.Intelligence.VisionEtDeplacement;
import Moteur.Terrain.Case;
public abstract class EtreVivant extends Etre implements FonctionsDeBaseVivant {
	//private boolean bloqueChangement=false;
	
	private boolean femelle;// true =femelle , false=male
	private int esperenceDeVie;
	private int toursEnVie;
	private boolean puber;
	private int nbToursPourDevenirPuber;
	private int nombreDeReproduction;
	protected int maxReproduction;
	private int esperenceSansManger;
	protected int toursSansManger;
	private int champDeVision=5;
	private boolean aEteTuer;
	private static Random random=new Random();
	public EtreVivant(Etre a , Etre b, Point position){
		super(position);
		
		this.femelle=random.nextBoolean();
			
		// est conserver le meilleur de chaque parent
		
		if( ((EtreVivant)a).esperenceDeVie >  ((EtreVivant)b).esperenceDeVie){
			
			this.esperenceDeVie=((EtreVivant)a).esperenceDeVie;
		}
		else{
			this.esperenceDeVie=((EtreVivant)b).esperenceDeVie;
		}		
		
		if( ((EtreVivant)a).nbToursPourDevenirPuber <  ((EtreVivant)b).nbToursPourDevenirPuber){
			
			this.nbToursPourDevenirPuber=((EtreVivant)a).nbToursPourDevenirPuber;
		}
		else{
			this.nbToursPourDevenirPuber=((EtreVivant)b).nbToursPourDevenirPuber;
		}
		
		if( ((EtreVivant)a).maxReproduction >  ((EtreVivant)b).maxReproduction){
			
			this.maxReproduction=((EtreVivant)a).maxReproduction;
		}
		else{
			this.maxReproduction=((EtreVivant)b).maxReproduction;
		}
		if( ((EtreVivant)a).esperenceSansManger >  ((EtreVivant)b).esperenceSansManger){
			
			this.esperenceSansManger=((EtreVivant)a).esperenceSansManger;
		}
		else{
			this.esperenceSansManger=((EtreVivant)b).esperenceSansManger;
		}
		if( ((EtreVivant)a).champDeVision >  ((EtreVivant)b).champDeVision){
			
			this.champDeVision=((EtreVivant)a).champDeVision;
		}
		else{
			this.champDeVision=((EtreVivant)b).champDeVision;
		}
		
		this.aEteTuer=false;
		this.puber=false;
		this.nombreDeReproduction=0;
		
	}
	public EtreVivant(int x , int y ,boolean femelle , int esperenceDeVie , int nbToursPourDevenirPuber, int  maxReproduction , int esperenceSansManger, int champDeVision){
		super(x,y);
		this.femelle=femelle;
		this.esperenceDeVie=esperenceDeVie;
		
		this.nbToursPourDevenirPuber=nbToursPourDevenirPuber;
		this.maxReproduction=maxReproduction;
		this.esperenceSansManger=esperenceSansManger;
		this.champDeVision=champDeVision;
		this.aEteTuer=false;
		this.puber=false;
		this.nombreDeReproduction=0;
	}
	
	/**
	 * Calcul si un une reproduction entre etrevivant , puber , de meme race et "consentant" est possible
	 * renvoi le bebe creer pour le stocker dans la liste des etre du moteur
	 * 
	 * @param map le terrain de la simulation
	 * @param laFenetre la fenetre d'affichage du moteur pour mettre a jour a l'emplacement du bebe
	 * @return
	 * @throws Exception
	 */
	public Etre actionReproduction(Case [][] map , Fenetre laFenetre) throws Exception{
		// l'etrevivant est arriver sur sa case et va essayer de se reproduire
		
		//trouver les partenaires
		//trouver les cases Vides autour de la femelle ou peuvent apparaitre le bebe
		//definir si this ou le partenaire de this est la femelle
		//si un bebe peut etre creer mettre a jour variable des parents
		//placer le bebe sur la map
		//return le bebe pour le rajouter a la liste des etres dans le moteur
		
		if(this instanceof Plante){
			int ale=random.nextInt(Moteur.valeurAleatoireReproductionPlante);
			if(ale!=0){
				return null;
			}
		}
		
		
		Case[][] visionThis = null;
		
		int champDeReproductionPlante=4;
		
		if (this instanceof Animal){
			visionThis=VisionEtDeplacement.miseAjourVision(new Point(this.positionX,this.positionY),1,map);
		}
		else if (this instanceof Plante){
			visionThis=VisionEtDeplacement.miseAjourVision(new Point(this.positionX,this.positionY),champDeReproductionPlante,map);
		}
		
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
				else if (((EtreVivant)PartenaireATester).isFemelle()){
					Case[][]  visionPartenaireATester=null;
					if(this instanceof Animal){
						visionPartenaireATester=VisionEtDeplacement.miseAjourVision(new Point(PartenaireATester.positionX,PartenaireATester.positionY),1,map);		
					}
					else if(this instanceof Plante){
						visionPartenaireATester=VisionEtDeplacement.miseAjourVision(new Point(PartenaireATester.positionX,PartenaireATester.positionY),champDeReproductionPlante,map);	
					}
					
					
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
			Point converteurBEBE = null;
			
			// remise a l'echelle de la map des coordonnes du bebe
			if(thisEstLaFemelle){
				
				if(this instanceof Animal){
					converteurBEBE=new Point (this.positionX-1,this.positionY-1);
				}
				else if(this instanceof Plante){
					
					converteurBEBE=new Point (this.positionX-champDeReproductionPlante,this.positionY-champDeReproductionPlante);
				}
			}
			else{
				
				if(this instanceof Animal){
					converteurBEBE=new Point (partenaireChoisi.positionX-1,partenaireChoisi.positionY-1);
				}
				else if(this instanceof Plante){
					
					converteurBEBE=new Point (partenaireChoisi.positionX-champDeReproductionPlante,partenaireChoisi.positionY-champDeReproductionPlante);
				}
				
			}
			
			coordonnesBebe.x=coordonnesBebe.x+converteurBEBE.x;
			coordonnesBebe.y=coordonnesBebe.y+converteurBEBE.y;
			Etre bebe=this.bebe(partenaireChoisi,coordonnesBebe);
			
			//placement du bebe sur la map
			if( this instanceof Animal){
				map[bebe.positionX][bebe.positionY].setAnimalPresent((Animal)bebe);		
			}
			else if (this instanceof Plante){
				
				map[bebe.positionX][bebe.positionY].setPlante((Plante)bebe);
			}
			laFenetre.miseAjoursCase(bebe.positionX, bebe.positionY);
			
			// les variables des parents sont incrementer
			((EtreVivant)partenaireChoisi).setNombreDeReproduction();
			((EtreVivant)this).setNombreDeReproduction();
			
			return bebe;
		}
		return null;
		
	}
	
	private List<Etre> partenairePossible(Case[][] visionAutourDeThis) throws Exception{
		//renvoi la liste des partenairePossible dans le champ de reproduction de this
		//plus precisement dans les 8cases autour de this pour un animal

		
		/*Securite
		
		if(this instanceof Animal ){
			visionAutourDeThisIsGoodSize(visionAutourDeThis,1);
		}
		*/
		List<Etre> partenairesPossible = new ArrayList<Etre>();
		
		
		for(int i=0 ; i<visionAutourDeThis.length;i++){
			for(int j=0 ; j<visionAutourDeThis[0].length;j++){
				
				if(this instanceof Animal){
				
				if (visionAutourDeThis[i][j].getAnimalPresent()==this){
					// this ne peut se reproduire avec lui meme
				}
				else if (visionAutourDeThis[i][j].getAnimalPresent() !=null){
					
					if (this.reproductionPossible(visionAutourDeThis[i][j].getAnimalPresent())){
						
						partenairesPossible.add(visionAutourDeThis[i][j].getAnimalPresent());
						
					}
				}
				}
				else if(this instanceof Plante){
					if (visionAutourDeThis[i][j].getPlante()==this){
						// this ne peut se reproduire avec lui meme
					}
					else if (visionAutourDeThis[i][j].getPlante() !=null){
						
						if (this.reproductionPossible(visionAutourDeThis[i][j].getPlante())){
							
							partenairesPossible.add(visionAutourDeThis[i][j].getPlante());
							
						}
					}
				}
			}
		}
		
		return partenairesPossible;
	}
	
	private List<Point> casesVidesAutourFemelle(Etre femelle,Case[][] visionAutourDeFemelle) throws Exception{
		
		//SECURITER obslete
		//visionAutourDeThisIsGoodSize(visionAutourDeFemelle,1);
		
		if(	!((EtreVivant)femelle).isFemelle() ){
			throw new Exception("Erreur la fonction casesVidesAutourFemelle a ete appeler avec un Male");
		}
		
		List<Point> listeCaseVidePourBebe = new ArrayList<Point>();
		
		for(int i=0 ; i<visionAutourDeFemelle.length;i++){
			for(int j=0 ; j<visionAutourDeFemelle[0].length;j++){
				
				Case tmp=visionAutourDeFemelle[i][j];
				
				if(femelle instanceof Animal){
					if( !tmp.isObstacle() && tmp.getAnimalPresent()==null && !(tmp.getAnimalPresent()==this) ){
						listeCaseVidePourBebe.add(new Point(i,j));// IL FAUDRA RECUPERER LES VRAI VALEUR DE LA MAP PAR RAPPORT A 
					}
				}
				else if(femelle instanceof Plante){
					if( !tmp.isObstacle() && tmp.getPlante()==null && !(tmp.getPlante()==this) ){
						listeCaseVidePourBebe.add(new Point(i,j));// IL FAUDRA RECUPERER LES VRAI VALEUR DE LA MAP PAR RAPPORT A 
					}
				}
		
			}
	
		}
		return listeCaseVidePourBebe;
	}
	
	
	public boolean toujourEnVie(){
		if (this.toursEnVie>=this.esperenceDeVie){
			this.aEteTuer=true;
			return false;
		}
		if(this.toursSansManger>=this.esperenceSansManger){
			this.aEteTuer=true;
			return false;
		}
		if(aEteTuer){
			return false;
		}
		return true;
	}	
	public boolean isPuber() {
		return puber;
	}
	public void setPuber() {
		if (this.nbToursPourDevenirPuber<this.toursEnVie){
			this.puber = true;
		}
		else{
			this.puber = false;
		}
	}
	public int getNbToursPourDevenirPuber() {
		return nbToursPourDevenirPuber;
	}
	public void setNbToursPourDevenirPuber(int nbToursPourDevenirPuber) {
		this.nbToursPourDevenirPuber = nbToursPourDevenirPuber;
	}
	public int getToursEnVie() {
		return toursEnVie;
	}
	public void incrementeToursEnVie() {
		this.toursEnVie++;
		if (!this.puber){
			this.setPuber();
		}
	}
	public int getEsperenceDeVie() {
		return esperenceDeVie;
	}
	public boolean isFemelle() {
		return femelle;
	}

	public int getChampDeVision() {
		return champDeVision;
	}

	public void setChampDeVision(int champDeVision) {
		
		if (champDeVision<1){
			champDeVision=1;
		}
		else{
			this.champDeVision = champDeVision;
		}
	}
	
	public void setaEteTuer(boolean aEteTuer) {
		this.aEteTuer = aEteTuer;
	}
	public Etre bebe(Etre b,Point positionBebe) {

		//if ( b.getClass().equals(this.getClass()) ){
		if(this.getClass().toString().contains(b.getClass().toString()) || b.getClass().toString().contains(this.getClass().toString())){
		
			//reflection
			Constructor<? extends Etre> constructeurBebe;
			
			try {
				constructeurBebe = this.getClass().getConstructor(Etre.class ,Etre.class,Point.class);
				//System.out.println(constructeurBebe.toString());
				Etre bebe;
				bebe = constructeurBebe.newInstance(this,b,positionBebe);
				return bebe;
			}
			catch (NoSuchMethodException | SecurityException |InstantiationException | IllegalAccessException
					| IllegalArgumentException | InvocationTargetException e)
			{
				e.printStackTrace();
			}
			return null;
		}
		else{
			return null;
		}
	}
	public int getNombreDeReproduction() {
		return nombreDeReproduction;
	}
	public void setNombreDeReproduction() throws Exception {
		this.nombreDeReproduction++;
		if(this.nombreDeReproduction>this.maxReproduction){
			throw new Exception("l'etre vivant c'est reproduit plus de fois que possible");
		}
		
		if(this instanceof Animal){
			for (Envie a : ((Animal)this).getLesEnvies()){
				if(a.getEmotion()==Emotion.REPRODUCTION){
					a.setValeur(0);
				}
			}
		}
	}
	
	public boolean reproductionPossible(Etre b){
		
		if (((EtreVivant) b).isFemelle() != this.isFemelle()  ){// les etres sont de  Sexe Opposer
			
			if(this.getClass().toString().contains(b.getClass().toString()) || b.getClass().toString().contains(this.getClass().toString())){
				//de meme "Race"
					
				//les etres peuvents encore se reproduire
				if(this.maxReproduction>this.getNombreDeReproduction() && ((EtreVivant)b).maxReproduction>((EtreVivant)b).getNombreDeReproduction()){// les etres peuvent se reproduire
					
					//les etres sont puberts
					if(this.isPuber() && ((EtreVivant)b).isPuber()){
						return true;
					}
					else return false;
				}
				else return false;
			}
				
			else return false;
		}
			
		else return false;
		
	}
}
