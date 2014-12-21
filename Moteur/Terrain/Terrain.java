package Moteur.Terrain;
import java.awt.Point;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import Moteur.Animal;
import Moteur.Etre;
import Moteur.Loup;
import Moteur.Mouton;
import Moteur.Plante;

public class Terrain {
	
	int x;
	int y;
	private int caseVide;
	
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
	
	public Terrain(int x , int y , int obstacles) throws Exception{
		this.x=x;
		this.y=y;
		this.map= new Case[x][y];
		boolean a=false;
		for (int i =0 ; i<map.length ; i++){
			for (int j=0 ; j<map[0].length ; j++){
				if (obstacles>0){
					map[i][j]=new Case(a);
				}
				else{
					map[i][j]=new Case();
				}
				
			}
		}
		
		if (obstacles>x*y){
			obstacles=x*y;
			return;
		}
		
		if (obstacles>0){
			
			Random rand = new Random();
			
			//pour partir d'environ le centre
			int nombreAleatoireX = rand.nextInt(map.length/2)+x/2;
			int nombreAleatoireY = rand.nextInt(map[0].length/3)+y/2;
			
			//la case aleatoire est rendu accessible et comme jamais modifier pour commencer
			map[nombreAleatoireX][nombreAleatoireY].setVisible(true);
			
			map[nombreAleatoireX][nombreAleatoireY].setModif(false);
			
			
			Point debut =new Point(nombreAleatoireX,nombreAleatoireY);
			
			//LinkedList<Point> listePointAccessible=new LinkedList<Point>();
			
			LinkedList<Point> listePointEntourerAccessible=new LinkedList<Point>();
			listePointEntourerAccessible.add(debut);
			int caseVide = (x*y)-obstacles;
			boolean uneCaseTrouver;
			while(caseVide>0){
				
				int aleatoire;
				if (listePointEntourerAccessible.size()<1000){
					aleatoire = rand.nextInt(listePointEntourerAccessible.size()+1);
				}
				else{
					aleatoire= rand.nextInt(1000);
				}
				
				//System.out.println(aleatoire);
				if(aleatoire>0){
					aleatoire--;
				}
				
				int Xproche=listePointEntourerAccessible.get(aleatoire).x;
				int Yproche=listePointEntourerAccessible.get(aleatoire).y;
				
				int Xalea = rand.nextInt(Xproche+10)+(Xproche-10);
				int Yalea = rand.nextInt(Yproche+10)+(Yproche-10);
			
				uneCaseTrouver=false;
				
				for(int i=Xalea-1 ; i<=Xalea+1 ; i++){
					
					if(uneCaseTrouver){
						
						//this.afficheShell();
						break;
					}
					
					for(int j=Yalea-1 ; j<=Yalea+1; j++){
						
						if (!((i==Xalea) && (y==Yalea) )){
								if(i+1>x || i<0 || j<0 || j+1>y || map[i][j].isModif()){
									uneCaseTrouver=true;
									break;
									
								}
								else if (i+1<x && j+1<y && !map[i+1][j+1].isObstacle()){
									map[i][j].setVisible(true);
									caseVide--;
									uneCaseTrouver=true;
									
									listePointEntourerAccessible.push(new Point(i,j));
									break;
								}
								else if (i+1<x && !map[i+1][j].isObstacle()){
									map[i][j].setVisible(true);
									caseVide--;
									uneCaseTrouver=true;
									
									listePointEntourerAccessible.push(new Point(i,j));
									break;
									
								}
								else if (j+1<y && !map[i][j+1].isObstacle()){
									map[i][j].setVisible(true);
									caseVide--;
									uneCaseTrouver=true;
									
									listePointEntourerAccessible.push(new Point(i,j));
									break;
									
								}
								else if (j-1>0 &&!map[i][j-1].isObstacle()){
									map[i][j].setVisible(true);
									caseVide--;
									uneCaseTrouver=true;
									
									listePointEntourerAccessible.push(new Point(i,j));
									break;
									
								}
								else if (i-1>0 &&!map[i-1][j].isObstacle()){
									map[i][j].setVisible(true);
									caseVide--;
									uneCaseTrouver=true;
									
									listePointEntourerAccessible.push(new Point(i,j));
									break;
									
								}
								else if (i-1>0 && j+1<y && !map[i-1][j+1].isObstacle()){
									map[i][j].setVisible(true);
									caseVide--;
									uneCaseTrouver=true;
									
									listePointEntourerAccessible.push(new Point(i,j));
									break;
								}
								else if (i+1<x && j-1>0 && !map[i+1][j-1].isObstacle()){
									map[i][j].setVisible(true);
									caseVide--;
									uneCaseTrouver=true;
									
									listePointEntourerAccessible.push(new Point(i,j));
									break;
								}
								else if (i-1>0 && j-1>0 &&!map[i-1][j-1].isObstacle()){
									map[i][j].setVisible(true);
									caseVide--;
									uneCaseTrouver=true;
									
									listePointEntourerAccessible.push(new Point(i,j));
									break;
								}
							
						}
					}
					
				}
		}
	}
		
			
}
	
	private boolean ListSansDefauts(List<Etre> listAjout) throws Exception{
		//return true si tous les Etre de la liste sont de la meme instance
		
		if (listAjout ==null){
			return false;
		}
		if (listAjout.isEmpty()){
			return false;
		}
		boolean pareil=true;
		Etre temporaire=listAjout.get(0);
		
		for(Etre etre : listAjout){
			if (!temporaire.getClass().equals(etre.getClass())){
				pareil=false;
				break;
			}
		}
		if(!pareil){
			throw new Exception("\nAttention la list doit contenir \nuniquement des object de la meme instance\n");
		}
		return pareil;
		
	}
	public List<Etre> ajouterEtreALeatoire(List<Etre> listAjout) throws Exception{
		
		if(! ListSansDefauts(listAjout)){
			return null;
		}
		List<Point> casesVides = new ArrayList<Point>();
		
		for (int i =0 ; i<map.length ; i++){
			for (int j=0 ; j<map[0].length ; j++){
				
				if (listAjout.get(0) instanceof Plante){
					
					if (map[i][j].getPlante()==null && !map[i][j].isObstacle()){
						casesVides.add(new Point(i,j));
					}
				}
				else if(listAjout.get(0) instanceof Animal){
					
					if (map[i][j].getAnimalPresent()==null && !map[i][j].isObstacle()){
						casesVides.add(new Point(i,j));
					}
				}
			}
		}
		Collections.shuffle(casesVides);
		List<Etre> lesEtrePlacer = new ArrayList<Etre>();
		int conteur=0;
		for (int i=0; i<listAjout.size() ; i++ ){
			
			if (conteur<casesVides.size()){
				
				//positionement de l'Etre
				Point tmp=casesVides.get(conteur);
				
				Etre EtreTmp=listAjout.get(i);
				
				EtreTmp.positionX=tmp.x;
				EtreTmp.positionY=tmp.y;
				
				lesEtrePlacer.add(EtreTmp);
				//placement sur la map de l'Etre
				
				if (listAjout.get(0) instanceof Plante){
					map[tmp.x][tmp.y].setPlante((Plante) EtreTmp);
				}
				else if(listAjout.get(0) instanceof Animal){
					map[tmp.x][tmp.y].setAnimalPresent((Animal) EtreTmp);
				}
				conteur++;
			}
			else{
				break;
			}
			
		}
		return lesEtrePlacer;
	}
	public void supprimer(List<Etre> listAsupprimer) throws Exception{
		
		boolean trouver;
		
		for (Etre a : listAsupprimer){
			trouver=false;
			
			for (int i =0 ; i<map.length ; i++){
				if (trouver){
					break;
				}
				for (int j=0 ; j<map[0].length ; j++){
					
					if (a == map[i][j].getAnimalPresent()){
						map[i][j].setAnimalPresent(null);
						trouver=true;
						break;
					}
					else if( a ==map[i][j].getPlante()){
						map[i][j].setPlante(null);
					}
				}
			}
		}
	}
	
	public List<Etre> unTour(){
		List<Etre> nouvellesPlantes = new ArrayList<Etre>();
		
		for (int i =0 ; i<map.length ; i++){
			for (int j=0 ; j<map[0].length ; j++){
				
				int valeur=map[i][j].valeurSel;
				
				if (valeur>0 && map[i][j].getPlante()==null){
					
					nouvellesPlantes.add(new Plante(i,j,false,valeur*10,10,3,valeur*5,6));// a VERIFIER
				}
			}
		}
		return nouvellesPlantes;
	}

	public int getCaseVide() {
		return caseVide;
	}

	public void setCaseVide(int caseVide) {
		this.caseVide = caseVide;
	}
	
	public void afficheShell(){
		System.out.println();
		System.out.println();
		System.out.print("/");
		for(int i=0 ; i<map[0].length; i++){
			System.out.print("——");
		}
		System.out.print(" \\");
		for (int i =0; i<map.length ; i++){
			
				System.out.println();
				for( int j=0 ; j<map[0].length; j++){
					if(j==0){System.out.print("|");}
					if(!map[i][j].isObstacle()){ // pas obstacle

						
						if (!(map[i][j].getAnimalPresent()==null)){
							
							if (map[i][j].getAnimalPresent() instanceof Mouton){
								if (!(map[i][j].getPlante()==null)){ 
									System.out.print(" M");
								}
								else{
									System.out.print(" m");		
								}
								
							}
							if (map[i][j].getAnimalPresent() instanceof Loup){
								if (!(map[i][j].getPlante()==null)){ 
									System.out.print(" L");
								}
								else{
									System.out.print(" l");
								}
									
							}
						}
						else {
							if (!(map[i][j].getPlante()==null)){ 
								System.out.print(" §");
							}
							else{System.out.print(" .");} // accessible et visible
						}
						
					}
					else{
						System.out.print(" #");// obstacle (pas accessible , pas visible)
					}
				}
				System.out.print(" |");
			}
		System.out.println("");
		System.out.print("\\");
		for(int i=0 ; i<map[0].length; i++){
			System.out.print("——");
		}
		System.out.print(" /");
	}
}
