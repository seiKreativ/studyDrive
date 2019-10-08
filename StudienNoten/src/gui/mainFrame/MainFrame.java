package gui.mainFrame;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.text.DecimalFormat;
import java.util.ArrayList;

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
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import data.exam.IllegalInputException;
import data.exam.exam.Exam;
import data.exam.exam.ExamContainer;
import data.exam.exam.ExamNotFoundException;
import data.exam.lecture.Lecture;
import data.exam.lecture.LectureContainer;
import data.exam.lecture.LectureNotFoundException;
import data.exam.sheet.Sheet;
import data.exam.sheet.SheetContainer;
import data.exam.sheet.SheetNotFoundException;
import gui.addFrame.AddExamFrame;
import gui.addFrame.AddLectureFrame;
import gui.addFrame.AddSheetFrame;
import gui.mainFrame.tables.TableExams;
import gui.mainFrame.tables.TableLectures;
import gui.mainFrame.tables.TableOtherSheets;
import gui.mainFrame.tables.TableSheets;
import gui.mainFrame.tables.TableSheetsLecture;
import gui.registration.PasswordDialog;
import gui.registration.SignUpDialog;
import store.StoreException;

public class MainFrame extends JFrame {

	private static final long serialVersionUID = -5724418947028211664L;
	private JPanel examsPane;
	private JTextField durchschnittsnote;
	private JTextField insgÜbungsblätter;
	private JTextField insgVorlesungen;
	private JTabbedPane sheetTabbedPane;
	private ExamContainer examContainer;
	private LectureContainer lectureContainer;
	private SheetContainer sheetContainer;
	private JButton btnAddExam, btnDelExam, btnModExam;
	private TableExams allExams;
	private TableSheets allSheets;
	private TableOtherSheets allOther;
	private TableLectures allLectures;
	private TableSheetsLecture allSheetLecture;
	private double durchschnitt;
	private JTextField textFieldEmail;

	/**
	 * Create the frame.
	 */
	public MainFrame() {
/*		try {
			BufferedImage  image = ImageIO.read(this.getClass().getResource("\\study-icon-19.png"));
			this.setIconImage(image);
			UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
				| UnsupportedLookAndFeelException | IOException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}*/
		setTitle("Studium Noten Manager");
		setResizable(false);
		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		Border emptyBorder = javax.swing.BorderFactory.createEmptyBorder();
		setBounds(100, 100, 800, 464);

		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);

		JMenu mnAllgemein = new JMenu("Allgemein");
		menuBar.add(mnAllgemein);

		JMenuItem mntmLogOut = new JMenuItem("Log out");
		mntmLogOut.addActionListener(e -> onLogOut());
		mnAllgemein.add(mntmLogOut);

		JMenuItem mntmRefresh = new JMenuItem("Refresh");
		mntmRefresh.addActionListener(e -> onRefresh());
		mnAllgemein.add(mntmRefresh);

		JMenuItem mntmInformation = new JMenuItem("Information");
		mntmInformation.addActionListener(e -> {
			JOptionPane.showMessageDialog(this, "Version 1.0");
		});
		mnAllgemein.add(mntmInformation);

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
		calcDurchschnitt();
		tabbedPane.addTab("Exams", examsPane);

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
		insgÜbungsblätter.setText(Integer.toString(sheetContainer.getCountSheetType()));
		insgÜbungsblätter.setBorder(emptyBorder);
		insgÜbungsblätter.setEditable(false);
		insgÜbungsblätter.setOpaque(true);
		countSheet.add(insgÜbungsblätter, BorderLayout.CENTER);
		insgÜbungsblätter.setColumns(100);

		JLabel lblInsgÜbungsblätter = new JLabel("Alle Le insgesamt");
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

		sheetTabbedPane = new JTabbedPane(JTabbedPane.TOP);
		sheetTabbedPane.setBounds(359, 11, 422, 364);
		sheetTabbedPane.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				if (((JTabbedPane) e.getSource())
						.getTitleAt(((JTabbedPane) e.getSource()).getSelectedIndex()) == "Other") {
					lblInsgÜbungsblätter.setText("Andere Leistungen insgesamt");
					insgÜbungsblätter.setText(Integer.toString(sheetContainer.getCountOtherType()));
				} else {
					lblInsgÜbungsblätter.setText("Übungsblätter insgesamt");
					insgÜbungsblätter.setText(Integer.toString(sheetContainer.getCountSheetType()));
				}
			}
		});
		sheetsPanel.add(sheetTabbedPane);

		if (sheetContainer != null) {
			allSheets = new TableSheets(sheetContainer);

			sheetTabbedPane.addTab("Sheets", allSheets);

			allOther = new TableOtherSheets(sheetContainer);

			sheetTabbedPane.addTab("Other", allOther);

			allSheetLecture = new TableSheetsLecture(sheetContainer, lectureContainer);

			sheetTabbedPane.addTab("Vorlesungen", allSheetLecture);
		}

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

		allLectures = new TableLectures(lectureContainer);
		allLectures.setBounds(359, 34, 427, 341);
		lecturePanel.add(allLectures);

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

		JPanel accountTab = new JPanel();
		tabbedPane.addTab("Account", accountTab);
		accountTab.setLayout(null);

		JLabel lblEmail = new JLabel("E-Mail:");
		lblEmail.setBounds(34, 33, 49, 14);
		accountTab.add(lblEmail);

		textFieldEmail = new JTextField();
		try {
			textFieldEmail.setText(lectureContainer.getUserEmail());
		} catch (StoreException e) {
			// Fehler tritt hier nicht auf
			e.printStackTrace();
		}
		textFieldEmail.setEditable(false);
		textFieldEmail.setBounds(86, 30, 157, 20);
		accountTab.add(textFieldEmail);
		textFieldEmail.setColumns(10);

		JButton btnPasswortAendern = new JButton("Passwort ändern");
		btnPasswortAendern.addActionListener((e) -> {
			new PasswordDialog((JFrame) this);
		});
		btnPasswortAendern.setBounds(34, 77, 137, 23);
		accountTab.add(btnPasswortAendern);

		JButton btnAccountLoeschen = new JButton("Account löschen");
		btnAccountLoeschen.setBounds(34, 111, 137, 23);
		btnAccountLoeschen.addActionListener(e -> onDeleteUser());
		accountTab.add(btnAccountLoeschen);

		ArrayList<Component> keyListenerComponents = new ArrayList<Component>();
		keyListenerComponents.add(allExams.getTable());
		keyListenerComponents.add(allLectures.getTable());
		keyListenerComponents.add(allSheets.getTable());
		keyListenerComponents.add(allOther.getTable());
		keyListenerComponents.add(tabbedPane);
		keyListenerComponents.add(sheetTabbedPane);
		keyListenerComponents.add(examsPane);
		keyListenerComponents.add(durchschnittsnote);
		keyListenerComponents.add(grade);
		keyListenerComponents.add(lblDurchschnittsnote);
		keyListenerComponents.add(btnAddLecture);
		keyListenerComponents.add(btnDelLecture);
		keyListenerComponents.add(btnModLecture);
		keyListenerComponents.add(sheetsPanel);
		keyListenerComponents.add(countSheet);
		keyListenerComponents.add(insgÜbungsblätter);
		keyListenerComponents.add(btnAddSheet);
		keyListenerComponents.add(btnDelSheet);
		keyListenerComponents.add(btnModSheet);
		keyListenerComponents.add(lecturePanel);
		keyListenerComponents.add(countLecture);
		keyListenerComponents.add(insgVorlesungen);
		keyListenerComponents.add(lblInsgVorlesungen);
		keyListenerComponents.add(btnAddLecture);
		keyListenerComponents.add(btnDelLecture);
		keyListenerComponents.add(btnModLecture);
		keyListenerComponents.add(accountTab);
		keyListenerComponents.add(btnAccountLoeschen);
		keyListenerComponents.add(btnPasswortAendern);
		keyListenerComponents.add(textFieldEmail);
		keyListenerComponents.add(lblEmail);
		keyListenerComponents.add(allSheetLecture.getTable());

		for (Component c : keyListenerComponents) {
			c.addKeyListener(new KeyAdapter() {
				@Override
				public void keyPressed(KeyEvent e) {
					if (e.getKeyCode() == KeyEvent.VK_1) {
						if (tabbedPane.getSelectedIndex() == 0)
							onAddExam();
						if (tabbedPane.getSelectedIndex() == 1)
							onAddSheet();
						if (tabbedPane.getSelectedIndex() == 2)
							onAddLecture();
					}
					if (e.getKeyCode() == KeyEvent.VK_2) {
						if (tabbedPane.getSelectedIndex() == 0)
							onDelExam();
						if (tabbedPane.getSelectedIndex() == 1)
							onDelSheet();
						if (tabbedPane.getSelectedIndex() == 2)
							onDelLecture();
					}
					if (e.getKeyCode() == KeyEvent.VK_3) {
						if (tabbedPane.getSelectedIndex() == 0)
							onModifyExam();
						if (tabbedPane.getSelectedIndex() == 1)
							onModifySheet();
						if (tabbedPane.getSelectedIndex() == 2)
							onModifyLecture();
					}
					if (e.getKeyCode() == KeyEvent.VK_F1)
						tabbedPane.setSelectedIndex(0);
					if (e.getKeyCode() == KeyEvent.VK_F2)
						tabbedPane.setSelectedIndex(1);
					if (e.getKeyCode() == KeyEvent.VK_F3)
						tabbedPane.setSelectedIndex(2);
					if (e.getKeyCode() == KeyEvent.VK_F4)
						tabbedPane.setSelectedIndex(3);
					if (e.getKeyCode() == KeyEvent.VK_A && tabbedPane.getSelectedIndex() == 1)
						sheetTabbedPane.setSelectedIndex(0);
					if (e.getKeyCode() == KeyEvent.VK_B && tabbedPane.getSelectedIndex() == 1)
						sheetTabbedPane.setSelectedIndex(1);
                    if (e.getKeyCode() == KeyEvent.VK_C && tabbedPane.getSelectedIndex() == 1)
                        sheetTabbedPane.setSelectedIndex(2);
				}
			});
		}

		setVisible(true);
	}

	private void calcDurchschnitt() {
		// TODO Auto-generated method stub
		int sumLp = 0;
		double sumNoten = 0;
		for (int i = 0; i < examContainer.getSize(); i++) {
			if (examContainer.getExamByIndex(i).getNote() <= 4.0) {
				sumLp += examContainer.getExamByIndex(i).getLeistungpunkte();
				sumNoten += examContainer.getExamByIndex(i).getNote()
						* examContainer.getExamByIndex(i).getLeistungpunkte();
			}
		}
		durchschnitt = sumNoten / (double) sumLp;
		durchschnittsnote.setText(new DecimalFormat("0.00").format(durchschnitt));
	}

	// Add exam, lecture, sheet

	private void onAddExam() {
		// Prüfung hinzufügen
		AddExamFrame addDia = new AddExamFrame(this, "Neue Prüfung hinzufügen");
		addDia.setVisible(true);
		calcDurchschnitt();
		allExams.load();
	}

	private void onAddLecture() {
		// Lecture hinzufügen
		AddLectureFrame addDia = new AddLectureFrame(this, "Neue Vorlesung", null);
		addDia.setVisible(true);
		allLectures.load();
		allExams.load();
		allSheetLecture.load();
		allSheets.load();
		allOther.load();
		insgVorlesungen.setText(String.valueOf(lectureContainer.getSize()));
	}

	private void onAddSheet() {
		// Sheet hinzufügen
		AddSheetFrame addDia = new AddSheetFrame(this, "Neues Übungsblatt hinzufügen");
		addDia.setVisible(true);
		allSheets.load();
		allOther.load();
		allSheetLecture.load();
		insgÜbungsblätter.setText(String.valueOf(sheetContainer.getSize()));
	}

	// Delete exam, lecture, sheet

	private void onDelExam() {
		// Prüfung löschen
		if (allExams.getTable().getSelectedRow() == -1) {
			JOptionPane.showMessageDialog(this,
					"Um eine Prüfung zu löschen muss zuerst eine Prüfung ausgewäht werden.");
		} else {
			JTable tb = allExams.getTable();
			int row = allExams.getTable().getSelectedRow();
			try {
				Lecture l = new Lecture(Integer.parseInt((String) tb.getValueAt(row, 0)),
						(String) tb.getValueAt(row, 1), Integer.parseInt((String) tb.getValueAt(row, 2)));
				Exam e = new Exam(l, Double.parseDouble((String) tb.getValueAt(row, 3)));
				examContainer.unlinkExam(e);
				allExams.load();
				calcDurchschnitt();
			} catch (IllegalInputException | StoreException | ExamNotFoundException e) {
				JOptionPane.showMessageDialog(this, "Error: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
			}
		}

	}

	private void onDelLecture() {
		// Lecture löschen
		if (allLectures.getTable().getSelectedRow() == -1) {
			JOptionPane.showMessageDialog(this,
					"Um eine Vorlesung zu löschen muss zuerst eine Vorlesung ausgewäht werden.");
		} else {
			JTable tb = allLectures.getTable();
			int row = allLectures.getTable().getSelectedRow();
			try {
				Lecture l = new Lecture(Integer.parseInt((String) tb.getValueAt(row, 0)),
						(String) tb.getValueAt(row, 1), Integer.parseInt((String) tb.getValueAt(row, 2)));
				lectureContainer.unlinkLecture(l);

				//Alle Exams und sheets löschen, die damit zusammenhängen:

                ArrayList<Sheet> sheetsDel = new ArrayList<>();
                for (Sheet s : sheetContainer) {
                    if (s.getLecture().equals(l))
                        sheetsDel.add(s);
                }
                for (Sheet s : sheetsDel)
                    sheetContainer.unlinkSheet(s);

                ArrayList<Exam> examsDel = new ArrayList<>();
                for (Exam e : examContainer) {
                    if (e.getLecture().equals(l))
                        examsDel.add(e);
                }
                for (Exam e : examsDel)
                    examContainer.unlinkExam(e);

				insgVorlesungen.setText(Integer.toString(lectureContainer.getSize()));
				allLectures.load();
                allExams.load();
                allSheetLecture.load();
                allSheets.load();
                allOther.load();
			} catch (IllegalInputException | StoreException | LectureNotFoundException | SheetNotFoundException | ExamNotFoundException e) {
				JOptionPane.showMessageDialog(this, "Error: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
			}
		}

	}

	private void onDelSheet() {
		// Sheet löschen
		int type;
		if (allSheets.getTable().getSelectedRow() == -1 && allOther.getTable().getSelectedRow() == -1) {
			JOptionPane.showMessageDialog(this,
					"Um eine Leistung zu löschen muss zuerst eine Leistung ausgewäht werden.");
		} else {
			JTable tb;
			int row;
			if (allSheets.getTable().getSelectedRow() == -1) {
				tb = allOther.getTable();
				row = allOther.getTable().getSelectedRow();
			} else {
				tb = allSheets.getTable();
				row = allSheets.getTable().getSelectedRow();
			}
			try {
				Lecture lecture = null;
				for (int i = 0; i < lectureContainer.getSize(); i++) {
					Lecture e = lectureContainer.getLectureByIndex(i);
					if (e.getName().equals((String) tb.getValueAt(row, 1))
							&& e.getSemester() == Integer.parseInt((String) tb.getValueAt(row, 0))) {
						lecture = e;
					}
				}
				if (sheetTabbedPane.getSelectedIndex() == 0) {
					type = Sheet.SHEET_TYPE;
				} else {
					type = Sheet.OTHER_TYPE;
				}
				Sheet e = new Sheet(lecture, Integer.parseInt((String) tb.getValueAt(row, 2)),
						Double.parseDouble((String) tb.getValueAt(row, 3)),
						Double.parseDouble((String) tb.getValueAt(row, 4)), type);
				sheetContainer.unlinkSheet(e);
				allSheets.load();
				allOther.load();
				allSheetLecture.load();
				insgÜbungsblätter.setText(String.valueOf(sheetContainer.getSize()));
			} catch (IllegalInputException | StoreException | SheetNotFoundException e) {
				JOptionPane.showMessageDialog(this, "Error: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
			}
		}

	}

	// Modify Exam, lecture, sheet

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
				String tempName = (String) tb.getValueAt(row, 1);
				int tempLp = Integer.parseInt((String) tb.getValueAt(row, 2));
				double tempNote = Double.parseDouble((String) tb.getValueAt(row, 3));
				Lecture l = new Lecture(tempSem, tempName, tempLp);
				Exam e = new Exam(l, tempNote);
				examContainer.unlinkExam(e);
				AddExamFrame addDia = new AddExamFrame(this, "Prüfung ändern");
				addDia.setData(tempSem, tempName, tempLp, tempNote);
				addDia.setCancelButtonActivated(false);
				addDia.setVisible(true);
				calcDurchschnitt();
				allExams.load();
			} catch (IllegalInputException | ExamNotFoundException | StoreException e) {
				JOptionPane.showMessageDialog(this, "Error: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
			}
		}
	}

	private void onModifyLecture() {
		// Lecture verändern
		if (allLectures.getTable().getSelectedRow() == -1) {
			JOptionPane.showMessageDialog(this,
					"Um eine Vorlesung zu ändern muss zuerst eine Vorlesung ausgewäht werden.");
		} else {
			JTable tb = allLectures.getTable();
			int row = allLectures.getTable().getSelectedRow();
            int tempSem = Integer.parseInt((String) tb.getValueAt(row, 0));
            String tempName = (String) tb.getValueAt(row, 1);
            int tempLp = Integer.parseInt((String) tb.getValueAt(row, 2));
            Lecture l = lectureContainer.getLectureByName(tempName, tempSem);
            AddLectureFrame editLec = new AddLectureFrame(this, "Vorlesung ändern", l);
            editLec.setData(tempSem, tempName, tempLp);
            editLec.setCancelButtonActivated(false);
            editLec.setVisible(true);
            allLectures.load();
            allExams.load();
            allSheetLecture.load();
            allSheets.load();
            allOther.load();
        }
	}

	private void onModifySheet() {
		// Sheet verändern
		int type;
		if (allSheets.getTable().getSelectedRow() == -1 && allOther.getTable().getSelectedRow() == -1) {
			JOptionPane.showMessageDialog(this,
					"Um ein Übungsblatt zu verändern muss zuerst ein Übungsblatt ausgewäht werden.");
		} else {
			JTable tb;
			int row;
			if (allSheets.getTable().getSelectedRow() == -1) {
				tb = allOther.getTable();
				row = allOther.getTable().getSelectedRow();
			} else {
				tb = allSheets.getTable();
				row = allSheets.getTable().getSelectedRow();
			}
			try {
				Lecture lecture = null;
				for (int i = 0; i < lectureContainer.getSize(); i++) {
					Lecture e = lectureContainer.getLectureByIndex(i);
					if (e.getName().equals((String) tb.getValueAt(row, 1))
							&& e.getSemester() == Integer.parseInt((String) tb.getValueAt(row, 0))) {
						lecture = e;
					}
				}
				if (sheetTabbedPane.getSelectedIndex() == 0) {
					type = Sheet.SHEET_TYPE;
				} else {
					type = Sheet.OTHER_TYPE;
				}
				Sheet e = new Sheet(lecture, Integer.parseInt((String) tb.getValueAt(row, 2)),
						Double.parseDouble((String) tb.getValueAt(row, 3)),
						Double.parseDouble((String) tb.getValueAt(row, 4)), type);
				sheetContainer.unlinkSheet(e);
				AddSheetFrame addDia = new AddSheetFrame(this, "Übungsblatt ändern");
				addDia.setData(Integer.parseInt((String) tb.getValueAt(row, 0)), (String) tb.getValueAt(row, 1),
						Integer.parseInt((String) tb.getValueAt(row, 2)),
						Double.parseDouble((String) tb.getValueAt(row, 3)),
						Double.parseDouble((String) tb.getValueAt(row, 4)), type);
				addDia.setCancelButtonActivated(false);
				addDia.setVisible(true);
				allOther.load();
				allSheets.load();
				allSheetLecture.load();
			} catch (IllegalInputException | StoreException | SheetNotFoundException e) {
				JOptionPane.showMessageDialog(this, "Error: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
			}
		}
	}

	// Menü-Methoden

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
			String tmpPassword = JOptionPane.showInputDialog(this,
					"Zum Löschen Passwort für User " + lectureContainer.getUserEmail() + " eingeben.", "Acoount Löschen",
					JOptionPane.INFORMATION_MESSAGE);
			if (tmpPassword.equals(lectureContainer.getPassword())) {
				lectureContainer.deleteUser();
				JOptionPane.showMessageDialog(this,
						"Account mit Email " + lectureContainer.getUserEmail() + " erfolgreich gelöscht", "Information",
						JOptionPane.INFORMATION_MESSAGE);
				onLogOut();
			} else {
				JOptionPane.showMessageDialog(this, "Falsches Passwort", "Error", JOptionPane.ERROR_MESSAGE);
				onDeleteUser();
			}
		} catch (StoreException e) {
			JOptionPane.showMessageDialog(this, "Error: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
		}
	}

	private void onRefresh() {
		try {
			lectureContainer.load();
			allOther.load();
			allSheets.load();
			allLectures.load();
			allExams.load();
		} catch (StoreException e) {
			JOptionPane.showMessageDialog(this, "Error: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
		}

	}

	public JTable getTable() {
		return this.allExams.getTable();
	}

	public TableExams getTableExams() {
		return this.allExams;
	}
}
