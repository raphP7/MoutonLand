package Moteur;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Moteur {
	public int vitesse;
	public boolean play;
	private int esperenceDeVieMoyenne; // les loup on 20% de plus et les mouton 20% de moins ,les plantes on 20%
	private int puberte; // pourcentage entre 0 et 30 de l'esperenceDeVieMoyenne
	private int maxReproduction;
	public Terrain leTerrain=new Terrain(10,10);
	List<Etre> etreMortDecomposer; // ceux a supprimer de la liste lesEtres
	List<Etre> lesnouveauxVivans; // ceux a ajouter a la liste lesEtres
	List<Etre> lesEtres;
	
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
	public Moteur(){
		this.lesEtres=new ArrayList<Etre>();
		
		this.lesnouveauxVivans= new ArrayList<Etre>();
		this.etreMortDecomposer=new ArrayList<Etre>();
	}
	public void CreerAlea(String type,int quantite) throws Exception{
		
		if (quantite<1){
			return ;
		}
		List<Etre> temp = new ArrayList<Etre>();
		boolean aEstUnEtreVivant=false;
		
		for (Vivant vivantTemp : Vivant.values()){
			
			if (type.equals(vivantTemp.toString())){
				
				
				aEstUnEtreVivant=true;}
		}
		if(aEstUnEtreVivant){
			Random random = new Random();
			
			int definirEsperanceVie;
			int definirPuberter;
			boolean femelle=random.nextBoolean();
			
				for (int i=0 ; i<quantite ; i++){
					
					if(type.equals("PLANTE")){
						
						definirEsperanceVie=this.esperenceDeVieMoyenne*20/100;
						definirPuberter=(definirEsperanceVie*this.puberte/100);
						
						Etre a =new Plante(0,0,femelle,definirEsperanceVie,definirPuberter,this.maxReproduction,1000);
						temp.add(a);
						
						//appel methode pour mettree dans terrain les plantes
					}
					
					if(type.equals("LOUP")){
						
						definirEsperanceVie=(this.esperenceDeVieMoyenne*20/100)+this.esperenceDeVieMoyenne;
						definirPuberter=(definirEsperanceVie*this.puberte/100);
						
						Etre a =new Loup(0,0,femelle,definirEsperanceVie,definirPuberter,this.maxReproduction,1000,4,3,2);
						
						temp.add(a);
					}
					else if (type.equals("MOUTON")){
						
						definirEsperanceVie=this.esperenceDeVieMoyenne-(this.esperenceDeVieMoyenne*20/100);
						definirPuberter=(definirEsperanceVie*this.puberte/100);
						
						Etre a =new Mouton(0,0,femelle,definirEsperanceVie,definirPuberter,this.maxReproduction,1000,4,3,2);
						temp.add(a);
					}
					
				}
				temp=leTerrain.ajouterEtreALeatoire(temp);
			
		}
		else {
			throw new Exception("Attention type d'ETRE inconnu");
		}
		
		this.ajouter(temp);
	}

	public void simulation() {
		while (play) {

			for (Etre a : lesEtres) {

				if (a instanceof EtreMort) {

					if (((EtreMort) a).decompositionFini()) {
						leTerrain.map[a.positionX][a.positionY].valeurSel = ((EtreMort) a).valeurEnSel;
						etreMortDecomposer.add(a);

					}
				}
				else if (a instanceof EtreVivant){
					
					if (a instanceof Animal){
						if(!	((Animal)a).action(leTerrain.map)){ // l'animal est mort
							
							a=new EtreMort((EtreVivant)a);
						}
					}
					else if (a instanceof Plante){
						if(!	((Plante) a).unTour()){// la plante est morte
							a=new EtreMort((EtreVivant)a);
						}
					}
				}
			}
			
			lesnouveauxVivans.addAll(leTerrain.unTour());// on applique 1 tour au terrain , pour recuperer les nouvelles plantes
			
			for (Etre b : etreMortDecomposer){ // retire les etre decomposer de la liste LesEtres
				lesEtres.remove(b);
			}
			for (Etre c : lesnouveauxVivans){// ajoute les nouveaux Etre a la liste lesEtres
				lesEtres.add(c);
			}
			
		}
	}

	private void ajouter(List<Etre> aAjouter){
		
		for (Etre c : aAjouter){// ajoute les nouveaux Etre a la liste lesEtres
			lesEtres.add(c);
		}
	}
	public EtreMort mourir(EtreVivant a){
		return new EtreMort(a);
		
	}
	
}
