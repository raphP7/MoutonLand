
public class FileDeSouvenirs {
	
	Emplacement tete;
	int taille;
	
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
		
		this.taille=taille;
		this.ajouter(x,y,visionSouvenir);
	}
	void ajouter(int x , int y ,Case[][] visionSouvenir){
		Emplacement tmp=this.tete;
		this.tete=creerEmplacement(x,y,visionSouvenir);
		this.tete.suivant=tmp;
	}
	public Emplacement creerEmplacement( int x , int y ,Case[][] visionSouvenir){
		return new Emplacement(x,y,visionSouvenir);
	}
}
