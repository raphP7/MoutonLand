package Affichage;

import Moteur.*;

public class tets {

	public static void main(String[] args) throws Exception {
		Moteur a = new Moteur();
		a.CreerAlea("LOUP", 10);
		a.CreerAlea("MOUTON", 3);
		//a.simulation();
		
		Case[][] tab = a.leTerrain.map;
		
		
		for (int i =0; i<tab.length ; i++){
			
			System.out.println();
			for( int j=0 ; j<tab[0].length; j++){

				
				if(!tab[i][j].isObstacle()){ // pas obstacle
					
					if (!(tab[i][j].animalPresent==null)){
						
						if (tab[i][j].animalPresent instanceof Mouton){
							System.out.print(" M");
						}
						if (tab[i][j].animalPresent instanceof Loup){
								System.out.print(" L");
						}
						
					}
					
					else {
					System.out.print(" |"); // accessible et visible
					}
					
				}
				else{
					System.out.print(" -");// obstacle (pas accessible , pas visible)
				}
			}
		}
		
		
		
	}
}
