package Affichage;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;

import Moteur.Moteur;
public class Fenetre extends JFrame{
	private int width;
	private int height;
	protected static Moteur moteur;
	Fenetre(int w,int h,int nbObstacle) throws Exception{
		
		moteur=new Moteur(w,h,nbObstacle);
		moteur.creerAlea("Loup", 10);
		moteur.creerAlea("Plante", 10);
		moteur.creerAlea("Mouton", 10);
		
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//pour que la croix ferme le Jframe
		this.setTitle("MoutonLand");//nom de la fenetre
		this.setMinimumSize(new Dimension(500,500));
		this.width=getToolkit().getScreenSize().width;//width de votre ecran.
		this.height=getToolkit().getScreenSize().height;//height maximal de votre ecran
		this.setSize((int)(this.width*0.95),(int)(this.height*0.95)); // la taille par defaut de la fenetre
		
		JPanel comteneurGlobal=new JPanel();
		comteneurGlobal.setLayout(new BoxLayout(comteneurGlobal,BoxLayout.LINE_AXIS));//J'ajoute un layout(Vertical) a mon comteneurGlobal.
		
		JPanel comteneurGauche=new JPanel();
		
		comteneurGauche.setMinimumSize(new Dimension(300,0));
		comteneurGauche.setBackground(Color.WHITE);
		comteneurGauche.setLayout(new BoxLayout(comteneurGauche,BoxLayout.PAGE_AXIS));//J'ajoute un layout(Horizontal) a mon comteneurGlobal.
		
		JPanel comteneurDroit=new JPanel();
		comteneurDroit.setBackground(new Color(115,67,29));
		comteneurDroit.setLayout(new BoxLayout(comteneurDroit,BoxLayout.PAGE_AXIS));//J'ajoute un layout(Horizontal) a mon comteneurGlobal.
		
		//Ajout des JPanel.
		comteneurGlobal.add(comteneurGauche);
		comteneurGlobal.add(comteneurDroit);
		comteneurGauche.add(new Bouton(this.width,this.height));
		comteneurGauche.add(new Minimap(this.width,this.height));
		comteneurDroit.add(new InfoHaut(this.width,this.height));
		comteneurDroit.add(new AffichageTerrain(this.width,this.height));
		comteneurDroit.add(new InfoBas(this.width,this.height));
		
	    this.setContentPane(comteneurGlobal);
		this.setVisible(true); // la fenetre est visible
		
		//pack();
		
		for (int i=0 ; i<1000; i++){
			moteur.simulation();
			
			try{
				Thread.sleep(100);

			}catch(Exception e){
				
			}
			
			repaint();
		}
	}
}
