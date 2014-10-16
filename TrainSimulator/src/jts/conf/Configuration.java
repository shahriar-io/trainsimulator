package jts.conf;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import jts.Controleur;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;


/**Cette classe représente la configuration du logiciel.
 * 
 * @author Yannick BISIAUX
 *
 */
public class Configuration implements InterfaceConfiguration {
	
	private boolean firstUse;
	private String codeLangue;
	
	private ConfigurationJoystick configurationJoystick;
	private ConfigurationGraphique configurationGraphique;
	
	public Configuration(){
		firstUse = true;
		codeLangue = "fr";
	}
	
	public boolean isFirstUse() { return firstUse; }
	
	public void setFirstUse(boolean firstUse) { this.firstUse = firstUse; }
	
	public String getLangueCode() { return codeLangue; }

	public void setLangueCode(String code) { this.codeLangue = code; }
	
	public ConfigurationJoystick getConfigurationJoystick() { return configurationJoystick; }
	
	public ConfigurationGraphique getConfigurationGraphique() { return configurationGraphique; }
	
	//A mutualiser avec la même dans Traduction
	public static Element getUniqueElement(Element rootElement, String eltName){
		return (Element)rootElement.getElementsByTagName(eltName).item(0);
	}
	
	public void load(File file) throws IOException {
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		try {
			DocumentBuilder db = dbf.newDocumentBuilder();
			Document document = db.parse(file);
			Element root = document.getDocumentElement();
			firstUse = Boolean.parseBoolean(root.getAttribute("firstUse"));
			codeLangue = root.getAttribute("codeLangue");
			configurationJoystick = new ConfigurationJoystick();
			configurationJoystick.load(getUniqueElement(root, "Joystick"));
			configurationGraphique = new ConfigurationGraphique();
			configurationGraphique.load(getUniqueElement(root, "Graphique"));
		} catch (ParserConfigurationException e) {
			Controleur.LOG.fatal("Erreur de parse sur le fichier " + file.toString() + " : " + e.getMessage());
		} catch (SAXException e) {
			Controleur.LOG.fatal("Erreur de SAX sur le fichier " + file.toString() + " : " + e.getMessage());
		}
	}
	
	public void save(File file) throws IOException {
		FileWriter fw = new FileWriter(file);
		BufferedWriter buffer = new BufferedWriter(fw);
		buffer.write("<?xml version=\"1.0\" encoding=\"ISO-8859-1\"?>");
		buffer.newLine();
		buffer.write("<Configuration firstUse=\"" + firstUse + "\" codeLangue=\"" + codeLangue + "\">");
		buffer.newLine();
		configurationJoystick.save(buffer, "  ");
		configurationGraphique.save(buffer, "  ");
		buffer.write("</Configuration>");
		buffer.close();
		fw.close();
	}
}
