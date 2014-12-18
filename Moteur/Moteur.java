package Moteur;
import java.util.ArrayList;
import java.util.List;

public class Moteur {
	public int vitesse;
	public boolean play;
	
	public Terrain terrain;
	List<Etre> etreMortDecomposer; // ceux a supprimer de la liste lesEtres
	List<Etre> lesnouveauxVivans; // ceux a ajouter a la liste lesEtres
	
	List<Etre> lesEtres;
	public Moteur(){
		this.lesEtres=new ArrayList<Etre>();
		
		this.lesnouveauxVivans= new ArrayList<Etre>();
		this.etreMortDecomposer=new ArrayList<Etre>();
	}
	public void Creer(String a,int quantite) throws Exception{
		
		if (a.equals("mouton")){
			for( int i=0; i<=quantite ; i++){
				
				lesEtres.add(new Mouton(0,0,true,100,10,3,10,3,2,2));
			}
		}
		else if (a.equals("loup")){
			for( int i=0; i<=quantite ; i++){
				lesEtres.add(new Loup(0,0,true,100,10,3,10,3,2,2));
			}
			
			
		}
		else if (a.equals("plante")){
			for( int i=0; i<=quantite ; i++){
				lesEtres.add(new Plante(0,0,true,100,5,3,80));
			}
		}
		else {
			throw new Exception("Attention type d'ETRE inconnu");
		}
	}

	public void simulation() {
		while (play) {

			for (Etre a : lesEtres) {

				if (a instanceof EtreMort) {

					if (((EtreMort) a).decompositionFini()) {
						terrain.map[a.positionX][a.positionY].valeurSel = ((EtreMort) a).valeurEnSel;
						etreMortDecomposer.add(a);

					}
				}
				else if (a instanceof EtreVivant){
					
					if (a instanceof Animal){
						if(!	((Animal)a).action(terrain.map)){ // l'animal est mort
							
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
			
			lesnouveauxVivans.addAll(terrain.unTour());// on applique 1 tour au terrain , pour recuperer les nouvelles plantes
			
			for (Etre b : etreMortDecomposer){ // retire les etre decomposer de la liste LesEtres
				lesEtres.remove(b);
			}
			for (Etre c : lesnouveauxVivans){// ajoute les nouveaux Etre a la liste lesEtres
				lesEtres.add(c);
			}
			
		}
	}

	
	public EtreMort mourir(EtreVivant a){
		return new EtreMort(a);
		
	}
	
}
