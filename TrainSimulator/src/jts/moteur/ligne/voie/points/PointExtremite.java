package jts.moteur.ligne.voie.points;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import jts.io.SauvegardableXml;
import jts.io.xml.AttributXml;
import jts.io.xml.ElementXml;
import jts.moteur.geometrie.Point;
import jts.moteur.ligne.voie.elements.CourbeElementaire;
import jts.moteur.ligne.voie.elements.Transition;

/**Cette classe représente un point particulier sur une voie.
 * 
 * @author Yannick BISIAUX
 *
 */
public abstract class PointExtremite extends Point implements SauvegardableXml {
	
	/**Tout point de passage est relié à au moins un élément de voie*/
	protected CourbeElementaire elementBase;
	
	/**Inclinaison en roulis de la voie à ce point de passage*/
	protected double phi;
	
	public PointExtremite(){
		super(0, 0, 0);
	}
	
	public PointExtremite(double x, double y, double z, double phi){
		super(x, y, z);
		this.phi = phi;
	}
	
	/**Permet de récupérer l'élément suivant accédé par ce point depuis l'élément courant.
	 * 
	 * @param elementCourant élément sur lequel se trouve le train.
	 * @return <code>Transition</code> : le nouvel élément et son sens de parcours.
	 */
	public abstract Transition getNextElement(CourbeElementaire elementCourant);
	
	public CourbeElementaire getElementBase(){ return this.elementBase; }

	public boolean setElement(CourbeElementaire element){
		boolean set = true;
		if(this.elementBase == null){
			this.elementBase = element;
		} else {
			set = false;
		}
		return set;
	}
	
	public double getPhi(){ return this.phi; }
	
	public void setPhi(double phi){ this.phi = phi; }
	
	public void load(DataInputStream dis) throws IOException {
		super.load(dis);
		this.phi = dis.readDouble();
	}

	public void save(DataOutputStream dos) throws IOException {
		super.save(dos);
		dos.writeDouble(this.phi);
	}
	
	/*public void save(String indent, BufferedWriter writer, String nomElement) throws IOException {
		String nomElmForce = "PointExtremite";
		if(nomElement != null){
			nomElmForce = nomElement;
		}
		writer.write(indent + "<" + nomElmForce + " x=\"" + x + "\" y=\"" + y + "\" z=\"" + z + "\" phi=\"" + phi + "\"/>");
		writer.newLine();
	}*/
	
	public ElementXml save(){
		ElementXml element = super.save();
		element.setNom("PointExtremite");
		element.addAttribut(new AttributXml("phi", Double.toString(phi)));
		return element;
	}
}
