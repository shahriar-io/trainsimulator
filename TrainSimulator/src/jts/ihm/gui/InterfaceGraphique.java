package jts.ihm.gui;

import jts.ihm.clavier.EcouteurClavier;
import jts.moteur.ligne.CircuitSections;
import jts.moteur.ligne.Ligne;

public interface InterfaceGraphique {

	public void afficherEcranDemarrage(boolean joystickActif);
	
	public void detruireEcranDemarrage();
	
	public void init(EcouteurClavier clavier);
	
	public void chargerTerrain(CircuitSections circuit);
	
	public void afficherLigne(Ligne ligne);
}
