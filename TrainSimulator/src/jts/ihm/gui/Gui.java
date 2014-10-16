package jts.ihm.gui;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import jts.Controleur;
import jts.ihm.InterfaceHommeMachine;
import jts.ihm.clavier.EcouteurClavier;
import jts.ihm.gui.ctrl.PanelVisuRoute;
import jts.ihm.gui.render.InterfaceMoteur3D;
import jts.ihm.gui.render.PanelConduite;
import jts.moteur.geometrie.Point;
import jts.moteur.ligne.Ligne;
import jts.moteur.ligne.ObjetScene;
import jts.moteur.ligne.voie.elements.CourbeElementaire;
import jts.moteur.train.Locomotive;
import jts.moteur.train.Train;

/**Cette classe gère l'interface graphique du logiciel.
 * 
 * @author Yannick BISIAUX
 *
 */
public class Gui implements InterfaceGraphique {
	
	public static Dimension PANEL_DIMENSION = new Dimension(800, 600);
	public static Font POLICE_TITRE = new Font("Agency FB", 3, 24);
	
	private InterfaceHommeMachine ihm;
	
	private List<JtsDimension> dimensionsPossibles;
	private JFrame fenetre;
	private JPanel panelCourant;
	private PanelConduite render;
	private InterfaceMoteur3D moteur3d;
	private JFrame fenetreCtrl;
	private PanelVisuRoute visuRoute;
	
	public Gui(InterfaceHommeMachine ihm){
		this.ihm = ihm;
	}
	
	public InterfaceHommeMachine getIhm(){ return this.ihm; }
	
	public List<JtsDimension> getDimensionsPossibles(){ return this.dimensionsPossibles; }
	
	public void init() {
		dimensionsPossibles = new ArrayList<JtsDimension>();
		dimensionsPossibles.add(new JtsDimension(640, 480));
		dimensionsPossibles.add(new JtsDimension(800, 600));
		dimensionsPossibles.add(new JtsDimension(1024, 768));
		dimensionsPossibles.add(new JtsDimension(1280, 720));
		dimensionsPossibles.add(new JtsDimension(1280, 1024));
		dimensionsPossibles.add(new JtsDimension(1366, 768));
		dimensionsPossibles.add(new JtsDimension(1600, 900));
		dimensionsPossibles.add(new JtsDimension(1600, 1024));
		dimensionsPossibles.add(new JtsDimension(1920, 1080));
		
		panelCourant = new PanelDemarrage(this);
		try {
			((PanelDemarrage)panelCourant).chargerArriereFond();
		} catch (IOException e) {
			Controleur.LOG.warn("Impossible de lire l'image d'arrière fond de démarrage");
		}
		fenetre = new JFrame("Java Train Simulator");
		fenetre.add(panelCourant);
		fenetre.pack();
		fenetre.setLocationRelativeTo(null);
		fenetre.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		fenetre.setResizable(false);
		fenetre.addWindowListener(new EcouteurFermeture(ihm.getControleur()));
		fenetre.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		fenetre.setVisible(true);
	}
	
	public void afficherEcranDemarrage() {
		PanelDemarrage panelDemarrage = new PanelDemarrage(this);
		try {
			panelDemarrage.chargerArriereFond();
		} catch (IOException e) {
			Controleur.LOG.warn("Impossible de lire l'image d'arrière fond de démarrage");
		}
		fenetre.getContentPane().removeAll();
		fenetre.add(panelDemarrage);
		this.relocaliserFenetre();
	}
	
	public void afficherEcranChoixScenario() {
		PanelChoixScenario panelChoixScenario = new PanelChoixScenario(this);
		try {
			panelChoixScenario.chargerArriereFond();
		} catch (IOException e) {
			Controleur.LOG.warn("Impossible de lire l'image d'arrière fond de démarrage");
		}
		fenetre.getContentPane().removeAll();
		fenetre.add(panelChoixScenario);
		this.relocaliserFenetre();
	}
	
	public void afficherEcranReglages() {
		PanelReglages panelReglages = new PanelReglages(this);
		try {
			panelReglages.chargerArriereFond();
		} catch (IOException e) {
			Controleur.LOG.warn("Impossible de lire l'image d'arrière fond de règlages");
		}
		fenetre.getContentPane().removeAll();
		fenetre.add(panelReglages);
		this.relocaliserFenetre();
	}
	
	public void afficherEcranJeu(){
		PanelConduite panelConduite = new PanelConduite();
		this.fenetre.getContentPane().removeAll();
		this.fenetre.add(panelConduite);
		
		this.render = panelConduite;
		this.moteur3d = this.render.init(this.ihm.getControleur().getConfiguration().getConfigurationGraphique(),
				(EcouteurClavier)ihm.getInterfaceClavier());
		this.fenetre.setFocusable(true);
		this.fenetre.addKeyListener((KeyListener)ihm.getInterfaceClavier());
		this.relocaliserFenetre();
		
		this.fenetreCtrl = new JFrame("Driver Simulator");
		this.visuRoute = new PanelVisuRoute();
		this.fenetreCtrl.add(visuRoute);
		this.fenetreCtrl.setVisible(true);
		this.fenetreCtrl.pack();
		this.fenetreCtrl.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.fenetreCtrl.setLocation(800, 0);
		this.fenetreCtrl.addKeyListener((KeyListener)ihm.getInterfaceClavier());
	}
	
	public void chargerTerrain(Ligne ligne){
		/*for (Section section : ligne.getCircuit().getSections()){
			vue3D.dessinerSurface(section.getFrontiere().getSommets());
			if(section.getNomObjet() != null){
				vue3D.chargerObjet(
						(float)section.getPositionAbsolue().getY(),
						(float)section.getPositionAbsolue().getZ(),
						(float)section.getPositionAbsolue().getX(),
						(float)section.getAngle().getPsi(),
						section.getNomObjet());
			}
			
		}*/
		
		for (CourbeElementaire element : ligne.getCircuit().getCourbesElementaires()){
			moteur3d.dessinerLigne(element.getPointsRemarquables());
		}
		
		for(ObjetScene objet : ligne.getObjets()){
			moteur3d.chargerObjet(objet);
		}
	}

	public void afficherLigne(Ligne ligne) {
		Train trainJoueur = ligne.getCircuit().getTrainJoueur();
		render.getTableauBord().afficherTrain(trainJoueur);
		
		visuRoute.setLigne(ligne);
		visuRoute.repaint();
		
		Locomotive locomotiveJoueur = trainJoueur.getLocomotiveTete();
		Point point = locomotiveJoueur.getObservation();
		moteur3d.deplacerCamera((float)point.getY(), (float)point.getZ(), (float)point.getX(), (float)((Math.PI/2 - locomotiveJoueur.getOrientation().getPsi())), -(float)locomotiveJoueur.getOrientation().getPhi());
		
	}
	
	private void relocaliserFenetre(){
		fenetre.pack();
		fenetre.setLocationRelativeTo(null);
		fenetre.setVisible(true);
	}
	
	public static JLabel creerArriereFondTransparent(int x, int y, int largeur, int hauteur){
		BufferedImage imageFondJoystick = new BufferedImage(largeur, hauteur, BufferedImage.TYPE_INT_ARGB);
		int valeurRGB = 200;
		int rgb = valeurRGB;
		rgb += valeurRGB<<8;
		rgb += valeurRGB<<16;
		rgb += valeurRGB<<24;
		for(int i=0; i<largeur; i++){
			for(int j=0; j<hauteur; j++){
				imageFondJoystick.setRGB(i, j, rgb);
			}
		}
		JLabel arriereFond = new JLabel(new ImageIcon(imageFondJoystick));
		arriereFond.setBounds(x, y, largeur, hauteur);
		return arriereFond;
	}
}
