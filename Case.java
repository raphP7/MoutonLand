
public class Case {

	protected Plante plante;
	
	protected EtreMort sel;
	
	protected Animal a;
	
	private int modif=0;
	
	private boolean obstacle; // impossible d'acceder , impossible de voir a travers
	private boolean accessible; // true = accessible , else = inaccessible
	
	public Case(boolean b){
		this.setObstacle(b);
	}

	public boolean isObstacle() {
		return obstacle;
	}

	public void setObstacle(boolean obstacle) {
		this.modif++;
		this.obstacle = obstacle;
		if (obstacle){this.accessible=false;}
	}

	public boolean isAccessible() {
		return accessible;
	}

	public void setAccessible(boolean accessible) {
		this.modif++;
		this.accessible = accessible;
		if (accessible){this.obstacle=false;}
	}
	
	public int getModif(){
		return this.modif;
	}

}
