package jts.ihm.audio;

/**Cette classe représente un son que peut jouer l'interface audio.
 * 
 * @author Yannick BISIAUX
 *
 */
public class Son {
	
	private int frequences[];
	private double amplitudes[];

	public Son(){
		
	}
	
	public Son(int nbFrequences){
		frequences = new int[nbFrequences];
		amplitudes = new double[nbFrequences];
	}
	
	public Son(int frequences[], double amplitudes[]){
		this.frequences = frequences;
		this.amplitudes = amplitudes;
	}
	
	public void setCoupleFrequenceAmplitude(int index, int frequence, double amplitude){
		this.frequences[index] = frequence;
		this.amplitudes[index] = amplitude;
	}

	public int[] getFrequences() { return frequences; }

	public void setFrequences(int[] frequences) { this.frequences = frequences; }

	public double[] getAmplitudes() { return amplitudes; }

	public void setAmplitudes(double[] amplitudes) { this.amplitudes = amplitudes; }
	
	public void normer(){
		double norme = 0;
		for(int i=0; i<amplitudes.length; i++){
			norme += amplitudes[i];
		}
		for(int i=0; i<amplitudes.length; i++){
			amplitudes[i] = amplitudes[i]/norme;
		}
	}
}
