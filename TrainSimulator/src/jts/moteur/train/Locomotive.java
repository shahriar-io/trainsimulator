package jts.moteur.train;

/**Cette classe repr�sente une locomotive.
 * 
 * @author Yannick BISIAUX
 *
 */
public class Locomotive extends Wagon {
	
	public Locomotive(float longueur, float largeur, float hauteur, float empattement, float masse){
		super(longueur, largeur, hauteur, empattement, masse);
	}

	/**Renvoie la force de traction en kN
	 * 
	 * @param vitesse
	 * @param commandeVitesse
	 * @return
	 */
	public double getForceTraction(double vitesse, double commandeVitesse){
		double tractionMax = 320;//kN
		double puissance = 5600;//kW
		double fTraction;
		if(vitesse>(puissance/tractionMax)){
			fTraction = puissance/vitesse;
		} else {
			fTraction = tractionMax;
		}
		
		return commandeVitesse*fTraction;
	}
}
