package Affichage;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.RenderingHints;
import java.awt.Stroke;
import java.awt.event.MouseListener;
import java.util.Arrays;

import javax.swing.JPanel;

import Controleur.ComtroleurMinimap;
import Moteur.animauxCarnivores.Loup;
import Moteur.animauxHerbivores.Mouton;
import Moteur.Terrain.Case;

public class Minimap extends JPanel {
	protected static int vueAbscisse = 30;// La vue par default de la minimap en
											// abscisse(en
	// nombre de case.)
	protected static int vueOrdonnee = 30;
	// Ordonner de la position de la minimap par rapport a la map.
	protected static int x = 0;// abscisse
	protected static int y = 0;// ordonnée
	public int w;
	public int h;

	public Minimap(int w, int h) {
		this.reboot();
		Minimap.x=0;
		Minimap.y=0;
		int length;
		// Si le terrain est plus petit que vue(Abscisse)
		if ((length = Fenetre.terrain.map.length) <= Minimap.vueAbscisse) {
			Minimap.vueAbscisse = length;
		}
		// Si le terrain est plus petit que vue(Ordonnee)
		if ((length = Fenetre.terrain.map[0].length) <= Minimap.vueOrdonnee) {
			Minimap.vueOrdonnee = length;
		}
		this.setPreferredSize(new Dimension((int) (w * 0.24), (int) (h * 0.30)));
		ComtroleurMinimap comtroleurMinimap = new ComtroleurMinimap(this);
		this.addMouseListener(comtroleurMinimap);
		if (Fenetre.terrain.map.length > Minimap.vueAbscisse) {
			this.addKeyListener(comtroleurMinimap);
			this.setFocusable(true);// permet au composante d'avoir le focus
			this.requestFocus();
		}
		// repaint();
	}

	@Override
	public void paintComponent(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
		g2.clearRect(0, 0, this.getWidth(), this.getHeight());
		Object cren = RenderingHints.VALUE_ANTIALIAS_ON;
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, cren);
		Case[][] map = Fenetre.terrain.map;
		this.w = this.getWidth() / Minimap.vueAbscisse;
		this.h = this.getHeight() / Minimap.vueOrdonnee;
		for (int i = 0; i < Minimap.vueAbscisse; i++) {
			for (int j = 0; j < Minimap.vueOrdonnee; j++) {
				Case a = map[i + Minimap.x][j + Minimap.y];
				if (a.isObstacle()) {
					g2.setColor(Color.BLACK);
					g2.fillOval(i * w, j * h, w, h);
				} 
				else {
					// afficher herbe.
					if(a.getPlante()!=null){
						g2.setColor(Color.GREEN);
						int[] x = { i * w + w / 2, i * w, i * w + w };
						int[] y = { j * h, j * h + h, j * h + h };
					g2.fillPolygon(new Polygon(x, y, 3));
					}
					if (a.getAnimalPresent() instanceof Loup) {
						// affiche loupherbe
						g2.setColor(Color.RED);
						g.fillRect(i * w, j * h, w, h);
					} else if (a.getAnimalPresent() instanceof Mouton) {
						// affiche moutoherbe
						g2.setColor(Color.BLUE);
						g2.fillRect(i * w, j * h, w, h);
					} 
					else {
						g2.setColor(Color.BLACK);
						g2.drawOval(i * w, j * h, w, h);
					}
				}
			}
		}
		Stroke stroke = new BasicStroke(3f, BasicStroke.CAP_SQUARE,
				BasicStroke.JOIN_ROUND, 1.0f);
		g2.setStroke(stroke);
		g2.setColor(Color.CYAN);
		g2.drawRect((AffichageTerrain.x-Minimap.x) * this.w, (AffichageTerrain.y-Minimap.y) * this.h, 
				this.w* AffichageTerrain.vueAbscisse, this.h* AffichageTerrain.vueOrdonnee);
		g2.setColor(Color.RED);
		if (Minimap.x == 0) {
			g2.drawLine(0, 0, 0, Minimap.vueOrdonnee * h);
		}
		if (Minimap.y == 0) {
			g2.drawLine(0, 0, Minimap.vueAbscisse * w, 0);
		}
		int length, length1;
		if (Minimap.x + Minimap.vueAbscisse == (length = Fenetre.terrain.map.length)) {
			g2.drawLine(Minimap.vueAbscisse*w,0, Minimap.vueAbscisse*w,
					Minimap.vueOrdonnee * h);
		}
		if (Minimap.y + Minimap.vueOrdonnee== (length1 = Fenetre.terrain.map[0].length)) {
			g2.drawLine(0, (length1 = Minimap.vueOrdonnee* h), Minimap.vueAbscisse*w, length1);
		}
	}

	public void monterMinimap() {
		int up=(int)(AffichageTerrain.vueOrdonnee * 0.75);
		if (Minimap.y - up < 0) {
			Minimap.y = 0;
		} 
		else {
			Minimap.y -= up;
		}
		this.repaint();
		rePositionnementTerrain(Minimap.x, Minimap.y);
	}

	public void decendreMinimap() {
		int up=(int) (AffichageTerrain.vueOrdonnee * 0.75);
		int length = Fenetre.terrain.map[0].length;
		if (Minimap.y + up > length-Minimap.vueAbscisse) {
			Minimap.y =length-Minimap.vueAbscisse;
		} 
		else {
			Minimap.y += up;
		}
		this.repaint();
		rePositionnementTerrain(Minimap.x, Minimap.y);
	}
	public void droiteMinimap(){
		int up=(int) (AffichageTerrain.vueAbscisse * 0.75);
		int length = Fenetre.terrain.map.length;
		if (Minimap.x + up >length-Minimap.vueAbscisse) {
			Minimap.x = length-Minimap.vueOrdonnee;
		} 
		else {
			Minimap.x += up;
		}
		this.repaint();
		rePositionnementTerrain(Minimap.x, Minimap.y);
	}
	public void gaucheMinimap(){
		int up=(int) (AffichageTerrain.vueAbscisse * 0.75);
		if (Minimap.x - up < 0) {
			Minimap.x = 0;
		} else {
			Minimap.x -= up;
		}
		this.repaint();
		rePositionnementTerrain(Minimap.x, Minimap.y);
	}

	public void miseAjoursCase(int x, int y) {
		
		if (x >= 0 && y >= 0 && x < Fenetre.terrain.map.length && y < Fenetre.terrain.map[0].length) {
			
			repaint(x * this.w, y * this.h, this.w, this.h);
			
			Fenetre.affichegeTerrain.repaint(x * Fenetre.affichegeTerrain.w, y
					* Fenetre.affichegeTerrain.h, Fenetre.affichegeTerrain.w,
					Fenetre.affichegeTerrain.h);
		}
	}

	public void rePositionnementTerrain(int positionX, int positionY) {
		int abscisse, ordonnee;
		abscisse = positionX / this.w;
		ordonnee = positionY / this.h;
		if (Minimap.vueAbscisse - abscisse < AffichageTerrain.vueAbscisse) {
			AffichageTerrain.x = Minimap.vueAbscisse
					- AffichageTerrain.vueAbscisse+ Minimap.x;
		} else {
			AffichageTerrain.x = abscisse+ Minimap.x;
		}
		if (Minimap.vueOrdonnee - ordonnee < AffichageTerrain.vueOrdonnee) {
			AffichageTerrain.y = Minimap.vueOrdonnee
					- AffichageTerrain.vueOrdonnee+ Minimap.y;
		} else {
			AffichageTerrain.y = ordonnee+Minimap.y;
		}
		this.repaint();
		Fenetre.affichegeTerrain.repaint();
	}
	public void rePositionnementTerrain() {
		int abscisse, ordonnee;
		abscisse = x+Minimap.x ;
		ordonnee = y+Minimap.y ;
		if (Minimap.vueAbscisse - abscisse < AffichageTerrain.vueAbscisse) {
			AffichageTerrain.x = Minimap.vueAbscisse
					- AffichageTerrain.vueAbscisse+ Minimap.x;
		} else {
			AffichageTerrain.x = abscisse+ Minimap.x;
		}
		if (Minimap.vueOrdonnee - ordonnee < AffichageTerrain.vueOrdonnee) {
			AffichageTerrain.y = Minimap.vueOrdonnee
					- AffichageTerrain.vueOrdonnee+ Minimap.y;
		} else {
			AffichageTerrain.y = ordonnee+Minimap.y;
		}
		this.repaint();
		Fenetre.affichegeTerrain.repaint();
	}
	
	public void reboot(){
		int length=Fenetre.terrain.map.length;
		AffichageTerrain.vueAbscisse=7;
		AffichageTerrain.vueOrdonnee=7;
		if(length>30){
			Minimap.vueAbscisse=30;
			Minimap.vueOrdonnee=30;
		}
		else{
			Minimap.vueAbscisse=length;
			Minimap.vueOrdonnee=length;
		}
	}
	
}
