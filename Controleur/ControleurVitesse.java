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
		
		String speed = String.valueOf(""+s+"m/s");
		if(s==0){speed = "Vitesse MAXIMUM";
		affichage.setText(speed);}
		else if(s==100){speed = " Vitesse MINIMUM";
		affichage.setText(speed);}
		else{
			affichage.setText("Ralenti : "+speed);
		}
		
		
	}
}
