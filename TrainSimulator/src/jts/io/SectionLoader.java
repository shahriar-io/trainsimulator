package jts.io;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import jts.log.Log;
import jts.moteur.geometrie.AngleEuler;
import jts.moteur.geometrie.Point;
import jts.moteur.ligne.voie.Section;
import jts.moteur.ligne.voie.elements.Arc;
import jts.moteur.ligne.voie.elements.Cubique;
import jts.moteur.ligne.voie.elements.Segment;
import jts.moteur.ligne.voie.points.Divergence;
import jts.moteur.ligne.voie.points.PointFrontiere;
import jts.moteur.ligne.voie.points.PointExtremite;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class SectionLoader {

	public static Section load(File file, Point position, AngleEuler angle){
		Section section = null;
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		try {
			DocumentBuilder db = dbf.newDocumentBuilder();
			Document document = db.parse(file);
			section = parseDocument(document, position, angle);
		} catch (ParserConfigurationException e) {
			System.out.println("Erreur de parse sur le fichier " + file.toString());
			e.printStackTrace();
		} catch (SAXException e) {
			System.out.println("Erreur de SAX sur le fichier " + file.toString());
			e.printStackTrace();
		} catch (IOException e) {
			System.out.println("Erreur de lecture sur le fichier " + file.toString());
			e.printStackTrace();
		}
		return section;
	}
	
	private static Section parseDocument(Document document, Point position, AngleEuler angle){
		Section section = new Section(position, angle);
		Element racine = document.getDocumentElement();
		
		Element points = (Element)racine.getElementsByTagName("PointsPassages").item(0);
		NodeList pointsNL = points.getChildNodes();
		for(int i=0; i<pointsNL.getLength(); i++){
			if(pointsNL.item(i).getNodeType() == 1){
				Element point = (Element)pointsNL.item(i);
				double x = Double.parseDouble(point.getAttribute("x"));
				double y = Double.parseDouble(point.getAttribute("y"));
				double phi = 0;
				String phiStr = point.getAttribute("phi");
				if(!phiStr.equals("")){
					phi = Double.parseDouble(phiStr);
				}
				
				if(point.getNodeName().equals("PointFrontiere")){
					PointFrontiere pf = new PointFrontiere(x, y, 0, phi);
					section.addPoint(pf);
				} else if(point.getNodeName().equals("Divergence")){
					String type = point.getAttribute("type");
					boolean typeGauche = type.equals("g");
					Divergence div = new Divergence(x, y, 0, 0, typeGauche);
					section.addPoint(div);
				}
			}
		}
		
		Element elements = (Element)racine.getElementsByTagName("Elements").item(0);
		NodeList elementsNL = elements.getChildNodes();
		for(int i=0; i<elementsNL.getLength(); i++){
			if(elementsNL.item(i).getNodeType() == 1){
				Element element = (Element)elementsNL.item(i);
				int index1 = Integer.parseInt(element.getAttribute("p1").split("#")[1]);
				int index2 = Integer.parseInt(element.getAttribute("p2").split("#")[1]);
				//Conversion numéro => index
				PointExtremite p1 = section.getPointsExtremites().get(index1 - 1);
				PointExtremite p2 = section.getPointsExtremites().get(index2 - 1);
				
				if(element.getNodeName().equals("Segment")){
					Segment segment = new Segment(p1, p2, 0);
					section.addElement(segment);
					p1.setElement(segment);
					p2.setElement(segment);
				} else if(element.getNodeName().equals("Arc")){
					Element centre = (Element)element.getChildNodes().item(1);
					double x = Double.parseDouble(centre.getAttribute("x"));
					double y = Double.parseDouble(centre.getAttribute("y"));
					Point pCentre = new Point(x, y);
					
					double rayon = Double.parseDouble(element.getAttribute("rayon"));
					double origine = Double.parseDouble(element.getAttribute("origine"));
					double ouverture = Double.parseDouble(element.getAttribute("ouverture"));
					//System.out.println(origine);
					Arc arc = new Arc(p1, p2, 0, pCentre, rayon, origine, ouverture);
					section.addElement(arc);
					p1.setElement(arc);
					p2.setElement(arc);
				}  else if(element.getNodeName().equals("Cubique")){
					double courbureFin = Double.parseDouble(element.getAttribute("courbureFin"));
					double psiFin = Double.parseDouble(element.getAttribute("psiFin"));
					Cubique mev = new Cubique(p1, p2, 0, courbureFin, psiFin);
					section.addElement(mev);
					p1.setElement(mev);
					p2.setElement(mev);
				}
			}
		}
		
		Element frontiere = (Element)racine.getElementsByTagName("Frontiere").item(0);
		if(frontiere != null){
			NodeList frontiereNL = frontiere.getChildNodes();
			List<Point> sommets = new ArrayList<Point>();
			for(int i=0; i<frontiereNL.getLength(); i++){
				if(frontiereNL.item(i).getNodeType() == 1){
					Element pointNode = (Element)frontiereNL.item(i);
					double x = Double.parseDouble(pointNode.getAttribute("x"));
					double y = Double.parseDouble(pointNode.getAttribute("y"));
					Point point = new Point(x, y);
					sommets.add(point);
				}
			}
			section.creerFrontiere(sommets);
		}
		
		Element objet = (Element)racine.getElementsByTagName("Objet").item(0);
		if(objet != null){
			String nom = objet.getAttribute("nom");
			section.setNomObjet(nom);
		}
		
		Log.getInstance().logWarning("Chargement d'une section " + racine.getAttribute("desc"), false);
		
		return section;
	}
}
