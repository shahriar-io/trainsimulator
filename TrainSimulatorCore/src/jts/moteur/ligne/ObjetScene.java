package jts.moteur.ligne;

import jts.moteur.geometrie.AngleEuler;
import jts.moteur.geometrie.Point;

/**Cette classe représente un objet de scène 3D.
 * 
 * @author Yannick BISIAUX
 *
 */
public class ObjetScene {
	
	private Point point;
	private AngleEuler angle;
	private String nomObjet;
	
	public ObjetScene(Point point, AngleEuler angle, String nomObjet) {
		this.point = point;
		this.angle = angle;
		this.nomObjet = nomObjet;
	}

	public Point getPoint() { return point; }
	
	public AngleEuler getAngle() { return angle; }

	public String getNomObjet() { return nomObjet; }
}
