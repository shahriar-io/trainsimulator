package jts.io;

import java.io.IOException;

import jts.io.xml.ElementXml;

import org.w3c.dom.Element;

/**Cette interface d�finit les services rendus par une classe sauvegardable au format XML.
 * Un �l�ment sauvegardable doit avoir un constructeur par d�faut.
 * 
 * @author Yannick BISIAUX
 *
 */
public interface SauvegardableXml {

	/**Permet de charger les attributs d'un �l�ment*/
	public void load(Element element) throws IOException;
	
	/**Permet de sauvegarder un �l�ment*/
	//public void save(String indent, BufferedWriter writer, String nomElement) throws IOException;
	public ElementXml save();
}
