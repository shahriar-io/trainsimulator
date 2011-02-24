package jts.ihm;

import java.awt.Dimension;
import java.awt.Font;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**Fenêtre d'initialisation du logiciel.
 * 
 * @author Yannick BISIAUX
 *
 */
@SuppressWarnings("serial")
public class InitFrame extends JFrame {
	
	public InitFrame(){
		super("Java Train Simulator");
		this.add(new InitPanel());
		this.pack();
		this.setLocationRelativeTo(null);
		this.setVisible(true);
	}
	
	
	public class InitPanel extends JPanel{
		public InitPanel(){
			super();
			this.setLayout(null);
			this.setPreferredSize(new Dimension(640, 480));
			
			JLabel labelJTS = new JLabel("JTS");
			labelJTS.setFont(new Font("Arial", Font.BOLD + Font.ITALIC, 120));
			labelJTS.setBounds(0, 50, 640, 200);
			labelJTS.setHorizontalAlignment(JLabel.CENTER);
			this.add(labelJTS);
			
			JLabel labelLoading = new JLabel("Chargement ...");
			labelLoading.setFont(new Font("Arial", Font.PLAIN, 12));
			labelLoading.setBounds(0, 300, 640, 50);
			labelLoading.setHorizontalAlignment(JLabel.CENTER);
			this.add(labelLoading);
		}
	}
}
