package jts.moteur.ligne.voie.elements;

import jts.io.xml.AttributXml;
import jts.io.xml.ElementXml;
import jts.moteur.geometrie.AngleEuler;
import jts.moteur.geometrie.Point;
import jts.moteur.ligne.voie.points.PointExtremite;

public class Cubique extends CourbeElementaire {
	
	private double courbureFin;
	private double psiFin;
	private double psi0;
	
	public Cubique(){
		this(null, null, 0, 0, Math.PI);
	}
	
	public Cubique(PointExtremite p1, PointExtremite p2, double theta, double courbureFin, double psiFin) {
		super(p1, p2, TypeElement.CUBIQUE, theta);
		this.courbureFin = courbureFin;
		this.psiFin = psiFin;
		this.calculerLongueur();
	}
	
	public Cubique(PointExtremite p1, PointExtremite p2, double theta, double courbureFin, double psiFin, double psi0) {
		this(p1, p2, theta, courbureFin, psiFin);
		this.psi0 = psi0;
	}

	public void calculerLongueur() {
		if(courbureFin!=0){
			longueur = 2*psiFin/courbureFin;
		} else {
			longueur = 0;
		}
	}
	
	public void recupererAngle(AngleEuler angle, double ratio) {
		super.recupererAngle(angle, ratio);
		angle.setPsi(psi0 + courbureFin*Math.pow(ratio*longueur, 2)/(2*longueur));
	}

	public void recupererPoint(Point point, double ratio) {
		point.setX(courbureFin*Math.pow(ratio*longueur, 3)/(6*longueur));
		point.setY(ratio * longueur);
		point.transformer(p1, psi0);
	}
	
	/**Permet d'effectuer une transformation affine sur cet élément.
	 * 
	 * @param translation les coordonnées de la translation
	 * @param rotation l'angle de rotation en radians
	 * @param theta angle de rotation (assiette) en radians
	 */
	public void transformer(Point translation, double rotation, double theta){
		psi0 = rotation;
	}

	public double getCourbureFin() { return courbureFin; }

	public double getPsiFin() { return psiFin; }

	/*public void save(String indent, BufferedWriter writer, int p1, int p2) throws IOException {
		writer.write(indent + "<Cubique p1=\"#" + p1 + "\" p2=\"#" + p2 + "\" courbureFin=\"" + courbureFin + "\" psiFin=\"" + psiFin + "\" psi0=\"" + psi0 + "\"/>");
		writer.newLine();
	}*/
	
	public ElementXml save(int p1, int p2){
		ElementXml element = super.save(p1, p2);
		element.setNom("Cubique");
		element.addAttribut(new AttributXml("courbureFin", Double.toString(courbureFin)));
		element.addAttribut(new AttributXml("psiFin", Double.toString(psiFin)));
		element.addAttribut(new AttributXml("psi0", Double.toString(psi0)));
		return element;
	}

}
