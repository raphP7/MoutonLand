package Affichage;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JPanel;

public class Bouton extends JPanel{
	Bouton(int w,int h){
		this.setBackground(Color.WHITE);
		this.setPreferredSize(new Dimension((int)(w*0.24),(int)(h*0.50)));
	}
}
