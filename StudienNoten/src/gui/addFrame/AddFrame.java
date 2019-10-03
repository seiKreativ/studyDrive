package gui.addFrame;

import java.awt.Color;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import data.exam.IllegalInputException;
import data.exam.exam.Exam;
import data.exam.exam.ExamAlreadyExistsException;
import data.exam.exam.ExamContainer;
import data.exam.lecture.Lecture;
import data.exam.lecture.LectureAlreadyExistsException;
import data.exam.lecture.LectureContainer;
import gui.mainFrame.MainFrame;
import store.StoreException;

public class AddFrame extends JDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8023202796137170626L;
	private JPanel contentPane;
	private JTextField txtLeistungspunkte;
	private JTextField txtName;
	private JComboBox<String> comboBoxSem, comboBoxNoten;
	private ExamContainer examContainer = null;
	private LectureContainer lectureContainer;
	private JButton btnClose;

	/*
	 * 
	 * public static void main(String[] args) { EventQueue.invokeLater(new
	 * Runnable() { public void run() { try { AddFrame frame = new AddFrame(null,
	 * "adding"); frame.setVisible(true); } catch (Exception e) {
	 * e.printStackTrace(); } } }); }
	 * 
	 */

	public AddFrame(MainFrame owner, String title) {
		super(owner, title, true);
		this.setBounds(300, 400, 717, 128);
		this.setUndecorated(true);
		contentPane = new JPanel();
		contentPane.setBackground(Color.LIGHT_GRAY);
		contentPane.setForeground(Color.LIGHT_GRAY);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lblSemester = new JLabel("Semester:");
		lblSemester.setBounds(20, 11, 78, 14);
		contentPane.add(lblSemester);

		JLabel lblLeistungspunkte = new JLabel("LPs:");
		lblLeistungspunkte.setBounds(110, 11, 72, 14);
		contentPane.add(lblLeistungspunkte);

		JLabel lblName = new JLabel("Name:");
		lblName.setBounds(190, 11, 403, 14);
		contentPane.add(lblName);

		JLabel lblNote = new JLabel("Note:");
		lblNote.setBounds(600, 11, 90, 14);
		contentPane.add(lblNote);

		comboBoxSem = new JComboBox<String>();
		comboBoxSem.setBackground(Color.LIGHT_GRAY);
		for (int i = 1; i <= 12; i++) {
			comboBoxSem.addItem(Integer.toString(i));
		}
		comboBoxSem.setSelectedItem("1");
		comboBoxSem.setBounds(20, 36, 50, 20);
		contentPane.add(comboBoxSem);

		txtLeistungspunkte = new JTextField();
		txtLeistungspunkte.setBounds(108, 36, 50, 20);
		contentPane.add(txtLeistungspunkte);
		txtLeistungspunkte.setColumns(10);

		txtName = new JTextField();
		txtName.setColumns(10);
		txtName.setBounds(190, 36, 380, 20);
		contentPane.add(txtName);

		comboBoxNoten = new JComboBox<String>();
		comboBoxNoten.setBackground(Color.LIGHT_GRAY);
		for (int i = 1; i <= 4; i++) {
			comboBoxNoten.addItem(Integer.toString(i) + ".0");
			comboBoxNoten.addItem(Integer.toString(i) + ".3");
			comboBoxNoten.addItem(Integer.toString(i) + ".7");

		}
		comboBoxNoten.addItem("5.0");
		comboBoxNoten.setSelectedItem("1.0");
		comboBoxNoten.setBounds(600, 36, 50, 20);
		contentPane.add(comboBoxNoten);

		JButton btnApply = new JButton("Apply");
		btnApply.setBackground(Color.GRAY);
		btnApply.addActionListener(e -> onAdd());

		btnApply.setBounds(265, 94, 90, 23);
		contentPane.add(btnApply);

		btnClose = new JButton("Close");
		btnClose.setBackground(Color.GRAY);
		btnClose.addActionListener(e -> {
			if (JOptionPane.showConfirmDialog(null, "Soll ohne die Werte zu speichern wirklich geschlossen werden?",
					"Warnung!", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
				this.dispose(); // yes option
			} else {

			}
		});
		btnClose.setBounds(365, 94, 90, 23);
		contentPane.add(btnClose);

		try {
			lectureContainer = LectureContainer.instance();
			examContainer = ExamContainer.instance();
		} catch (StoreException e) {
			// Dieser Fehler kann an der Stelle nicht auftreten
		}
	}

	public void setData(int sem, String name, int lp, double note) {
		this.txtLeistungspunkte.setText(Integer.toString(lp));
		this.comboBoxNoten.setSelectedItem(Double.toString(note));
		this.comboBoxSem.setSelectedItem(Integer.toString(sem));
		this.txtName.setText(name);
	}

	public void setCancelButtonActivated(boolean stat) {
		btnClose.setEnabled(stat);
	}

	private void onAdd() {
		try {
			Lecture lecture = new Lecture(Integer.parseInt((String) comboBoxSem.getSelectedItem()),
					txtName.getText().replace("'", ""), Integer.parseInt(txtLeistungspunkte.getText()));
			Exam exam = new Exam(lecture, Double.parseDouble((String) comboBoxNoten.getSelectedItem()));
			lectureContainer.linkLecture(lecture);
			examContainer.linkExam(exam);
		} catch (NumberFormatException | StoreException | IllegalInputException | ExamAlreadyExistsException | LectureAlreadyExistsException e) {
			JOptionPane.showMessageDialog(null, "Error: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
		}
		dispose(); 
	}
}
