package jts.ihm.audio;

import java.util.ArrayList;
import java.util.List;

public class InterpolationSon {
	
	private int nbFrequences;
	private Son son;
	
	private List<Son> sons;
	private List<Double> abscisses;
	
	public InterpolationSon(int nbFrequences){
		this.son = new Son();
		for(int i=0; i<nbFrequences; i++){
			son.addComposante(0, 0);
		}
		this.sons = new ArrayList<Son>();
		this.abscisses = new ArrayList<Double>();
		this.nbFrequences = nbFrequences;
	}
	
	public boolean addSon(Son son, double abscisse){
		if(son.getNombreElements() == nbFrequences){
			sons.add(son);
			abscisses.add(abscisse);
			return true;
		} else {
			return false;
		}
	}
	
	public Son getSon(double abscisse){
		double ratio;
		int indice = 0;
		while(indice<abscisses.size()){
			if(abscisse<abscisses.get(indice)){
				break;
			}
			indice ++;
		}
		
		if(indice == 0){
			for(int i=0; i<son.getNombreElements(); i++){
				son.getFrequences().set(i, sons.get(0).getFrequences().get(i));
				son.getAmplitudes().set(i, sons.get(0).getAmplitudes().get(i));
			}
		} else if(indice == abscisses.size()) {
			for(int i=0; i<son.getNombreElements(); i++){
				son.getFrequences().set(i, sons.get(indice-1).getFrequences().get(i));
				son.getAmplitudes().set(i, sons.get(indice-1).getAmplitudes().get(i));
			}
		} else {
			ratio = (abscisse - abscisses.get(indice-1))/(abscisses.get(indice)-abscisses.get(indice-1));
			for(int i=0; i<son.getNombreElements(); i++){
				double frequence = ratio*(sons.get(indice).getFrequences().get(i)-sons.get(indice-1).getFrequences().get(i)) + sons.get(indice-1).getFrequences().get(i);
				son.getFrequences().set(i, frequence);
				double amplitude = ratio*(sons.get(indice).getAmplitudes().get(i)-sons.get(indice-1).getAmplitudes().get(i)) + sons.get(indice-1).getAmplitudes().get(i);
				son.getAmplitudes().set(i, amplitude);
			}
		}
		
		return son;
	}
}
