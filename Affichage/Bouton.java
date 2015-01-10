package Affichage;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import Controleur.BoutonAccueil;
import Controleur.BoutonPlay;
import Controleur.BoutonAleatoire;
import Moteur.Moteur;

public class Bouton extends JPanel{	
	BufferedImage imgMoutonPlus=null;
	BufferedImage imgMoutonMoin=null;
	BufferedImage imgLoupPlus=null;
	BufferedImage imgLoupMoin=null;
	BufferedImage imgPlantePlus=null;
	BufferedImage imgPlanteMoin=null;
	ImageIcon playimg=null;
	ImageIcon accueilimg=null;
	JButton play;
	JButton accueil;
	Bouton(int w,int h,Moteur moteur, InfoBas infobas){
		this.setBackground(Color.WHITE);
		this.setPreferredSize(new Dimension((int)(w*0.24),(int)(h*0.50)));
		this.setLayout(new BoxLayout(this,BoxLayout.PAGE_AXIS));
		
		try{
			imgMoutonPlus= ImageIO.read(this.getClass().getResource("plusMouton.png"));
			imgMoutonMoin=ImageIO.read(this.getClass().getResource("moinMouton.png"));
			imgLoupPlus=ImageIO.read(this.getClass().getResource("plusLoup.png"));
			imgLoupMoin=ImageIO.read(this.getClass().getResource("moinLoup.png"));
			imgPlantePlus=ImageIO.read(this.getClass().getResource("plusHerbe.png"));
			imgPlanteMoin=ImageIO.read(this.getClass().getResource("moinHerbe.png"));
			playimg=new ImageIcon(this.getClass().getResource("play.png"));
			accueilimg=new ImageIcon(this.getClass().getResource("accueil.png"));
		}
		catch (IOException e) {
			System.out.println("Une image na pas etait trouver.");
		}
		play = new JButton(playimg);
		play.setFocusable(false);
		play.setPreferredSize(new Dimension(60,60));
		BoutonPlay boutonplay = new BoutonPlay(moteur,infobas);
		play.addActionListener(boutonplay);
		
		accueil = new JButton(accueilimg);
		accueil.setFocusable(false);
		accueil.setPreferredSize(new Dimension(60,60));
		BoutonAccueil boutonAccueil = new BoutonAccueil(moteur);
		accueil.addActionListener(boutonAccueil);
		
		
		
		BoutonAleatoire boutonAle = new BoutonAleatoire(moteur);
		//Bouton
		JButton plusMouton=new JButton();
		plusMouton.setActionCommand("ajoutMouton");
		plusMouton.setPreferredSize(new Dimension(90,90));
		plusMouton.setIcon(new ImageIcon(imgMoutonPlus));
		plusMouton.setFocusable(false);
		plusMouton.addActionListener(boutonAle);
		
		JButton moinMouton=new JButton();
		moinMouton.setPreferredSize(new Dimension(90,90));
		moinMouton.setIcon(new ImageIcon(imgMoutonMoin));
		moinMouton.setFocusable(false);
		moinMouton.setActionCommand("suppressionMouton");
		moinMouton.addActionListener(boutonAle);
		
		JButton plusLoup=new JButton();
		plusLoup.setActionCommand("ajoutloup");
		plusLoup.setPreferredSize(new Dimension(90,90));
		plusLoup.setIcon(new ImageIcon(imgLoupPlus));
		plusLoup.setFocusable(false);
		plusLoup.addActionListener(boutonAle);
		
		JButton moinLoup=new JButton();
		moinLoup.setActionCommand("suppressionloup");
		moinLoup.setPreferredSize(new Dimension(90,90));
		moinLoup.setIcon(new ImageIcon(imgLoupMoin));
		moinLoup.setFocusable(false);
		moinLoup.addActionListener(boutonAle);
		
		JButton plusPlante=new JButton();
		plusPlante.setActionCommand("ajoutPlante");
		plusPlante.setPreferredSize(new Dimension(90,90));
		plusPlante.setIcon(new ImageIcon(imgPlantePlus));
		plusPlante.setFocusable(false);
		plusPlante.addActionListener(boutonAle);
		
		JButton moinPlante=new JButton();
		moinPlante.setActionCommand("suppressionPlante");
		moinPlante.setPreferredSize(new Dimension(90,90));
		moinPlante.setIcon(new ImageIcon(imgPlanteMoin));
		moinPlante.setFocusable(false);
		moinPlante.addActionListener(boutonAle);
		
		//JPanel
		JPanel mouton=new JPanel();
		JPanel loup=new JPanel();
		JPanel plante=new JPanel();
		JPanel boutonSimple=new JPanel();
		this.add(boutonSimple);
		boutonSimple.add(play);
		boutonSimple.add(accueil);
		this.add(mouton);
		mouton.add(plusMouton);
		mouton.add(moinMouton);
		
		this.add(loup);
		loup.add(plusLoup);
		loup.add(moinLoup);
		
		this.add(plante);
		plante.add(plusPlante);
		plante.add(moinPlante);
	}
}
