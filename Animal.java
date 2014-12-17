public abstract class Animal extends EtreVivant implements FonctionsDeBase {
	
	int force;
	int vitesse;
	
	Case[][] visionActuel;
	
	private int [] choix = new int [7];
	//0 = faim
	//1 = peur
	//2 = reproduction
	//3 = deplacement oisif
	
	public Animal(int x, int y){
		int nombreCases = 1+ champDeVision*2;
		this.visionActuel= new Case[nombreCases][nombreCases];
		this.positionX=x;
		this.positionY=y;
		this.toursEnVie=0;
		this.toursSansManger=0;
		this.immobile=0;
		this.nombreDeReproduction=0;
	}
	
	public void actualiserChoix(){
		
	}
	public void action(Case [][] map){
		
		actualiserChoix();
		
	}
	
	public void manger() {
		super.toursSansManger=0;
	}
	public void champObstacle( Case [][] cases){
		boolean changement=false;
		for (int i =0; i<cases.length ; i++ ){
			
			for (int j =0; j<cases[0].length ; j++){
				
				if (!cases[i][j].isObstacle()){
					int cmp=0;
					if(i-1>0){
						
						if (cases[i-1][j].isObstacle()){cmp++;}
					}
					if (j-1>0){
						
						if (cases[i][j-1].isObstacle()){cmp++;}
					}
					if (i+1<cases.length ){
	
						if (cases[i+1][j].isObstacle()){cmp++;}
						
					}
					if(j+1<cases[0].length){
						
						if (cases[i][j+1].isObstacle()){cmp++;}
					}
					if(cmp>2){
						
						System.out.print(i+"  ");
						System.out.println(j);
						cases[i][j].setObstacle(true);
						changement=true;
						
						try{
							
							
							for (int w =0; w<cases.length ; w++){
								System.out.println();
								for( int z=0 ; z<cases[0].length; z++){
									if(!cases[w][z].isObstacle()){
										System.out.print(" 0");
									}
									else{
										System.out.print(" -");
									}
								}
							}
							Thread.sleep(100);
							
						}
						catch(Exception e){
							
						}
						break;
					}
				}
			}
			
			if (changement){break;}
		}
		
		if (changement){
			System.out.println("recursif");
			champObstacle(cases);}
		
	}
	public void ligneObstacle( int x ,int  y){

		int centre = champDeVision;

		if (x == centre && y > centre) {// a droite 180degres
			for (int j = y + 1; j < visionActuel[0].length; j++) {
					visionActuel[centre][j].setVisible(false);
			}
		}

		else if (x == centre && y < centre) {// a gauche 0 degres
			for (int j = 0; j < y; j++) {
				visionActuel[centre][j].setVisible(false);
			}
		}
		else if (y == centre && x < centre) {// en haut 90 degres
			
				for (int i= 0; i < x; i++) {
						visionActuel[i][centre].setVisible(false);
					}
				}
		else if (y == centre && x >centre) {// en bas 270 degres
			
			for (int i= x + 1; i < visionActuel[0].length; i++) {
					visionActuel[i][centre].setVisible(false);
				}
			}
		else{// en diagonal

			 if (x<centre && y<centre){ // de 0 a 90 degres
					for (int i=x-1 ; i>=0 ; i--){
						for(int j=y-1; j>=0;j--){
							if (visionActuel[i+1][j+1].isObstacle() || !visionActuel[i+1][j+1].isVisible()){
								visionActuel[i][j].setVisible(false);
							}
						}
					}
				}
			 
			 else  if (x>centre && y>centre){ // de 180 a 270 degres
					for (int i=x+1; i<visionActuel.length ; i++){
						for(int j=y+1; j<visionActuel[0].length; j++){
							
							if (visionActuel[i-1][j-1].isObstacle() || !visionActuel[i-1][j-1].isVisible() ){
									visionActuel[i][j].setVisible(false);
								
							}
						}
					}
				}
			 else if (x<centre && y>centre){ // de 90 a 180 degres
				 for( int i =x-1 ; i>=0 ; i--){
					 for(int j=y+1 ;j<visionActuel[0].length  ;j++ ){
						 if (visionActuel[i+1][j-1].isObstacle() || !visionActuel[i+1][j-1].isVisible()){
							 visionActuel[i][j].setVisible(false);
						 }
					 }
				 }
			 }
			 

			 else if (x>centre && y<centre){ // de 270 a 0 degres
				 for( int i =x+1 ; i<visionActuel[0].length ; i++){
					 
					 for(int j=y-1 ; j>=0; j-- ){
						 if (visionActuel[i-1][j+1].isObstacle() || !visionActuel[i-1][j+1].isVisible()){
							 visionActuel[i][j].setVisible(false);
						 }
					 }
				 }
			 }
			 
			 
			 
		}
			
		
	}
	public  Case[][] possible(Case [][] map){
		
		for (int i = 0; i < visionActuel.length; i++) {
			for (int j = 0; j < visionActuel[0].length; j++) {
				visionActuel[i][j]=new Case(); // cases remise par default (sans obstacles et visible)
			}
		}
		int parcour=0;
		while(parcour<2){
			for (int i=positionX-champDeVision ; i<=positionX+champDeVision ;i++){
				for (int j=positionY-champDeVision ; j<=positionY+champDeVision ;j++){
					
					
					//if (cases[i+champDeVision-positionX][j+champDeVision-positionY].getModif()==2){ // la case n'a pas encore été invalidé
					
					if (i<0 || j<0 || i>=map.length || j>=map[0].length){//hors du tableau
							
							visionActuel[i+champDeVision-positionX][j+champDeVision-positionY].setObstacle(true);
							
					}
					
					else if(parcour !=0 && map[i][j].isObstacle()){//obstacle de visibilité dans le terrain
						
						visionActuel[i+champDeVision-positionX][j+champDeVision-positionY].setObstacle(true);
							
						ligneObstacle(i+champDeVision-positionX ,j+champDeVision-positionY);
							
						}
						// juste pour test
					else if (parcour !=0 && !map[i][j].isVisible()){
							visionActuel[i+champDeVision-positionX][j+champDeVision-positionY].setVisible(false);
							ligneObstacle(i+champDeVision-positionX ,j+champDeVision-positionY);
						}
						}
					}
			parcour++;
				}
			
		//champObstacle(cases);
		
		return visionActuel;
		
	}

}
