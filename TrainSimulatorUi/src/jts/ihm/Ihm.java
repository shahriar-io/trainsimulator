package jts.ihm;

import javax.swing.JFrame;

import jts.ihm.clavier.EcouteurClavier;
import jts.ihm.clavier.InterfaceClavier;
import jts.ihm.gui.Gui;
import jts.ihm.gui.InterfaceGraphique;
import jts.ihm.joystick.GestionnaireJoystick;
import jts.ihm.joystick.InterfaceJoystick;

/**Cette classe est l'interface homme machine par défaut.
 * 
 * @author Yannick BISIAUX
 *
 */
public class Ihm implements InterfaceHommeMachine {
	
	private InterfaceGraphique gui;
	private InterfaceClavier clavier;
	private InterfaceJoystick joystick;
	
	private JFrame frameInit;
	
	public Ihm(){
		
	}
	
	public InterfaceGraphique getInterfaceGraphique() { return gui;	}
	
	public InterfaceClavier getInterfaceClavier() { return clavier;	}

	public InterfaceJoystick getIntefaceJoystick() { return joystick; }

	public void preinit() {
		System.out.println("toto");
		frameInit = new InitFrame();
		System.out.println("titi");
	}

	public void init() {
		clavier = new EcouteurClavier();
		clavier.init();
		joystick = new GestionnaireJoystick();
		joystick.init();
		gui = new Gui();
		gui.init();
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		frameInit.dispose();
	}
}
