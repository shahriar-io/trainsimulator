package jts.moteur.ligne.signalisation;

public class Signal {
	
	private TypeSignal type;

	public Signal(TypeSignal type){
		this.type = type;
	}
	
	public TypeSignal getTypeSignal(){
		return this.type;
	}
}
