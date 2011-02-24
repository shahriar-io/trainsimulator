package jts;

import jts.ihm.Ihm;
import jts.ihm.InterfaceHommeMachine;

/**Cette classe est le controleur de l'application. Il fait le lien entre les moteurs de l'application :
 * moteur physique, moteur graphique, moteur son, récupération des entrées utilisateurs, moteur réseau.
 * 
 * @author Yannick BISIAUX
 *
 */
public class Controleur implements InterfaceControleur {
	
	private InterfaceHommeMachine ihm;
	
	public Controleur(){
		
	}
	
	public void init(){
		ihm = new Ihm();
		(new PreInitThread()).start();
		ihm.init();
	}
	
	public class PreInitThread extends Thread{
		public void run(){
			ihm.preinit();
		}
	}

	public void boucler() {
		
	}
}
