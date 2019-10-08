package data.exam;

import java.io.File;
import java.io.FileOutputStream;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.Font;
import com.itextpdf.text.List;
import com.itextpdf.text.ListItem;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

public class test {

	
	
    File file = new File("/home/bettacjo/Dokumente/PdfFile.pdf");
    file.delete();
    Document document = new Document(PageSize.A4);
    PdfWriter.getInstance(document, new FileOutputStream(file));
    document.open();
    document.addAuthor("Grid");

    // Title
    String title = "Ein PDF-Dokument mit iText und Java";
    Font titleFont = new Font(Font.FontFamily.COURIER, 22, Font.BOLD, BaseColor.WHITE);
    Chunk titleObj = new Chunk(title, titleFont);
    titleObj.setBackground(new BaseColor(33, 33, 233), 1f, 1f, 1f, 3f);

    // List
    List list = new List(true, 30);
    list.add(new ListItem("Erstens"));
    list.add(new ListItem("Zweitens"));
    list.add(new ListItem("Drittens"));

    // Table
    PdfPTable table = new PdfPTable(2);
    table.setWidthPercentage(100);
    table.setSpacingAfter(10f);
    table.setSpacingBefore(15f);
    table.setWidths(new float[] { 2f, 2f });
    Font fontHeader = new Font(Font.FontFamily.COURIER, 15, Font.BOLD, BaseColor.DARK_GRAY);
    PdfPCell headerCell = new PdfPCell(new Phrase("Überschrift 1", fontHeader));
    headerCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
    headerCell.setPaddingBottom(5f);
    table.addCell(headerCell);
    headerCell = new PdfPCell(new Phrase("Überschrift 2", fontHeader));
    headerCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
    headerCell.setPaddingBottom(5f);
    table.addCell(headerCell);
    table.addCell("Data 1");
    table.addCell("Data 2");
    table.addCell("Data 3");
    table.addCell("Data 4");

   /* // Image
    String imagePath = "logo.JPG";
    Image img = Image.getInstance(imagePath);
    img.scaleAbsolute(137f, 64f);*/

    // Document
    document.open();
    //document.add(img);
    document.add(new Paragraph(""));
    document.add(Chunk.NEWLINE);
    document.add(titleObj);
    document.add(Chunk.NEWLINE);
    document.add(new Paragraph("Beispiel einer Liste"));
    document.add(list);
    document.newPage();
    document.add(table);
    document.close();

    System.out.println("done");
}
