package gui.mainFrame.tables;

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

import data.exam.lecture.Lecture;
import data.exam.lecture.LectureContainer;
import data.exam.sheet.Sheet;
import data.exam.sheet.SheetContainer;

public class TableSheetsLecture extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1536495359476264660L;

	public JTable tblTaskList;
	private DefaultTableModel dm;
	private SheetContainer sheetContainer;
	private LectureContainer lectureContainer;

	@SuppressWarnings("serial")
	public TableSheetsLecture(SheetContainer sheetContainer, LectureContainer lectureContainer) {

		super();
		this.sheetContainer = sheetContainer;
		this.lectureContainer = lectureContainer;

		// create object of table and table model
		dm = new DefaultTableModel(0, 0) {
			@Override
			public boolean isCellEditable(int row, int column) {
				// all cells false
				return false;
			}
		};
		String header[] = new String[] { "Sem", "Vorlesung", "insg.", "pts.", "max", "%" };
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
				if (isRowSelected(row)) {
					c.setBackground(Color.lightGray);
				} else {
					c.setBackground(Color.white);
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
		tblTaskList.getColumnModel().getColumn(2).setCellRenderer(centerRenderer);
		tblTaskList.getColumnModel().getColumn(4).setCellRenderer(centerRenderer);
		tblTaskList.getColumnModel().getColumn(5).setCellRenderer(centerRenderer);

		// Table Sorter
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
		tblTaskList.getColumn("Vorlesung").setPreferredWidth(190);
		tblTaskList.getColumn("insg.").setPreferredWidth(10);
		tblTaskList.getColumn("pts.").setPreferredWidth(10);
		tblTaskList.getColumn("max").setPreferredWidth(10);
		tblTaskList.getColumn("%").setPreferredWidth(10);
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
		if (lectureContainer != null) {
			while (dm.getRowCount() > 0) {
				dm.removeRow(0);
			}
		for (int i = 0; i < lectureContainer.getSize(); i++) {
			int anzahl = 0; 
			double points = 0; 
			double maxPoints = 0; 
			double prozent; 
			Lecture l = lectureContainer.getLectureByIndex(i);
			ArrayList<Sheet> tempSheets = sheetContainer.getSheetByName(l.getName(), l.getSemester());
			if (tempSheets.isEmpty()) {
				continue; 
			}
			for (int j = 0; j < tempSheets.size(); j++) {
				Sheet s = tempSheets.get(j); 
				anzahl += 1; 
				points += s.getPoints(); 
				maxPoints += s.getMaxPoints(); 
			}
			prozent = points / maxPoints; 
			Vector<String> data = new Vector<String>(); 
			data.add(Integer.toString(l.getSemester()));
			data.add(l.getName());
			data.add(Integer.toString(anzahl));
			data.add(Double.toString(points));
			data.add(Double.toString(maxPoints));
			data.add(Integer.toString((int) Math.round(prozent * 100)) + "%");
			dm.addRow(data);
		}
		}
		revalidate(); 
	}

	public DefaultTableModel getDefaultTableModel() {
		return dm;
	}

	public void loadAll() {
		if (lectureContainer != null) {
			while (dm.getRowCount() > 0) {
				dm.removeRow(0);
			}
		for (int i = 0; i < lectureContainer.getSize(); i++) {
			int anzahl = 0; 
			double points = 0; 
			double maxPoints = 0; 
			double prozent; 
			Lecture l = lectureContainer.getLectureByIndex(i);
			ArrayList<Sheet> tempSheets = sheetContainer.getSheetByName(l.getName(), l.getSemester());
			if (tempSheets.isEmpty()) {
				continue; 
			}
			for (int j = 0; j < tempSheets.size(); j++) {
				Sheet s = tempSheets.get(j); 
				anzahl += 1; 
				points += s.getPoints(); 
				maxPoints += s.getMaxPoints(); 
			}
			prozent = points / maxPoints; 
			Vector<String> data = new Vector<String>();
			data.add(Integer.toString(l.getSemester()));
			data.add(l.getName());
			data.add(Integer.toString(anzahl));
			data.add(Double.toString(points));
			data.add(Double.toString(maxPoints));
			data.add(Integer.toString((int) Math.round(prozent * 100)) + "%");
			dm.addRow(data);
		}
		}
		revalidate(); 
	}

	public JTable getTable() {
		return tblTaskList;
	}

}