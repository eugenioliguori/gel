package gel.component;

import gel.component.ports.Port;
import gel.dock.Dock;

import java.awt.Component;
import java.awt.Dimension;
import java.util.Collections;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.TreeCellRenderer;

import zjlib.config.Config;
import zjlib.logger.Logger;

/**
 * Questa classe implementa una finestra per gestire le opzioni
 * dell'oggetto durante la modalità di sviluppo
 */
public class ObjectsManagerWindow extends JFrame{
	private static final long serialVersionUID = 4254592763990454281L;
	Config conf;
	Logger log;
	JTree tree;
	Dock dock;

	/**
	 * Costruttore, prende come argomenti l'oggetto di cui gestice le opzioni
	 * e il titolo che dovrà avere la finestra
	 * @param _obj		GEL Object
	 * @param _title	String
	 */
	public ObjectsManagerWindow(Logger _log, Config _conf,Dock _dock){
		super("Objects Manager");
		dock=_dock;
		conf=_conf;
		log=_log;
		setMinimumSize(new Dimension(800,300));

		createTree();

		pack();
	}



	private void createTree(){


		DefaultMutableTreeNode root = new DefaultMutableTreeNode("Root");
		DefaultMutableTreeNode DocksNode = new DefaultMutableTreeNode("Docks");

		/*
		 * Objects manager
		 */
		DefaultMutableTreeNode ObjectsNode = new DefaultMutableTreeNode("Objects");
		root.add(DocksNode);
		DocksNode.add(ObjectsNode);

		List<Object> objects = dock.getObjects();
		for(Object object: objects){
			ObjectsNode.add(createObjectManagerNode(object));
		}
		
		/*
		 * Start Sequnce manager
		 */
		DefaultMutableTreeNode ObjectsStartSequenceNode = new DefaultMutableTreeNode("Start sequence");
		DocksNode.add(ObjectsStartSequenceNode);

		List<Object> osas = dock.getObjectsStartSequence();
		for(Object object: osas){
			ObjectsStartSequenceNode.add(new DefaultMutableTreeNode("["+object.getId()+"] "+object.getName()+"::"+object.getTitle()));
		}
		
		/*
		 * Stop sequence manager
		 */
		DefaultMutableTreeNode ObjectsStopSequenceNode = new DefaultMutableTreeNode("Stop sequence");
		DocksNode.add(ObjectsStopSequenceNode);

		List<Object> osts = dock.getObjectsStopSequence();
		for(Object object: osts){
			ObjectsStopSequenceNode.add(new DefaultMutableTreeNode("["+object.getId()+"] "+object.getName()+"::"+object.getTitle()));
		}

		//create the tree by passing in the root node
		tree = new JTree(root);
		tree.setRootVisible(false);
		tree.setExpandsSelectedPaths(true);

		for (int i = 0; i < tree.getRowCount(); i++) {
			tree.expandRow(i);
		}

		add(new JScrollPane(tree));
	}

	/**
	 * Crea un nodo per un oggetto
	 * @param _object
	 * @return
	 */
	private DefaultMutableTreeNode createObjectManagerNode(Object _object){

		DefaultMutableTreeNode obj = new DefaultMutableTreeNode("["+_object.getId()+"] "+_object.getName()+"::"+_object.getTitle());
		List<Port> ports = _object.getPorts();
		for(Port port: ports){
			DefaultMutableTreeNode prt = new DefaultMutableTreeNode("Port: "+port.getName());
			obj.add(prt);
		}

		//creo gli oggetti interni
		for(Object object: _object.getObjects()){
			obj.add(createObjectManagerNode(object));
		}

		return obj;


	}
}
