package Affichage;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.RenderingHints;
import java.util.Arrays;

import javax.swing.JPanel;

import Moteur.Loup;
import Moteur.Mouton;
import Moteur.Terrain.Case;

public class Minimap extends JPanel{
	public Minimap(int w,int h){
		this.setPreferredSize(new Dimension((int)(w*0.24),(int)(h*0.30)));
	}
	@Override
	public void paint(Graphics g) {
		Graphics2D g2=(Graphics2D)g;
		Object cren=RenderingHints.VALUE_ANTIALIAS_ON;
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,cren);
		Case [][] map=Fenetre.moteur.leTerrain.map;
		int w=this.getWidth()/map.length;
		int h=this.getHeight()/map[0].length;
		for(int i=0;i<map.length;i++)
			for(int j=0;j<map[0].length;j++){
				Case a=map[i][j];
				if(a.isObstacle()){
					g2.setColor(Color.BLACK);
					g2.fillOval(i*w, j*h, w, h);
				}
				else{
					if(a.getAnimalPresent() instanceof Loup){
						//affiche loupherbe
						g2.setColor(Color.RED);
						g.fillRect(i*w, j*h, w, h);
					}
					else if(a.getAnimalPresent() instanceof Mouton){
						//affiche moutoherbe
						g2.setColor(Color.BLUE);
						g2.fillRect(i*w, j*h, w, h);
					}
					else if(a.getPlante()!=null){
						//afficher herbe.
						g2.setColor(Color.GREEN);
						int [] x={i*w+w/2,i*w,i*w+w};
						int [] y={j*h,j*h+h,j*h+h};
						g2.fillPolygon(new Polygon(x,y,3));
					}
					else {
						g2.setColor(Color.BLACK);
						g2.drawOval(i*w, j*h, w, h);
					}
				}
			}
	}
}
