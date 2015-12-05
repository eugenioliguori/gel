package gel.component;

import gel.component.ports.Port;
import gel.dock.Dock;

import javax.swing.JComponent;




import zjlib.config.Config;
import zjlib.logger.Logger;

public class Link extends JComponent{
	private static final long serialVersionUID = -6776233289358430922L;
	/*
	 * ------------------------------------------
	 * LOG e CONFIG
	 */
	Config conf;
	Logger log;

	public void setConf(Config _conf){
		this.conf=_conf;
	}
	public void setLog(Logger _log){
		this.log=_log;
	}
	public Config getConf(){
		return this.conf;
	}
	public Logger getLog(){
		return this.log;
	}
	
	/*
	 * ---------------------------------------------
	 * DOCK
	 */
	protected Dock dock;
	public void setDock(Dock _dock){dock=_dock;}
	public Dock getDock(){return dock;}

	/*
	 * ----------------------------------------------
	 * TERMINALS
	 */
	public Port portA,portB;
	
	/*
	 * -----------------------------------------------
	 * GUI
	 */
//	public void guiLoad(){
//		
//	}
	
	
}
