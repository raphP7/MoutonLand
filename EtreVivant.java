
public abstract class EtreVivant {
	//private boolean bloqueChangement=false;
	
	protected int positionX;
	protected int positionY;
	private boolean femelle;// true =femelle , false=male
	protected int immobile;
	private int esperenceDeVie;
	private int toursEnVie;
	private boolean puber;
	private int nbToursPourDevenirPuber;
	protected int nombreDeReproduction;
	protected int maxReproduction;
	private int esperenceSansManger;
	protected int toursSansManger;
	
	private int champDeVision=5;
	
	
	public EtreVivant(int x , int y ,boolean femelle , int esperenceDeVie , int nbToursPourDevenirPuber, int  maxReproduction , int esperenceSansManger, int champDeVision){
		
		this.positionX=x;
		this.positionY=y;
		this.femelle=femelle;
		this.esperenceDeVie=esperenceDeVie;
		this.nbToursPourDevenirPuber=nbToursPourDevenirPuber;
		this.maxReproduction=maxReproduction;
		this.esperenceSansManger=esperenceSansManger;
		this.champDeVision=champDeVision;
		//this.bloqueChangement=true;
	}
	
	public boolean mortNaturel(){
		return this.toursEnVie>this.esperenceDeVie || this.toursSansManger>this.esperenceSansManger;
	}
	public abstract void manger();
	
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
}