package gui.mainFrame;

import java.util.Vector;

import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;

import data.exam.Exam;

class Model implements TableModel{
	private Vector<Exam> exams = new Vector<Exam>();
	private Vector<TableModelListener> listeners = new Vector<TableModelListener>();
	
	public void addExam( Exam exam ){
		int index = exams.size();
		exams.add( exam );
		TableModelEvent e = new TableModelEvent( this, index, index, 
				TableModelEvent.ALL_COLUMNS, TableModelEvent.INSERT );
		for( int i = 0, n = listeners.size(); i<n; i++ ){
			((TableModelListener)listeners.get( i )).tableChanged( e );
		}
	}
	
	
	public int getColumnCount() {
		return 4;
	}
	
	
	public int getRowCount() {
		return exams.size();
	}
	
	
	public String getColumnName(int column) {
		switch( column ){
			case 0: return "Semester";
			case 1: return "Name";
			case 2: return "Leistungspunkte";
			case 3: return "Note";
			default: return null;
		}
	}
	
	
	public Object getValueAt(int rowIndex, int columnIndex) {
		Exam exam = (Exam) exams.get( rowIndex );
		
		switch( columnIndex ){
			case 0: return exam.getSemester();
			case 1: return exam.getName();
			case 2: return exam.getLeistungpunkte();
			case 3: return exam.getNote(); 
			default: return null;
		}
	}
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Class getColumnClass(int columnIndex) {
		switch( columnIndex ){
			case 0: return Integer.class;
			case 1: return String.class;
			case 2: return Integer.class;
			case 3: return Double.class; 
			default: return null;
		}	
	}
	
	public void addTableModelListener(TableModelListener l) {
		listeners.add( l );
	}
	public void removeTableModelListener(TableModelListener l) {
		listeners.remove( l );
	}
	

	public boolean isCellEditable(int rowIndex, int columnIndex) {
		return false;
	}
	public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
		// nicht beachten
	}
}