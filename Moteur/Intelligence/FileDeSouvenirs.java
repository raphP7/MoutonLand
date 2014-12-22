package Moteur.Intelligence;
import java.util.Iterator;

import Moteur.Terrain.Case;

public class FileDeSouvenirs implements Iterable<Emplacement>{
	
	private Emplacement tete;
	private int taille;
	
	public FileDeSouvenirs(int taille , int x , int y ,Case[][] visionSouvenir){
	
		this.setTaille(taille);
		this.ajouter(x,y,visionSouvenir);
	}
	
	public int taillereele(){
		
		boolean a=true;
		Emplacement b=this.tete;
		if(this.tete==null){
			return 0;	
		}
		int i=1;
		while(a){
			
			if(b.hasNext()){
				b=b.suivant;
				i++;
			}
			else{
				a=false;
			}
		}
		return i;
	}
	public void ajouter(int x , int y ,Case[][] visionSouvenir){
		
		this.tete=new Emplacement(x,y,visionSouvenir,this.tete);
		boolean parcour=true;
		Emplacement iter=this.getTete();
		
		int i=1;
		
		while(parcour){
			
			if (i==taille){
				if (iter.suivant!=null){
				iter.suivant=null;
				}
				parcour=false;
			}
			else if(iter.suivant!=null){
				iter=iter.suivant;
				i++;
			}
			else{
				parcour=false;
			}
		}
		
	}
	
	@Deprecated
	public Emplacement creerUnEmplacement( int x , int y ,Case[][] visionSouvenir){
		return new Emplacement(x,y,visionSouvenir,null); 
	}
	
	public int getTaille() {
		return taille;
	}
	
	public void setTaille(int taille) {
		if (taille<1){
			this.taille = 1;
		}
		else{
			this.taille = taille;
		}
	}
	
	public Emplacement getTete() {
		return tete;
	}

	@Override
	
	public Iterator<Emplacement> iterator() {
		return tete;
		
	}

}
