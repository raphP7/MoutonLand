package Moteur;
import java.awt.Point;

public abstract class Etre {
	

	public int positionX;
	public int positionY;
	
	
	public Etre(int x, int y){
		this.positionX=x;
		this.positionY=y;
	}
	
	// dans le cadre d'un bebe
	public Etre(Point position){
		this.positionX=position.x;
		this.positionY=position.y;
	}

}
