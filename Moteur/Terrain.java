package Moteur;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

class Terrain {
	
	int x;
	int y;
	int caseVide;
	
	public Case [][] map;
	
	public Terrain(int x , int y){//dimension
		this.x=x;
		this.y=y;
		this.map= new Case[x][y];
		for (int i =0 ; i<map.length ; i++){
			for (int j=0 ; j<map[0].length ; j++){
				map[i][j]=new Case();
			}
		}
	}
	public boolean ajouterUnAnimal(){
		int cmp=0;
		for (int i =0 ; i<map.length ; i++){
			for (int j=0 ; j<map[0].length ; j++){
				if (map[i][j].animalPresent==null && !map[i][j].isObstacle()){
					cmp++;
				}
			}
		}
		this.caseVide=cmp;
		return cmp>0;
	}
	public List<Etre> unTour(){
		List<Etre> nouvellesPlantes = new ArrayList<Etre>();
		
		for (int i =0 ; i<map.length ; i++){
			for (int j=0 ; j<map[0].length ; j++){
				
				int valeur=map[i][j].valeurSel;
				
				if (valeur>0 && map[i][j].plante==null){
					
					nouvellesPlantes.add(new Plante(i,j,false,valeur*10,10,3,valeur*5));
				}
			}
		}
		
		return nouvellesPlantes;
	}
}
