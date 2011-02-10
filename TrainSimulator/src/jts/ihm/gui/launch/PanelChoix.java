package jts.ihm.gui.launch;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.IOException;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JPanel;

import jts.ihm.Ihm;
import jts.ihm.joystick.ConfigurationJoystick;
import jts.io.FileLoader;

@SuppressWarnings("serial")
public class PanelChoix extends JPanel implements ActionListener, ItemListener {
	
	private final static Dimension DIMENSION = new Dimension(300, 110);

	private Ihm ihm;
	private FileLoader fl;
	
	private JComboBox comboLigne;
	private JComboBox comboScenario;
	private JCheckBox checkBoxJoystick;
	private JButton lancer;
	
	public PanelChoix(Ihm ihm){
		super();
		this.ihm = ihm;
	}
	
	public void init(boolean joystickActif){
		this.setLayout(null);
		this.setPreferredSize(DIMENSION);
		
		fl = new FileLoader();
		fl.chercherNomLignes();
		
		this.comboLigne = new JComboBox();
		this.comboLigne.setBounds(10, 10, 140, 20);
		this.comboLigne.setModel(new DefaultComboBoxModel(fl.getNomsLignes().toArray()));
		this.comboLigne.addItemListener(this);
		this.add(comboLigne);
		
		fl.chercherNomScenarios(fl.getFichiersLignes().get(0).getName().split(".xml")[0]);
		
		this.comboScenario = new JComboBox();
		this.comboScenario.setBounds(10, 40, 140, 20);
		this.comboScenario.setModel(new DefaultComboBoxModel(fl.getNomsScenarios().toArray()));
		this.add(comboScenario);
		
		this.checkBoxJoystick = new JCheckBox("Activer Joystick");
		this.checkBoxJoystick.setBounds(160, 25, 140, 20);
		this.checkBoxJoystick.setSelected(joystickActif);
		this.add(checkBoxJoystick);
		
		this.lancer = new JButton("Lancer");
		this.lancer.setBounds(110, 70, 80, 30);
		this.lancer.addActionListener(this);
		this.add(lancer);
	}

	public void actionPerformed(ActionEvent event) {
		if(event.getSource().equals(this.lancer)){
			ConfigurationJoystick confJoy = this.ihm.getGestionnaireJoystick().getConfJoystick();
			confJoy.setUtiliserJoystick(this.checkBoxJoystick.isSelected());
			try {
				confJoy.saveConf();
			} catch (IOException e) {
				System.out.println("Impossible de sauvegarder la configuration joystick");
			}
			int indexFichierLigne = this.comboLigne.getSelectedIndex();
			int indexFichierScenario = this.comboScenario.getSelectedIndex();
			this.ihm.getControleur().lancerSimu(fl.getFichiersLignes().get(indexFichierLigne), fl.getFichiersScenarios().get(indexFichierScenario));
		}
	}

	@Override
	public void itemStateChanged(ItemEvent arg0) {
		int indexFichierLigne = this.comboLigne.getSelectedIndex();
		fl.chercherNomScenarios(fl.getFichiersLignes().get(indexFichierLigne).getName().split(".xml")[0]);
		this.comboScenario.removeAllItems();
		this.comboScenario.setModel(new DefaultComboBoxModel(fl.getNomsScenarios().toArray()));
	}
}
