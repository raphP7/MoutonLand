package Moteur;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import Moteur.Terrain.Terrain;

public class Moteur {
	public int vitesse;
	private int esperenceDeVieMoyenne=500000; // les loup on 20% de plus et les mouton 20% de moins ,les plantes on 20%
	private int puberte=20; // pourcentage entre 0 et 30 de l'esperenceDeVieMoyenne
	private int maxReproduction=3;
	public Terrain leTerrain;
	public List<Etre> etreMortDecomposer; // ceux a supprimer de la liste lesEtres
	public List<Etre> lesnouveauxVivans; // ceux a ajouter a la liste lesEtres
	private List<Etre> lesEtres;
	private Random random = new Random();
	
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
		return puberte;
	}
	public void setPuberte(int puberte) {
		
		if(puberte<0 || puberte>30){
			this.puberte=20;
		}else{
			this.puberte = puberte;
		}
	}
	public Moteur(int x,int y,int obstacles) throws Exception{
		this.leTerrain=new Terrain(x,y,obstacles);
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
	private List<Etre> copieListPrincipalMelanger(){
		
		List<Etre> temp = new ArrayList<Etre>();
		for (Etre a : lesEtres) {
			temp.add(a);
		}
		Collections.shuffle(temp);
		return temp;
	}
	
	public void supprimerAle(String type,int quantite) throws Exception{
		
		if(!	VerifierArgument(type,quantite)){
			throw new Exception("Attention type d'ETRE inconnu ou quantité nul");
		}
		
		List<Etre> temp = new ArrayList<Etre>();// la list qui va contenir les Etre a supprimer
		List<Etre> melange =copieListPrincipalMelanger(); // rendre aleatoire la selection des etre
		
		int i=0;
		for (Etre a : melange){ // parcourir la list Melanger
			
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
			boolean femelle=random.nextBoolean();
			
				for (int i=0 ; i<quantite ; i++){
					
					if(type.equals("Plante")){
						
						definirEsperanceVie=this.esperenceDeVieMoyenne*20/100;
						definirPuberter=(definirEsperanceVie*this.puberte/100);
						definirEsperanceVie=10;// POUR TEST
						
						Etre a =new Plante(0,0,femelle,definirEsperanceVie,definirPuberter,this.maxReproduction,1000,10);
						temp.add(a);
						
					/*	Etre.class.asSubclass(Mouton.class).getConstructor(Integer.class,Integer.class,Boolean.class,
								Integer.class,Integer.class,Integer.class,Integer.class ).newInstance(0,0,femelle,definirEsperanceVie,
										definirPuberter,this.maxReproduction,1000);
						*/
							
						
						//appel methode pour mettree dans terrain les plantes
					}
					
					else if(type.equals("Loup")){
						
						definirEsperanceVie=(this.esperenceDeVieMoyenne*20/100)+this.esperenceDeVieMoyenne;
						definirPuberter=(definirEsperanceVie*this.puberte/100);
						
						Etre a =new Loup(0,0,femelle,definirEsperanceVie,definirPuberter,this.maxReproduction,500000,3,3,2);
						
						temp.add(a);
					}
					else if (type.equals("Mouton")){
						
						definirEsperanceVie=this.esperenceDeVieMoyenne-(this.esperenceDeVieMoyenne*20/100);
						definirPuberter=(definirEsperanceVie*this.puberte/100);
						
						Etre a =new Mouton(0,0,femelle,definirEsperanceVie,definirPuberter,this.maxReproduction,1000,4,3,2);
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
						
						leTerrain.map[a.positionX][a.positionY].valeurSel = ((EtreMort) a).valeurEnSel;
						etreMortDecomposer.add(a);
					}
				}
				else if (a instanceof EtreVivant){
					
					if(((FonctionsDeBaseVivant)a).isMort()){
						if (a instanceof Animal){
							this.leTerrain.map[a.positionX][a.positionY].setAnimalPresent(null);
						}
						else if (a instanceof Plante){
							this.leTerrain.map[a.positionX][a.positionY].setPlante(null);
						}
						a=new EtreMort((EtreVivant) a);
						lesEtres.set(i, a);
						
					}
					else{
						Etre bebe =((FonctionsDeBaseVivant) a).action(this.leTerrain.map);
						if (bebe != null){
							lesnouveauxVivans.add(bebe);
						}
					}
					
				/*	if (a instanceof Animal){
						
						if(!	((FonctionsDeBaseVivant)a).action(leTerrain.map)){ // l'animal est mort
							a=new EtreMort((EtreVivant) a);
							lesEtres.set(i, a);// remplace l'etreVivant par le nouveau EtreMort dans la list
							this.leTerrain.map[a.positionX][a.positionY].animalPresent=null;
						}
					}
					else if (a instanceof Plante){
						if(!	((Plante) a).action(leTerrain.map)){// la plante est morte
							a=new EtreMort((EtreVivant)a);
							lesEtres.set(i, a);
							this.leTerrain.map[a.positionX][a.positionY].plante=null;
						}
					}
					
					*/
				}
			}
			
			//lesnouveauxVivans.addAll(leTerrain.unTour());// on applique 1 tour au terrain , pour recuperer les nouvelles plantes
			
			for (Etre b : etreMortDecomposer){ // retire les etre decomposer de la liste LesEtres
				lesEtres.remove(b);
			}
			etreMortDecomposer.clear();
			for (Etre c : lesnouveauxVivans){// ajoute les nouveaux Etre a la liste lesEtres
				lesEtres.add(c);
			}
			lesnouveauxVivans.clear();
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
		
		for (Etre d : aSupprimer){
			lesEtres.remove(d);
		}
		this.leTerrain.supprimer(aSupprimer);
	}
	
	public EtreMort mourir(EtreVivant a){
		return new EtreMort(a);
		
	}
	
}
