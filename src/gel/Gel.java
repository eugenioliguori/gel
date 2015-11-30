package gel;

import gel.component.Dock;
import gel.dev.object.ViewerDataRow;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

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
		

		
		JFrame frame = new JFrame("GEL");
		
		// Add a window listner for close button
		frame.addWindowListener(new WindowAdapter() {

			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});
		
		Dock dock = new Dock(log,conf); //inizializzo il dock
		
		ViewerDataRow test = new ViewerDataRow(log,conf);
		test.setTitle("ViewerData");
		dock.addObject(test);
		
		dock.generate(); //avvio il dock
		
		frame.getContentPane().add(dock, BorderLayout.CENTER);
		frame.setExtendedState( JFrame.MAXIMIZED_BOTH);
		frame.pack();
		frame.setVisible(true);
	}

}


