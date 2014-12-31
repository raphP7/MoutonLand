package Moteur;

import java.awt.Point;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Random;

import Affichage.Fenetre;
public abstract class EtreVivant extends Etre implements FonctionsDeBaseVivant {
	//private boolean bloqueChangement=false;
	
	private boolean femelle;// true =femelle , false=male
	private int esperenceDeVie;
	private int toursEnVie;
	private boolean puber;
	private int nbToursPourDevenirPuber;
	protected int nombreDeReproduction;
	protected int maxReproduction;
	private int esperenceSansManger;
	protected int toursSansManger;
	private int champDeVision=5;
	private boolean aEteTuer;
	
	public EtreVivant(Etre a , Etre b, Point position){
		super(position);
		
		Random random=new Random();
		
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
		
	}
	public EtreVivant(int x , int y ,boolean femelle , int esperenceDeVie , int nbToursPourDevenirPuber, int  maxReproduction , int esperenceSansManger, int champDeVision){
		super(x,y);
		this.femelle=femelle;
		this.esperenceDeVie=esperenceDeVie;
		this.puber=false;
		this.nbToursPourDevenirPuber=nbToursPourDevenirPuber;
		this.maxReproduction=maxReproduction;
		this.esperenceSansManger=esperenceSansManger;
		this.champDeVision=champDeVision;
		this.aEteTuer=false;
	}
	
	public boolean toujourEnVie(){
		if (this.toursEnVie>=this.esperenceDeVie){
			return false;
		}
		if(this.toursSansManger>=this.esperenceSansManger){
			return false;
		}
		if( aEteTuer){
			
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
		if(aEteTuer){
			// si animal tuer alors un autre est sur sa case
			//map[this.positionX][this.positionY].setAnimalPresent(null);
			Fenetre.affichegeTerrain.repaint();
		}
		
	}
	
	public Etre bebe(Etre b,Point positionBebe) {

		if ( b.getClass().equals(this.getClass()) ){
			
			//reflection
			Constructor<? extends Etre> constructeurBebe;
			
			try {
				constructeurBebe = this.getClass().getConstructor(Etre.class ,Etre.class,Point.class);
				System.out.println(constructeurBebe.toString());
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
	
}
