package Controleur;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import Affichage.Accueil;
import Affichage.Fenetre;
import Moteur.Moteur;

public class BoutonAccueil implements ActionListener{

	Moteur moteur;
	public BoutonAccueil(Moteur moteur){
		this.moteur=moteur;
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		moteur.play=false;
		moteur.simulationEnCour=false;
		moteur.vistesseSimulation=0;
		moteur.laFenetre.dispose();
		new Accueil();
		Thread.currentThread().interrupt();
		
		
	}

}
