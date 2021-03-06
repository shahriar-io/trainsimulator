package jts;

import java.io.File;
import java.io.IOException;

import jts.conf.Configuration;
import jts.conf.InterfaceConfiguration;
import jts.ihm.Ihm;
import jts.ihm.InterfaceHommeMachine;
import jts.ihm.audio.AudioPlayer;
import jts.ihm.audio.InterfaceAudio;
import jts.ihm.audio.InterpolationSon;
import jts.ihm.audio.Son;
import jts.ihm.gui.Gui;
import jts.io.LigneLoader;
import jts.io.ScenarioLoader;
import jts.moteur.MoteurPhysique;
import jts.moteur.ligne.Ligne;
import jts.moteur.ligne.Terrain;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

/**Cette classe est le controleur de l'application. Il fait le lien entre les moteurs de l'application :
 * moteur physique, moteur graphique, moteur son, r�cup�ration des entr�es utilisateurs, moteur r�seau.
 * 
 * @author Yannick BISIAUX
 *
 */
public class Controleur implements InterfaceControleur {
	
	public static Logger LOG = Logger.getLogger("jts");
	/**Duree de boucle en millisecondes*/
	private static long DUREE = 50;
	/**Fichier de configuration*/
	private static File CONFIGURATION_FILE = new File("conf.xml");
	
	private InterfaceConfiguration configuration;
	private InterfaceHommeMachine ihm;
	private InterfaceAudio audio;
	private MoteurPhysique moteurPhysique;
	private PreneurDecisionClavier decisions;
	
	
	private InterpolationSon interpolationSon;
	
	public Controleur(){
		interpolationSon = new InterpolationSon(4);
		Son son1 = new Son();
		son1.addComposante(0, 0.1);
		son1.addComposante(0, 0.1);
		son1.addComposante(0, 0.2);
		son1.addComposante(0, 0.2);
		Son son2 = new Son();
		son2.addComposante(78, 0.1);
		son2.addComposante(92, 0.1);
		son2.addComposante(146, 0.2);
		son2.addComposante(176, 0.2);
		Son son3 = new Son();
		son3.addComposante(155, 0.2);
		son3.addComposante(432, 0.2);
		son3.addComposante(635, 0.1);
		son3.addComposante(654, 0.1);
		Son son4 = new Son();
		son4.addComposante(300, 0.2);
		son4.addComposante(800, 0.2);
		son4.addComposante(1200, 0.05);
		son4.addComposante(1300, 0.05);
		interpolationSon.addSon(son1, 0);
		interpolationSon.addSon(son2, 30/3.6);
		interpolationSon.addSon(son3, 120/3.6);
		interpolationSon.addSon(son4, 240/3.6);
	}
	
	public void init(){
		PropertyConfigurator.configure("log4j.properties");
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
				LOG.fatal("Unable to load configuration : " + e.getMessage());
			}
			ihm.init();
			Terrain.init();
			moteurPhysique = new MoteurPhysique(DUREE);
			decisions = new PreneurDecisionClavier(moteurPhysique);
			audio = new AudioPlayer();
			audio.init((double)DUREE/1000.0);
		}
	}
	
	public InterfaceConfiguration getConfiguration(){ return this.configuration; }
	
	public void lancerSimu(String fichierLigne, File fichierScenario){
		LOG.warn("Chargement de la ligne");
		Ligne ligne = LigneLoader.load(fichierLigne);
		this.moteurPhysique.setLigne(ligne);
		
		LOG.warn("Chargement du sc�nario");
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
			double valeur = ihm.getIntefaceJoystick().getValeurs().getAxe(4);
			this.moteurPhysique.setDeltaCommandeVolant(-Math.signum(valeur)*Math.pow(valeur, 2.0));
			this.moteurPhysique.setDeltaCommandeFrein(0);
			if(ihm.getIntefaceJoystick().getValeurs().getFrontMontant(0)){
				this.moteurPhysique.changerProchainAiguillage();
			}
		}
		this.moteurPhysique.nextStep();
		((Gui)this.ihm.getInterfaceGraphique()).afficherLigne(moteurPhysique.getLigne());
		
		double commande = this.moteurPhysique.getLigne().getCircuit().getTrainJoueur().getCommandeTraction();
		double vitesse = this.moteurPhysique.getLigne().getCircuit().getTrainJoueur().getVitesse();
		Son son = interpolationSon.getSon(vitesse);
		son.getAmplitudes().set(0, commande*0.2);
		son.getAmplitudes().set(1, commande*0.2);
		son.getAmplitudes().set(2, commande*0.2);
		son.getAmplitudes().set(3, commande*0.2);
		audio.jouerSon(son);
		audio.flush();
	}
	
	public void sauvegarderConfiguration(){
		try {
			configuration.save(CONFIGURATION_FILE);
		} catch (IOException e) {
			LOG.error("Unable to save configuration");
		}
	}
	
	public void quitter(){
		System.exit(0);
	}
}
