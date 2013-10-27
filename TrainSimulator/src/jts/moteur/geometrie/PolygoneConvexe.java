package jts.moteur.geometrie;

import java.util.List;

/**Cette classe représente un polygone convexe.
 * 
 * @author Yannick BISIAUX
 *
 */
public class PolygoneConvexe extends Polygone {

	protected PolygoneConvexe(List<Point> sommets, List<Vecteur> vecteurs){
		super(sommets, vecteurs);
	}

	/**Indique si le point p est dans le polygone : s'il est à gauche de chaque segment.
	 * 
	 * @param p
	 * @return
	 */
	public boolean isInside(Point p) {
		boolean isInside = true;
		Vecteur vPoint;
		double dAngle;
		
		for(int i=0; i<sommets.size()-1; i++){
			vPoint = new Vecteur(sommets.get(i), p);
			dAngle = BasicGeo.liPi(vPoint.getAngle() - vecteurs.get(i).getAngle());
			if(dAngle<0){
				isInside = false;
				break;
			}
		}
		
		vPoint = new Vecteur(sommets.get(vecteurs.size()-1), p);
		dAngle = BasicGeo.liPi(vPoint.getAngle() - vecteurs.get(vecteurs.size()-1).getAngle());
		if(dAngle<0){
			isInside = false;
		}
		
		return isInside;
	}
}
