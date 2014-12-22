package Moteur.Intelligence;

public class Envie implements Comparable<Envie>{
	
	private Emotion emotion;
	private int valeur;

	public Envie(){
		
	}
	
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

	public void setValeur(int valeur) {
		
		if (valeur>100){
			this.valeur = 100;	
		}
		else if(valeur<0){
			this.valeur = 0;
		}
	}
	
	public Emotion envieLaPlusForte(Envie[] lesEnvies){
		
		Envie tmp=lesEnvies[0];
		int valeur;
		for(int i=0 ; i<lesEnvies.length-1 ; i++){
			
			valeur=tmp.compareTo(lesEnvies[i+1]);
			if(valeur<0){
				tmp=lesEnvies[i+1];
			}
		}
		return tmp.getEmotion();
	}

	@Override
	public int compareTo(Envie arg0){
		
		
		if(arg0.getEmotion().getClass().equals(Emotion.DEPLACEMENT.getClass())){
			
			System.out.println("deplacement dans comparTO : "+arg0.getValeur());
		}
		
		if (this.valeur==arg0.valeur){
			
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
			System.out.println(this.valeur +" == " +arg0.valeur);
			return 0;
			}
		else if (this.valeur>arg0.valeur){
			System.out.println(this.valeur +" > " +arg0.valeur);
			return 1;	
		}
		else{
			System.out.println(this.valeur +" < " +arg0.valeur);
			return -1;
		}
	}
}
