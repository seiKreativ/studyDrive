package gui.mainFrame;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.text.DecimalFormat;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
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
import gui.addFrame.AddFrameExam;
import gui.registration.SignUpDialog;
import store.StoreException;

public class MainFrame extends JFrame {

	private static final long serialVersionUID = -5724418947028211664L;
	private JPanel examsPane;
	private JTextField durchschnittsnote;
	private JTextField insgÜbungsblätter;
	private JTextField insgVorlesungen;
	private ExamContainer examContainer;
	private LectureContainer lectureContainer;
	private SheetContainer sheetContainer;
	private JButton btnAddExam, btnDelExam, btnModExam;
	private TableExams allExams;
	private TableSheets tableSheets;
	private TableLectures tableLectures;
	private double durchschnitt;
	
	/**
	 * Create the frame.
	 */
	public MainFrame() {
		try {
			UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
				| UnsupportedLookAndFeelException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		setTitle("Studium Noten Manager");
		setResizable(false);
		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		Border emptyBorder = javax.swing.BorderFactory.createEmptyBorder();
		setBounds(100, 100, 800, 464);

		//Menu

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

		//Datan laden

		try {
			lectureContainer = LectureContainer.instance();
			examContainer = ExamContainer.instance();
			sheetContainer = SheetContainer.instance();
		} catch (StoreException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
        setContentPane(tabbedPane);

        // Exam-Panel

		examsPane = new JPanel();
		examsPane.setBackground(Color.WHITE);
		examsPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		examsPane.setLayout(null);

		JPanel grade = new JPanel();
		grade.setLayout(new BorderLayout(0, 0));
		grade.setBorder(new EmptyBorder(5, 5, 5, 5));
		grade.setBounds(0, 35, 349, 175);
		grade.setBackground(Color.WHITE);
		examsPane.add(grade);

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

		JPanel examsOptions = new JPanel();
		examsOptions.setBackground(Color.WHITE);
		examsOptions.setBounds(10, 210, 339, 165);
		examsPane.add(examsOptions);
		examsOptions.setLayout(null);

		btnAddExam = new JButton("Prüfung hinzufügen ");
		btnAddExam.setBackground(Color.GRAY);
		btnAddExam.setBounds(10, 5, 319, 41);
		examsOptions.add(btnAddExam);
		btnAddExam.addActionListener(e -> onAddExam());

		btnDelExam = new JButton("Prüfung löschen ");
		btnDelExam.setBackground(Color.GRAY);
		btnDelExam.setBounds(10, 57, 319, 41);
		examsOptions.add(btnDelExam);
		btnDelExam.addActionListener(e -> onDelExam());

		btnModExam = new JButton("Prüfung verändern");
		btnModExam.setBackground(Color.GRAY);
		btnModExam.setBounds(10, 109, 319, 41);
		examsOptions.add(btnModExam);
		btnModExam.addActionListener(e -> onModifyExam());

		allExams = new TableExams(examContainer);
		allExams.setBackground(Color.WHITE);
		allExams.setForeground(Color.WHITE);
		allExams.setBounds(359, 34, 427, 312);
		examsPane.add((JPanel) allExams);

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
		examsPane.add(chckbxNichtBestandenePrfungen);
		calcDurchschnittExam();
		tabbedPane.addTab("Exams", examsPane);

		//Lecture-Panel

		JPanel lecturePanel = new JPanel();
		lecturePanel.setLayout(null);
		lecturePanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		lecturePanel.setBackground(Color.WHITE);
		tabbedPane.addTab("Lectures", lecturePanel);

		JPanel countLecture = new JPanel();
		countLecture.setLayout(new BorderLayout(0, 0));
		countLecture.setBorder(new EmptyBorder(5, 5, 5, 5));
		countLecture.setBounds(0, 35, 349, 175);
		countLecture.setBackground(Color.WHITE);

		insgVorlesungen = new JTextField();
		insgVorlesungen.setBackground(Color.WHITE);
		insgVorlesungen.setFont(new Font("Tahoma", Font.PLAIN, 48));
		insgVorlesungen.setHorizontalAlignment(SwingConstants.CENTER);
		insgVorlesungen.setText(Integer.toString(lectureContainer.getAllLectures().size()));
		insgVorlesungen.setBorder(emptyBorder);
		insgVorlesungen.setEditable(false);
		insgVorlesungen.setOpaque(true);
		countLecture.add(insgVorlesungen, BorderLayout.CENTER);
		insgVorlesungen.setColumns(100);

		JLabel lblInsgVorlesungen = new JLabel("Vorlesungen insgesamt");
		lblInsgVorlesungen.setBackground(Color.WHITE);
		lblInsgVorlesungen.setOpaque(true);
		countLecture.add(lblInsgVorlesungen, BorderLayout.NORTH);
		lblInsgVorlesungen.setHorizontalAlignment(SwingConstants.CENTER);
		lblInsgVorlesungen.setFont(new Font("Tahoma", Font.PLAIN, 19));

		lecturePanel.add(countLecture);

		JPanel lectureOptions = new JPanel();
		lectureOptions.setLayout(null);
		lectureOptions.setBackground(Color.WHITE);
		lectureOptions.setBounds(10, 210, 339, 165);
		lecturePanel.add(lectureOptions);

		JButton btnAddLecture = new JButton("Vorlesung hinzufügen ");
		btnAddLecture.setBackground(Color.GRAY);
		btnAddLecture.setBounds(10, 5, 319, 41);
		btnAddLecture.addActionListener(e -> onAddLecture());
		lectureOptions.add(btnAddLecture);

		JButton btnDelLecture = new JButton("Vorlesung löschen ");
		btnDelLecture.setBackground(Color.GRAY);
		btnDelLecture.setBounds(10, 57, 319, 41);
		btnDelLecture.addActionListener(e -> onDelLecture());
		lectureOptions.add(btnDelLecture);

		JButton btnModLecture = new JButton("Vorlesung verändern");
		btnModLecture.setBackground(Color.GRAY);
		btnModLecture.setBounds(10, 109, 319, 41);
		btnModLecture.addActionListener(e -> onModifyLecture());
		lectureOptions.add(btnModLecture);

		tableLectures = new TableLectures(lectureContainer);
		tableLectures.setBounds(359, 34, 427, 341);
		lecturePanel.add(tableLectures);

		//Sheets-Panel

		JPanel sheetsPanel = new JPanel();
		sheetsPanel.setLayout(null);
		sheetsPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		sheetsPanel.setBackground(Color.WHITE);
		tabbedPane.addTab("Sheets", sheetsPanel);
		
		JPanel countSheet = new JPanel();
		countSheet.setLayout(new BorderLayout(0, 0));
		countSheet.setBorder(new EmptyBorder(5, 5, 5, 5));
		countSheet.setBounds(0, 35, 349, 175);
		countSheet.setBackground(Color.WHITE);
		
		insgÜbungsblätter = new JTextField();
		insgÜbungsblätter.setBackground(Color.WHITE);
		insgÜbungsblätter.setFont(new Font("Tahoma", Font.PLAIN, 48));
		insgÜbungsblätter.setHorizontalAlignment(SwingConstants.CENTER);
		insgÜbungsblätter.setText(Integer.toString(sheetContainer.getAllExams().size()));
		insgÜbungsblätter.setBorder(emptyBorder);
		insgÜbungsblätter.setEditable(false);
		insgÜbungsblätter.setOpaque(true);
		countSheet.add(insgÜbungsblätter, BorderLayout.CENTER);
		insgÜbungsblätter.setColumns(100);
		
		JLabel lblInsgÜbungsblätter = new JLabel("Übungsblätter insgesamt");
		lblInsgÜbungsblätter.setBackground(Color.WHITE);
		lblInsgÜbungsblätter.setOpaque(true);
		countSheet.add(lblInsgÜbungsblätter, BorderLayout.NORTH);
		lblInsgÜbungsblätter.setHorizontalAlignment(SwingConstants.CENTER);
		lblInsgÜbungsblätter.setFont(new Font("Tahoma", Font.PLAIN, 19));
		
		sheetsPanel.add(countSheet);
		
		JPanel sheetOptions = new JPanel();
		sheetOptions.setLayout(null);
		sheetOptions.setBackground(Color.WHITE);
		sheetOptions.setBounds(10, 210, 339, 165);
		sheetsPanel.add(sheetOptions);
		
		JButton btnAddSheet = new JButton("Übungsblatt hinzufügen ");
		btnAddSheet.setBackground(Color.GRAY);
		btnAddSheet.setBounds(10, 5, 319, 41);
		btnAddSheet.addActionListener(e -> onAddSheet());
		sheetOptions.add(btnAddSheet);
		
		JButton btnDelSheet = new JButton("Übungsblatt löschen ");
		btnDelSheet.setBackground(Color.GRAY);
		btnDelSheet.setBounds(10, 57, 319, 41);
		btnDelSheet.addActionListener(e -> onDelSheet());
		sheetOptions.add(btnDelSheet);
		
		JButton btnModSheet = new JButton("Übungsblatt verändern");
		btnModSheet.setBackground(Color.GRAY);
		btnModSheet.setBounds(10, 109, 319, 41);
		btnModSheet.addActionListener(e -> onModifySheet());
		sheetOptions.add(btnModSheet);
		
		tableSheets = new TableSheets(sheetContainer);
		tableSheets.setBounds(359, 34, 427, 341);
		sheetsPanel.add(tableSheets);

		//Window Closing

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
		setVisible(true);
	}

	//calc funktion exam

	private void calcDurchschnittExam() {
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

	//add-Methoden exam, lecture, sheet

	private void onAddExam() {
		// Prüfung hinzufügen
		AddFrameExam addDia = new AddFrameExam(this, "Neue Prüfung hinzufügen");
		addDia.setVisible(true);
		calcDurchschnittExam();
		allExams.load();
	}

	private void onAddLecture() {
	}

	private void onAddSheet() {

	}

	//delete-Methoden exam, lecture, sheet

	private void onDelExam() {
		// Prüfung löschen
		if (allExams.getTable().getSelectedRow() == -1) {
			JOptionPane.showMessageDialog(this,
					"Um eine Prüfung zu löschen muss zuerst eine Prüfung ausgewäht werden.");
		} else {
			JTable tb = allExams.getTable();
			int row = allExams.getTable().getSelectedRow();
			try {
				Lecture l = new Lecture(Integer.parseInt((String) tb.getValueAt(row, 0)), (String) tb.getValueAt(row, 2),
						Integer.parseInt((String) tb.getValueAt(row, 1)));
				Exam e = new Exam(l, Double.parseDouble((String) tb.getValueAt(row, 3)));
				examContainer.unlinkExam(e);
				lectureContainer.unlinkLecture(l);
				allExams.load();
				calcDurchschnittExam();
			} catch (IllegalInputException | StoreException | ExamNotFoundException | LectureNotFoundException e) {
				JOptionPane.showMessageDialog(this, "Error: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
			}
		}
	}

	private void onDelLecture() {
		if (tableLectures.getTable().getSelectedRow() == -1) {
			JOptionPane.showMessageDialog(this,
					"Um eine Vorlesung zu löschen muss zuerst eine Vorlesung ausgewäht werden.");
		} else {
			JTable tb = tableLectures.getTable();
			int row = tableLectures.getTable().getSelectedRow();
			try {
				Lecture l = new Lecture(Integer.parseInt((String) tb.getValueAt(row, 0)), (String) tb.getValueAt(row, 1),
						Integer.parseInt((String) tb.getValueAt(row, 2)));
				lectureContainer.unlinkLecture(l);
				tableLectures.load();
				insgVorlesungen.setText(Integer.toString(lectureContainer.getAllLectures().size()));
			} catch (IllegalInputException | StoreException | LectureNotFoundException e) {
				JOptionPane.showMessageDialog(this, "Error: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
			}
		}
	}

	private void onDelSheet() {
		if (tableSheets.getTable().getSelectedRow() == -1) {
			JOptionPane.showMessageDialog(this,
					"Um ein Übungsblatt zu löschen muss zuerst ein Übungsblatt ausgewäht werden.");
		} else {
			JTable tb = tableSheets.getTable();
			int row = tableSheets.getTable().getSelectedRow();
			try {
				//neues Sheet
				tableSheets.load();
				insgÜbungsblätter.setText(Integer.toString(sheetContainer.getAllExams().size()));
			} catch (Exception e) {
				JOptionPane.showMessageDialog(this, "Error: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
			}
		}
	}

	//modify-Mathoden exam, lecture, sheet

	private void onModifyExam() {
		// Prüfung verändern
		if (allExams.getTable().getSelectedRow() == -1) {
			JOptionPane.showMessageDialog(this,
					"Um eine Prüfung zu löschen muss zuerst eine Prüfung ausgewäht werden.");
		} else {
			JTable tb = allExams.getTable();
			int row = allExams.getTable().getSelectedRow();
			try {
				int tempSem = Integer.parseInt((String) tb.getValueAt(row, 0));
				String tempName = (String) tb.getValueAt(row, 2);
				int tempLp = Integer.parseInt((String) tb.getValueAt(row, 1));
				double tempNote = Double.parseDouble((String) tb.getValueAt(row, 3));
				Lecture l = new Lecture(tempSem, tempName, tempLp);
				Exam e = new Exam(l, tempNote);
				lectureContainer.unlinkLecture(l);
				examContainer.unlinkExam(e);
				AddFrameExam addDia = new AddFrameExam(this, "Prüfung ändern");
				addDia.setData(tempSem, tempName, tempLp, tempNote);
				addDia.setCancelButtonActivated(false);
				addDia.setVisible(true);
				calcDurchschnittExam();
				allExams.load();
			} catch (IllegalInputException | ExamNotFoundException | StoreException | LectureNotFoundException e) {
				JOptionPane.showMessageDialog(this, "Error: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
			}
		}
	}

	private void onModifyLecture() {
	}

	private void onModifySheet() {
	}

	// Menü-Funktionen

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

	//nie benutzt

	public JTable getTable() {
		return this.allExams.getTable();
	}

	public TableExams getTableExams() {
		return this.allExams;
	}
}
