package gui.addFrame;

import data.exam.Exam;
import data.exam.ExamAlreadyExistsException;
import data.exam.ExamContainer;
import data.exam.IllegalInputException;
import store.StoreException;

import java.awt.Color;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

public class AddFrame extends JDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8023202796137170626L;
	private JPanel contentPane;
	private JTextField textField;
	private JTextField textField_1;
	private JComboBox<String> comboBoxSem, comboBoxNoten;
	private ExamContainer container;

	/*
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					AddFrame frame = new AddFrame(null, "adding");
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	*/
	 
	public AddFrame(JFrame owner, String title) {
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

		textField = new JTextField();
		textField.setBounds(108, 36, 50, 20);
		contentPane.add(textField);
		textField.setColumns(10);

		textField_1 = new JTextField();
		textField_1.setColumns(10);
		textField_1.setBounds(190, 36, 380, 20);
		contentPane.add(textField_1);

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

		JButton btnClose = new JButton("Close");
		btnClose.setBackground(Color.GRAY);
		btnClose.addActionListener(e -> {
				if (JOptionPane.showConfirmDialog(null, "Soll ohne die Werte zu speichern wirklich geschlossen werden?",
						"Warnung!", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
					this.dispose(); // yes option
				} else {
					
				}
			}
		);
		btnClose.setBounds(365, 94, 90, 23);
		contentPane.add(btnClose);

		try {
			container = ExamContainer.instance();
		} catch (StoreException e) {
			// Dieser Fehler kann an der Stelle nicht auftreten
		}
	}

	private void onAdd() {
		try {
			Exam exam = new Exam(Integer.parseInt((String) comboBoxSem.getSelectedItem()), textField_1.getText(), Integer.parseInt(textField.getText()), Double.parseDouble((String) comboBoxNoten.getSelectedItem()));
			container.linkExam(exam);
			//Aber wie man das Exam jetzt zu diser Vektorliste/Tabelle hinzufügt habe ich keine Ahnung
			dispose();
		} catch (StoreException | ExamAlreadyExistsException | IllegalInputException e) {
			JOptionPane.showMessageDialog(null, "Error: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
		}
	}
}
