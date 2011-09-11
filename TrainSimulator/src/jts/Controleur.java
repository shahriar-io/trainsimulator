package jts;

import java.io.File;
import java.io.IOException;

import jts.conf.Configuration;
import jts.conf.InterfaceConfiguration;
import jts.ihm.Ihm;
import jts.ihm.InterfaceHommeMachine;
import jts.ihm.gui.Gui;
import jts.io.LigneLoader;
import jts.io.ScenarioLoader;
import jts.log.Log;
import jts.log.LogMode;
import jts.moteur.MoteurPhysique;
import jts.moteur.ligne.Ligne;

/**Cette classe est le controleur de l'application. Il fait le lien entre les moteurs de l'application :
 * moteur physique, moteur graphique, moteur son, récupération des entrées utilisateurs, moteur réseau.
 * 
 * @author Yannick BISIAUX
 *
 */
public class Controleur implements InterfaceControleur {
	
	/**Duree de boucle en millisecondes*/
	private static long DUREE = 50;
	/**Fichier de configuration*/
	private static File CONFIGURATION_FILE = new File("conf.xml");
	
	private InterfaceConfiguration configuration;
	private InterfaceHommeMachine ihm;
	private MoteurPhysique moteurPhysique;
	
	public Controleur(){
		
	}
	
	public void init(){
		try {
			Log.init(new File("log.txt"), LogMode.WARNING);
		} catch (IOException e) {
			System.out.println("Impossible de créer un fichier de log");
			e.printStackTrace();
		}
		configuration = new Configuration();
		ihm = new Ihm(this);
		ihm.preinit();
		(new PreInitThread()).start();
	}
	
	public class PreInitThread extends Thread{
		public void run(){
			try {
				configuration.load(CONFIGURATION_FILE);
			} catch (IOException e) {
				Log.getInstance().logInfo("Unable to load configuration");
			}
			ihm.init();
		}
	}
	
	public InterfaceConfiguration getConfiguration(){ return this.configuration; }
	
	public void lancerSimu(File fichierLigne, File fichierScenario){
		this.moteurPhysique = new MoteurPhysique(DUREE);
		
		Log.getInstance().logInfo("**********  Chargement de la ligne  **********");
		Ligne ligne = LigneLoader.load(fichierLigne);
		this.moteurPhysique.setLigne(ligne);
		
		Log.getInstance().logInfo("**********  Chargement du scénario  **********");
		ScenarioLoader.loadScenario(ligne.getCircuit(), fichierScenario);
		
		this.ihm.getInterfaceGraphique().afficherEcranJeu();
		((Gui)this.ihm.getInterfaceGraphique()).chargerTerrain(ligne.getCircuit());
		
		Thread gb = new GrandeBoucle(this, DUREE);
		gb.start();
	}

	public void boucler() {
		//float[] valeurJoystick = this.ihm.getIntefaceJoystick().getValeursVolantFrein();
		boolean[] touchesClavier = this.ihm.getInterfaceClavier().getTouchePressee();
		if(configuration.getConfigurationJoystick().isUseJoystick()){
			ihm.getIntefaceJoystick().refreshValeurs();
			this.moteurPhysique.setDeltaCommandeVolant(-ihm.getIntefaceJoystick().getValeurs().getAxe(0));
			this.moteurPhysique.setDeltaCommandeFrein(0);
		}
		this.moteurPhysique.nextStep();
		((Gui)this.ihm.getInterfaceGraphique()).afficherLigne(moteurPhysique.getLigne());
	}
	
	public void sauvegarderConfiguration(){
		try {
			configuration.save(CONFIGURATION_FILE);
		} catch (IOException e) {
			Log.getInstance().logInfo("Unable to save configuration");
		}
	}
	
	public void quitter(){
		System.exit(0);
	}
}
