package jts.moteur.train;

/**Modélise les frottements sur un véhicule (frottement sec + visqueux premier et secon ordre)
 * 
 * @author Yannick BISIAUX
 *
 */
public class Frottement {

	/**Frottement sec*/
	private double k0;
	/**Frottement fluide premier ordre*/
	private double k1;
	/**Frottement fluide second ordre*/
	private double k2;
	
	public Frottement(double k0, double k1, double k2) {
		this.k0 = k0;
		this.k1 = k1;
		this.k2 = k2;
	}
	
	public double getFrottement(double vitesse){
		return k0 + k1*vitesse + k2*vitesse*vitesse;
	}
}
