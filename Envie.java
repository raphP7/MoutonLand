
public class Envie {
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

	public void setValeur(int valeur) {
		
		if (valeur>100){
			this.valeur = 100;	
		}
		else if(valeur<0){
			this.valeur = 0;
		}
	}
}
