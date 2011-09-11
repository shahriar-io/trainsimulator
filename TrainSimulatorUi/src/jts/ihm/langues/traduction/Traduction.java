package jts.ihm.langues.traduction;

import java.io.File;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

/**Cette classe représente les informations présentes dans un fichier traduction.
 * 
 * @author Yannick BISIAUX
 *
 */
public class Traduction {
	
	private String language;
	private TraductionPanelDemarrage panelDemarrage;
	private TraductionPanelChoixScenario panelChoixScenario;
	private TraductionPanelReglages panelReglages;
	
	public Traduction(){
		this.panelDemarrage = new TraductionPanelDemarrage();
		this.panelChoixScenario = new TraductionPanelChoixScenario();
		this.panelReglages = new TraductionPanelReglages();
	}
	
	public String getLanguage() { return language; }
	
	public TraductionPanelDemarrage getPanelDemarrage() { return panelDemarrage; }
	
	public TraductionPanelChoixScenario getPanelChoixScenario() { return panelChoixScenario; }
	
	public TraductionPanelReglages getPanelReglages() { return panelReglages; }

	/**
	 * 
	 * @param file
	 * @param parseCourt permet de ne parser que les éléments principaux du fichier
	 */
	public void parse(File file, boolean parseCourt){
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		try {
			DocumentBuilder db = dbf.newDocumentBuilder();
			Document document = db.parse(file);
			Element root = document.getDocumentElement();
			language = root.getAttribute("language");
			if(!parseCourt){
				panelDemarrage.parse(getUniqueElement(root, "PanelDemarrage"));
				panelChoixScenario.parse(getUniqueElement(root, "PanelChoixScenario"));
				panelReglages.parse(getUniqueElement(root, "PanelReglages"));
			}
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
	}
	
	public static Element getUniqueElement(Element rootElement, String eltName){
		return (Element)rootElement.getElementsByTagName(eltName).item(0);
	}
	
	public static String tryToLoad(String string, Element element, String attribute){
		String returnString = string;
		
		//On ne retourne le string attribut que si l'élément et l'attribut existent.
		if (element != null){
			String attString = element.getAttribute(attribute);
			if(!attString.equals("")){
				returnString = attString;
			}
		}
		
		return returnString;
	}
}
