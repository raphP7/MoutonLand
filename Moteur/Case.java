package Moteur;

class Case {

	protected Plante plante;
	
	protected EtreMort nourriture;
	
	protected Animal a;
	
	private int modif=0;
	
	protected int valeurSel;
	
	private boolean obstacle; // impossible d'acceder , impossible de voir a travers
	private boolean visible; // true = accessible , else = inaccessible
	
	
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
