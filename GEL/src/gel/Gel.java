package gel;

import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.*;

import zjlib.Config;
import zjlib.Logger;
import zjlib.cli.CliOpt;
import zjlib.exceptions.CLIOptionArgumentRequiredException;
import zjlib.exceptions.CLIOptionArgumentValueRequiredException;

public class Gel {
	static CliOpt opts; //cli option parser
	static Logger log; //logger
	static Config conf; //configuratore

	static String dflConfFile; //file di configurazione locale di default, se non specificato con -c
	

	public static void main(String[] args) {
		
		log = new Logger(); //inizializzo il log
		log.setLogToStdOutLvl(0);

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
			conf.readConfigFromXml(opts.getValue("configfile"),"general");
		}else{
			conf.readConfigFromXml("config.xml","general");
		}
		
		//MANCA: configurazione log da config.xml
		
		
		//carico il tema grafico
		conf.readConfigFromXml("theme.xml",conf.getValueByName("theme"));
		

		
		JFrame frame = new JFrame("GEL");
		
		// Add a window listner for close button
		frame.addWindowListener(new WindowAdapter() {

			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});
		
		Dock dock = new Dock(log,conf); //inizializzo il dock
		
//		Object test = new Object(log,conf);
//		dock.addObject(test);
		
		dock.generate(); //avvio il dock
		
		frame.getContentPane().add(dock, BorderLayout.CENTER);
		frame.setExtendedState( JFrame.MAXIMIZED_BOTH);
		frame.pack();
		frame.setVisible(true);
	}

}


