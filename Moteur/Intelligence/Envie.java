package Moteur.Intelligence;
import java.util.Arrays;

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
	
	public void trierEnvies(Envie[] lesEnvies){
		
		Arrays.sort(lesEnvies);
		/*
		for (int i=0 ; i<lesEnvies.length ; i++){
			
			System.out.println(lesEnvies[i].valeur);
		}
		*/
	}

	@Override
	public int compareTo(Envie arg0) {
		
		if (this.valeur==arg0.valeur){  return 0;}
		else if (this.valeur>arg0.valeur){
			return 1;	
		}
		else{
			return -1;
		}
	}
}
