package gui.mainFrame;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.text.DecimalFormat;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;

import data.exam.IllegalInputException;
import data.exam.exam.Exam;
import data.exam.exam.ExamContainer;
import data.exam.exam.ExamNotFoundException;
import data.exam.lecture.Lecture;
import data.exam.lecture.LectureContainer;
import data.exam.lecture.LectureNotFoundException;
import data.exam.sheet.SheetContainer;
import gui.addFrame.AddFrame;
import gui.registration.SignUpDialog;
import store.StoreException;
import javax.swing.JCheckBox;

public class MainFrame extends JFrame implements PropertyChangeListener {

	private static final long serialVersionUID = -5724418947028211664L;
	private JPanel contentPane;
	private JTextField durchschnittsnote;
	private ExamContainer examContainer;
	private LectureContainer lectureContainer;
	private SheetContainer sheetContainer;
	private JButton btnAdd, btnDel, btnMod;
	private TableExams allExams;
	private double durchschnitt;

	/**
	 * 
	 * public static void main(String[] args) { EventQueue.invokeLater(new
	 * Runnable() { public void run() { try { MainFrame frame = new MainFrame();
	 * frame.setVisible(true); } catch (Exception e) { e.printStackTrace(); } } });
	 * }
	 * 
	 * 
	 */
	public MainFrame() {
		setTitle("Studium Noten Manager");
		setResizable(false);
		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		Border emptyBorder = javax.swing.BorderFactory.createEmptyBorder();
		setBounds(100, 100, 800, 431);

		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);

		JMenu mnAllgemein = new JMenu("Allgemein");
		menuBar.add(mnAllgemein);

		JMenuItem mntmLogOut = new JMenuItem("Log out");
		mntmLogOut.addActionListener(e -> onLogOut());
		mnAllgemein.add(mntmLogOut);

		JMenuItem mntmDeleteUser = new JMenuItem("Account Löschen");
		mntmDeleteUser.addActionListener(e -> onDeleteUser());
		mnAllgemein.add(mntmDeleteUser);

		JMenuItem mntmInformation = new JMenuItem("Information");
		mntmInformation.addActionListener(e -> {
			JOptionPane.showMessageDialog(this, "Version 1.0");
		});
		mnAllgemein.add(mntmInformation);
		contentPane = new JPanel();
		contentPane.setBackground(Color.WHITE);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JPanel grade = new JPanel();
		grade.setLayout(new BorderLayout(0, 0));
		grade.setBorder(new EmptyBorder(5, 5, 5, 5));
		grade.setBounds(0, 35, 349, 175);
		grade.setBackground(Color.WHITE);
		contentPane.add(grade);

		durchschnittsnote = new JTextField();
		durchschnittsnote.setBackground(Color.WHITE);
		durchschnittsnote.setFont(new Font("Tahoma", Font.PLAIN, 48));
		durchschnittsnote.setHorizontalAlignment(SwingConstants.CENTER);
		durchschnittsnote.setText("1,0");
		durchschnittsnote.setBorder(emptyBorder);
		durchschnittsnote.setEditable(false);
		durchschnittsnote.setOpaque(true);
		grade.add(durchschnittsnote, BorderLayout.CENTER);
		durchschnittsnote.setColumns(10);

		JLabel lblDurchschnittsnote = new JLabel("Durchschnittsnote");
		lblDurchschnittsnote.setBackground(Color.WHITE);
		lblDurchschnittsnote.setOpaque(true);
		grade.add(lblDurchschnittsnote, BorderLayout.NORTH);
		lblDurchschnittsnote.setHorizontalAlignment(SwingConstants.CENTER);
		lblDurchschnittsnote.setFont(new Font("Tahoma", Font.PLAIN, 19));

		JPanel panel = new JPanel();
		panel.setBackground(Color.WHITE);
		panel.setBounds(10, 210, 339, 165);
		contentPane.add(panel);
		panel.setLayout(null);

		btnAdd = new JButton("Pr\u00FCfung hinzuf\u00FCgen ");
		btnAdd.setBackground(Color.GRAY);
		btnAdd.setBounds(10, 5, 319, 41);
		panel.add(btnAdd);
		btnAdd.addActionListener(e -> onAdd());

		btnDel = new JButton("Pr\u00FCfung l\u00F6schen ");
		btnDel.setBackground(Color.GRAY);
		btnDel.setBounds(10, 57, 319, 41);
		panel.add(btnDel);
		btnDel.addActionListener(e -> onDel());

		btnMod = new JButton("Pr\u00FCfung ver\u00E4ndern");
		btnMod.setBackground(Color.GRAY);
		btnMod.setBounds(10, 109, 319, 41);
		panel.add(btnMod);
		btnMod.addActionListener(e -> onModify());

		try {
			lectureContainer= LectureContainer.instance();
			examContainer = ExamContainer.instance();
			sheetContainer = SheetContainer.instance();
		} catch (StoreException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		allExams = new TableExams(examContainer);
		allExams.setBackground(Color.WHITE);
		allExams.setForeground(Color.WHITE);
		allExams.setBounds(359, 34, 427, 312);
		contentPane.add((JPanel) allExams);

		JCheckBox chckbxNichtBestandenePrfungen = new JCheckBox("Nicht bestandene Prüfungen anzeigen ");
		chckbxNichtBestandenePrfungen.setBackground(Color.WHITE);
		chckbxNichtBestandenePrfungen.setBounds(355, 348, 431, 23);
		chckbxNichtBestandenePrfungen.setOpaque(true);
		chckbxNichtBestandenePrfungen.addActionListener(e -> {
			if (((JCheckBox) e.getSource()).isSelected()) {
				allExams.loadAll();
			} else {
				allExams.load();
			}
		});
		contentPane.add(chckbxNichtBestandenePrfungen);

		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				try {
					lectureContainer.close();
				} catch (StoreException ex) {
					System.out.println("Database Error: closing failed " + ex.getMessage());
				}
				System.exit(0);
			}
		});
		calcDurchschnitt();
		setVisible(true);
	}

	private void calcDurchschnitt() {
		// TODO Auto-generated method stub
		int sumLp = 0;
		double sumNoten = 0;
		for (int i = 0; i < examContainer.getSize(); i++) {
			if (examContainer.getExamByIndex(i).getNote() <= 4.0) {
				sumLp += examContainer.getExamByIndex(i).getLeistungpunkte();
				sumNoten += examContainer.getExamByIndex(i).getNote() * examContainer.getExamByIndex(i).getLeistungpunkte();
			}
		}
		durchschnitt = sumNoten / (double) sumLp;
		durchschnittsnote.setText(new DecimalFormat("0.00").format(durchschnitt));
	}

	private void onModify() {
		// Prüfung verändern
		if (allExams.getTable().getSelectedRow() == -1) {
			JOptionPane.showMessageDialog(this,
					"Um eine Prüfung zu löschen muss zuerst eine Prüfung ausgewäht werden.");
		} else {
			JTable tb = allExams.getTable();
			int row = allExams.getTable().getSelectedRow();
			try {
				int tempSem = Integer.valueOf((String) tb.getValueAt(row, 0));
				String tempName = (String) tb.getValueAt(row, 2);
				int tempLp = Integer.valueOf((String) tb.getValueAt(row, 1));
				double tempNote = Double.valueOf((String) tb.getValueAt(row, 3));
				Lecture l = new Lecture(tempSem, tempName, tempLp);
				Exam e = new Exam(l, tempNote);
				examContainer.unlinkExam(e);
				lectureContainer.unlinkLecture(l);
				AddFrame addDia = new AddFrame(this, "Prüfung ändern");
				addDia.setData(tempSem, tempName, tempLp, tempNote);
				addDia.setCancelButtonActivated(false);
				addDia.setVisible(true);
				calcDurchschnitt();
				allExams.load();
			} catch (IllegalInputException | ExamNotFoundException | StoreException | LectureNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	private void onAdd() {
		// Prüfung hinzufügen
		AddFrame addDia = new AddFrame(this, "Neue Prüfung hinzufügen");
		addDia.setVisible(true);
		calcDurchschnitt();
		allExams.load();

	}

	private void onDel() {
		// Prüfung löschen
		if (allExams.getTable().getSelectedRow() == -1) {
			JOptionPane.showMessageDialog(this,
					"Um eine Prüfung zu löschen muss zuerst eine Prüfung ausgewäht werden.");
		} else {
			JTable tb = allExams.getTable();
			int row = allExams.getTable().getSelectedRow();
			try {
				Lecture l = new Lecture(Integer.valueOf((String) tb.getValueAt(row, 0)), (String) tb.getValueAt(row, 2),
						Integer.valueOf((String) tb.getValueAt(row, 1)));
				Exam e = new Exam(l, Double.valueOf((String) tb.getValueAt(row, 3)));
				examContainer.unlinkExam(e);
				lectureContainer.unlinkLecture(l);
				allExams.load();
				calcDurchschnitt();
			} catch (IllegalInputException | StoreException | ExamNotFoundException | LectureNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

	private void onLogOut() {
		try {
			lectureContainer.close();
			dispose();
			new SignUpDialog();
		} catch (StoreException e) {
			JOptionPane.showMessageDialog(this, "Error: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
		}
	}

	private void onDeleteUser() {
		try {
			String tmpPassword = JOptionPane.showInputDialog(this, "Zum Löschen Passwort für User " + lectureContainer.getUser() + " eingeben.", "Acoount Löschen", JOptionPane.INFORMATION_MESSAGE);
			if (tmpPassword.equals(lectureContainer.getPassword())) {
				lectureContainer.deleteUser();
				JOptionPane.showMessageDialog(this, "User " + lectureContainer.getUser() + " erfolgreich gelöscht", "Information", JOptionPane.INFORMATION_MESSAGE);
				onLogOut();
			}
			else {
				JOptionPane.showMessageDialog(this, "Falsches Passwort", "Error", JOptionPane.ERROR_MESSAGE);
				onDeleteUser();
			}
		} catch (StoreException e) {
			JOptionPane.showMessageDialog(this, "Error: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
		}
	}

	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		// TODO Auto-generated method stub

	}

	public JTable getTable() {
		return this.allExams.getTable();
	}

	public TableExams getTableExams() {
		return this.allExams;
	}
}
