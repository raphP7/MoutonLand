package Affichage;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JPanel;

public class Minimap extends JPanel{
	public Minimap(int w,int h){
		this.setPreferredSize(new Dimension((int)(w*0.24),(int)(h*0.30)));
		this.setBackground(Color.GRAY);
	}
}
