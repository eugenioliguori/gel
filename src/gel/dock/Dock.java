package gel.dock;


import gel.component.Link;
import gel.component.Object;
import gel.component.ports.Action;
import gel.component.ports.ActionHandler;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.RenderingHints;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionAdapter;
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
	private String title;


	protected final List<Object> objects; //oggetti
	public final List<Link> links; //Link tra gli oggetti

	

	/**
	 * Costruttore, crea un Dock e i sui componenti base come la griglia per gli oggetti
	 * le List degli oggetti e i settaggi iniziali
	 * @param _log
	 * @param _conf
	 */
	public Dock(Logger _log, Config _conf,String _title){
		log=_log;
		conf = _conf;

		setTitle(_title);

		objects = new ArrayList<Object>();
		links = new ArrayList<Link>();
		objectsStartSequence= new ArrayList<Object>();
		objectsStopSequence=new ArrayList<Object>();

		setDevelopMode(true);

		//setto la GUI
		setHorizontalScrollBarPolicy(HORIZONTAL_SCROLLBAR_AS_NEEDED);
		setVerticalScrollBarPolicy(VERTICAL_SCROLLBAR_AS_NEEDED);

		//		getViewport().setBackground(Color.decode(conf.getValueByName("dockBackgroundColor")));
		//		getViewport().setForeground(Color.decode(conf.getValueByName("dockTextColor")));

		content = new contentContainer(log,conf);
		//		content.setPreferredSize(this.getSize());
		content.setForeground(Color.decode(conf.getValueByName("dockTextColor")));
		content.setBackground(Color.decode(conf.getValueByName("dockBackgroundColor")));
		getViewport().add(content);

		
		runStarted=false;
		
		/**
		 * Action listner per il click sul dock
		 */
		content.addMouseListener(new MouseListener() {
			
			@Override
			public void mouseClicked(MouseEvent e) {
				
				
			}

			@Override
			public void mouseEntered(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseExited(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mousePressed(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseReleased(MouseEvent arg0) {
				unselectAllObjects(); //deseleziona tutti gli oggetti nel dock
				
			}
		});

	}

	/*
	 * ------------------------------------------------------
	 * CONTENT CONTAINER
	 * 
	 * Qui viene estesa una classe JPanel per poter sovrascrivere il metodo
	 * PaintComponent per disegnare i link
	 */
	protected contentContainer content; //contenitore degli oggetti nello scrollPane
	
	/**
	 * 
	 */
	protected class contentContainer extends JPanel{
		private static final long serialVersionUID = 7572396355574657531L;

		/**
		 * Costruttore
		 * @param _log
		 * @param _conf
		 */
		public contentContainer(Logger _log, Config _conf){
			setLayout(null);
			setLog(_log);
			setConf(_conf);
		}
		
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


		@Override
		protected void paintComponent(Graphics g) {


			super.paintComponent(g);
			Graphics2D g2d = (Graphics2D) g;
			g2d.setColor(Color.decode(conf.getValueByName("LinkColor")));
			g2d.setRenderingHint(
					RenderingHints.KEY_ANTIALIASING,
					RenderingHints.VALUE_ANTIALIAS_ON);
			g2d.setStroke(new BasicStroke(Integer.parseInt(conf.getValueByName("LinkSize")),
					BasicStroke.CAP_SQUARE, BasicStroke.JOIN_BEVEL));

			for(int i=0;i<links.size();i++){
				Container objAGui = links.get(i).portA.getGI().getParent(); //gui dell'oggetto che contiene la porta portA del link
				Container objBGui = links.get(i).portB.getGI().getParent(); //gui dell'oggetto che contiene la porta portB del link

				/*
				 * posizione della porta: posizione relativa(al container) dell'oggetto(GEL Object) + 
				 * posizione relativa della porta(Port) nell'oggetto +
				 * metà dell'altezza dell'oggetto Port per posizionare il link al centro della
				 * porta e non all'origine
				 */
				int portAx = objAGui.getLocation().x+links.get(i).portA.getGI().getLocation().x+(links.get(i).portA.getGI().getWidth());
				int portAy = objAGui.getLocation().y+links.get(i).portA.getGI().getLocation().y+(links.get(i).portA.getGI().getHeight()/2);
				int portBx = objBGui.getLocation().x+links.get(i).portB.getGI().getLocation().x+(links.get(i).portB.getGI().getWidth());
				int portBy = objBGui.getLocation().y+links.get(i).portB.getGI().getLocation().y+(links.get(i).portB.getGI().getHeight()/2);

				g.drawLine(portAx, portAy, portBx, portBy);
			}
		}

	}

	/*
	 * -------------------------------------------------------
	 */
	public void setTitle(String _title){title=_title;}
	public String getTitle(){return title;}

	/*
	 * -------------------------------------------------------
	 * DEVELOP MODE
	 */
	/**
	 * Attiva e disattiva la developm mode
	 */
	protected boolean developMode;
	public void setDevelopMode(boolean _val){
		developMode=_val; //setto la modalità svilutto al dock

		//setto la modalità sviluppo agli oggetti
		for(int i=0;i<objects.size();i++){
			objects.get(i).setDevelopMode(_val);
		}
	}
	/**
	 * Restituisce true se la develop mode è attiva
	 * @return
	 */
	public boolean getDevelopMode(){
		return developMode;
	}

	/*
	 * ---------------------------------------------------
	 * GESTIONE DEGLI OGGETTI
	 * 
	 */
	/**
	 * restituisce il JPanel contenitore degli oggetti
	 * @return JPanel 	Ritona il JPanel content
	 */
	public JPanel getContentPane(){
		return content;
	}

	/**
	 * Aggiunge un oggetto al dock
	 * @param _obj GEL Object
	 * @return GEL Object 	L'oggetto appena creato
	 */
	public Object addObject(Object _obj){
		addStartSequenceObject(_obj); //aggiugo l'oggetto alla sequenza d'avvio degli oggetti
		addStopSequenceObject(_obj);//aggiugo l'oggetto alla sequenza di stop degli oggetti
		_obj.setPortsRuntimeInvisible();
		objects.add(_obj); //aggiungo l'oggetto
		
		return objects.get(this.objects.size()-1); //lo ritotno all'utente 
	}
	
	//return all objects
	public List<Object> getObjects(){
		return objects;
	}

	/**
	 * Genera gli oggetti del dock
	 */
	public void generate(){
		log.info("Generate Dock objects");
		int i;
		for(i=0;i<objects.size();i++){
			objects.get(i).generate(); //genero oggetto

			//setto i nuovi oggetti al centro se non viene specificato dove
			Insets insets = getContentPane().getInsets();
			Dimension size = objects.get(i).getGI().getPreferredSize();
			objects.get(i).getGI().setBounds(25 + insets.left, 5 + insets.top,
					size.width, size.height);

			getContentPane().add(objects.get(i).getGI()); //inserisco nel contentContaner
			handleMouseEvent(objects.get(i)); //aggancio l'oggetto all'handle del drug
		}
		
		setDevelopMode(developMode);
	}




	/*
	 * ---------------------------------------------------
	 * GUI
	 */
	List<Object> objectSelected = new ArrayList<Object>();
	
	public List<Object> getSelectedObjects(){
		return objectSelected;
	}

	/**
	 * Seleziona un oggetto nel dock, se viene passato true all'argomento
	 * _OR allora vengono deselezionati tutti gli alatri oggetti altrimenti
	 * la selezione degli altri oggetti viene mantenuta
	 * @param _obj	gel.component.Object
	 * @param _OR	Boolean
	 */
	public void selectObject(Object _obj,boolean _OR){
		if(isSelectLocked()){return;}
		if(_OR){unselectAllObjects();}
		//seleziono graficamente lì'oggettp
		if(_obj.getDevelopMode()){
			_obj.setGuiSelected();
			objectSelected.add(_obj); //memorizzo l'oggetto selezionato
			
			dockActionHandler.runOnSelectObject();
		}
		
		
	}
	
	/**
	 * Deseleziona un oggetto nel Dock
	 */
	public void unselectObject(Object _obj){
		if(isSelectLocked()){return;}
	}
	
	/**
	 * Deseleziona tutti gli oggetti nel dock
	 */
	public void unselectAllObjects(){
		if(isSelectLocked()){return;}
		for(int a=0;a<objectSelected.size();a++){
			objectSelected.get(a).setGuiUnselected();
		}
		objectSelected.clear();
		
		dockActionHandler.runOnUnselectAllObjects();
		
	}
	
	/**
	 * Seleziona tutti gli oggetti nel dock
	 */
	public void selectAllObjects(){
		if(isSelectLocked()){return;}
		List<Object> objs=getObjects();
		for(int a=0;a<objs.size();a++){
			selectObject(objs.get(a),false);
		}
	}
	
	/**
	 * Inverte la selezione degli oggetti 
	 */
	public void selectInvert(){
		if(isSelectLocked()){return;}
	}
	
	private boolean selectLock = false;
	public boolean isSelectLocked(){
		return selectLock;
	}
	public void selectUnlock(){
		selectLock=false;
	}
	public void selectLock(){
		selectLock=true;
	}
	
	/*
	 * -----------------------------------------
	 */

	/**
	 * Action Listner per il drag degli oggetti nel Dock e altri eventi del mouse
	 * sugli oggeti
	 * @param _obj	GEL Object
	 */
	private void handleMouseEvent(final Object _obj){

		final JPanel panel = _obj.getGI();
		
		/*
		 * Drag degli oggetti
		 */
		panel.addMouseMotionListener(new MouseMotionAdapter() {
			public void mouseDragged(MouseEvent me) {
				//l'oggetto può essere spostato solo se in modalita sviluppo
				if(_obj.getDevelopMode()){
					me.translatePoint(me.getComponent().getLocation().x, me.getComponent().getLocation().y);
					panel.setLocation(me.getX(), me.getY());
					getContentPane().repaint(); //ridisegno il link
				}
			}
		});
		
		panel.addMouseListener(new MouseListener() {
			
			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mousePressed(MouseEvent e) {
				selectObject(_obj,true);

				//DA FARE: deve prima controllare se è stato mosso, se no mantiene la posizione gia memorizzata
				_obj.setGuiLastPosition(_obj.getGI().getX(),_obj.getGI().getY()); //setto l'ultima posizione prima dello spostamento
				
			}
			
			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
		});
			
	}
	
	
	
	
	/*
	 * -------------------------------------------
	 * RUN-TIME
	 */
	
	private boolean runStarted; //primo oggetto ad essere avviato
	public boolean isStarted(){return runStarted;}
	
	public void start(){
		runStarted=true;
			//avvio gli oggetti
			for(Object object: objectsStartSequence){
				object.start();
			}
	}
	
	public void stop(){
		//fermo gli oggetti
		for(Object object: objectsStartSequence){
			object.stop();
		}
		runStarted=false;
	}
	
	public void kill(){
		//fermo gli oggetti
		for(Object object: objectsStartSequence){
			object.kill();
		}
		runStarted=false;
	}
	
	/*
	 * ---------------------------------------------
	 * RUN-TIME SEQUENCE
	 */
	protected List<Object> objectsStartSequence; //sequenza di start degli oggetti
	protected List<Object> objectsStopSequence; //sequenza di stop degli oggetti
	
	/**
	 * Ritorna la lista(List) di oggetti ordinati nella sequenza di avvio
	 * @return
	 */
	public List<Object> getObjectsStartSequence(){return objectsStartSequence;}
	
	/**
	 * Ritorna la lista(List) di oggetti ordinati nella sequenza di avvio
	 * @return
	 */
	public List<Object> getObjectsStopSequence(){return objectsStopSequence;}
	/**
	 * Aggiunge un oggetto alla lista di start di esecuzione oggetti
	 */
	public void addStartSequenceObject(Object _object){objectsStartSequence.add(_object);}
	
	/**
	 * Aggiunge un oggetto alla lista di stop di esecuzione oggetti
	 */
	public void addStopSequenceObject(Object _object){objectsStopSequence.add(0,_object);}
	
	/*
	 * --------------------------------------------
	 * DOCK ACTION
	 */
	DockActionHandler dockActionHandler = DockActionHandler.getInstance();
	
	/**
	 * Aggiunge un azione da eseguire quando viene selezionato un solo oggetto
	 * nel dock
	 * @param _action	DockAction
	 */
	public void addActionOnSelectObject(DockAction _action){
		dockActionHandler.addActionOnSelectObject(_action);
	}
	/**
	 * Aggiunge un azione da eseguire quando vengono deselezionati tutti gli oggetti
	 * nel dock
	 * @param _action	DockAction
	 */
	public void addActionOnUnselectAllObjects(DockAction _action){
		dockActionHandler.addActionOnUnselectAllObjects(_action);
	}
	
	
}
