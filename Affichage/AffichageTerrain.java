package Affichage;
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
import Moteur.interfaceNoir;
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
	private BufferedImage LoupAdulteFemelle2 = null;
	private BufferedImage LoupAdulteMal = null;
	private BufferedImage LoupAdulteMal2 = null;
	private BufferedImage LoupBebe = null;
	private BufferedImage LoupBebe2 = null;
	private BufferedImage MoutonAdulteFemelle1 = null;
	private BufferedImage MoutonAdulteFemelle2 = null;
	private BufferedImage MoutonAdulteMal2 = null;
	private BufferedImage MoutonBebe1 = null;
	private BufferedImage MoutonBebe2 = null;
	private BufferedImage MoutonAdulteMal1 = null;
	private BufferedImage terre = null;
	private BufferedImage herbe = null;
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
			terre= ImageIO.read(this.getClass().getResource("terre.jpg"));
			herbe = ImageIO.read(this.getClass().getResource("herbe.jpg"));
			obstacle = ImageIO.read(this.getClass().getResource("obstacle.jpg"));
			LoupAdulteFemelle = ImageIO.read(this.getClass().getResource("loupAdulteFemelle.png"));
			LoupAdulteFemelle2 = ImageIO.read(this.getClass().getResource("loupAdulteFemelle2.png"));
			LoupAdulteMal = ImageIO.read(this.getClass().getResource("loupAdulteMal.png"));
			LoupAdulteMal2 = ImageIO.read(this.getClass().getResource("loupAdulteMal2.png"));
			LoupBebe = ImageIO.read(this.getClass().getResource("loupBebe.png"));
			LoupBebe2 = ImageIO.read(this.getClass().getResource("loupBebe2.png"));
			MoutonAdulteMal1 = ImageIO.read(this.getClass().getResource("moutonAdulteMale1.png"));
			MoutonAdulteMal2 = ImageIO.read(this.getClass().getResource("moutonAdulteMale2.png"));
			MoutonAdulteFemelle1 = ImageIO.read(this.getClass().getResource("moutonAdulteFemelle1.png"));
			MoutonAdulteFemelle2 = ImageIO.read(this.getClass().getResource("moutonAdulteFemelle2.png"));
			MoutonBebe1 = ImageIO.read(this.getClass().getResource("moutonBebe1.png"));
			MoutonBebe2 = ImageIO.read(this.getClass().getResource("moutonBebe2.png"));

		} catch (IOException e) {
			System.out.println("Une image na pas etait trouver.");
		}
		this.setPreferredSize(new Dimension((int) (w * 0.72), (int) (h * 0.80)));
		repaint();
	}

	@Override
	public void paintComponent(Graphics g) {
		Case[][] map = Fenetre.terrain.map;
		this.w = this.getWidth() / AffichageTerrain.vueAbscisse;
		this.h = this.getHeight() / AffichageTerrain.vueOrdonnee;
		g.clearRect(0, 0,this.getWidth(),this.getHeight());
		for (int i =0; i < AffichageTerrain.vueAbscisse; i++) {
			for (int j =0; j < AffichageTerrain.vueOrdonnee; j++) {
				
				Case a = map[i+AffichageTerrain.x][j+AffichageTerrain.y];
				
				if(a.isObstacle()){
					
					g.drawImage(obstacle,i*w, j*h, w, h,this);
				}
				
				else{
					
					if(a.getPlante() != null){
						
						g.drawImage(herbe,i*w, j*h, w, h,this);
					}
					else{
						
						g.drawImage(terre,i*w, j*h, w, h,this);
						
					}
					
					if(a.getAnimalPresent() instanceof Carnivore){
							if (a.getAnimalPresent() instanceof Loup){
								
								if(a.getAnimalPresent().isPuber()){
									if(a.getAnimalPresent().isFemelle()){
										
										if(a.getAnimalPresent() instanceof interfaceNoir){
											g.drawImage(LoupAdulteFemelle2,i*w, j*h, w, h,this);
											
										}
										else{
											g.drawImage(LoupAdulteFemelle,i*w, j*h, w, h,this);	
										}
										
									}else{
										if(a.getAnimalPresent() instanceof interfaceNoir){
											g.drawImage(LoupAdulteMal2,i*w, j*h, w, h,this);	
										}
										else{
											g.drawImage(LoupAdulteMal,i*w, j*h, w, h,this);
										}
										
									}
								}
								else{
									if(a.getAnimalPresent() instanceof interfaceNoir){
										
										g.drawImage(LoupBebe2,i*w, j*h, w, h,this);
										
									}else{
										g.drawImage(LoupBebe,i*w, j*h, w, h,this);
									}
									
								}
								
							}
							
					}
					
					else if (a.getAnimalPresent() instanceof Herbivore) {
						if (a.getAnimalPresent() instanceof Mouton) {

							if (a.getAnimalPresent().isPuber()) {
								
								if (a.getAnimalPresent().isFemelle()) {
									
									if(a.getAnimalPresent() instanceof interfaceNoir){
										g.drawImage(MoutonAdulteFemelle2,i * w, j * h, w, h, this);
									}
									else{
										g.drawImage(MoutonAdulteFemelle1,i * w, j * h, w, h, this);
									}										
									
								} else {
									if(a.getAnimalPresent() instanceof interfaceNoir){
										g.drawImage(MoutonAdulteMal2, i * w, j * h, w, h, this);
									}
									else{
										g.drawImage(MoutonAdulteMal1, i * w, j * h, w, h, this);
									}
									}
								}
							else{
								if(a.getAnimalPresent() instanceof interfaceNoir){
									g.drawImage(MoutonBebe2, i * w, j * h, w, h, this);
								}
								else{
									g.drawImage(MoutonBebe1, i * w, j * h, w, h, this);
								}
							}
							}
						}

					}
				}
			}
		}
	}
