package jts.io;

import java.io.File;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.soap.Node;

import jts.moteur.geometrie.AngleEuler;
import jts.moteur.geometrie.Point;
import jts.moteur.ligne.CircuitSections;
import jts.moteur.ligne.Ligne;
import jts.moteur.ligne.ObjetScene;
import jts.moteur.ligne.voie.Section;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class LigneLoader {

	public static Ligne load(File file){
		Ligne ligne = null;
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		try {
			DocumentBuilder db = dbf.newDocumentBuilder();
			Document document = db.parse(file);
			ligne = parseDocument(document);
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
		return ligne;
	}
	
	private static Ligne parseDocument(Document document){
		Ligne ligne = new Ligne();
		CircuitSections circuit = new CircuitSections();
		ligne.setCircuit(circuit);
		
		Element racine = document.getDocumentElement();
		
		Element circuitNode = (Element)racine.getElementsByTagName("Circuit").item(0);
		Element sections = (Element)circuitNode.getElementsByTagName("Sections").item(0);
		NodeList sectionsNL = sections.getChildNodes();
		for(int i=0; i<sectionsNL.getLength(); i++){
			if(sectionsNL.item(i).getNodeType() == Node.ELEMENT_NODE){
				Element sectionNode = (Element)sectionsNL.item(i);
				
				Element pointNode = (Element)sectionNode.getElementsByTagName("Point").item(0);
				double x = Double.parseDouble(pointNode.getAttribute("x"));
				double y = Double.parseDouble(pointNode.getAttribute("y"));
				double z = Double.parseDouble(pointNode.getAttribute("z"));
				Point point = new Point(x, y, z);
				
				Element angleNode = (Element)sectionNode.getElementsByTagName("Angle").item(0);
				double psi = Double.parseDouble(angleNode.getAttribute("psi"));
				double theta = Double.parseDouble(angleNode.getAttribute("theta"));
				AngleEuler angle = new AngleEuler(theta*Math.PI/180, 0, psi*Math.PI/180);
				
				String type = sectionNode.getAttribute("type");
				Section section = SectionLoader.load(new File("data/sections/" + type + ".xml"), point, angle);
				circuit.addSection(section);
			}
		}
		
		circuit.rendreAbsolu();
		
		Element connexions = (Element)circuitNode.getElementsByTagName("Connexions").item(0);
		NodeList connexionsNL = connexions.getChildNodes();
		for(int i=0; i<connexionsNL.getLength(); i++){
			if(connexionsNL.item(i).getNodeType() == Node.ELEMENT_NODE){
				Element connexionNode = (Element)connexionsNL.item(i);
				
				int index1 = Integer.parseInt(connexionNode.getAttribute("sec1").split("#")[1]);
				int index2 = Integer.parseInt(connexionNode.getAttribute("sec2").split("#")[1]);
				//Conversion numéro => index
				Section sec1 = circuit.getSections().get(index1 - 1);
				Section sec2 = circuit.getSections().get(index2 - 1);
				circuit.essayerLienBidirectionnel(sec1, sec2);
			}
		}
		
		Element objetsNode = (Element)racine.getElementsByTagName("Objets").item(0);
		if(objetsNode!=null){
			NodeList objetsNL = objetsNode.getChildNodes();
			for(int i=0; i<objetsNL.getLength(); i++){
				if(objetsNL.item(i).getNodeType() == Node.ELEMENT_NODE){
					Element objet = (Element)objetsNL.item(i);
					String nomObjet = objet.getAttribute("nomObjet");
					Element point = (Element)objet.getElementsByTagName("Point").item(0);
					double x = Double.parseDouble(point.getAttribute("x"));
					double y = Double.parseDouble(point.getAttribute("y"));
					double z = Double.parseDouble(point.getAttribute("z"));
					ligne.addObjet(new ObjetScene(new Point(x, y, z), nomObjet));
				}
			}
		}
		
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
			System.out.println("Erreur de parse sur le fichier " + file.toString());
			e.printStackTrace();
		} catch (SAXException e) {
			System.out.println("Erreur de SAX sur le fichier " + file.toString());
			e.printStackTrace();
		} catch (IOException e) {
			System.out.println("Erreur de lecture sur le fichier " + file.toString());
			e.printStackTrace();
		}
		return nom;
	}
}
