package Moteur.Intelligence;

import java.awt.Point;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import Moteur.Animal;
import Moteur.Carnivore;
import Moteur.Etre;
import Moteur.Herbivore;
import Moteur.Terrain.Case;
import Moteur.Terrain.Terrain;

public class VisionEtDeplacement {
	
	public Emotion emotionChoisiPourLeDeplacement;
	
	public VisionEtDeplacement() {
		
	}
	
	public VisionEtDeplacement(Emotion emotion) {
		this.emotionChoisiPourLeDeplacement=emotion;
	}
	
	/**
	 * 
	 * Calcul les champs d'obstacle de la vision de l'animal
	 * modifie le tableau "cases"
	 * 
	 * @author Raphael Auvert
	 * @param cases tableau stockant la vue actuel de l'animal
	 * @param champDeVision La taille du champs de vision de l'animal , 1 = les 8 cases autour de lui , 2=les 25 cases autour de lui
	 * @throws	Erreur si une case avec obstacle essaye de stocker un animal ou une plante
	 */	
	private void champObstacle( Case [][] cases,int champDeVision) throws Exception{
		List<Point> coordonnesCasesEncoreVisible = new ArrayList<Point>();
		
		// litage de toutes les cases visibles de la vue de l'animal
		for (int i =0; i<cases.length ; i++ ){
			for (int j =0; j<cases[0].length ; j++){
				if(cases[i][j].isVisible()){
					coordonnesCasesEncoreVisible.add(new Point(i,j));
				}
			}
		}
		//Verification de chaque case visible si pas entoure d'obstacle 
		//et donc devenu invisible en realite
		for(Point a : coordonnesCasesEncoreVisible){
			if(!backtrak(champDeVision, champDeVision, a, cases)){
				cases[a.x][a.y].setVisible(false);
			}
		}
	}

	/**
	 * Calcul si la case visible "arriver" est reelement visible depuis la position x et y de l'annimal
	 * 
	 * @author Raphael Auvert
	 * 
	 * @param x Position x de l'animal
	 * @param y Position y de l'animal
	 * @param arriver Point stockant les coordonnes x et y de la case visible
	 * @return true si la case est vraiment visible ou false si elle est entouré d'obstacles
	 * 
	 */	
	private boolean backtrak(int x , int y , Point arriver,Case [][] cases){
		if(arriver.x==x && arriver.y==y){
			return true;
		}
		
		if(!cases[x][y].isVisible()){
			return false;
		}
		if (arriver.x<x){
			if(backtrak(x-1 ,y ,arriver,cases)){
				return true;
			}
		}
		if( arriver.y<y){
			if(backtrak(x ,y-1 ,arriver,cases)){
				return true;
			}
		}
		if(arriver.x>x){
			if(backtrak(x+1 ,y ,arriver,cases)){
				return true;
			}
		}
		if(arriver.y>y){
			if(backtrak(x ,y+1 ,arriver,cases)){
				return true;
			}
		}
		if(arriver.x<x && arriver.y<y){
			if(backtrak(x-1 ,y-1 ,arriver,cases)){
				return true;
			}
		}
		if(arriver.x>x && arriver.y<y){
			if(backtrak(x+1 ,y-1 ,arriver,cases)){
				return true;
			}
		}
		if(arriver.x>x && arriver.y>y){
			if(backtrak(x+1 ,y+1 ,arriver,cases)){
				return true;
			}
		}
		if(arriver.x<x && arriver.y>y){
			if(backtrak(x-1 ,y+1 ,arriver,cases)){
				return true;
			}
		}
		return false;
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

	public int calculTailleVision(int champDeVision){
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
		
		for (int i = 0; i <visionActuel.length; i++) {
			for (int j = 0; j < visionActuel[0].length; j++) {
				visionActuel[i][j]=new Case(); //initialise
				
				//cases remise par default (sans obstacles et visible)
			}
		}
		int parcour=0;
		// il y a 2 parcour
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
							if(visionActuel[visionActuelI][visionActuelJ].isVisible()){
								visionActuel[visionActuelI][visionActuelJ].setAnimalPresent(map[i][j].getAnimalPresent());
								visionActuel[visionActuelI][visionActuelJ].setPlante(map[i][j].getPlante());		
							}
						}
					}
					
				}
			}
		parcour++;
		}
		
		champObstacle(visionActuel,champDeVision);
		
		Terrain temp=new Terrain(10,10);
		temp.map=visionActuel;
		System.out.println("Vision de l'animal");
		temp.afficheVisionShell();
		
	return visionActuel;

	}	
	
	private void animalPresent(int x , int y,Case [][] map) throws Exception{
		
		if( map[x][y].getAnimalPresent()==null){
			String s="\nAttention il n'y a pas d'Animal sur la case \n["+x+"] ["+y+"] impossible de tester ce qu'il peut voir\n";
			throw new Exception(s);	
		}
		
	}
	
	public Envie [] regarder(Case [][] map, int ChampDeVision) throws Exception{// A FINIR
		//met a jour les emotions en fonction de l'environement
		
		int x=ChampDeVision;
		int y=ChampDeVision;
		
		animalPresent(x, y, map);// peut renvoyer une Exception
		
		Etre animal=map[x][y].getAnimalPresent();
		
		//securiter : la map est a la bonne taille par rapport au champ de vision de l'animal
		((Animal)animal).visionAutourDeThisIsGoodSize(map, ((Animal)animal).getChampDeVision());
		
		int nbCarnivore=0;
		int nbHerbivore=0;
		int nbPlante=0;
		int nbObstacle=0;
		int nBcaseOccuperParUnAnimal=0;
		int nBcaseSansAnimal=0;
		
		for (int i =0 ; i<map.length ; i++){
			for (int j=0 ; j<map[0].length ; j++){
				
				if(map[i][j].isObstacle()){
					nbObstacle++;
				}
				else if (map[i][j].isVisible()){
					
					if (map[i][j].getAnimalPresent()!=null){
						if(map[i][j].getAnimalPresent() instanceof Carnivore){
							nbCarnivore++;
							nBcaseOccuperParUnAnimal++;
						}
						else if(map[i][j].getAnimalPresent() instanceof Herbivore){
							nbHerbivore++;
							nBcaseOccuperParUnAnimal++;
						}
					}
					if(map[i][j].getPlante()!=null){
						nbPlante++;
					}
					
					nBcaseSansAnimal++;
					
				}
				else{
					// la case n'est pas visible !!
				}
			}
		}
		
		Envie[] temp = ((Animal)animal).getLesEnvies();
		
		
		for (int i=0; i<temp.length ; i++){
				
				if (temp[i].getEmotion().getClass().equals(Emotion.PEUR.getClass())){
					
					if (animal instanceof Carnivore){
						
						if (nbCarnivore*5>nbHerbivore){
							//peur a 0
							temp[i].setValeur(0);
						}
						else{// un carnivor est apeurer seulement a partir de 5 herbivor
							//le ratio de carnivore/herbivore multiplier par un ratio 200 de courage pour le carnivore
							// le resultat est un pourcentage 
							temp[i].setValeur(100 - ((nbCarnivore/nbHerbivore)*200 ) );
						}
					}
					else{
						if (nbCarnivore>1){							
							temp[i].setValeur(nbCarnivore*50);
						}
						else{
							// l'herbivore ne voit pas de Carnivore a ce tour sa peur diminu de 10%
							temp[i].setValeur(temp[i].getValeur()-10);
						}
					}
				}
				else if (temp[i].getEmotion().getClass().equals(Emotion.FAIM.getClass())){
					
					if (animal instanceof Carnivore){
						if(nbHerbivore>1){
							// voir un herbivore donne l'apetit a un carnivore
							temp[i].setValeur(temp[i].getValeur()+5);
						}
					}
					else{
						if(nbPlante>1){
							// voir une plante donne l'apetit a un mouton
							temp[i].setValeur(temp[i].getValeur()+5);
						}
					}
				}
				else if (temp[i].getEmotion().getClass().equals(Emotion.DECPLACEMENT.getClass())){
					
				int ratioCaseVide = (nBcaseOccuperParUnAnimal/nBcaseSansAnimal)*100;
				// si au moin 50% des cases autour de l'animal sont libre sa augmente de 5% sont envie de se deplacer pour le plaisir
				
					if(ratioCaseVide<50){
						temp[i].setValeur(temp[i].getValeur() +5);
					}
			}
		}
		
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
