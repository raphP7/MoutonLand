package Moteur.Intelligence;
import Moteur.Terrain.Case;

public class FileDeSouvenirs {
	
	private Emplacement tete;
	private int taille;
	
	class Emplacement{

		int x;
		int y;
		
		Case[][] visionSouvenir;
		Emplacement suivant;
		
		private Emplacement(int x , int y ,Case[][] visionSouvenir){
			this.x=x;
			this.y=y;
			this.visionSouvenir=visionSouvenir;
		}
	}
	
	public FileDeSouvenirs(int taille , int x , int y ,Case[][] visionSouvenir){
	
		this.setTaille(taille);
		this.ajouter(x,y,visionSouvenir);
	}
	void ajouter(int x , int y ,Case[][] visionSouvenir){
		
		Emplacement tmp=this.tete;
		this.tete=creerEmplacement(x,y,visionSouvenir);
		this.tete.suivant=tmp;
		
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
	public Emplacement creerEmplacement( int x , int y ,Case[][] visionSouvenir){
		return new Emplacement(x,y,visionSouvenir);
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
}
