package Affichage;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JPanel;

public class AffichageTerrain extends JPanel {
	AffichageTerrain(int w,int h){
		this.setBackground(Color.red);
		this.setPreferredSize(new Dimension((int)(w*0.72),(int)(h*0.90)));
	}
}
