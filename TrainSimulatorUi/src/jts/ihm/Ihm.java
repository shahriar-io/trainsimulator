package jts.ihm;

import jts.InterfaceControleur;
import jts.ihm.clavier.EcouteurClavier;
import jts.ihm.clavier.InterfaceClavier;
import jts.ihm.gui.Gui;
import jts.ihm.gui.InterfaceGraphique;
import jts.ihm.joystick.GestionnaireJoystick;
import jts.ihm.joystick.InterfaceJoystick;
import jts.ihm.langues.DefaultLangueManager;
import jts.ihm.langues.Langue;
import jts.ihm.langues.LangueManager;

/**Cette classe est l'interface homme machine par défaut.
 * 
 * @author Yannick BISIAUX
 *
 */
public class Ihm implements InterfaceHommeMachine {
	
	private InterfaceControleur controleur;
	
	private InterfaceGraphique gui;
	private InterfaceClavier clavier;
	private InterfaceJoystick joystick;
	private LangueManager langueManager;
	
	private InitFrame frameInit;
	
	public Ihm(InterfaceControleur controleur){
		this.controleur = controleur;
	}
	
	public InterfaceControleur getControleur() { return controleur;	}
	
	public InterfaceGraphique getInterfaceGraphique() { return gui;	}
	
	public InterfaceClavier getInterfaceClavier() { return clavier;	}

	public InterfaceJoystick getIntefaceJoystick() { return joystick; }
	
	public LangueManager getLangueManager() { return langueManager; }

	public void preinit() {
		frameInit = new InitFrame(controleur);
	}

	public void init() {
		clavier = new EcouteurClavier();
		clavier.init();
		frameInit.setProgression(20);
		joystick = new GestionnaireJoystick();
		joystick.init();
		frameInit.setProgression(40);
		langueManager = new DefaultLangueManager();
		langueManager.init();
		modifierLangue(Langue.getLangueFromCode(controleur.getConfiguration().getLangueCode()));
		frameInit.setProgression(60);
		try {
			fakeProgression();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		gui = new Gui(this);
		gui.init();
		frameInit.dispose();
	}
	
	public void modifierLangue(Langue langue) {
		langueManager.setLangue(langue);
		langueManager.chargerTraduction();
		controleur.getConfiguration().setLangueCode(langue.getCode());
	}

	private void fakeProgression() throws InterruptedException {
		/*Thread.sleep(500);
		frameInit.setProgression(20);
		Thread.sleep(500);
		frameInit.setProgression(40);
		Thread.sleep(500);
		frameInit.setProgression(60);*/
		Thread.sleep(500);
		frameInit.setProgression(80);
		Thread.sleep(500);
		frameInit.setProgression(100);
		Thread.sleep(500);
	}
}
