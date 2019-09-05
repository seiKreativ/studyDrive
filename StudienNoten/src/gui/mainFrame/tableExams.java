package gui.mainFrame;

import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import data.exam.Exam;

public class tableExams extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1536495359476264660L;
	
	public Model model;
	public JTable table; 

	public tableExams() {
 
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
	
	public void add() {
		try {
			model.addExam(new Exam(4, "Medizin", 4, 1.0));
		} catch (IllegalInputException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
