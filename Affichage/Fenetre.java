package Affichage;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;

import Moteur.Moteur;
import Moteur.Terrain.Terrain;
public class Fenetre extends JFrame{
	
	public static Terrain terrain;
	public static Minimap minimap;
	public static AffichageTerrain affichegeTerrain;
	
	public Fenetre(Moteur moteur) throws Exception{
		Fenetre.terrain=moteur.leTerrain;
		
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//pour que la croix ferme le Jframe
		this.setTitle("MoutonLand");//nom de la fenetre
		this.setMinimumSize(new Dimension(500,500));
		int width=getToolkit().getScreenSize().width;//width de votre ecran.
		int height=getToolkit().getScreenSize().height;//height maximal de votre ecran
		this.setSize((int)(width*0.95),(int)(height*0.95)); // la taille par defaut de la fenetre
		
		JPanel comteneurGlobal=new JPanel();
		comteneurGlobal.setLayout(new BoxLayout(comteneurGlobal,BoxLayout.LINE_AXIS));//J'ajoute un layout(Vertical) a mon comteneurGlobal.
		
		JPanel comteneurGauche=new JPanel();
		
		comteneurGauche.setLayout(new BoxLayout(comteneurGauche,BoxLayout.PAGE_AXIS));//J'ajoute un layout(Horizontal) a mon comteneurGlobal.
		
		JPanel comteneurDroit=new JPanel();
		comteneurDroit.setLayout(new BoxLayout(comteneurDroit,BoxLayout.PAGE_AXIS));//J'ajoute un layout(Horizontal) a mon comteneurGlobal.
		
		//Ajout des JPanel.
		comteneurGlobal.add(comteneurGauche);
		
		Fenetre.minimap= new Minimap(width,height);
		InfoBas infobas=new InfoBas(width,height);
		Bouton bouton =new Bouton(width,height,moteur,infobas);
		comteneurGlobal.add(comteneurDroit);
		comteneurGauche.add(bouton);
		comteneurGauche.add(Fenetre.minimap);
		comteneurDroit.add(new InfoHaut(width,height,moteur));
		comteneurDroit.add((this.affichegeTerrain=new AffichageTerrain(width,height)));
		comteneurDroit.add(infobas);
	    this.setContentPane(comteneurGlobal);
		this.setVisible(true); // la fenetre est visible	
	}
	public void miseAjoursCase(int x , int y ){
		Fenetre.minimap.miseAjoursCase(x, y);
	}
	public void miseAjourAffichage(){
		Fenetre.affichegeTerrain.repaint();
		Fenetre.minimap.repaint();
	}
}
