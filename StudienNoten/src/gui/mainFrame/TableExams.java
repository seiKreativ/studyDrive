package gui.mainFrame;

import java.awt.BorderLayout;
import java.util.Vector;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import data.exam.Exam;
import data.exam.ExamContainer;

public class TableExams extends JPanel{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1536495359476264660L;

	public Model model;
	public JTable tblTaskList;
	private DefaultTableModel dm;

	public TableExams(ExamContainer container) {

		super();

		tblTaskList = new JTable();
		tblTaskList.setShowVerticalLines(true);
		tblTaskList.setCellSelectionEnabled(true);
		tblTaskList.setColumnSelectionAllowed(true);
		// tblTaskList.setBorder(new LineBorder(null));

		((JPanel) this).add(tblTaskList);

		// create object of table and table model
		dm = new DefaultTableModel(0, 0);
		String header[] = new String[] { "Semester", "Leistungspunkte", "Name", "Note" };
		dm.setColumnIdentifiers(header);
		tblTaskList.setModel(dm);

		for (int count = 1; count <= container.getSize(); count++) {
			Vector<String> data = new Vector<String>();
			Exam e = container.getExamByIndex(count - 1);
			data.add(Integer.toString(e.getSemester()));
			data.add(Integer.toString(e.getLeistungpunkte()));
			data.add(e.getName());
			data.add(Double.toString(e.getNote()));
			dm.addRow(data);
		}

		tblTaskList.getColumn("Semester").setPreferredWidth(20);
		tblTaskList.getColumn("Name").setPreferredWidth(200); 
		tblTaskList.getColumn("Leistungspunkte").setPreferredWidth(10);
		tblTaskList.getColumn("Note").setPreferredWidth(10);
		this.setLayout(new BorderLayout());
		this.add(new JScrollPane(tblTaskList));

	}


		/*
		 * Vector<String> columnNames = new Vector<String>(); Vector<Vector<String>>
		 * rowData = new Vector<Vector<String>>(); columnNames.addElement("Semester");
		 * columnNames.addElement("Name"); columnNames.addElement("Leistungspunkte");
		 * columnNames.addElement("Note");
		 * 
		 * for (int i = 0; i < container.getSize(); i++) { System.out.println("Hello");
		 * Exam e = container.getExamByIndex(i); Vector<String> temp = new
		 * Vector<String>(); temp.add(Integer.toString(e.getSemester()));
		 * temp.add(Integer.toString(e.getLeistungpunkte())); temp.add(e.getName());
		 * temp.add(Double.toString(e.getNote())); rowData.add(temp);
		 * System.out.println("geooo"); }
		 * 
		 * dataTable = new JTable(rowData, columnNames);
		 * 
		 * dataTable.getColumn("Semester").setPreferredWidth(20);
		 * dataTable.getColumn("Name").setPreferredWidth(200);
		 * dataTable.getColumn("Leistungspunkte").setPreferredWidth(10);
		 * dataTable.getColumn("Note").setPreferredWidth(10); this.setLayout(new
		 * BorderLayout()); this.add(new JScrollPane(dataTable));
		 */
	

	public DefaultTableModel getDefaultTableModel() {
		return dm;
	}
	 
	public JTable getTable() {
		return tblTaskList; 
	}
}