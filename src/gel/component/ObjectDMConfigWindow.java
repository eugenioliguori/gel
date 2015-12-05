package gel.component;

import java.awt.Dimension;

import javax.swing.JFrame;

/**
 * Questa classe implementa una finestra per gestire le opzioni
 * dell'oggetto durante la modalità di sviluppo
 */
public class ObjectDMConfigWindow extends JFrame{
	private static final long serialVersionUID = 4254592733990454281L;
	Object obj;
	
	/**
	 * Costruttore, prende come argomenti l'oggetto di cui gestice le opzioni
	 * e il titolo che dovrà avere la finestra
	 * @param _obj		GEL Object
	 * @param _title	String
	 */
	public ObjectDMConfigWindow(Object _obj,String _title){
		super(_title);
		obj=_obj;
		
		setMinimumSize(new Dimension(800,300));
		
		pack();
	}
}
