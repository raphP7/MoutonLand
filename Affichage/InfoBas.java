package Affichage;

import java.awt.Color;
import java.awt.Dimension;

import Moteur.*;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;

import Controleur.ComtroleurZoomMinimap;
import Controleur.ComtroleurZoomTerrain;
import Controleur.ControleurAleaPlante;
import Controleur.ControleurVitesse;

public class InfoBas extends JPanel{
	
	public JSlider speed;
	
	InfoBas(int w,int h){
		this.setPreferredSize(new Dimension((int)(w*0.72),(int)(h*0.10)));
		
		
		JLabel speedaffiche = new JLabel("vitesse");
		speed = new JSlider(0,200,Moteur.vistesseSimulation); // cr�e un slider avec (min, max, o� commence le slider)
		speed.setFocusable(false);
		ControleurVitesse listenerVitesse=new ControleurVitesse(speed,speedaffiche);
		speed.addChangeListener(listenerVitesse);
		speed.setMajorTickSpacing(700); 
		speed.setMinorTickSpacing(100); 
		speed.setPaintTicks(true); 
		speed.setPaintLabels(false);
		speedaffiche.setText("Vitesse : "+speed.getValue()+"m/s");
		
		JSlider AleaPlante;
		JLabel AleaPlanteAffiche = new JLabel("vitesse");
		AleaPlante = new JSlider(10,100,Moteur.valeurAleatoireReproductionPlante); // cr�e un slider avec (min, max, o� commence le slider)
		AleaPlante.setFocusable(false);
		ControleurAleaPlante AleaPlanteControleur=new ControleurAleaPlante(AleaPlante,AleaPlanteAffiche);
		AleaPlante.addChangeListener(AleaPlanteControleur);
		AleaPlante.setMajorTickSpacing(400); 
		AleaPlante.setMinorTickSpacing(1); 
		AleaPlante.setPaintTicks(false); 
		AleaPlante.setPaintLabels(false);
		AleaPlanteAffiche.setText("AleaPlante : 0,"+AleaPlante.getValue());

		JSlider zoomMinimap;
		JLabel zoomMinimapAffiche= new JLabel("Zoom Minimap:");
		int length=(Fenetre.terrain.map.length>100)?100:Fenetre.terrain.map.length;
		
		
		zoomMinimap = new JSlider(Minimap.vueAbscisse,length,Minimap.vueAbscisse); // cr�e un slider avec (min, max, o� commence le slider)
		zoomMinimap.setFocusable(false);
		//comtroleur minimap
		ComtroleurZoomMinimap controleZoomMinimap=new ComtroleurZoomMinimap(this,zoomMinimapAffiche,zoomMinimap);
		zoomMinimap.addChangeListener(controleZoomMinimap);
		
		zoomMinimap.setMajorTickSpacing(400); 
		zoomMinimap.setMinorTickSpacing(1); 
		zoomMinimap.setPaintTicks(false); 
		zoomMinimap.setPaintLabels(false);
		zoomMinimapAffiche.setText("Zoom Minimap:"+Minimap.vueAbscisse);
		
		JSlider zoomTerrain;
		JLabel zoomTerrainAffiche= new JLabel("Zoom Minimap:");
		zoomTerrain = new JSlider(AffichageTerrain.vueAbscisse,Minimap.vueAbscisse,AffichageTerrain.vueAbscisse); // cr�e un slider avec (min, max, o� commence le slider)
		zoomTerrain.setFocusable(false);
		//Controleurs affiche et slider.
		ComtroleurZoomTerrain   comtroleurZoomTerrain =new  ComtroleurZoomTerrain(this,zoomTerrainAffiche,zoomTerrain);
		zoomTerrain.addChangeListener(comtroleurZoomTerrain);
		
		
		zoomTerrain.setMajorTickSpacing(400); 
		zoomTerrain.setMinorTickSpacing(1); 
		zoomTerrain.setPaintTicks(false); 
		zoomTerrain.setPaintLabels(false);
		zoomTerrainAffiche.setText("Zoom Terrain:"+AffichageTerrain.vueAbscisse);
		
		
		//Jpanel Comteneur;
		JPanel comteneurHaut=new JPanel();
		JPanel comteneurBas=new JPanel();
		JPanel comteneurSpeed=new JPanel();
		JPanel comteneurAleaPlante=new JPanel();
		JPanel comteneurZoomMinimap=new JPanel();
		JPanel comteneurZoomTerrain=new JPanel();
		//Layout
		this.setLayout(new BoxLayout(this,BoxLayout.PAGE_AXIS));
		comteneurHaut.setLayout(new BoxLayout(comteneurHaut,BoxLayout.LINE_AXIS));
		comteneurBas.setLayout(new BoxLayout(comteneurBas,BoxLayout.LINE_AXIS));
		//ajout dans les panel
		this.add(comteneurHaut);
		this.add(comteneurBas);
		comteneurHaut.add(comteneurSpeed);
		comteneurHaut.add(comteneurAleaPlante);
		comteneurBas.add(comteneurZoomMinimap);
		comteneurBas.add(comteneurZoomTerrain);
		comteneurSpeed.add(speedaffiche);
		comteneurSpeed.add(speed);
		comteneurAleaPlante.add(AleaPlanteAffiche);
		comteneurAleaPlante.add(AleaPlante);
		comteneurZoomMinimap.add(zoomMinimapAffiche);
		comteneurZoomMinimap.add(zoomMinimap);
		comteneurZoomTerrain.add(zoomTerrainAffiche);
		comteneurZoomTerrain.add(zoomTerrain);
		
	}
	public void setVueMinimap(int x){
		Minimap.vueAbscisse=x;
		Minimap.vueOrdonnee=x;
	}
	public void setVueTerrain(int x){
		AffichageTerrain.vueAbscisse=x;
		AffichageTerrain.vueOrdonnee=x;
	}
	public int getVueMinimap(){
		return Minimap.vueAbscisse;
	}
	public int getVueTerrain(){
		return AffichageTerrain.vueAbscisse;
	}
	
}
