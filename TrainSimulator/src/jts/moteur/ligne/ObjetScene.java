package jts.moteur.ligne;

import jts.io.xml.AttributXml;
import jts.io.xml.ElementXml;
import jts.moteur.geometrie.AngleEuler;
import jts.moteur.geometrie.Point;

/**Cette classe représente un objet de scène 3D.
 * 
 * @author Yannick BISIAUX
 *
 */
public class ObjetScene {
	
	private Point point;
	private AngleEuler angle;
	private String nomObjet;
	
	public ObjetScene(Point point, AngleEuler angle, String nomObjet) {
		this.point = point;
		this.angle = angle;
		this.nomObjet = nomObjet;
	}

	public Point getPoint() { return point; }
	
	public AngleEuler getAngle() { return angle; }

	public String getNomObjet() { return nomObjet; }
	
	/*public void save(String indent, BufferedWriter writer) throws IOException {
		writer.write(indent + "<Objet nomObjet=\"" + nomObjet + "\">");
		writer.newLine();
		
		//point.save(indent + "\t", writer, "Point");
		point.save().write(indent + "\t", writer);
		angle.save(indent + "\t", writer);
		
		writer.write(indent + "</Objet>");
		writer.newLine();
	}*/
	
	public ElementXml save(){
		ElementXml element = new ElementXml("Objet");
		element.addAttribut(new AttributXml("nomObjet",nomObjet));
		element.addElement(point.save());
		element.addElement(angle.save());
		return element;
	}
}
