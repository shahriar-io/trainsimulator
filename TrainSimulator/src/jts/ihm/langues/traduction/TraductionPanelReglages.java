package jts.ihm.langues.traduction;

import org.w3c.dom.Element;

public class TraductionPanelReglages {
	
	private String choixJoystick;
	private String utilisationJoystick;
	private String interfaceGraphique;
	private String choixLangue;
	private String menuPrincipal;
	private String sauvegarde;

	public TraductionPanelReglages(){
		
	}
	
	public void parse(Element element){
		choixJoystick = Traduction.tryToLoad(choixJoystick, element, "choixJoystick");
		utilisationJoystick = Traduction.tryToLoad(utilisationJoystick, element, "utilisationJoystick");
		interfaceGraphique = Traduction.tryToLoad(interfaceGraphique, element, "interfaceGraphique");
		choixLangue = Traduction.tryToLoad(choixLangue, element, "choixLangue");
		menuPrincipal = Traduction.tryToLoad(menuPrincipal, element, "menuPrincipal");
		sauvegarde = Traduction.tryToLoad(sauvegarde, element, "sauvegarde");
	}
	
	public String getChoixJoystick(){ return this.choixJoystick; }
	
	public String getUtilisationJoystick(){ return this.utilisationJoystick; }
	
	public String getInterfaceGraphique(){ return this.interfaceGraphique; }
	
	public String getChoixLangue(){ return this.choixLangue; }
	
	public String getMenuPrincipal(){ return this.menuPrincipal; }
	
	public String getSauvegarde(){ return this.sauvegarde; }
}
