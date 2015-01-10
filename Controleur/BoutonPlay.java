package Controleur;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JSlider;

import Affichage.InfoBas;
import Moteur.Moteur;

public class BoutonPlay implements ActionListener{

	Moteur moteur;
	JSlider slid;
	public BoutonPlay(Moteur moteur, InfoBas infobas){
		this.moteur=moteur;
		this.slid=infobas.speed;
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		
		if(moteur.play){
			slid.setValue(0);
			moteur.play=false;
		}
		else{
			slid.setValue(100);
			moteur.play=true;
		}
	}

}
