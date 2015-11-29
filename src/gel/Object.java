package gel;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.swing.JPanel;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;

import zjlib.config.Config;
import zjlib.logger.Logger;

abstract public class Object {
	protected Config conf;
	protected Logger log;
	protected int id;
	protected String name;
	protected List<Object> objects; //Oggetti interni all'oggetto
	
	/*
	 * ------------------------------------------
	 * LOG e CONFIG
	 */
	
	
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
	
	//------------------------------------------
	
	/**
	 * costruttore
	 */
	public Object(){
		setId(); //setto un id provvisorio
		setName(); //setto un name provvisiorio
		ports = new ArrayList<Port>(); //inizializzo le porte
		objects = new ArrayList<Object>(); //inizializzo gli oggetti
		gui = new JPanel();
	}
	
	/**
	 * costruttore
	 */
	public Object(Logger _log, Config _conf){
		setLog(_log);
		setConf(_conf);
		setId(); //setto un id provvisorio
		setName(); //setto un name provvisiorio
		ports = new ArrayList<Port>(); //inizializzo le porte
		objects = new ArrayList<Object>(); //inizializzo gli oggetti
		gui = new JPanel();
	}

	//------------------------------------------
	/**
	 * aggiunge un oggetto
	 * @param _obj
	 * @return
	 */
	public Object addObject(Object _obj){
		this.objects.add(_obj);
		return this.objects.get(this.objects.size()-1); 
	}
	
	//------------------------------------------
	protected List<Port> ports; //porte di connessione con gli altri oggetti

	/**
	 * Aggiunge una porta e restituisce l'oggetto nella lista porte
	 * @return 	Port	L'oggetto Port creato 
	 */
	public Port addPort(Port _port){
		this.ports.add(_port);
		return this.ports.get(this.ports.size()-1);
	}

	/**
	 * Aggiunge un gruppo di porte a quelle esistenti
	 * @param _ports	List<Port>
	 */
	public void addPorts(List<Port> _ports){
		for(int a=0;a<_ports.size();a++){
			this.ports.add(_ports.get(a));
		}
	}
	
	/**
	 * sostituisce le porte esistenti con quelle passate
	 * @param _ports	List<Port>
	 */
	public void substitutePorts(List <Port> _ports){
		this.ports = _ports;
	}

	/**
	 * Restituisce la prima porta avenete il nome specificato
	 * @param _name	String	Nome della porta
	 * @return	Port	Oggetto Port
	 * @return	null	Se non viene trovata nessuna porta con il nome specificato
	 */
	public Port getPort(String _name){
		for(int a=0; a<this.ports.size();a++){
			if(this.ports.get(a).getName()==_name){
				return this.ports.get(a);
			}
		}
		return null;
	}

	/**
	 * restituisce una List delle porte che hanno il nome specificato
	 * @param	_name	String	Nome delle porte
	 * @return	List	Oggetto List con le porte aventi il nome specificato
	 * @return	null	Se non sono state trovate porte con il nome specificato
	 */
	public List<Port> getPorts(String _name){
		List<Port> ports = new ArrayList<Port>(); 
		for(int a=0; a<this.ports.size();a++){
			if(this.ports.get(a).getName()==_name){
				ports.add(this.ports.get(a));
			}
		}
		//nessuna porta trovata
		if(ports.size()==0){
			return null;
		}else{
			return ports;
		}
	}
	
	/**
	 * Restituisce una porta dall'indice
	 * @param _index
	 * @return
	 */
	public Port getPort(int _index){
		return this.ports.get(_index);
	}

	//---------------------------------------
	
	
	/**
	 * setta id dell'oggetto con un id audogenerato
	 */
	@SuppressWarnings("null")
	public void setId(){
		Random rand = new Random();
		id = rand.nextInt((99999 - 10000 ) + 1) + 10000;
	}

	/**
	 * setta id dell'oggetto
	 * @param _id	Integer
	 */
	public void setId(int _id){
		this.id=_id;
	}

	/**
	 * Restituisce l'id dell'oggetto
	 */
	public int getId(){
		return this.id;
	}

	//---------------------------------------------
	
	
	/**
	 * setta il nome dell'oggetto
	 */
	public void setName(String _name){
		this.name = _name;
	}

	/**
	 * setta il nome dell'oggetto con un nome autogenerato
	 */
	public void setName(){
		this.name = this.getClass().getName();
	}

	/**
	 * restituisce il nome dell'oggetto
	 */
	public String getName(){
		return this.name;
	}

	/*
	 * -------------------------------------------------------
	 * GUI
	 * 
	 * Le specifiche della gui vengono caricate da un file XML
	 * e la parte di codice è una classe Java
	 */
	protected Config confGui;
	public JPanel gui;
	
	protected void guiLoad(){
		
		if(this.confGui==null){
			this.confGui=this.conf;
		}
		gui.setPreferredSize(new Dimension(Integer.parseInt(this.confGui.getValueByName("objectWidth")),Integer.parseInt(this.confGui.getValueByName("objectHeight"))));
		gui.setBorder(
				new TitledBorder(
						new LineBorder(
								Color.decode(conf.getValueByName("objectBorderColor")),
								Integer.parseInt(this.confGui.getValueByName("objectBorderSize"))),
								this.name,
							TitledBorder.CENTER, 
							TitledBorder.TOP, 
							new Font(conf.getValueByName("objectTitleFont"),Font.PLAIN,Integer.parseInt(this.confGui.getValueByName("objectTitleSize"))), 
							Color.decode(conf.getValueByName("objectTitleColor"))
							));
		
		gui.setBackground(Color.decode(conf.getValueByName("objectBackgroudColor")));
		gui.setForeground(Color.decode(conf.getValueByName("objectTextColor")));
	}
	
	
	/*
	 * -------------------------------------------------------
	 * RUN-TIME
	 */

	/**
	 * Avvia l'esecuzione dell'oggeto
	 * @return
	 */
	public int start(){
		return -1;

	}

	/**
	 * ferma lesecuzione dell'oggetto
	 * @return
	 */
	public int stop(){
		return -1;

	}

	/**
	 * caricamento l'oggetto
	 * 
	 * @return
	 */
	public int generate(){
		guiLoad();
		return 0;
	}
}
