package jts.ihm.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import jts.ihm.langues.traduction.TraductionPanelDemarrage;

/**Ceci est le panel de l'écran de démarrage.
 * 
 * @author Yannick BISIAUX
 *
 */
@SuppressWarnings("serial")
public class PanelDemarrage extends JPanel implements ActionListener {
	
	private static final File FICHIER_IMAGE_FOND = new File("images/main.png");
	
	private Gui gui;
	
	private JButton boutonStart;
	private JButton boutonReglages;
	private JButton remerciements;
	private JButton boutonQuitter;

	public PanelDemarrage(Gui gui){
		super();
		this.gui = gui;
		this.setLayout(null);
		this.setPreferredSize(Gui.PANEL_DIMENSION);
		
		TraductionPanelDemarrage tpd = gui.getIhm().getLangueManager().getTraduction().getPanelDemarrage();
		boutonStart = new JButton(tpd.getStart());
		boutonStart.setBounds(600, 450, 150, 30);
		boutonStart.addActionListener(this);
		this.add(boutonStart);
		
		boutonReglages = new JButton(tpd.getReglages());
		boutonReglages.setBounds(600, 490, 150, 30);
		boutonReglages.addActionListener(this);
		this.add(boutonReglages);
		
		/*remerciements = new JButton(tpd.getRemerciements());
		remerciements.setBounds(600, 490, 150, 30);
		remerciements.addActionListener(this);
		this.add(remerciements);*/
		
		boutonQuitter = new JButton(tpd.getQuitter());
		boutonQuitter.setBounds(600, 530, 150, 30);
		boutonQuitter.addActionListener(this);
		this.add(boutonQuitter);
	}
	
	public void chargerArriereFond() throws IOException{
		ImageIcon imageFond = new ImageIcon(ImageIO.read(FICHIER_IMAGE_FOND));
		JLabel arriereFond = new JLabel(imageFond);
		arriereFond.setBounds(1, 1, 801, 601);
		this.add(arriereFond);
	}

	public void actionPerformed(ActionEvent event) {
		if(event.getSource().equals(boutonStart)){
			gui.afficherEcranChoixScenario();
		} else if(event.getSource().equals(boutonReglages)){
			gui.afficherEcranReglages();
		} else if(event.getSource().equals(boutonQuitter)){
			gui.getIhm().getControleur().quitter();
		}
	}
}
