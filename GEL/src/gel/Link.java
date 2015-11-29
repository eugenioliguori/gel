package gel;

import java.util.ArrayList;

import zjlib.Config;
import zjlib.Logger;

public class Link {
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
	
	//--------------------------------------------
	/**
	 * costruttore
	 */
	public Link(){
		
	}
	
	/**
	 * costruttore
	 */
	public Link(Logger _log, Config _conf){
		setLog(_log);
		setConf(_conf);
	}
}
