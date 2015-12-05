package gel.component.ports;


import gel.component.Link;
import gel.dock.Dock;

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
import java.nio.charset.Charset;
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
	public Port(Logger _log, Config _conf,Dock _dock,String _title){
		setLog(_log);
		setConf(_conf);
		GI = new JPanel();
		setGuiTitle(_title);
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
	protected Port link; //link interfaccia esterna 

	/**
	 * Setta il link(oggetto Port) per l'interfaccia esterna della porta, ritorna l'oggetto link
	 * @param _link	Port
	 * @return	Port
	 */
	public Port setLink(Port _link){
		link=_link;

		//creo il link
		Link link = new Link();
		dock.links.add(link); //aggiungo il link al dock, sarà lui a disegnarlo
		link.portA=this;
		link.portB=_link;


		return _link;
	}

	public Port getLink(){
		return link;
	}

	/*
	 * ----------------------------------------------
	 * GUI
	 */
	public JPanel GI;
	JPanel guiLink;
	String guiPosition;
	String guiTitle;

	public void loadGI(){
		if(guiPosition==null){setGuiPosition(LEFT);}
		GI.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));
		JLabel title = new JLabel(guiTitle);
		title.setFont (title.getFont ().deriveFont (8.0f));

		GI.add(title);
	}

	public static final String TOP=BorderLayout.NORTH;
	public static final String BOTTOM=BorderLayout.SOUTH;
	public static final String LEFT=BorderLayout.WEST;
	public static final String RIGHT=BorderLayout.EAST;

	public JPanel getGI(){return GI;}

	private boolean runTimeGIVisibility; //visibilità della porta nella grafica in runtime
	/**
	 * Rende invisibile la porta in run-time
	 */
	public void setRuntimeInvisible(){
		runTimeGIVisibility=false;
	}
	/**
	 * Rende visibile la porta in run-time
	 */
	public void setRuntimeVisible(){
		runTimeGIVisibility=true;
	}
	/**
	 * Controlla se la porta è visibile in run-time
	 * @return	Boolean
	 */
	public boolean isRuntimeVisible(){
		return runTimeGIVisibility;
	}

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

	/*
	 * --------------------------------------
	 * ACTION
	 */
	ActionHandler rxActionHandler = ActionHandler.getInstance();
	ActionHandler txActionHandler = ActionHandler.getInstance();

	public void addRxAction(Action _action){
		rxActionHandler.addAction(_action);
	}
	public void addTxAction(Action _action){
		txActionHandler.addAction(_action);
	}

	/*
	 *----------------------------------
	 *RX 
	 */

	public int rx(String _data){
		rxActionHandler.run(_data);
		return 0;
	}
	
	public int rx(int _data){
		rxActionHandler.run(_data);
		return 0;
	}
	
	public int rx(float _data){
		rxActionHandler.run(_data);
		return 0;
	}
	
	public int rx(long _data){
		rxActionHandler.run(_data);
		return 0;
	}
	
	public int rx(byte[] _data){
		rxActionHandler.run(_data);
		return 0;
	}
	
	public int rx(char _data){
		rxActionHandler.run(_data);
		return 0;
	}
	
	public int rx(char[] _data){
		rxActionHandler.run(_data);
		return 0;
	}
	
	public int rx(short _data){
		rxActionHandler.run(_data);
		return 0;
	}
	
	public int rx(double _data){
		rxActionHandler.run(_data);
		return 0;
	}

	/*
	 *----------------------------------
	 *TX
	 */
	protected String txBuffer; //buffer string

	public int tx(String _data){
		getLink().rx(_data);
		txActionHandler.run(_data);
		return 0;
	}
	
	public int tx(int _data){
		getLink().rx(_data);
		txActionHandler.run(_data);
		return 0;
	}
	
	public int tx(float _data){
		getLink().rx(_data);
		txActionHandler.run(_data);
		return 0;
	}
	
	public int tx(byte[] _data){
		getLink().rx(_data);
		txActionHandler.run(_data);
		return 0;
	}
	
	public int tx(long _data){
		getLink().rx(_data);
		txActionHandler.run(_data);
		return 0;
	}
	
	public int tx(char _data){
		getLink().rx(_data);
		txActionHandler.run(_data);
		return 0;
	}
	
	public int tx(char[] _data){
		getLink().rx(_data);
		txActionHandler.run(_data);
		return 0;
	}
	
	public int tx(double _data){
		getLink().rx(_data);
		txActionHandler.run(_data);
		return 0;
	}
	
}
