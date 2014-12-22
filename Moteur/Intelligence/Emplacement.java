package Moteur.Intelligence;

import java.util.Iterator;
import java.util.NoSuchElementException;

import Moteur.Terrain.Case;

public class Emplacement implements Iterator<Emplacement>{

	public int x;
	public int y;
	
	public Case[][] visionSouvenir;
	
	public Emplacement suivant;
	
	public Emplacement(int x , int y ,Case[][] visionSouvenir,Emplacement suivant){
		this.x=x;
		this.y=y;
		this.visionSouvenir=visionSouvenir;
		this.suivant=suivant;
		
	}

	@Override
	public boolean hasNext() {
		
		if (suivant==null){
			return false;
		}
		else{
			return true;
		}
		
	}

	@Override
	public Emplacement next() throws NoSuchElementException {
		
		if(hasNext()){
			return this.suivant;
		}
		else{
			System.out.println("ici");
			throw new NoSuchElementException();	
		}
		
	}

	@Override
	public String toString() {
		return " X: "+x+", Y: "+y;
	}

	@Override
	public void remove() {
		// TODO Auto-generated method stub
		
	}
	
}