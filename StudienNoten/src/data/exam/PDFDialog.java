package data.exam;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.mail.MessagingException;
import javax.swing.DefaultListModel;
import javax.swing.DefaultListSelectionModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;

import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Font.FontFamily;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import data.exam.exam.Exam;
import data.exam.exam.ExamContainer;
import data.exam.lecture.Lecture;
import data.exam.lecture.LectureContainer;
import data.exam.sheet.Sheet;
import data.exam.sheet.SheetContainer;
import store.StoreException;

public class PDFDialog extends JDialog {

	private static final long serialVersionUID = 4665437924800154541L;
	private final JPanel contentPanel = new JPanel();
	private LectureContainer lecCon;
	private SheetContainer sheetCon;
	private ExamContainer examCon;
	private JFrame owner;
	private JCheckBox cbExams, cbSheets, cbSelectAllSemExam, cbDeselectFailedExams, cbAllLectures, cbMail;
	private JList<Integer> semesterExamSelectList;
	private JList<String> lectureSheetSelectList;
	private List<Integer> semExamList, semLecList;
	private List<String> lecSheetList;
	private ArrayList<Lecture> lecturesToSheetPdf = new ArrayList<>();
	private JCheckBox cbIncludeOther;

	public PDFDialog(LectureContainer lecCon, SheetContainer sheetCon, ExamContainer examCon, JFrame owner)
			throws IOException {
		this.lecCon = lecCon;
		this.sheetCon = sheetCon;
		this.examCon = examCon;
		this.owner = owner;
		setBounds(100, 100, 450, 293);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);

		// Exams
		cbExams = new JCheckBox("Klausuren");
		cbExams.setSelected(false);
		cbExams.setBounds(6, 7, 99, 23);
		cbExams.addActionListener((e) -> {
			if (((JCheckBox) e.getSource()).isSelected()) {
				semesterExamSelectList.setEnabled(true);
				cbSelectAllSemExam.setEnabled(true);
				cbDeselectFailedExams.setEnabled(true);
			} else {
				semesterExamSelectList.setEnabled(false);
				cbSelectAllSemExam.setEnabled(false);
				cbDeselectFailedExams.setEnabled(false);
			}
		});
		contentPanel.add(cbExams);

		ArrayList<Integer> semesterExamList = new ArrayList<Integer>();
		for (int i = 0; i < examCon.getSize(); i++) {
			Exam e = examCon.getExamByIndex(i);
			if (!semesterExamList.contains(Integer.valueOf(e.getSemester()))) {
				semesterExamList.add(Integer.valueOf(e.getSemester()));
			}
		}
		Collections.sort(semesterExamList);
		DefaultListModel<Integer> semesterExamListModel = new DefaultListModel<Integer>();
		for (int s : semesterExamList) {
			semesterExamListModel.addElement(Integer.valueOf(s));
		}
		semesterExamSelectList = new JList<Integer>(semesterExamListModel);
		semesterExamSelectList.setSelectionModel(new DefaultListSelectionModel() {
		    private int i0 = -1;
		    private int i1 = -1;
		    
		    @Override
		    public void setSelectionInterval(int index0, int index1) {
		        if(i0 == index0 && i1 == index1){
		            if(getValueIsAdjusting()){
		                 setValueIsAdjusting(false);
		                 setSelection(index0, index1);
		            }
		        }else{
		            i0 = index0;
		            i1 = index1;
		            setValueIsAdjusting(false);
		            setSelection(index0, index1);
		        }
		    }
		    private void setSelection(int index0, int index1){
		        if(super.isSelectedIndex(index0)) {
		            super.removeSelectionInterval(index0, index1);
		        }else {
		            super.addSelectionInterval(index0, index1);
		        }
		    }
		});
		semesterExamSelectList.setEnabled(false);
		semesterExamSelectList.setSelectionBackground(Color.LIGHT_GRAY);
		JScrollPane scrollpaneSemesterExam = new JScrollPane(semesterExamSelectList);
		scrollpaneSemesterExam.setBounds(113, 36, 63, 46);
		contentPanel.add(scrollpaneSemesterExam);

		JLabel lblSemesterExam = new JLabel("Semester:");
		lblSemesterExam.setBounds(32, 37, 73, 14);
		contentPanel.add(lblSemesterExam);

		cbSelectAllSemExam = new JCheckBox("alle Semester");
		cbSelectAllSemExam.setEnabled(false);
		cbSelectAllSemExam.setBounds(203, 33, 99, 23);
		cbSelectAllSemExam.addItemListener((e) -> {
			if (!(cbSelectAllSemExam.isSelected())) {
				semesterExamSelectList.setEnabled(true);
				semesterExamSelectList.setSelectedIndices(null);
			} else {
				semesterExamSelectList.setSelectionInterval(0, examCon.getSize());
				semesterExamSelectList.setEnabled(false);
			}
		});
		contentPanel.add(cbSelectAllSemExam);

		cbDeselectFailedExams = new JCheckBox("nicht bestandene Klausuren ausblenden");
		cbDeselectFailedExams.setEnabled(false);
		cbDeselectFailedExams.setBounds(204, 59, 226, 23);
		contentPanel.add(cbDeselectFailedExams);

		// Sheets
		cbSheets = new JCheckBox("Übungsblätter");
		cbSheets.setSelected(false);
		cbSheets.setBounds(6, 83, 99, 23);
		cbSheets.addActionListener((e) -> {
			if (((JCheckBox) e.getSource()).isSelected()) {
				lectureSheetSelectList.setEnabled(true);
				semesterExamSelectList.setSelectedIndex(1);
				cbAllLectures.setEnabled(true);
				cbIncludeOther.setEnabled(true);
			} else {
				lectureSheetSelectList.setEnabled(false);
				cbAllLectures.setEnabled(false);
				cbIncludeOther.setEnabled(false);

			}
		});
		contentPanel.add(cbSheets);

		JLabel lblLectureSheet = new JLabel("Vorlesung:");
		lblLectureSheet.setBounds(32, 109, 73, 14);
		contentPanel.add(lblLectureSheet);

		ArrayList<String> lectureSheetList = new ArrayList<String>();
		for (int i = 0; i < sheetCon.getSize(); i++) {
			Sheet s = sheetCon.getSheetByIndex(i);
			if (!lectureSheetList.contains(s.getName() + " (" + Integer.toString(s.getSemester()) + ")")) {
				lectureSheetList.add(s.getName() + " (" + Integer.toString(s.getSemester()) + ")");
			}
		}
		DefaultListModel<String> semesterSheetListModel = new DefaultListModel<String>();
		for (String s : lectureSheetList) {
			semesterSheetListModel.addElement(s);
		}
		lectureSheetSelectList = new JList<String>(semesterSheetListModel);
		lectureSheetSelectList.setSelectionModel(new DefaultListSelectionModel() {
		    private int i0 = -1;
		    private int i1 = -1;
		    
		    @Override
		    public void setSelectionInterval(int index0, int index1) {
		        if(i0 == index0 && i1 == index1){
		            if(getValueIsAdjusting()){
		                 setValueIsAdjusting(false);
		                 setSelection(index0, index1);
		            }
		        }else{
		            i0 = index0;
		            i1 = index1;
		            setValueIsAdjusting(false);
		            setSelection(index0, index1);
		        }
		    }
		    private void setSelection(int index0, int index1){
		        if(super.isSelectedIndex(index0)) {
		            super.removeSelectionInterval(index0, index1);
		        }else {
		            super.addSelectionInterval(index0, index1);
		        }
		    }
		});
		lectureSheetSelectList.setEnabled(false);
		lectureSheetSelectList.setSelectionBackground(Color.LIGHT_GRAY);
		JScrollPane scrollpaneLecturesSheet = new JScrollPane(lectureSheetSelectList);
		scrollpaneLecturesSheet.setBounds(113, 108, 183, 77);
		contentPanel.add(scrollpaneLecturesSheet);

		cbAllLectures = new JCheckBox("alle Vorlesungen");
		cbAllLectures.setEnabled(false);
		cbAllLectures.setBounds(302, 105, 128, 23);
		cbAllLectures.addActionListener((e) -> {
			if (!((JCheckBox) e.getSource()).isSelected()) {
				lectureSheetSelectList.setEnabled(true);
				lectureSheetSelectList.setSelectedIndices(null);
			} else {
				lectureSheetSelectList.setSelectionInterval(0, sheetCon.getSize());
				lectureSheetSelectList.setEnabled(false);
			}
		});
		contentPanel.add(cbAllLectures);

		ArrayList<Integer> semesterLectureList = new ArrayList<Integer>();
		for (int i = 0; i < lecCon.getSize(); i++) {
			Lecture e = lecCon.getLectureByIndex(i);
			if (!semesterLectureList.contains(e.getSemester())) {
				semesterLectureList.add(e.getSemester());
			}
		}
		Collections.sort(semesterLectureList);
		DefaultListModel<Integer> semesterLectureListModel = new DefaultListModel<Integer>();
		for (int s : semesterLectureList) {
			semesterLectureListModel.addElement(Integer.valueOf(s));
		}

		cbIncludeOther = new JCheckBox("andere Leistungen");
		cbIncludeOther.setEnabled(false);
		cbIncludeOther.setSelected(true);
		cbIncludeOther.setBounds(302, 131, 128, 23);
		contentPanel.add(cbIncludeOther);

		cbMail = new JCheckBox("Zusätzlich Datei per Mail senden");
		cbMail.setSize(210, 20);
		cbMail.setLocation(6, 196);
		contentPanel.add(cbMail);

		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			buttonPane.add(cbMail, FlowLayout.LEFT);
			{
				JButton okButton = new JButton("OK");
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				okButton.addActionListener((e) -> {
					if (cbSelectAllSemExam.isSelected()) {
						semExamList = semesterExamList;
					} else {
						semExamList = semesterExamSelectList.getSelectedValuesList();
					}
					if (cbAllLectures.isSelected()) {
						lecSheetList = lectureSheetList;
						for (Lecture l : lecCon)
							lecturesToSheetPdf.add(l);
					} else {
						lecSheetList = lectureSheetSelectList.getSelectedValuesList();
						for (String lectureString : lectureSheetList) {
							String[] data = lectureString.split(" \\(");
							String lectureName = data[0];
							int lectureSemester = Integer.parseInt(data[1].replaceAll("\\)", ""));
							lecturesToSheetPdf.add(lecCon.getLectureByName(lectureName, lectureSemester));
						}
					}
					makePdf();
				});
				getRootPane().setDefaultButton(okButton);
			}
			{
				JButton cancelButton = new JButton("Cancel");
				cancelButton.setActionCommand("Cancel");
				cancelButton.addActionListener((e) -> this.dispose());
				buttonPane.add(cancelButton);
			}
		}
		this.setVisible(true);
	}

	private void makePdf() {
		if ((cbExams.isSelected() && !cbSelectAllSemExam.isSelected() && semesterExamSelectList.isSelectionEmpty())
				|| (cbSheets.isSelected() && !cbAllLectures.isSelected() && lectureSheetSelectList.isSelectionEmpty())) {
			JOptionPane.showMessageDialog(this,
					"Wenn nicht \"alle Semester (bzw. Vorlesungen)\" \n"
							+ "ausgewählt ist, musst du in der Liste welche auswähen.",
					"Error", JOptionPane.ERROR_MESSAGE);
			return;
		}
		this.dispose();
		JFileChooser choose = new JFileChooser();
		choose.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		/*
		 * pdfFilter = new FileNameExtensionFilter("Pdf file(.pdf)", "pdf");
		 * choose.setFileFilter(pdfFilter);
		 */
		String filename;
		if (choose.showSaveDialog(owner) == JFileChooser.APPROVE_OPTION) {
			filename = choose.getSelectedFile() + "\\StudyAcc-" + LocalDate.now().toString() + ".pdf";
			try (FileOutputStream writer = new FileOutputStream(filename)) {
				Font header = new Font(FontFamily.HELVETICA, 20, Font.BOLD);
				Font subHeader = new Font(FontFamily.HELVETICA, 16, Font.BOLD);
				Font subHeader2 = new Font(FontFamily.HELVETICA, 13, Font.BOLD);
				Font tableColumnHeader = new Font(FontFamily.HELVETICA, 12, Font.BOLD);
				Font tableCell = new Font(FontFamily.HELVETICA, 11, Font.NORMAL);
				Document doc = new Document(PageSize.A4);
				PdfWriter.getInstance(doc, writer);

				doc.open();

				Paragraph paragraph1 = new Paragraph();
				paragraph1.setFont(header);
				paragraph1.add("StudyAcc Übersicht");
				paragraph1.setIndentationLeft(80);
				paragraph1.setIndentationRight(80);
				paragraph1.setAlignment(Element.ALIGN_CENTER);
				paragraph1.setSpacingAfter(15);
				doc.add(paragraph1);

				doc.add(Chunk.NEWLINE);

				// Profile
				Paragraph profile = new Paragraph();
				profile.setFont(subHeader);
				profile.add("Profil");
				profile.setSpacingAfter(10);
				doc.add(profile);

				Paragraph name = new Paragraph();
				name.setFont(tableColumnHeader);
				name.add("Name: ");
				name.setFont(new Font(FontFamily.HELVETICA, 12));
				name.add(lecCon.getUserName());
				doc.add(name);

				doc.add(new Paragraph(""));

				Paragraph email = new Paragraph();
				email.setFont(tableColumnHeader);
				email.add("Email: ");
				email.setFont(new Font(FontFamily.HELVETICA, 12));
				email.add(lecCon.getUserEmail());
				doc.add(email);

				doc.add(new Paragraph(""));

				Paragraph memberSince = new Paragraph();
				memberSince.setFont(tableColumnHeader);
				memberSince.add("Mitglied seit: ");
				memberSince.setFont(new Font(FontFamily.HELVETICA, 12));
				memberSince.add(lecCon.getUserDate());
				doc.add(memberSince);

				doc.add(Chunk.NEWLINE);

				// Exam Table
				if (examCon != null && cbExams.isSelected()) {

					Paragraph exams = new Paragraph();
					exams.setFont(subHeader);
					exams.add("Klausuren");
					exams.setSpacingAfter(15);
					doc.add(exams);

					PdfPTable examTable = new PdfPTable(4);
					// adding table headers

					examTable.setWidths(new float[] { 2, 19, 3, 3 });

					examTable.setTotalWidth(PageSize.A4.getWidth() * 0.8f);
					;
					examTable.setLockedWidth(true);

					examTable.addCell(new PdfPCell(new Phrase("Sem", tableColumnHeader))).setHorizontalAlignment(Element.ALIGN_CENTER);
					examTable.addCell(new PdfPCell(new Phrase("Vorlesung", tableColumnHeader)));
					examTable.addCell(new PdfPCell(new Phrase("ECTS", tableColumnHeader))).setHorizontalAlignment(Element.ALIGN_CENTER);
					examTable.addCell(new PdfPCell(new Phrase("Note", tableColumnHeader))).setHorizontalAlignment(Element.ALIGN_CENTER);

					// extracting data from the JTable and inserting it to PdfPTable
					// Sortiert die exams:
					ExamContainer.setExamsSortedBySemester();
					int gesamtLP = 0;
					for (int rows = 0; rows < examCon.getSize(); rows++) {
						Exam e = examCon.getExamByIndex(rows);
						if (semExamList.contains(e.getSemester())) {
							if (e.getNote() <= 4.0)
								gesamtLP = gesamtLP + e.getLeistungpunkte();
							if (cbDeselectFailedExams.isSelected()) {
								PdfPCell semCellExam = new PdfPCell(new Phrase(Integer.toString(e.getSemester()), tableCell));
								semCellExam.setHorizontalAlignment(Element.ALIGN_CENTER);
								examTable.addCell(semCellExam);
								examTable.addCell(new PdfPCell(new Phrase(e.getName(), tableCell)));
								PdfPCell lpCellExam = new PdfPCell(new Phrase(Integer.toString(e.getLeistungpunkte()), tableCell));
								lpCellExam.setHorizontalAlignment(Element.ALIGN_CENTER);
								examTable.addCell(lpCellExam);
								PdfPCell markCellExam = new PdfPCell(new Phrase(Double.toString(e.getNote()), tableCell));
								markCellExam.setHorizontalAlignment(Element.ALIGN_CENTER);
								examTable.addCell(markCellExam);
							} else {
								if (e.getNote() < 4.1) {
									PdfPCell semCellExam = new PdfPCell(new Phrase(Integer.toString(e.getSemester()), tableCell));
									semCellExam.setHorizontalAlignment(Element.ALIGN_CENTER);
									examTable.addCell(semCellExam);
									examTable.addCell(new PdfPCell(new Phrase(e.getName(), tableCell)));
									PdfPCell lpCellExam = new PdfPCell(new Phrase(Integer.toString(e.getLeistungpunkte()), tableCell));
									lpCellExam.setHorizontalAlignment(Element.ALIGN_CENTER);
									examTable.addCell(lpCellExam);
									PdfPCell markCellExam = new PdfPCell(new Phrase(Double.toString(e.getNote()), tableCell));
									markCellExam.setHorizontalAlignment(Element.ALIGN_CENTER);
									examTable.addCell(markCellExam);
								}
							}
						}
					}
					examTable.addCell(new PdfPCell(new Phrase("", tableCell)));
					examTable.addCell(new PdfPCell(new Phrase("Gesamt", tableColumnHeader)));
					examTable.addCell(new PdfPCell(new Phrase(Integer.toString(gesamtLP), tableColumnHeader))).setHorizontalAlignment(Element.ALIGN_CENTER);
					examTable.addCell(new PdfPCell(new Phrase(calcDurchschnitt(), tableColumnHeader))).setHorizontalAlignment(Element.ALIGN_CENTER);
					doc.add(examTable);
					doc.add(Chunk.NEWLINE);

				}

				// Sheet Table
				if (sheetCon != null && cbSheets.isSelected()) {

					Paragraph sheets = new Paragraph();
					sheets.setFont(subHeader);
					if (cbIncludeOther.isSelected())
						sheets.add("Übungsblätter und andere Leistungen");
					else
						sheets.add("Übungsblätter");
					sheets.setSpacingAfter(15);
					doc.add(sheets);

					Paragraph insgesamtTableHeader = new Paragraph();
					insgesamtTableHeader.setFont(subHeader2);
					insgesamtTableHeader.add("Insgesamt");
					insgesamtTableHeader.setSpacingAfter(15);

					Paragraph sheetsTableHeader = new Paragraph();
					sheetsTableHeader.setFont(subHeader2);
					sheetsTableHeader.add("Übungsblätter");
					sheetsTableHeader.setSpacingAfter(15);

					Paragraph otherTableHeader = new Paragraph();
					otherTableHeader.setFont(subHeader2);
					otherTableHeader.add("Andere Leistungen");
					otherTableHeader.setSpacingAfter(15);

					PdfPTable insgesamtTable = new PdfPTable(5);
					// adding table headers

					insgesamtTable.setWidths(new float[] { 2, 16, 3, 3, 3 });

					insgesamtTable.setTotalWidth(PageSize.A4.getWidth() * 0.8f);

					insgesamtTable.setLockedWidth(true);

					insgesamtTable.addCell(new PdfPCell(new Phrase("Sem", tableColumnHeader))).setHorizontalAlignment(Element.ALIGN_CENTER);
					insgesamtTable.addCell(new PdfPCell(new Phrase("Vorlesung", tableColumnHeader)));
					insgesamtTable.addCell(new PdfPCell(new Phrase("Punkte", tableColumnHeader))).setHorizontalAlignment(Element.ALIGN_CENTER);
					insgesamtTable.addCell(new PdfPCell(new Phrase("Max", tableColumnHeader))).setHorizontalAlignment(Element.ALIGN_CENTER);
					insgesamtTable.addCell(new PdfPCell(new Phrase("Prozent", tableColumnHeader))).setHorizontalAlignment(Element.ALIGN_CENTER);

					PdfPTable sheetTable = new PdfPTable(5);
					// adding table headers

					sheetTable.setWidths(new float[] { 2, 16, 3, 3, 3 });

					sheetTable.setTotalWidth(PageSize.A4.getWidth() * 0.8f);

					sheetTable.setLockedWidth(true);

					sheetTable.addCell(new PdfPCell(new Phrase("Sem", tableColumnHeader))).setHorizontalAlignment(Element.ALIGN_CENTER);
					sheetTable.addCell(new PdfPCell(new Phrase("Vorlesung", tableColumnHeader)));
					sheetTable.addCell(new PdfPCell(new Phrase("Nummer", tableColumnHeader))).setHorizontalAlignment(Element.ALIGN_CENTER);
					sheetTable.addCell(new PdfPCell(new Phrase("Punkte", tableColumnHeader))).setHorizontalAlignment(Element.ALIGN_CENTER);
					sheetTable.addCell(new PdfPCell(new Phrase("Max", tableColumnHeader))).setHorizontalAlignment(Element.ALIGN_CENTER);

					PdfPTable otherTable = new PdfPTable(4);
					// adding table headers

					otherTable.setWidths(new float[] { 2, 19, 3, 3 });

					otherTable.setTotalWidth(PageSize.A4.getWidth() * 0.8f);

					otherTable.setLockedWidth(true);

					otherTable.addCell(new PdfPCell(new Phrase("Sem", tableColumnHeader))).setHorizontalAlignment(Element.ALIGN_CENTER);
					otherTable.addCell(new PdfPCell(new Phrase("Vorlesung", tableColumnHeader)));
					otherTable.addCell(new PdfPCell(new Phrase("Punkte", tableColumnHeader))).setHorizontalAlignment(Element.ALIGN_CENTER);
					otherTable.addCell(new PdfPCell(new Phrase("Max", tableColumnHeader))).setHorizontalAlignment(Element.ALIGN_CENTER);

					// extracting data from the JTable and inserting it to PdfPTable

					lecturesToSheetPdf.sort(new Comparator<Lecture>() {
						@Override
						public int compare(Lecture e1, Lecture e2) {
							if (e1.getSemester() - e2.getSemester() == 0)
								return e1.getLeistungpunkte() - e2.getLeistungpunkte();
							else
								return e1.getSemester() - e2.getSemester();
						}
					});

					for (Lecture l : lecturesToSheetPdf) {
						boolean existierendeSheets = false;
						for (Sheet s : sheetCon) {
							if (cbIncludeOther.isSelected()) {
								if (s.getLecture().equals(l))
									existierendeSheets = true;
							} else {
								if (s.getLecture().equals(l) && s.getType() == Sheet.SHEET_TYPE)
									existierendeSheets = true;
							}
						}
						if (existierendeSheets) {
							PdfPCell semCellSheet = new PdfPCell(new Phrase(Integer.toString(l.getSemester()), tableCell));
							semCellSheet.setHorizontalAlignment(Element.ALIGN_CENTER);
							insgesamtTable.addCell(semCellSheet);
							insgesamtTable.addCell(new PdfPCell(new Phrase(l.getName(), tableCell)));
							PdfPCell ptsCellSheet = new PdfPCell(new Phrase(
									new DecimalFormat("0.00").format(getPoints(l)).replaceAll(",", "."), tableCell));
							ptsCellSheet.setHorizontalAlignment(Element.ALIGN_CENTER);
							insgesamtTable.addCell(ptsCellSheet);
							PdfPCell ptsMaxCellSheet = new PdfPCell(new Phrase(new DecimalFormat("0.00").format(getMaxPoints(l)).replaceAll(",", "."),
									tableCell));
							ptsMaxCellSheet.setHorizontalAlignment(Element.ALIGN_CENTER);
							insgesamtTable.addCell(ptsMaxCellSheet);
							PdfPCell prcCellSheet = new PdfPCell(new Phrase(new DecimalFormat("0.00").format(getPercentage(l)).replaceAll(",", ".") + "%",
									tableCell));
							prcCellSheet.setHorizontalAlignment(Element.ALIGN_CENTER);
							insgesamtTable.addCell(prcCellSheet);
						}
						ArrayList<Sheet> toPrintSheets = getSheetsSortedByNumber(l, Sheet.SHEET_TYPE);
						for (Sheet s : toPrintSheets) {
							PdfPCell semCellSheet = new PdfPCell(new Phrase(Integer.toString(s.getSemester()), tableCell));
							semCellSheet.setHorizontalAlignment(Element.ALIGN_CENTER);
							sheetTable.addCell(semCellSheet);
							sheetTable.addCell(new PdfPCell(new Phrase(s.getName(), tableCell)));
							PdfPCell numCellSheet = new PdfPCell(new Phrase(Integer.toString(s.getNumber()), tableCell));
							numCellSheet.setHorizontalAlignment(Element.ALIGN_CENTER);
							sheetTable.addCell(numCellSheet);
							PdfPCell ptsCellSheet = new PdfPCell(new Phrase(
									new DecimalFormat("0.00").format(s.getPoints()).replaceAll(",", "."), tableCell));
							ptsCellSheet.setHorizontalAlignment(Element.ALIGN_CENTER);
							sheetTable.addCell(ptsCellSheet);
							PdfPCell ptsMaxCellSheet = new PdfPCell(new Phrase(new DecimalFormat("0.00").format(s.getMaxPoints()).replaceAll(",", "."),
									tableCell));
							ptsMaxCellSheet.setHorizontalAlignment(Element.ALIGN_CENTER);
							sheetTable.addCell(ptsMaxCellSheet);
						}
						if (cbIncludeOther.isSelected()) {
							toPrintSheets = getSheetsSortedByNumber(l, Sheet.OTHER_TYPE);
							for (Sheet s : toPrintSheets) {
								PdfPCell semCellSheet = new PdfPCell(new Phrase(Integer.toString(s.getSemester()), tableCell));
								semCellSheet.setHorizontalAlignment(Element.ALIGN_CENTER);
								otherTable.addCell(semCellSheet);
								otherTable.addCell(new PdfPCell(new Phrase(s.getName(), tableCell)));
								PdfPCell ptsCellSheet = new PdfPCell(new Phrase(
										new DecimalFormat("0.00").format(s.getPoints()).replaceAll(",", "."), tableCell));
								ptsCellSheet.setHorizontalAlignment(Element.ALIGN_CENTER);
								otherTable.addCell(ptsCellSheet);
								PdfPCell ptsMaxCellSheet = new PdfPCell(new Phrase(new DecimalFormat("0.00").format(s.getMaxPoints()).replaceAll(",", "."),
										tableCell));
								ptsMaxCellSheet.setHorizontalAlignment(Element.ALIGN_CENTER);
								otherTable.addCell(ptsMaxCellSheet);
							}
						}
					}

					if (insgesamtTable.getRows().size() > 1) {
						doc.add(insgesamtTableHeader);
						doc.add(insgesamtTable);
						doc.add(Chunk.NEWLINE);
					}
					if (sheetTable.getRows().size() > 1) {
						doc.add(sheetsTableHeader);
						doc.add(sheetTable);
						doc.add(Chunk.NEWLINE);
					}
					if (otherTable.getRows().size() > 1) {
						doc.add(otherTableHeader);
						doc.add(otherTable);
						doc.add(Chunk.NEWLINE);
					}

				}

				/*
				if (lecCon != null && cbLectures.isSelected()) {

					Paragraph lectures = new Paragraph();
					lectures.setFont(subHeader);
					lectures.add("Vorlesungen");
					lectures.setSpacingAfter(15);
					doc.add(lectures);

					PdfPTable lecTable = new PdfPTable(3);
					// adding table headers

					lecTable.setWidths(new float[] { 2, 22, 3 });

					lecTable.setTotalWidth(PageSize.A4.getWidth() * 0.8f);

					lecTable.setLockedWidth(true);

					lecTable.addCell(new PdfPCell(new Phrase("Sem", tableColumnHeader)));
					lecTable.addCell(new PdfPCell(new Phrase("Vorlesung", tableColumnHeader)));
					lecTable.addCell(new PdfPCell(new Phrase("ECTS", tableColumnHeader)));

					// extracting data from the JTable and inserting it to PdfPTable
					LectureContainer.setLecturesSortedBySemester();
					for (int rows = 0; rows < lecCon.getSize(); rows++) {
						Lecture l = lecCon.getLectureByIndex(rows);
						if (semLecList.contains(l.getSemester())) {
							lecTable.addCell(new PdfPCell(new Phrase(Integer.toString(l.getSemester()), tableCell)));
							lecTable.addCell(new PdfPCell(new Phrase(l.getName(), tableCell)));
							lecTable.addCell(
									new PdfPCell(new Phrase(Integer.toString(l.getLeistungpunkte()), tableCell)));
						}
					}
					doc.add(lecTable);
					doc.add(Chunk.NEWLINE);
					

				}*/

				doc.close();

			} catch (DocumentException e1) {
				JOptionPane.showMessageDialog(owner, "Error while saving the file");
			} catch (StoreException e1) {
				JOptionPane.showMessageDialog(owner, "The data could not be fetched");
			} catch (IOException e2) {
				JOptionPane.showMessageDialog(owner, "File could not be found");
			}

			if (cbMail.isSelected()) {
				try {
					Email.postPdfMail(filename);
				} catch (MessagingException | StoreException | UnsupportedEncodingException e) {
					JOptionPane.showMessageDialog(owner, "Error: " + e.getMessage());
				}
			}

		} else {
			JOptionPane.showMessageDialog(owner, "Nothing selected");
		}

	}

	private String calcDurchschnitt() {
		int sumLp = 0;
		double sumNoten = 0;
		for (int i = 0; i < examCon.getSize(); i++) {
			if (semExamList.contains(examCon.getExamByIndex(i).getSemester())) {
				if (examCon.getExamByIndex(i).getNote() <= 4.0) {
					sumLp += examCon.getExamByIndex(i).getLeistungpunkte();
					sumNoten += examCon.getExamByIndex(i).getNote() * examCon.getExamByIndex(i).getLeistungpunkte();
				}
			}
		}
		double durchschnitt = sumNoten / (double) sumLp;
		return new DecimalFormat("0.00").format(durchschnitt).replaceAll(",", ".");
	}

	private ArrayList<Sheet> getSheetsSortedByNumber(Lecture l, int sheetType) {
		ArrayList<Sheet> result = new ArrayList<>();
		for (Sheet s : sheetCon) {
			if (s.getLecture().equals(l) && s.getType() == sheetType)
				result.add(s);
		}
		result.sort(new Comparator<Sheet>() {
			@Override
			public int compare(Sheet s1, Sheet s2) {
				return s1.getNumber() - s2.getNumber();
			}
		});
		return result;
	}

	private double getPoints(Lecture l) {
		double points = 0;
		for (Sheet s : sheetCon) {
			if (s.getLecture().equals(l))
				points = points + s.getPoints();
		}
		return points;
	}

	private double getMaxPoints(Lecture l) {
		double maxPoints = 0;
		for (Sheet s : sheetCon) {
			if (s.getLecture().equals(l))
				maxPoints = maxPoints + s.getMaxPoints();
		}
		return maxPoints;
	}

	private double getPercentage(Lecture l) {
		double percentage = getPoints(l) / getMaxPoints(l);
		return percentage * 100;
	}
}
