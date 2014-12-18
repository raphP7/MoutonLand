package Affichage;

import Moteur.*;

public class tets {

	public static void main(String[] args) throws Exception {
		Moteur a = new Moteur();
		a.CreerAlea("LOUP", 10);
		a.CreerAlea("MOUTON", 3);
		a.CreerAlea("PLANTE", 50);
		//a.simulation();
		
		Case[][] tab = a.leTerrain.map;
		
		
		for (int i =0; i<tab.length ; i++){
			
			System.out.println();
			for( int j=0 ; j<tab[0].length; j++){
				
				if(!tab[i][j].isObstacle()){ // pas obstacle

					
					if (!(tab[i][j].animalPresent==null)){
						
						if (tab[i][j].animalPresent instanceof Mouton){
							if (!(tab[i][j].plante==null)){ 
								System.out.print(" M");
							}
							else{
								System.out.print(" m");		
							}
							
						}
						if (tab[i][j].animalPresent instanceof Loup){
							if (!(tab[i][j].plante==null)){ 
								System.out.print(" L");
							}
							else{
								System.out.print(" l");
							}
								
						}
						
					}
					
					else {
						if (!(tab[i][j].plante==null)){ 
							System.out.print(" §");
						}
						else{System.out.print(" |");} // accessible et visible
					}
					
				}
				else{
					System.out.print(" -");// obstacle (pas accessible , pas visible)
				}
			}
		}
		
		
		
	}
}
