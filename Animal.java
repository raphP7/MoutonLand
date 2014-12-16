public abstract class Animal extends EtreVivant{
	
	int force;
	int vitesse;
	
	private int [] choix = new int [7];
	//0 = faim
	//1 = peur
	//2 = reproduction
	//3 = deplacement oisif
	
	public Animal(int x, int y){
		this.positionX=x;
		this.positionY=y;
		this.toursEnVie=0;
		this.toursSansManger=0;
		this.immobile=0;
		this.nombreDeReproduction=0;
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
	public void ligneObstacle( Case [][] cases , int x ,int  y){

		int centre = champDeVision;

		if (x == centre && y > centre) {// a droite 180degres
			for (int j = y + 1; j < cases[0].length; j++) {
					cases[centre][j].setObstacle(true);
			}
		}

		else if (x == centre && y < centre) {// a gauche 0 degres
			for (int j = 0; j < y; j++) {
				cases[centre][j].setObstacle(true);
			}
		}
		else if (y == centre && x < centre) {// en haut 90 degres
			
				for (int i= 0; i < x; i++) {
						cases[i][centre].setObstacle(true);
					}
				}
		else if (y == centre && x >centre) {// en bas 270 degres
			
			for (int i= x + 1; i < cases[0].length; i++) {
					cases[i][centre].setObstacle(true);
				}
			}
		else{// en diagonal

			 if (x<centre && y<centre){ // de 0 a 90 degres
					for (int i=x-1 ; i>=0 ; i--){
						for(int j=y-1; j>=0;j--){
							if (cases[i+1][j+1].isObstacle()){
								cases[i][j].setObstacle(true);;
							}
						}
					}
				}
			 
			 else  if (x>centre && y>centre){ // de 180 a 270 degres
					for (int i=x+1; i<cases.length ; i++){
						for(int j=y+1; j<cases[0].length; j++){
							if (cases[i-1][j-1].isObstacle()){
								cases[i][j].setObstacle(true);;
							}
						}
					}
				}
			 else if (x<centre && y>centre){ // de 90 a 180 degres
				 for( int i =x-1 ; i>=0 ; i--){
					 for(int j=y+1 ;j<cases[0].length  ;j++ ){
						 if (cases[i+1][j-1].isObstacle()){
							 cases[i][j].setObstacle(true);
						 }
					 }
				 }
			 }
			 

			 else if (x>centre && y<centre){ // de 270 a 0 degres
				 for( int i =x+1 ; i<cases[0].length ; i++){
					 
					 for(int j=y-1 ; j>=0; j-- ){
						 if (cases[i-1][j+1].isObstacle()){
							 cases[i][j].setObstacle(true);;
						 }
					 }
				 }
			 }
			 
			 
			 
		}
			
		
	}
	public  Case[][] possible(Case [][] map){
		
		int nombreCases = 1+ champDeVision*2;
		
		Case[][] cases = new Case[nombreCases][nombreCases];
		 
		//0 innaccesible
		//1 accessible
		
		for (int i = 0; i < cases.length; i++) {

			for (int j = 0; j < cases[0].length; j++) {
				cases[i][j]=new Case(false); // accessible
			}
		}
		
		for (int i=positionX-champDeVision ; i<=positionX+champDeVision ;i++){
			for (int j=positionY-champDeVision ; j<=positionY+champDeVision ;j++){
				
				if (cases[i+champDeVision-positionX][j+champDeVision-positionY].getModif()==1){ // la case n'a pas encore été invalidé
					
					if (i<0 || j<0 || i>=map.length || j>=map[0].length){//hors du tableau

						cases[i+champDeVision-positionX][j+champDeVision-positionY].setObstacle(true);
					}
					
					else if(map[i][j].isObstacle()){//obstacle de visibilité dans le terrain
						
						cases[i+champDeVision-positionX][j+champDeVision-positionY] = new Case(true);
						
						ligneObstacle(cases,i+champDeVision-positionX ,j+champDeVision-positionY);
						
					}
					
				}
				
			}
			
		}
		//champObstacle(cases);
		
		return cases;
		
	}
		
	
	
}
