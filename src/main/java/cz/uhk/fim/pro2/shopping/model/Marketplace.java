package cz.uhk.fim.pro2.shopping.model;

import cz.uhk.fim.pro2.shopping.utils.DataGenerator;
//import cz.uhk.fim.pro2.shopping.utils.parser.CSVParser;
//import cz.uhk.fim.pro2.shopping.utils.parser.JSONParser;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableListBase;

import javax.xml.crypto.Data;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class Marketplace {

    private ObservableList<Child> offerList;
    private ObservableList<Child> offerBackUpList;

    public Marketplace() {
        this.offerBackUpList = DataGenerator.generateOffers(20);
        this.offerList = FXCollections.observableArrayList();
        for (Child child: offerBackUpList) offerList.add(child);
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

        List<Child> filteredList = this.offerBackUpList;

        if(gender != GenderType.ALL) filteredList = filteredList.stream().filter(child -> child.getGender().equals(gender)).collect(Collectors.toList());

        if(minAge>0) filteredList = filteredList.stream().filter(child -> child.getAge() >= minAge).collect(Collectors.toList());
        if(maxAge>0&&maxAge>minAge) filteredList = filteredList.stream().filter(child -> child.getAge() <= maxAge).collect(Collectors.toList());
        if(minPrice>0) filteredList = filteredList.stream().filter(child -> child.getPrice() >= minPrice).collect(Collectors.toList());
        if(maxPrice>0&&maxPrice>minPrice) filteredList = filteredList.stream().filter(child -> child.getPrice() <= maxPrice).collect(Collectors.toList());

        offerList = FXCollections.observableArrayList();
        for (Child child: filteredList) offerList.add(child);

    }

    /**
     * Metoda odebere nabidku z listu po pridani do kosiku podle indexu
     * @param index index nabidky
     */
    public void removeOffer(int index) {
        // odebrani prvku z listu dle indexu
        offerBackUpList.remove(index);
    }

    /**
     * Metoda odebere nabidku z listu po pridani do kosiku podle reference
     * @param child reference na konkretni nabidku
     */
    public void removeOffer(Child child) {
        // odebrani prvku z listu dle reference
        offerBackUpList.remove(child);
    }

    /**
     * Metoda pro vraceni konkretni nabidky
     * @param index index v listu
     * @return nabidka/dite
     */
    public Child getOfferDetail(int index) {
        // vraceni vybrane nabidky podle indexu
        return offerList.get(index);
    }

    public ObservableList<Child> getOfferList() {
        return offerList;
    }

    public void setOfferList(ObservableList<Child> offerList) {
        this.offerList = offerList;
    }

    public void addChild(Child child){
        offerBackUpList.add(child);
    }
}
