package com.kodewerk.cal;


import javafx.application.Application;
import javafx.beans.binding.Bindings;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.time.LocalDate;

public class Calendar extends Application {

    private TableView<Booking> table = new TableView<>();
    static private ObservableList<Booking> data;

    public static void main(String[] args) {

        CalendarModel model = new CalendarModel(LocalDate.parse("2017-01-01"));
        data = FXCollections.observableArrayList(model.getWeeklyCalendar());
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("Calendar");
        primaryStage.setWidth(900);
        primaryStage.setHeight(735);
        Scene scene = new Scene(new Group());

//        final Label label = new Label("Address Book");
//        label.setFont(new Font("Arial", 20));x

        table.setEditable(true);
        Callback<TableColumn, TableCell> cellFactory = p -> new EditingCell();
        table.setPrefHeight(685);

        TableColumn<Booking,String> weekCol = new TableColumn("Week");
        weekCol.setMinWidth(20.0d);
        weekCol.setCellValueFactory(new PropertyValueFactory<>("week"));

        TableColumn<Booking,String> datesCol = new TableColumn("Dates");
        datesCol.setMinWidth(40.0d);
        datesCol.setCellValueFactory(new PropertyValueFactory<>("dates"));

        final TableColumn activityCol = new TableColumn("Activity");
        activityCol.setMinWidth(80.0d);
        activityCol.setCellValueFactory(new PropertyValueFactory<Booking, String>("activity"));
        activityCol.setCellFactory(cellFactory);
        activityCol.setOnEditCommit(
                new EventHandler<CellEditEvent<Booking, String>>() {
                    @Override
                    public void handle(CellEditEvent<Booking, String> t) {
                        (t.getTableView().getItems().get(
                                t.getTablePosition().getRow())
                        ).setActivity(t.getNewValue());
                    }
                }
        );

        TableColumn customerCol = new TableColumn("Customer");
        customerCol.setMinWidth(150.0d);
        customerCol.setCellValueFactory(new PropertyValueFactory<Booking, String>("customer"));
        customerCol.setCellFactory(cellFactory);
        customerCol.setOnEditCommit(
                new EventHandler<CellEditEvent<Booking, String>>() {
                    @Override
                    public void handle(CellEditEvent<Booking, String> t) {
                        (t.getTableView().getItems().get(
                                t.getTablePosition().getRow())
                        ).setCustomer(t.getNewValue());
                    }
                }
        );

        TableColumn locationCol = new TableColumn("location");
        locationCol.setMinWidth(150.0d);
        locationCol.setCellValueFactory(new PropertyValueFactory<Booking, String>("location"));
        locationCol.setCellFactory(cellFactory);
        locationCol.setOnEditCommit(
                new EventHandler<CellEditEvent<Booking, String>>() {
                    @Override
                    public void handle(CellEditEvent<Booking, String> t) {
                        (t.getTableView().getItems().get(
                                t.getTablePosition().getRow())
                        ).setLocation(t.getNewValue());
                    }
                }
        );

        TableColumn notesCol = new TableColumn("Notes");
        notesCol.setMinWidth(400);
        notesCol.setCellValueFactory(new PropertyValueFactory<Booking, String>("notes"));
        notesCol.setCellFactory(cellFactory);
        notesCol.setOnEditCommit(
                new EventHandler<CellEditEvent<Booking, String>>() {
                    @Override
                    public void handle(CellEditEvent<Booking, String> t) {
                        (t.getTableView().getItems().get(
                                t.getTablePosition().getRow())
                        ).setNotes(t.getNewValue());
                    }
                }
        );



        table.setItems(data);
        table.getColumns().addAll(weekCol, datesCol, activityCol, customerCol, locationCol, notesCol);

        table.setRowFactory(tableView -> {
            final TableRow<Booking> row = new TableRow<>();
            final ContextMenu contextMenu = new ContextMenu();
            final MenuItem tentativeMenuItem = new MenuItem("Tentative");
            tentativeMenuItem.setOnAction(event -> {
                row.setStyle("-fx-background-color:yellow");
            });

            final MenuItem confirmMenuItem = new MenuItem("Confirm");
            confirmMenuItem.setOnAction(event -> {
                row.setStyle("-fx-background-color:limegreen");
            });

            final MenuItem cancelMenuItem = new MenuItem("Cancel");
            cancelMenuItem.setOnAction(event -> {
                row.setStyle("-fx-background-color:white");
            });

            final MenuItem blockMenuItem = new MenuItem("Block");
            blockMenuItem.setOnAction(event -> {
                row.setStyle("-fx-background-color:darkgray");
            });

            contextMenu.getItems().addAll(tentativeMenuItem, confirmMenuItem, cancelMenuItem, blockMenuItem);
            // Set context menu on row, but use a binding to make it only show for non-empty rows:
            row.contextMenuProperty().bind(
                    Bindings.when(row.emptyProperty())
                            .then((ContextMenu)null)
                            .otherwise(contextMenu)
            );
            return row ;
        });


        final VBox vbox = new VBox();
        vbox.setSpacing(5);
        vbox.setPadding(new Insets(10, 0, 0, 10));
        //vbox.getChildren().addAll(label, table);
        vbox.getChildren().addAll(table);

        ((Group) scene.getRoot()).getChildren().addAll(vbox);

        primaryStage.setScene(scene);
        primaryStage.show();
        primaryStage.show();
    }

    private class EditingCell extends TableCell<Booking,String> {

            private TextField textField;

            public EditingCell() {
            }

            @Override
            public void startEdit() {
                if (!isEmpty()) {
                    super.startEdit();
                    createTextField();
                    setText(null);
                    setGraphic(textField);
                    textField.selectAll();
                }
            }

            @Override
            public void cancelEdit() {
                super.cancelEdit();

                setText((String) getItem());
                setGraphic(null);
            }

            @Override
            public void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);

                if (empty) {
                    setText(null);
                    setGraphic(null);
                } else {
                    if (isEditing()) {
                        if (textField != null) {
                            textField.setText(getString());
                        }
                        setText(null);
                        setGraphic(textField);
                    } else {
                        setText(getString());
                        setGraphic(null);
                    }
                }
            }

            private void createTextField() {
                textField = new TextField(getString());
                textField.setMinWidth(this.getWidth() - this.getGraphicTextGap()* 2);
                textField.focusedProperty().addListener(new ChangeListener<Boolean>(){
                    @Override
                    public void changed(ObservableValue<? extends Boolean> arg0,
                                        Boolean arg1, Boolean arg2) {
                        if (!arg2) {
                            commitEdit(textField.getText());
                        }
                    }
                });
            }

            private String getString() {
                return getItem() == null ? "" : getItem().toString();
            }
    }
}
