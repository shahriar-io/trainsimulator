package jts.ihm.langues.traduction;

import org.w3c.dom.Element;

/**Cette classe représente les textes à charger pour le <code>PanelDemarrage</code>.
 * 
 * @author Yannick BISIAUX
 *
 */
public class TraductionPanelDemarrage {

	private String start;
	private String reglages;
	private String remerciements;
	private String quitter;
	
	public TraductionPanelDemarrage(){
		
	}
	
	public void parse(Element element){
		start = Traduction.tryToLoad(start, element, "start");
		reglages = Traduction.tryToLoad(reglages, element, "reglages");
		remerciements = Traduction.tryToLoad(remerciements, element, "remerciements");
		quitter = Traduction.tryToLoad(quitter, element, "quitter");
	}

	public String getStart() { return start; }

	public String getReglages() { return reglages; }
	
	public String getRemerciements() { return remerciements; }

	public String getQuitter() { return quitter; }
}
