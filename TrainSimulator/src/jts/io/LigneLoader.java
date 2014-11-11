package jts.io;

import java.io.File;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import jts.Controleur;
import jts.moteur.geometrie.AngleEuler;
import jts.moteur.geometrie.CoordonneesGps;
import jts.moteur.geometrie.Point;
import jts.moteur.ligne.Circuit;
import jts.moteur.ligne.Ligne;
import jts.moteur.ligne.ObjetScene;
import jts.moteur.ligne.voie.elements.CourbeElementaire;
import jts.moteur.ligne.voie.points.Divergence;
import jts.moteur.ligne.voie.points.PointPassage;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class LigneLoader {

	public static Ligne load(String nomDossier){
		File file = new File("data/lignes/" + nomDossier + "/" + nomDossier + ".xml");
		Ligne ligne = null;
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		try {
			DocumentBuilder db = dbf.newDocumentBuilder();
			Document document = db.parse(file);
			ligne = parseDocument(document, nomDossier);
		} catch (ParserConfigurationException e) {
			Controleur.LOG.error("Erreur de parse sur le fichier " + file.toString() + " : " + e.getMessage());
		} catch (SAXException e) {
			Controleur.LOG.error("Erreur de SAX sur le fichier " + file.toString() + " : " + e.getMessage());
		} catch (IOException e) {
			Controleur.LOG.error("Erreur de lecture sur le fichier " + file.toString() + " : " + e.getMessage());
		}
		return ligne;
	}
	
	private static Ligne parseDocument(Document document, String nomDossier){
		Ligne ligne = new Ligne(nomDossier);
		Circuit circuit = new Circuit();
		ligne.setCircuit(circuit);
		
		Element racine = document.getDocumentElement();
		
		Element coordGpsNode = (Element)racine.getElementsByTagName("CoordonneesGps").item(0);
		CoordonneesGps origineGps = new CoordonneesGps(
				Double.parseDouble(coordGpsNode.getAttribute("latitude")),
				Double.parseDouble(coordGpsNode.getAttribute("longitude")));
		ligne.setOrigine(origineGps);
		
		Element circuitNode = (Element)racine.getElementsByTagName("Circuit").item(0);
		Element points = (Element)circuitNode.getElementsByTagName("PointsPassages").item(0);
		NodeList pointsNL = points.getChildNodes();
		for(int i=0; i<pointsNL.getLength(); i++){
			if(pointsNL.item(i).getNodeType() == 1){
				Element point = (Element)pointsNL.item(i);
				double x = Double.parseDouble(point.getAttribute("x"));
				double y = Double.parseDouble(point.getAttribute("y"));
				double z = Double.parseDouble(point.getAttribute("z"));
				double phi = Double.parseDouble(point.getAttribute("phi"));
				
				if(point.getNodeName().equals("PointPassage")){
					PointPassage pp = new PointPassage(x, y, z, phi);
					circuit.addPointPassage(pp);
				} else if(point.getNodeName().equals("Divergence")){
					String type = point.getAttribute("type");
					boolean typeGauche = type.equals("g");
					Divergence div = new Divergence(x, y, z, 0, typeGauche);
					circuit.addPointPassage(div);
				}
			}
		}
		//On reboucle pour connecter les aiguillages
		int indiceAiguille = 0;
		for(int i=0; i<pointsNL.getLength(); i++){
			if(pointsNL.item(i).getNodeType() == 1){
				Element point = (Element)pointsNL.item(i);
				if(point.getNodeName().equals("Divergence")){
					NodeList aiguillesNL = point.getElementsByTagName("AiguilleConnectee");
					for(int j=0; j<aiguillesNL.getLength(); j++){
						if(aiguillesNL.item(j).getNodeType() == 1){
							Element aiguille = (Element)aiguillesNL.item(j);
							int indice = Integer.parseInt(aiguille.getAttribute("indice"));
							Divergence aiguilleConnectee = (Divergence)circuit.getPointsPassages().get(indice);
							circuit.getAiguillages().get(indiceAiguille).addAiguilleConnecte(aiguilleConnectee);
						}
					}
					indiceAiguille++;
				}
			}
		}
		Element courbes = (Element)circuitNode.getElementsByTagName("CourbesElementaires").item(0);
		NodeList courbesNL = courbes.getChildNodes();
		for(int i=0; i<courbesNL.getLength(); i++){
			if(courbesNL.item(i).getNodeType() == 1){
				Element courbe = (Element)courbesNL.item(i);
				int p1 = Integer.parseInt(courbe.getAttribute("p1").split("#")[1]);
				int p2 = Integer.parseInt(courbe.getAttribute("p2").split("#")[1]);
				boolean inversionPhi1 = Boolean.parseBoolean(courbe.getAttribute("inversionPhi1"));
				boolean inversionPhi2 = Boolean.parseBoolean(courbe.getAttribute("inversionPhi2"));
				
				if(courbe.getNodeName().equals("Segment")){
					circuit.addSegment(p1, p2);
				} else if(courbe.getNodeName().equals("Arc")){
					Element centre = (Element)courbe.getChildNodes().item(1);
					double x = Double.parseDouble(centre.getAttribute("x"));
					double y = Double.parseDouble(centre.getAttribute("y"));
					Point pCentre = new Point(x, y);
					
					double rayon = Double.parseDouble(courbe.getAttribute("rayon"));
					double origine = Double.parseDouble(courbe.getAttribute("origine"));
					double ouverture = Double.parseDouble(courbe.getAttribute("ouverture"));
					circuit.addArc(p1, p2, 0/*theta ???*/, pCentre, rayon, origine, ouverture);
				} else if(courbe.getNodeName().equals("Cubique")){
					double courbureFin = Double.parseDouble(courbe.getAttribute("courbureFin"));
					double psiFin = Double.parseDouble(courbe.getAttribute("psiFin"));
					double psi0 = Double.parseDouble(courbe.getAttribute("psi0"));
					circuit.addCubique(p1, p2, 0/*theta ???*/, courbureFin, psiFin, psi0);
				}
				
				CourbeElementaire ce = circuit.getCourbesElementaires().get(circuit.getCourbesElementaires().size()-1);
				if(inversionPhi1){
					ce.inverserPhi1();
				}
				if(inversionPhi2){
					ce.inverserPhi2();
				}
			}
		}
		
		/*Element objetsNode = (Element)racine.getElementsByTagName("Objets").item(0);
		if(objetsNode!=null){
			NodeList objetsNL = objetsNode.getChildNodes();
			for(int i=0; i<objetsNL.getLength(); i++){
				if(objetsNL.item(i).getNodeType() == Node.ELEMENT_NODE){
					Element objet = (Element)objetsNL.item(i);
					String nomObjet = objet.getAttribute("nomObjet");
					if(!nomObjet.equals("null")){
						Element point = (Element)objet.getElementsByTagName("Point").item(0);
						double x = Double.parseDouble(point.getAttribute("x"));
						double y = Double.parseDouble(point.getAttribute("y"));
						double z = Double.parseDouble(point.getAttribute("z"));
						Element angle = (Element)objet.getElementsByTagName("Angle").item(0);
						double psi = Double.parseDouble(angle.getAttribute("psi"));
						double theta = Double.parseDouble(angle.getAttribute("theta"));
						ligne.addObjet(new ObjetScene(new Point(x, y, z), new AngleEuler(theta*Math.PI/180, 0, psi*Math.PI/180), nomObjet));
					}
				}
			}
		}*/
		
		ligne.preloadParcelles();
		
		return ligne;
	}
	
	public static String getNomLigne(File file){
		String nom = "Inconnu";
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		try {
			DocumentBuilder db = dbf.newDocumentBuilder();
			Document document = db.parse(file);
			Element racine = document.getDocumentElement();
			nom = racine.getAttribute("name");
		} catch (ParserConfigurationException e) {
			Controleur.LOG.error("Erreur de parse sur le fichier " + file.toString() + " : " + e.getMessage());
		} catch (SAXException e) {
			Controleur.LOG.error("Erreur de SAX sur le fichier " + file.toString() + " : " + e.getMessage());
		} catch (IOException e) {
			Controleur.LOG.error("Erreur de lecture sur le fichier " + file.toString() + " : " + e.getMessage());
		}
		return nom;
	}
}
