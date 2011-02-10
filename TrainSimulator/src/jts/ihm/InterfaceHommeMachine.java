package jts.ihm;

//import jts.Controleur;
import jts.ihm.clavier.InterfaceClavier;
import jts.ihm.gui.InterfaceGraphique;
import jts.ihm.joystick.GestionnaireJoystick;

public interface InterfaceHommeMachine {
	
	public void afficherEcranDemarrage();
	
	public void detruireEcranDemarrage();
	
	public void initIhm();
	
	//public Controleur getControleur();
	
	public InterfaceGraphique getInterfaceGraphique();
	
	public InterfaceClavier getClavier();
	
	public GestionnaireJoystick getJoystick();
}
