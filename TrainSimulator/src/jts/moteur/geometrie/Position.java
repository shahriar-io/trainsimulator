package jts.moteur.geometrie;

import jts.moteur.ligne.voie.elements.CourbeElementaire;
import jts.moteur.ligne.voie.elements.Transition;
import jts.moteur.ligne.voie.points.PointFrontiere;
import jts.moteur.ligne.voie.points.PointExtremite;

/**Cette classe repr�sente la position d'un point sur un rail m�dian par son abscisse curviligne et son �l�ment de r�f�rence.
 * 
 * @author Yannick BISIAUX
 *
 */
public class Position {

	private CourbeElementaire element;
	private boolean sensDirect;
	private double abscisseTotale;
	private double abscisseCourbe;
	
	public Position(PointFrontiere pf){
		this(pf.getElementBase(), 0, true);
	}
	
	public Position(CourbeElementaire element, double abscisse, boolean sensDirect){
		abscisseTotale = 0;
		abscisseCourbe = abscisse;
		
		this.element = element;
		this.sensDirect = sensDirect;
	}
	
	public boolean isSensDirect(){ return sensDirect; }

	public double getAbscisseTotale() {	return abscisseTotale; }

	public double getAbscisseCourbe() { return abscisseCourbe; }
	
	public void addAbscisse(double ds){
		abscisseTotale += ds;
		abscisseCourbe += ds;
	}

	public CourbeElementaire getElement() {
		return element;
	}
	
	/**Permet de r�cup�rer le point et l'angle de la position.
	 * 
	 * @param point le point � r�cup�rer
	 * @param angle l'angle � r�cup�rer
	 * @return [0] <b>true</b> si le bogie change d'�l�ment, <b>false</b> sinon
	 * 		   [1] <b>true</b> si le bogie a d�raill�, <b>false</b> sinon
	 */
	public boolean[] recupererPosition(Point point, AngleEuler angle){
		boolean deraillement = false;
		boolean encoreSurElement = this.element.recupererPosition(point, angle, abscisseCourbe, sensDirect);
		
		if(!encoreSurElement){
			this.abscisseCourbe -= element.getLongueur();
			PointExtremite pivot;
			if(sensDirect){
				pivot = element.getP2();
			} else {
				pivot = element.getP1();
			}
			Transition transition;
			transition = pivot.getNextElement(element, true);
			sensDirect = transition.isSensDirect();
			element = transition.getElement();
			
			//S'il n'y a plus d'�l�ment, le train a d�raill� !
			if(element == null){
				deraillement = true;
			}
		}
		
		boolean[] resultats = {!encoreSurElement, deraillement};
		return resultats;
	}
}
