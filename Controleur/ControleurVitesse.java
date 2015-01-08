package Controleur;
import Moteur.*;

import javax.swing.JLabel;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 * Slider permettant de gerer la vitesse de la simulation
 * @author Raph
 *
 */
public class ControleurVitesse implements ChangeListener {

	JSlider speed;
	JLabel affichage;
	public ControleurVitesse(JSlider speed , JLabel affichage){
		this.speed=speed;
		this.affichage=affichage;
	}
	@Override
	public void stateChanged(ChangeEvent e) {
			
		int s = speed.getValue();
		Moteur.vistesseSimulation=s;
		
		String speed = String.valueOf("0,"+s+"/S");
		if(s==0){speed = "MAXIMUM";}
		if(s==1000){speed = "MINIMUM";}
		affichage.setText("Vitesse : "+speed);
		
	}
}
