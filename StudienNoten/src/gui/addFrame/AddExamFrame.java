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
import javax.swing.border.EmptyBorder;

import data.exam.IllegalInputException;
import data.exam.exam.Exam;
import data.exam.exam.ExamAlreadyExistsException;
import data.exam.exam.ExamContainer;
import data.exam.lecture.Lecture;
import data.exam.lecture.LectureContainer;
import gui.mainFrame.MainFrame;
import store.StoreException;

public class AddExamFrame extends JDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8023202796137170626L;
	private JPanel contentPane;
	private JComboBox<String> comboBoxSem, comboBoxNoten, comboBoxLectures;
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

	public AddExamFrame(MainFrame owner, String title) {
		super(owner, title, true);
		this.setBounds(300, 400, 572, 155);
		this.setUndecorated(false);
		contentPane = new JPanel();
		contentPane.setBackground(Color.WHITE);
		contentPane.setForeground(Color.LIGHT_GRAY);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		try {
			lectureContainer = LectureContainer.instance();
			examContainer = ExamContainer.instance();
		} catch (StoreException e) {
			// Dieser Fehler kann an der Stelle nicht auftreten
		}

		JLabel lblSemester = new JLabel("Semester:");
		lblSemester.setBounds(20, 11, 78, 14);
		contentPane.add(lblSemester);

		JLabel lblName = new JLabel("Name:");
		lblName.setBounds(93, 11, 403, 14);
		contentPane.add(lblName);

		JLabel lblNote = new JLabel("Note:");
		lblNote.setBounds(503, 11, 90, 14);
		contentPane.add(lblNote);

		comboBoxSem = new JComboBox<String>();
		comboBoxSem.setBackground(Color.LIGHT_GRAY);
		for (int i = 1; i <= 12; i++) {
			comboBoxSem.addItem(Integer.toString(i));
		}
		comboBoxSem.setSelectedItem(null);
		comboBoxSem.setBounds(20, 36, 50, 20);
		comboBoxSem.addActionListener((e) -> {
			@SuppressWarnings("unchecked")
			int semTemp = Integer.parseInt((String) ((JComboBox<String>) e.getSource()).getSelectedItem());
			int itemCount = comboBoxLectures.getItemCount();
			comboBoxLectures.setSelectedItem(null);
			for (int i = 0; i < itemCount; i++) {
				comboBoxLectures.removeItemAt(0);
			}
			for (int i = 0; i < lectureContainer.getSize(); i++) {
				Lecture l = lectureContainer.getLectureByIndex(i);
				if (l.getSemester() == semTemp) {
					boolean isExisting = false;
					for (int j = 0; j < examContainer.getSize(); j++) {
						Exam ex = examContainer.getExamByIndex(j);
						if (ex.getLecture().equals(l)) {
							isExisting = true;
						}
					}
					if (!isExisting) {
						comboBoxLectures.addItem(l.getName());
					}
				}
			}
		});
		contentPane.add(comboBoxSem);

		comboBoxLectures = new JComboBox<>();
		comboBoxLectures.setBounds(93, 36, 380, 20);
		contentPane.add(comboBoxLectures);

		comboBoxNoten = new JComboBox<String>();
		comboBoxNoten.setBackground(Color.LIGHT_GRAY);
		for (int i = 1; i <= 4; i++) {
			comboBoxNoten.addItem(Integer.toString(i) + ".0");
			comboBoxNoten.addItem(Integer.toString(i) + ".3");
			comboBoxNoten.addItem(Integer.toString(i) + ".7");

		}
		comboBoxNoten.addItem("5.0");
		comboBoxNoten.setSelectedItem("1.0");
		comboBoxNoten.setBounds(503, 36, 50, 20);
		contentPane.add(comboBoxNoten);

		JButton btnApply = new JButton("Apply");
		btnApply.setBackground(Color.GRAY);
		btnApply.addActionListener(e -> onAdd());

		btnApply.setBounds(363, 84, 90, 23);
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
		btnClose.setBounds(463, 84, 90, 23);
		contentPane.add(btnClose);

		ArrayList<Component> keyListenerComponents = new ArrayList<Component>();
		keyListenerComponents.add(btnApply);
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
							AddExamFrame.this.dispose(); // yes option
						} else {

						}
					}
				}
			});
		}
	}

	public void setData(int sem, String name, int lp, double note) {
		this.comboBoxNoten.setSelectedItem(Double.toString(note));
		this.comboBoxSem.setSelectedItem(Integer.toString(sem));
		this.comboBoxLectures.setSelectedItem(lectureContainer.getLectureByName(name, sem));
		this.comboBoxLectures.setEnabled(false);
		this.comboBoxSem.setEnabled(false);
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
			Exam exam = new Exam(lecture, Double.parseDouble((String) comboBoxNoten.getSelectedItem()));
			examContainer.linkExam(exam);
			dispose();
		} catch (NumberFormatException | StoreException | IllegalInputException | ExamAlreadyExistsException e) {
			JOptionPane.showMessageDialog(null, "Error: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
		}
	}
}
