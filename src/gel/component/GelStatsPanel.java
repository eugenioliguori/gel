package gel.component;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
/*
 * NON SI VEDE
 */
public class GelStatsPanel extends JPanel{
	private static final long serialVersionUID = -7388879643861639486L;

	public GelStatsPanel(){
		JPanel container=new JPanel();
		add(new JScrollPane(container));
		
		final int N = 8;
	    final int SIZE = 75;
		container.setLayout (new GridLayout(N,2));
		container.setPreferredSize(new Dimension(2 * SIZE,N * SIZE));
		
		//nomero dei thread attivi
		container.add(new JLabel("Thread:"));
		container.add(new JLabel(String.valueOf(Thread.activeCount())));
		
//		setVisible(false);
	}

}
