package jts.moteur.ligne.voie.points;

import jts.moteur.geometrie.Point;
import jts.moteur.ligne.voie.elements.CourbeElementaire;
import jts.moteur.ligne.voie.elements.Transition;

/**Cette classe repr�sente un point de passage particulier sur une voie.
 * 
 * @author Yannick BISIAUX
 *
 */
public abstract class PointPassage extends Point {
	
	/**Tout point de passage est reli� � au moins un �l�ment de voie*/
	protected CourbeElementaire elementBase;
	
	public PointPassage(){
		super();
	}
	
	public PointPassage(double x, double y){
		super(x, y);
	}
	
	/**Permet de r�cup�rer l'�l�ment suivant acc�d� par ce point depuis l'�l�ment courant.
	 * 
	 * @param elementCourant �l�ment sur lequel se trouve le train.
	 * @return <code>Transition</code> : le nouvel �l�ment et son sens de parcours.
	 */
	public abstract Transition getNextElement(CourbeElementaire elementCourant);
	
	public CourbeElementaire getElement(){ return this.elementBase; }

	public void setElement(CourbeElementaire element){ this.elementBase = element; }
}
