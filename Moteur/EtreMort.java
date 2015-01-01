package Moteur;

import Moteur.animauxCarnivores.Loup;
import Moteur.animauxHerbivores.Mouton;



public class EtreMort extends Etre {
	int valeurEnSel;
	int decompostion;
	
	public EtreMort (EtreVivant a){
		
		super(a.positionX , a.positionY);	
		
		if (a instanceof Plante ){
			this.decompostion=2;
			this.valeurEnSel=this.decompostion;
		}
		else if(a instanceof Mouton){
			this.decompostion=7;
			this.valeurEnSel=this.decompostion;
		}
		else if(a instanceof Loup){
			this.decompostion=13;
			this.valeurEnSel=this.decompostion;
		}
		else{
			this.decompostion=0;
			this.valeurEnSel=this.decompostion;
		}
		
	}
	 public boolean decompositionFini(){
		 this.decompostion--;
		 return this.decompostion<1;
	 }
}
