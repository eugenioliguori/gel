package gel.component;


import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

import zjlib.config.Config;
import zjlib.logger.Logger;

/**
 * Questa è una porta generica manipola solo byte
 */
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
	public Port(String _title){
		gui = new JPanel();
		setGuiTitle(_title);
		setDisable();
	}
	/**
	 * costruttore
	 */
	public Port(Logger _log, Config _conf,String _title){
		gui = new JPanel();
		setGuiTitle(_title);
		setDisable();
		setLog(_log);
		setConf(_conf);
	}
	//----------------------------------------------
	protected boolean enabled; //abilita/disabilita porta

	/**
	 * abilita la porta
	 */
	public void setEnable(){
		this.enabled=true;
		setRxEnable();
		setTxEnable();
		setLightOn();
	}
	/**
	 * disabilita la porta
	 */
	public void setDisable(){
		this.enabled=false;
		setRxEnable();
		setTxEnable();
		gui.setBackground(Color.decode("#BDBDBD"));
	}

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
	protected Port link;

	/**
	 * Setta il link(oggetto Port) per la porta, ritorna l'oggetto link
	 * @param _link	Port
	 * @return	Port
	 */
	public Port setLink(Port _link){
		this.link=_link;
		return this.link;
	}

	public Port getLink(){
		return this.link;
	}

	/*
	 * ----------------------------------------------
	 * GUI
	 */
	JPanel gui;
	String guiPosition;
	String guiTitle;
	
	public void loadGui(){
		if(guiPosition==null){setGuiPosition(LEFT);}
		gui.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));
		JLabel title = new JLabel(guiTitle);
		title.setFont (title.getFont ().deriveFont (8.0f));
		
		gui.add(title);
	}

	public static final String TOP=BorderLayout.NORTH;
	public static final String BOTTOM=BorderLayout.SOUTH;
	public static final String LEFT=BorderLayout.WEST;
	public static final String RIGHT=BorderLayout.EAST;

	/**
	 * Setta la posizione che avrà icona della porta
	 * nell'icon dell'oggetto, prende come argomenti
	 * le costanti Port.TOP,Port.BOTTOM,Port.LEFT,Port.RIGHT
	 * che sono identiche a quelle del BorderLayout.NORTH, etc..
	 * 
	 * @param _position String
	 */
	public void setGuiPosition(String _position){
		guiPosition=_position;
	}
	
	public void setGuiTitle(String _title){
		guiTitle=_title;
	}

	/**
	 * Restituisce la posizione della porta nella gui
	 * intesa come costante di BorderLayout
	 * @return	String	BorderLayout.NORTH, etc.
	 */
	public String getGuiPosition(){
		return guiPosition;
	}
	
	/**
	 * colora la porta con il colore verde scuro
	 */
	public void setLightOn(){
		gui.setBackground(Color.decode("#ACFA58"));
	}
	
	/**
	 * colora la porta con il colore verde chiaro
	 */
	public void setLightOff(){
		gui.setBackground(Color.decode("#4B8A08"));
	}
	
	/**
	 * colora la porta con il colore Rosso
	 */
	public void setLightErrorOn(){
		gui.setBackground(Color.decode("#FF0000"));
	}
	
	/**
	 * Ripristina il colore di setLightOff
	 */
	public void setLightErrorOff(){
		setLightOff();
	}

	
	/*
	 * ----------------------------------------------
	 * RX
	 */
	boolean rxEnable;
	public void setRxEnable(){rxEnable=true;}
	public void setRxDisable(){rxEnable=false;}
	public boolean isRxEnable(){return rxEnable;}
	
	
	byte[] buffer;
	
	public int rx(byte[] _data, int _lenght){
		if(!isRxEnable()){
			return -1;
		}
		setLightOn();
		buffer=_data;
		setLightOff();
		beforeRead();
		return 0;
	}
	
	 public int beforeRead(){
		 return 0;
	 }

	
	/*
	 * ----------------------------------------------
	 * TX
	 */
	boolean txEnable;
	public void setTxEnable(){txEnable=true;}
	public void setTxDisable(){txEnable=false;}
	public boolean isTxEnable(){return txEnable;}
	
	
	public int tx(byte[] _data, int _lenght){
		if(!isTxEnable()){
			return -1;
		}
		setLightOn();
		link.rx(_data,_lenght);
		setLightOff();
		return 0;
	}
	
	/**
	 * Scrive una stringa nel canale rx della porta lincata a questa porta
	 * partendo da una stringa che viene convertita in byte
	 * @param _data
	 * @return
	 */
	public int tx(String _data){
		if(!isTxEnable()){
			return -1;
		}
		setLightOn();
		
		//converto la stringa in byte
		byte[] b = _data.getBytes(StandardCharsets.UTF_8);
		
		
		link.rx(b,b.length);
		setLightOff();
		return 0;
	}
}
