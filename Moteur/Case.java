package Moteur;

public class Case {

	public Plante plante;
	
	//protected EtreMort nourriture;
	
	public Animal animalPresent;
	
	private int modif=0;
	
	protected int valeurSel;
	
	private boolean obstacle; // impossible d'acceder , impossible de voir a travers
	private boolean visible; // true = visible , else = contenu invisible depuis la position x , y de l'Animal
	
	
	public Case(){
		this.setObstacle(false);
		this.setVisible(true);
	}

	public boolean isObstacle() {
		return obstacle;
	}

	public void setObstacle(boolean obstacle) {
		this.modif++;
		this.obstacle = obstacle;
		if (obstacle){this.visible=false;}
	}

	public boolean isVisible() {
		return visible;
	}

	public void setVisible(boolean accessible) {
		this.modif++;
		this.visible = accessible;
		if (accessible){this.obstacle=false;}
	}
	
	public int getModif(){
		return this.modif;
	}

}
