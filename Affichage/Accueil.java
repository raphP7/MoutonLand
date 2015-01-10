package Affichage;


import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

import javax.swing.*;

import Moteur.Moteur;

public class Accueil extends JFrame implements ActionListener{
	private JFormattedTextField loupBlanc = new JFormattedTextField(); //création d'un champ 
	private JLabel loupBlancB = new JLabel("Nombre de loup Blanc");

	private JFormattedTextField loupNoir = new JFormattedTextField(); //création d'un champ 
	private JLabel loupNoirB = new JLabel("Nombre de loup Noir");
	
	private JFormattedTextField moutonBlanc = new JFormattedTextField(); //création d'un champ 
	private JLabel moutonBlancB = new JLabel("Nombre de mouton blanc");
	
	private JFormattedTextField moutonNoir = new JFormattedTextField(); //création d'un champ 
	private JLabel moutonNoirB = new JLabel("Nombre de mouton noir");
	
	private JFormattedTextField tailleTerrain = new JFormattedTextField();
	private JLabel tailleTerrainJlabel = new JLabel("Taille du terrain");
	private JFormattedTextField plantes = new JFormattedTextField();
	private JLabel plantesJlabel = new JLabel("Nombre de plantes");
	private JFormattedTextField obstacles = new JFormattedTextField();
	private JLabel obstaclesJlabel = new JLabel("Nombre d'obstacles");
	private JButton ok = new JButton("ok");
	private JButton alea=new JButton("ALEATOIRE");
	private Thread t;
	public Accueil(){
		int largeur=150,longeur=800;
		Font font = new Font("Arial",Font.BOLD,13);
		loupBlancB.setFont(font);
		loupNoirB.setFont(font);
		moutonNoirB.setFont(font);
		moutonBlancB.setFont(font);
		tailleTerrainJlabel.setFont(font);
		plantesJlabel.setFont(font);
		obstaclesJlabel.setFont(font);
		this.setTitle("Accueil");
		this.setSize(longeur, largeur);
		this.setMinimumSize(new Dimension(100,100));
		this.setLocationRelativeTo(null);
		
		JPanel content0= new JPanel(); // conteneur de tous les boutons
		content0.setLayout(new BoxLayout(content0,BoxLayout.PAGE_AXIS));
		
		JPanel content1= new JPanel();
		
		JPanel fin =new JPanel();
		JPanel content = new JPanel();
		
		
		content.setMaximumSize(new Dimension(1000,40));
		
		content0.add(content1); // bouton aleatoire
		
		content0.add(content); // bouton manuel
		content0.add(fin);
		
		content.setLayout(new BoxLayout(content,BoxLayout.LINE_AXIS));
		
		content.add(tailleTerrainJlabel);
		content.add(tailleTerrain);
		content.add(plantesJlabel);
		content.add(plantes);
		content.add(obstaclesJlabel);
		content.add(obstacles);
		content.add(ok);
		
		content1.setLayout(new BoxLayout(content1,BoxLayout.LINE_AXIS));
		content1.add(loupBlancB);
		content1.add(loupBlanc);
		content1.add(loupNoirB);
		content1.add(loupNoir);
		content1.add(moutonBlancB);
		content1.add(moutonBlanc);
		content1.add(moutonNoirB);
		content1.add(moutonNoir);

		fin.add(alea);

		ok.addActionListener(this);
		alea.addActionListener(this);
		this.setContentPane(content0);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
	}

	public synchronized void actionPerformed(ActionEvent r){
		if(r.getSource() == ok){
			int loupBlancVal=0,nbplantes=0,nombreObstacle=0,terrain=0;
			int loupNoirVal=0,moutonBlancVal=0,moutonNoirVal=0;
			try {
				loupBlancVal=Integer.parseInt(loupBlanc.getText());
				loupNoirVal=Integer.parseInt(loupNoir.getText());
				moutonBlancVal=Integer.parseInt(moutonBlanc.getText());
				moutonNoirVal=Integer.parseInt(moutonNoir.getText());
				terrain=Integer.parseInt(tailleTerrain.getText());
				nbplantes=Integer.parseInt(plantes.getText());
				nombreObstacle=Integer.parseInt(obstacles.getText());
				
				if(loupBlancVal>=0 && terrain>=8 && terrain<201 && nombreObstacle<terrain*terrain){ // on test si les champs remplis correspond à un terrain valide
					this.dispose();
					t = new Thread(new Play()); //permet de générer la simulation 
					t.start();	     
				}
				else{
					String newLine = System.getProperty("line.separator");
					JOptionPane.showMessageDialog(this,
							"Le nombre d'etre doit etre inferieur a 10000."+newLine
							+ "Le terrain doit etre de mimimum 8 case et maximum 200."+newLine
							+"Le nombre d'obstacle dois etre strictement inferieur au nombre de case du terrain:"+newLine
							+"ATTENTION"+newLine
							+"Avec un terrain de " +terrain +" cases , vous ne pouvez pas mettre plus de "+(terrain*terrain-1) +" obstacle total",
							"warning: MoutonLand ",
							JOptionPane.WARNING_MESSAGE); //affichage d'une fenêtre d'alerte si les champs ne sont pas valides
				}

			} catch (NumberFormatException i) {
				JOptionPane.showMessageDialog(this,"Les Champs doivent etre des nombres Entier et obligatoirement rempli.",
						"Erreur",
						JOptionPane.ERROR_MESSAGE);
			}
		}
		if(r.getSource() == alea){
			this.dispose();
			t = new Thread(new Play2());
			t.start();
		}
	}
	
	class Play implements Runnable{
		public  synchronized void run(){
			Moteur moteur;
			int taille=Integer.valueOf(tailleTerrain.getText());
			int obstaclesVal=Integer.valueOf(obstacles.getText());
			
			int nbLoupBlanc=Integer.valueOf(loupBlanc.getText());;
			int nbLoupNoir=Integer.valueOf(loupNoir.getText());
			
			int nbMoutonBlanc=Integer.valueOf(moutonBlanc.getText());
			int nbMoutonNoir=Integer.valueOf(moutonNoir.getText());
			int nbplante=Integer.valueOf(plantes.getText());
			
			
			try {
				
				moteur = new Moteur(taille,taille,obstaclesVal);
				//Fenetre.minimap.reboot();
				moteur.vistesseSimulation=100;
				
				if(nbLoupBlanc>0){
					moteur.creerAlea("Loup",nbLoupBlanc);
				}
				if(nbLoupNoir>0){
					moteur.creerAlea("LoupNoir",nbLoupNoir );
				}
				if(nbMoutonBlanc>0){
					moteur.creerAlea("Mouton", nbMoutonBlanc);
				}
				if(nbMoutonNoir>0){
					moteur.creerAlea("MoutonNoir", nbMoutonNoir);
				}
				if(nbplante>0){
					moteur.creerAlea("Plante", nbplante);
				}
				
				Fenetre fenetre=new Fenetre(moteur);
				
				moteur.laFenetre=fenetre;
				moteur.play=true;
				
				moteur.start();
				
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			if(Integer.valueOf(loupBlanc.getText())>0){
				
				
			}
			
		}
	}
	class Play2 implements Runnable{
		public synchronized void run(){
			Moteur moteur;
			try {
				
				Random random = new Random();
				int taille =random.nextInt(50)+20;
				int obstacles =40*taille/100;
				moteur = new Moteur(taille,taille,obstacles);
				
				moteur.vistesseSimulation=100;
				moteur.creerAlea("Plante", (taille*taille)/3);
				moteur.creerAlea("MoutonNoir",taille);
				moteur.creerAlea("Mouton", taille);
				moteur.creerAlea("Loup",taille/4);
				moteur.creerAlea("LoupNoir",taille/4 );
				Fenetre fenetre=new Fenetre(moteur);				
				moteur.laFenetre=fenetre;
				
				moteur.play=true;
				
				moteur.start();
				
				
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
	}
}