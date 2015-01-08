package Controleur;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import Affichage.Minimap;

public class ComtroleurMinimap implements MouseListener,KeyListener{
	Minimap minimap;
	public ComtroleurMinimap(Minimap minimap){
		this.minimap=minimap;
	}
	@Override
	public void mouseClicked(MouseEvent e) {
		minimap.rePositionnementTerrain(e.getX(), e.getY());
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
	}
	@Override
	public void keyReleased(KeyEvent e) {
		if(e.getKeyCode()==38){
			//haut
			this.minimap.monterMinimap();
		}
		if(e.getKeyCode()==40){
			//bas
			this.minimap.decendreMinimap();
		}
		if(e.getKeyCode()==39){
			//Droite
			this.minimap.droiteMinimap();
		}
		if(e.getKeyCode()==37){
			//Gauche
			this.minimap.gaucheMinimap();
		}
		
	}
}