package Controleur;

import javax.swing.JLabel;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.plaf.SliderUI;

import Affichage.Fenetre;
import Affichage.InfoBas;

public class ComtroleurZoomTerrain implements ChangeListener {
	InfoBas infoBas;
	JLabel jlabel;
	JSlider jslider; 
	public ComtroleurZoomTerrain(InfoBas a,JLabel b,JSlider c){
		this.infoBas=a;
		this.jlabel=b;
		this.jslider=c;
	}
	@Override
	public void stateChanged(ChangeEvent e) {
		int a=jslider.getValue();
		this.jlabel.setText("Zoom Terrain:"+a);
		this.infoBas.setVueTerrain(a);
		this.jslider.setMaximum(infoBas.getVueMinimap());
		Fenetre.minimap.rePositionnementTerrain();
		Fenetre.minimap.repaint();
		Fenetre.affichegeTerrain.repaint();
	}
}
