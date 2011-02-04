package jts.moteur.ligne.voie;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import jts.io.Sauvegardable;
import jts.moteur.geometrie.AngleEuler;
import jts.moteur.geometrie.Point;
import jts.moteur.geometrie.Polygone;
import jts.moteur.ligne.voie.elements.Arc;
import jts.moteur.ligne.voie.elements.CourbeElementaire;
import jts.moteur.ligne.voie.elements.TypeElement;
import jts.moteur.ligne.voie.points.PointExtremite;

/**Cette classe représente une section de voie ferrée.
 * 
 * @author Yannick BISIAUX
 *
 */
public class Section implements Sauvegardable{

	private boolean absolu;
	private Point positionAbsolue;
	private AngleEuler angle;
	private List<PointExtremite> pointsPassages;
	private List<CourbeElementaire> elements;
	private Polygone frontiere;
	
	private String nomObjet;
	
	public Section(){
		this(new Point(), new AngleEuler());
	}
	
	public Section(Point position, AngleEuler angle){
		this.positionAbsolue = position;
		this.angle = angle;
		this.pointsPassages = new ArrayList<PointExtremite>();
		this.elements = new ArrayList<CourbeElementaire>();
		absolu = false;
	}
	
	public Point getPositionAbsolue() { return positionAbsolue;	}
	
	public AngleEuler getAngle(){ return this.angle; }
	
	public void addPoint(PointExtremite point){ this.pointsPassages.add(point);	}

	public List<PointExtremite> getPointsPassages() { return pointsPassages;	}
	
	public void addElement(CourbeElementaire element){ this.elements.add(element);	}

	public List<CourbeElementaire> getElements() { return elements;	}
	
	public void creerFrontiere(List<Point> sommets){
		this.frontiere = Polygone.createPolygone(sommets);
	}

	public Polygone getFrontiere() { return frontiere; }
	
	public String getNomObjet(){ return this.nomObjet; }
	
	public void setNomObjet(String nomObjet){ this.nomObjet = nomObjet; }
	
	/**Translate les éléments dans leur position absolue si nécessaire.
	 * 
	 */
	public void rendreAbsolu(){
		if (!absolu){
			for (PointExtremite pp : pointsPassages){
				pp.transformer(positionAbsolue, angle.getPsi());
			}
			for (CourbeElementaire element : elements){
				if(element.getType().equals(TypeElement.ARC)){
					((Arc)element).transformer(positionAbsolue, angle.getPsi());
				}
			}
			if(frontiere != null){
				for (Point point : frontiere.getSommets()){
					point.transformer(positionAbsolue, angle.getPsi());
				}
			}
		}
		absolu = true;
	}
	
	public boolean isInside(Point p){
		return false;
	}

	public void load(DataInputStream dis) throws IOException {
		//Reconstitution de la position et de l'orientation
		positionAbsolue.load(dis);
		angle.load(dis);
		
		//Reconstitution des points d'entrée
		int nEntree = dis.readShort();
		for(int i=0; i<nEntree; i++){
			/*PointPassage pp = new PointPassage();
			pp.load(dis);
			this.addPoint(pp);*/
		}
		
		//Reconstitution de la frontiere
		//TODO passer le code dans Polygone
		int nFrontiere = dis.readShort();
		List<Point> sommets = new ArrayList<Point>();
		for(int i=0; i<nFrontiere; i++){
			Point point = new Point();
			point.load(dis);
			sommets.add(point);
		}
		frontiere = Polygone.createPolygone(sommets);
	}

	public void save(DataOutputStream dos) throws IOException {
		//Sauvegarde de la position et de l'orientation
		positionAbsolue.save(dos);
		angle.save(dos);
		
		//Sauvegarde des points d'entrée
		dos.writeShort(pointsPassages.size());
		for (PointExtremite pp : pointsPassages){
			pp.save(dos);
		}
		
		//Sauvegarde de la frontiere
		//TODO passer le code dans Polygone
		dos.writeShort(frontiere.getSommets().size());
		for (Point point : frontiere.getSommets()){
			point.save(dos);
		}
	}
}
