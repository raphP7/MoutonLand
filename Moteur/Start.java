package Moteur;

import java.util.HashSet;
import java.util.Set;

class Start {
	
	public static void toot(Class b){
		System.out.println(b.getName());
		
		System.out.println(b.toString().contains("Animal"));
		
		
	}
	public static void main(String[] args) {
		
		toot(Mouton.class);
		
		String a ="LOUP";

		boolean aEstUnEtreVivant=false;
		
		for (Vivant vivantTemp : Vivant.values()){
			if (a.equals(vivantTemp.toString())){aEstUnEtreVivant=true;}
		}
		System.out.println(aEstUnEtreVivant);
		
		
		Animal un = new Mouton(6,6, false, 0, 0, 0, 0, 3, 0, 0);
		un.manger();
		
		Terrain terrain = new Terrain(10,10);
		
		//terrain.map[2][2].setVisible(false);
		
		terrain.map[7][2].setObstacle(true);
		terrain.map[6][5].setObstacle(true);
		terrain.map[5][4].setObstacle(true);
		terrain.map[7][7].setObstacle(true);
		terrain.map[5][2].setObstacle(true);
		
		
		
		
			Case[][] tab=un.miseAjourVision(terrain.map);;
			
			for (int i =0; i<tab.length ; i++){
				
				System.out.println();
				for( int j=0 ; j<tab[0].length; j++){

					
					if(!tab[i][j].isObstacle()){ // pas obstacle
						
						if (i==tab.length/2 && j==tab[0].length/2){
							System.out.print(" x");	 // centre
						}
						
						else if (!tab[i][j].isVisible()){
							System.out.print(" |"); // pas visible
						}
						
						else{
						System.out.print(" 0"); // accessible et visible
						}
						
					}
					else{
						System.out.print(" -");// obstacle (pas accessible , pas visible)
					}
				}
			}
			
			
			
			
			
			
			
			
			
			
			
		}
}