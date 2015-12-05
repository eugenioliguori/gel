package gel.component;

import gel.component.ports.Port;
import gel.dock.Dock;
import gel.dock.DockAction;
import gel.dock.DockActionHandler;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.Border;

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
	public Object(Logger _log, Config _conf,Dock _dock){
		setLog(_log);
		setConf(_conf);
		properties=new ArrayList<Property>();
		setId(); //setto un id provvisorio
		setTitle(getName()); //setto un titolo provvisorio
		setName(); //setto un name provvisiorio
		ports = new ArrayList<Port>(); //inizializzo le porte
		objects = new ArrayList<Object>(); //inizializzo gli oggetti

		/*
		 * GUI
		 */
		setDock(_dock);
		GI = new JPanel();
		GUI = new JPanel();
		setGuiDruggable(true);

	}

	/*
	 * ---------------------------------------------
	 * DOCK
	 */
	protected Dock dock;
	public void setDock(Dock _dock){dock=_dock;}
	public Dock getDock(){return dock;}

	//------------------------------------------
	
	/*
	 * ------------------------------------------
	 * PROPERIES
	 */
	private List<Property> properties;
	
	/**
	 * Struttura dati di una proprietà
	 * @author eugenio
	 *
	 */
	public class Property{
		public Property(String _name,String _value){
			name=_name;
			value=_value;
		}
		public String name;
		public String value;
	}
	
	/**
	 * Aggiunge una proprietà all'oggetto
	 * @param _name		String
	 * @param _value	String
	 */
	public void addProperty(String _name,String _value){properties.add(new Property(_name,_value));}
	
	public void setProperty(String _name, String _value){
		for(int a=0;a<properties.size();a++){
			if(properties.get(a).name==_name){properties.get(a).value=_value;}
		}
		return;
	}
	
	/**
	 * Rimuove tutte le proprietà dell'oggetto
	 */
	public void clearProperty(){properties.clear();}
	
	/**
	 * Restituisce un proprietà dell'oggetto dal nome
	 * @param _name	String
	 * @return	Property
	 */
	public Property getProperty(String _name){
		for(int a=0;a<properties.size();a++){
			if(properties.get(a).name==_name){return properties.get(a);}
		}
		return null;
	}
	/**
	 * Verifica se esiste una proprietà per l'oggetto con il nome specificato
	 * @param _name	String
	 * @return	Boolean
	 */
	public boolean hasProperty(String _name){
		for(int a=0;a<properties.size();a++){
			if(properties.get(a).name==_name){return true;}
		}
		return false;
	}
	
	/**
	 * Restituisce tutte le proprietà dell'oggetto
	 * @return	List<Property>
	 */
	public List<Property> getProperties(){
		return properties;
	}
	
	/**
	 * Restituisce il valore di una proprietà dell'oggetto dal nome
	 * @param _name	String
	 * @return	String
	 */
	public String getPropertyValue(String _name){
		for(int a=0;a<properties.size();a++){
			if(properties.get(a).name==_name){return properties.get(a).value;}
		}
		return null;
	}
	
	/**
	 * Verifica se una proprietà dell'oggetto possiede un valore, la ricerca 
	 * avviene per nome della proprietà
	 * @param _name	String
	 * @return	int		0  = proprietà non vuota
	 * @return	int		-1 = proprietà vuota
	 * @return	int		-2 = proprietà non esiste
	 */
	public int isBlankProperty(String _name){
		for(int a=0;a<properties.size();a++){
			if(properties.get(a).name==_name){
				if(properties.get(a).value==""){
					return 0;
				}else{
					return -1;
				}
			}
		}
		return -2;
	}
	
	/*
	 * -----------------------------------------
	 */
	/**
	 * aggiunge un oggetto
	 * @param _obj
	 * @return
	 */
	public Object addObject(Object _obj){
		dock.addStartSequenceObject(_obj); //aggiugo l'oggetto alla sequenza d'avvio degli oggetti
		dock.addStopSequenceObject(_obj);//aggiugo l'oggetto alla sequenza di stop degli oggetti
		objects.add(_obj); //aggiungo l'oggetto a questo oggetto
		return objects.get(objects.size()-1);
	}

	/**
	 * Ritorna tutti gli oggetti contenuti in questo oggetto
	 * @return	List<Object>
	 */
	public List<Object> getObjects(){return objects;}

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
		for(int a=0; a<ports.size();a++){
			if(ports.get(a).getName()==_name){
				ports.add(ports.get(a));
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
	 * restituisce una List con tutte le porte dell'oggetto
	 * @return	List	Oggetto List con le porte 
	 */
	public List<Port> getPorts(){
		return ports;
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
	public void setId(){
		Random rand = new Random();
		addProperty("id",String.valueOf(rand.nextInt((99999 - 10000 ) + 1) + 10000));
	}

	/**
	 * setta id dell'oggetto
	 * @param _id	Integer
	 */
	public void setId(int _id){
		addProperty("id",String.valueOf(_id));
	}

	/**
	 * Restituisce l'id dell'oggetto
	 */
	public int getId(){
		return Integer.valueOf(getPropertyValue("id"));
	}

	//---------------------------------------------


	/**
	 * setta il nome dell'oggetto
	 */
	public void setName(String _name){
		addProperty("name",String.valueOf(_name));
	}

	/**
	 * setta il nome dell'oggetto con un nome autogenerato
	 */
	public void setName(){
		addProperty("name",String.valueOf(getClass().getSimpleName()));
	}

	/**
	 * restituisce il nome dell'oggetto
	 */
	public String getName(){
		return getPropertyValue("name");
	}

	//---------------------------------------------
	protected String title;

	public void setTitle(String _title){
		title=_title;
	}

	public String getTitle(){
		return title;
	}

	/*
	 * -------------------------------------------------------
	 * DEVELOP MODE
	 */
	/**
	 * Attiva e disattiva la developm mode
	 */
	protected boolean developMode;
	public void setDevelopMode(boolean _val){
		developMode=_val;
	}
	/**
	 * Restituisce true se la develop mode è attiva
	 * @return
	 */
	public boolean getDevelopMode(){
		return developMode;
	}

	/*
	 * -------------------------------------------------------
	 * GUI
	 * 
	 * Le specifiche della gui vengono caricate da un file XML
	 * e la parte di codice è una classe Java
	 */
	protected Config confGui;
	public JPanel GI; //grahical Interface: interfaccia grafica(GEL+utente)
	public JPanel GUI; //graphical User Interface: Interfaccia grafica(Utente)
	public JLabel GITitle; //titolo dell'oggetto
	private ObjectDMConfigWindow OGELCW; //finestra di configurazione per l'oggetto in modalità sviluppo
	private Border objectBorderSelected; //bordo dell'oggetto selzionato
	private Border objectBorderUnselected; //bordo dell'oggetto non selzionato
	protected int lastPosX,lastPosY; //ultima posizione dell'oggetto nel dock, prima di quella attuale

	protected void loadGI(){
		GI.setLayout(new BorderLayout());
		GI.add(GUI, BorderLayout.CENTER);


		//titolo oggetto
		GITitle=new JLabel(title);
		Font fontGITitle = new Font( conf.getValueByName("objectTitleFont"), Font.PLAIN,Integer.parseInt(conf.getValueByName("objectTitleSize")));
		GITitle.setForeground(Color.decode(conf.getValueByName("objectTitleColor")));
		GITitle.setFont(fontGITitle);

		GI.add(GITitle,BorderLayout.PAGE_START);

		//bordo oggetto non selezionato
		objectBorderSelected=BorderFactory.createLineBorder(
				Color.decode(conf.getValueByName("objectBorderColorSelected")),
				Integer.parseInt(conf.getValueByName("objectBorderSizeSelected"))
				);
		//bordo oggetto selezionato
		objectBorderUnselected=BorderFactory.createLineBorder(
				Color.decode(conf.getValueByName("objectBorderColor")),
				Integer.parseInt(conf.getValueByName("objectBorderSize"))
				);

		setGuiUnselected();

		GI.setBackground(Color.decode(conf.getValueByName("objectBackgroudColor")));
		GI.setForeground(Color.decode(conf.getValueByName("objectTextColor")));

		//aggancio la finestra di configurazione per l'oggetto in modalità sviluppo
		OGELCW = new ObjectDMConfigWindow(this,"Object options - "+GITitle);
		//DAFARE: manca la voce nel menu per OGELCW

		//disegno le porte e le posiziono
		//		Insets insets = GI.getInsets();
		for(int i=0;i<ports.size();i++){
			ports.get(i).loadGI();
			//			this.ports.get(i).GI.setBounds(insets.left, 5 + insets.top,10, 10);
			GI.add(ports.get(i).GI, ports.get(i).getGuiPosition());

		}
	}

	/**
	 * Memorizza l'ultima posizione prima dello spostamento(drug)
	 * @param _X
	 * @param _Y
	 */
	public void setGuiLastPosition(int _X, int _Y){
		lastPosX=_X;
		lastPosY=_Y;
	}
	/**
	 * Restituisce il valore della X prima della posizione prima dell'ultimo spostamento(drug)
	 * @return int
	 */
	public int setGuiLastPositionX(){
		return lastPosX;
	}
	/**
	 * Restituisce il valore della Y prima della posizione prima dell'ultimo spostamento(drug)
	 * @return int
	 */
	public int setGuiLastPositionY(){
		return lastPosY;
	}

	//DA FARE: le setLight ora sono generiche, vanno modificate per accendere e spegnere le spie specifiche


	protected boolean draggable;
	/**
	 * Setta l'oggetto come spostabile o meno
	 * @param _val	boolean
	 */
	public void setGuiDruggable(boolean _val){
		draggable=_val;
	}
	public boolean getGuiDruggable(){
		return draggable;
	}


	public void setGuiSelected(){
		GI.setBorder(objectBorderSelected);
	}

	public void setGuiUnselected(){
		GI.setBorder(objectBorderUnselected);
	}

	/**
	 * Restituisce il pannello GUI
	 * @return	JPanel
	 */
	public JPanel getGUI(){return GUI;}
	/**
	 * Restituisce il panello GI che comprende il pannello GUI
	 * @return	JPanel
	 */
	public JPanel getGI(){return GI;}

	/**
	 * Rende tutte le porte invisibili in runtime
	 */
	public void setPortsRuntimeInvisible(){
		for(Port port: ports){
			port.setRuntimeInvisible();
		}
	}
	/**
	 * Rende tutte le porte visibili a runtime
	 */
	public void setPortsRuntimesVisible(){
		for(Port port: ports){
			port.setRuntimeVisible();
		}
	}

	/*
	 * -------------------------------------------------------
	 * RUN-TIME
	 */
	boolean run=false;
	protected Core core;
	/**
	 * Verifica se in esecuzione o no
	 * @return	boolean
	 */
	public boolean isStarted(){
		return run;
	}

	/**
	 * Avvia l'esecuzione dell'oggeto
	 * @return
	 */
	public void start(){
		if(!isStarted()){
			run=true;

			setDevelopMode(false);
			//se le porte sono setta invisibili in run-time, le nascondo
			for(Port port: ports){
				if(!port.isRuntimeVisible()){
					port.getGI().setVisible(false);
				}
			}
			core = new Core();
			core.start();
		}
	}

	/**
	 * ferma lesecuzione dell'oggetto
	 * @return
	 */
	public void stop(){
		if(isStarted()){
			core.interrupt();
			run=false;
			
			setDevelopMode(true);
			
			//ripritino visibili le porte che sono settate nascoste in run-time
			for(Port port: ports){
				if(!port.isRuntimeVisible()){
					port.getGI().setVisible(true);
				}
			}
		}
	}

	
	/**
	 * ferma lesecuzione dell'oggetto in maniera forzata
	 * @return
	 */
	public void kill(){
		if(isStarted()){
			core.stop();
			run=false;
			
			setDevelopMode(true);
			
			//ripritino visibili le porte che sono settate nascoste in run-time
			for(Port port: ports){
				if(!port.isRuntimeVisible()){
					port.getGI().setVisible(true);
				}
			}
		}
	}

	/**
	 * Questo metodo viene implementato dall'utente con le operazioni
	 * che l'oggetto deve eseguire tra start() e stop()
	 * @return	Integer
	 */
	abstract public int core();

	/**
	 * caricamento l'oggetto
	 * 
	 * @return	Integer		Ritorna 0 sempre
	 */
	public int generate(){
		log.info("Object generate");
		loadGI();
		return 0;
	}

	/*
	 * Il metodo astratto core che viene poi definito dall'utente
	 * viene avviato in un nuovo thread per non bloccare l'esecuzione 
	 * programma principale
	 */
	public class Core extends Thread {
		public void run(){
			core();
		}
	}

}
