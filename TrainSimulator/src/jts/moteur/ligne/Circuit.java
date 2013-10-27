package jts.moteur.ligne;

import java.util.ArrayList;
import java.util.List;

import jts.io.xml.ElementXml;
import jts.moteur.geometrie.Point;
import jts.moteur.ligne.voie.elements.Arc;
import jts.moteur.ligne.voie.elements.CourbeElementaire;
import jts.moteur.ligne.voie.elements.Cubique;
import jts.moteur.ligne.voie.elements.Segment;
import jts.moteur.ligne.voie.points.Divergence;
import jts.moteur.ligne.voie.points.PointPassage;
import jts.moteur.train.Train;

/**Cette classe représente le circuit ferroviaire d'une ligne.
 * 
 * @author Yannick BISIAUX
 *
 */
public class Circuit {

	private List<PointPassage> pointsPassages;
	private List<CourbeElementaire> courbesElementaires;
	private List<Divergence> aiguillages;
	
	private Train trainJoueur;
	
	public Circuit(){
		this.pointsPassages = new ArrayList<PointPassage>();
		this.courbesElementaires = new ArrayList<CourbeElementaire>();
		this.aiguillages = new ArrayList<Divergence>();
	}
	
	public void addPointPassage(PointPassage pointPassage){
		pointsPassages.add(pointPassage);
		if(pointPassage instanceof Divergence){
			aiguillages.add((Divergence)pointPassage);
		}
	}
	
	public void addSegment(int point1, int point2){
		PointPassage p1 = pointsPassages.get(point1);
		PointPassage p2 = pointsPassages.get(point2);
		double theta = Math.asin((p2.getZ() - p1.getZ())/p1.getDistance(p2));
		CourbeElementaire segment = new Segment(p1, p2, theta);
		segment.calculerLongueur();
		courbesElementaires.add(segment);
		p1.setElement(segment);
		p2.setElement(segment);
	}
	
	public void addArc(int point1, int point2, double theta, Point centre, double rayon, double angleOrigine, double ouverture){
		PointPassage p1 = pointsPassages.get(point1);
		PointPassage p2 = pointsPassages.get(point2);
		
		//TODO : Doit-on déduire le theta comme pour le segment ?
		CourbeElementaire arc = new Arc(p1, p2, theta, centre, rayon, angleOrigine, ouverture);
		arc.calculerLongueur();
		courbesElementaires.add(arc);
		p1.setElement(arc);
		p2.setElement(arc);
	}
	
	public void addCubique(int point1, int point2, double theta, double courbureFin, double psiFin, double psi0){
		PointPassage p1 = pointsPassages.get(point1);
		PointPassage p2 = pointsPassages.get(point2);
		
		//TODO : Doit-on déduire le theta comme pour le segment ?
		CourbeElementaire mev = new Cubique(p1, p2, theta, courbureFin, psiFin, psi0);
		mev.calculerLongueur();
		courbesElementaires.add(mev);
		p1.setElement(mev);
		p2.setElement(mev);
	}
	
	public void addCourbeElementaire(CourbeElementaire courbeElementaire){
		this.courbesElementaires.add(courbeElementaire);
	}
	
	public List<PointPassage> getPointsPassages(){ return this.pointsPassages; }
	
	public List<CourbeElementaire> getCourbesElementaires(){ return this.courbesElementaires; }
	
	public List<Divergence> getAiguillages(){ return this.aiguillages; }
	
	/*public void save(String indent, BufferedWriter writer) throws IOException {
		writer.write(indent + "<Circuit>");
		writer.newLine();
		
		writer.write(indent + "\t<PointsPassages>");
		writer.newLine();
		for(PointPassage pointPassage : pointsPassages){
			pointPassage.save(indent + "\t\t", writer, null);
		}
		writer.write(indent + "\t</PointsPassages>");
		writer.newLine();
		
		writer.write(indent + "\t<CourbesElementaires>");
		writer.newLine();
		for(CourbeElementaire courbeElementaire : courbesElementaires){
			int p1 = pointsPassages.indexOf(courbeElementaire.getP1());
			int p2 = pointsPassages.indexOf(courbeElementaire.getP2());
			courbeElementaire.save(indent + "\t\t", writer, p1, p2);
		}
		writer.write(indent + "\t</CourbesElementaires>");
		writer.newLine();
		
		writer.write(indent + "</Circuit>");
		writer.newLine();
	}*/
	
	public ElementXml save(){
		ElementXml element = new ElementXml("Circuit");
		ElementXml ppElement = new ElementXml("PointsPassages");
		for(PointPassage pointPassage : pointsPassages){
			ppElement.addElement(pointPassage.save());
		}
		element.addElement(ppElement);
		ElementXml ceElement = new ElementXml("CourbesElementaires");
		for(CourbeElementaire courbeElementaire : courbesElementaires){
			int p1 = pointsPassages.indexOf(courbeElementaire.getP1());
			int p2 = pointsPassages.indexOf(courbeElementaire.getP2());
			ceElement.addElement(courbeElementaire.save(p1, p2));
		}
		element.addElement(ceElement);
		return element;
	}
	
	public void init(int numeroCourbe, double abscisse, boolean sensDirect){
		this.trainJoueur.initTrain(this.courbesElementaires.get(numeroCourbe), abscisse, sensDirect);
	}
	
	public Train getTrainJoueur() { return trainJoueur; }
	
	public void setTrainJoueur(Train train){ this.trainJoueur = train; }
}
