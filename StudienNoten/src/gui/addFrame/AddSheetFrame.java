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
import data.exam.exam.ExamAlreadyExistsException;
import data.exam.lecture.Lecture;
import data.exam.lecture.LectureContainer;
import data.exam.sheet.Sheet;
import data.exam.sheet.SheetAlreadyExistsException;
import data.exam.sheet.SheetContainer;
import gui.mainFrame.MainFrame;
import store.StoreException;

public class AddSheetFrame extends JDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8023202796137170626L;
	private JPanel contentPane;
	private JComboBox<String> comboBoxSem, comboBoxNummer, comboBoxLectures;
	private LectureContainer lectureContainer = null;
	private SheetContainer sheetContainer;
	private JButton btnClose;
	private JTextField txtPoints;
	private JTextField txtPointsMax;

	/*
	 * 
	 * public static void main(String[] args) { EventQueue.invokeLater(new
	 * Runnable() { public void run() { try { AddFrame frame = new AddFrame(null,
	 * "adding"); frame.setVisible(true); } catch (Exception e) {
	 * e.printStackTrace(); } } }); }
	 * 
	 */

	public AddSheetFrame(MainFrame owner, String title) {
		super(owner, title, true);
		this.setBounds(300, 400, 527, 128);
		this.setUndecorated(true);
		try {
			lectureContainer = LectureContainer.instance();
			sheetContainer = SheetContainer.instance();
		} catch (StoreException e) {
			// Dieser Fehler kann an der Stelle nicht auftreten
		}
		contentPane = new JPanel();
		contentPane.setBackground(Color.LIGHT_GRAY);
		contentPane.setForeground(Color.LIGHT_GRAY);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lblSemester = new JLabel("Semester:");
		lblSemester.setBounds(20, 11, 78, 14);
		contentPane.add(lblSemester);

		JLabel lblName = new JLabel("Name:");
		lblName.setBounds(80, 11, 241, 14);
		contentPane.add(lblName);

		JLabel lblNummer = new JLabel("Nummer:");
		lblNummer.setBounds(313, 11, 50, 14);
		contentPane.add(lblNummer);

		comboBoxSem = new JComboBox<String>();
		comboBoxSem.setBackground(Color.LIGHT_GRAY);
		for (int i = 1; i <= 12; i++) {
			comboBoxSem.addItem(Integer.toString(i));
		}
		comboBoxSem.setSelectedItem("1");
		comboBoxSem.setBounds(20, 36, 50, 20);
		contentPane.add(comboBoxSem);

		comboBoxNummer = new JComboBox<String>();
		comboBoxNummer.setBackground(Color.LIGHT_GRAY);
		for (int i = 1; i <= 13; i++) {
			comboBoxNummer.addItem(Integer.toString(i));
		}
		comboBoxNummer.setSelectedItem("1");
		comboBoxNummer.setBounds(313, 36, 50, 20);
		contentPane.add(comboBoxNummer);

		JButton btnApply = new JButton("Apply");
		btnApply.setBackground(Color.GRAY);
		btnApply.addActionListener(e -> onAdd());

		btnApply.setBounds(173, 94, 90, 23);
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
		btnClose.setBounds(273, 94, 90, 23);
		contentPane.add(btnClose);

		comboBoxLectures = new JComboBox<>();
		comboBoxLectures.setBounds(80, 36, 223, 22);
		for (int i = 0; i < lectureContainer.getSize(); i++) {
			comboBoxLectures.addItem(lectureContainer.getLectureByIndex(i).getName());
		}
		contentPane.add(comboBoxLectures);

		JLabel lblPunkte = new JLabel("Punkte:");
		lblPunkte.setBounds(384, 11, 50, 14);
		contentPane.add(lblPunkte);

		JLabel lblNummer_1 = new JLabel("Punkte insg:");
		lblNummer_1.setBounds(444, 11, 73, 14);
		contentPane.add(lblNummer_1);

		txtPoints = new JTextField();
		txtPoints.setBounds(384, 36, 50, 20);
		contentPane.add(txtPoints);
		txtPoints.setColumns(10);

		txtPointsMax = new JTextField();
		txtPointsMax.setColumns(10);
		txtPointsMax.setBounds(444, 36, 50, 20);
		contentPane.add(txtPointsMax);

	}

	public void setData(int sem, String name, int num, double points, double maxPoints) {
		this.txtPoints.setText(Double.toString(points));
		this.txtPointsMax.setText(Double.toString(maxPoints));
		this.comboBoxNummer.setSelectedItem(Integer.toString(num));
		this.comboBoxSem.setSelectedItem(Integer.toString(sem));
		this.comboBoxLectures.setSelectedItem(name);
	}

	public void setCancelButtonActivated(boolean stat) {
		btnClose.setEnabled(stat);
	}

	private void onAdd() {
		try {
			Lecture lecture = null;
			for (int i = 0; i < lectureContainer.getSize(); i++) {
				Lecture e = lectureContainer.getLectureByIndex(i);
				if (e.getName().equals(comboBoxLectures.getSelectedItem())
						&& e.getSemester() == Integer.parseInt((String) comboBoxSem.getSelectedItem())) {
					lecture = e;
				}
			}
			if (lecture == null) {
				JOptionPane.showMessageDialog(this, "this lecture does not exist");
				this.dispose();
			}
			// Hier noch constraints einfügen sonst wirds unübersichtlich
			Sheet sheet = new Sheet(lecture, Integer.parseInt((String) comboBoxSem.getSelectedItem()),
					Double.parseDouble(txtPoints.getText()), Double.parseDouble(txtPointsMax.getText()));
			sheetContainer.linkSheet(sheet);
		} catch (NumberFormatException | StoreException | IllegalInputException | SheetAlreadyExistsException e) {
			JOptionPane.showMessageDialog(null, "Error: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
		}
		dispose();
	}
}
