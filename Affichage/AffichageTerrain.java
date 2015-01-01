package Affichage;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

import Moteur.Carnivore;
import Moteur.Herbivore;
import Moteur.Plante;
import Moteur.Terrain.Case;
import Moteur.animauxCarnivores.Loup;
import Moteur.animauxHerbivores.Mouton;

public class AffichageTerrain extends JPanel {
	// La vue par default de la terrain en abscisse(en nombre de case.)
	protected static int vueAbscisse = 7;
	// La vue par default de la minimap en abscisse(en nombre de case.)
	protected static int vueOrdonnee = 7;
	protected static int x = Minimap.x;// abscisse
	protected static int y = Minimap.y;// ordonnée
	protected int w;
	protected int h;
	Random random = new Random();
	private BufferedImage LoupAdulteFemelle = null;
	private BufferedImage LoupAdulteMal = null;
	private BufferedImage LoupBebe = null;
	private BufferedImage MoutonAdulteFemelle1 = null;
	private BufferedImage MoutonAdulteFemelle2 = null;
	private BufferedImage MoutonAdulteMal2 = null;
	private BufferedImage MoutonBebe1 = null;
	private BufferedImage MoutonBebe2 = null;
	private BufferedImage MoutonAdulteMal1 = null;
	private BufferedImage terre = null;
	private BufferedImage plante = null;
	private BufferedImage obstacle = null;

	AffichageTerrain(int w, int h) {
		int length;
		// Si le terrain est plus petit que vue
		if ((length = Fenetre.terrain.map.length) <= AffichageTerrain.vueAbscisse) {
			vueAbscisse = length;
		}
		if ((length = Fenetre.terrain.map[0].length) <= AffichageTerrain.vueOrdonnee) {
			vueOrdonnee = length;
		}
		try {
			terre= ImageIO.read(this.getClass().getClassLoader().getResource("Terre.jpg"));
			plante = ImageIO.read(this.getClass().getClassLoader().getResource("Herbe.jpg"));
			obstacle = ImageIO.read(this.getClass().getClassLoader().getResource("obstacle.jpg"));

			 LoupAdulteFemelle = ImageIO.read(this.getClass().getClassLoader().getResource("LoupAdulteFemelle.png"));
			 LoupAdulteMal = ImageIO.read(this.getClass().getClassLoader()
					.getResource("LoupAdulteMal.png"));
			 LoupBebe = ImageIO.read(this.getClass().getClassLoader()
					.getResource("LoupBebe.png"));
			 MoutonAdulteMal1 = ImageIO.read(this.getClass()
					.getClassLoader()
					.getResource("MoutonAdulteMale1.png"));
			 MoutonAdulteMal2 = ImageIO.read(this.getClass()
					.getClassLoader()
					.getResource("MoutonAdulteMale2.png"));
			 MoutonAdulteFemelle1 = ImageIO.read(this.getClass()
					.getClassLoader()
					.getResource("MoutonAdulteFemelle1.png"));
			 MoutonAdulteFemelle2 = ImageIO.read(this.getClass()
					.getClassLoader()
					.getResource("MoutonAdulteFemelle2.png"));

			 MoutonBebe1 = ImageIO.read(this.getClass().getClassLoader()
					.getResource("MoutonBebe1.png"));
			 MoutonBebe2 = ImageIO.read(this.getClass().getClassLoader()
					.getResource("MoutonBebe2.png"));

			 
		} catch (IOException e) {
			System.out.println("Une image na pas etait trouver.");
		}
		this.setPreferredSize(new Dimension((int) (w * 0.72), (int) (h * 0.90)));
		repaint();
	}

	@Override
	public void paint(Graphics g) {
		Case[][] map = Fenetre.terrain.map;
		this.w = this.getWidth() / AffichageTerrain.vueAbscisse;
		this.h = this.getHeight() / AffichageTerrain.vueOrdonnee;
		for (int i = 0; i < AffichageTerrain.vueAbscisse; i++) {
			for (int j = 0; j < AffichageTerrain.vueOrdonnee; j++) {
				Case a = map[i][j];
				//Affichage Image:
				if(a.isObstacle()){
					g.drawImage(obstacle,i*w, j*h, w, h,this);
				}
				else{
					
					if(a.getPlante()!=null){
						g.drawImage(plante,i*w, j*h, w, h,this);}
					else{
						g.drawImage(terre,i*w, j*h, w, h,this);}
					
					if(a.getAnimalPresent() instanceof Carnivore){
							if (a.getAnimalPresent() instanceof Loup){
								
								if(a.getAnimalPresent().isPuber()){
									if(a.getAnimalPresent().isFemelle()){
										g.drawImage(LoupAdulteFemelle,i*w, j*h, w, h,this);
									}else{
										g.drawImage(LoupAdulteMal,i*w, j*h, w, h,this);
									}
								}
								else{
									g.drawImage(LoupBebe,i*w, j*h, w, h,this);
								}
								
							}
							
					}
					
					else if (a.getAnimalPresent() instanceof Herbivore) {
						if (a.getAnimalPresent() instanceof Mouton) {

							if (a.getAnimalPresent().isPuber()) {
								
								if (a.getAnimalPresent().isFemelle()) {
									
									//if(a.getAnimalPresent().)
										g.drawImage(MoutonAdulteFemelle1,i * w, j * h, w, h, this);
										//g.drawImage(MoutonAdulteFemelle2,i * w, j * h, w, h, this);
									
								} else {
										g.drawImage(MoutonAdulteMal1, i * w, j * h, w, h, this);
										//g.drawImage(MoutonAdulteMal2, i * w, j * h, w, h, this);
									}
								}
							else{
								g.drawImage(MoutonBebe1, i * w, j * h, w, h, this);
								//g.drawImage(MoutonBebe2, i * w, j * h, w, h, this);
							}
							}
						}

					}
				}
			}
		}
	}
