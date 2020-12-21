package cz.uhk.fim.pro2.shopping.model;

import cz.uhk.fim.pro2.shopping.utils.DataGenerator;
//import cz.uhk.fim.pro2.shopping.utils.parser.CSVParser;
//import cz.uhk.fim.pro2.shopping.utils.parser.JSONParser;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class Marketplace {
    private ObservableList<Child> offerList;

    public Marketplace() {
        this.offerList = DataGenerator.generateOffers(20);


        // JSONParser.toFile("json-list", this.offerList);
        // CSVParser.toFile(this.offerList, "csv-list");

        int minAge = 0;
        int maxAge = 14;
        double minPrice = 0;
        double maxPrice = 2000;
        GenderType gender = GenderType.MALE;

        filter(minAge, maxAge, minPrice, maxPrice, gender);

    }

    /**
     * Metoda pro filtrovani zbozi dle parametru
     * @param minPrice minimalni cena
     * @param maxPrice maximalni cena
     * @param minAge minimalni vek
     * @param maxAge maximalni vek
     * @param gender pohlavi
     */
    public void filter(int minAge, int maxAge, double minPrice, double maxPrice, GenderType gender) {
        List<Child> filteredList = this.offerList
                .stream()
                .filter(child ->
                        child.getAge() >= minAge &&
                                child.getAge() <= maxAge &&
                                child.getPrice() >= minPrice &&
                                child.getPrice() <= maxPrice &&
                                child.getGender().equals(gender)
                )
                .collect(Collectors.toList());

        System.out.println("=======");
        for (Child c : filteredList) {
            System.out.println(c);
        }
        System.out.println("=======");
    }

    /**
     * Metoda odebere nabidku z listu po pridani do kosiku podle indexu
     * @param index index nabidky
     */
    public void removeOffer(int index) {
        // TODO odebrani prvku z listu dle indexu
    }

    /**
     * Metoda odebere nabidku z listu po pridani do kosiku podle reference
     * @param child reference na konkretni nabidku
     */
    public void removeOffer(Child child) {
        // TODO odebrani prvku z listu dle reference
    }

    /**
     * Metoda pro vraceni konkretni nabidky
     * @param index index v listu
     * @return nabidka/dite
     */
    public Child getOfferDetail(int index) {
        // TODO vraceni vybrane nabidky podle indexu
        return null;
    }

    public ObservableList<Child> getOfferList() {
        return offerList;
    }

    public void setOfferList(ObservableList<Child> offerList) {
        this.offerList = offerList;
    }
}
