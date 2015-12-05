package gel.dock;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;

public class DockPanel extends JPanel{
	public DockPanel(String _title){
		setPreferredSize(new Dimension(200,300));
		
		setLayout(new BorderLayout());

		container = new JScrollPane();
		add(container);
		/*
		 * box titolo pannello
		 */
		JPanel titlePanel = new JPanel();
		titlePanel.setBorder(new EmptyBorder(0,0,0,0));
		titlePanel.setBackground(Color.decode("#BDBDBD"));
		add(titlePanel,BorderLayout.NORTH);
		
		/*
		 * titolo pannello
		 */
		JLabel title = new JLabel(_title);
		title.setBorder(new EmptyBorder(0,0,0,0));
		titlePanel.add(title,BorderLayout.CENTER);
		
		Font font = new Font( "Monospace", Font.PLAIN,11);
		title.setFont(font);
	}
	
	JScrollPane container;
	
	public void add(JPanel _obj){
		container.getViewport().add(_obj,BorderLayout.CENTER);
	}
	
	/*
	 * ------------------------------------
	 * GI
	 */
	/**
	 * Restituisce tutto il pannello
	 * @return	JPanel
	 */
	public JPanel getPanel(){
		return this;
	}
	
}
