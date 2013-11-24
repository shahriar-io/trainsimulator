package jts.moteur.ligne.signalisation;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;

import jts.io.xml.AttributXml;
import jts.io.xml.ElementXml;
import jts.moteur.geometrie.Point;


public abstract class PanneauLumineux extends Panneau {
	
	protected List<Lumiere> lumieres;

	public PanneauLumineux(){
		this(0, 0, 0, 0);
	}

	public PanneauLumineux(double x, double y, double z, double phi){
		super(x, y, z, phi);
		this.lumieres = new ArrayList<Lumiere>();
	}
	
	public List<Lumiere> getLumieres() { return this.lumieres; }

	public abstract Signal getSignal(Signal signalAval);
	
	public void draw(Graphics2D g2d, boolean clignotantAllume){
		for(Lumiere lumiere : lumieres){
			Point p = lumiere.getPoint2D();
			double rayon = lumiere.getRayon2D();
			
			if((lumiere.getAllumage()==Allumage.ALLUME)||(lumiere.getAllumage()==Allumage.CLIGNOTANT && clignotantAllume)){
				g2d.setColor(lumiere.getCouleur());
			} else {
				g2d.setColor(Color.DARK_GRAY);
			}
			
			g2d.fillOval((int)(p.getX()-rayon), (int)(p.getY()-rayon), (int)(2*rayon), (int)(2*rayon));
		}
	}
	
	/*public void save(String indent, BufferedWriter writer, String nomElement) throws IOException {
		super.save(indent, writer, "PanneauLumineux");
	}*/
	
	public ElementXml save(){
		ElementXml element = super.save();
		element.setNom("PanneauLumineux");
		element.addAttribut(new AttributXml("type", this.getClass().getCanonicalName()));
		return element;
	}
}
