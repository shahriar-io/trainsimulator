package jts.ihm.audio;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;
import javax.sound.sampled.FloatControl.Type;

/**Cette classe permet de jouer certaines fr�quences.
 * 
 * @author Fr�d�ric Boulanger, Yannick BISIAUX
 *
 */
public class AudioPlayer implements InterfaceAudio {
	
	private float sampleRate = 20500;  // fr�quence d'�chantillonnage
	private int sampleSize = 8;        // nombre de bits par �chantillon : ici un octet
	private boolean bigEndian = true;  // ordre des octets dans l'�chantillon (si sampleSize = 16)
	private boolean signed = true;     // les �chantillons sont sign�s (valeurs de -128 � 127)
	
	private double phase;
	
	private int nbOctets;  // nombre d'octets par �chantillon
	private int maxVal;    // valeur maximal d'un �chantillon
	
	private int j;
	
	private AudioFormat audioFormat;
	private SourceDataLine line;
	private int bufferSize;
	private byte audioSamples1[];
	private byte audioSamples2[];
	
	private boolean writeOn1 = true;
	
	public AudioPlayer(){
		
	}

	public void init() {
		audioFormat = new AudioFormat(sampleRate, sampleSize, 1, signed, bigEndian);
		try {
			line = AudioSystem.getSourceDataLine(audioFormat);
			line.open(audioFormat);
			line.start();
			bufferSize = line.getBufferSize();
			System.out.println("Buffer size : " + bufferSize);
			audioSamples1 = new byte[bufferSize];
			audioSamples2 = new byte[bufferSize];
		} catch (LineUnavailableException lue) {
			System.out.println("# Erreur : impossible de trouver une ligne de sortie audio au format :");
			System.out.println("#          " + audioFormat);
		}
		
		nbOctets = 0;  // nombre d'octets par �chantillon
		maxVal = 0;    // valeur maximal d'un �chantillon
		j = 0;
		if (sampleSize == 8) {
			nbOctets = 1;    // 8 bits => 1 octet, max = 127
			maxVal = 0x7F;
		} else if (sampleSize == 16) {
			nbOctets = 2;    // 16 bits => 2 octets, max = 32767
			maxVal = 0x7FFF;
		} else {
			throw new Error("# Nombre de bits par �chantillon non support� : " + sampleSize);
		}
		
		
	}
	
	public void jouerLa(){
		FloatControl control = (FloatControl)line.getControl(Type.MASTER_GAIN);
		control.setValue(0.01f);
		jouerFrequence(440.0, 1.0);
	}
	
	public void jouerFrequence(double frequence, double duree){
		int nbSamples = (int)(sampleRate*duree);
		
		for (int i=0; i<nbSamples; i++){
			double ratioFreq = frequence/sampleRate;
			int val = (int)(maxVal*Math.sin(2*i*ratioFreq*Math.PI));			
			writeSample(val);
		}
	}
	
	public void jouerFrequences(double frequences[], double amplitudes[], double duree){
		int nbSamples = (int)(sampleRate*duree);
		
		for (int i=0; i<nbSamples; i++){
			double val = 0;
			for(int k=0; k<frequences.length; k++){
				double ratioFreq = frequences[k]/sampleRate;
				val += (amplitudes[k]*maxVal*Math.sin(2*i*ratioFreq*Math.PI + phase));			
			} 
			writeSample((int)val);
		}
	}
	
	public void flush(){
		if (writeOn1){
			line.write(audioSamples1, 0, j);
		} else {
			line.write(audioSamples2, 0, j);
		}
		j=0;
		writeOn1 = !writeOn1;
		/*try {
			Thread.sleep(100);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
	}

	private void writeSample(int val) {
		if(j == bufferSize){
			flush();
		}
		
		byte[] audioSamples;
		if(writeOn1){
			audioSamples = audioSamples1;
		} else {
			audioSamples = audioSamples2;
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
