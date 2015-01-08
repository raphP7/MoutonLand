package Controleur;

import javax.swing.JLabel;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import Affichage.Fenetre;
import Affichage.InfoBas;
import Affichage.Minimap;

public class ComtroleurZoomMinimap implements ChangeListener {
	InfoBas infoBas;
	JLabel jlabel;
	JSlider jslider; 
	public ComtroleurZoomMinimap(InfoBas a,JLabel b,JSlider c){
		this.infoBas=a;
		this.jlabel=b;
		this.jslider=c;
	}
	@Override
	public void stateChanged(ChangeEvent e) {
		int a=jslider.getValue();
		this.jlabel.setText("Zoom Minimap:"+a);
		this.infoBas.setVueMinimap(a);
		if(a<infoBas.getVueTerrain()){
			infoBas.setVueTerrain(a);
		}
		Fenetre.minimap.rePositionnementTerrain();
		Fenetre.affichegeTerrain.repaint();
		Fenetre.minimap.repaint();
	}
}
