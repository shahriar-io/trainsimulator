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
	
	/**Calcul le cap du vecteur p1p2.
	 * 
	 * @param p1
	 * @param p2
	 * @return
	 */
	public static double getCap(Point p1, Point p2){
		double cap = Math.atan2(p2.getX() - p1.getX(), p2.getY() - p1.getY());
		cap = li2Pi(cap);
		return cap;
	}
}
