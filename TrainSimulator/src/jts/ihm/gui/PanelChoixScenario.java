package jts.ihm.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import jts.ihm.langues.traduction.TraductionPanelChoixScenario;
import jts.io.FileLoader;

/**Ceci est le panel permettant de choisir une ligne et un scénario.
 * 
 * @author Yannick BISIAUX
 *
 */
@SuppressWarnings("serial")
public class PanelChoixScenario extends JPanel implements ActionListener, ListSelectionListener  {
	
	private static final File FICHIER_IMAGE_FOND = new File("images/main2.png");
	
	private Gui gui;
	private FileLoader fl;
	
	private JList listLigne;
	private JList listScenario;
	private JButton menuPrincipal;
	private JButton lancer;

	public PanelChoixScenario(Gui gui){
		super();
		this.gui = gui;
		this.setLayout(null);
		this.setPreferredSize(Gui.PANEL_DIMENSION);
		
		TraductionPanelChoixScenario tpcs = gui.getIhm().getLangueManager().getTraduction().getPanelChoixScenario();
		
		JLabel labelLigne = new JLabel(tpcs.getLigne());
		labelLigne.setBounds(40, 30, 150, 30);
		labelLigne.setFont(Gui.POLICE_TITRE);
		this.add(labelLigne);
		
		fl = new FileLoader();
		fl.chercherNomLignes();
		
		this.listLigne = new JList();
		this.listLigne.setBounds(90, 65, 220, 140);
		this.listLigne.setListData(fl.getNomsLignes().toArray());
		this.listLigne.addListSelectionListener(this);
		this.add(listLigne);
		
		this.add(Gui.creerArriereFondTransparent(20, 20, 760, 210));
		
		JLabel labelScenario = new JLabel(tpcs.getScenario());
		labelScenario.setBounds(40, 280, 150, 30);
		labelScenario.setFont(Gui.POLICE_TITRE);
		this.add(labelScenario);
		
		fl.chercherNomScenarios(fl.getFichiersLignes().get(0).getName().split(".xml")[0]);
		
		this.listScenario = new JList();
		this.listScenario.setBounds(90, 315, 220, 140);
		this.listScenario.setListData(fl.getNomsScenarios().toArray());
		this.add(listScenario);
		
		this.add(Gui.creerArriereFondTransparent(20, 270, 760, 210));
		
		this.menuPrincipal = new JButton(tpcs.getMenuPrincipal());
		this.menuPrincipal.setBounds(120, 530, 160, 30);
		this.menuPrincipal.addActionListener(this);
		this.add(menuPrincipal);
		
		this.lancer = new JButton(tpcs.getLancement());
		this.lancer.setBounds(520, 530, 160, 30);
		this.lancer.addActionListener(this);
		this.add(lancer);
	}
	
	public void chargerArriereFond() throws IOException{
		ImageIcon imageFond = new ImageIcon(ImageIO.read(FICHIER_IMAGE_FOND));
		JLabel arriereFond = new JLabel(imageFond);
		arriereFond.setBounds(1, 1, 801, 601);
		this.add(arriereFond);
	}
	
	public void actionPerformed(ActionEvent event) {
		if(event.getSource().equals(this.menuPrincipal)){
			this.gui.afficherEcranDemarrage();
		} else if(event.getSource().equals(this.lancer)){
			int indexFichierLigne = this.listLigne.getSelectedIndex();
			int indexFichierScenario = this.listScenario.getSelectedIndex();
			if((indexFichierLigne != -1) && (indexFichierScenario != -1)){
				File fichierLigne = fl.getFichiersLignes().get(indexFichierLigne);
				File fichierScenario = fl.getFichiersScenarios().get(indexFichierScenario);
				this.gui.getIhm().getControleur().lancerSimu(fichierLigne, fichierScenario);
			}
		}
	}

	public void valueChanged(ListSelectionEvent event) {
		int indexFichierLigne = this.listLigne.getSelectedIndex();
		fl.chercherNomScenarios(fl.getFichiersLignes().get(indexFichierLigne).getName().split(".xml")[0]);
		this.listScenario.setListData(fl.getNomsScenarios().toArray());
	}
}
