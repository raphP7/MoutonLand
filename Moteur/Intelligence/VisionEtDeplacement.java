package Moteur.Intelligence;

import java.awt.Point;
import java.util.LinkedList;

import Moteur.Animal;
import Moteur.Etre;
import Moteur.Terrain.Case;

public class VisionEtDeplacement {
	
	public Emotion emotionChoisiPourLeDeplacement;
	
	public VisionEtDeplacement() {
		
	}
	public VisionEtDeplacement(Emotion emotion) {
		this.emotionChoisiPourLeDeplacement=emotion;
	}
	
	@Deprecated
	private void champObstacle( Case [][] cases) throws Exception{
		boolean changement=false;
		for (int i =0; i<cases.length ; i++ ){
			
			for (int j =0; j<cases[0].length ; j++){
				
				if (!cases[i][j].isObstacle()){
					int cmp=0;
					if(i-1>0){
						
						if (cases[i-1][j].isObstacle()){cmp++;}
					}
					if (j-1>0){
						
						if (cases[i][j-1].isObstacle()){cmp++;}
					}
					if (i+1<cases.length ){
	
						if (cases[i+1][j].isObstacle()){cmp++;}
						
					}
					if(j+1<cases[0].length){
						
						if (cases[i][j+1].isObstacle()){cmp++;}
					}
					if(cmp>2){
						
						System.out.print(i+"  ");
						System.out.println(j);
						cases[i][j].setObstacle(true);
						changement=true;
						
						try{
							for (int w =0; w<cases.length ; w++){
								System.out.println();
								for( int z=0 ; z<cases[0].length; z++){
									if(!cases[w][z].isObstacle()){
										System.out.print(" 0");
									}
									else{
										System.out.print(" -");
									}
								}
							}
							Thread.sleep(100);
							
						}
						catch(Exception e){
							
						}
						break;
					}
				}
			}
			
			if (changement){break;}
		}
		
		if (changement){
			System.out.println("recursif");
			champObstacle(cases);}
		
	}
	
	private Case[][] ligneObstacle( int x ,int  y, Case [][] visionActuel, int TaillechampsDeVision){
		
		int centre = TaillechampsDeVision;
				
				//getChampDeVision();

		if (x == centre && y > centre) {// a droite 180degres
			for (int j = y + 1; j < visionActuel[0].length; j++) {
					visionActuel[centre][j].setVisible(false);
			}
		}

		else if (x == centre && y < centre) {// a gauche 0 degres
			for (int j = 0; j < y; j++) {
				visionActuel[centre][j].setVisible(false);
			}
		}
		else if (y == centre && x < centre) {// en haut 90 degres
			
				for (int i= 0; i < x; i++) {
						visionActuel[i][centre].setVisible(false);
					}
				}
		else if (y == centre && x >centre) {// en bas 270 degres
			
			for (int i= x + 1; i < visionActuel[0].length; i++) {
					visionActuel[i][centre].setVisible(false);
				}
			}
		else{// en diagonal

			 if (x<centre && y<centre){ // de 0 a 90 degres
					for (int i=x-1 ; i>=0 ; i--){
						for(int j=y-1; j>=0;j--){
							if (visionActuel[i+1][j+1].isObstacle() || !visionActuel[i+1][j+1].isVisible()){
								visionActuel[i][j].setVisible(false);
							}
						}
					}
				}
			 
			 else  if (x>centre && y>centre){ // de 180 a 270 degres
					for (int i=x+1; i<visionActuel.length ; i++){
						for(int j=y+1; j<visionActuel[0].length; j++){
							
							if (visionActuel[i-1][j-1].isObstacle() || !visionActuel[i-1][j-1].isVisible() ){
									visionActuel[i][j].setVisible(false);
								
							}
						}
					}
				}
			 else if (x<centre && y>centre){ // de 90 a 180 degres
				 for( int i =x-1 ; i>=0 ; i--){
					 for(int j=y+1 ;j<visionActuel[0].length  ;j++ ){
						 if (visionActuel[i+1][j-1].isObstacle() || !visionActuel[i+1][j-1].isVisible()){
							 visionActuel[i][j].setVisible(false);
						 }
					 }
				 }
			 }
			 

			 else if (x>centre && y<centre){ // de 270 a 0 degres
				 for( int i =x+1 ; i<visionActuel[0].length ; i++){
					 
					 for(int j=y-1 ; j>=0; j-- ){
						 if (visionActuel[i-1][j+1].isObstacle() || !visionActuel[i-1][j+1].isVisible()){
							 visionActuel[i][j].setVisible(false);
						 }
					 }
				 }
			 }
			 
			 
			 
		}
		return visionActuel;
		
	}

	private int calculTailleVision(int champDeVision){
		return 1+champDeVision*2;
	}
	
	
/**
 * Renvoi un tableau avec la vue de la map depuis la position d'un animal ,
 * prend en compte le champ de vision de l'animal et le fait qu'il ne puisse voir a travers un obstacle
 * 
 * @author Raphael Auvert
 * 
 * @param positionAnimal Un Point avec les coordonnes x et y de l'animal sur la map
 * @param champDeVision La taille du champs de vision de l'animal , 1 = les 8 cases autour de lui , 2=les 25 cases autour de lui
 * @param map Le tableau a deux dimension representant la map de la simulation
 * @return la carte de la vision de l'animal
 * @throws	Erreur si une case avec obstacle essaye de stocker un animal ou une plante
 */	
	public  Case[][] miseAjourVision(Point positionAnimal,int champDeVision ,Case [][] map) throws Exception{
		
		
		
		int taille=calculTailleVision(champDeVision);
		Case [][] visionActuel = new Case[taille][taille]; // redefinition de la taille de la visionActuel
		
		for (int i = 0; i < visionActuel.length; i++) {
			for (int j = 0; j < visionActuel[0].length; j++) {
				visionActuel[i][j]=new Case(); //initialise
				
				//cases remise par default (sans obstacles et visible)
			}
		}
		int parcour=0;
		// 2 parcour
		// le premier pour trouver les obstacles limites de la vue ( dans les angles et bords de la map)
		// le deuxieme pour trouver les obstacles dans le champDeVision et leur appliquer des non visible
		while(parcour<2){
			for (int i=positionAnimal.x-champDeVision ; i<=positionAnimal.x+champDeVision ;i++){
				for (int j=positionAnimal.y-champDeVision ; j<=positionAnimal.y+champDeVision ;j++){
					
					int visionActuelI=i+champDeVision-positionAnimal.x;
					int visionActuelJ=j+champDeVision-positionAnimal.y;
					
					if (i<0 || j<0 || i>=map.length || j>=map[0].length){//hors de la map

							visionActuel[visionActuelI][visionActuelJ].setObstacle(true);
					}
					
					else if(parcour !=0 && map[i][j].isObstacle()){//obstacle de visibilité dans le terrain
						
						visionActuel[visionActuelI][visionActuelJ].setObstacle(true);
							
						visionActuel=ligneObstacle(visionActuelI ,visionActuelJ, visionActuel,champDeVision);
						
						}
					
					/*pour tester les cases non visible  ATTENTION INUTILE pour le moment!
					if (parcour !=0 && !map[i][j].isVisible()){
							visionActuel[i+champDeVision-positionAnimal.x][j+champDeVision-positionAnimal.y].setVisible(false);
							visionActuel=ligneObstacle(i+champDeVision-positionAnimal.x ,j+champDeVision-positionAnimal.y , visionActuel,champDeVision);
						}
					*/
					else{
						// la case est dans le champ de vision et n'est pas un obstacle
						// on recopie l'animal ou la plante qui est dessus
						if (parcour!=0){
							visionActuel[visionActuelI][visionActuelJ].setAnimalPresent(map[i][j].getAnimalPresent());
							visionActuel[visionActuelI][visionActuelJ].setPlante(map[i][j].getPlante());		
							
						}
					}
					
				}
			}
		parcour++;
		}
			
		//champObstacle(cases);
		
	return visionActuel;

	}	
	
	private void animalPresent(int x , int y,Case [][] map) throws Exception{
		
		if( map[x][y].getAnimalPresent()==null){
			String s="Attention il n'y a pas d'Animal sur la case ["+x+"] ["+y+"]";
			throw new Exception(s);	
		}
		
	}
	
	public Envie [] regarder(int x , int y,Case [][] map) throws Exception{// A FINIR
		//met a jour les emotions en fonction de l'environement
		
		animalPresent(x, y, map);// peut renvoyer une Exception
		
		Etre a=map[x][y].getAnimalPresent();
		
		Envie[] temp = ((Animal)a).getLesEnvies();
		
		return temp;
		
	}
	
	public LinkedList<Point> deplacement(int x , int y,Case [][] map) throws Exception{// A FINIR
		// choisi un deplacement 
		//renvoi les coordonnées des point du deplacement 
		animalPresent(x, y, map);
		
		LinkedList<Point> listeDePoint = new LinkedList<Point>();
		Etre a=map[x][y].getAnimalPresent();
		
		
		Envie[] temp = ((Animal)a).getLesEnvies();
		Point positionArriver=new Point(x,y);// POUR FAIRE DES TEST
		
		listeDePoint.add(positionArriver);
		return listeDePoint;
		
	}
}
