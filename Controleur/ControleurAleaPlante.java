package Controleur;

import javax.swing.JLabel;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import Moteur.Moteur;
/**
 * Slider permettant de gerer le pourcentage de chance qu'une plante essaye de se reproduire
 * @author Raph
 *
 */
public class ControleurAleaPlante implements ChangeListener {

	
	JSlider aleaPlante;
	JLabel aleaPlanteAffiche;
	
	public ControleurAleaPlante(JSlider aleaPlante, JLabel aleaPlanteAffiche) {
		this.aleaPlante=aleaPlante;
		this.aleaPlanteAffiche=aleaPlanteAffiche;
	}

	@Override
	public void stateChanged(ChangeEvent e) {
		
		int s = aleaPlante.getValue();
		Moteur.valeurAleatoireReproductionPlante=s;
		String alea = String.valueOf(s);
		
		aleaPlanteAffiche.setText("Reproduction Plante :1 chance sur "+alea);
		
		
	}

}
