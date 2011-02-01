package jts.moteur.train;

/**Décrit les aspects mécaniques d'un wagon (accélération, ...)
 * 
 * @author Yannick BISIAUX
 *
 */
public class Mecanique {

	protected double vitesse;//m/s;
	protected double masse;
	protected Frottement frottement;
	
	protected double accelerationMax;//m/s/s;
	protected double freinageMax;//m/s/s;
	
	/*public Mecanique(){
		vitesse = 0;
		masse = 1000;
		frottement = new Frottement(250, 20, 0.9);
		accelerationMax = 2;
		freinageMax = 8;
	}*/
	
	public void accelerer(long ms){
		this.vitesse += ms*accelerationMax/1000;
	}
	
	public void freiner(long ms){
		this.vitesse -= ms*freinageMax/1000;
		if (this.vitesse<0){
			this.vitesse=0;
		}
	}
	
	public double getVitesse(){	return this.vitesse; }
	
	public void appliquerForceMotrice(double force, long ms){
		this.vitesse += (force/masse)*(ms/1000.0);
	}
	
	public void appliquerForceFreinage(long ms){
		double force = frottement.getFrottement(vitesse);
		double dv = (force/masse)*(ms/1000.0);
		if(Math.abs(vitesse)>Math.abs(dv)){
			this.vitesse -= dv;
		} else {
			this.vitesse = 0;
		}
	}
}
