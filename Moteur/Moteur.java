package Moteur;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import Affichage.Fenetre;
import Moteur.Terrain.Terrain;
import Moteur.animauxCarnivores.Loup;
import Moteur.animauxHerbivores.Mouton;

public class Moteur {
	private int esperenceDeVieMoyenne=10; // les loup on 20% de plus et les mouton 20% de moins ,les plantes on 20%
	private int pubert=1; // pourcentage entre 0 et 30 de la duree avant puberter
	private int maxReproduction=3;
	public Terrain leTerrain;
	private List<Etre> etreMortDecomposer; // ceux a supprimer de la liste lesEtres
	private List<Etre> lesnouveauxVivans; // ceux a ajouter a la liste lesEtres
	private List<Etre> lesEtres;
	private static int valeurParDefautPlante;
	private Random random = new Random();
	public static Fenetre laFenetre;
	
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

	public Moteur(int x,int y,int obstacles) throws Exception{
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
		
		if(!VerifierArgument(type,quantite)){
			throw new Exception("Attention type d'ETRE inconnu ou quantité nul");
		}
		
		List<Etre> temp = new ArrayList<Etre>();// la list qui va contenir les Etre a supprimer
		
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
						
						definirEsperanceVie=this.esperenceDeVieMoyenne*20/100;
						definirPuberter=(definirEsperanceVie*this.pubert/100);
						definirEsperanceVie=10;// POUR TEST
						
						Etre a =new Plante(0,0,femelle,definirEsperanceVie,definirPuberter,this.maxReproduction,1000,getValeurParDefautPlante());
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
						
						Etre a =new Loup(0,0,femelle,definirEsperanceVie,definirPuberter,this.maxReproduction,5000,3,3,2);
						
						temp.add(a);
					}
					else if (type.equals("Mouton")){
						
						definirEsperanceVie=this.esperenceDeVieMoyenne-(this.esperenceDeVieMoyenne*20/100);
						definirPuberter=(definirEsperanceVie*this.pubert/100);
						
						Etre a =new Mouton(0,0,femelle,definirEsperanceVie,definirPuberter,this.maxReproduction,1000,3,3,2);
						temp.add(a);
					}
					else{
						//impossible grace a la methode VerifierArgument();
					}
					
				}
		temp=leTerrain.ajouterEtreALeatoire(temp);
		this.ajouter(temp);
	}

	public void simulation() throws Exception {
		
		
		for (int i=0; i<lesEtres.size(); i++) {
			
			Etre a =lesEtres.get(i);				
			
			if (a instanceof EtreMort) {
				if (((EtreMort) a).decompositionFini()) {
					//System.out.println("decomposition");
					leTerrain.map[a.positionX][a.positionY].setValeurSel(((EtreMort) a).valeurEnSel);
					etreMortDecomposer.add(a);
				}
			}
			else if (a instanceof EtreVivant){
				
				if(((EtreVivant)a).isMort(leTerrain.map)){
					
					//decomposition fini
					//System.out.println("decomposition fini");
					a=new EtreMort((EtreVivant) a);
					lesEtres.set(i, a);
				}
				else{
					Etre bebe =((EtreVivant) a).action(this.leTerrain.map);
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

	private void ajouter(List<Etre> aAjouter) throws Exception{
		
		if (aAjouter==null || aAjouter.isEmpty()){
			throw new Exception("liste vide");
		}
		
		for (Etre c : aAjouter){// ajoute les nouveaux Etre a la liste lesEtres
			lesEtres.add(c);
		}
	}
	
	private void suprimer(List<Etre> aSupprimer) throws Exception{
		
		if (aSupprimer==null || aSupprimer.isEmpty()){
			throw new Exception("liste vide");
		}
		Collections.shuffle(lesEtres);
		for (Etre d : aSupprimer){
			lesEtres.remove(d);
		}
		this.leTerrain.supprimer(aSupprimer);
	}
	
	public EtreMort mourir(EtreVivant a) throws Exception{
		
		if (a==null){
			throw new Exception("un etre null essaye de Mourir");
		}
		
		return new EtreMort(a);
	}
	
	/**
	 * 
	 * @param taille nombre de souvenir des anciennes position de l'animal
	 */
	public void changerTailleSouvenir(int taille){
		
		for (Etre a : lesEtres){
			((Animal)a).getMouvements().setTaille(taille);
		}
	}

	
	public static int getValeurParDefautPlante() {
		return valeurParDefautPlante;
	}
	

	public static void setValeurParDefautPlante(int valeurParDefautPlante) {
		if(valeurParDefautPlante<0){
			Moteur.valeurParDefautPlante = 10;
		}
		else{
			Moteur.valeurParDefautPlante = valeurParDefautPlante;
		}
	}
}
