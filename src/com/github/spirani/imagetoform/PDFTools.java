package com.github.spirani.imagetoform;

import org.apache.pdfbox.exceptions.COSVisitorException;
import org.apache.pdfbox.pdmodel.*;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.edit.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType1Font;

import java.io.File;
import java.io.IOException;

public class PDFTools {
    PDDocument document;
    PDPage page;
    PDFont font;
    PDPageContentStream contentStream;
    public PDFTools() {
        document = new PDDocument();
        page = new PDPage();
        document.addPage(page);
        font = PDType1Font.COURIER_BOLD;
        try {
            contentStream = new PDPageContentStream(document, page);
            contentStream.beginText();
            contentStream.setFont(font, 14);
            contentStream.moveTextPositionByAmount(10, 10);
            contentStream.drawString("Hello");
            contentStream.moveTextPositionByAmount(10, 100);
            contentStream.drawString("Banana");
            contentStream.endText();
            contentStream.close();
            document.save("C:\\Users\\Bhai\\Desktop\\banana.pdf");
            document.close();
        } catch (IOException e) {
        } catch (COSVisitorException e) {}
    }
}
