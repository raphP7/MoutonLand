
public class Start {
	public static void main(String[] args) {
		
		
		Animal un = new Mouton(4,4);
		Terrain terrain = new Terrain(10,10);
		
		
		terrain.map[6][5].setObstacle(true);
		terrain.map[5][4].setObstacle(true);
		terrain.map[1][5].setObstacle(true);
		terrain.map[5][3].setObstacle(true);
		
		
			Case[][] tab=un.possible(terrain.map);;
			
			
			for (int i =0; i<tab.length ; i++){
				System.out.println();
				for( int j=0 ; j<tab[0].length; j++){
					if(!tab[i][j].isObstacle()){
						System.out.print(" 0");
					}
					else{
						System.out.print(" -");
					}
				}
			}
		}
}
