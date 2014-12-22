package Affichage;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

import Moteur.Loup;
import Moteur.Mouton;
import Moteur.Terrain.Case;

public class AffichageTerrain extends JPanel {
	private BufferedImage MoutonTerre=null;
	private BufferedImage MoutonHerbe=null;
	private BufferedImage LoupTerre=null;
	private BufferedImage LoupHerbe=null;
	private BufferedImage Terre=null;
	private BufferedImage Herbe=null;
	private BufferedImage Obstacle=null;
	AffichageTerrain(int w,int h){
		try {
		    MoutonTerre = ImageIO.read(this.getClass().getClassLoader().getResource("images/MoutonTerre.png"));
	    	MoutonHerbe = ImageIO.read(this.getClass().getClassLoader().getResource("images/MoutonHerbe.png"));
	    	LoupTerre= ImageIO.read(this.getClass().getClassLoader().getResource("images/LoupTerre.png"));
	    	LoupHerbe=ImageIO.read(this.getClass().getClassLoader().getResource("images/LoupHerbe.png"));
	    	Terre= ImageIO.read(this.getClass().getClassLoader().getResource("images/Terre.png"));
	    	Herbe=ImageIO.read(this.getClass().getClassLoader().getResource("images/Herbe.png"));
	    	Obstacle=ImageIO.read(this.getClass().getClassLoader().getResource("images/Obstacle.png"));
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
				if(a.isObstacle()){
					g.drawImage(Obstacle,i*w, j*h, w, h,this);
				}
				else{
					if(a.getPlante()!=null){
						if(a.getAnimalPresent() instanceof Loup){
							//affiche loupherbe
							g.drawImage(LoupHerbe,i*w, j*h, w, h,this);
						}
						else if(a.getAnimalPresent() instanceof Mouton){
							//affiche moutoherbe
							g.drawImage(MoutonHerbe,i*w, j*h, w, h,this);
						}
						else{
							//afficher herbe.
							g.drawImage(Herbe,i*w, j*h, w, h,this);
						}
					}
					else{
						if(a.getAnimalPresent() instanceof Loup){
							//affiche loupTerre
							g.drawImage(LoupTerre,i*w, j*h, w, h,this);
						}
						else if(a.getAnimalPresent() instanceof Mouton){
							//affiche moutoTerre
							g.drawImage(MoutonTerre,i*w, j*h, w, h,this);
						}
						else{
							//afficher herbe.
							g.drawImage(Terre,i*w, j*h, w, h,this);
						}
					}
				}
			}
	}
}
