package Controleur;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import Moteur.Moteur;

public class BoutonPlay implements ActionListener{

	Moteur moteur;
	public BoutonPlay(Moteur moteur){
		this.moteur=moteur;
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		System.out.println("ici");
		if(moteur.play){
			
			moteur.play=false;
		}
		else{
			moteur.play=true;
		}
	}

}
