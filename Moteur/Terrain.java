package Moteur;
import java.awt.Point;
import java.util.ArrayList;
import java.util.Collections;
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
	public List<Etre> ajouterAnimalALeatoire(List<Etre> listAjout){
		
		List<Point> casesVides = new ArrayList<Point>();
		
		for (int i =0 ; i<map.length ; i++){
			for (int j=0 ; j<map[0].length ; j++){
				if (map[i][j].animalPresent==null && !map[i][j].isObstacle()){
					casesVides.add(new Point(i,j));
				}
			}
		}
		
		Collections.shuffle(casesVides);
		int conteur=0;
		for (int i=0; i<listAjout.size() ; i++ ){
			
			if (conteur<casesVides.size()){
				
				//positionement de l'animal
				Point tmp=casesVides.get(conteur);
				
				Etre animalTmp=listAjout.get(i);
				
				animalTmp.positionX=tmp.x;
				animalTmp.positionY=tmp.y;
				
				//placement sur la map
				map[tmp.x][tmp.y].animalPresent=(Animal) animalTmp;
				
				conteur++;
			}
			else{
				break;
			}
			
		}
		return listAjout;
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
