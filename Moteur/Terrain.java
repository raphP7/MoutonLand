package Moteur;
import java.awt.Point;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Terrain {
	
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
	public List<Etre> ajouterEtreALeatoire(List<Etre> listAjout) throws Exception{
		
		if (listAjout ==null){
			return null;
		}
		if (listAjout.size()==0){
			return null;
		}
		boolean different=false;
		Etre temporaire=listAjout.get(0);
		
		for(Etre etre : listAjout){
			if (!temporaire.getClass().equals(etre.getClass())){
				different=true;
			}
		}
		if(different){
			throw new Exception("Attention la list doit contenir uniquement des object de la meme instance");
		}
		
		List<Point> casesVides = new ArrayList<Point>();
		
		for (int i =0 ; i<map.length ; i++){
			for (int j=0 ; j<map[0].length ; j++){
				
				if (listAjout.get(0) instanceof Plante){
					
					if (map[i][j].plante==null && !map[i][j].isObstacle()){
						casesVides.add(new Point(i,j));
					}
				}
				else if(listAjout.get(0) instanceof Animal){
					
					if (map[i][j].animalPresent==null && !map[i][j].isObstacle()){
						casesVides.add(new Point(i,j));
					}
				}
			}
		}
		
		Collections.shuffle(casesVides);
		int conteur=0;
		for (int i=0; i<listAjout.size() ; i++ ){
			
			if (conteur<casesVides.size()){
				
				//positionement de l'Etre
				Point tmp=casesVides.get(conteur);
				
				Etre EtreTmp=listAjout.get(i);
				
				EtreTmp.positionX=tmp.x;
				EtreTmp.positionY=tmp.y;
				
				//placement sur la map de l'Etre
				
				if (listAjout.get(0) instanceof Plante){
					map[tmp.x][tmp.y].plante=(Plante) EtreTmp;
				}
				else if(listAjout.get(0) instanceof Animal){
					map[tmp.x][tmp.y].animalPresent=(Animal) EtreTmp;
				}
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
