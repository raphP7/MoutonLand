package Moteur.Intelligence;

import java.awt.Point;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

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
	 * @param tableauVision tableau stockant la vue actuel de l'animal
	 * @param champDeVision La taille du champs de vision de l'animal , 1 = les 8 cases autour de lui , 2=les 25 cases autour de lui
	 * @throws	Erreur si une case avec obstacle essaye de stocker un animal ou une plante
	 */	
	private void champObstacle( Case [][] tableauVision,int champDeVision) throws Exception{
		List<Point> coordonnesCasesEncoreVisible = new ArrayList<Point>();
		
		// litage de toutes les cases visibles de la vue de l'animal
		for (int i =0; i<tableauVision.length ; i++ ){
			for (int j =0; j<tableauVision[0].length ; j++){
				if(tableauVision[i][j].isVisible()){
					coordonnesCasesEncoreVisible.add(new Point(i,j));
				}
			}
		}
		//Verification de chaque case visible si pas entoure d'obstacle 
		//et donc devenu invisible en realite
		for(Point a : coordonnesCasesEncoreVisible){
			if(!backtrak(champDeVision, champDeVision, a, tableauVision,null)){
				tableauVision[a.x][a.y].setVisible(false);
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
	 * @param arriver Point stockant les coordonnes x et y de la case a tester
	 * @param tableauVision le tableau de la visionActuel de l'animal
	 * @param chemin linkedList avec un chemin vers l'arriver
	 * 
	 * @return true si la case est vraiment visible ou false si elle est entouré d'obstacles
	 * 
	 */	
	private boolean backtrak(int x , int y , Point arriver,Case [][] tableauVision,LinkedList<Point> chemin){
		if(arriver.x==x && arriver.y==y){
			if(chemin!=null){chemin.add(new Point(x,y));}
			return true;
		}
		
		if(!tableauVision[x][y].isVisible()){
			return false;
		}
		if (arriver.x<x){
			if(backtrak(x-1 ,y ,arriver,tableauVision, chemin)){
				if(chemin!=null){chemin.add(new Point(x,y));}
				return true;
			}
		}
		if( arriver.y<y){
			if(backtrak(x ,y-1 ,arriver,tableauVision, chemin)){
				if(chemin!=null){chemin.add(new Point(x,y));}
				return true;
			}
		}
		if(arriver.x>x){
			if(backtrak(x+1 ,y ,arriver,tableauVision, chemin)){
				if(chemin!=null){chemin.add(new Point(x,y));}
				return true;
			}
		}
		if(arriver.y>y){
			if(backtrak(x ,y+1 ,arriver,tableauVision, chemin)){
				if(chemin!=null){chemin.add(new Point(x,y));}
				return true;
			}
		}
		if(arriver.x<x && arriver.y<y){
			if(backtrak(x-1 ,y-1 ,arriver,tableauVision, chemin)){
				if(chemin!=null){chemin.add(new Point(x,y));}
				return true;
			}
		}
		if(arriver.x>x && arriver.y<y){
			if(backtrak(x+1 ,y-1 ,arriver,tableauVision, chemin)){
				if(chemin!=null){chemin.add(new Point(x,y));}
				return true;
			}
		}
		if(arriver.x>x && arriver.y>y){
			if(backtrak(x+1 ,y+1 ,arriver,tableauVision, chemin)){
				if(chemin!=null){chemin.add(new Point(x,y));}
				return true;
			}
		}
		if(arriver.x<x && arriver.y>y){
			if(backtrak(x-1 ,y+1 ,arriver,tableauVision, chemin)){
				if(chemin!=null){chemin.add(new Point(x,y));}
				return true;
			}
		}
		return false;
	}

	private Case[][] ligneObstacle( int x ,int  y, Case [][] tableauVision, int TaillechampsDeVision){
		
		int centre = TaillechampsDeVision;
				
				//getChampDeVision();

		if (x == centre && y > centre) {// a droite 180degres
			for (int j = y + 1; j < tableauVision[0].length; j++) {
					tableauVision[centre][j].setVisible(false);
			}
		}

		else if (x == centre && y < centre) {// a gauche 0 degres
			for (int j = 0; j < y; j++) {
				tableauVision[centre][j].setVisible(false);
			}
		}
		else if (y == centre && x < centre) {// en haut 90 degres
			
				for (int i= 0; i < x; i++) {
						tableauVision[i][centre].setVisible(false);
					}
				}
		else if (y == centre && x >centre) {// en bas 270 degres
			
			for (int i= x + 1; i < tableauVision[0].length; i++) {
					tableauVision[i][centre].setVisible(false);
				}
			}
		else{// en diagonal

			 if (x<centre && y<centre){ // de 0 a 90 degres
					for (int i=x-1 ; i>=0 ; i--){
						for(int j=y-1; j>=0;j--){
							if (tableauVision[i+1][j+1].isObstacle() || !tableauVision[i+1][j+1].isVisible()){
								tableauVision[i][j].setVisible(false);
							}
						}
					}
				}
			 
			 else  if (x>centre && y>centre){ // de 180 a 270 degres
					for (int i=x+1; i<tableauVision.length ; i++){
						for(int j=y+1; j<tableauVision[0].length; j++){
							
							if (tableauVision[i-1][j-1].isObstacle() || !tableauVision[i-1][j-1].isVisible() ){
									tableauVision[i][j].setVisible(false);
								
							}
						}
					}
				}
			 else if (x<centre && y>centre){ // de 90 a 180 degres
				 for( int i =x-1 ; i>=0 ; i--){
					 for(int j=y+1 ;j<tableauVision[0].length  ;j++ ){
						 if (tableauVision[i+1][j-1].isObstacle() || !tableauVision[i+1][j-1].isVisible()){
							 tableauVision[i][j].setVisible(false);
						 }
					 }
				 }
			 }
			 

			 else if (x>centre && y<centre){ // de 270 a 0 degres
				 for( int i =x+1 ; i<tableauVision[0].length ; i++){
					 
					 for(int j=y-1 ; j>=0; j-- ){
						 if (tableauVision[i-1][j+1].isObstacle() || !tableauVision[i-1][j+1].isVisible()){
							 tableauVision[i][j].setVisible(false);
						 }
					 }
				 }
			 }
			 
			 
			 
		}
		return tableauVision;
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
		
		Case [][] tableauVision = new Case[taille][taille]; // redefinition de la taille de la visionActuel
		
		for (int i = 0; i <tableauVision.length; i++) {
			for (int j = 0; j < tableauVision[0].length; j++) {
				tableauVision[i][j]=new Case(); //initialise
				
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

							tableauVision[visionActuelI][visionActuelJ].setObstacle(true);
					}
					else if(parcour !=0 && map[i][j].isObstacle()){//obstacle de visibilité dans le terrain
						
						tableauVision[visionActuelI][visionActuelJ].setObstacle(true);
							
						tableauVision=ligneObstacle(visionActuelI ,visionActuelJ, tableauVision,champDeVision);
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
							if(tableauVision[visionActuelI][visionActuelJ].isVisible()){
								tableauVision[visionActuelI][visionActuelJ].setAnimalPresent(map[i][j].getAnimalPresent());
								tableauVision[visionActuelI][visionActuelJ].setPlante(map[i][j].getPlante());		
							}
						}
					}
					
				}
			}
		parcour++;
		}
		
		champObstacle(tableauVision,champDeVision);
		
		Terrain temp=new Terrain(10,10);
		temp.map=tableauVision;
		//System.out.println("Vision de l'animal");
		//temp.afficheVisionShell();
		
	return tableauVision;

	}	
	
	private void animalPresent(int x , int y,Case [][] tableauVision) throws Exception{
		
		if( tableauVision[x][y].getAnimalPresent()==null){
			String s="\nAttention il n'y a pas d'Animal sur la case \n["+x+"] ["+y+"] impossible de tester ce qu'il peut voir\n";
			throw new Exception(s);	
		}
		
	}
	
	/**
	 * 
	 * @param tableauVision la vision de l'animal
	 * @param ChampDeVision la taille du champ de vision de l'animal
	 * @return un nouveau tableau d'emotion
	 * @throws Exception
	 */
	public Envie [] regarder(Case [][] tableauVision, int ChampDeVision) throws Exception{// A FINIR
		//met a jour les emotions en fonction de l'environement
		
		int x=ChampDeVision;
		int y=ChampDeVision;
		
		animalPresent(x, y, tableauVision);// peut renvoyer une Exception
		
		Etre animal=tableauVision[x][y].getAnimalPresent();
		
		//securiter : la map est a la bonne taille par rapport au champ de vision de l'animal
		((Animal)animal).visionAutourDeThisIsGoodSize(tableauVision, ((Animal)animal).getChampDeVision());
		
		int nbCarnivore=0;
		int nbHerbivore=0;
		int nbPlante=0;
		int nbObstacle=0;
		int nBcaseOccuperParUnAnimal=0;
		int nBcaseSansAnimal=0;
		
		for (int i =0 ; i<tableauVision.length ; i++){
			for (int j=0 ; j<tableauVision[0].length ; j++){
				
				if(tableauVision[i][j].isObstacle()){
					nbObstacle++;
				}
				else if (tableauVision[i][j].isVisible()){
					
					if (tableauVision[i][j].getAnimalPresent()!=null){
						if(tableauVision[i][j].getAnimalPresent() instanceof Carnivore){
							nbCarnivore++;
							nBcaseOccuperParUnAnimal++;
						}
						else if(tableauVision[i][j].getAnimalPresent() instanceof Herbivore){
							nbHerbivore++;
							nBcaseOccuperParUnAnimal++;
						}
					}
					if(tableauVision[i][j].getPlante()!=null){
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
				else if (temp[i].getEmotion().getClass().equals(Emotion.DEPLACEMENT.getClass())){
					
				int ratioCaseVide = (nBcaseOccuperParUnAnimal/nBcaseSansAnimal)*100;
				// si au moin 50% des cases autour de l'animal sont libre sa augmente de 5% sont envie de se deplacer pour le plaisir
				
					if(ratioCaseVide<50){
						temp[i].setValeur(temp[i].getValeur() +5);
					}
			}
		}
		
		return temp;
		
	}
	
	/**
	 * Calcul le deplacement de l'animal
	 * @param x Position X de l'animal
	 * @param y Position Y de l'animal
	 * @param map Tableau de la map
	 * @return Une liste de point qui represente le chemin que va parcourir l'animal sur la map
	 * dont le dernier point est la position d'arriver
	 * @throws Exception si la variable emotionChoisiPourLeDeplacement de class est null;
	 */
	public LinkedList<Point> deplacement(int x , int y,Case [][] map) throws Exception{// A FINIR
		// choisi un deplacement 
		//renvoi les coordonnées des point du deplacement 
		//animalPresent(x, y, );
		
		Case [][]  tableauVision =((Animal)(map[x][y].getAnimalPresent())).tableauVision;
		if(this.emotionChoisiPourLeDeplacement==null){
			throw new Exception("Attention l'animal ne peut se deplacer sans Emotion");
		}
		
		LinkedList<Point> chemin = new LinkedList<Point>();
		Etre animal=map[x][y].getAnimalPresent();
		
		FileDeSouvenirs souvenirs=((Animal)animal).getMouvements();
		
		List<Point> casesAccessible = new LinkedList<Point>();
		
		for (int i = 0 ; i< tableauVision.length ; i++){
			for (int j =0 ; j<tableauVision[0].length ; j++){
				
				Case tmp =tableauVision[i][j];  
				
				if (tmp.isVisible()){
					if (animal instanceof Herbivore){
						// un herbivore ne peut aller que sur une case sans animal deja present
						if (tmp.getAnimalPresent()==null){
							casesAccessible.add(new Point(i,j));
						}
					}
					else{
						// un carnivor peut aller sur la case d'un herbivor le manger
						if(tmp.getAnimalPresent()==null || (tmp.getAnimalPresent() instanceof Herbivore)){
							casesAccessible.add(new Point(i,j));
						}
					}
				}
			}
		}
		
		//if(souvenirs.getTete()==null){
			//pas de souvenirs
			Random random = new Random();
			int alea=random.nextInt(casesAccessible.size());
			Point arriver=casesAccessible.get(alea);
			chemin.add(arriver);
			

			//System.out.println("arriver X sans conversion: "+arriver.x);
			//System.out.println("arriver Y sans conversion: "+arriver.y);
			
			souvenirs.ajouter(arriver.x, arriver.y, tableauVision);
			
			//if(backtrak(x, y, arriver, tableauVision, chemin)){
			//}
			
		//}
		//else{
			
			//for(Emplacement a : souvenirs){
			//}
			
			
		//}
		
			
		/*
		switch(this.emotionChoisiPourLeDeplacement){
		
		case DEPLACEMENT:
			System.out.println("DEPLACEMENT");
			break;
		case REPRODUCTION:
			System.out.println("REPRODUCTION");
			break;
		case PEUR:
			System.out.println("PEUR");
			break;
		case FAIM:
			System.out.println("FAIM");
			break;
		}
		
		*/

		return chemin;
		
	}
}
