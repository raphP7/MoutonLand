package Affichage;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JPanel;

public class InfoBas extends JPanel{
	InfoBas(int w,int h){
		this.setBackground(Color.MAGENTA);
		this.setPreferredSize(new Dimension((int)(w*0.72),(int)(h*0.05)));
	}
}
