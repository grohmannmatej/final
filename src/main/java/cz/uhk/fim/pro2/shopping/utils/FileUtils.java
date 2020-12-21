package cz.uhk.fim.pro2.shopping.utils;


import javafx.scene.image.Image;

import java.io.File;
import java.io.InputStream;

public class FileUtils {
    /**
     * Metoda pro nacteni obrazku ze souboru
     * @param imageName nazev souboru
     * @return obrazek
     */
    public static Image loadImage(String imageName, String type) {
    return new Image(String.valueOf(FileUtils.class.getResource("/assets/image/"+type+"/"+imageName)));
    }

    /**
     * Metoda pro zapis dat do souboru
     * @param content data
     * @param filename nazev souboru
     * @param extension koncovka souboru
     */
    public static void writeToFile(String content, String filename, String extension) {
        // TODO zapis obsahu do souboru s definovanym nazvem a priponou souboru
    }
}
