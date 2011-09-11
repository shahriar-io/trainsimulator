package jts.ihm.gui;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import jts.InterfaceControleur;

/**Cette classe sert à fermer proprement le logiciel quand on ferme la fenêtre de l'interface graphique.
 * 
 * @author Yannick BISIAUX
 *
 */
public class EcouteurFermeture extends WindowAdapter {
	
	private InterfaceControleur controleur;
	
	public EcouteurFermeture(InterfaceControleur controleur){
		this.controleur = controleur;
	}

	public void windowClosing(WindowEvent event) {
		controleur.quitter();
	}
}