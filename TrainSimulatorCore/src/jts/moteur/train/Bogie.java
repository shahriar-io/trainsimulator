package jts.moteur.train;

import jts.moteur.geometrie.AngleEuler;
import jts.moteur.geometrie.Point;
import jts.moteur.geometrie.Position;
import jts.moteur.ligne.voie.elements.CourbeElementaire;

/**Cette classe représente un bogie : l'ensemble sur lesquelles sont montées les roues du train.
 * Dans notre modélisation les bogies restent parfaitement sur le rail médian.
 * 
 * @author Yannick BISIAUX
 *
 */
public class Bogie {
	
	private Position position;
	private Point point;
	private AngleEuler angle;
	
	public Bogie(CourbeElementaire element, double abscisse, boolean sensDirect){
		this.position = new Position(element, abscisse, sensDirect);
		this.point = new Point();
		this.angle = new AngleEuler();
	}
	
	public boolean[] avancer(double ds){
		this.position.addAbscisse(ds);
		boolean[] resultats = this.position.recupererPosition(point, angle);
		return resultats;
	}
	
	public Position getPositionCourbe(){ return this.position; }
	
	public Point getPoint(){ return this.point;	}
	
	public AngleEuler getAngle(){ return this.angle; }
}
