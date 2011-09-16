package jts.moteur.ligne;

import jts.moteur.geometrie.Point;

/**Cette classe repr�sente un objet de sc�ne 3D.
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
