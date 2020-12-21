package cz.uhk.fim.pro2.shopping.utils;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.scene.image.Image;

import java.io.*;

public class FileUtils {

    private static ObjectMapper objectMapper = getDefaultObjectMapper();

    private static ObjectMapper getDefaultObjectMapper(){
        ObjectMapper defaultObjectMapper = new ObjectMapper();
        defaultObjectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        return defaultObjectMapper;
    }

    private static JsonNode parse(String src) throws IOException {
        return objectMapper.readTree(src);
    }

    public static <A> A fromJson(JsonNode node, Class<A> clas) throws JsonProcessingException {
        return objectMapper.treeToValue(node,clas);
    }

    public static JsonNode toJson(Object a){

        return objectMapper.valueToTree(a);

    }



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
    public static void writeToFile(String content, String filename, String extension) throws IOException {

        FileOutputStream fileOutputStream = new FileOutputStream(filename+extension);
        DataOutputStream dataOutputStream = new DataOutputStream(new BufferedOutputStream(fileOutputStream));
        dataOutputStream.writeUTF(content);
        dataOutputStream.close();

    }

}
