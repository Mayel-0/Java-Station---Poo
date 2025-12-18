package com.example.testjavastation;

import com.example.boutique.*;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.util.StringConverter;

/**
 * Application JavaFX pour la démo de la boutique.
 * Version adaptée : suppression de la MenuBar en haut et ajout d'un onglet Vendeur en bas.
 */
public class App extends Application {

    private Magasin magasin;
    private Client client;
    private Manager manager;
    private Vendeur vendeur;
    private Employe employeActif;

    private TableView<Jeu> stockTable;
    private ListView<Jeu> panierList;
    private Label totalLabel;
    private Label clientInfoLabel;

    private ObservableList<Jeu> stockObservable;
    private ObservableList<Jeu> panierObservable;

    private ObservableList<Client> clientsObservable;
    private ComboBox<Client> clientComboBox;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        initData();

        BorderPane root = new BorderPane();
        root.setPadding(new Insets(10));

        // Top: Client info and employee selector (no MenuBar at top)
        clientInfoLabel = new Label();
        updateClientInfo();

        ComboBox<String> employeCombo = new ComboBox<>();
        employeCombo.getItems().add("Aucun");
        if (vendeur != null) employeCombo.getItems().add("Vendeur - " + vendeur.getNom());
        if (manager != null) employeCombo.getItems().add("Manager - " + manager.getNom());
        employeCombo.getSelectionModel().selectFirst();

        clientComboBox = new ComboBox<>();
        clientsObservable = FXCollections.observableArrayList();
        client = new Client("Hugo", 150.00);
        clientsObservable.add(client);
        clientsObservable.add(new Client("Bob", 300.00));
        clientComboBox.setItems(clientsObservable);
        clientComboBox.setConverter(new StringConverter<>() {
            @Override
            public String toString(Client c) {
                if (c == null) return "Aucun";
                return String.format("%s (%.2f €)", c.getNom(), c.getSolde());
            }

            @Override
            public Client fromString(String string) { return null; }
        });
        clientComboBox.getSelectionModel().selectFirst();
        client = clientComboBox.getSelectionModel().getSelectedItem();

        Button newClientBtn = new Button("Nouveau client");
        newClientBtn.setOnAction(e -> {
            Client created = createClientDialog();
            if (created != null) {
                clientsObservable.add(created);
                clientComboBox.getSelectionModel().select(created);
                client = created;
                refreshPanier();
                updateTotalLabel();
                updateClientInfo();
            }
        });

        HBox topBox = new HBox(10);
        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);
        topBox.getChildren().addAll(clientInfoLabel, spacer, new Label("Connecté en:"), employeCombo, new Label("   "), new Label("Client:"), clientComboBox, newClientBtn);
        topBox.setAlignment(Pos.CENTER_LEFT);
        root.setTop(topBox);
        BorderPane.setMargin(topBox, new Insets(0,0,10,0));

        clientComboBox.setOnAction(evt -> {
            Client sel = clientComboBox.getSelectionModel().getSelectedItem();
            if (sel != null) {
                client = sel;
                refreshPanier();
                updateTotalLabel();
                updateClientInfo();
            }
        });

        // Center: stock table
        stockTable = new TableView<>();
        stockTable.setPrefWidth(600);

        TableColumn<Jeu, String> titreCol = new TableColumn<>("Titre");
        titreCol.setCellValueFactory(cell -> javafx.beans.property.SimpleStringProperty.stringExpression(
                javafx.beans.binding.Bindings.createStringBinding(cell.getValue()::getTitre)));
        titreCol.setPrefWidth(180);

        TableColumn<Jeu, String> typeCol = new TableColumn<>("Type");
        typeCol.setCellValueFactory(cell -> javafx.beans.property.SimpleStringProperty.stringExpression(
                javafx.beans.binding.Bindings.createStringBinding(cell.getValue()::getType)));
        typeCol.setPrefWidth(100);

        TableColumn<Jeu, String> specCol = new TableColumn<>("Console/Config/Année");
        specCol.setCellValueFactory(cell -> javafx.beans.property.SimpleStringProperty.stringExpression(
                javafx.beans.binding.Bindings.createStringBinding(() -> getSpec(cell.getValue()))));
        specCol.setPrefWidth(180);

        TableColumn<Jeu, String> prixCol = new TableColumn<>("Prix");
        prixCol.setCellValueFactory(cell -> javafx.beans.property.SimpleStringProperty.stringExpression(
                javafx.beans.binding.Bindings.createStringBinding(() -> String.format("%.2f €", cell.getValue().getPrix()))));
        prixCol.setPrefWidth(80);

        TableColumn<Jeu, String> promoCol = new TableColumn<>("Promo");
        promoCol.setCellValueFactory(cell -> javafx.beans.property.SimpleStringProperty.stringExpression(
                javafx.beans.binding.Bindings.createStringBinding(() -> cell.getValue().estEnPromo() ? "Oui" : "Non")));
        promoCol.setPrefWidth(60);

        stockTable.getColumns().addAll(titreCol, typeCol, specCol, prixCol, promoCol);

        stockObservable = FXCollections.observableArrayList(magasin.getJeuxEnStock());
        stockTable.setItems(stockObservable);

        root.setCenter(stockTable);

        // Right: panier
        VBox rightPane = new VBox(10);
        rightPane.setPadding(new Insets(0,0,0,10));
        rightPane.setPrefWidth(300);

        Label panierLabel = new Label("Panier :");
        panierList = new ListView<>();
        panierObservable = FXCollections.observableArrayList(client.getPanier().getContenu());
        panierList.setItems(panierObservable);
        panierList.setCellFactory(lv -> new ListCell<>() {
            @Override
            protected void updateItem(Jeu item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty || item == null ? null : item.getDetails());
            }
        });

        totalLabel = new Label();
        updateTotalLabel();

        Button addToCartBtn = new Button("Ajouter au panier");
        addToCartBtn.setOnAction(e -> onAddToCart());

        Button removeFromCartBtn = new Button("Retirer du panier");
        removeFromCartBtn.setOnAction(e -> onRemoveFromCart());

        Button payBtn = new Button("Payer");
        payBtn.setOnAction(e -> onPay());

        rightPane.getChildren().addAll(panierLabel, panierList, totalLabel, addToCartBtn, removeFromCartBtn, payBtn);
        root.setRight(rightPane);

        // Bottom: onglets employés (Manager + Vendeur)
        TabPane employeeTabs = new TabPane();

        // --- Manager Tab ---
        Tab managerTab = new Tab("Manager");
        GridPane mgrGrid = new GridPane();
        mgrGrid.setHgap(8);
        mgrGrid.setVgap(8);
        mgrGrid.setPadding(new Insets(8));

        TextField titreField = new TextField();
        TextField typeField = new TextField();
        TextField prixField = new TextField();
        ComboBox<String> categorieBox = new ComboBox<>(FXCollections.observableArrayList("Console","PC","Retro"));
        categorieBox.getSelectionModel().selectFirst();
        TextField specField = new TextField();

        mgrGrid.add(new Label("Titre:"), 0, 0);
        mgrGrid.add(titreField, 1, 0);
        mgrGrid.add(new Label("Type:"), 0, 1);
        mgrGrid.add(typeField, 1, 1);
        mgrGrid.add(new Label("Prix:"), 0, 2);
        mgrGrid.add(prixField, 1, 2);
        mgrGrid.add(new Label("Catégorie:"), 0, 3);
        mgrGrid.add(categorieBox, 1, 3);
        mgrGrid.add(new Label("Console/Config/Année:"), 0, 4);
        mgrGrid.add(specField, 1, 4);

        Button addStockBtn = new Button("Ajouter au stock");
        addStockBtn.setOnAction(e -> {
            if (!(employeActif instanceof Manager)) { showAlert("Accès refusé","Connectez-vous en tant que manager pour ajouter un jeu."); return; }
            try {
                String t = titreField.getText().trim();
                String tp = typeField.getText().trim();
                double p = Double.parseDouble(prixField.getText().trim());
                String cat = categorieBox.getValue();
                String spec = specField.getText().trim();
                Jeu newJeu;
                if (cat.equals("Console")) newJeu = new JeuConsole(t, tp, p, spec);
                else if (cat.equals("PC")) newJeu = new JeuPC(t, tp, p, spec);
                else {
                    int an = Integer.parseInt(spec);
                    newJeu = new JeuRetro(t, tp, p, an);
                }
                manager.ajouterNouveauJeu(newJeu, magasin);
                refreshStock();
                titreField.clear(); typeField.clear(); prixField.clear(); specField.clear();
            } catch (NumberFormatException ex) {
                showAlert("Erreur", "Prix ou année invalide.");
            }
        });

        Button removeStockBtn = new Button("Supprimer du stock");
        removeStockBtn.setOnAction(e -> {
            if (!(employeActif instanceof Manager)) { showAlert("Accès refusé","Connectez-vous en tant que manager pour supprimer un jeu."); return; }
            Jeu sel = stockTable.getSelectionModel().getSelectedItem();
            if (sel == null) { showAlert("Erreur","Sélectionnez un jeu dans le rayon."); return; }
            manager.retirerJeuDuRayon(sel, magasin);
            refreshStock();
            refreshPanier();
        });

        mgrGrid.add(addStockBtn, 0, 6);
        mgrGrid.add(removeStockBtn, 1, 6);

        managerTab.setContent(mgrGrid);
        managerTab.setClosable(false);

        // --- Vendeur Tab ---
        Tab vendeurTab = new Tab("Vendeur");
        GridPane vendGrid = new GridPane();
        vendGrid.setHgap(8);
        vendGrid.setVgap(8);
        vendGrid.setPadding(new Insets(8));

        // UI simplifiée : seul le champ pourcentage et le bouton d'application de la promo
        TextField promoField = new TextField();
        promoField.setPromptText("Pourcentage (ex: 20)");
        Button applyPromoBtn = new Button("Appliquer promo au jeu sélectionné");
        applyPromoBtn.setOnAction(e -> {
            if (!(employeActif instanceof Vendeur)) { showAlert("Accès refusé","Connectez-vous en tant que vendeur pour appliquer une promo."); return; }
            Jeu sel = stockTable.getSelectionModel().getSelectedItem();
            if (sel == null) { showAlert("Erreur","Sélectionnez un jeu dans le rayon."); return; }
            try {
                double pct = Double.parseDouble(promoField.getText().trim());
                if (pct <= 0 || pct > 100) { showAlert("Erreur","Pourcentage invalide (doit être >0 et ≤100)."); return; }
                ((Vendeur) employeActif).appliquerPromo(sel, pct);
                refreshStock();
                stockTable.refresh();
                refreshPanier();
                updateTotalLabel();
                showAlert("Succès","Promotion appliquée : " + pct + "% pour " + sel.getTitre());
                promoField.clear();
            } catch (NumberFormatException ex) {
                showAlert("Erreur","Pourcentage non numérique.");
            }
        });

        vendGrid.add(new Label("Promo %:"), 0, 0);
        vendGrid.add(promoField, 1, 0);
        vendGrid.add(applyPromoBtn, 1, 1);

        vendeurTab.setContent(vendGrid);
        vendeurTab.setClosable(false);

        employeeTabs.getTabs().addAll(managerTab, vendeurTab);
        // Par défaut : ne montrer aucun onglet tant qu'aucun employé n'est connecté
        employeeTabs.getTabs().clear();
        root.setBottom(employeeTabs);

        // Activer/afficher uniquement l'onglet correspondant selon l'employé sélectionné
        employeCombo.setOnAction(evt -> {
            String sel = employeCombo.getSelectionModel().getSelectedItem();
            if (sel == null || sel.equals("Aucun")) {
                // Aucun : ne montrer aucun onglet
                employeActif = null;
                employeeTabs.getTabs().clear();
            } else if (sel.startsWith("Vendeur")) {
                // Vendeur : ne montrer que l'onglet Vendeur
                employeActif = vendeur;
                employeeTabs.getTabs().setAll(vendeurTab);
                vendeurTab.setDisable(false);
                employeeTabs.getSelectionModel().select(vendeurTab);
            } else if (sel.startsWith("Manager")) {
                // Manager : ne montrer que l'onglet Manager
                employeActif = manager;
                employeeTabs.getTabs().setAll(managerTab);
                managerTab.setDisable(false);
                employeeTabs.getSelectionModel().select(managerTab);
            }
        });

        Scene scene = new Scene(root, 1000, 600);
        primaryStage.setTitle("Boutique de jeux - Démo");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void initData() {
        magasin = new Magasin("La Console", "1 rue du Jeu");

        Jeu j1 = new JeuConsole("Zelda: Aventure", "Aventure", 59.99, "Switch");
        Jeu j2 = new JeuPC("CyberZone", "FPS", 39.99, "GTX 1060, 8GB RAM");
        Jeu j3 = new JeuRetro("SuperMario", "Plateforme", 29.99, 1990);
        magasin.ajouterJeu(j1);
        magasin.ajouterJeu(j2);
        magasin.ajouterJeu(j3);

        // clients initialisés ici pour être sûrs qu'ils existent avant l'UI
        clientsObservable = FXCollections.observableArrayList();
        client = new Client("Alice", 100.00);
        clientsObservable.add(client);
        clientsObservable.add(new Client("Bob", 20.00));

        // employés
        vendeur = new Vendeur("Paul", 1);
        manager = new Manager("Sophie", 2);
        magasin.embaucherEmploye(vendeur);
        magasin.embaucherEmploye(manager);
    }

    private String getSpec(Jeu j) {
        if (j instanceof JeuConsole) return ((JeuConsole) j).getConsole();
        if (j instanceof JeuPC) return ((JeuPC) j).getConfigRequise();
        if (j instanceof JeuRetro) return Integer.toString(((JeuRetro) j).getAnneeSortie());
        return "";
    }

    private void onAddToCart() {
        Jeu sel = stockTable.getSelectionModel().getSelectedItem();
        if (sel == null) { showAlert("Erreur","Sélectionnez un jeu dans le rayon."); return; }
        if (client == null) { showAlert("Erreur","Sélectionnez ou créez un client."); return; }
        client.ajouterAuPanier(sel);
        refreshPanier();
        updateTotalLabel();
    }

    private void onRemoveFromCart() {
        Jeu sel = panierList.getSelectionModel().getSelectedItem();
        if (sel == null) { showAlert("Erreur","Sélectionnez un jeu dans le panier."); return; }
        client.retirerDuPanier(sel);
        refreshPanier();
        updateTotalLabel();
    }

    private void onPay() {
        if (client == null) { showAlert("Erreur","Sélectionnez un client."); return; }
        // Exiger qu'un vendeur soit connecté pour encaisser
        if (!(employeActif instanceof Vendeur)) {
            showAlert("Accès refusé","Connectez-vous en tant que vendeur pour encaisser.");
            return;
        }
        boolean ok = ((Vendeur) employeActif).encaisser(client);
        if (ok) {
            showAlert("Succès","Paiement accepté. Merci pour votre achat !");
            refreshPanier();
            updateTotalLabel();
            updateClientInfo();
        } else {
            showAlert("Échec","Solde insuffisant.");
        }
    }

    private void refreshStock() {
        stockObservable.setAll(magasin.getJeuxEnStock());
    }

    private void refreshPanier() {
        if (client != null) panierObservable.setAll(client.getPanier().getContenu());
        else panierObservable.clear();
    }

    private void updateTotalLabel() {
        double total = client == null ? 0.0 : client.getPanier().calculerTotal();
        totalLabel.setText(String.format("Total à payer: %.2f €", total));
    }

    private void updateClientInfo() {
        if (client != null) clientInfoLabel.setText(String.format("Client : %s | Solde : %.2f €", client.getNom(), client.getSolde()));
        else clientInfoLabel.setText("Client : Aucun");
    }

    private Client createClientDialog() {
        Dialog<Client> dialog = new Dialog<>();
        dialog.setTitle("Créer un nouveau client");
        dialog.setHeaderText(null);

        ButtonType createButtonType = new ButtonType("Créer", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(createButtonType, ButtonType.CANCEL);

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));

        TextField nameField = new TextField();
        nameField.setPromptText("Nom");
        TextField soldeField = new TextField();
        soldeField.setPromptText("Solde (ex: 50.0)");

        grid.add(new Label("Nom:"), 0, 0);
        grid.add(nameField, 1, 0);
        grid.add(new Label("Solde:"), 0, 1);
        grid.add(soldeField, 1, 1);

        dialog.getDialogPane().setContent(grid);

        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == createButtonType) {
                String name = nameField.getText().trim();
                String solStr = soldeField.getText().trim();
                if (name.isEmpty()) { showAlert("Erreur","Le nom ne peut pas être vide."); return null; }
                try { double s = Double.parseDouble(solStr); return new Client(name, s); }
                catch (NumberFormatException ex) { showAlert("Erreur","Solde invalide."); return null; }
            }
            return null;
        });

        return dialog.showAndWait().orElse(null);
    }

    private void showAlert(String titre, String message) {
        Alert a = new Alert(Alert.AlertType.INFORMATION);
        a.setTitle(titre);
        a.setHeaderText(null);
        a.setContentText(message);
        a.showAndWait();
    }
}
