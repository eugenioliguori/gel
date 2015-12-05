package gel.dev.object;

import java.awt.Dimension;
import java.awt.Font;

import javax.swing.JLabel;
import javax.swing.border.EmptyBorder;

import gel.component.Object;
import gel.component.ports.Action;
import gel.component.ports.Port;
import gel.dock.Dock;
import zjlib.config.Config;
import zjlib.logger.Logger;

public class GelLabel extends Object{
	private JLabel lblData;
	private JLabel lblText;
	public GelLabel(Logger _log, Config _conf,Dock _dock){
		super(_log,_conf,_dock);
		addProperty("Text", "");


		/*
		 * inizializzo le porte standard
		 */
		Port pIn = new Port(_log,_conf,this.dock,"IN"); //porta dati in
		pIn.setGuiPosition(Port.LEFT);
		this.addPort(pIn);
		pIn.addRxAction(new rxAction());

		lblText=new JLabel();
		Font font = new Font( "Monospace", Font.PLAIN,11);
		lblText.setFont(font);
		lblText.setBorder(new EmptyBorder(0,0,0,0));
		getGUI().add(lblText);

		lblData=new JLabel();
		lblData.setFont(font);
		lblData.setBorder(new EmptyBorder(0,0,0,0));
		lblData.setPreferredSize(new Dimension(50,10));
		getGUI().add(lblData);
		
		
	}

	/*
	 * Classe Action per il canale RX di una porta
	 */
	protected class rxAction extends Action{

		@Override
		protected void run(String _data) {
			lblData.setText(_data);
		}
		@Override
		protected void run(int _data) {
			lblData.setText(String.valueOf(_data));
		}
		@Override
		protected void run(long _data) {
			lblData.setText(String.valueOf(_data));
		}
		@Override
		protected void run(char _data) {
			lblData.setText(String.valueOf(_data));
		}
		@Override
		protected void run(char[] _data) {
			String str = null;
			for (Character c : _data){
			    str += c.toString();
			}
			str+="\n";
			lblData.setText(str);
		}
		@Override
		protected void run(short _data) {
			lblData.setText(String.valueOf(_data));
		}
		@Override
		protected void run(byte[] _data) {
			lblData.setText(String.valueOf(_data));
		}
		@Override
		protected void run(float _data) {
			lblData.setText(String.valueOf(_data));
		}
		@Override
		protected void run(double _data) {
			lblData.setText(String.valueOf(_data));
		}

	}

	@Override
	public int core() {
		//		lbl.setText("one");
		return 0;
	}

	public void setText(String _string){
		setProperty("Text", _string);
		lblText.setText(_string);
	}


}
