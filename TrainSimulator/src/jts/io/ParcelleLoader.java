package jts.io;

import java.io.File;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import jts.Controleur;
import jts.moteur.geometrie.AngleEuler;
import jts.moteur.geometrie.Point;
import jts.moteur.ligne.ObjetScene;
import jts.moteur.ligne.Parcelle;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class ParcelleLoader {

	public static Parcelle load(File fichier){
		Parcelle parcelle = null;
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		try {
			DocumentBuilder db = dbf.newDocumentBuilder();
			Document document = db.parse(fichier);
			parcelle = parseDocument(document);
		} catch (ParserConfigurationException e) {
			Controleur.LOG.error("Erreur de parse sur le fichier " + fichier.toString() + " : " + e.getMessage());
		} catch (SAXException e) {
			Controleur.LOG.error("Erreur de SAX sur le fichier " + fichier.toString() + " : " + e.getMessage());
		} catch (IOException e) {
			Controleur.LOG.error("Erreur de lecture sur le fichier " + fichier.toString() + " : " + e.getMessage());
		}
		return parcelle;
	}
	
	private static Parcelle parseDocument(Document document){
		Element racine = document.getDocumentElement();
		int xParcelle = Integer.parseInt(racine.getAttribute("x"));
		int yParcelle = Integer.parseInt(racine.getAttribute("y"));
		
		Parcelle parcelle = new Parcelle(xParcelle, yParcelle);
		
		Element objetsNode = (Element)racine.getElementsByTagName("Objets").item(0);
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
						parcelle.addObjet(new ObjetScene(new Point(x, y, z), new AngleEuler(theta*Math.PI/180, 0, psi*Math.PI/180), nomObjet));
					}
				}
			}
		}
		
		return parcelle;
	}
}
