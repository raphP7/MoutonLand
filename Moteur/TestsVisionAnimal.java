package Moteur;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

import Moteur.Intelligence.Emplacement;
import Moteur.Intelligence.FileDeSouvenirs;
import Moteur.Intelligence.VisionEtDeplacement;
import Moteur.Terrain.Terrain;


class TestsVisionAnimal {
	
	public static void toot(Class<?> b){
		System.out.println(b.getName());		
		System.out.println(b.toString().contains("Animal"));
		
	}
	public static void main(String[] args) throws Exception {
	
		
		
		List<Etre> listAjout =new ArrayList<Etre>();
		Etre aa =new Plante(0,0,false,100,100,100,1000,10);
		Etre aaa =new Mouton(0,0,false,100,100,100,1000, 0, 0, 0);
		listAjout.add(aa);
		listAjout.add(aaa);
		
		if (listAjout.get(0) instanceof Plante){
			System.out.println("une plante");
		}
		Etre temporaire=listAjout.get(0);
		boolean different=false;
		
		for(Etre etre : listAjout){
			if (!temporaire.getClass().equals(etre.getClass())){
				different=true;
			}
		}
		System.out.println("different ? " +different);
		
		toot(Mouton.class);
		
		String a ="LOUP";

		boolean aEstUnEtreVivant=false;
		
		for (Vivant vivantTemp : Vivant.values()){
			if (a.equals(vivantTemp.toString())){aEstUnEtreVivant=true;}
		}
		System.out.println(aEstUnEtreVivant);
		
		
		Animal un = new Mouton(5,5, false, 0, 0, 0, 0, 3, 0, 0);
		un.setChampDeVision(10);
		//un.manger();
		
		Terrain terrain = new Terrain(11,10);
		
		terrain.map[5][5].setAnimalPresent(un);
		
		terrain.map[1][0].setObstacle(true);
		terrain.map[6][4].setObstacle(true);
		 
		terrain.map[7][2].setObstacle(true);
		terrain.map[6][5].setObstacle(true);
		terrain.map[5][4].setObstacle(true);
		terrain.map[2][4].setObstacle(true);
		terrain.map[7][7].setObstacle(true);
		terrain.map[6][7].setObstacle(true);
		
		terrain.map[7][6].setObstacle(true);
		terrain.map[5][2].setObstacle(true);
		
		terrain.map[7][5].setObstacle(true);
		
		terrain.afficheShell();
		terrain.map=new VisionEtDeplacement().miseAjourVision(new Point(((Animal)un).positionX,((Animal)un).positionY),((Animal)un).getChampDeVision(),terrain.map);
			
		terrain.afficheVisionShell();
		
		FileDeSouvenirs test=((Animal)un).getMouvements();
		
		test.ajouter(10, 10, terrain.map);
		test.ajouter(10, 10, terrain.map);
		
		System.out.println(test.taillereele());
		
		for(Emplacement temp:test){
			try{
				Thread.sleep(1000);
				
				if(temp!=null){System.out.println(temp.toString());}
			}
			catch(Exception e){
				
			}
			
		}
		
			
		}
}