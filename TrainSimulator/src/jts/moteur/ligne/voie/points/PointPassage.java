package jts.moteur.ligne.voie.points;

import java.io.IOException;
import java.util.List;

import jts.io.SauvegardableXml;
import jts.io.xml.ElementXml;
import jts.moteur.ligne.voie.elements.CourbeElementaire;
import jts.moteur.ligne.voie.elements.Transition;

import org.w3c.dom.Element;

/**Cette classe représente un point de passage particulier sur une voie.
 * 
 * @author Yannick BISIAUX
 *
 */
public class PointPassage extends PointExtremite implements SauvegardableXml {
	
	/**Tout point de passage peut être relié à un autre élément de voie*/
	protected CourbeElementaire elementConnecte;
	
	public PointPassage(){
		super(0, 0, 0, 0);
	}
	
	public PointPassage(double x, double y, double z, double phi){
		super(x, y, z, phi);
	}

	@Override
	public Transition getNextElement(CourbeElementaire elementCourant) {
		CourbeElementaire next = null;
		boolean sensAller = false;
		
		//D'abord, on détermine l'élément suivant.
		if(elementCourant.equals(elementBase)){
			next = elementConnecte;
		} else if(elementCourant.equals(elementConnecte)) {
			next = elementBase;
		}
		
		//Ensuite on détermine le prochain sens de parcours
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
	
	public CourbeElementaire getElementConnecte(){ return this.elementConnecte; }
	
	/**Permet d'ajouter un élément à une divergence. Les éléments seront remplis dans l'ordre : elementBase, elementConnecte.
	 * 
	 * @param element
	 */
	public boolean setElement(CourbeElementaire element){
		boolean set = super.setElement(element);
		if(!set){
			if(this.elementConnecte == null){
				this.elementConnecte = element;
				set = true;
			}
		}
		return set;
	}

	public void load(Element element) throws IOException {
		
	}

	/*public void save(String indent, BufferedWriter writer, String nomElement) throws IOException {
		String nomElmForce = "PointPassage";
		if(nomElement != null){
			nomElmForce = nomElement;
		}
		super.save(indent, writer, nomElmForce);
	}*/
	
	public ElementXml save(){
		ElementXml element = super.save();
		element.setNom("PointPassage");
		return element;
	}
	
	public ElementXml save(List<PointPassage> pointsPassages){
		return this.save();
	}
}
