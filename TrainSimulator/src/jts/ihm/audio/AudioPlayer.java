package jts.ihm.audio;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;
import javax.sound.sampled.FloatControl.Type;

/**Cette classe permet de jouer certaines fréquences.
 * 
 * @author Frédéric Boulanger, Yannick BISIAUX
 *
 */
public class AudioPlayer implements InterfaceAudio {
	
	private float sampleRate = 20500;  // fréquence d'échantillonnage
	private int sampleSize = 16;        // nombre de bits par échantillon : ici un octet
	private boolean bigEndian = true;  // ordre des octets dans l'échantillon (si sampleSize = 16)
	private boolean signed = true;     // les échantillons sont signés (valeurs de -128 à 127)
	
	private double phase;
	
	private int nbOctets;  // nombre d'octets par échantillon
	private int maxVal;    // valeur maximal d'un échantillon
	
	private int j;
	
	private double duree;
	private AudioFormat audioFormat;
	private SourceDataLine line;
	private int bufferSize;
	private byte audioSamples[];
	
	public AudioPlayer(){
		
	}

	public void init(double duree) {
		this.duree = 2*duree;
		audioFormat = new AudioFormat(sampleRate, sampleSize, 1, signed, bigEndian);
		try {
			line = AudioSystem.getSourceDataLine(audioFormat);
			line.open(audioFormat);
			line.start();
			bufferSize = line.getBufferSize();
			//System.out.println("Buffer size : " + bufferSize);
			//audioSamples = new byte[(int)(sampleRate*this.duree)];
			audioSamples = new byte[bufferSize];
		} catch (LineUnavailableException lue) {
			System.out.println("# Erreur : impossible de trouver une ligne de sortie audio au format :");
			System.out.println("#          " + audioFormat);
		}
		
		nbOctets = 0;  // nombre d'octets par échantillon
		maxVal = 0;    // valeur maximal d'un échantillon
		j = 0;
		if (sampleSize == 8) {
			nbOctets = 1;    // 8 bits => 1 octet, max = 127
			maxVal = 0x7F;
		} else if (sampleSize == 16) {
			nbOctets = 2;    // 16 bits => 2 octets, max = 32767
			maxVal = 0x7FFF;
		} else {
			throw new Error("# Nombre de bits par échantillon non supporté : " + sampleSize);
		}
		
		
	}
	
	public void jouerLa(){
		FloatControl control = (FloatControl)line.getControl(Type.MASTER_GAIN);
		control.setValue(0.01f);
		jouerFrequence(440.0);
	}
	
	public void jouerFrequence(double frequence){
		if(line.available()>bufferSize - duree*sampleRate){
			int nbSamples = (int)(sampleRate*duree);

			for (int i=0; i<nbSamples; i++){
				double ratioFreq = frequence/sampleRate;
				int val = (int)(maxVal*Math.sin(2*i*ratioFreq*Math.PI));			
				writeSample(val);
			}
		}
	}
	
	public void jouerFrequences(double frequences[], double amplitudes[]){
		if(line.available()>5000){
			int nbSamples = (int)(sampleRate*duree);

			for (int i=0; i<nbSamples; i++){
				double val = 0;
				for(int k=0; k<frequences.length; k++){
					double ratioFreq = frequences[k]/sampleRate;
					val += (amplitudes[k]*((double)maxVal)*Math.sin(2*((double)i)*ratioFreq*Math.PI + phase));			
				} 
				writeSample((int)val);
			}
		}
	}
	
	public void jouerSon(Son son){
		int nbSamples = (int)(sampleRate*duree);
		double[] phasesf = new double[son.getNombreElements()];
		if(line.available()>2*nbSamples){
			for (int i=1; i<=nbSamples; i++){
				double val = 0;
				for(int k=0; k<son.getNombreElements(); k++){
					double ratioFreq = son.getFrequences().get(k)/sampleRate;
					phasesf[k] = 2*((double)i)*ratioFreq*Math.PI + son.getPhases().get(k);
					val += (son.getAmplitudes().get(k)*((double)maxVal)*Math.sin(phasesf[k]));			
				} 
				writeSample((int)val);
			}
			for(int k=0; k<son.getNombreElements(); k++){
				son.setPhase(k, phasesf[k]);
			}
		}
	}
	
	public void flush(){
		line.write(audioSamples, 0, j);
		j=0;
	}

	private void writeSample(int val) {
		if(j == bufferSize){
			flush();
		}
		
		if (nbOctets == 1) {
			// Un seul octet : il suffit de le mettre dans le tableau
			audioSamples[j++] = (byte)val;
		} else if (bigEndian) {
			// deux octets en big endian : on met l'octet de poids fort en premier
			audioSamples[j++] = (byte)((val & 0xFF00) >> 8);
			audioSamples[j++] = (byte)(val & 0x00FF);
		} else {
			// deux octets en little endian : on met l'octet de poids faible en premier
			audioSamples[j++] = (byte)(val & 0x00FF);
			audioSamples[j++] = (byte)((val & 0xFF00) >> 8);
		}
	}

}
