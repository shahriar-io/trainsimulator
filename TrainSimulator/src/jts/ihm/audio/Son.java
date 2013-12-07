package jts.ihm.audio;

import java.util.ArrayList;
import java.util.List;

/**Cette classe représente un son que peut jouer l'interface audio.
 * 
 * @author Yannick BISIAUX
 *
 */
public class Son {

	private List<Double> frequences;
	private List<Double> amplitudes;
	private List<Double> phases;
	
	public Son(){
		this.frequences = new ArrayList<Double>();
		this.amplitudes = new ArrayList<Double>();
		this.phases = new ArrayList<Double>();
	}
	
	public void addComposante(double frequence, double amplitude){
		this.addComposante(frequence, amplitude, 0);
	}
	
	public void addComposante(double frequence, double amplitude, double phase){
		this.frequences.add(frequence);
		this.amplitudes.add(amplitude);
		this.phases.add(phase);
	}
	
	public int getNombreElements() { return this.frequences.size(); }
	
	public List<Double> getFrequences() { return frequences; }

	public List<Double> getAmplitudes() { return amplitudes; }

	public List<Double> getPhases() { return phases; }
	
	public void setPhase(int i, double phase){
		if(phase >= 2*Math.PI){
			phases.set(i, phase - 2*Math.PI);
		} else {
			phases.set(i, phase);
		}
	}
}
