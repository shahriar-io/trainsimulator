package jts.moteur.geometrie;

import java.util.ArrayList;
import java.util.List;

/**Cette classe représente un polygone.
 * 
 * @author Yannick BISIAUX
 *
 */
public abstract class Polygone {
	
	protected List<Point> sommets;
	protected List<Vecteur> vecteurs;
	
	protected Polygone(List<Point> sommets, List<Vecteur> vecteurs){
		this.sommets = sommets;
		this.vecteurs = vecteurs;
	}
	
	public List<Point> getSommets(){ return this.sommets; }
	
	/**Indique si le point p est dans le polygone.
	 * 
	 * @param p
	 * @return
	 */
	public abstract boolean isInside(Point p);
	
	/**Crée un polygone à partir d'une liste de sommets.
	 * 
	 * @param sommets
	 * @return
	 */
	public static Polygone createPolygone(List<Point> sommets){
		List<Vecteur> vecteurs = creerVecteursAssocies(sommets);
		
		boolean convexe = true;
		double dAngle;
		for(int i=0; i<sommets.size()-1; i++){
			dAngle = BasicGeo.liPi(vecteurs.get(i+1).getAngle() - vecteurs.get(i).getAngle());
			if(dAngle<0){
				convexe = false;
				break;
			}
		}
		dAngle = BasicGeo.liPi(vecteurs.get(0).getAngle() - vecteurs.get(vecteurs.size()-1).getAngle());
		if(dAngle<0){
			convexe = false;
		}
		
		Polygone poly;
		if(convexe){
			poly = new PolygoneConvexe(sommets, vecteurs);
		} else {
			poly = new PolygoneConcave(sommets, vecteurs);
			((PolygoneConcave)poly).analyser();
		}
		
		return poly;
	}
	
	/**Crée la liste de vecteurs issue de la différence des sommets donnés.
	 * 
	 * @param sommets
	 * @return
	 */
	protected static List<Vecteur> creerVecteursAssocies(List<Point> sommets){
		List<Vecteur> vecteurs = new ArrayList<Vecteur>();
		for(int i=0; i<sommets.size()-1; i++){
			vecteurs.add(new Vecteur(sommets.get(i), sommets.get(i+1)));
		}
		vecteurs.add(new Vecteur(sommets.get(sommets.size()-1), sommets.get(0)));
		return vecteurs;
	}
}
