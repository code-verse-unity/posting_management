/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package utils;

import model.Posting;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 *
 * @author ismael-rakotondrazaka
 */
public class GeneratePDF {
    public static String generate(Posting posting) {
        String outputPath = "outputs/pdf/affectation_" + posting.getId() + "_"
                + DateFormatter.format(posting.getPostingDate(), "yyyy-MM-dd") + "_"
                + posting.getEmployee().getFullName().replaceAll(" ", "_") + ".pdf";

        try {
            File file = new File(outputPath);

            // create nested folders
            file.getParentFile().mkdirs();

            // create the file
            file.createNewFile();

            Document document = new Document();
            PdfWriter.getInstance(document, new FileOutputStream(file));

            document.open();

            // Header
            Paragraph header = new Paragraph(
                    "Arrêté N° " +
                            posting.getId() + " du " +
                            DateFormatter.format(posting.getPostingDate(), "dd/MM/yyyy"));

            header.setPaddingTop(200);
            header.setAlignment(Element.ALIGN_CENTER);

            // Content
            Paragraph content = new Paragraph(
                    posting.getEmployee().getCivility() + " " +
                            posting.getEmployee().getFullName() + ", qui occupe le poste de " +
                            posting.getEmployee().getJob() +
                            (posting.getOldPlace().getId() != 0 ? (" à " + posting.getOldPlace().getFullName()) : " ")
                            + ", est affecté à " +
                            posting.getPlace().getFullName() + " pour compter de prise de service le " +
                            DateFormatter.format(posting.getPostingDate(), "dd/MM/yyyy") + ".");

            content.setPaddingTop(150);

            Paragraph footer = new Paragraph(
                    "Le présent communiqué sera enregistré et communiqué partout ou besoin sera.");

            content.setPaddingTop(150);

            document.add(header);
            document.add(content);
            document.add(footer);

            document.close();

        } catch (IOException | DocumentException e) {
            e.printStackTrace();
        }

        return outputPath;
    }
}
