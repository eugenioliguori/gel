package gel.component;

import java.awt.BorderLayout;

import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class GelAboutWindow extends JDialog{
	public GelAboutWindow(){
		setTitle("GEL Informations...");
		setModal(true);
		getContentPane().setLayout(new BorderLayout());
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setLocation(400,300);
		JPanel container=new JPanel(new BorderLayout());
		getContentPane().add(container);
		container.add(new JLabel("Generic Eugenio Language - v 0.1"),BorderLayout.NORTH);
		container.add(new JLabel("Development by Eugenio Liguori<zerounozerolab@gmail.com>"),BorderLayout.CENTER);
		
	}

}
