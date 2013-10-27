package jts;

import java.io.File;
import java.io.IOException;

import jts.conf.Configuration;
import jts.conf.InterfaceConfiguration;
import jts.ihm.Ihm;
import jts.ihm.InterfaceHommeMachine;
import jts.ihm.audio.AudioPlayer;
import jts.ihm.audio.InterfaceAudio;
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
	private InterfaceAudio audio;
	private MoteurPhysique moteurPhysique;
	private PreneurDecisionClavier decisions;
	
	public Controleur(){
		
	}
	
	public void init(){
		try {
			Log.init(new File("log.txt"), LogMode.DEBUG);
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
			moteurPhysique = new MoteurPhysique(DUREE);
			decisions = new PreneurDecisionClavier(moteurPhysique);
			audio = new AudioPlayer();
			audio.init((double)DUREE/1000.0);
		}
	}
	
	public InterfaceConfiguration getConfiguration(){ return this.configuration; }
	
	public void lancerSimu(File fichierLigne, File fichierScenario){
		Log.getInstance().logInfo("**********  Chargement de la ligne  **********");
		Ligne ligne = LigneLoader.load(fichierLigne);
		//Ligne ligne = ScriptLgnReader.load(new File("data/lignes/Circuit_Luzerian.lgn"));
		//Ligne ligne = LigneLuzerianCreator.create();
		this.moteurPhysique.setLigne(ligne);
		
		Log.getInstance().logInfo("**********  Chargement du scénario  **********");
		ScenarioLoader.loadScenario(ligne.getCircuit(), fichierScenario);
		
		this.ihm.getInterfaceGraphique().afficherEcranJeu();
		((Gui)this.ihm.getInterfaceGraphique()).chargerTerrain(ligne);
		
		Thread gb = new GrandeBoucle(this, DUREE);
		gb.start();
	}

	public void boucler() {
		//float[] valeurJoystick = this.ihm.getIntefaceJoystick().getValeursVolantFrein();
		boolean[] touchesClavier = this.ihm.getInterfaceClavier().getTouchePressee();
		decisions.prendreDecisions(touchesClavier);
		
		if(configuration.getConfigurationJoystick().isUseJoystick()){
			ihm.getIntefaceJoystick().refreshValeurs();
			this.moteurPhysique.setDeltaCommandeVolant(-ihm.getIntefaceJoystick().getValeurs().getAxe(0));
			this.moteurPhysique.setDeltaCommandeFrein(0);
		}
		this.moteurPhysique.nextStep();
		((Gui)this.ihm.getInterfaceGraphique()).afficherLigne(moteurPhysique.getLigne());
		
		//Harmoniques 400 et 1200 Hz fonction de la commande moteur.
		double commande = this.moteurPhysique.getLigne().getCircuit().getTrainJoueur().getCommandeTraction();
		double frequences[] = {300.0, 400.0, 1200.0};
		double amplitudes[] = new double[3];
		amplitudes[0] = 0.8 * commande;
		amplitudes[1] = 0.05 * commande;
		amplitudes[2] = 0.05 * commande;
		audio.jouerFrequences(frequences, amplitudes);
		audio.flush();
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
