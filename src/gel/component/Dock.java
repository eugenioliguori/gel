package gel.component;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;
import javax.swing.JScrollPane;

import zjlib.config.Config;
import zjlib.logger.Logger;


/**
 * Questa classe crea il dock in cui vengono messi gli oggetti(Object)
 * @author Eugenio
 *
 */
public class Dock extends JScrollPane{
	/**
	 * 
	 */
	private static final long serialVersionUID = 914280923988999877L;
	protected Config conf;
	protected Logger log;
	
	protected JPanel content; //contenitore degli oggetti nello scrollPane
	
	protected List<Object> objects;
	protected List<Link> links;
	
	public Dock(Logger _log, Config _conf){
		this.log=_log;
		this.conf = _conf;
		
		this.objects = new ArrayList<Object>();
		this.links = new ArrayList<Link>();
		
		//setto la GUI
		setHorizontalScrollBarPolicy(HORIZONTAL_SCROLLBAR_AS_NEEDED);
		setVerticalScrollBarPolicy(VERTICAL_SCROLLBAR_AS_NEEDED);
		
//		getViewport().setBackground(Color.decode(conf.getValueByName("dockBackgroundColor")));
//		getViewport().setForeground(Color.decode(conf.getValueByName("dockTextColor")));
		
		content = new JPanel();
//		content.setPreferredSize(this.getSize());
		content.setForeground(Color.decode(conf.getValueByName("dockTextColor")));
		content.setBackground(Color.decode(conf.getValueByName("dockBackgroundColor")));
		getViewport().add(content);
		
		
	}
	
	/**
	 * restituisce il JPanel contenitore degli oggetti
	 * @return
	 */
	public JPanel getContentPane(){
		return this.content;
	}
	
	public Object addObject(Object _obj){
		//setto logger e conf
		_obj.setLog(this.log);
		_obj.setConf(this.conf);
		
		
		this.objects.add(_obj); //aggiungo l'oggetto
		return this.objects.get(this.objects.size()-1); //lo ritotno all'utente 
	}
	
	public int generate(){
		this.log.info("Caricamento oggetti");
		for(int i=0;i<this.objects.size();i++){
			this.objects.get(i).generate();
			getContentPane().add(this.objects.get(i).gui);
		}
		
		return 0;
	}
}
