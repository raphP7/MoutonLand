package Moteur.Intelligence;

import Moteur.Terrain.Case;

/**
 * Class pour stocker une postion et un vision de cette position dans la memoire de l'animal
 * @author Raph
 *
 */
public class Emplacement{

	public int x;
	public int y;
	
	public Case[][] visionSouvenir;
	
	
	public Emplacement(int x , int y ,Case[][] visionSouvenir){
		this.x=x;
		this.y=y;
		this.visionSouvenir=visionSouvenir;
		
	}

	@Override
	public String toString() {
		return " X: "+x+", Y: "+y;
	}

	
}