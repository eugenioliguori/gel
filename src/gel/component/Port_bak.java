package gel.component;


import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Stroke;
import java.awt.geom.Line2D;
import java.io.ByteArrayInputStream;
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
public class Port_bak {
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
	Dock dock;
	public void setDock(Dock _dock){dock=_dock;}
	public Dock getDock(){return dock;}
	

	//----------------------------------------------
	/**
	 * costruttore
	 */
	public Port_bak(Logger _log, Config _conf,Dock _dock,String _title){
		setLog(_log);
		setConf(_conf);
		gui = new JPanel();
		setGuiTitle(_title);
		setRxDisable();
		setTxDisable();
		setName(_title);
		setDock(_dock);
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
	protected Link linkTerminal;
	protected Port_bak elink; //link interfaccia esterna 
	protected Port_bak ilink; //link interfaccia interna

	/**
	 * Setta il link(oggetto Port) per l'interfaccia esterna della porta, ritorna l'oggetto link
	 * @param _link	Port
	 * @return	Port
	 */
	public Port_bak setExternalLink(Port_bak _link){
		elink=_link;
		elink.setRxEnable();
		
		//creo il link
		Link link = new Link();
		dock.links.add(link); //aggiungo il link al dock, sarà lui a disegnarlo
		link.portA=this;
		link.portB=_link;
		
		setTxEnable();

		return elink;
	}

	public Port_bak getExternalLink(){
		return elink;
	}

	/**
	 * Setta il link(oggetto Port) per l'interfaccia interna della porta, ritorna l'oggetto link
	 * @param _link	Port
	 * @return	Port
	 */
	public Port_bak setInternalLink(Port_bak _link){
		ilink=_link;
		ilink.setRxEnable();
		setTxEnable();
		return ilink;
	}

	public Port_bak getInternalLink(){
		return ilink;
	}

	/*
	 * ----------------------------------------------
	 * GUI
	 */
	JPanel gui;
	JPanel guiLink;
	String guiPosition;
	String guiTitle;

	public void loadGui(){
		log.info("Port "+name+": elaborazione componenti grafiche");
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

	/**
	 * Colora la porta come disabilitata in grigio
	 */
	public void setLightDisable(){
		gui.setBackground(Color.decode("#BDBDBD"));
	}


	/*
	 * ----------------------------------------------
	 * RX
	 */
	boolean rxEnable;
	public void setRxEnable(){
		log.info("Port "+name+": canale RX abilitato");
		rxEnable=true;
		setLightOff();
	}
	public void setRxDisable(){
		log.info("Port "+name+": canale RX disabilitato");
		rxEnable=false;
		setLightDisable();
	}
	public boolean isRxEnable(){return rxEnable;}


	ByteArrayInputStream rxBuffer;

	public int rx(byte[] _data, int _lenght){
		//		if(!isRxEnable()){
		//			return -1;
		//		}
		//		setLightOn();
		//		
		//		rxBuffer = new ByteArrayInputStream(_data);
		//		
		//		buffer=new byte[_lenght];
		//		
		//		buffer=_data;
		//		setLightOff();
		//		beforeRead();
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
	public void setTxEnable(){
		log.info("Port "+name+": canale TX abilitato");
		txEnable=true;
		setLightOff();
	}
	public void setTxDisable(){
		log.info("Port "+name+": canale TX disabilitato");
		txEnable=false;
		setLightDisable();
	}
	public boolean isTxEnable(){return txEnable;}


	public int tx(byte[] _data, int _lenght){
		if(!isTxEnable()){
			return -1;
		}
		setLightOn();
		//		elink.rx(_data,_lenght);
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


		rx(b,b.length);
		setLightOff();
		return 0;
	}
}
