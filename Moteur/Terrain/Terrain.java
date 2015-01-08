package Moteur.Terrain;
import java.awt.Point;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

import Moteur.Animal;
import Moteur.Etre;
import Moteur.Plante;
import Moteur.animauxCarnivores.Loup;
import Moteur.animauxHerbivores.Mouton;

/**
 * Pour la construction du terrain 
 * l'ajout aleatoire des etres sur le terrain
 * un affichage dans le shell du terrain
 * 
 * @author Raph
 *
 */
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
	class Position{
	    private int[] position;
	    Position(int x,int y){
	        position=new int[2];
	        position[0]=x;
	        position[1]=y;
	    }
	    @Override
	    public boolean equals(Object obj) {
	        return this.position[0]==((Position)obj).position[0] && this.position[1]==((Position)obj).position[1];
	    }
	    @Override
	    public int hashCode() {
	        return this.position[0]*2+this.position[1]*3;
	    }
	}
	public Terrain(int x , int y , int obstacles) throws Exception{
		
		class NombreObstacle extends Exception{
		    public NombreObstacle(){
		        System.out.println("Vous voulez mettre plus d'obstacle que de nombre de case");
		    }
		}
		
	if(obstacles>=(x*y)){//Si il a plus d'obstable que de case.
	throw new NombreObstacle();//Je leve une exeption
	}
	int abscisse,ordonnee;
	Random choix=new Random();

	Set<Position> ensemblePossible=new HashSet<Position>();//Ensemble ou je met toute les case possiblement visible.
	map=new Case[x][y];//Initialisation des ma map a la taille x,y.
	for(int i=0;i<x;i++){
	for(int j=0;j<y;j++){
	map[i][j]=new Case();
	map[i][j].setObstacle(true);//J'initialise toute mes case comme inaccessible.
	}
	}

	map[(abscisse=choix.nextInt(x))][(ordonnee=choix.nextInt(y))].setObstacle(false);//Je libere une case au hazard
	//System.out.println(abscisse+" "+ordonnee);
	//J'ajoute toute les nouvelle possibilité des cases potentiellement libre.
	this.ajouterPossibilite(abscisse, ordonnee,x,y, ensemblePossible);
	for(int i=0;i<(x*y-obstacles-1);i++){//Tant que j'ai pas liberer x*y-obstacle-1 de case.
	Position[] possible=ensemblePossible.toArray(new Position [0]);//Pour avoir un tableau avec tout mes case qui peuvent devenir accecible.
	Position b=possible[choix.nextInt(possible.length)];//prendre un element au hazard.
	map[b.position[0]][b.position[1]].setObstacle(false);//supprime l'obstacle.
	this.ajouterPossibilite(b.position[0],b.position[1],x,y, ensemblePossible);//on ajoute les nouvelle possibilité
	ensemblePossible.remove(b);
	//for(Position a:possible){
	//System.out.println(a.position[0]+" "+a.position[1]+" "+map[a.position[0]][a.position[1]].isObstacle());
	//}
	}
	}
	private void ajouterPossibilite(int abscisse,int ordonnee,int x,int y,Set<Position> ensemblePossible ){
		if(abscisse-1>=0){
				//System.out.println("Ajoute1");
			if(map[abscisse-1][ordonnee].isObstacle())
				ensemblePossible.add(new Position(abscisse-1,ordonnee));
			if(ordonnee-1>=0){
					//System.out.println("Ajoute2");
				if(map[abscisse-1][ordonnee-1].isObstacle())
					ensemblePossible.add(new Position(abscisse-1,ordonnee-1));
			}
			if(ordonnee+1<y){
					//System.out.println("Ajoute3");
				if(map[abscisse-1][ordonnee+1].isObstacle())
					ensemblePossible.add(new Position(abscisse-1,ordonnee+1));
			}
		}
		if(ordonnee-1>=0){
			//System.out.println("Ajoute4");
			if(map[abscisse][ordonnee-1].isObstacle())
			ensemblePossible.add(new Position(abscisse,ordonnee-1));
		}
		if(ordonnee+1<y){
			//System.out.println("Ajoute5");
			if(map[abscisse][ordonnee+1].isObstacle())
				ensemblePossible.add(new Position(abscisse,ordonnee+1));
		}
		if(abscisse+1<x){
			//System.out.println("Ajoute6");
			if(map[abscisse+1][ordonnee].isObstacle())
				ensemblePossible.add(new Position(abscisse+1,ordonnee));
			if(ordonnee-1>=0){
				//System.out.println("Ajoute7");
				if(map[abscisse+1][ordonnee-1].isObstacle())
					ensemblePossible.add(new Position(abscisse+1,ordonnee-1));
			}
			if(ordonnee+1<y){
					//System.out.println("Ajoute8");
				if(map[abscisse+1][ordonnee+1].isObstacle())
					ensemblePossible.add(new Position(abscisse+1,ordonnee+1));
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
	
	/**
	 * ajoute aleatoirement les etres sur le terrain 
	 * 
	 * @param listAjout la liste des etres a ajouter
	 * @return la liste des etres reelement ajouter sur le terrain ,
	 * @throws Exception
	 */
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
	/**
	 * Permet de supprimer du terrain les etres supprimer aleatoirement avec les boutons
	 * 
	 * @param listAsupprimer la liste des etres a supprimer du terrain
	 * @throws Exception
	 */
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
				
				int valeur=map[i][j].getValeurSel();
				
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
	
	
	/**
	 * affiche la vision d'un animal
	 */
	public void afficheVisionShell(){
		
		System.out.println("\n");
		for (int i =0; i<map.length ; i++){
			
			System.out.println();
			for( int j=0 ; j<map[0].length; j++){

				
				if(!map[i][j].isObstacle()){ // pas obstacle
					
					if (i==map.length/2 && j==map[0].length/2){
						System.out.print(" x");	 // centre
					}
					
					else if (!map[i][j].isVisible()){
						System.out.print(" I"); // pas visible
					}
					
					else{
					System.out.print(" ."); // accessible et visible
					}
					
				}
				else{
					System.out.print(" O");// obstacle (pas accessible , pas visible)
				}
			}
		}
		System.out.println("\n");
	}
	
	/**
	 * affiche le terrain dans le shell
	 */
	public void afficheShell(){
		System.out.println("\n");
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
							if ((map[i][j].getPlante()!=null)){ 
								System.out.print(" §");
							}
							else{System.out.print(" .");} // accessible et visible
						}
						
					}
					else if(map[i][j].isVisible() && !map[i][j].isObstacle()){
						
						System.out.print(" °");// obstacle (pas accessible , pas visible)
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
		System.out.println("\n");
	}
	public void ajouterBebePlante(Etre bebe) throws Exception {
		
		if(bebe.positionX<0 || bebe.positionX>=map.length || bebe.positionY<0 || bebe.positionY>=map.length ){
			throw new Exception("Attention , le bebe plante a sa position hors de la map");
		}
			
		if(this.map[bebe.positionX][bebe.positionY].getPlante()!=null){
			//il y a deja une plante
			Etre anciennePlante=this.map[bebe.positionX][bebe.positionY].getPlante();
			
			// la plante recupere sa valeur d'origine / sa maximum
			((Plante)anciennePlante).setValeur(((Plante)anciennePlante).getValmax());
		}
		
	}
}
