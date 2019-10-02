package gui.mainFrame;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.RowSorter;
import javax.swing.SortOrder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

import data.exam.exam.Exam;
import data.exam.exam.ExamContainer;

public class TableExams extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1536495359476264660L;

	public JTable tblTaskList;
	private DefaultTableModel dm;
	private ExamContainer container;

	@SuppressWarnings("serial")
	public TableExams(ExamContainer container) {

		super();
		this.container = container;

		// create object of table and table model
		dm = new DefaultTableModel(0, 0) {
			@Override
			public boolean isCellEditable(int row, int column) {
				// all cells false
				return false;
			}
		};
		String header[] = new String[] { "Sem", "LPs", "Name", "Note" };
		dm.setColumnIdentifiers(header);
		
		// Table row should be red if exam not passed
		tblTaskList = new JTable(dm) {
			@Override
			public Dimension getPreferredScrollableViewportSize() {
				return new Dimension(350, 150);
			}

			@Override
			public Component prepareRenderer(TableCellRenderer renderer, int row, int col) {
				Component c = super.prepareRenderer(renderer, row, col);
				String status = (String) getValueAt(row, 3);
				if (Double.parseDouble(status) > 4.0) {
					c.setBackground(Color.RED);
					c.setForeground(Color.WHITE);
				} else {
					c.setBackground(super.getBackground());
					c.setForeground(super.getForeground());
				}
				return c;
			}
		};
		tblTaskList.setShowVerticalLines(true);
		tblTaskList.setCellSelectionEnabled(false);
		tblTaskList.setRowSelectionAllowed(true);
		

		// Table Cell Renderer 
		DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer(); 
		centerRenderer.setHorizontalAlignment(JLabel.CENTER);
		tblTaskList.getColumnModel().getColumn(0).setCellRenderer(centerRenderer);
		tblTaskList.getColumnModel().getColumn(3).setCellRenderer(centerRenderer);
		tblTaskList.getColumnModel().getColumn(1).setCellRenderer(centerRenderer);
		
		
		//Table Sorter
		TableRowSorter<TableModel> sorter = new TableRowSorter<TableModel>(tblTaskList.getModel());
		tblTaskList.setRowSorter(sorter);
		sorter.setSortsOnUpdates(true);

		List<RowSorter.SortKey> sortKeys = new ArrayList<>(25);
		sortKeys.add(new RowSorter.SortKey(0, SortOrder.ASCENDING));
		sortKeys.add(new RowSorter.SortKey(1, SortOrder.DESCENDING));
		sortKeys.add(new RowSorter.SortKey(2, SortOrder.ASCENDING));
		sorter.setSortKeys(sortKeys);

		load();

		tblTaskList.getColumn("Sem").setPreferredWidth(10);
		tblTaskList.getColumn("Name").setPreferredWidth(210);
		tblTaskList.getColumn("LPs").setPreferredWidth(10);
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
	public void load() {
		while (dm.getRowCount() > 0) {
			dm.removeRow(0);
		}
		for (int count = 1; count <= container.getSize(); count++) {
			Exam e = container.getExamByIndex(count - 1);
			if (e.getNote() > 4.0) {
				continue;
			} else {
				Vector<String> data = new Vector<String>();
				data.add(Integer.toString(e.getSemester()));
				data.add(Integer.toString(e.getLeistungpunkte()));
				data.add(e.getName());
				data.add(Double.toString(e.getNote()));
				dm.addRow(data);
			}
		}
		revalidate();
	}

	public DefaultTableModel getDefaultTableModel() {
		return dm;
	}

	public void loadAll() {
		while (dm.getRowCount() > 0) {
			dm.removeRow(0);
		}
		for (int count = 1; count <= container.getSize(); count++) {
			Vector<String> data = new Vector<String>();
			Exam e = container.getExamByIndex(count - 1);
			data.add(Integer.toString(e.getSemester()));
			data.add(Integer.toString(e.getLeistungpunkte()));
			data.add(e.getName());
			data.add(Double.toString(e.getNote()));
			dm.addRow(data);
		}
		for (int i = 0; i < dm.getRowCount(); i++) {
			String status = (String) dm.getValueAt(i, 3);
			if (Double.parseDouble(status) > 4.0) {
				setBackground(Color.RED);
				setForeground(Color.WHITE);
			} else {
				setBackground(tblTaskList.getBackground());
				setForeground(tblTaskList.getForeground());
			}
		}
		revalidate();
	}

	public JTable getTable() {
		return tblTaskList;
	}

}