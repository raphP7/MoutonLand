
public class Terrain {
	
	int x;
	int y;

	public Case [][] map;
	
	public Terrain(int x , int y){
		this.x=x;
		this.y=y;
		this.map= new Case[x][y];
		for (int i =0 ; i<map.length ; i++){
			for (int j=0 ; j<map[0].length ; j++){
				map[i][j]=new Case(false);
			}
		}
	}
}
