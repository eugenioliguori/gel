package gel.component;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.util.ArrayList;
import java.util.List;

import javax.swing.Action;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.event.CellEditorListener;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;

public class PropertyBox extends JPanel {
	private JTable table;
	
	public PropertyBox(){
		setLayout(new GridBagLayout());
		
		/*
		 * griglia
		 */
		String[] columnNames = {"Name",
                "Data"};
//		java.lang.Object[][] data = {{"name","string"}};
		
		table = new JTable(null, columnNames);
		table.setFillsViewportHeight(true);
//		table.setModel(new DefaultTableModel());
		table.setModel(new DefaultTableModel(null,columnNames));
		
		setLayout(new BorderLayout());
//		add(table.getTableHeader(), BorderLayout.PAGE_START);
		add(table, BorderLayout.CENTER);
		
		/*
		 * setto le modifiche hai valori quando vegono modificati
		 * leggendo il nome delle proprietà e usando il mentodo
		 * seProperty() dell'oggetto di cui visualizzo le 
		 * proprietà
		 */
		table.getDefaultEditor(String.class).addCellEditorListener(
                new CellEditorListener() {
                    public void editingCanceled(ChangeEvent e) {
                        System.out.println("editingCanceled");
                    }

                    public void editingStopped(ChangeEvent e) {
                         // Fill table with stuff.
                         for( int row = 0; row < table.getRowCount(); ++row )
                         {
                           for( int col = 0; col < table.getColumnCount(); ++col )
                           {
                        	   obj.setProperty(table.getValueAt( row, 0 ).toString(), table.getValueAt( row, 1 ).toString());
//                             System.out.print( "  " + row + "," + col + " = " + table.getValueAt( row, col ) );
                           }
                         }
                    }
                });

	}

	
	/**
	 * Aggiunge una riga al box per un campo testo
	 * @param _title	String
	 * @param _obj		JTextField
	 */
	public void addItem(String _title, String _data){
		
		DefaultTableModel model = (DefaultTableModel) table.getModel();
		
		model.addRow(new java.lang.Object[]{_title, _data});
//		JPanel property = new JPanel();
////		property.setBorder(new EmptyBorder(0,0,0,0));
//		property.setBorder(new LineBorder(Color.blue,1));
//		properties.add(property);
//		
//		GridBagConstraints gbc = makeGbc(0, properties.size()-1);
//		
//		JLabel lbl = new JLabel(_title);
////		lbl.setBorder(new EmptyBorder(0,0,0,0));
//		lbl.setBorder(new LineBorder(Color.black,1));
//		Font font = new Font( "Arial", Font.PLAIN,12);
//		lbl.setFont(font);
//		add(lbl,gbc);
//		
//		
//		property.setBorder(new EmptyBorder(0,0,0,0));
////		_obj.setBorder(new EmptyBorder(0,0,0,0));
//		_obj.setPreferredSize(new Dimension(100,15));
//		_obj.setBorder(new LineBorder(Color.yellow,1));
//		property.add(_obj);
//		
//		
//		gbc = makeGbc(1, properties.size()-1);
//		add(properties.get(properties.size()-1),gbc);
	}
	
	/**
	 * Rimuove tutte le proprietà nella finestra
	 */
	public void clear(){
		DefaultTableModel dm = (DefaultTableModel)table.getModel();
		dm.getDataVector().removeAllElements();
		dm.fireTableDataChanged(); // notifies the JTable that the model has changed
	}
	
	private Object obj; 
	public void setObject(Object _obj){obj=_obj;}
}