package gel.component.toolbar;

import gel.dock.Dock;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.JSeparator;
import javax.swing.JToolBar;
import javax.swing.border.EmptyBorder;

public class ToolbarMain extends JToolBar{
	static Dock dock;
	public ToolbarMain(Dock _dock){
		super("ToolbarMain");
		dock=_dock;
		setFloatable(false);
		setRollover(true);
		setLayout(new FlowLayout(FlowLayout.LEFT));
		setBorder(new EmptyBorder(0,0,0,0));
		try {
			/*
			 * bottoni
			 */
			add(new ToolbarButton("images/print.png"));
			add(new JSeparator(JSeparator.VERTICAL));

			//bottone run->start
			ToolbarButton btnRunStart = new ToolbarButton("images/play.png");
			btnRunStart.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e)
				{
					dock.start();
				}
			});
			add(btnRunStart);
			
//			add(new ToolbarButton("images/pause.png"));

			//bottone run->stop
			ToolbarButton btnRunStop = new ToolbarButton("images/stop.png");
			btnRunStop.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e)
				{
					dock.stop();
				}
			});
			add(btnRunStop);

			ToolbarButton btnEditSelectLock = new ToolbarButton("images/lock.png");
			btnEditSelectLock.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e)
				{
					if(dock.isSelectLocked()){
						dock.selectUnlock();
					}else{
						dock.selectLock();
					}
				}
			});
			add(btnEditSelectLock);

		} catch ( IOException e) {
			e.printStackTrace();
		}
	}
	
	private void dockRunStart(){
		
	}
}
