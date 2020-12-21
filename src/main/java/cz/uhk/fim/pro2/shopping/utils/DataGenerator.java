package cz.uhk.fim.pro2.shopping.utils;

import cz.uhk.fim.pro2.shopping.model.Child;
import javafx.collections.ObservableList;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Year;
import java.util.Date;
import java.util.Random;

/**
 * Utils trida, ktera bude slouzit pro generovani dat
 */
public class DataGenerator {
    /**
     * Metoda pro generovani nahodneho data narozeni
     * @return nahodny datum narozeni mezi 0 a 15 lety
     */
    public static Date randomBirthdate() {
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
     * Metoda pro vygenerovani ditete
     * @return dite
     */
    private Child generateChild() {
        // TODO [assignment2] doplnit generovani ditete:
        //  generovana data by mela davat smysl, v zakladu zohlednit pohlavi pro spravne prirazeni obrazku (boy-#.png | girl-#.png),
        //  u vahy a ceny zohlednit nejake limity (nesmysl je u vahy mit 5kg kdyz je dite stare 12 let nebo treba 400kg apod., cena by mohla zacinat na 100,-),
        //  cena i vaha by mela byt zohlednena i u pohlavi - muzi budou vetsinou tezsi, pokud budeme sexiste tak zeny mohou mit mensi hodnotu nez muzi apod
        return null;
    }

    /**
     * Metoda pro vygenerovani seznamu nabidek
     * @param n pocet generovanych nabidek
     * @return seznam nabidek
     */
    public static ObservableList<Child> generateOffers(int n) {
        // TODO [assignment2] generovani poctu nabidek na zaklade vstupniho parametru - vyuziti metody generateChild()
        return null;
    }
}
