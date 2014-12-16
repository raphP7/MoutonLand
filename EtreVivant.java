
public abstract class EtreVivant {

	protected int positionX;
	protected int positionY;
	protected boolean femelle;// true =femelle , false=male
	protected int champDeVision=4;
	protected int immobile;
	protected int toursEnVie;
	protected int nombreDeReproduction;
	protected int toursSansManger;
	
	public abstract boolean mourir();
	public abstract boolean manger();
	
}
