
public class EtreMort {
	int x;
	int y;
	int decompostion;
	
	 public EtreMort (Plante a){
		this.decompostion=2;
	}
	 public EtreMort (Mouton a){
		this.decompostion=7;
		}
	 public EtreMort (Loup a){
		 this.decompostion=13;	
		}
	 public boolean decompositionFini(){
		 this.decompostion--;
		 return this.decompostion<1;
	 }

}
