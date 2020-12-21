package cz.uhk.fim.pro2.shopping.utils;

import cz.uhk.fim.pro2.shopping.model.Child;
import cz.uhk.fim.pro2.shopping.model.GenderType;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.image.Image;

import java.awt.*;
import java.io.File;
import java.io.FileInputStream;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Year;
import java.util.*;

/**
 * Utils trida, ktera bude slouzit pro generovani dat
 */
public class DataGenerator {
    /**
     * Metoda pro generovani nahodneho data narozeni
     * @return nahodny datum narozeni mezi 0 a 15 lety
     */
    public static Date generateBirthdate() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
        Random random = new Random();
        int day = random.nextInt(31) + 1;
        int month = random.nextInt(12) + 1;
        int age = random.nextInt(15) + 1;
        Date date = null;

        try {
            date = sdf.parse(
                    String.format("%s.%s.%s",
                            day < 10 ? "0" + day : String.valueOf(day),
                            month < 10 ? "0" + month : String.valueOf(month),
                            (Year.now().getValue() - age)
                    )
            );
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return date;
    }

    /**
     * Metoda pro vygenerovani pohlavi ditete, předpokládám poměr 50/50
     * @return GenderType
     */

    private static GenderType generateGender(){
        Random random = new Random();
        if(random.nextBoolean()){
            return GenderType.MALE;
        }
        else return GenderType.FEMALE;
    }

    /**
     * Metoda pro vygenerování virginity, předpoklad je že čím je dítě starší tím pravděpodobnější je že už není virgin
     * vyřešeno tak že čím je dítě starší tím je větší pravděpodobnost že už není virgin
     * @return virginity
     */

    private static boolean generateVirginity(int age){
        Random random= new Random();
        return (random.nextInt(100) > (age * 6));
    }


    /**
     * Metoda pro vygenerování hmotnosti, hmotnost je do 90kg a řídí se podle věku a pohlaví (váha u ženy se přenásobí koeficientem 0,8)
     * @param age
     * @param gender
     * @return
     */

    private static int generateWeight(int age, GenderType gender){
        Random random = new Random();
        double weight = 0;

        for(int i=0; i<age;i++){
            weight += 4 + random.nextInt(3);
        }

        if(gender == GenderType.FEMALE){
            weight *= 0.8;
        }

        return (int) weight;
    }

    /**
     * nenapadlo me nic specialniho jak bych tohle generoval. predpokladam ze vetsina lidi je true a ze o narazky si rika mensina (30%)
     * @return
     */
    private static boolean generateRace(){
        Random random = new Random();
        if(random.nextInt(100)>30) return true;
        else return false;
    }

    /**
     * Metoda vracici skintone, slo by pozdeji propojit s race (na zaklade skin tone by se urcovala)
     * @return
     */

    private static int generateSkinTone(){
        Random random = new Random();
        int[] tones = { new Color(239,222,205).getRGB(),new Color(255,218,185).getRGB(),new Color(244,213,187).getRGB(),new Color(244,172,105).getRGB(),new Color(141,85,36).getRGB(), new Color(255,219,172).getRGB()};
        return tones[random.nextInt(tones.length)];
    }

    /**
     * metoda na generovani barvy oci
     * @return
     */

    private static int generateEyeTone(){
        Random random = new Random();
        int[] tones = { new Color(99,78,52).getRGB(),new Color(46,83,111).getRGB(),new Color(28,120,71).getRGB(), new Color(63,45,21).getRGB()};
        return tones[random.nextInt(tones.length)];
    }

    /**
     * metoda na generovani barvy vlasu
     * @return
     */
    private static int generateHairTone(){
        Random random = new Random();
        int[] tones = { new Color(140,104,74).getRGB(), new Color(51,42,35).getRGB(), new Color(242,218,145).getRGB(),new Color(102,79,60).getRGB() };
        return tones[random.nextInt(tones.length)];
    }

    private static String generateName(GenderType gender){
        String[] boyNames = {"Petr", "Pavel", "Tomáš", "Jan", "Jirka", "Matěj", "Šimon", "Míra", "Ondra", "Michal", "Matouš" };
        String[] girlNames = {"Lenka", "Jitka", "Anna", "Tereza", "Barbora", "Jana", "Markéta", "Alžběta", "Simona", "Adéla" };
        Random random = new Random();

        if(gender == GenderType.MALE){
            return boyNames[random.nextInt(boyNames.length)];
        }
        else return girlNames[random.nextInt(girlNames.length)];

    }

    /**
     * cena zacina na 1000 (predpokladam dolary, deti na soukrome ucely sou drahy)
     * cena se zveda podle pohlavi (zeny jsou drazsi), virginity (virgin se ceni) a veku (stary nikdo nechce)
     * @param gender
     * @param virgin
     * @param Age
     * @return
     */

    private static double generatePrice(GenderType gender,boolean virgin, int Age){
        Random random = new Random();
        double price = 1000;
        DecimalFormat decimalFormat = new DecimalFormat("#.##");

        if (gender == GenderType.FEMALE) price+=(100)+random.nextInt(1000)+random.nextDouble();
        if (gender == GenderType.MALE) price+=random.nextInt(1000)+random.nextDouble();
        if (virgin) price*=(random.nextDouble()+1.1);

        for(int i=0; i<Age;i++) price-=random.nextInt(150);

        if (price<1000) price = 1000;

        return Double.valueOf(decimalFormat.format(price));
    }

    private static Image generateAvatar(GenderType gender){
        String boy = "boy-";
       String girl = "girl-";
       String end = ".png";
       int position = 0;
       Random random = new Random();

       String name = null;
       if(gender==GenderType.FEMALE){
           name = girl;
           position =  random.nextInt(26);
       } else if (gender==GenderType.MALE) {
           name = boy;
           position =  random.nextInt(22);
       }

       position++;

       String filename = name + position + end;

       return FileUtils.loadImage(filename, "avatar");
    }

    private static String generateNationality(){
        Random random = new Random();
        String[] nationality = {
                "Czech", "Slovak"
        };

        return nationality[random.nextInt(nationality.length)];
    }

    /**
     * Metoda pro vygenerovani ditete
     * @return dite
     */

    private static Child generateChild() {
        Child random = new Child();
        random.setBirthDate(generateBirthdate());
        random.setGender(generateGender());
        random.setVirginity(generateVirginity(random.getAge()));
        random.setWeight(generateWeight(random.getAge(),random.getGender()));
        random.setRace(generateRace());
        random.setSkinTone(generateSkinTone());
        random.setEyeColor(generateEyeTone());
        random.setHairColor(generateHairTone());
        random.setDisplayName(generateName(random.getGender()));
        random.setPrice(generatePrice(random.getGender(), random.isVirginity(), random.getAge()));
        random.setAvatar(generateAvatar(random.getGender()));
        random.setPersonalId(String.valueOf(Math.abs(random.getDisplayName().hashCode())));
        random.setNationality(generateNationality());
        return random;
    }

    /**
     * Metoda pro vygenerovani seznamu nabidek
     * @param n pocet generovanych nabidek
     * @return seznam nabidek
     */
    public static ObservableList<Child> generateOffers(int n) {
        /**
         * vyreseno for cyklem ktery naplni list nahodne generovanymi detmi na zaklade vstupniho parametru n;
         */

        ObservableList<Child> list = FXCollections.observableArrayList();

        for (int i=0; i<n; i++){
            list.add(generateChild());
        }

        return list;

    }
}
