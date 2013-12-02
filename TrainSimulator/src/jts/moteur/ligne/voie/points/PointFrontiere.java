package jts.moteur.ligne.voie.points;

import java.io.IOException;

import jts.io.SauvegardableXml;
import jts.io.xml.ElementXml;
import jts.moteur.ligne.voie.elements.CourbeElementaire;
import jts.moteur.ligne.voie.elements.Transition;

import org.w3c.dom.Element;


/**Cette classe représente un point de passage frontière d'une section.
 * 
 * @author Yannick BISIAUX
 *
 */
public class PointFrontiere extends PointExtremite implements SauvegardableXml {

	protected PointFrontiere connexion;
	
	public PointFrontiere(){
		super(0, 0, 0, 0);
	}
	
	public PointFrontiere(double x, double y, double z, double phi){
		super(x, y, z, phi);
	}
	
	public Transition getNextElement(CourbeElementaire elementCourant, boolean transitionPhysique) {
		CourbeElementaire next = null;
		boolean sensAller = true;
		
		//On ne s'intéresse à l'élément suivant que s'il y a connexion.
		if(connexion != null){
			next = connexion.elementBase;
			if (next.getP1().equals(this)){
				sensAller = true;
			} else {
				sensAller = false;
			}
		}
		
		Transition transition = new Transition(sensAller, next);
		return transition;
	}
	
	public PointFrontiere getConnexion() { return connexion; }

	public void setConnexion(PointFrontiere connexion) { this.connexion = connexion; }
	
	public void load(Element element) throws IOException {
		
	}

	/*public void save(String indent, BufferedWriter writer, String nomElement) throws IOException {
		String nomElmForce = "PointFrontiere";
		if(nomElement != null){
			nomElmForce = nomElement;
		}
		super.save(indent, writer, nomElmForce);
	}*/
	
	public ElementXml save(){
		ElementXml element = super.save();
		element.setNom("PointFrontiere");
		return element;
	}
}
