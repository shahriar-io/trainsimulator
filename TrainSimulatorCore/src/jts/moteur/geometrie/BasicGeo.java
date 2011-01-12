package jts.moteur.geometrie;

/**Cette classe sert à effectuer une série d'opérations géométriques de base.
 * 
 * @author Yannick BISIAUX
 *
 */
public class BasicGeo {

	/**
	 * 
	 * @param angle une valeur d'angle en radians
	 * @return la même valeur, modulo 2Pi, entre ]-pi, pi]
	 */
	public static double liPi(double angle){
		while(angle>Math.PI){
			angle -=2*Math.PI;
		}
		while(angle<=-Math.PI){
			angle +=2*Math.PI;
		}
		return angle;
	}
	
	/**
	 * 
	 * @param angle une valeur d'angle en radians
	 * @return la même valeur, modulo 2Pi, entre [0, 2pi[
	 */
	public static double li2Pi(double angle){
		while(angle>=2*Math.PI){
			angle -=2*Math.PI;
		}
		while(angle<0){
			angle +=2*Math.PI;
		}
		return angle;
	}
}
