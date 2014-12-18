
public class EtreMort extends Etre {
	int valeurEnSel;
	int decompostion;
	
	
	public EtreMort (EtreVivant a){
		
		super(a.positionX , a.positionY);
		
		if (a instanceof Plante ){
			this.valeurEnSel=this.decompostion=2;
			
		}
		else if(a instanceof Mouton){
			this.valeurEnSel=this.decompostion=7;
		}
		else if(a instanceof Loup){
			this.valeurEnSel=this.decompostion=13;
		}
		else{
			decompostion=0;
		}
		
	}
	 public boolean decompositionFini(){
		 this.decompostion--;
		 return this.decompostion<1;
	 }
}
