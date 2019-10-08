package gui.addFrame;

import java.awt.Color;
import java.awt.Component;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

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
import data.exam.exam.ExamContainer;
import data.exam.lecture.Lecture;
import data.exam.lecture.LectureAlreadyExistsException;
import data.exam.lecture.LectureContainer;
import data.exam.sheet.Sheet;
import data.exam.sheet.SheetContainer;
import gui.mainFrame.MainFrame;
import store.StoreException;

public class AddLectureFrame extends JDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8023202796137170626L;
	private JPanel contentPane;
	private JComboBox<String> comboBoxSem, comboBoxLp;
	private LectureContainer lectureContainer;
	private JButton btnClose;
	private JTextField txtName;
	private Lecture zuModLecture;
	/*
	 * 
	 * public static void main(String[] args) { EventQueue.invokeLater(new
	 * Runnable() { public void run() { try { AddFrame frame = new AddFrame(null,
	 * "adding"); frame.setVisible(true); } catch (Exception e) {
	 * e.printStackTrace(); } } }); }
	 * 
	 */

	public AddLectureFrame(MainFrame owner, String title, Lecture zuModLecture) {
		super(owner, title, true);
		this.setBounds(300, 400, 572, 161);
		
		contentPane = new JPanel();
		contentPane.setBackground(Color.WHITE);
		contentPane.setForeground(Color.LIGHT_GRAY);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		try {
			lectureContainer = LectureContainer.instance();
			
		} catch (StoreException e) {
			// Dieser Fehler kann an der Stelle nicht auftreten
		}

		this.zuModLecture = zuModLecture;

		JLabel lblSemester = new JLabel("Semester:");
		lblSemester.setBounds(20, 11, 78, 14);
		contentPane.add(lblSemester);

		JLabel lblName = new JLabel("Name:");
		lblName.setBounds(93, 11, 403, 14);
		contentPane.add(lblName);

		JLabel lblLp = new JLabel("ECTS:");
		lblLp.setBounds(503, 11, 90, 14);
		contentPane.add(lblLp);

		comboBoxSem = new JComboBox<String>();
		comboBoxSem.setBackground(Color.LIGHT_GRAY);
		for (int i = 1; i <= 12; i++) {
			comboBoxSem.addItem(Integer.toString(i));
		}
		comboBoxSem.setSelectedItem(null);
		comboBoxSem.setBounds(20, 36, 50, 20);
		contentPane.add(comboBoxSem);

		comboBoxLp = new JComboBox<String>();
		comboBoxLp.setBackground(Color.LIGHT_GRAY);
		for (int i = 1; i <= 20; i++) {
			comboBoxLp.addItem(Integer.toString(i));
		}
		comboBoxLp.setSelectedItem(null);
		comboBoxLp.setBounds(503, 36, 50, 20);
		contentPane.add(comboBoxLp);

		JButton btnApply = new JButton("Apply");
		btnApply.setBackground(Color.GRAY);
		btnApply.addActionListener(e -> onAdd());

		btnApply.setBounds(363, 90, 90, 23);
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
		btnClose.setBounds(463, 90, 90, 23);
		contentPane.add(btnClose);

		txtName = new JTextField();
		txtName.setBounds(93, 36, 381, 20);
		contentPane.add(txtName);
		txtName.setColumns(10);

		ArrayList<Component> keyListenerComponents = new ArrayList<Component>();
		keyListenerComponents.add(btnApply);
		keyListenerComponents.add(txtName);

		for (Component c : keyListenerComponents) {
			c.addKeyListener(new KeyAdapter() {
				@Override
				public void keyPressed(KeyEvent e) {
					if (e.getKeyCode() == KeyEvent.VK_ENTER)
						onAdd();
					if (e.getKeyCode() == KeyEvent.VK_DELETE) {
						if (JOptionPane.showConfirmDialog(null,
								"Soll ohne die Werte zu speichern wirklich geschlossen werden?", "Warnung!",
								JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
							AddLectureFrame.this.dispose(); // yes option
						} else {

						}
					}
				}
			});
		}
	}

	public void setData(int sem, String name, int lp) {
		this.comboBoxLp.setSelectedItem(Integer.toString(lp));
		this.comboBoxSem.setSelectedItem(Integer.toString(sem));
		this.txtName.setText(name);
	}

	public void setCancelButtonActivated(boolean stat) {
		btnClose.setEnabled(stat);
	}

	private void onAdd() {
		int newLp = Integer.parseInt((String)comboBoxLp.getSelectedItem());
		try {
			if (txtName.getText().length() < 4) {
				throw new IllegalInputException("Name muss länger als 3 Buchstaben sein");
			} 
			Lecture lecture = new Lecture(Integer.parseInt((String)comboBoxSem.getSelectedItem()), txtName.getText(), newLp);

			if (zuModLecture == null) {
				if (lectureContainer.contains(lecture)) {
					throw new IllegalInputException("Lecture existiert bereits");
				}
				lectureContainer.linkLecture(lecture);
			} else {

				// Lecture auch in den anderen Containern verändern auf Datenbank

				ExamContainer examContainer = ExamContainer.instance();
				for (Exam e : examContainer) {
					if (e.getSemester() == zuModLecture.getSemester() && e.getName().equals(zuModLecture.getName())) {
						Exam eold = new Exam(e.getLecture(), e.getNote());
						e.setLecture(lecture);
						examContainer.modify(eold, e);
					}
				}
				SheetContainer sheetContainer = SheetContainer.instance();
				for (Sheet e : sheetContainer) {
					if (e.getSemester() == zuModLecture.getSemester() && e.getName().equals(zuModLecture.getName())) {
						Sheet eold = new Sheet(e.getLecture(), e.getNumber(), e.getPoints(), e.getMaxPoints(), e.getType());
						e.setLecture(lecture);
						sheetContainer.modify(eold, e);
						System.out.println("Zu ändern: " + eold.toString() + " zu: " + e.toString());
					}
				}

				lectureContainer.modify(zuModLecture, lecture);
				zuModLecture.setSemester(lecture.getSemester());
				zuModLecture.setLeistungsPunkte(lecture.getLeistungpunkte());
				zuModLecture.setName(lecture.getName());
			}

			dispose();
		} catch (NumberFormatException | StoreException | IllegalInputException | LectureAlreadyExistsException e) {
			JOptionPane.showMessageDialog(null, "Error: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
		}
	}
}
