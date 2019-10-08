package data.exam;

import java.io.FileOutputStream;
import java.io.IOException;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import data.exam.exam.Exam;
import data.exam.exam.ExamContainer;
import data.exam.lecture.LectureContainer;
import data.exam.sheet.SheetContainer;

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
				Document doc = new Document(PageSize.A4);
				PdfWriter.getInstance(doc, writer);

				doc.open();

				Paragraph paragraph1 = new Paragraph("StudyAcc Übersicht");
				paragraph1.setIndentationLeft(80);
				paragraph1.setIndentationRight(80);
				paragraph1.setAlignment(Element.ALIGN_CENTER);
				paragraph1.setSpacingAfter(15);
				doc.add(paragraph1);

				if (examCon != null) {

					Paragraph exams = new Paragraph("Klausuren");
					exams.setSpacingAfter(15);
					doc.add(exams);

					PdfPTable examTable = new PdfPTable(4);
					// adding table headers
					
					examTable.setWidths(new float[] { 1, 4, 1, 1});


					examTable.setTotalWidth(PageSize.A4.getWidth() * 0.8f);;
					examTable.setLockedWidth(true);

					examTable.addCell("Semester");
					examTable.addCell("Vorlesung");
					examTable.addCell("ECTS");
					examTable.addCell("Note");

					// extracting data from the JTable and inserting it to PdfPTable
					for (int rows = 0; rows < examCon.getSize(); rows++) {
						Exam e = examCon.getExamByIndex(rows);
						examTable.addCell(Integer.toString(e.getSemester()));
						examTable.addCell(e.getName());
						examTable.addCell(Integer.toString(e.getLeistungpunkte()));
						examTable.addCell(Double.toString(e.getNote()));
					}
					doc.add(examTable);

				}

				doc.close();
				
			} catch (DocumentException e1) {
				JOptionPane.showMessageDialog(owner, "Error while saving the file");
			} 
		} else {
			JOptionPane.showMessageDialog(owner, "Nothing selected");
		}

	}

}
