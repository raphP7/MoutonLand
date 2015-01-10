package Moteur;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import Affichage.Fenetre;
import Moteur.Terrain.Terrain;
import Moteur.animauxCarnivores.Loup;
import Moteur.animauxCarnivores.LoupNoir;
import Moteur.animauxHerbivores.Mouton;
import Moteur.animauxHerbivores.MoutonNoir;
/**La class Moteur permet la gestion de la simulation , des varaibles de simulations , ajout suppression d'etres 
 * 
 * @author Raph
 *
 */
public class Moteur {
	public int compteurDeMort;
	public int nbdeTour;
	private int nbEtreDansLesEtres;
	public boolean play;
	public static boolean simulationEnCour;
	private int esperenceDeVieMoyenne=300; // les loup on 20% de plus et les mouton 20% de moins ,les plantes on 20%
	private int pubert=10; // pourcentage entre 0 et 30 de la duree de vie pour devenir puber
	private int maxReproduction=3;
	public Terrain leTerrain;
	private List<Etre> etreMortDecomposer; // ceux a supprimer de la liste lesEtres
	private List<Etre> lesnouveauxVivans; // ceux a ajouter a la liste lesEtres
	private List<Etre> lesEtres;
	private int valeurParDefautPlante=20;
	private Random random = new Random();
	public Fenetre laFenetre;
	public static int vistesseSimulation=10;
	public static int valeurAleatoireReproductionPlante=70;
	
	//public static Fenetre lafenetre;
	
	public int getMaxReproduction() {
		return maxReproduction;
	}
	
	public void setMaxReproduction(int maxReproduction) {
		if (maxReproduction<0){
			maxReproduction=0;
		}
		this.maxReproduction = maxReproduction;
	}
	
	public int getEsperenceDeVieMoyenne() {
		return esperenceDeVieMoyenne;
	}
	
	public void setEsperenceDeVieMoyenne(int esperenceDeVieMoyenne) {
		if (esperenceDeVieMoyenne<0){
			esperenceDeVieMoyenne=2000;
		}
		this.esperenceDeVieMoyenne = esperenceDeVieMoyenne;
	}
	
	public int getPuberte() {
		return pubert;
	}

	public void setPuberte(int puberte) {
		
		if(puberte<0 || puberte>30){
			this.pubert=20;
		}else{
			this.pubert = puberte;
		}
	}

	/**
	 * Constructeur du terrain
	 * 
	 * @param x hauteur du terrain
	 * @param y largeur du terrain
	 * @param obstacles nombre d'obstacles environ voulut
	 * @throws Exception
	 */
	public Moteur(int x,int y,int obstacles) throws Exception{
		this.nbdeTour=0;
		this.play=false;
		this.simulationEnCour=true;
		this.leTerrain =new Terrain(x,y,obstacles);
		this.lesEtres=new ArrayList<Etre>();
		this.lesnouveauxVivans= new ArrayList<Etre>();
		this.etreMortDecomposer=new ArrayList<Etre>();
	}

	private boolean VerifierArgument(String type,int quantite){
		
		if (quantite<1){
			return false;
		}
		boolean aEstUnEtreVivant=false;
		
		for (Vivant vivantTemp : Vivant.values()){
			
			if (type.equals(vivantTemp.toString())){
				aEstUnEtreVivant=true;}
		}
		return aEstUnEtreVivant;
	}
	
	public void supprimerAle(String type,int quantite) throws Exception{
		if(play){
			throw new Exception("Attention imposible d'ajouter quand la simulation est en play");
		}
		
		if(!VerifierArgument(type,quantite)){
			throw new Exception("Attention type d'ETRE inconnu ou quantité nul");
		}
		
		List<Etre> temp = new ArrayList<Etre>();// la list qui va contenir les Etre a supprimer
		Collections.shuffle(lesEtres);
		int i=0;
		for (Etre a : lesEtres){ // parcourir la list Melanger
			
			if (i>=quantite){break;}
			
			if (a.getClass().toString().contains(type.toString())){// recuperer un object du type voulut
				temp.add(a);
			}
			i++;
		}
		suprimer(temp);// suppresion de la liste d'object trouve
	}
	
	public void creerAlea(String type,int quantite) throws Exception{
		if(play){
			throw new Exception("Attention imposible d'ajouter quand la simulation est en play");
		}
		
		if(!	VerifierArgument(type,quantite)){
			throw new Exception("Attention type d'ETRE inconnu ou quantite nul");
		}
		List<Etre> temp = new ArrayList<Etre>();
		
			int definirEsperanceVie;
			int definirPuberter;
			boolean femelle;
			
				for (int i=0 ; i<quantite ; i++){
					
					femelle=random.nextBoolean();
					
					if(type.equals("Plante")){
						
						definirEsperanceVie=this.esperenceDeVieMoyenne;
						definirPuberter=(definirEsperanceVie*this.pubert/100);
						definirEsperanceVie=100;// POUR TEST
						
						Etre a =new Plante(0,0,femelle,definirEsperanceVie,definirPuberter,this.maxReproduction*10,100,getValeurParDefautPlante());
						temp.add(a);
						
					/*	Etre.class.asSubclass(Mouton.class).getConstructor(Integer.class,Integer.class,Boolean.class,
								Integer.class,Integer.class,Integer.class,Integer.class ).newInstance(0,0,femelle,definirEsperanceVie,
										definirPuberter,this.maxReproduction,1000);
						*/
							
						
						//appel methode pour mettree dans terrain les plantes
					}
					
					else if(type.equals("Loup")){
						
						definirEsperanceVie=(this.esperenceDeVieMoyenne*20/100)+this.esperenceDeVieMoyenne;
						definirPuberter=(definirEsperanceVie*this.pubert/100);
						
						Etre a =new Loup(0,0,femelle,definirEsperanceVie,definirPuberter,this.maxReproduction,50,3,3,2);
						
						temp.add(a);
					}
					else if(type.equals("LoupNoir")){
						
						definirEsperanceVie=(this.esperenceDeVieMoyenne*20/100)+this.esperenceDeVieMoyenne;
						definirPuberter=(definirEsperanceVie*this.pubert/100);
						
						Etre a =new LoupNoir(0,0,femelle,definirEsperanceVie,definirPuberter,this.maxReproduction,70,6,3,2);
						
						temp.add(a);
					}
					else if (type.equals("Mouton")){
						
						definirEsperanceVie=this.esperenceDeVieMoyenne-(this.esperenceDeVieMoyenne*20/100);
						definirPuberter=(definirEsperanceVie*this.pubert/100);
						
						Etre a =new Mouton(0,0,femelle,definirEsperanceVie,definirPuberter,this.maxReproduction+2,15,3,3,2);
						temp.add(a);
					}
					else if (type.equals("MoutonNoir")){

						definirEsperanceVie=this.esperenceDeVieMoyenne-(this.esperenceDeVieMoyenne*20/100);
						definirPuberter=(definirEsperanceVie*this.pubert/100);
						
						Etre a =new MoutonNoir(0,0,femelle,definirEsperanceVie,definirPuberter,this.maxReproduction+2,10,4,3,2);
						temp.add(a);
					}
					
					else{
						//impossible grace a la methode VerifierArgument();
					}
				}
		temp=leTerrain.ajouterEtreALeatoire(temp);
		this.ajouter(temp);
	}

	
	public void start() throws Exception{
		while(simulationEnCour){
			play();
		}
		// la simulation a été quitter 
		this.lesEtres.clear();
		this.etreMortDecomposer.clear();
		this.lesnouveauxVivans.clear();
	}
	public void play() throws Exception{
		if(this.play){
			if(this.lesEtres.isEmpty()){
				System.out.println("ici");
				try{// petite pause pour pas que le programme boucle trop vite dans la liste vide
					Thread.sleep(300);
				}
			catch(Exception ex) {
				}
			}
			this.simulation();// demande un depalcement/action pour chaque Etre de la simulation
			this.nbdeTour++;
			this.laFenetre.repaint();
		}else{
			try { // petite pause pour que quand la simulation est en pause , le program ne boucle pas trop vite avec le if(false)
				Thread.sleep(300);
				}
			catch(InterruptedException ex) {
				}
			
		}
	}
	/**
	 * Permet de jouer 1 tour a chaque Etre , un deplacement ainsi qu'une action pour les Animaux
	 * les 3 actions possible suite a un deplacement ou non sont :
	 * ne rien faire , esayer de se reproduire , essayer de manger
	 * ou  l'essais d'une reproduction , ou de manger des sels mineraux pour une plante 
	 * 
	 * @throws Exception
	 */
	public void simulation() throws Exception {

		for (Etre a : lesEtres){
			
			//Etre a =lesEtres.get(i);				
			
			if (a instanceof EtreMort) {
				if (((EtreMort) a).decompositionFini()) {
					//System.out.println("decomposition");
					leTerrain.map[a.positionX][a.positionY].setValeurSel(((EtreMort) a).valeurEnSel);
					etreMortDecomposer.add(a);
					this.compteurDeMort++;
				}
			}
			else if (a instanceof EtreVivant){
				
				if(((EtreVivant)a).isMort(leTerrain.map)){
					
					//decomposition fini
					//System.out.println("decomposition fini");
					a=new EtreMort((EtreVivant) a);
					//lesEtres.set(i, a);
				}
				else{
					Etre bebe =((EtreVivant) a).action(this.leTerrain.map,vistesseSimulation,laFenetre);
					if (bebe != null){
						
						if(bebe instanceof Plante){
							this.leTerrain.ajouterBebePlante(bebe);
						}
						
						lesnouveauxVivans.add(bebe);
					}
				}
			}
		}
		
		//lesnouveauxVivans.addAll(leTerrain.unTour());// on applique 1 tour au terrain , pour recuperer les nouvelles plantes
		
		for (Etre b : etreMortDecomposer){ // retire les etre decomposer de la liste LesEtres
			lesEtres.remove(b);
		}
		etreMortDecomposer.clear();
		
		for (Etre c : lesnouveauxVivans){// ajoute les nouveaux Etre a la liste lesEtres
			//System.out.println("bebe ajouter");
			lesEtres.add(c);
		}
		lesnouveauxVivans.clear();
		
		Fenetre.affichegeTerrain.repaint();
		
	}

	/**Permet d'ajouter des etres a la liste principal des etres de la simulation	 * 
	 * @param aAjouter la list des etres a ajouter a la list des etres
	 * @throws Exception
	 */
	private void ajouter(List<Etre> aAjouter) throws Exception{
		
		if (aAjouter==null || aAjouter.isEmpty()){
			throw new Exception("liste vide");
		}
		
		for (Etre c : aAjouter){// ajoute les nouveaux Etre a la liste lesEtres
			lesEtres.add(c);
		}
	}
	/**Permet de supprimer des etres de la liste principal des etres
	 * 
	 * @param aSupprimer la liste des etres a supprimer 
	 * @throws Exception
	 */
	private void suprimer(List<Etre> aSupprimer) throws Exception{
		
		if (aSupprimer==null || aSupprimer.isEmpty()){
			throw new Exception("liste vide");
		}
		
		for (Etre d : aSupprimer){
			lesEtres.remove(d);
		}
		this.leTerrain.supprimer(aSupprimer);
	}
	
	/**
	 * 
	 * @param a
	 * @return
	 * @throws Exception
	 * 
	 */
	@Deprecated
	public EtreMort mourir(EtreVivant a) throws Exception{
		
		if (a==null){
			throw new Exception("un etre null essaye de Mourir");
		}
		
		return new EtreMort(a);
	}

	
	public  int getValeurParDefautPlante() {
		return valeurParDefautPlante;
	}
	
	public  void setValeurParDefautPlante(int valeurParDefautPlante) {
		if(valeurParDefautPlante<0){
			this.valeurParDefautPlante = 10;
		}
		else{
			this.valeurParDefautPlante = valeurParDefautPlante;
			
			for (Etre d : lesEtres){
				if(d instanceof Plante){
					((Plante) d).setValmax(valeurParDefautPlante);
				}
			}
		}
	}

	
	public int getNbEtreDansLesEtres() {
		return this.lesEtres.size();
	}
	
}
