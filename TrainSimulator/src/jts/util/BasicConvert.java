package jts.util;

/**Cette classe permet les conversions entre unités.
 * 
 * @author Yannick BISIAUX
 *
 */
public class BasicConvert {

	protected final static double MS_TO_KMH = 3.6;
	
	protected final static double RDS_TO_TRMIN = 9.5493;
	
	protected final static double RAD_TO_DEG = 180/Math.PI;
	
	
	public static double msToKmh(double ms){ return ms*MS_TO_KMH; }
	
	public static double kmhToMs(double kmh){ return kmh/MS_TO_KMH; }
	
	public static double rdsToTrmin(double rds){ return rds*RDS_TO_TRMIN; }
	
	public static double trminToRds(double trMin){ return trMin/RDS_TO_TRMIN; }
	
	public static double radToDeg(double rad){ return rad*RAD_TO_DEG; }
	
	public static double degToRad(double deg){ return deg/RAD_TO_DEG; }
}
