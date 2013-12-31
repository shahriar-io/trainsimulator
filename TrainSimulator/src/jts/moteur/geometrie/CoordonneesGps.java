package jts.moteur.geometrie;

import jts.io.xml.AttributXml;
import jts.io.xml.ElementXml;
import jts.util.BasicConvert;

public class CoordonneesGps {
	
	private static double DEG2M = 40000000/360;

	private double latitude;
	private double longitude;
	private double deg2mLon;
	
	public CoordonneesGps(){
		this(0, 0);
	}
	
	public CoordonneesGps(double latitude, double longitude){
		this.latitude = latitude;
		this.longitude = longitude;
		this.deg2mLon = DEG2M*Math.cos(BasicConvert.degToRad(latitude));
		//Protection pôles nord-sud
		this.deg2mLon = Math.max(deg2mLon, 1);
	}

	public double getLatitude() { return latitude; }

	public double getLongitude() { return longitude; }
	
	public double getX(CoordonneesGps origine) {
		return (this.longitude - origine.longitude)*origine.deg2mLon;
	}
	
	public double getY(CoordonneesGps origine) {
		return (this.latitude - origine.latitude)*DEG2M;
	}

	/**Recalcule des coordonnées GPS à partir de coordonnées X/Y. Fait l'hypothèse
	 * d'une terre plate autour du point d'origine.
	 * 
	 * @param x
	 * @param y
	 * @param origine
	 */
	public void setXY(double x, double y, CoordonneesGps origine){
		latitude = origine.latitude + y/DEG2M;
		longitude = origine.longitude + x/origine.deg2mLon;
	}
	
	public String toString(){
		String str;
		if(latitude>=0){
			str = "N";
		} else {
			str = "S";
		}
		int iLatDeg = (int)Math.abs(latitude);
		int iLatMin = (int)((Math.abs(latitude) - iLatDeg)*60);
		int iLatSec = (int)(((Math.abs(latitude) - iLatDeg)*60 - iLatMin)*60);
		str += iLatDeg + "° " + iLatMin + "'' " + iLatSec + "'";
		str += " ";
		
		if(longitude>=0){
			str += "E";
		} else {
			str += "W";
		}
		int iLonDeg = (int)Math.abs(longitude);
		int iLonMin = (int)((Math.abs(longitude) - iLonDeg)*60);
		int iLonSec = (int)(((Math.abs(longitude) - iLonDeg)*60 - iLonMin)*60);
		
		str += iLonDeg + "° " + iLonMin + "'' " + iLonSec + "'";
		
		return str;
	}
	
	public static void main(String[] args){
		CoordonneesGps orig = new CoordonneesGps(48.5, 2.3);
		System.out.println(orig);
		CoordonneesGps train = new CoordonneesGps(0, 0);
		train.setXY(1000, -100, orig);
		System.out.println(train);
	}
	
	public ElementXml save(){
		ElementXml element = new ElementXml("CoordonneesGps");
		element.addAttribut(new AttributXml("latitude", Double.toString(latitude)));
		element.addAttribut(new AttributXml("longitude",  Double.toString(longitude)));
		return element;
	}
}
