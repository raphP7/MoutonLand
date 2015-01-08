package Moteur.Intelligence;
/**
 * Enumeration avec les emotions
 * 
 * @author Raph
 *
 *
 */
public enum Emotion {

	FAIM ("faim"),
	PEUR ("peur"),
	REPRODUCTION ("reproduction"),
	DEPLACEMENT ("deplacement oisif");

	private String emotion;
	
	Emotion(String emotion){
		this.setEmotion(emotion);
	}

	public String getEmotion() {
		return emotion;
	}

	public void setEmotion(String emotion) {
		this.emotion = emotion;
	}
}
