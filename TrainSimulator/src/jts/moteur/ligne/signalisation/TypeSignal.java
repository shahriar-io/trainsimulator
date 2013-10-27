package jts.moteur.ligne.signalisation;

import java.awt.Color;

public enum TypeSignal {

	LIBRE(Color.GREEN),
	APPROCHE(Color.YELLOW),
	STOP(Color.RED);
	
	private Color couleur;
	
	private TypeSignal(Color couleur){
		this.couleur = couleur;
	}
	
	public Color getCouleur(){ return this.couleur; }
}
