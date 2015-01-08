package Moteur.Intelligence;

import java.util.Iterator;
import java.util.NoSuchElementException;

import Moteur.Terrain.Case;

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