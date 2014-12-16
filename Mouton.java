
public class Mouton extends Animal{

	
	Mouton(int x , int y){
		super(x,y);
	}
	
	
	@Override
	public boolean mourir() {
		
		return false;
	}

	@Override
	public boolean manger() {
		
		return false;
	}
}
