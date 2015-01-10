package Moteur.Intelligence;

import java.awt.Point;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.Stack;

import Moteur.Animal;
import Moteur.Carnivore;
import Moteur.Etre;
import Moteur.Herbivore;
import Moteur.Terrain.Case;


/**Class qui permet de : 
 * mettre a jour la vision d'un Animal.
 * mettre a jour ces emotions en fonction de ce qui l'entour.
 * recuperer un deplacement selon les emotions de l'animal
 * 
 * @author Raph
 *
 */
public class VisionEtDeplacement {
	
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
	private static void champObstacle( Case [][] tableauVision,int champDeVision) throws Exception{
		List<Point> coordonnesCasesEncoreVisible = new ArrayList<Point>();
		
		// listage de toutes les cases visibles de la vue de l'animal
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
			if(!backtrak(champDeVision, champDeVision, a, tableauVision,champDeVision)){
				
				// la case est invisible dans le champs de vision de l'animal
				tableauVision[a.x][a.y].setVisible(false);
				tableauVision[a.x][a.y].setAnimalPresent(null);
				tableauVision[a.x][a.y].setPlante(null);
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
	private static boolean backtrak(int x , int y , Point arriver,Case [][] tableauVision, int tailleMax){
		
		
		if(!tableauVision[x][y].isVisible()){
			return false;
		}
		
		if(arriver.x==x && arriver.y==y){
				return true;
		}
		
		if (arriver.x<x && arriver.y==y){
			if(backtrak(x-1 ,y ,arriver,tableauVision,tailleMax)){
				return true;
			}
		}
		if( arriver.y<y && arriver.x==x){
			if(backtrak(x ,y-1 ,arriver,tableauVision,tailleMax)){
				return true;
			}
		}
		if(arriver.x>x && arriver.y==y){
			if(backtrak(x+1 ,y ,arriver,tableauVision,tailleMax)){
				return true;
			}
		}
		if(arriver.y>y && arriver.x==x){
			if(backtrak(x ,y+1 ,arriver,tableauVision,tailleMax)){
				return true;
			}
		}
		if(arriver.x<x && arriver.y<y){
			if(backtrak(x-1 ,y-1 ,arriver,tableauVision,tailleMax)){
				return true;
			}
		}
		if(arriver.x>x && arriver.y<y){
			if(backtrak(x+1 ,y-1 ,arriver,tableauVision,tailleMax)){
				return true;
			}
		}
		if(arriver.x>x && arriver.y>y){
			if(backtrak(x+1 ,y+1 ,arriver,tableauVision,tailleMax)){
				return true;
			}
		}
		if(arriver.x<x && arriver.y>y){
			if(backtrak(x-1 ,y+1 ,arriver,tableauVision,tailleMax)){
				return true;
			}
		}
		return false;
	}

	private static boolean backtrakCheminAnimal(int x , int y , Point arriver,Case [][] tableauVision,Stack<Point> chemin, int tailleMax, Etre etreEnMouvement,int nbAnimal){
		
		if(nbAnimal>0){
			return false;
		}
		
		if(tableauVision[x][y].getAnimalPresent() != null && tableauVision[x][y].getAnimalPresent() != etreEnMouvement ){
			nbAnimal++;
		}
		
		if(!tableauVision[x][y].isVisible()){
			return false;
		}
		
		if(tableauVision[x][y].isObstacle()){
			return false;
			
		}
		
		if(arriver.x==x && arriver.y==y){
			if(chemin.size()<=tailleMax){
				if(chemin != null){chemin.push(new Point(x, y));}
				return true;
			}
		}
		
		if (arriver.x < x && arriver.y == y) {
			if (
					chemin.size() <= tailleMax
					&& backtrakCheminAnimal(x - 1, y, arriver, tableauVision, chemin,tailleMax,etreEnMouvement,nbAnimal)
					) {
				if(chemin != null){chemin.push(new Point(x, y));}
				return true;
			}
		}
		if( arriver.y<y && arriver.x==x){
			if(
					chemin.size() <= tailleMax
					&& backtrakCheminAnimal(x ,y-1 ,arriver,tableauVision, chemin,tailleMax,etreEnMouvement,nbAnimal)
					) {

				if(chemin != null){chemin.push(new Point(x, y));}
				return true;
			}
		}
		if(arriver.x>x && arriver.y==y){
			if(
					chemin.size() <= tailleMax
					&& backtrakCheminAnimal(x+1 ,y ,arriver,tableauVision, chemin,tailleMax,etreEnMouvement,nbAnimal)
					) {

				if(chemin != null){chemin.push(new Point(x, y));}
				return true;
			}
		}
		if(arriver.y>y && arriver.x==x){
			if(	
					chemin.size() <= tailleMax
					&& backtrakCheminAnimal(x ,y+1 ,arriver,tableauVision, chemin,tailleMax,etreEnMouvement,nbAnimal)
					) {

				if(chemin != null){chemin.push(new Point(x, y));}
				return true;
			}
		}
		if(arriver.x<x && arriver.y<y){
			if(		
					chemin.size() <= tailleMax
					&& backtrakCheminAnimal(x-1 ,y-1 ,arriver,tableauVision, chemin,tailleMax,etreEnMouvement,nbAnimal)
					) {

				if(chemin != null){chemin.push(new Point(x, y));}
				return true;
			}
		}
		if(arriver.x>x && arriver.y<y){
			if(	
					chemin.size() <= tailleMax
					&& backtrakCheminAnimal(x+1 ,y-1 ,arriver,tableauVision, chemin,tailleMax,etreEnMouvement,nbAnimal)
					) {

				if(chemin != null){chemin.push(new Point(x, y));}
				return true;
			}
		}
		if(arriver.x>x && arriver.y>y){
			if(
					chemin.size() <= tailleMax
					&& backtrakCheminAnimal(x+1 ,y+1 ,arriver,tableauVision, chemin,tailleMax,etreEnMouvement,nbAnimal)
					) {

				if(chemin != null){chemin.push(new Point(x, y));}
				return true;
			}
		}
		if(arriver.x<x && arriver.y>y){
			if(
					chemin.size() <= tailleMax
					&& backtrakCheminAnimal(x-1 ,y+1 ,arriver,tableauVision, chemin,tailleMax,etreEnMouvement,nbAnimal)
					) {

				if(chemin != null){chemin.push(new Point(x, y));}
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Calcul les case non visible A cause d'un et seulement un obstacle dans le champ de vision d'un animal
	 * @param x Position X de l'animal dans le tableau de vision 
	 * @param y Position Y de l'animal dans le tableau de vision
	 * @param tableauVision Le tableau de vision actuelde l'animal
	 * @param TaillechampsDeVision La taille du champs de vision de l'animal
	 * @return
	 */
	private static Case[][] ligneObstacle( int x ,int  y, Case [][] tableauVision, int TaillechampsDeVision){
		
		int centre = TaillechampsDeVision;

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

	private static int calculTailleVision(int champDeVision){
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
	public static Case[][] miseAjourVision(Point positionAnimal,int champDeVision ,Case [][] map) throws Exception{

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

		while(parcour<3){
			
			
			for (int i=positionAnimal.x-champDeVision ; i<=positionAnimal.x+champDeVision ;i++){
				for (int j=positionAnimal.y-champDeVision ; j<=positionAnimal.y+champDeVision ;j++){
					
					int visionActuelI=i+champDeVision-positionAnimal.x;
					int visionActuelJ=j+champDeVision-positionAnimal.y;
					
					if (i<0 || j<0 || i>=map.length || j>=map[0].length){//hors de la map

							tableauVision[visionActuelI][visionActuelJ].setObstacle(true);
					}
					else if(parcour ==1 && map[i][j].isObstacle()){//obstacle de visibilité dans le terrain
						
						tableauVision[visionActuelI][visionActuelJ].setObstacle(true);
							
						tableauVision=ligneObstacle(visionActuelI ,visionActuelJ, tableauVision,champDeVision);
						}
					
					/*pour tester les cases non visible  ATTENTION INUTILE pour le moment!
					if (parcour !=0 && !map[i][j].isVisible()){
							visionActuel[i+champDeVision-positionAnimal.x][j+champDeVision-positionAnimal.y].setVisible(false);
							visionActuel=ligneObstacle(i+champDeVision-positionAnimal.x ,j+champDeVision-positionAnimal.y , visionActuel,champDeVision);
						}
					*/
					else if(parcour==2){
						// la case est dans le champ de vision et n'est pas un obstacle
						// on recopie l'animal ou la plante qui est dessus
						
						if(tableauVision[visionActuelI][visionActuelJ].isVisible()){
								tableauVision[visionActuelI][visionActuelJ].setAnimalPresent(map[i][j].getAnimalPresent());
								tableauVision[visionActuelI][visionActuelJ].setPlante(map[i][j].getPlante());
						}
					}
					
				}
			}
		parcour++;
		}
		
		champObstacle(tableauVision,champDeVision);
	return tableauVision;
	
	}	
	
	private static void animalPresent(int x , int y,Case [][] tableauVision) throws Exception{
		
		if( tableauVision[x][y].getAnimalPresent()==null){
			String s="\nAttention il n'y a pas d'Animal sur la case \n["+x+"] ["+y+"] impossible de tester ce qu'il peut voir\n";
			throw new Exception(s);	
		}
		
	}
	
	/**
	 * Mise a jour des emotions en fonction de l'environement
	 * @param tableauVision la vision de l'animal
	 * @param ChampDeVision la taille du champ de vision de l'animal
	 * @return un nouveau tableau d'emotion
	 * @throws Exception
	 */
	public static Envie [] regarder(Case [][] tableauVision, int ChampDeVision) throws Exception{

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
				
				if (temp[i].getEmotion().equals(Emotion.PEUR)){
					
					if (animal instanceof Carnivore){
						
						if (nbCarnivore*5>nbHerbivore){
							//peur a 0
							temp[i].setValeur(0);
						}
						else{// un carnivor est apeurer seulement a partir de 5 herbivor
							//le ratio de carnivore/herbivore multiplier par un ratio 500 de courage pour le carnivore
							// le resultat est un pourcentage 
							temp[i].setValeur(100 - ((nbCarnivore/nbHerbivore)*500 ) );
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
				else if (temp[i].getEmotion().equals(Emotion.FAIM)){
					
					if (animal instanceof Carnivore){
						if(nbHerbivore>1){
							// voir un herbivore donne l'apetit a un carnivore
							//temp[i].setValeur(temp[i].getValeur()+5);
						}
					}
					else{
						if(nbPlante>1){
							// voir une plante donne l'apetit a un mouton
							//temp[i].setValeur(temp[i].getValeur()+5);
						}
					}
				}
				else if (temp[i].getEmotion().equals(Emotion.DEPLACEMENT)){
					
				int ratioCaseVide = (nBcaseOccuperParUnAnimal/nBcaseSansAnimal)*100;
				// si au moin 50% des cases autour de l'animal sont libre sa augmente de 5% sont envie de se deplacer pour le plaisir
				
					if(ratioCaseVide<50){
						temp[i].setValeur(temp[i].getValeur() +5);
					}
					else{
						temp[i].setValeur(temp[i].getValeur() -1);
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
	public static Stack<Point> deplacement(int x , int y,Case [][] map, Emotion emotion) throws Exception{// A FINIR
		// choisi un deplacement 
		//renvoi les coordonnées des point du deplacement 
		
		if(emotion==null){
			throw new Exception("Attention l'animal ne peut se deplacer sans Emotion");
		}
		
		Etre animal=map[x][y].getAnimalPresent();
		Case [][]  tableauVision =((Animal)animal).tableauVision;
		
		Stack<Point> chemin = new Stack<Point>();
		
		LinkedList<Emplacement> souvenirs=((Animal)animal).getSouvenirs();
		
		int champDeVision=((Animal)animal).getChampDeVision();
		
		LinkedList<Point> casesAccessible =listePointAccessible(tableauVision, animal , emotion);
		
		Random random = new Random();
		int alea;
		alea=random.nextInt(casesAccessible.size());
		Point arriver=casesAccessible.get(alea);
		
		
		switch(emotion){
			
			case DEPLACEMENT:
				break;
			case REPRODUCTION:
				break;
			case PEUR:
				break;
			case FAIM:
				
				// un herbivore qui a faim et qui se trouve sur une plante reste dessus pour manger
				if(animal instanceof Herbivore){
					if(map[animal.positionX][animal.positionY].getPlante()!=null){
						arriver=new Point(champDeVision,champDeVision);
					}
				}
				
				break;
		
		}
		
		backtrakCheminAnimal(champDeVision, champDeVision, arriver, tableauVision, chemin, champDeVision,animal,0);
		return chemin;
		
	}
	
	/**
	 * Trouve toutes les case accessible a un animal
	 * @param tableauVision Le tableau de la vision de l'animal
	 * @param animal L'animal qui cherche a se deplacer
	 * @return une liste des case accessible a l'animal a ce tour
	 */
	private static LinkedList<Point> listePointAccessible(Case [][] tableauVision , Etre animal ,Emotion emotion){
	
	LinkedList<Point> casesAccessible = new LinkedList<Point>();
	int champDeVision=((Animal)animal).getChampDeVision();
	Stack<Point> chemin = new Stack<Point>();
		
		//Met les cases visible dans la liste
		int parcour=0;
		while (parcour<2){
		for (int i = 0; i < tableauVision.length; i++) {
			for (int j = 0; j < tableauVision[0].length; j++) {
				Case tmp = tableauVision[i][j];
				Point point = new Point(i,j);
				if(parcour==0){
					
						if (tmp.isVisible()) {

							if (animal instanceof Herbivore) {
								// un herbivore ne peut aller que sur une case sans animal deja present
								if (tmp.getAnimalPresent() == null || tmp.getAnimalPresent() == animal) {} 
								else {tmp.setVisible(false);}
								
							}
							else{

								if (tmp.getAnimalPresent() == null || tmp.getAnimalPresent() == animal) {}
								// un carnivor peut aller sur la case d'un herbivor pour le manger
								else if(emotion==Emotion.FAIM && tmp.getAnimalPresent() instanceof Herbivore){}
								
								else {tmp.setVisible(false);}
							}
						}
				
			}
			if(parcour==1){
				chemin.clear();
					if(backtrakCheminAnimal(champDeVision, champDeVision, point, tableauVision, chemin, champDeVision,animal,0))
					{
						casesAccessible.add(new Point(i, j));
					}
				}
			}
		}
		parcour++;
		}
		return casesAccessible;
	}
	
}
