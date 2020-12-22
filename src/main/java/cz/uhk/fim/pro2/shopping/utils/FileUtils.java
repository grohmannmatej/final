package cz.uhk.fim.pro2.shopping.utils;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfObject;
import com.itextpdf.kernel.pdf.PdfPage;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.pdf.canvas.PdfCanvas;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.IBlockElement;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import cz.uhk.fim.pro2.shopping.model.ShoppingCart;
import javafx.scene.image.Image;

import java.awt.*;
import java.io.*;

public class FileUtils {

    private static ObjectMapper objectMapper = getDefaultObjectMapper();

    private static ObjectMapper getDefaultObjectMapper() {
        ObjectMapper defaultObjectMapper = new ObjectMapper();
        defaultObjectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        return defaultObjectMapper;
    }

    private static JsonNode parse(String src) throws IOException {
        return objectMapper.readTree(src);
    }

    public static <A> A fromJson(JsonNode node, Class<A> clas) throws JsonProcessingException {
        return objectMapper.treeToValue(node, clas);
    }

    public static JsonNode toJson(Object a) {

        return objectMapper.valueToTree(a);

    }


    /**
     * Metoda pro nacteni obrazku ze souboru
     *
     * @param imageName nazev souboru
     * @return obrazek
     */
    public static Image loadImage(String imageName, String type) {
        return new Image(String.valueOf(FileUtils.class.getResource("/assets/image/" + type + "/" + imageName)));
    }

    /**
     * Metoda pro zapis dat do souboru
     *
     * @param content   data
     * @param filename  nazev souboru
     * @param extension koncovka souboru
     */
    public static void writeToFile(String content, String filename, String extension) throws IOException {

        FileOutputStream fileOutputStream = new FileOutputStream(filename + extension);
        DataOutputStream dataOutputStream = new DataOutputStream(new BufferedOutputStream(fileOutputStream));
        dataOutputStream.writeUTF(content);
        dataOutputStream.close();

    }

    public static void printShoppingCart(String filename, ShoppingCart cart) throws FileNotFoundException {

        PdfDocument pdfDocument = new PdfDocument(new PdfWriter(filename));

        PageSize pagesize = PageSize.A4;
        PdfPage page = pdfDocument.addNewPage(pagesize);

        Document document = new Document(pdfDocument);

        float[] pointColumnWidths = {50F, 140F, 60F, 80F, 80F, 80F, 80F};
        Table table = new Table(pointColumnWidths);

        table.addCell("");
        table.addCell("Name");
        table.addCell("Gender");
        table.addCell("Age");
        table.addCell("Weight");
        table.addCell("Virginity");
        table.addCell("Price");

        for(int i = 0; i<cart.getSize(); i++){

            table.addCell(String.valueOf(i+1));

            table.addCell(cart.getChild(i).getDisplayName());
            table.addCell(String.valueOf(cart.getChild(i).getGender()));
            table.addCell(String.valueOf(cart.getChild(i).getAge()));
            table.addCell(String.valueOf(cart.getChild(i).getWeight()));
            table.addCell(String.valueOf(cart.getChild(i).isVirginity()));
            table.addCell(String.format("%.2f",cart.getFinalPrice(i)));

        }

        table.addCell("");
        table.addCell("");
        table.addCell("");
        table.addCell("");
        table.addCell("");
        table.addCell("");

        table.addCell(String.format("%.2f",cart.getFinalPrice()));


        document.add(table);
        document.close();
    }

    public static void openFile(String s) {
        File file = new File(s);
        Desktop desktop = Desktop.getDesktop();
        if (file.exists()) {
            try {
                desktop.open(file);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}