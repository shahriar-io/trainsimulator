package jts.moteur.ligne;

import jts.moteur.geometrie.Point;

/**Cette classe représente un objet de scène 3D.
 * 
 * @author Yannick BISIAUX
 *
 */
public class ObjetScene {
	
	private Point point;
	private String nomObjet;
	
	public ObjetScene(Point point, String nomObjet) {
		this.point = point;
		this.nomObjet = nomObjet;
	}

	public Point getPoint() { return point; }

	public String getNomObjet() { return nomObjet; }
}
