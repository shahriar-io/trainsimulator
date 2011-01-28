package jts.moteur.ligne.voie.points;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import jts.moteur.geometrie.Point;
import jts.moteur.ligne.voie.elements.CourbeElementaire;
import jts.moteur.ligne.voie.elements.Transition;

/**Cette classe représente un point à l'extrémité d'une courbe élémentaire.
 * 
 * @author Yannick BISIAUX
 *
 */
public abstract class PointExtremite extends Point {
	
	/**Tout point extrémité est relié à au moins un élément de voie*/
	protected CourbeElementaire elementBase;
	
	/**Inclinaison en roulis de la voie à ce point*/
	protected double phi;
	
	public PointExtremite(){
		this(0, 0, 0);
	}
	
	public PointExtremite(double x, double y, double phi){
		super(x, y);
		this.phi = phi;
	}
	
	/**Permet de récupérer l'élément suivant accédé par ce point depuis l'élément courant.
	 * 
	 * @param elementCourant élément sur lequel se trouve le train.
	 * @return <code>Transition</code> : le nouvel élément et son sens de parcours.
	 */
	public abstract Transition getNextElement(CourbeElementaire elementCourant);
	
	public CourbeElementaire getElement(){ return this.elementBase; }

	public void setElement(CourbeElementaire element){ this.elementBase = element; }
	
	public double getPhi(){ return this.phi; }
	
	public void setPhi(double phi){ this.phi = phi; }
	
	public void load(DataInputStream dis) throws IOException {
		super.load(dis);
		this.phi = dis.readDouble();
	}

	public void save(DataOutputStream dos) throws IOException {
		super.save(dos);
		dos.writeDouble(this.phi);
	}
}
