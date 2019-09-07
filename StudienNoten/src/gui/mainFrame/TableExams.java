<<<<<<< HEAD
package gui.mainFrame;

import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import data.exam.Exam;

public class TableExams extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1536495359476264660L;
	
	public Model model;
	public JTable table; 

	public TableExams() {
 
		super();
		model = new Model();
		table = new JTable(model);
		table.setBackground(Color.WHITE);
		/*final JButton buttonVehicle = new JButton("add vehicle");
		buttonVehicle.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int size = model.getRowCount();
				Exam exam = createExam(size);
				model.addExam(exam);
			}
		});*/
		table.getColumn("Semester").setPreferredWidth(20);
		table.getColumn("Name").setPreferredWidth(200);
		table.getColumn("Leistungspunkte").setPreferredWidth(10);
		table.getColumn("Note").setPreferredWidth(10);
		this.setLayout(new BorderLayout());
		this.add(new JScrollPane(table));
	}
	
	public void addExam(Exam e) {
		model.addExam(e);
	}
	
	public void removeExam(Exam e) {
		model.removeExam(e);
	}
}
=======
package gui.mainFrame;

import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import data.exam.Exam;
import data.exam.IllegalInputException;

public class TableExams extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1536495359476264660L;
	
	public Model model;
	public JTable table; 

	public TableExams() {
 
		super();
		model = new Model();
		table = new JTable(model);
		table.setBackground(Color.WHITE);
		/*final JButton buttonVehicle = new JButton("add vehicle");
		buttonVehicle.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int size = model.getRowCount();
				Exam exam = createExam(size);
				model.addExam(exam);
			}
		});*/
		table.getColumn("Semester").setPreferredWidth(20);
		table.getColumn("Name").setPreferredWidth(200);
		table.getColumn("Leistungspunkte").setPreferredWidth(10);
		table.getColumn("Note").setPreferredWidth(10);
		this.setLayout(new BorderLayout());
		this.add(new JScrollPane(table));
	}
	
	public void add(Exam e) {
		model.addExam(e);
	}
}
>>>>>>> branch 'master' of https://github.com/seiKreativ/studyDrive.git
