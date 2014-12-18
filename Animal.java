
public abstract class Animal extends EtreVivant implements FonctionsDeBase {
	
	int force;
	int vitesse;
	FileDeSouvenirs mouvements;
	Case[][] visionActuel;
	
	private Envie [] lesEnvies;// voir enum Emotion
	
	public Animal(int x, int y, boolean femelle, int esperenceDeVie,
			int nbToursPourDevenirPuber, int maxReproduction,
			int esperenceSansManger, int champDeVision, int force, int vitesse) {
		
		super(x, y, femelle, esperenceDeVie, nbToursPourDevenirPuber,
				maxReproduction, esperenceSansManger, champDeVision);
		
		this.force=force;
		this.vitesse=vitesse;
		//this.deplacements = new <Point>();
		int nombreCases = 1+ this.getChampDeVision()*2;
		this.visionActuel= new Case[nombreCases][nombreCases];
		
		this.toursSansManger=0;
		this.immobile=0;
		this.nombreDeReproduction=0;		
		
		//initialisation du tableau lesEnvies avec des objects Envie qui sont listé par la Enum Emotion
		
		this.lesEnvies=new Envie[Emotion.values().length];
		int i=0;
		for (Emotion a : Emotion.values()){
			this.lesEnvies[i]=new Envie(a,0);
			i++;
		}
		//initialisation de la file de souvenir avec la position actuel
		this.mouvements=new FileDeSouvenirs(10, x , y , visionActuel) {
		};
		
	}
	
	public void actualiserVariables(){
		this.incrementeToursEnVie();
		
	}

	public boolean reproduction(Animal b){
		if (b.isFemelle() == this.isFemelle()){// les animaux sont de meme sexe
			if(this.maxReproduction>this.nombreDeReproduction && b.maxReproduction>this.maxReproduction){// les animaux peuvent se reproduire
				return true;
			}
			else return false;
		}
		else return false;
		
	}
	
	public boolean action(Case [][] map){
		
		if (this.mort()){
			return false;
		}
		this.miseAjourVision(map);
		
		
		actualiserVariables();
		
		this.manger(map);
		return true;
	}
	
	public void manger(Case[][] map) {
		
		map[positionX][positionY].plante.getValeur();
		
		toursSansManger=0;
		
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

		int centre = getChampDeVision();

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
	public  Case[][] miseAjourVision(Case [][] map){
		
		for (int i = 0; i < visionActuel.length; i++) {
			for (int j = 0; j < visionActuel[0].length; j++) {
				visionActuel[i][j]=new Case(); // cases remise par default (sans obstacles et visible)
			}
		}
		int parcour=0;
		while(parcour<2){
			for (int i=positionX-getChampDeVision() ; i<=positionX+getChampDeVision() ;i++){
				for (int j=positionY-getChampDeVision() ; j<=positionY+getChampDeVision() ;j++){
					
					
					//if (cases[i+champDeVision-positionX][j+champDeVision-positionY].getModif()==2){ // la case n'a pas encore été invalidé
					
					if (i<0 || j<0 || i>=map.length || j>=map[0].length){//hors du tableau
							
							visionActuel[i+getChampDeVision()-positionX][j+getChampDeVision()-positionY].setObstacle(true);
					}
					
					else if(parcour !=0 && map[i][j].isObstacle()){//obstacle de visibilité dans le terrain
						
						visionActuel[i+getChampDeVision()-positionX][j+getChampDeVision()-positionY].setObstacle(true);
							
						ligneObstacle(i+getChampDeVision()-positionX ,j+getChampDeVision()-positionY);
							
						}
						// juste pour test
					else if (parcour !=0 && !map[i][j].isVisible()){
							visionActuel[i+getChampDeVision()-positionX][j+getChampDeVision()-positionY].setVisible(false);
							ligneObstacle(i+getChampDeVision()-positionX ,j+getChampDeVision()-positionY);
						}
						}
					}
			parcour++;
				}
			
		//champObstacle(cases);
		
		return visionActuel;
		
	}

}
