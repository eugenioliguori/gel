package gel.dev.object;

import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.swing.JFrame;

import zjlib.logger.Logger;
import zjlib.config.Config;
import gel.component.Dock;
import gel.component.Object;
import gel.component.Port;


/**
 * Implementa un visualizzatore testuale di dati
 * @author Eugenio
 *
 */
public class FileReader extends Object{
	JFrame SetupWin; //finestra mostra testo
	File file;
	BufferedReader br;
	InputStreamReader ipsr;
	String data;
	
	public FileReader(Logger _log, Config _conf,Dock _dock){
		super(_log,_conf,_dock);

		/*
		 * inizializzo le porte standard
		 */
		Port pOut = new Port(_log,_conf,this.dock,"OUT"); //porta dati out
		pOut.setGuiPosition(Port.RIGHT);
		this.addPort(pOut);

		br = null;

	}

	public void setFile(String _file){
		file =  new File(_file);
		log.info("Set file to open: "+_file);
	}
	
	protected void openFile(){
		log.info("Opening file: "+file);
		try{
            InputStream ips=new FileInputStream(file); 
            ipsr=new InputStreamReader(ips);
            br=new BufferedReader(ipsr);
        }       
        catch (Exception e){
            System.out.println(e.toString());
        }
	}
	
	protected void readFile(){
		log.info("Reading file: "+file);
		String line;
		try {
			while ((line=br.readLine())!=null){
			    getPort(0).write(line+"\n");
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	protected void closeFile(){
		log.info("Close file: "+file);
		try {
			br.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	public int core(){
		openFile();
		readFile();
		closeFile();
		return 0;
	}
	
}
