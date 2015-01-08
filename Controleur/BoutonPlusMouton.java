package Controleur;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import Moteur.Moteur;

public class BoutonPlusMouton implements ActionListener {

	Moteur moteur;
	public BoutonPlusMouton(Moteur moteur){
		this.moteur=moteur;
	}
	@Override
	
	public void actionPerformed(ActionEvent e) {
		try {
			moteur.creerAlea("Mouton", 1);
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
	}

}