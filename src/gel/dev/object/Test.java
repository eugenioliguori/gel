package gel.dev.object;

import javax.swing.JLabel;

import gel.component.Object;
import gel.component.ports.Action;
import gel.component.ports.Port;
import gel.dock.Dock;
import zjlib.config.Config;
import zjlib.logger.Logger;

public class Test extends Object{
	protected JLabel lbl;
	public Test(Logger _log, Config _conf,Dock _dock){
		super(_log,_conf,_dock);
		
		/*
		 * inizializzo le porte standard
		 */
		Port pOut = new Port(_log,_conf,this.dock,"OUT"); //porta dati in
		pOut.setGuiPosition(Port.RIGHT);
		this.addPort(pOut);
		
}
	

	@Override
	public int core() {
		int a=0;
		while(true){
			getPort(0).tx(a);
			a++;
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				break;
			} 
		}
		return 0;
	}
	
	
}
