package Controleur;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import Moteur.Moteur;

public class BoutonAleatoire implements ActionListener {

	Moteur moteur;
	
	public BoutonAleatoire(Moteur moteur){
		this.moteur=moteur;
	}
	@Override
	
	public void actionPerformed(ActionEvent e) {
		if(moteur.play){
			return ;
		}
		try {
			if(e.getActionCommand().toString().equals("ajoutMouton")){
				moteur.creerAlea("Mouton", 1);
			}
			else if(e.getActionCommand().toString().equals("suppressionMouton")){
				moteur.supprimerAle("Mouton", 1);
			}
			else if (e.getActionCommand().toString().equals("suppressionloup")){
				moteur.supprimerAle("Loup", 1);
			}
			else if (e.getActionCommand().toString().equals("ajoutloup")){
				moteur.creerAlea("Loup", 1);
			}
			else if (e.getActionCommand().toString().equals("ajoutPlante")){
				moteur.creerAlea("Plante", 1);
			}
			else if (e.getActionCommand().toString().equals("suppressionPlante")){
				moteur.supprimerAle("plante", 1);
			}
		}
		catch (Exception e1) {
		}
		
		this.moteur.laFenetre.minimap.repaint();
	}

}