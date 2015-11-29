package gel;


import zjlib.config.Config;
import zjlib.logger.Logger;


public class Port {
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
	
	//----------------------------------------------
	/**
	 * costruttore
	 */
	public Port(){
	}
	/**
	 * costruttore
	 */
	public Port(Logger _log, Config _conf){
		setLog(_log);
		setConf(_conf);
	}
	//----------------------------------------------
	protected boolean enabled; //abilita/disabilita porta
	
	/**
	 * abilita la porta
	 */
	public void enable(){this.enabled=true;}
	/**
	 * disabilita la porta
	 */
	public void disable(){this.enabled=false;}
	
	//----------------------------------------------
	protected String name; //nome della porta
	
	/**
	 * Setta il nome della porta
	 * @param _name
	 */
	public void setName(String _name){
		this.name = _name;
	}
	/**
	 * Restituisce il nome della porta
	 * @return
	 */
	public String getName(){
		return this.name;
	}
	
	/*
	 * ----------------------------------------------
	 * LINK
	 */
	Link link;
	
	/**
	 * Setta il link(oggetto Link) per la porta, ritorna l'oggetto link
	 * @param _link	Link
	 * @return	Link
	 */
	public Link setLink(Link _link){
		this.link=_link;
		return this.link;
	}
	
	public Link getLink(){
		return this.link;
	}
	
	/*
	 * ----------------------------------------------
	 * RX
	 */
	
	
	/*
	 * ----------------------------------------------
	 * TX
	 */
	
	
}
