package Moteur.Intelligence;

/**
 * Une envie stock le nom d'une emotion et sa valeur d'importance en pourcentage
 * 
 * exemple si Faim et valeur=100 , l'animal a 100% envie de manger
 * et cette envie guidera son choix d'action et de deplacement
 * @author Raph
 *
 */
public class Envie implements Comparable<Envie>{
	
	private Emotion emotion;
	private int valeur;
	
	public Envie(Emotion emotion, int val){
		this.setEmotion(emotion);
		this.setValeur(val);
	}

	public Emotion getEmotion() {
		return emotion;
	}

	public void setEmotion(Emotion emotion) {
		this.emotion = emotion;
	}

	public int getValeur() {
		return valeur;
	}

	public void reduireValeur(int pourcentage){
		if(pourcentage>0 && pourcentage<=100){
			this.setValeur(this.valeur-pourcentage);
		}
		
	}
	public void setValeur(int valeur) {
		
		if (valeur>100){
			this.valeur = 100;	
		}
		else if(valeur<0){
			this.valeur = 0;
		}
		else{
			this.valeur=valeur;
		}
	}
	
	public static Envie envieLaPlusForte(Envie[] lesEnvies){
		
		Envie tmp=lesEnvies[0];
		int valeur;
		for(int i=0 ; i<lesEnvies.length-1 ; i++){
			
			valeur=tmp.compareTo(lesEnvies[i+1]);
			if(valeur<0){
				tmp=lesEnvies[i+1];
			}
		}
		return tmp;
	}

	@Override
	public int compareTo(Envie arg0){
		
		if (this.valeur==arg0.valeur){
			
			// dans une egaliter la peur est prioritaire
			if(this.getEmotion().getClass().equals(Emotion.PEUR.getClass())){
				return 1;
			}
			else if(arg0.getEmotion().getClass().equals(Emotion.PEUR.getClass())){
				return -1;
			}
			if(this.getEmotion().getClass().equals(Emotion.FAIM.getClass())){
				return 1;
			}
			else if(arg0.getEmotion().getClass().equals(Emotion.FAIM.getClass())){
				return -1;
			}
			
			return 0;
			}
		else if (this.valeur>arg0.valeur){
			
			return 1;	
		}
		else{
			
			return -1;
		}
	}
	
	@Override
	public String toString() {
		return "Envie | Emotion : "+emotion.toString()+" , valeur : "+valeur;
	}
}
