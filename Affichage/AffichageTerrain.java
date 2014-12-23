package Affichage;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

import Moteur.Loup;
import Moteur.Mouton;
import Moteur.Terrain.Case;

public class AffichageTerrain extends JPanel {
	
	Random random = new Random();
	private BufferedImage herbeLoupAdulteFemelle=null;
	private BufferedImage herbeLoupAdulteMal=null;
	private BufferedImage herbeLoupBebe=null;
	private BufferedImage herbeMoutonAdulteFemelle1=null;
	private BufferedImage herbeMoutonAdulteFemelle2=null;
	private BufferedImage herbeMoutonAdulteMal2=null;
	private BufferedImage herbeMoutonBebe1=null;
	private BufferedImage herbeMoutonBebe2=null;
	private BufferedImage herbeMoutonAdulteMal1=null;
	
	private BufferedImage terreLoupAdulteFemelle=null;
	private BufferedImage terreLoupAdulteMal=null;
	private BufferedImage terreLoupBebe=null;
	private BufferedImage terreMoutonAdulteFemelle1=null;
	private BufferedImage terreMoutonAdulteFemelle2=null;
	private BufferedImage terreMoutonAdulteMal2=null;
	private BufferedImage terreMoutonBebe1=null;
	private BufferedImage terreMoutonBebe2=null;
	private BufferedImage terreMoutonAdulteMal1=null;
	
	
	
	private BufferedImage terre=null;
	private BufferedImage herbe=null;
	private BufferedImage obstacle=null;
	
	AffichageTerrain(int w,int h){
		try {
			
			herbeLoupAdulteFemelle = ImageIO.read(this.getClass().getClassLoader().getResource("images/herbeLoupAdulteFemelle.jpg"));
			herbeLoupAdulteMal = ImageIO.read(this.getClass().getClassLoader().getResource("images/herbeLoupAdulteMal.jpg"));
			herbeLoupBebe= ImageIO.read(this.getClass().getClassLoader().getResource("images/herbeLoupBebe.jpg"));
			
			herbeMoutonAdulteMal1=ImageIO.read(this.getClass().getClassLoader().getResource("images/herbeMoutonAdulteMal1.jpg"));
			herbeMoutonAdulteMal2=ImageIO.read(this.getClass().getClassLoader().getResource("images/herbeMoutonAdulteMal2.jpg"));
			
			herbeMoutonAdulteFemelle1=ImageIO.read(this.getClass().getClassLoader().getResource("images/herbeMoutonAdulteFemelle1.jpg"));
			herbeMoutonAdulteFemelle2=ImageIO.read(this.getClass().getClassLoader().getResource("images/herbeMoutonAdulteFemelle2.jpg"));
			
			herbeMoutonBebe1=ImageIO.read(this.getClass().getClassLoader().getResource("images/herbeMoutonBebe1.jpg"));
			herbeMoutonBebe2=ImageIO.read(this.getClass().getClassLoader().getResource("images/herbeMoutonBebe2.jpg"));
			
			
			terreLoupAdulteFemelle = ImageIO.read(this.getClass().getClassLoader().getResource("images/terreLoupAdulteFemelle.jpg"));
			terreLoupAdulteMal = ImageIO.read(this.getClass().getClassLoader().getResource("images/terreLoupAdulteMal.jpg"));
			terreLoupBebe= ImageIO.read(this.getClass().getClassLoader().getResource("images/terreLoupBebe.jpg"));
			
			terreMoutonAdulteMal1=ImageIO.read(this.getClass().getClassLoader().getResource("images/terreMoutonAdulteMal1.jpg"));
			terreMoutonAdulteMal2=ImageIO.read(this.getClass().getClassLoader().getResource("images/terreMoutonAdulteMal2.jpg"));
			
			terreMoutonAdulteFemelle1=ImageIO.read(this.getClass().getClassLoader().getResource("images/terreMoutonAdulteFemelle1.jpg"));
			terreMoutonAdulteFemelle2=ImageIO.read(this.getClass().getClassLoader().getResource("images/terreMoutonAdulteFemelle2.jpg"));
			
			terreMoutonBebe1=ImageIO.read(this.getClass().getClassLoader().getResource("images/terreMoutonBebe1.jpg"));
			terreMoutonBebe2=ImageIO.read(this.getClass().getClassLoader().getResource("images/terreMoutonBebe2.jpg"));
			
	    	terre= ImageIO.read(this.getClass().getClassLoader().getResource("images/terre.jpg"));
	    	herbe=ImageIO.read(this.getClass().getClassLoader().getResource("images/herbe.jpg"));
	    	obstacle=ImageIO.read(this.getClass().getClassLoader().getResource("images/obstacle.jpg"));
	    	
		} 
		catch (IOException e) {
			System.out.println("Une image na pas etait trouver.");
	    }
		this.setPreferredSize(new Dimension((int)(w*0.72),(int)(h*0.90)));
	}
	@Override
	public void paint(Graphics g) {
		Case [][] map=Fenetre.moteur.leTerrain.map;
		int w=this.getWidth()/map.length;
		int h=this.getHeight()/map[0].length;
		for(int i=0;i<map.length;i++)
			for(int j=0;j<map[0].length;j++){
				Case a=map[i][j];
				int valRandom;
				if(a.isObstacle()){
					g.drawImage(obstacle,i*w, j*h, w, h,this);
				}	
				else{
					if(a.getPlante()!=null){
						if(a.getAnimalPresent() instanceof Loup){
							
							if(a.getAnimalPresent().isPuber()){
								if(a.getAnimalPresent().isFemelle()){
									g.drawImage(terreLoupAdulteFemelle,i*w, j*h, w, h,this);
								}else{
									g.drawImage(terreLoupAdulteMal,i*w, j*h, w, h,this);
								}
							}
							else{
								g.drawImage(terreLoupBebe,i*w, j*h, w, h,this);
							}
						}
						else if(a.getAnimalPresent() instanceof Mouton){
							if(a.getAnimalPresent().isPuber()){
								if(a.getAnimalPresent().isFemelle()){
									valRandom=random.nextInt(1);
									if(valRandom==0){
										g.drawImage(terreMoutonAdulteFemelle1,i*w, j*h, w, h,this);
									}else{
										g.drawImage(terreMoutonAdulteFemelle2,i*w, j*h, w, h,this);
										}
								}else{
									valRandom=random.nextInt(1);
									if(valRandom==0){
										g.drawImage(terreMoutonAdulteMal1,i*w, j*h, w, h,this);
									}
									else{
										g.drawImage(terreMoutonAdulteMal2,i*w, j*h, w, h,this);
									}
								}
							}
							else{
								valRandom=random.nextInt(1);
								if(valRandom==0){
									g.drawImage(terreMoutonBebe1,i*w, j*h, w, h,this);
								}else{
									g.drawImage(terreMoutonBebe2,i*w, j*h, w, h,this);
								}
								
							}	
						}
						else{
							g.drawImage(terre,i*w, j*h, w, h,this);
						}
					}
					else{

						if(a.getAnimalPresent() instanceof Loup){
							
							if(a.getAnimalPresent().isPuber()){
								if(a.getAnimalPresent().isFemelle()){
									g.drawImage(herbeLoupAdulteFemelle,i*w, j*h, w, h,this);
								}else{
									g.drawImage(herbeLoupAdulteMal,i*w, j*h, w, h,this);
								}
							}
							else{
								g.drawImage(herbeLoupBebe,i*w, j*h, w, h,this);
							}
						}
						else if(a.getAnimalPresent() instanceof Mouton){
							if(a.getAnimalPresent().isPuber()){
								if(a.getAnimalPresent().isFemelle()){
									
									valRandom=random.nextInt(1);
									if(valRandom==0){
										g.drawImage(herbeMoutonAdulteFemelle1,i*w, j*h, w, h,this);
									}
									else{
										g.drawImage(herbeMoutonAdulteFemelle2,i*w, j*h, w, h,this);
									}
									
								}else{
									
									valRandom=random.nextInt(1);
									if(valRandom==0){
										g.drawImage(herbeMoutonAdulteMal1,i*w, j*h, w, h,this);
									}
									else{
										g.drawImage(herbeMoutonAdulteMal2,i*w, j*h, w, h,this);
									}
								}
							}
							else{
								valRandom=random.nextInt(1);
								if(valRandom==0){
									g.drawImage(herbeMoutonBebe1,i*w, j*h, w, h,this);
								}
								else{
									g.drawImage(herbeMoutonBebe2,i*w, j*h, w, h,this);
								}
								
							}	
						}
						else{
							//afficher herbe.
							System.out.println("icic");
							g.drawImage(herbe,i*w, j*h, w, h,this);
						}
					}
				}
			}
	}
}
