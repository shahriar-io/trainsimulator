package jts.moteur.ligne.voie.points;

import java.io.IOException;

import jts.io.SauvegardableXml;
import jts.io.xml.AttributXml;
import jts.io.xml.ElementXml;
import jts.moteur.ligne.voie.elements.CourbeElementaire;
import jts.moteur.ligne.voie.elements.Transition;

import org.w3c.dom.Element;

/**Cette classe repr�sente un point de passage particulier, quand la voie se s�pare en deux, sur un aiguillage.
 * 
 * @author Yannick BISIAUX
 *
 */
public class Divergence extends PointPassage implements SauvegardableXml {
	
	/**Indique si l'aiguillage va nous d�vier*/
	private boolean aiguillageEnDivergence;
	/**Indique si l'aiguillage peut d�vier � gauche (sinon � droite)*/
	private boolean typeGauche;
	
	private CourbeElementaire voieDeviee;
	
	public Divergence(boolean typeGauche){
		this(0, 0, 0, 0, typeGauche);
	}
	
	public Divergence(double x, double y, double z, double phi, boolean typeGauche){
		super(x, y, z, phi);
		this.aiguillageEnDivergence = false;
		this.typeGauche = typeGauche;
	}
	
	/**Permet d'ajouter un �l�ment � une divergence. Les �l�ments seront remplis dans l'ordre : element, voieNormale, voieDeviee.
	 * 
	 * @param element
	 */
	public boolean setElement(CourbeElementaire element) {
		boolean set = super.setElement(element);
		if(!set){
			if(this.voieDeviee == null){
				this.voieDeviee = element;
				set = true;
			}
		}
		return set;
	}

	/**Permet de changer l'aiguillage de place.
	 * 
	 */
	public void switcher(){
		aiguillageEnDivergence = !aiguillageEnDivergence;
		System.out.println("Switch !");
	}

	public Transition getNextElement(CourbeElementaire elementCourant) {
		CourbeElementaire next = null;
		boolean sensAller = false;
		
		//D'abord, on d�termine l'�l�ment suivant.
		if(elementCourant.equals(elementBase)){
			if(aiguillageEnDivergence){
				next = voieDeviee;
			} else {
				next = elementConnecte;
			}
		} else if((elementCourant.equals(elementConnecte))&&(!aiguillageEnDivergence)) {
			next = elementBase;
		} else if((elementCourant.equals(voieDeviee))&&(aiguillageEnDivergence)) {
			next = elementBase;
		}
		
		//Ensuite on d�termine le prochain sens de parcours
		if(next != null){
			if (next.getP1().equals(this)){
				sensAller = true;
			} else {
				sensAller = false;
			}
		}
		
		Transition transition = new Transition(sensAller, next);
		return transition;
	}
	
	public boolean isTypeGauche(){ return this.typeGauche; }
	
	public void load(Element element) throws IOException {
		
	}

	/*public void save(String indent, BufferedWriter writer, String nomElement) throws IOException {
		String nomElmForce = "Divergence";
		if(nomElement != null){
			nomElmForce = nomElement;
		}
		
		String type = "d";
		if(typeGauche){
			type = "g";
		}
		
		writer.write(indent + "<" + nomElmForce + " x=\"" + x + "\" y=\"" + y + "\" z=\"" + z + "\" phi=\"" + phi + "\" type=\"" + type + "\"/>");
		writer.newLine();
	}*/
	
	public ElementXml save(){
		String type = "d";
		if(typeGauche){
			type = "g";
		}
		
		ElementXml element = super.save();
		element.setNom("Divergence");
		element.addAttribut(new AttributXml("type", type));
		return element;
	}
}
