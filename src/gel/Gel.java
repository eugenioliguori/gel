package gel;

import gel.component.GelAboutWindow;
import gel.component.GelOptionsWindow;
import gel.component.GelStatsPanel;
import gel.component.Object.Property;
import gel.component.ObjectsManagerWindow;
import gel.component.ProjectOptionsWindow;
import gel.component.PropertyBox;
import gel.component.footer.GelFooter;
import gel.component.ports.Action;
import gel.component.toolbar.ToolbarMain;
import gel.dev.object.GelLabel;
import gel.dev.object.Test;
import gel.component.Object;
import gel.dock.Dock;
import gel.dock.DockAction;
import gel.dock.DockPanel;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;
import javax.swing.border.LineBorder;

import static java.awt.event.InputEvent.CTRL_DOWN_MASK;
import zjlib.config.Config;
import zjlib.config.exception.ConfigFileContextNotFound;
import zjlib.config.exception.ConfigFileParameterTooManyContext;
import zjlib.config.exception.ConfigFilePathException;
import zjlib.logger.Logger;
import zjlib.cli.CliOpt;
import zjlib.cli.exception.CLIOptionArgumentRequiredException;
import zjlib.cli.exception.CLIOptionArgumentValueRequiredException;

public class Gel {
	static CliOpt opts; //cli option parser
	static Logger log; //logger
	static Config conf; //configuratore

	static String dflConfFile; //file di configurazione locale di default, se non specificato con -c

	static JFrame frame;
	static Dock dock;



	/*
	 * -------------------------------------------------
	 * GUI: MENU PRINCIPALE
	 */
	protected static JMenuBar createMenuMain() {
		//		Color.decode(confGui.getValueByName("objectBorderColorSelected")),
		//		Integer.parseInt(confGui.getValueByName("objectBorderSizeSelected"))
		//Costruzione della menuBar
		JSeparator sepa = new JSeparator(JSeparator.HORIZONTAL);
		sepa.setBorder(null);
		sepa.setBackground(Color.decode(conf.getValueByName("menuAcceleratorColor")));

		JMenuBar menuBar = new JMenuBar();
		//customizzazione del menu
		//http://www.java2s.com/Tutorial/Java/0240__Swing/CustomizingJMenuLookandFeel.htm
		menuBar.setBackground(Color.decode(conf.getValueByName("menuBackgroundColor")));
		//		menuBar.setForeground(Color.decode(conf.getValueByName("menuForegroundColor")));
		Font menuMainFont = new Font( conf.getValueByName("menuFont"), Font.PLAIN,Integer.parseInt(conf.getValueByName("menuFontSize")));
		Font menuMainAcceleratorFont = new Font( conf.getValueByName("menuFont"), Font.PLAIN,Integer.parseInt(conf.getValueByName("menuAcceleratorFontSize")));
		UIManager.put("Menu.font", menuMainFont);
		UIManager.put("MenuBar.background", Color.decode(conf.getValueByName("menuBackgroundColor")));
		UIManager.put("MenuBar.foreground", Color.decode(conf.getValueByName("menuForegroundColor")));
		UIManager.put("Menu.background", Color.decode(conf.getValueByName("menuBackgroundColor")));
		UIManager.put("Menu.foreground", Color.decode(conf.getValueByName("menuForegroundColor")));
		UIManager.put("MenuItem.background", Color.decode(conf.getValueByName("menuBackgroundColor")));
		UIManager.put("MenuItem.foreground", Color.decode(conf.getValueByName("menuForegroundColor")));
		UIManager.put("MenuItem.acceleratorForeground", Color.decode(conf.getValueByName("menuAcceleratorColor")));
		UIManager.put("MenuItem.acceleratorSelectionForeground", Color.decode(conf.getValueByName("menuAcceleratorColorSelected")));
		UIManager.put("MenuItem.acceleratorFont", menuMainAcceleratorFont);
		UIManager.put("MenuItem.font", menuMainFont);
		UIManager.put("MenuItem.selectionBackground", Color.decode(conf.getValueByName("menuBackgroundColorSelected")));
		UIManager.put("MenuItem.selectionForeground", Color.decode(conf.getValueByName("menuForegroundColorSelected")));
		UIManager.put("MenuItem.borderPainted", false);
		UIManager.put("Menu.border", new LineBorder(Color.decode(conf.getValueByName("menuBorderColor")), Integer.parseInt(conf.getValueByName("menuBorderSize"))));
		UIManager.put("MenuBar.border", new LineBorder(Color.decode(conf.getValueByName("menuBorderColor")), Integer.parseInt(conf.getValueByName("menuBorderSize"))));
		UIManager.put("MenuItem.opaque", true);
		/*
		 * menu File
		 */
		JMenu menuFile = new JMenu("File");
		menuFile.setMnemonic(KeyEvent.VK_F);

		JMenuItem menuFileProjectNew = new JMenuItem("New",new ImageIcon("images/new.png"));
		menuFileProjectNew.setMnemonic('N');
		menuFileProjectNew.setAccelerator(KeyStroke.getKeyStroke('N', CTRL_DOWN_MASK));

		JMenuItem menuFileProjectOpen = new JMenuItem("Open",new ImageIcon("images/open.png"));
		menuFileProjectOpen.setMnemonic('O');
		menuFileProjectOpen.setAccelerator(KeyStroke.getKeyStroke('O', CTRL_DOWN_MASK));

		JMenuItem menuFileProjectClose = new JMenuItem("Close",new ImageIcon("images/close.png"));
		menuFileProjectClose.setMnemonic('W');
		menuFileProjectClose.setAccelerator(KeyStroke.getKeyStroke('W', CTRL_DOWN_MASK));

		JMenuItem menuFileProjectSave = new JMenuItem("Save",new ImageIcon("images/save.png"));
		menuFileProjectSave.setMnemonic('S');
		menuFileProjectSave.setAccelerator(KeyStroke.getKeyStroke('S', CTRL_DOWN_MASK));

		JMenuItem menuFileProjectSaveAs = new JMenuItem("Save as...");
		menuFileProjectSaveAs.setMnemonic('A');
		menuFileProjectSaveAs.setAccelerator(KeyStroke.getKeyStroke("control shift S"));

		JMenuItem menuFilePrint = new JMenuItem("Print",new ImageIcon("images/print.png"));
		menuFilePrint.setMnemonic('P');
		menuFilePrint.setAccelerator(KeyStroke.getKeyStroke("control P"));

		JMenuItem menuFileImport = new JMenuItem("Import",new ImageIcon("images/import.png"));
		JMenuItem menuFileExport = new JMenuItem("Export",new ImageIcon("images/export.png"));
		JMenuItem menuFileExit = new JMenuItem("Exit");
		//		JMenu radioButtonMenu = new JMenu("Setta sfondo");
		//		JRadioButtonMenuItem buttonBlue = new JRadioButtonMenuItem("Blue");
		//		JRadioButtonMenuItem buttonRed = new JRadioButtonMenuItem("Rosso");
		//		JRadioButtonMenuItem buttonGreen = new JRadioButtonMenuItem("Verde");
		//		ButtonGroup group = new ButtonGroup();
		//		group.add(buttonBlue);
		//		group.add(buttonGreen);
		//		group.add(buttonRed);
		//		radioButtonMenu.add(buttonBlue);
		//		radioButtonMenu.add(buttonGreen);
		//		radioButtonMenu.add(buttonRed);

		menuFile.add(menuFileProjectNew);
		menuFile.addSeparator();
		menuFile.add(menuFileProjectOpen);
		menuFile.add(menuFileProjectClose);
		menuFile.addSeparator();
		menuFile.add(menuFileProjectSave);
		menuFile.add(menuFileProjectSaveAs);
		//		menuFile.add(radioButtonMenu);//popup pull-right
		menuFile.addSeparator();
		menuFile.add(menuFilePrint);
		menuFile.addSeparator();
		menuFile.add(menuFileImport);
		menuFile.add(menuFileExport);
		menuFile.addSeparator();
		menuFile.add(menuFileExit);
		menuBar.add(menuFile);

		/*
		 * menu edit
		 */
		JMenu menuEdit = new JMenu("Edit");
		menuEdit.setMnemonic(KeyEvent.VK_E);

		JMenuItem menuEditCopy = new JMenuItem("Copy",new ImageIcon("images/copy.png"));
		menuEditCopy.setMnemonic('C');
		menuEditCopy.setAccelerator(KeyStroke.getKeyStroke('C', CTRL_DOWN_MASK));

		JMenuItem menuEditCopySpecial = new JMenuItem("Copy special");
		menuEditCopySpecial.setMnemonic('S');
		menuEditCopySpecial.setAccelerator(KeyStroke.getKeyStroke("control shift C"));

		JMenuItem menuEditCut= new JMenuItem("Cut",new ImageIcon("images/cut.png"));
		menuEditCut.setMnemonic('U');
		menuEditCut.setAccelerator(KeyStroke.getKeyStroke('X', CTRL_DOWN_MASK));

		JMenuItem menuEditPaste= new JMenuItem("Paste",new ImageIcon("images/paste.png"));
		menuEditPaste.setMnemonic('P');
		menuEditPaste.setAccelerator(KeyStroke.getKeyStroke('V', CTRL_DOWN_MASK));

		JMenuItem menuEditOptions= new JMenuItem("Options",new ImageIcon("images/settings.png"));
		menuEditOptions.setMnemonic('O');
		menuEditOptions.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				GelOptionsWindow win = new GelOptionsWindow();
				win.pack();
				win.setVisible(true);
			}
		});

		JMenuItem menuEditSelectAll= new JMenuItem("Select all");
		menuEditSelectAll.setMnemonic('L');
		menuEditSelectAll.setAccelerator(KeyStroke.getKeyStroke("control A"));
		menuEditSelectAll.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dock.selectAllObjects();
			}
		});

		JMenuItem menuEditInvertSelection= new JMenuItem("Invert selection");
		menuEditInvertSelection.setMnemonic('I');
		menuEditInvertSelection.setAccelerator(KeyStroke.getKeyStroke("control I"));
		menuEditInvertSelection.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dock.selectInvert();
			}
		});

		JCheckBoxMenuItem menuEditLockSelection = new JCheckBoxMenuItem("Lock/Unlock selection");
		menuEditLockSelection.setMnemonic('K');
		menuEditLockSelection.setAccelerator(KeyStroke.getKeyStroke("control shift K"));
		menuEditLockSelection.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(dock.isSelectLocked()){
					dock.selectUnlock();
				}else{
					dock.selectLock();
				}
			}
		});

		JMenuItem menuEditFind= new JMenuItem("Find",new ImageIcon("images/find.png"));
		menuEditFind.setMnemonic('F');
		menuEditFind.setAccelerator(KeyStroke.getKeyStroke("control F"));

		JMenuItem menuEditUndo= new JMenuItem("Undo",new ImageIcon("images/undo.png"));
		menuEditUndo.setMnemonic('U');
		menuEditUndo.setAccelerator(KeyStroke.getKeyStroke("control Z"));

		JMenuItem menuEditRedo= new JMenuItem("Redo",new ImageIcon("images/redo.png"));
		menuEditRedo.setMnemonic('F');
		menuEditRedo.setAccelerator(KeyStroke.getKeyStroke("control Y"));

		JMenuItem menuEditHistory= new JMenuItem("History",new ImageIcon("images/history.png"));
		menuEditHistory.setMnemonic('H');

		menuEdit.add(menuEditCopy);
		menuEdit.add(menuEditCopySpecial);
		menuEdit.add(menuEditCut);
		menuEdit.add(menuEditPaste);
		menuEdit.addSeparator();
		menuEdit.add(menuEditSelectAll);
		menuEdit.add(menuEditInvertSelection);
		menuEdit.add(menuEditLockSelection);
		menuEdit.addSeparator();
		menuEdit.add(menuEditFind);
		menuEdit.addSeparator();
		menuEdit.add(menuEditUndo);
		menuEdit.add(menuEditRedo);
		menuEdit.add(menuEditHistory);
		menuEdit.addSeparator();
		menuEdit.add(menuEditOptions);
		menuBar.add(menuEdit);

		/*
		 * menu objects
		 */
		JMenu menuObjects = new JMenu("Objects");
		menuObjects.setMnemonic(KeyEvent.VK_O);

		JMenuItem menuObjectsAdd = new JMenuItem("Add");
		menuObjectsAdd.setMnemonic('A');
		menuObjectsAdd.setAccelerator(KeyStroke.getKeyStroke("control alt A"));

		JMenuItem menuObjectsObjList = new JMenuItem("Objects Manager");
		menuObjectsObjList.setMnemonic('O');
		menuObjectsObjList.setAccelerator(KeyStroke.getKeyStroke("control shift J"));
		menuObjectsObjList.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ObjectsManagerWindow win = new ObjectsManagerWindow(log,conf,dock);
				win.pack();
				win.setVisible(true);
			}
		});

		menuObjects.add(menuObjectsAdd);
		menuObjects.addSeparator();
		menuObjects.add(menuObjectsObjList);
		menuBar.add(menuObjects);

		/*
		 * menu project
		 */
		JMenu menuProject = new JMenu("Project");
		menuProject.setMnemonic(KeyEvent.VK_P);
		JMenuItem menuProjectOptions= new JMenuItem("Options",new ImageIcon("images/options.png"));
		menuProjectOptions.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ProjectOptionsWindow win = new ProjectOptionsWindow();
				win.pack();
				win.setVisible(true);
			}
		});

		JMenu menuProjectDoc= new JMenu("Documentation");
		menuProject.setMnemonic(KeyEvent.VK_D);

		JMenuItem menuProjectDocEdit= new JMenuItem("Edit");
		menuProjectDocEdit.setMnemonic(KeyEvent.VK_E);
		menuProjectDoc.add(menuProjectDocEdit);

		JMenuItem menuProjectDocGen= new JMenuItem("Generate");
		menuProjectDocEdit.setMnemonic(KeyEvent.VK_G);
		menuProjectDoc.add(menuProjectDocGen);

		menuProject.add(menuProjectOptions);
		menuProject.addSeparator();
		menuProject.add(menuProjectDoc);

		menuBar.add(menuProject);

		/*
		 * menu run
		 */
		JMenu menuRun= new JMenu("Run");
		menuRun.setMnemonic(KeyEvent.VK_R);

		JMenuItem menuRunStart= new JMenuItem("Start",new ImageIcon("images/play.png"));
		//		JMenuItem menuRunPause= new JMenuItem("Start",new ImageIcon("images/pause.png"));
		JMenuItem menuRunStop= new JMenuItem("Stop",new ImageIcon("images/stop.png"));
		JMenuItem menuRunKill= new JMenuItem("Kill");

		menuRunStart.setMnemonic('S');
		menuRunStart.setAccelerator(KeyStroke.getKeyStroke("control F9"));
		menuRunStart.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dock.start();
			}
		});

		menuRunStop.setMnemonic('T');
		menuRunStop.setAccelerator(KeyStroke.getKeyStroke("control F10"));
		menuRunStop.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dock.stop();
			}
		});

		menuRunKill.setMnemonic('K');
		menuRunKill.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dock.kill();
			}
		});

		menuRun.add(menuRunStart);
		//		menuRun.add(menuRunPause);
		menuRun.add(menuRunStop);
		menuRun.addSeparator();
		menuRun.add(menuRunKill);
		menuRun.addSeparator();
		menuBar.add(menuRun);

		/*
		 * menu windwos
		 */
		JMenu menuWindow = new JMenu("Window");
		menuWindow.setMnemonic(KeyEvent.VK_W);

		JMenuItem menuWindowProperiesBox = new JMenuItem("Properties box");
		menuWindowProperiesBox.setMnemonic('P');
		menuWindowProperiesBox.setAccelerator(KeyStroke.getKeyStroke("control shift P"));

		JMenuItem menuWindowToolbars = new JMenuItem("Tool bars");
		menuWindowToolbars.setMnemonic('L');

		JMenuItem menuWindowToolBox = new JMenuItem("Tools box");
		menuWindowToolBox.setMnemonic('T');
		menuWindowToolBox.setAccelerator(KeyStroke.getKeyStroke("control shift T"));

		JMenuItem menuWindowCopyBox = new JMenuItem("Copy box");
		menuWindowCopyBox.setMnemonic('y');
		menuWindowCopyBox.setAccelerator(KeyStroke.getKeyStroke("control shift Y"));



		JMenuItem menuWindowGelLog = new JMenuItem("GEL Log");
		menuWindowGelLog.setMnemonic('L');
		menuWindowGelLog.setAccelerator(KeyStroke.getKeyStroke("control shift L"));

		JMenuItem menuWindowConsole = new JMenuItem("Console");
		menuWindowConsole.setMnemonic('N');
		menuWindowConsole.setAccelerator(KeyStroke.getKeyStroke("control shift N"));

		JMenuItem menuWindowStats = new JMenuItem("Statistics");
		menuWindowStats.setMnemonic('R');
		menuWindowStats.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				GelStatsPanel win = new GelStatsPanel();
				win.setVisible(true);
				frame.getContentPane().add(win,BorderLayout.LINE_END);

			}
		});

		//		JMenuItem menuEditCopySpecial = new JMenuItem("Copy special");
		//		JMenuItem menuEditCut= new JMenuItem("Cut");
		//		JMenuItem menuEditPaste= new JMenuItem("Paste");
		//		
		menuWindow.add(menuWindowProperiesBox);
		menuWindow.add(menuWindowToolBox);
		menuWindow.add(menuWindowCopyBox);
		menuWindow.addSeparator();
		menuWindow.add(menuWindowGelLog);
		menuWindow.add(menuWindowConsole);
		menuWindow.add(menuWindowStats);
		//		menuEdit.add(menuEditCopySpecial);
		//		menuEdit.add(menuEditCut);
		//		menuEdit.add(menuEditPaste);
		//		menuEdit.addSeparator();
		menuBar.add(menuWindow);

		/*
		 * menu help
		 */
		JMenu menuHelp = new JMenu("Help");
		menuHelp.setMnemonic(KeyEvent.VK_H);

		JMenuItem menuHelpAbout = new JMenuItem("About...");
		menuHelpAbout.setMnemonic('A');
		menuHelpAbout.setAccelerator(KeyStroke.getKeyStroke("F1"));
		menuHelpAbout.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				GelAboutWindow win = new GelAboutWindow();
				win.pack();
				win.setVisible(true);
			}
		});

		JMenuItem menuHelpOnlineguide= new JMenuItem("Online guide");
		JMenuItem menuHelpUpdate= new JMenuItem("Check update");

		menuHelp.add(menuHelpOnlineguide);
		menuHelp.add(menuHelpUpdate);
		menuHelp.addSeparator();
		menuHelp.add(menuHelpAbout);
		menuBar.add(menuHelp);
		//Listeners
		//		buttonBlue.addActionListener(new BlueActionListener());
		//		buttonGreen.addActionListener(new GreenActionListener());
		//		buttonRed.addActionListener(new RedActionListener());
		//		esci.addActionListener(new ExitActionListener());
		return menuBar;
	}

	/*
	 * azioni del menu principale
	 */
	private class GreenActionListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
		}
	}
	private class RedActionListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
		}
	}
	private class BlueActionListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
		}
	}

	private class ExitActionListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			System.exit(0);
		}
	}



	/*
	 * MAIN
	 */
	public static void main(String[] args) {

		log = new Logger(); //inizializzo il log
		log.setLogToStdOutLvl(log.DEBUG);

		//controlli preliminari
		//			Check chk = new Check(log);
		//			if(!chk.checkGexyMinimal()){System.exit(1);}

		//argomenti cli
		opts = new CliOpt("GEL",args);
		opts.addOption("configfile", "Set local config file", false, true);
		opts.addOption('v',"version", "Show information of this program",false, false);

		try {
			opts.parse();
		} catch (CLIOptionArgumentValueRequiredException | CLIOptionArgumentRequiredException e) {
			System.err.println(e.getMessage());
			opts.getHelp();
			System.exit(1);
		}


		//carico configurazione iniziale
		conf = new Config(log);
		if(opts.isPassed("configfile")){
			try {
				conf.readConfigFromXml(opts.getValue("configfile"),"general");
			} catch (ConfigFilePathException | ConfigFileContextNotFound
					| ConfigFileParameterTooManyContext e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}else{
			try {
				conf.readConfigFromXml("config.xml","general");
			} catch (ConfigFilePathException | ConfigFileContextNotFound
					| ConfigFileParameterTooManyContext e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}

		//MANCA: configurazione log da config.xml


		//carico il tema grafico
		try {
			conf.readConfigFromXml("theme.xml",conf.getValueByName("theme"));
		} catch (ConfigFilePathException | ConfigFileContextNotFound
				| ConfigFileParameterTooManyContext e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		frame = new JFrame("GEL");

		// Add a window listner for close button
		frame.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});

		dock = new Dock(log,conf,"test"); //inizializzo il dock

		GelLabel lbl = new GelLabel(log,conf,dock);
		lbl.setText("Livello: ");
		dock.addObject(lbl);

		Test test1 = new Test(log,conf,dock);
		test1.getPort(0).setLink(lbl.getPort(0));
		test1.setTitle("Test1");
		dock.addObject(test1);


		dock.generate(); //avvio il dock

		frame.getContentPane().add(dock, BorderLayout.CENTER);

		frame.setJMenuBar(createMenuMain()); //main menu
		frame.add(new ToolbarMain(dock), BorderLayout.NORTH); //mainToolBar

		frame.add(new GelFooter(),BorderLayout.SOUTH);

		/*
		 * pannelli 
		 */
		DockPanel propertyPanel = new DockPanel("Properties");
		final PropertyBox pb = new PropertyBox();
		propertyPanel.add(pb);
		frame.add(propertyPanel,BorderLayout.EAST);
		dock.addActionOnSelectObject(new DockAction(){
			@Override
			protected void run() {

				if(dock.getSelectedObjects().size()==1){
					pb.clear();
					java.util.List<Property> properties = dock.getSelectedObjects().get(dock.getSelectedObjects().size()-1).getProperties();
					for(int a=0;a<properties.size();a++){
						pb.addItem(properties.get(a).name, properties.get(a).value);
						pb.setObject(dock.getSelectedObjects().get(dock.getSelectedObjects().size()-1)); //passo l'oggetto in cui scrivere le modifiche alle priopeità
					}
				}
			}
		});
		dock.addActionOnUnselectAllObjects(new DockAction(){
			@Override
			protected void run() {
				pb.clear();
			}
		});


		frame.setExtendedState( JFrame.MAXIMIZED_BOTH);
		frame.pack();
		frame.setVisible(true);
	}

}


