package View;

import Controller.Controller;
import Model.Exceptions.MyException;
import Model.PrgState;
import Model.Values.StringValue;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.collections.FXCollections;
import javafx.scene.control.cell.PropertyValueFactory;
import Model.Values.IValue;

import java.util.List;
import java.util.Map;

import javafx.scene.layout.Priority;

public class MainWindow {
    private final Controller controller;

    public MainWindow(Controller controller) {
        this.controller = controller;
    }

    public void show() {
        Stage stage = new Stage();

        Label prgLabel = new Label("Number of Program States: ");
        TextField prgStatesField = new TextField();
        prgStatesField.setEditable(false);

        // a)
        int numberOfPrgStates = controller.getRepository().getPrgList().size();
        prgStatesField.setText(String.valueOf(numberOfPrgStates));

        // b)
        TableView<Map.Entry<Integer, IValue>> heapTable = new TableView<>();
        TableColumn<Map.Entry<Integer, IValue>, Integer> addressColumn = new TableColumn<>("Address");
        addressColumn.setCellValueFactory(cellData ->
                new javafx.beans.property.SimpleIntegerProperty(
                        cellData.getValue().getKey()
                ).asObject()
        );
        TableColumn<Map.Entry<Integer, IValue>, String> valueColumn = new TableColumn<>("Value");
        valueColumn.setCellValueFactory(cellData ->
                new javafx.beans.property.SimpleStringProperty(
                        cellData.getValue().getValue().toString()
                )
        );
        heapTable.getColumns().addAll(addressColumn, valueColumn);

        PrgState firstPrg = controller.getRepository().getPrgList().get(0);
        heapTable.setItems(
                FXCollections.observableArrayList(
                        firstPrg.getHeap().getContent().entrySet()
                )
        );

        // c)
        ListView<IValue> outListView = new ListView<>();
        outListView.setItems(
                FXCollections.observableArrayList(
                        firstPrg.getOut().getAll()
                )
        );

        // d)
        ListView<String> fileTableListView = new ListView<>();
        fileTableListView.setItems(
                FXCollections.observableArrayList(
                        firstPrg.getFileTable()
                                .getContent()
                                .keySet()
                                .stream()
                                .map(StringValue::toString)
                                .toList()
                )
        );

        // e)

        ListView<Integer> prgStateIdListView = new ListView<>();
        prgStateIdListView.setItems(
                FXCollections.observableArrayList(
                        controller.getRepository()
                                .getPrgList()
                                .stream()
                                .map(PrgState::getId)
                                .toList()
                )
        );

        // f)
        TableView<Map.Entry<String, IValue>> symTableView = new TableView<>();
        TableColumn<Map.Entry<String, IValue>, String> varNameColumn = new TableColumn<>("Variable");
        varNameColumn.setCellValueFactory(cellData ->
                new javafx.beans.property.SimpleStringProperty(
                        cellData.getValue().getKey()
                )
        );
        TableColumn<Map.Entry<String, IValue>, String> valueColumn2 = new TableColumn<>("Value");
        valueColumn2.setCellValueFactory(cellData ->
                new javafx.beans.property.SimpleStringProperty(
                        cellData.getValue().getValue().toString()
                )
        );

        symTableView.getColumns().addAll(varNameColumn, valueColumn2);

        // g)
        ListView<String> exeStackListView = new ListView<>();
        prgStateIdListView.getSelectionModel()
                .selectedItemProperty()
                .addListener((observable, oldId, newId) -> {
                    if (newId == null)
                        return;

                    PrgState selectedPrg = controller.getRepository()
                            .getPrgList()
                            .stream()
                            .filter(p -> p.getId() == newId)
                            .findFirst()
                            .orElse(null);

                    if (selectedPrg == null)
                        return;

                    symTableView.setItems(
                            FXCollections.observableArrayList(
                                    selectedPrg.getSymTable()
                                            .getContent()
                                            .entrySet()
                            )
                    );

                    exeStackListView.setItems(
                            FXCollections.observableArrayList(
                                    selectedPrg.getExeStack()
                                            .toList() // my myStack method
                                            .stream()
                                            .map(Object::toString)
                                            .toList()
                            )
                    );

                });

        // h)
        Button runOneStepButton = new Button("Run one step");
        runOneStepButton.setOnAction(event -> {
            try {
                controller.oneStepGUI();
                refreshUI(
                        prgStatesField,
                        heapTable,
                        outListView,
                        fileTableListView,
                        prgStateIdListView,
                        symTableView,
                        exeStackListView
                );
            } catch (MyException ex) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setContentText(ex.getMessage());
                alert.showAndWait();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

        VBox left = new VBox(
                new Label("Heap"), heapTable,
                new Label("Out"), outListView,
                new Label("File Table"), fileTableListView
        );

        VBox right = new VBox(
                new Label("Program State IDs"), prgStateIdListView,
                new Label("Symbol Table"), symTableView,
                new Label("Execution Stack"), exeStackListView
        );

        VBox.setVgrow(heapTable, Priority.ALWAYS);
        VBox.setVgrow(outListView, Priority.ALWAYS);
        VBox.setVgrow(prgStateIdListView, Priority.ALWAYS);
        VBox.setVgrow(symTableView, Priority.ALWAYS);
        VBox.setVgrow(exeStackListView, Priority.ALWAYS);

        SplitPane splitPane = new SplitPane(left, right);
        VBox root = new VBox(
                prgLabel,
                prgStatesField,
                runOneStepButton,
                splitPane
        );

        VBox.setVgrow(splitPane, Priority.ALWAYS);

        Scene scene = new Scene(root, 900, 600);
        stage.setTitle("Interpreter");
        stage.setScene(scene);
        stage.show();

        refreshUI(
                prgStatesField,
                heapTable,
                outListView,
                fileTableListView,
                prgStateIdListView,
                symTableView,
                exeStackListView
        );

    }

    private void refreshUI(
            TextField prgStatesField,
            TableView<Map.Entry<Integer, IValue>> heapTable,
            ListView<IValue> outListView,
            ListView<String> fileTableListView,
            ListView<Integer> prgStateIdListView,
            TableView<Map.Entry<String, IValue>> symTableView,
            ListView<String> exeStackListView
    ) {
        List<PrgState> prgList = controller.getRepository().getPrgList();

        prgStatesField.setText(String.valueOf(prgList.size()));

        if (prgList.isEmpty())
            return;

        Integer selectedId = prgStateIdListView.getSelectionModel().getSelectedItem();

        List<Integer> activeIds = prgList.stream()
                .map(PrgState::getId)
                .toList();

        if (selectedId == null || !activeIds.contains(selectedId)) {
            prgStateIdListView.getSelectionModel().selectFirst();
        }


        if (prgStateIdListView.getItems().size() != prgList.size()) {
            prgStateIdListView.setItems(
                    FXCollections.observableArrayList(
                            prgList.stream()
                                    .map(PrgState::getId)
                                    .toList()
                    )
            );
        }


        if (selectedId != null)
            prgStateIdListView.getSelectionModel().select(selectedId);
        else
            prgStateIdListView.getSelectionModel().selectFirst();

        PrgState currentPrg =
                prgList.stream()
                        .filter(p -> p.getId() ==
                                prgStateIdListView.getSelectionModel().getSelectedItem())
                        .findFirst()
                        .orElse(prgList.get(0));

        heapTable.setItems(
                FXCollections.observableArrayList(
                        currentPrg.getHeap().getContent().entrySet()
                )
        );

        outListView.setItems(
                FXCollections.observableArrayList(
                        prgList.get(0).getOut().getAll()
                )
        );

        fileTableListView.setItems(
                FXCollections.observableArrayList(
                        currentPrg.getFileTable()
                                .getContent()
                                .keySet()
                                .stream()
                                .map(StringValue::toString)
                                .toList()
                )
        );

        symTableView.setItems(
                FXCollections.observableArrayList(
                        currentPrg.getSymTable()
                                .getContent()
                                .entrySet()
                )
        );
        symTableView.refresh();

        if (currentPrg.getExeStack().isEmpty())
            exeStackListView.getItems().clear();
        else {
            exeStackListView.setItems(
                    FXCollections.observableArrayList(
                            currentPrg.getExeStack()
                                    .toList()
                                    .stream()
                                    .map(Object::toString)
                                    .toList()
                    )
            );
        }
    }
}
