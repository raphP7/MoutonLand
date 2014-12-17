
public abstract class EtreVivant {

	protected int positionX;
	protected int positionY;
	protected boolean femelle;// true =femelle , false=male
	protected int champDeVision=5;
	protected int immobile;
	protected int esperenceDeVie;
	protected int toursEnVie;
	protected int nombreDeReproduction;
	protected int esperenceSansManger;
	protected int toursSansManger;
	
	public boolean mortNaturel(){
		return this.toursEnVie>this.esperenceDeVie || this.toursSansManger>this.esperenceSansManger;
	}
	public abstract void manger();
	
}
