import javafx.animation.FadeTransition;
import javafx.animation.ScaleTransition;
import javafx.application.Application;
import javafx.beans.binding.Bindings;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class Main extends Application {

    private final ObservableList<GradeEntry> masterData =
            FXCollections.observableArrayList();

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Student Grade Manager");

        BorderPane root = new BorderPane();
        root.setPadding(new Insets(20));

        // ---------- HEADER ----------
        Label title = new Label("Student Grade Manager");
        title.getStyleClass().add("title-label");

        Label subtitle = new Label("Track students, assignments, and grades with a clean, modern UI.");
        subtitle.getStyleClass().add("subtitle-label");

        VBox headerBox = new VBox(5, title, subtitle);
        headerBox.setAlignment(Pos.CENTER);
        headerBox.setPadding(new Insets(10, 0, 30, 0));

        root.setTop(headerBox);

        // ---------- CENTER CONTENT ----------
        VBox centerBox = new VBox(20);
        centerBox.setFillWidth(true);

        // form card + table card
        HBox topRow = new HBox(20);
        topRow.setAlignment(Pos.TOP_CENTER);

        VBox formCard = createFormCard();
        VBox summaryCard = createSummaryCard();

        topRow.getChildren().addAll(formCard, summaryCard);

        VBox tableCard = createTableCard();

        centerBox.getChildren().addAll(topRow, tableCard);

        root.setCenter(centerBox);

        Scene scene = new Scene(root, 1150, 700);
        scene.getStylesheets().add(getClass().getResource("styles.css").toExternalForm());

        // Fade-in animation for whole UI
        root.setOpacity(0);
        FadeTransition fadeIn = new FadeTransition(Duration.millis(900), root);
        fadeIn.setFromValue(0);
        fadeIn.setToValue(1);
        fadeIn.play();

        primaryStage.setScene(scene);
        primaryStage.show();
    }

    // ---------- FORM CARD ----------
    private VBox createFormCard() {
        VBox card = new VBox(15);
        card.getStyleClass().add("card");
        card.setPrefWidth(500);

        Label heading = new Label("Add / Edit Entry");
        heading.getStyleClass().add("card-title");

        // form fields
        TextField tfId = new TextField();
        tfId.setPromptText("e.g. 12345");

        TextField tfName = new TextField();
        tfName.setPromptText("e.g. Alex Johnson");

        TextField tfCourse = new TextField();
        tfCourse.setPromptText("e.g. CSCE 146");

        TextField tfItem = new TextField();
        tfItem.setPromptText("e.g. Midterm Exam");

        ComboBox<String> cbCategory = new ComboBox<>();
        cbCategory.getItems().addAll("Homework", "Quiz", "Exam", "Project", "Participation", "Other");
        cbCategory.setValue("Exam");

        TextField tfScore = new TextField();
        tfScore.setPromptText("Score (e.g. 87)");

        TextField tfMaxScore = new TextField();
        tfMaxScore.setPromptText("Max (e.g. 100)");

        GridPane grid = new GridPane();
        grid.setHgap(12);
        grid.setVgap(10);

        int r = 0;
        grid.add(new Label("Student ID"), 0, r);
        grid.add(tfId, 1, r++);

        grid.add(new Label("Student Name"), 0, r);
        grid.add(tfName, 1, r++);

        grid.add(new Label("Course"), 0, r);
        grid.add(tfCourse, 1, r++);

        grid.add(new Label("Item Name"), 0, r);
        grid.add(tfItem, 1, r++);

        grid.add(new Label("Category"), 0, r);
        grid.add(cbCategory, 1, r++);

        grid.add(new Label("Score"), 0, r);
        grid.add(tfScore, 1, r++);

        grid.add(new Label("Max Score"), 0, r);
        grid.add(tfMaxScore, 1, r);

        // Buttons
        Button btnAddUpdate = new Button("Add / Update");
        Button btnClear = new Button("Clear Form");

        HBox btnRow = new HBox(10, btnAddUpdate, btnClear);
        btnRow.setAlignment(Pos.CENTER_RIGHT);

        card.getChildren().addAll(heading, grid, btnRow);

        // store on card as user data so table / handlers can access later
        card.setUserData(new FormFields(tfId, tfName, tfCourse, tfItem, cbCategory, tfScore, tfMaxScore, btnAddUpdate, btnClear));

        addCardShadow(card);

        return card;
    }

    // ---------- SUMMARY CARD ----------
    private VBox createSummaryCard() {
        VBox card = new VBox(15);
        card.getStyleClass().add("card");
        card.setPrefWidth(350);

        Label heading = new Label("Student Summary");
        heading.getStyleClass().add("card-title");

        Label lblSelectedStudent = new Label("No student selected.");
        Label lblAverage = new Label("Average: —");
        Label lblBest = new Label("Best Category: —");

        lblSelectedStudent.getStyleClass().add("summary-label");
        lblAverage.getStyleClass().add("summary-label");
        lblBest.getStyleClass().add("summary-label");

        card.getChildren().addAll(heading, lblSelectedStudent, lblAverage, lblBest);

        // store labels on card for later access
        card.setUserData(new SummaryLabels(lblSelectedStudent, lblAverage, lblBest));

        addCardShadow(card);
        return card;
    }

    // ---------- TABLE CARD ----------
    private VBox createTableCard() {
        VBox card = new VBox(15);
        card.getStyleClass().add("card");

        Label heading = new Label("Grade Records");
        heading.getStyleClass().add("card-title");

        // Search bar + export
        TextField tfSearch = new TextField();
        tfSearch.setPromptText("Search by student name, ID, or course...");
        tfSearch.setPrefWidth(300);

        Button btnExport = new Button("Export CSV");
        HBox topRow = new HBox(10, tfSearch, btnExport);
        topRow.setAlignment(Pos.CENTER_LEFT);

        TableView<GradeEntry> table = new TableView<>();
        table.setPrefHeight(350);

        // columns
        TableColumn<GradeEntry, String> colId = new TableColumn<>("ID");
        colId.setCellValueFactory(new PropertyValueFactory<>("studentId"));
        colId.setPrefWidth(80);

        TableColumn<GradeEntry, String> colName = new TableColumn<>("Name");
        colName.setCellValueFactory(new PropertyValueFactory<>("studentName"));
        colName.setPrefWidth(180);

        TableColumn<GradeEntry, String> colCourse = new TableColumn<>("Course");
        colCourse.setCellValueFactory(new PropertyValueFactory<>("course"));
        colCourse.setPrefWidth(100);

        TableColumn<GradeEntry, String> colItem = new TableColumn<>("Item");
        colItem.setCellValueFactory(new PropertyValueFactory<>("itemName"));
        colItem.setPrefWidth(160);

        TableColumn<GradeEntry, String> colCat = new TableColumn<>("Category");
        colCat.setCellValueFactory(new PropertyValueFactory<>("category"));
        colCat.setPrefWidth(100);

        TableColumn<GradeEntry, Number> colScore = new TableColumn<>("Score");
        colScore.setCellValueFactory(new PropertyValueFactory<>("score"));
        colScore.setPrefWidth(80);

        TableColumn<GradeEntry, Number> colMax = new TableColumn<>("Max");
        colMax.setCellValueFactory(new PropertyValueFactory<>("maxScore"));
        colMax.setPrefWidth(80);

        TableColumn<GradeEntry, String> colPercent = new TableColumn<>("%");
        colPercent.setCellValueFactory(c ->
                new ReadOnlyObjectWrapper<>(String.format("%.1f", c.getValue().getPercent())));
        colPercent.setPrefWidth(70);

        TableColumn<GradeEntry, String> colLetter = new TableColumn<>("Letter");
        colLetter.setCellValueFactory(c ->
                new ReadOnlyObjectWrapper<>(c.getValue().getLetter()));
        colLetter.setPrefWidth(70);

        table.getColumns().addAll(colId, colName, colCourse, colItem, colCat, colScore, colMax, colPercent, colLetter);

        // Filtered list for search
        FilteredList<GradeEntry> filtered = new FilteredList<>(masterData, p -> true);
        tfSearch.textProperty().addListener((obs, oldV, newV) -> {
            String q = newV == null ? "" : newV.trim().toLowerCase();
            filtered.setPredicate(entry -> {
                if (q.isEmpty()) return true;
                return entry.getStudentId().toLowerCase().contains(q) ||
                        entry.getStudentName().toLowerCase().contains(q) ||
                        entry.getCourse().toLowerCase().contains(q);
            });
        });

        table.setItems(filtered);

        // double-click row -> put into form for editing
        table.setRowFactory(tv -> {
            TableRow<GradeEntry> row = new TableRow<>();
            row.setOnMouseClicked(ev -> {
                if (ev.getClickCount() == 2 && !row.isEmpty()) {
                    GradeEntry entry = row.getItem();
                    populateFormFromEntry(entry);
                }
            });
            return row;
        });

        // when selection changes -> update summary card
        table.getSelectionModel().selectedItemProperty().addListener((obs, oldSel, newSel) -> {
            updateSummaryFromSelection(newSel);
        });

        // Export CSV
        btnExport.setOnAction(e -> exportToCsv(table.getScene().getWindow()));

        card.getChildren().addAll(heading, topRow, table);
        addCardShadow(card);

        // Save reference to table in root userData so add/update handlers can reach it
        card.setUserData(table);

        // After building everything, wire form buttons
        hookUpFormButtons(table);

        return card;
    }

    // ---------- WIRING FORM BUTTONS ----------
    private void hookUpFormButtons(TableView<GradeEntry> table) {
        // Find the form card & summary card from the scene graph
        BorderPane root = (BorderPane) table.getScene().getRoot();
        VBox center = (VBox) root.getCenter();
        HBox topRow = (HBox) center.getChildren().get(0);

        VBox formCard = (VBox) topRow.getChildren().get(0);
        VBox summaryCard = (VBox) topRow.getChildren().get(1);

        FormFields f = (FormFields) formCard.getUserData();
        SummaryLabels s = (SummaryLabels) summaryCard.getUserData();

        Button btnAddUpdate = f.btnAddUpdate;
        Button btnClear = f.btnClear;

        btnAddUpdate.setOnAction(e -> {
            String id = f.tfId.getText().trim();
            String name = f.tfName.getText().trim();
            String course = f.tfCourse.getText().trim();
            String item = f.tfItem.getText().trim();
            String category = f.cbCategory.getValue();
            String scoreStr = f.tfScore.getText().trim();
            String maxStr = f.tfMaxScore.getText().trim();

            if (id.isEmpty() || name.isEmpty() || course.isEmpty() || item.isEmpty()
                    || scoreStr.isEmpty() || maxStr.isEmpty()) {
                showAlert(Alert.AlertType.WARNING, "Missing Data",
                        "Please fill in all fields before adding or updating.");
                return;
            }

            double score, max;
            try {
                score = Double.parseDouble(scoreStr);
                max = Double.parseDouble(maxStr);
            } catch (NumberFormatException ex) {
                showAlert(Alert.AlertType.ERROR, "Invalid Numbers",
                        "Score and Max Score must be numeric.");
                return;
            }

            if (max <= 0) {
                showAlert(Alert.AlertType.ERROR, "Invalid Max Score",
                        "Max Score must be greater than 0.");
                return;
            }

            // If row selected, update it; else add new
            GradeEntry selected = table.getSelectionModel().getSelectedItem();
            if (selected != null &&
                    selected.getStudentId().equals(id) &&
                    selected.getItemName().equals(item)) {
                selected.setStudentName(name);
                selected.setCourse(course);
                selected.setCategory(category);
                selected.setScore(score);
                selected.setMaxScore(max);
                table.refresh();
            } else {
                GradeEntry entry = new GradeEntry(id, name, course, item, category, score, max);
                masterData.add(entry);
                table.getSelectionModel().select(entry);
            }

            animateTable(table);
            updateSummaryFromSelection(table.getSelectionModel().getSelectedItem());
        });

        btnClear.setOnAction(e -> {
            f.tfId.clear();
            f.tfName.clear();
            f.tfCourse.clear();
            f.tfItem.clear();
            f.tfScore.clear();
            f.tfMaxScore.clear();
            f.cbCategory.setValue("Exam");
            table.getSelectionModel().clearSelection();

            s.lblSelectedStudent.setText("No student selected.");
            s.lblAverage.setText("Average: —");
            s.lblBest.setText("Best Category: —");
        });
    }

    private void populateFormFromEntry(GradeEntry entry) {
        BorderPane root = (BorderPane) getPrimaryRoot();
        VBox center = (VBox) root.getCenter();
        HBox topRow = (HBox) center.getChildren().get(0);
        VBox formCard = (VBox) topRow.getChildren().get(0);
        FormFields f = (FormFields) formCard.getUserData();

        f.tfId.setText(entry.getStudentId());
        f.tfName.setText(entry.getStudentName());
        f.tfCourse.setText(entry.getCourse());
        f.tfItem.setText(entry.getItemName());
        f.cbCategory.setValue(entry.getCategory());
        f.tfScore.setText(String.valueOf(entry.getScore()));
        f.tfMaxScore.setText(String.valueOf(entry.getMaxScore()));
    }

    private void updateSummaryFromSelection(GradeEntry selected) {
        BorderPane root = (BorderPane) getPrimaryRoot();
        if (root == null) return;

        VBox center = (VBox) root.getCenter();
        HBox topRow = (HBox) center.getChildren().get(0);
        VBox summaryCard = (VBox) topRow.getChildren().get(1);
        SummaryLabels s = (SummaryLabels) summaryCard.getUserData();

        if (selected == null) {
            s.lblSelectedStudent.setText("No student selected.");
            s.lblAverage.setText("Average: —");
            s.lblBest.setText("Best Category: —");
            return;
        }

        String id = selected.getStudentId();

        List<GradeEntry> forStudent = masterData.stream()
                .filter(e -> e.getStudentId().equals(id))
                .collect(Collectors.toList());

        if (forStudent.isEmpty()) return;

        double avg = forStudent.stream()
                .mapToDouble(GradeEntry::getPercent)
                .average().orElse(0);

        // compute best category by average percent
        var byCategory = forStudent.stream()
                .collect(Collectors.groupingBy(GradeEntry::getCategory,
                        Collectors.averagingDouble(GradeEntry::getPercent)));

        String bestCat = byCategory.entrySet().stream()
                .max(Comparator.comparingDouble(e -> e.getValue()))
                .map(e -> e.getKey() + String.format(" (%.1f%%)", e.getValue()))
                .orElse("—");

        s.lblSelectedStudent.setText(
                selected.getStudentName() + "  [" + selected.getStudentId() + "]");
        s.lblAverage.setText(String.format("Average: %.2f%% (%s)",
                avg, GradeEntry.letterForPercent(avg)));
        s.lblBest.setText("Best Category: " + bestCat);
    }

    // ---------- EXPORT ----------
    private void exportToCsv(javafx.stage.Window owner) {
        if (masterData.isEmpty()) {
            showAlert(Alert.AlertType.INFORMATION, "Nothing to Export",
                    "There are no grade records yet.");
            return;
        }

        FileChooser chooser = new FileChooser();
        chooser.setTitle("Export Grades to CSV");
        chooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("CSV Files", "*.csv"));
        chooser.setInitialFileName("grades_export.csv");
        File file = chooser.showSaveDialog(owner);
        if (file == null) return;

        try (FileWriter fw = new FileWriter(file)) {
            fw.write("Student ID,Student Name,Course,Item,Category,Score,Max,Percent,Letter\n");
            for (GradeEntry e : masterData) {
                fw.write(String.format("%s,%s,%s,%s,%s,%.2f,%.2f,%.2f,%s\n",
                        escapeCsv(e.getStudentId()),
                        escapeCsv(e.getStudentName()),
                        escapeCsv(e.getCourse()),
                        escapeCsv(e.getItemName()),
                        escapeCsv(e.getCategory()),
                        e.getScore(),
                        e.getMaxScore(),
                        e.getPercent(),
                        e.getLetter()));
            }
            showAlert(Alert.AlertType.INFORMATION, "Export Complete",
                    "CSV exported to:\n" + file.getAbsolutePath());
        } catch (IOException ex) {
            showAlert(Alert.AlertType.ERROR, "Export Failed",
                    "Could not write file:\n" + ex.getMessage());
        }
    }

    private String escapeCsv(String s) {
        if (s.contains(",") || s.contains("\"")) {
            return "\"" + s.replace("\"", "\"\"") + "\"";
        }
        return s;
    }

    // ---------- UTIL ----------
    private void showAlert(Alert.AlertType type, String title, String message) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void animateTable(TableView<GradeEntry> table) {
        ScaleTransition st = new ScaleTransition(Duration.millis(150), table);
        st.setFromX(1.0);
        st.setFromY(1.0);
        st.setToX(1.02);
        st.setToY(1.02);
        st.setAutoReverse(true);
        st.setCycleCount(2);
        st.play();
    }

    private void addCardShadow(Region card) {
        DropShadow shadow = new DropShadow();
        shadow.setRadius(15);
        shadow.setOffsetX(0);
        shadow.setOffsetY(8);
        shadow.setColor(Color.rgb(0, 0, 0, 0.35));
        card.setEffect(shadow);
    }

    private Region getPrimaryRoot() {
        // helper to grab root from any control in scene
        for (Stage stage : Stage.getWindows().stream()
                .filter(w -> w instanceof Stage)
                .map(w -> (Stage) w)
                .collect(Collectors.toList())) {
            if (stage.getScene() != null && stage.getScene().getRoot() != null) {
                return stage.getScene().getRoot();
            }
        }
        return null;
    }

    // small holder classes for form and summary controls
    private static class FormFields {
        final TextField tfId, tfName, tfCourse, tfItem, tfScore, tfMaxScore;
        final ComboBox<String> cbCategory;
        final Button btnAddUpdate, btnClear;

        FormFields(TextField tfId, TextField tfName, TextField tfCourse,
                   TextField tfItem, ComboBox<String> cbCategory,
                   TextField tfScore, TextField tfMaxScore,
                   Button btnAddUpdate, Button btnClear) {
            this.tfId = tfId;
            this.tfName = tfName;
            this.tfCourse = tfCourse;
            this.tfItem = tfItem;
            this.cbCategory = cbCategory;
            this.tfScore = tfScore;
            this.tfMaxScore = tfMaxScore;
            this.btnAddUpdate = btnAddUpdate;
            this.btnClear = btnClear;
        }
    }

    private static class SummaryLabels {
        final Label lblSelectedStudent, lblAverage, lblBest;

        SummaryLabels(Label s, Label a, Label b) {
            this.lblSelectedStudent = s;
            this.lblAverage = a;
            this.lblBest = b;
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}