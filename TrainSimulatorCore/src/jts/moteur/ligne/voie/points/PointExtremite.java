package jts.moteur.ligne.voie.points;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import jts.moteur.geometrie.Point;
import jts.moteur.ligne.voie.elements.CourbeElementaire;
import jts.moteur.ligne.voie.elements.Transition;

/**Cette classe repr�sente un point � l'extr�mit� d'une courbe �l�mentaire.
 * 
 * @author Yannick BISIAUX
 *
 */
public abstract class PointExtremite extends Point {
	
	/**Tout point extr�mit� est reli� � au moins un �l�ment de voie*/
	protected CourbeElementaire elementBase;
	
	/**Inclinaison en roulis de la voie � ce point*/
	protected double phi;
	
	public PointExtremite(){
		this(0, 0, 0);
	}
	
	public PointExtremite(double x, double y, double phi){
		super(x, y);
		this.phi = phi;
	}
	
	/**Permet de r�cup�rer l'�l�ment suivant acc�d� par ce point depuis l'�l�ment courant.
	 * 
	 * @param elementCourant �l�ment sur lequel se trouve le train.
	 * @return <code>Transition</code> : le nouvel �l�ment et son sens de parcours.
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
