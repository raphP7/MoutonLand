package Affichage;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class InfoHaut extends JPanel{
	InfoHaut(int w,int h){
		this.setPreferredSize(new Dimension((int)(w*0.70),(int)(h*0.05)));
		
		JLabel time=new JLabel();
		time.setText("Time:00:00:00");
		
		JLabel etreVivant=new JLabel();
		etreVivant.setText("Etre: xx");
		
		JLabel etreMort=new JLabel();
		etreMort.setText("Nombre de Mort:xx");
		
		JLabel nombreDeplacement=new JLabel();
		nombreDeplacement.setText("Nombre Deplacement:0");
		
		//Jpanel
		JPanel panelTime=new JPanel();
		JPanel panelDeplacement=new JPanel();
		JPanel panelVivant=new JPanel();
		JPanel panelMort=new JPanel();
		
		
		this.setLayout(new BoxLayout(this,BoxLayout.LINE_AXIS));
		panelTime.add(time);
		panelDeplacement.add(nombreDeplacement);
		panelMort.add(etreMort);
		panelVivant.add(etreVivant);
		this.add(panelTime);
		this.add(panelDeplacement);
		this.add(panelVivant);
		this.add(panelMort);
		
	}
}
