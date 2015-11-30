package gel.dev.object;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.border.EmptyBorder;

import zjlib.logger.Logger;
import zjlib.config.Config;
import gel.component.Object;
import gel.component.Port;
import gel.component.TextLineNumber;


/**
 * Implementa un visualizzatore testuale di dati
 * @author Eugenio
 *
 */
public class ViewerDataRow extends Object{
	JFrame vdWinText; //finestra mostra testo

	public ViewerDataRow(Logger _log, Config _conf){
		super(_log,_conf);

		/*
		 * inizializzo le porte standard
		 */
		Port pIn = new Port("IN"); //porta dati in
		pIn.setGuiPosition(Port.LEFT);
		this.addPort(pIn);
		Port pOut = new Port("OUT"); //porta dati out
		pOut.setGuiPosition(Port.RIGHT);
		this.addPort(pOut);

		vdWinText=new VDWinText(_log,_conf,this.getName());

		//listner per il click e apertura della casella di testo
		this.gui.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				vdWinText.setVisible(true);
			}
		});

	}


	public int core(){
		return 0;
	}
	
	/**
	 * Implementa una finestra per la visualizzazione del test
	 */
	protected class VDWinText extends JFrame{
		private static final long serialVersionUID = 621924725414873385L;
		public JTextArea text;
		public JPanel lineNums;

		public VDWinText(Logger _log, Config _conf,String _title){
			super(_title);

			//modifico il modo di chiusura della finestra, 
			//faccio in modo che non si chiada ma diventi solo invisibile
			this.addWindowListener(new WindowAdapter() {
				public void windowClosing(WindowEvent evt) {
					onExit();
				}
			});

			this.setMinimumSize(new Dimension(800,300));
			this.setBackground(Color.decode("#FFFFFF"));

			//area testo e scoll
			text=new JTextArea();
			text.setPreferredSize(this.getPreferredSize());

			JScrollPane scroll=new JScrollPane(text);
			getContentPane().add(scroll,BorderLayout.CENTER);

			//area numero righe
			lineNums = new JPanel();
			lineNums.setBorder(new EmptyBorder(0, 0, 0, 0));
			lineNums.setBackground(Color.decode("#FFFFFF"));
			getContentPane().add(lineNums,BorderLayout.EAST);

			//numero riga 
			TextLineNumber tln = new TextLineNumber(text);
			scroll.setRowHeaderView( tln );
			pack();
		}

		public void onExit() {
			this.setVisible(false);
		}


	}
}
