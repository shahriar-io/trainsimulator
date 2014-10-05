package jts.ihm.gui;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

import jts.conf.JmeRenderer;
import jts.ihm.langues.Langue;
import jts.ihm.langues.traduction.TraductionPanelReglages;

/**Ceci est le panel de l'écran de réglages.
 * 
 * @author Yannick BISIAUX
 *
 */
@SuppressWarnings("serial")
public class PanelReglages extends JPanel implements ActionListener, ItemListener {
	
	private static final File FICHIER_IMAGE_FOND = new File("images/main3.png");

	private Gui gui;
	private TraductionPanelReglages tpr;
	
	private JLabel labelChoixJoystick;
	private JCheckBox utiliserJoystick;
	private JComboBox<String> nomsJoystick;
	private JLabel labelInterfaceGraphique;
	private JLabel labelChoixLangue;
	private JComboBox<String> langages;
	private JComboBox<JtsDimension> resolutions;
	private JComboBox<String> renderers;
	
	private JButton menuPrincipal;
	private JButton sauvegarde;
	
	public PanelReglages(Gui gui){
		super();
		this.gui = gui;
		this.setLayout(null);
		this.setPreferredSize(Gui.PANEL_DIMENSION);
		
		tpr = gui.getIhm().getLangueManager().getTraduction().getPanelReglages();
		
		this.labelChoixJoystick = new JLabel(tpr.getChoixJoystick());
		this.labelChoixJoystick.setBounds(40, 30, 150, 30);
		this.labelChoixJoystick.setFont(Gui.POLICE_TITRE);
		this.add(labelChoixJoystick);
		
		this.utiliserJoystick = new JCheckBox(tpr.getUtilisationJoystick());
		this.utiliserJoystick.setBounds(200, 30, 160, 30);
		this.utiliserJoystick.setBackground(new Color(200, 200, 200));
		this.utiliserJoystick.addActionListener(this);
		this.utiliserJoystick.setSelected(gui.getIhm().getControleur().getConfiguration().getConfigurationJoystick().isUseJoystick());
		this.add(utiliserJoystick);
		
		this.nomsJoystick = new JComboBox<String>();
		this.nomsJoystick.setBounds(100, 70, 200, 30);
		this.nomsJoystick.setModel(new DefaultComboBoxModel(gui.getIhm().getIntefaceJoystick().getJoystickNames().toArray()));
		this.nomsJoystick.addItemListener(this);
		int index = Math.min(gui.getIhm().getControleur().getConfiguration().getConfigurationJoystick().getNumeroJoystick(), nomsJoystick.getModel().getSize()-1);
		this.nomsJoystick.setSelectedIndex(index);
		this.nomsJoystick.setEnabled(utiliserJoystick.isSelected());
		this.add(nomsJoystick);
		
		this.add(Gui.creerArriereFondTransparent(20, 20, 360, 490));
		
		/*JPanel panelFond2 = new JPanel();
		panelFond2.setBackground(new Color(200, 200, 200, 200));
		panelFond2.setBounds(420, 20, 360, 130);
		this.add(panelFond2);*/
		
		this.labelInterfaceGraphique = new JLabel(tpr.getInterfaceGraphique());
		this.labelInterfaceGraphique.setBounds(440, 30, 200, 30);
		this.labelInterfaceGraphique.setFont(Gui.POLICE_TITRE);
		this.add(labelInterfaceGraphique);
		
		this.labelChoixLangue = new JLabel(tpr.getChoixLangue());
		this.labelChoixLangue.setBounds(440, 70, 200, 30);
		this.add(labelChoixLangue);
		
		this.langages = new JComboBox<String>();
		this.langages.setBounds(575, 70, 150, 30);
		this.langages.setModel(new DefaultComboBoxModel(Langue.getNoms()));
		Langue langue = Langue.getLangueFromCode(this.gui.getIhm().getControleur().getConfiguration().getLangueCode());
		this.langages.setSelectedIndex(Langue.getOrdinalFromLangue(langue));
		this.langages.addItemListener(this);
		this.add(langages);
		
		this.resolutions = new JComboBox<JtsDimension>();
		this.resolutions.setBounds(575, 110, 150, 30);
		this.resolutions.setModel(new DefaultComboBoxModel(gui.getDimensionsPossibles().toArray()));
		this.resolutions.setSelectedIndex(gui.getDimensionsPossibles().indexOf(this.gui.getIhm().getControleur().getConfiguration().getConfigurationGraphique().getDimension()));
		this.resolutions.addItemListener(this);
		this.add(resolutions);
		
		this.renderers = new JComboBox<String>();
		this.renderers.setBounds(575, 150, 150, 30);
		this.renderers.setModel(new DefaultComboBoxModel(JmeRenderer.getNoms()));
		JmeRenderer jmeRenderer = this.gui.getIhm().getControleur().getConfiguration().getConfigurationGraphique().getRenderer();
		this.renderers.setSelectedIndex(jmeRenderer.ordinal());
		this.renderers.addItemListener(this);
		this.add(renderers);
		
		this.add(Gui.creerArriereFondTransparent(420, 20, 360, 170));
		
		
		
		this.menuPrincipal = new JButton(tpr.getMenuPrincipal());
		this.menuPrincipal.setBounds(120, 530, 160, 30);
		this.menuPrincipal.addActionListener(this);
		this.add(menuPrincipal);
		
		this.sauvegarde = new JButton(tpr.getSauvegarde());
		this.sauvegarde.setBounds(520, 530, 160, 30);
		this.sauvegarde.addActionListener(this);
		this.add(sauvegarde);
	}
	
	public void chargerArriereFond() throws IOException{
		ImageIcon imageFond = new ImageIcon(ImageIO.read(FICHIER_IMAGE_FOND));
		JLabel arriereFond = new JLabel(imageFond);
		arriereFond.setBounds(1, 1, 801, 601);
		this.add(arriereFond);
	}
	
	public void chargerTextes(){
		tpr = gui.getIhm().getLangueManager().getTraduction().getPanelReglages();
		this.labelChoixJoystick.setText(tpr.getChoixJoystick());
		this.utiliserJoystick.setText(tpr.getUtilisationJoystick());
		this.labelInterfaceGraphique.setText(tpr.getInterfaceGraphique());
		this.labelChoixLangue.setText(tpr.getChoixLangue());
		this.menuPrincipal.setText(tpr.getMenuPrincipal());
		this.sauvegarde.setText(tpr.getSauvegarde());
	}
	
	public void actionPerformed(ActionEvent event) {
		if(event.getSource().equals(menuPrincipal)){
			gui.afficherEcranDemarrage();
		} else if(event.getSource().equals(sauvegarde)){
			gui.getIhm().getControleur().sauvegarderConfiguration();
		} else if(event.getSource().equals(utiliserJoystick)){
			gui.getIhm().getControleur().getConfiguration().getConfigurationJoystick().setUseJoystick(utiliserJoystick.isSelected());
			int indexJoystick = nomsJoystick.getSelectedIndex();
			gui.getIhm().getIntefaceJoystick().selectJoystick(indexJoystick);
			nomsJoystick.setEnabled(utiliserJoystick.isSelected());
		}
	}
	
	public void itemStateChanged(ItemEvent event) {
		if(event.getSource().equals(langages)){
			int indexLangage = langages.getSelectedIndex();
			this.gui.getIhm().modifierLangue(Langue.values()[indexLangage]);
			this.chargerTextes();
		} else if(event.getSource().equals(nomsJoystick)){
			int indexJoystick = nomsJoystick.getSelectedIndex();
			this.gui.getIhm().getControleur().getConfiguration().getConfigurationJoystick().setNumeroJoystick(indexJoystick);
			this.gui.getIhm().getIntefaceJoystick().selectJoystick(indexJoystick);
		} else if(event.getSource().equals(resolutions)){
			int indexResolution = resolutions.getSelectedIndex();
			JtsDimension resolution = this.gui.getDimensionsPossibles().get(indexResolution);
			this.gui.getIhm().getControleur().getConfiguration().getConfigurationGraphique().setDimension(resolution);
		} else if(event.getSource().equals(renderers)){
			int indexRenderer = renderers.getSelectedIndex();
			this.gui.getIhm().getControleur().getConfiguration().getConfigurationGraphique().setRenderer(JmeRenderer.values()[indexRenderer]);
		}
	}
}
