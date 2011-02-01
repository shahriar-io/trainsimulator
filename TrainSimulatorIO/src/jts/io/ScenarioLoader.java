package jts.io;

import java.io.File;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import jts.moteur.ligne.CircuitSections;
import jts.moteur.train.Train;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

public class ScenarioLoader {
	
	public static void loadScenario(CircuitSections circuit, File scenario){
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		try {
			DocumentBuilder db = dbf.newDocumentBuilder();
			Document document = db.parse(scenario);
			Element racine = document.getDocumentElement();
			Element initialisation = (Element)racine.getElementsByTagName("Initialisation").item(0);
			int sectionNum = Integer.parseInt(initialisation.getAttribute("sectionNum"));
			double abscisse = Double.parseDouble(initialisation.getAttribute("abscisse"));
			boolean sensDirect = Boolean.parseBoolean(initialisation.getAttribute("sensDirect"));
			Train train = TrainLoader.load(new File("data/trains/Train_Essai.xml"));
			circuit.setTrainJoueur(train);
			circuit.init(sectionNum, abscisse, sensDirect);
			
		} catch (ParserConfigurationException e) {
			System.out.println("Erreur de parse sur le fichier " + scenario.toString());
			e.printStackTrace();
		} catch (SAXException e) {
			System.out.println("Erreur de SAX sur le fichier " + scenario.toString());
			e.printStackTrace();
		} catch (IOException e) {
			System.out.println("Erreur de lecture sur le fichier " + scenario.toString());
			e.printStackTrace();
		}
	}

	public static String[] getNomScenario(File file){
		String[] noms = new String[2];
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		try {
			DocumentBuilder db = dbf.newDocumentBuilder();
			Document document = db.parse(file);
			Element racine = document.getDocumentElement();
			noms[0] = racine.getAttribute("name");
			noms[1] = racine.getAttribute("ligne");
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
		return noms;
	}
}
