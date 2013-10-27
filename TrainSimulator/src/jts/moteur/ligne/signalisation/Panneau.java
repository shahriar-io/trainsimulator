package jts.moteur.ligne.signalisation;

import jts.io.xml.ElementXml;
import jts.moteur.ligne.voie.points.PointPassage;

public abstract class Panneau extends PointPassage {
	
	public Panneau(){
		super();
	}
	
	public Panneau(double x, double y, double z, double phi){
		super(x, y, z, phi);
	}
	
	public ElementXml save(){
		ElementXml element = super.save();
		element.setNom("Panneau");
		return element;
	}
}
