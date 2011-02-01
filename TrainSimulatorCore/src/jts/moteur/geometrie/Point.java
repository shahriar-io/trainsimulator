package jts.moteur.geometrie;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import jts.io.Sauvegardable;


/**Représente un point de l'espace.
 * 
 * @author Yannick BISIAUX
 *
 */
public class Point implements Sauvegardable {
	
	public static double DISTANCE_NULLE = 0.01;
	public static double DISTANCE_NULLE_2 = DISTANCE_NULLE*DISTANCE_NULLE;

	private double x;
	private double y;
	private double z;
	
	public Point(){
		this(0, 0);
	}
	
	public Point(double x, double y){
		this(x, y, 0);
	}
	
	public Point(double x, double y, double z){
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	public Point clone(){ return new Point(x, y, z); }
	
	public void setXYZ(double x, double y, double z){
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	public double getX(){ return this.x; }
	
	public double getY(){ return this.y; }
	
	public double getZ(){ return this.z; }
	
	public void setX(double x){ this.x = x; }
	
	public void setY(double y){ this.y = y; }
	
	public void setZ(double z){ this.z = z; }
	
	/**Renvoie la distance euclidienne à un autre point.
	 * 
	 * @param p l'autre point.
	 * @return
	 */
	public double getDistance(Point p){
		double distance = Math.sqrt((p.x - x)*(p.x - x) + (p.y - y)*(p.y - y) + (p.z - z)*(p.z - z));
		return distance;
	}
	
	/**Renvoie la distance euclidienne au carré à un autre point. Permet de gagner en temps d'exécution pour les comparaisons.
	 * 
	 * @param p l'autre point.
	 * @return
	 */
	public double getDistanceCarre(Point p){
		double distance2 = (p.x - x)*(p.x - x) + (p.y - y)*(p.y - y) + (p.z - z)*(p.z - z);
		return distance2;
	}
	
	/**Effectue une transformation (affine) sur un point en le faisant rotationner autour de l'origine puis en le translatant.
	 * 
	 * @param translation point coorconnées de translation
	 * @param rotation angle de rotation en radians
	 */
	public void transformer(Point translation, double rotation){
		double x2 = Math.cos(rotation)*x + Math.sin(rotation)*y + translation.x;
		double y2 = - Math.sin(rotation)*x + Math.cos(rotation)*y + translation.y;
		
		x = x2;
		y = y2;
	}
	
	/**Place le point au milieu des deux autres.
	 * 
	 * @param p1
	 * @param p2
	 */
	public void setBarycentre(Point p1, Point p2){
		this.x = (p1.x + p2.x)/2;
		this.y = (p1.y + p2.y)/2;
		this.z = (p1.z + p2.z)/2;
	}
	
	/**Crée un point en coordonnée polaires à partir de l'axe x=0 (Est).
	 * 
	 * @param rayon
	 * @param theta
	 * @return
	 */
	public static Point createPolar(double rayon, double theta){
		double x = (rayon)*Math.cos(theta);
		double y = (rayon)*Math.sin(theta);
		return new Point(x, y);
	}
	
	/**Crée un point en coordonnée polaires à partir de l'axe y=0 (Nord), positif vers l'Est.
	 * 
	 * @param rayon
	 * @param psi
	 * @return
	 */
	public static Point createPolarNorth(double rayon, double psi){
		double x = (rayon)*Math.sin(psi);
		double y = (rayon)*Math.cos(psi);
		return new Point(x, y);
	}
	
	public String toString(){
		String str = "(" + x + ", " + y + ", " + z + ")";
		return str;
	}
	
	public boolean equals(Point p){
		double dist2 = this.getDistanceCarre(p);
		boolean egal = (dist2<DISTANCE_NULLE_2);
		return egal;
	}

	public void load(DataInputStream dis) throws IOException {
		this.x = dis.readDouble();
		this.y = dis.readDouble();
		this.z = dis.readDouble();
	}

	public void save(DataOutputStream dos) throws IOException {
		dos.writeDouble(this.x);
		dos.writeDouble(this.y);
		dos.writeDouble(this.z);
	}
}
