package Affichage;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;
import java.text.NumberFormat;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;

import Moteur.Moteur;

public class InfoHaut extends JPanel{
	Moteur moteur;
	Timer chrono;
	InfoHaut(int w,int h,final Moteur moteur){
		this.moteur=moteur;
		this.setPreferredSize(new Dimension((int)(w*0.70),(int)(h*0.05)));
		
		final JLabel time=new JLabel();
		time.setText("Time:00:00:00");
		class Time{
			int time;
			public Time(){
				time=0;
			}
			public int getTime() {
				return time;
			}
			public void setTime(int time) {
				this.time = time;
			}
		}
		final Time reference=new Time();//un nouveau objet Time qui contient juste un 
		//int initialise a 0.
		final JLabel etreVivant=new JLabel();
		etreVivant.setText("Etre: xx");
		
		final JLabel etreMort=new JLabel();
		etreMort.setText("Nombre de Mort:xx");
		
		final JLabel nombreDeplacement=new JLabel();
		nombreDeplacement.setText("Nombre Deplacement:0");
		chrono=new Timer(1000,new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				reference.setTime(reference.getTime()+1);//j'incremente le int dans l'objet time.(car le timer se declenche toutes les secondes)
				int seconde=reference.getTime();
				int minute=seconde/60;
				int heur=minute/60;
				seconde-=minute*60;
				minute-=heur*60;
				NumberFormat formatter = new DecimalFormat("00");
				time.setText("<html> Time:"+formatter.format(heur)+":"+formatter.format(minute)
						+":"+formatter.format(seconde)+"</html>");
				nombreDeplacement.setText("<html>Nombre de tour :"+ moteur.nbdeTour +"</html>");
				//etreVivant.setText("<html>Nombre d'Etre :"+ moteur.getNbEtreDansLesEtres() +"</html>");
				//etreMort.setText("<html>Nombre de Mort:"+ moteur.compteurDeMort+"</html>");
			}
		});
		chrono.start();//Je mets en marche le Timer.
		//Jpanel
		JPanel panelTime=new JPanel();
		JPanel panelDeplacement=new JPanel();
		JPanel panelVivant=new JPanel();
		JPanel panelMort=new JPanel();
		
		
		this.setLayout(new BoxLayout(this,BoxLayout.LINE_AXIS));
		panelTime.add(time);
		panelDeplacement.add(nombreDeplacement);
		//panelMort.add(etreMort);
		//panelVivant.add(etreVivant);
		this.add(panelTime);
		this.add(panelDeplacement);
		this.add(panelVivant);
		this.add(panelMort);
		
	}
	public void stopChrono(){//arrete le chrono
		chrono.stop();
		//nombreFourmi.setText("<html>Nombre de Fourmi: <br\\>" +"a ACTUALISER" +"</html>");
	}
	public void startChrono(){
		chrono.start();
	}
}
