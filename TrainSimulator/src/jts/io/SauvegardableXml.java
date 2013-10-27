package jts.io;

import java.io.IOException;

import jts.io.xml.ElementXml;

import org.w3c.dom.Element;

/**Cette interface définit les services rendus par une classe sauvegardable au format XML.
 * Un élément sauvegardable doit avoir un constructeur par défaut.
 * 
 * @author Yannick BISIAUX
 *
 */
public interface SauvegardableXml {

	/**Permet de charger les attributs d'un élément*/
	public void load(Element element) throws IOException;
	
	/**Permet de sauvegarder un élément*/
	//public void save(String indent, BufferedWriter writer, String nomElement) throws IOException;
	public ElementXml save();
}
