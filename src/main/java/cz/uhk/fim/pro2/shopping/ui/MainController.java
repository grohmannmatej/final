package cz.uhk.fim.pro2.shopping.ui;

import cz.uhk.fim.pro2.shopping.model.Child;
import cz.uhk.fim.pro2.shopping.model.GenderType;
import cz.uhk.fim.pro2.shopping.model.Marketplace;
import cz.uhk.fim.pro2.shopping.model.ShoppingCart;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.awt.event.MouseAdapter;
import java.net.URL;
import java.time.ZoneId;
import java.util.List;
import java.util.ResourceBundle;

public class MainController implements Initializable {

//    @FXML
//    public Pane pnlColor;
//    @FXML
//    public Label lblNameAndAge;
//    @FXML
//    public Label lblPrice;
//    @FXML
//    public TextArea txtLog;

    @FXML
    public Label lblDisplayName;
    @FXML
    public Label lblPersonalId;
    @FXML
    public Label lblAge;
    @FXML
    public Label lblWeight;
    @FXML
    public Slider sldWeight;
    @FXML
    public Label lblNationality;
    @FXML
    public CheckBox checkboxTrueRace;
    @FXML
    public DatePicker dtBirthDate;
    @FXML
    public Label lblPrice;
    @FXML
    public TextArea txtDescription;

    @FXML
    public TableView<Child> offerTable;
    @FXML
    public TableColumn<Child, String> colId;
    @FXML
    public TableColumn<Child, String> colName;
    @FXML
    public TableColumn<Child, Integer> colAge;
    @FXML
    public TableColumn<Child, GenderType> colGender;
    @FXML
    public TableColumn<Child, Double> colPrice;
    @FXML
    public Tab tabDetail;
    @FXML
    public ImageView Gender;
    @FXML
    public ImageView Virginity;
    @FXML
    public ImageView Avatar;

    @FXML
    public ChoiceBox genderFilterBox;
    @FXML
    public TextField minPriceValue;
    @FXML
    public TextField maxPriceValue;
    @FXML
    public TextField minAgeValue;
    @FXML
    public TextField maxAgeValue;

    @FXML
    public Button loadMarketButton;

    @FXML
    public Button resetFiltersButton;

    private ShoppingCart cart;
    private Marketplace marketplace;
    private List<Child> childsToBuy;
    private Child currentChild;

    private int minAge;
    private int maxAge;
    private double minPrice;
    private double maxPrice;
    private GenderType gender;

    public void filter(){
        marketplace.filter(minAge,maxAge,minPrice,maxPrice,gender);

        initUi();
    }


    public void onSkipButtonClick() {
        this.currentChild = this.childsToBuy.get(this.childsToBuy.indexOf(this.currentChild) + 1);
        updateUi();
    }

    public void addToShoppingCart() {
        System.out.println(this.offerTable.getSelectionModel().getSelectedItem());
    }

    public void onCheckCartButtonClick() {
        StringBuilder sb = new StringBuilder();
        for (Child child : this.cart.getChildList()) {
            sb.append(child).append("\n");
        }
//        txtLog.setText(sb.toString());
    }

    private void initTableView() {
        this.colId.setCellValueFactory(cellData -> cellData.getValue().getPersonalIdProperty());
        this.colName.setCellValueFactory(cellData -> cellData.getValue().getDisplayNameProperty());
        this.colAge.setCellValueFactory(cellData -> cellData.getValue().getAgeProperty().asObject());
        this.colGender.setCellValueFactory(cellData -> cellData.getValue().getGenderProperty());
        this.colPrice.setCellValueFactory(cellData -> cellData.getValue().getPriceProperty().asObject());

        this.offerTable.setItems(this.marketplace.getOfferList());

        this.offerTable.getSelectionModel().getSelectedCells().addListener(new ListChangeListener<TablePosition>() {

            @Override
            public void onChanged(Change<? extends TablePosition> change) {
                ObservableList selectedCells = offerTable.getSelectionModel().getSelectedCells();
                TablePosition tablePosition = (TablePosition) selectedCells.get(0);
                currentChild = marketplace.getOfferList().get(tablePosition.getRow());
                updateUi();
            }
        });
    }

    private void initListeners(){
        initFilterListeners();
        initButtonListeners();
    }

    private void initButtonListeners(){
        loadMarketButton.setOnAction(new EventHandler<ActionEvent>()
        {
            @Override public void handle(ActionEvent e)
            {
                initMarketplace();
                filter();
                initUi();
            }
        });

        resetFiltersButton.setOnAction(new EventHandler<ActionEvent>()
        {
            @Override public void handle(ActionEvent e)
            {
                initFilterVariables();




                filter();
            }
        });
    }

    private void initFilterListeners() {

        genderFilterBox.setItems(FXCollections.observableArrayList("Vše","Chlapci", "Dívky"));

        genderFilterBox.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number number, Number number2) {
                if((int) number2 == 1) gender = GenderType.MALE;
                if((int) number2 == 2) gender = GenderType.FEMALE;
                if((int) number2 == 0) gender = GenderType.ALL;
                filter();
            }
        });

        minAgeValue.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue,
                                String newValue) {
                if (!newValue.matches("\\d*")) {
                    minAgeValue.setText(newValue.replaceAll("[^\\d]", ""));
                }
                else if(newValue != "")
                minAge = Integer.parseInt(newValue);
                else minAge = -1;
                filter();
            }
        });


        maxAgeValue.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue,
                                String newValue) {
                if (!newValue.matches("\\d*")) {
                    maxAgeValue.setText(newValue.replaceAll("[^\\d]", ""));
                }

                else if(newValue != "")
                maxAge = Integer.parseInt(newValue);
                else maxAge = -1;
                filter();
            }
        });

        minPriceValue.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue,
                                String newValue) {
                if (!newValue.matches("\\d*")) {
                    minPriceValue.setText(newValue.replaceAll("[^\\d]", ""));
                }
                else if(newValue != "")
                minPrice = Double.parseDouble(newValue);
                else minPrice = -1;
                filter();
            }
        });

        maxPriceValue.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue,
                                String newValue) {
                if (!newValue.matches("\\d*")) {
                    maxPriceValue.setText(newValue.replaceAll("[^\\d]", ""));
                }
                else if(newValue != "")
                maxPrice = Double.parseDouble(newValue);
                else maxPrice = -1;
                filter();
            }
        });


    }

    private void initMarketplace() {
        this.marketplace = new Marketplace();
    }

    private void updateUi() {
        this.tabDetail.setDisable(false);
        this.lblDisplayName.setText(this.currentChild.getDisplayName());
        this.lblPersonalId.setText(String.format("(%s)", this.currentChild.getPersonalId()));
        this.lblPrice.setText(String.format("%.2f", this.currentChild.getPrice()));
        this.lblAge.setText(String.valueOf(this.currentChild.getAge()));
        this.dtBirthDate.setValue(this.currentChild
                .getBirthDate()
                .toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDate());
        this.lblWeight.setText(String.format("%.1f kg", this.currentChild.getWeight()));
        this.sldWeight.setValue(this.currentChild.getWeight());
        this.checkboxTrueRace.setSelected(!this.currentChild.isRace());

        Gender.setImage(this.currentChild.getImgGender());
        Virginity.setImage(this.currentChild.getImgVirginity());
        Avatar.setImage(this.currentChild.getAvatar());
        lblNationality.setText(this.currentChild.getNationality());
    }

    private void initUi() {
        initTableView();
        tabDetail.setDisable(true);
        sldWeight.setDisable(true);
        checkboxTrueRace.setDisable(true);
        dtBirthDate.setDisable(true);
    }

    private void initCart() {
        this.cart = new ShoppingCart();
        this.cart.setVat(0.21);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initCart();
        initMarketplace();
        initUi();
        initListeners();
        initFilterVariables();
    }

    public void initFilterVariables(){
        minAge = -1;
        maxAge = -1;
        minPrice = -1;
        maxPrice = -1;
        gender = GenderType.ALL;
        genderFilterBox.setValue("Vše");
        minAgeValue.setText("");
        maxAgeValue.setText("");
        minPriceValue.setText("");
        maxPriceValue.setText("");
    }
}
