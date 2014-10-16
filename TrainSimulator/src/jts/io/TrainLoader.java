package jts.io;

import java.io.File;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import jts.Controleur;
import jts.moteur.train.Locomotive;
import jts.moteur.train.Train;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

public class TrainLoader {
	public static Train load(File file){
		Train train = null;
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		try {
			DocumentBuilder db = dbf.newDocumentBuilder();
			Document document = db.parse(file);
			train = parseDocument(document);
		} catch (ParserConfigurationException e) {
			Controleur.LOG.error("Erreur de parse sur le fichier " + file.toString() + " : " + e.getMessage());
		} catch (SAXException e) {
			Controleur.LOG.error("Erreur de SAX sur le fichier " + file.toString() + " : " + e.getMessage());
		} catch (IOException e) {
			Controleur.LOG.error("Erreur de lecture sur le fichier " + file.toString() + " : " + e.getMessage());
		}
		return train;
	}
	
	private static Train parseDocument(Document document){
		Train train = new Train();
		Element racine = document.getDocumentElement();
		
		Element locomotive = (Element)racine.getElementsByTagName("Locomotive").item(0);
		String type = locomotive.getAttribute("type");
		Locomotive loco = (Locomotive)WagonLoader.load(new File("data/wagons/" + type + ".xml"));
		//train.setLocomotive(loco);
		train.addWagon(loco);
		
		return train;
	}
}
