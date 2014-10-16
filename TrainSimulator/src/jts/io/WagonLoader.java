package jts.io;

import java.io.File;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import jts.Controleur;
import jts.moteur.train.Locomotive;
import jts.moteur.train.Wagon;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

public class WagonLoader {
	public static Wagon load(File file){
		Wagon wagon = null;
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		try {
			DocumentBuilder db = dbf.newDocumentBuilder();
			Document document = db.parse(file);
			wagon = parseDocument(document);
		} catch (ParserConfigurationException e) {
			Controleur.LOG.error("Erreur de parse sur le fichier " + file.toString() + " : " + e.getMessage());
		} catch (SAXException e) {
			Controleur.LOG.error("Erreur de SAX sur le fichier " + file.toString() + " : " + e.getMessage());
		} catch (IOException e) {
			Controleur.LOG.error("Erreur de lecture sur le fichier " + file.toString() + " : " + e.getMessage());
		}
		return wagon;
	}
	
	private static Wagon parseDocument(Document document){
		Wagon wagon;
		Element racine = document.getDocumentElement();
		
		Element dimension = (Element)racine.getElementsByTagName("Dimension").item(0);
		float longueur = Float.parseFloat(dimension.getAttribute("longueur"));
		float largeur = Float.parseFloat(dimension.getAttribute("largeur"));
		float hauteur = Float.parseFloat(dimension.getAttribute("hauteur"));

		Element bogies = (Element)racine.getElementsByTagName("Bogies").item(0);
		float empattement = Float.parseFloat(bogies.getAttribute("empattement"));
		
		Element mecanique = (Element)racine.getElementsByTagName("Mecanique").item(0);
		float masse = Float.parseFloat(mecanique.getAttribute("masse"));
		
		if(racine.getNodeName().equals("Locomotive")){
			wagon = new Locomotive(longueur, largeur, hauteur, empattement, masse*1000);
		} else {
			wagon = new Wagon(longueur, largeur, hauteur, empattement, masse*1000);
		}
		
		Element observation = (Element)racine.getElementsByTagName("Observation").item(0);
		float x = Float.parseFloat(observation.getAttribute("x"));
		float y = Float.parseFloat(observation.getAttribute("y"));
		float z = Float.parseFloat(observation.getAttribute("z"));
		
		wagon.setOrientation(x, y, z);
		
		return wagon;
	}
}
