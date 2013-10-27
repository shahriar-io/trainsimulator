package jts.ihm.langues.traduction;

import org.w3c.dom.Element;

/**Cette classe représente les textes à charger pour le <code>PanelChoixScenario</code>.
 * 
 * @author Yannick BISIAUX
 *
 */
public class TraductionPanelChoixScenario {
	
	private String ligne;
	private String scenario;
	private String menuPrincipal;
	private String lancement;
	
	public TraductionPanelChoixScenario(){
		
	}
	
	public void parse(Element element){
		ligne = Traduction.tryToLoad(ligne, element, "ligne");
		scenario = Traduction.tryToLoad(scenario, element, "scenario");
		menuPrincipal = Traduction.tryToLoad(menuPrincipal, element, "menuPrincipal");
		lancement = Traduction.tryToLoad(lancement, element, "lancement");
	}
	
	public String getLigne(){ return this.ligne; }
	
	public String getScenario(){ return this.scenario; }

	public String getMenuPrincipal(){ return this.menuPrincipal; }
	
	public String getLancement(){ return this.lancement; }
}
