package data.exam;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.swing.DefaultListModel;
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
import data.exam.lecture.LectureContainer;
import data.exam.sheet.Sheet;
import data.exam.sheet.SheetContainer;
import store.StoreException;

public class PDFDialog extends JDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4665437924800154541L;
	private final JPanel contentPanel = new JPanel();
	private LectureContainer lecCon;
	private SheetContainer sheetCon;
	private ExamContainer examCon;
	private JFrame owner;
	private JCheckBox cbExams, cbSheets, cbSelectAllSemExam,
			cbDeselectFailedExams, cbAllLectures;
	private JList<Integer> semesterExamSelectList;
	private JList<String> lectureSheetSelectList;
	private List<Integer> semExamList;
	private List<String> lecSheetList;
	private JCheckBox cbIncludeOther;

	public PDFDialog(LectureContainer lecCon, SheetContainer sheetCon, ExamContainer examCon, JFrame owner)
			throws IOException {
		this.lecCon = lecCon;
		this.sheetCon = sheetCon;
		this.examCon = examCon;
		this.owner = owner;
		setBounds(100, 100, 450, 240);
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
		cbSelectAllSemExam.addActionListener((e) -> {
			if (!((JCheckBox) e.getSource()).isSelected()) {
				semesterExamSelectList.setEnabled(true);
				semesterExamSelectList.setSelectedIndex(1);
			} else {
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
			if (!lectureSheetList.contains(s.getName() + "(" + Integer.toString(s.getSemester()) + ")")) {
				lectureSheetList.add(s.getName() + "(" + Integer.toString(s.getSemester()) + ")");
			}
		}
		DefaultListModel<String> semesterSheetListModel = new DefaultListModel<String>();
		for (String s : lectureSheetList) {
			semesterSheetListModel.addElement(s);
		}
		lectureSheetSelectList = new JList<String>(semesterSheetListModel);
		lectureSheetSelectList.setEnabled(false);
		lectureSheetSelectList.setSelectionBackground(Color.LIGHT_GRAY);
		JScrollPane scrollpaneLecturesSheet = new JScrollPane(lectureSheetSelectList);
		scrollpaneLecturesSheet.setBounds(113, 108, 183, 46);
		contentPanel.add(scrollpaneLecturesSheet);

		cbAllLectures = new JCheckBox("alle Vorlesungen");
		cbAllLectures.setEnabled(false);
		cbAllLectures.setBounds(302, 105, 128, 23);
		cbSelectAllSemExam.addActionListener((e) -> {
			if (!((JCheckBox) e.getSource()).isSelected()) {
				lectureSheetSelectList.setEnabled(true);
				lectureSheetSelectList.setSelectedIndex(1);
			} else {
				lectureSheetSelectList.setEnabled(false);
			}
		});
		contentPanel.add(cbAllLectures);

		cbIncludeOther = new JCheckBox("andere Leistungen");
		cbIncludeOther.setEnabled(false);
		cbIncludeOther.setBounds(302, 131, 128, 23);
		contentPanel.add(cbIncludeOther);

		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
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
					} else {
						lecSheetList = lectureSheetSelectList.getSelectedValuesList();
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
		this.dispose();
		JFileChooser choose = new JFileChooser();
		choose.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		/* pdfFilter = new FileNameExtensionFilter("Pdf file(.pdf)", "pdf");
		choose.setFileFilter(pdfFilter);*/
		if (choose.showSaveDialog(owner) == JFileChooser.APPROVE_OPTION) {
			try (FileOutputStream writer = new FileOutputStream(choose.getSelectedFile() + "\\StudyAcc-" + LocalDate.now().toString() + ".pdf")) {
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
				profile.add("Profile");
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

					examTable.setWidths(new float[] { 1, 4, 1, 1 });

					examTable.setTotalWidth(PageSize.A4.getWidth() * 0.8f);
					;
					examTable.setLockedWidth(true);

					examTable.addCell(new PdfPCell(new Phrase("Semester", tableColumnHeader)));
					examTable.addCell(new PdfPCell(new Phrase("Vorlesung", tableColumnHeader)));
					examTable.addCell(new PdfPCell(new Phrase("ECTS", tableColumnHeader)));
					examTable.addCell(new PdfPCell(new Phrase("Note", tableColumnHeader)));

					// extracting data from the JTable and inserting it to PdfPTable
					for (int rows = 0; rows < examCon.getSize(); rows++) {
						Exam e = examCon.getExamByIndex(rows);
						if (semExamList.contains(e.getSemester())) {
							if (!cbDeselectFailedExams.isSelected() || e.getNote() <= 4.0) {
								examTable.addCell(
										new PdfPCell(new Phrase(Integer.toString(e.getSemester()), tableCell)));
								examTable.addCell(new PdfPCell(new Phrase(e.getName(), tableCell)));
								examTable.addCell(
										new PdfPCell(new Phrase(Integer.toString(e.getLeistungpunkte()), tableCell)));
								examTable.addCell(new PdfPCell(new Phrase(Double.toString(e.getNote()), tableCell)));
							}
						}
					}
					doc.add(examTable);
					doc.add(Chunk.NEWLINE);

				}

				// Sheet Table
				if (sheetCon != null && cbSheets.isSelected()) {

					Paragraph sheets = new Paragraph();
					sheets.setFont(subHeader);
					sheets.add("Übungsblätter und andere Leistungen");
					sheets.setSpacingAfter(15);
					doc.add(sheets);

					Paragraph sheetsTableHeader = new Paragraph();
					sheetsTableHeader.setFont(subHeader2);
					sheetsTableHeader.add("Übungsblätter");
					sheetsTableHeader.setSpacingAfter(15);

					Paragraph otherTableHeader = new Paragraph();
					otherTableHeader.setFont(subHeader2);
					otherTableHeader.add("Andere Leistungen");
					otherTableHeader.setSpacingAfter(15);

					PdfPTable sheetTable = new PdfPTable(5);
					// adding table headers

					sheetTable.setWidths(new float[] { 1, 8, 1, 2, 2 });

					sheetTable.setTotalWidth(PageSize.A4.getWidth() * 0.8f);

					sheetTable.setLockedWidth(true);

					sheetTable.addCell(new PdfPCell(new Phrase("Sem.", tableColumnHeader)));
					sheetTable.addCell(new PdfPCell(new Phrase("Vorlesung", tableColumnHeader)));
					sheetTable.addCell(new PdfPCell(new Phrase("No.", tableColumnHeader)));
					sheetTable.addCell(new PdfPCell(new Phrase("pts.", tableColumnHeader)));
					sheetTable.addCell(new PdfPCell(new Phrase("pts. max.", tableColumnHeader)));

					PdfPTable otherTable = new PdfPTable(4);
					// adding table headers

					otherTable.setWidths(new float[] { 1, 8, 2, 2 });

					otherTable.setTotalWidth(PageSize.A4.getWidth() * 0.8f);

					otherTable.setLockedWidth(true);

					otherTable.addCell(new PdfPCell(new Phrase("Sem", tableColumnHeader)));
					otherTable.addCell(new PdfPCell(new Phrase("Vorlesung", tableColumnHeader)));
					otherTable.addCell(new PdfPCell(new Phrase("pts.", tableColumnHeader)));
					otherTable.addCell(new PdfPCell(new Phrase("pts. max.", tableColumnHeader)));

					// extracting data from the JTable and inserting it to PdfPTable
					for (int rows = 0; rows < sheetCon.getSize(); rows++) {
						Sheet s = sheetCon.getSheetByIndex(rows);
						if (lecSheetList.contains(s.getName() + "(" + s.getSemester() + ")")) {
							if (s.getType() == Sheet.SHEET_TYPE) {
								sheetTable.addCell(
										new PdfPCell(new Phrase(Integer.toString(s.getSemester()), tableCell)));
								sheetTable.addCell(new PdfPCell(new Phrase(s.getName(), tableCell)));
								sheetTable
										.addCell(new PdfPCell(new Phrase(Integer.toString(s.getNumber()), tableCell)));
								sheetTable.addCell(new PdfPCell(new Phrase(Double.toString(s.getPoints()), tableCell)));
								sheetTable.addCell(
										new PdfPCell(new Phrase(Double.toString(s.getMaxPoints()), tableCell)));

							} else {
								if (cbIncludeOther.isSelected()) {
									otherTable.addCell(
											new PdfPCell(new Phrase(Integer.toString(s.getSemester()), tableCell)));
									otherTable.addCell(new PdfPCell(new Phrase(s.getName(), tableCell)));
									otherTable.addCell(
											new PdfPCell(new Phrase(Double.toString(s.getPoints()), tableCell)));
									otherTable.addCell(
											new PdfPCell(new Phrase(Double.toString(s.getMaxPoints()), tableCell)));
								}
							}
						}

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
				// Lecture Tabl3#
				if (lecCon != null && cbLectures.isSelected()) {

					Paragraph lectures = new Paragraph();
					lectures.setFont(subHeader);
					lectures.add("Vorlesungen");
					lectures.setSpacingAfter(15);
					doc.add(lectures);

					PdfPTable lecTable = new PdfPTable(3);
					// adding table headers

					lecTable.setWidths(new float[] { 8, 1, 1 });

					lecTable.setTotalWidth(PageSize.A4.getWidth() * 0.8f);

					lecTable.setLockedWidth(true);

					lecTable.addCell(new PdfPCell(new Phrase("Vorlesung", tableColumnHeader)));
					lecTable.addCell(new PdfPCell(new Phrase("Semester", tableColumnHeader)));
					lecTable.addCell(new PdfPCell(new Phrase("ECTS", tableColumnHeader)));

					// extracting data from the JTable and inserting it to PdfPTable
					for (int rows = 0; rows < lecCon.getSize(); rows++) {
						Lecture l = lecCon.getLectureByIndex(rows);
						if (semLecList.contains(l.getSemester())) {
							lecTable.addCell(new PdfPCell(new Phrase(l.getName(), tableCell)));
							lecTable.addCell(new PdfPCell(new Phrase(Integer.toString(l.getSemester()), tableCell)));
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
			} catch (FileNotFoundException e2) {
				JOptionPane.showMessageDialog(owner, "File could not be found");
			} catch (IOException e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			}
		} else {
			JOptionPane.showMessageDialog(owner, "Nothing selected");
		}

	}
}
