package jts.moteur.geometrie;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import jts.io.xml.AttributXml;
import jts.io.xml.ElementXml;

/**Cette classe représente l'orientation angulaire d'un solide en trois dimensions.
 * 
 * @author Yannick BISIAUX
 *
 */
public class AngleEuler {
	
	/**Tangage, positif vers le haut*/
	private double theta;
	/**Roulis, positif à droite*/
	private double phi;
	/**Cap, 0 au Nord, 90 à l'Est*/
	private double psi;
	
	public AngleEuler(){
		this(0, 0, 0);
	}
	
	public AngleEuler(double theta, double phi, double psi){
		this.theta = theta;
		this.phi = phi;
		this.psi = psi;
	}
	
	/**Transforme l'angle en son opposé.
	 * 
	 */
	public void opposer(){
		this.theta = -this.theta;
		//this.phi = this.phi;
		this.psi = this.psi - Math.PI;
	}
	
	/**Tangage, positif vers le haut*/
	public double getTheta(){ return this.theta; }
	
	/**Roulis, positif à droite*/
	public double getPhi(){ return this.phi; }
	
	/**Cap, 0 au Nord, 90 à l'Est*/
	public double getPsi(){ return this.psi; }
	
	public void setTheta(double theta){ this.theta = theta; }
	
	public void setPhi(double phi){ this.phi = phi; }
	
	public void setPsi(double psi){ this.psi = psi; }
	
	public void load(DataInputStream dis) throws IOException {
		this.theta = dis.readDouble();
		this.phi = dis.readDouble();
		this.psi = dis.readDouble();
	}

	public void save(DataOutputStream dos) throws IOException {
		dos.writeDouble(this.theta);
		dos.writeDouble(this.phi);
		dos.writeDouble(this.psi);
	}
	
	public ElementXml save(){
		ElementXml element = new ElementXml("Angle");
		element.addAttribut(new AttributXml("theta", Double.toString(theta*180/Math.PI)));
		element.addAttribut(new AttributXml("phi", Double.toString(phi*180/Math.PI)));
		element.addAttribut(new AttributXml("psi", Double.toString(psi*180/Math.PI)));
		return element;
	}
}
