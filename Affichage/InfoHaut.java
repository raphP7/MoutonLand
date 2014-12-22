package Affichage;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JPanel;

public class InfoHaut extends JPanel{
	InfoHaut(int w,int h){
		this.setBackground(Color.GREEN);
		this.setPreferredSize(new Dimension((int)(w*0.70),(int)(h*0.05)));
	}
}
