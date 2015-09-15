package com.github.spirani.imagetoform;

import com.github.spirani.imagetoform.gui.ITFTextBox;
import org.apache.pdfbox.exceptions.COSVisitorException;
import org.apache.pdfbox.pdmodel.*;
import org.apache.pdfbox.pdmodel.edit.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType1Font;

import javax.swing.*;
import java.io.IOException;
import java.util.ArrayList;

public class PDFTools {
    public static boolean generateAndSavePDF(int imagewidth, String doctype, String saveurl,
                                          ArrayList<JTextField> v, ArrayList<ITFTextBox> b) {
        PDDocument document = new PDDocument();
        PDPage page = new PDPage();
        document.addPage(page);
        PDFont font = PDType1Font.COURIER_BOLD;
        try {
            PDPageContentStream contentStream = new PDPageContentStream(document, page);
            contentStream.beginText();
            contentStream.setFont(font, 14);

            //contentStream.moveTextPositionByAmount(10, 10);
            //contentStream.drawString("Hello");
            //contentStream.moveTextPositionByAmount(10, 100);
            //contentStream.drawString("Banana");

            System.out.println(imagewidth);

            for(int i = 0; i < v.size(); i++) {
                System.out.println(b.get(i).x + " " + b.get(i).y);
                float h = page.getMediaBox().getHeight();
                float w = page.getMediaBox().getWidth();
                contentStream.moveTextPositionByAmount((float)b.get(i).x / (imagewidth/w),
                                                       h - ((float)b.get(i).y / (imagewidth/w)));
                contentStream.drawString(v.get(i).getText());
                contentStream.moveTextPositionByAmount(-1*((float)b.get(i).x / (imagewidth/w)),
                                                       -1*(h - ((float)b.get(i).y / (imagewidth/w))));
            }
            contentStream.endText();
            contentStream.close();
            document.save(saveurl);
            document.close();
            return true;
        } catch (IOException e) {
            return false;
        } catch (COSVisitorException e) {
            return false;
        }
    }
}
