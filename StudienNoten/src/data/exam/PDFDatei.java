package data.exam;

import java.io.FileOutputStream;
import java.io.IOException;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

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

/*
Nur die Testdatei, ich hab noch nicht angefangen, da was zu machen
Du musst die jar http://central.maven.org/maven2/com/itextpdf/itextpdf/5.5.6/itextpdf-5.5.6.jar einbinden
Aber angeblich ist die Jar deprecated, aber ich hab irgendwie nicht gecheckt, wie man die neuste Version einbinden kann,
ich weiß auch nicht wo es die gibt 8es gibt nur eine zip.-Datei (https://sourceforge.net/projects/itext/files/),
aber obwohl ich alle jars eingebunden habe, hat es nicht geklappt
 */

public class PDFDatei {

	LectureContainer lecCon;
	SheetContainer sheetCon;
	ExamContainer examCon;

	public PDFDatei(LectureContainer lecCon, SheetContainer sheetCon, ExamContainer examCon, JFrame owner)
			throws IOException {
		this.lecCon = lecCon;
		this.sheetCon = sheetCon;
		this.examCon = examCon;

		JFileChooser choose = new JFileChooser();
		if (choose.showSaveDialog(owner) == JFileChooser.APPROVE_OPTION) {
			try (FileOutputStream writer = new FileOutputStream(choose.getSelectedFile() + ".pdf")) {
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
				if (examCon != null) {

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
						examTable.addCell(new PdfPCell(new Phrase(Integer.toString(e.getSemester()), tableCell)));
						examTable.addCell(new PdfPCell(new Phrase(e.getName(), tableCell)));
						examTable.addCell(new PdfPCell(new Phrase(Integer.toString(e.getLeistungpunkte()), tableCell)));
						examTable.addCell(new PdfPCell(new Phrase(Double.toString(e.getNote()), tableCell)));
					}
					doc.add(examTable);
					doc.add(Chunk.NEWLINE);

				}

				// Sheet Table
				if (sheetCon != null) {

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

					sheetTable.addCell(new PdfPCell(new Phrase("Sem", tableColumnHeader)));
					sheetTable.addCell(new PdfPCell(new Phrase("Vorlesung", tableColumnHeader)));
					sheetTable.addCell(new PdfPCell(new Phrase("Number", tableColumnHeader)));
					sheetTable.addCell(new PdfPCell(new Phrase("points", tableColumnHeader)));
					sheetTable.addCell(new PdfPCell(new Phrase("points max.", tableColumnHeader)));

					PdfPTable otherTable = new PdfPTable(4);
					// adding table headers

					otherTable.setWidths(new float[] { 1, 8, 2, 2 });

					otherTable.setTotalWidth(PageSize.A4.getWidth() * 0.8f);

					otherTable.setLockedWidth(true);

					otherTable.addCell(new PdfPCell(new Phrase("Sem", tableColumnHeader)));
					otherTable.addCell(new PdfPCell(new Phrase("Vorlesung", tableColumnHeader)));
					otherTable.addCell(new PdfPCell(new Phrase("points", tableColumnHeader)));
					otherTable.addCell(new PdfPCell(new Phrase("points max.", tableColumnHeader)));

					// extracting data from the JTable and inserting it to PdfPTable
					for (int rows = 0; rows < sheetCon.getSize(); rows++) {
						Sheet s = sheetCon.getSheetByIndex(rows);
						if (s.getType() == Sheet.SHEET_TYPE) {
							sheetTable.addCell(new PdfPCell(new Phrase(Integer.toString(s.getSemester()), tableCell)));
							sheetTable.addCell(new PdfPCell(new Phrase(s.getName(), tableCell)));
							sheetTable.addCell(new PdfPCell(new Phrase(Integer.toString(s.getNumber()), tableCell)));
							sheetTable.addCell(new PdfPCell(new Phrase(Double.toString(s.getPoints()), tableCell)));
							sheetTable.addCell(new PdfPCell(new Phrase(Double.toString(s.getMaxPoints()), tableCell)));

						} else {
							otherTable.addCell(new PdfPCell(new Phrase(Integer.toString(s.getSemester()), tableCell)));
							otherTable.addCell(new PdfPCell(new Phrase(s.getName(), tableCell)));
							otherTable.addCell(new PdfPCell(new Phrase(Double.toString(s.getPoints()), tableCell)));
							otherTable.addCell(new PdfPCell(new Phrase(Double.toString(s.getMaxPoints()), tableCell)));
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

				doc.close();

			} catch (DocumentException e1) {
				JOptionPane.showMessageDialog(owner, "Error while saving the file");
			} catch (StoreException e1) {
				JOptionPane.showMessageDialog(owner, "The data could not be fetched");
			}
		} else {
			JOptionPane.showMessageDialog(owner, "Nothing selected");
		}

	}

}
