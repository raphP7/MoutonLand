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
import Moteur.Terrain.Case;
import Moteur.animauxCarnivores.Loup;
import Moteur.animauxHerbivores.Mouton;

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
	public void paint(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
		g2.clearRect(0, 0, w, h);
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
				} else {
					if (a.getAnimalPresent() instanceof Loup) {
						// affiche loupherbe
						g2.setColor(Color.RED);
						g.fillRect(i * w, j * h, w, h);
					} else if (a.getAnimalPresent() instanceof Mouton) {
						// affiche moutoherbe
						g2.setColor(Color.BLUE);
						g2.fillRect(i * w, j * h, w, h);
					} else if (a.getPlante() != null) {
						// afficher herbe.
						g2.setColor(Color.GREEN);
						int[] x = { i * w + w / 2, i * w, i * w + w };
						int[] y = { j * h, j * h + h, j * h + h };
						g2.fillPolygon(new Polygon(x, y, 3));
					} else {
						g2.setColor(Color.BLACK);
						g2.drawOval(i * w, j * h, w, h);
					}
				}
			}
		}
		Stroke stroke = new BasicStroke(3f, BasicStroke.CAP_SQUARE,
				BasicStroke.JOIN_ROUND, 1.0f);
		g2.setStroke(stroke);
		g2.setColor(Color.RED);
		g2.drawRect(AffichageTerrain.x * w, AffichageTerrain.y * h, w
				* AffichageTerrain.vueAbscisse, h
				* AffichageTerrain.vueOrdonnee);
		if (Minimap.x == 0) {
			g2.drawLine(0, 0, Minimap.vueAbscisse * w, 0);
			;
		}
		if (Minimap.y == 0) {
			g2.drawLine(0, 0, 0, Minimap.vueOrdonnee * h);
		}
		int length, length1;
		if (Minimap.x + Minimap.vueAbscisse == (length = Fenetre.terrain.map.length)) {
			g2.drawLine(length = (length * w), 0, length,
					Fenetre.terrain.map.length * h);

		}
		if (Minimap.y + Minimap.vueOrdonnee == (length1 = Fenetre.terrain.map[0].length)) {
			g2.drawLine(0, (length1 = length1 * h), length, length1);
		}
		g2.drawRect(AffichageTerrain.x * w, AffichageTerrain.y * h, w
				* AffichageTerrain.vueAbscisse, h
				* AffichageTerrain.vueOrdonnee);
	}

	public void monterMinimap() {
		int up;
		if (Minimap.x - ((up = (int) (AffichageTerrain.vueAbscisse * 0.75))) < 0) {
			Minimap.x = 0;
		} else {
			Minimap.x -= up;
		}
		this.repaint();
	}

	public void decendreMinimap() {
		int up;
		int length = Fenetre.terrain.map.length;
		System.out.println(length);
		if (Minimap.x + ((up = (int) (AffichageTerrain.vueAbscisse * 0.75))) > length) {
			Minimap.x = length - 1;
		} else {
			Minimap.x += up;
		}
		System.out.println(Minimap.x);
		this.repaint();
	}

	public void miseAjoursCase(int x, int y) {
		if (x >= 0 && y >= 0 && x < Fenetre.terrain.map.length
				&& y < Fenetre.terrain.map[0].length) {
			repaint(x * this.w, y * this.h, this.w, this.h);
			Fenetre.affichegeTerrain.repaint(x * Fenetre.affichegeTerrain.w, y
					* Fenetre.affichegeTerrain.h, Fenetre.affichegeTerrain.w,
					Fenetre.affichegeTerrain.h);
		}
	}

	public void rePositionnementTerrain(int positionX, int positionY) {
		int abscisse, ordonnee;
		abscisse = positionX / this.w + Minimap.x;
		ordonnee = positionY / this.h + Minimap.y;
		if (Minimap.vueAbscisse - abscisse < AffichageTerrain.vueAbscisse) {
			AffichageTerrain.x = Minimap.vueAbscisse
					- AffichageTerrain.vueAbscisse;
		} else {
			AffichageTerrain.x = abscisse;
		}
		if (Minimap.vueOrdonnee - ordonnee < AffichageTerrain.vueOrdonnee) {
			AffichageTerrain.y = Minimap.vueOrdonnee
					- AffichageTerrain.vueOrdonnee;
		} else {
			AffichageTerrain.y = ordonnee;
		}
		this.repaint();
		Fenetre.affichegeTerrain.repaint();
	}
}
