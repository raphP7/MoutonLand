package Moteur;

import java.awt.Point;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

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
	}
	public EtreVivant(int x , int y ,boolean femelle , int esperenceDeVie , int nbToursPourDevenirPuber, int  maxReproduction , int esperenceSansManger, int champDeVision){
		super(x,y);
		this.femelle=femelle;
		this.esperenceDeVie=esperenceDeVie;
		this.nbToursPourDevenirPuber=nbToursPourDevenirPuber;
		this.maxReproduction=maxReproduction;
		this.esperenceSansManger=esperenceSansManger;
		this.champDeVision=champDeVision;
		//this.bloqueChangement=true;
		this.aEteTuer=false;
	}
	
	public boolean toujourEnVie(){
		return this.toursEnVie<this.esperenceDeVie && this.toursSansManger<this.esperenceSansManger && !aEteTuer;
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

	public boolean isaEteTuer() {
		return aEteTuer;
	}

	public void setaEteTuer(boolean aEteTuer) {
		this.aEteTuer = aEteTuer;
	}
	
	public Etre bebe(Etre a,Point positionBebe) {

		if ( a.getClass().equals(this.getClass()) ){
			
			//reflection
			Constructor<? extends Etre> constructeurBebe;
			try {
				constructeurBebe = this.getClass().getConstructor(Etre.class ,Etre.class,Point.class);
				System.out.println(constructeurBebe.toString());
				Etre bebe;
				bebe = constructeurBebe.newInstance(this,a,positionBebe);
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
