import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import javafx.stage.Modality;
import javafx.scene.text.Text;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.time.Period;

import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.event.EventHandler;
import javafx.event.ActionEvent;

public class PopupWindowFX extends Application {
    private TextField startDate;
    private TextField endDate;
    private Button button;
    private VBox vbox;
    private Label startLabel;
    private Label endLabel;

    @Override
    public void start(Stage primaryStage) throws Exception {
        Pane root = new Pane();
        startDate = new TextField("01/01/2000");
        endDate = new TextField(LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
        startLabel = new Label("Start date: ");
        endLabel = new Label("End date: ");
        root.setPrefWidth(500);
        root.setPrefHeight(500);
        Scene scene = new Scene(root);
        button = new Button("Count");
        vbox = new VBox(10, startLabel, startDate, endLabel, endDate, button);
        vbox.setPadding(new Insets(50));
        root.getChildren().add(vbox);
        button.setOnAction(new clickHandle());
        startDate.setOnAction(new clickHandle());
        endDate.setOnAction(new clickHandle());
        primaryStage.setTitle("Date Counter");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private class clickHandle implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent e) {
            try {
                LocalDate start = null, end = null;
                String startDateStr = startDate.getText();
                String endDateStr = endDate.getText();

                DateTimeFormatter[] f = new DateTimeFormatter[]{
                        DateTimeFormatter.ofPattern("dd/MM/yyyy"),
                        DateTimeFormatter.ofPattern("d/M/yyyy"),
                        DateTimeFormatter.ofPattern("dd/M/yyyy"),
                        DateTimeFormatter.ofPattern("d/MM/yyyy")
                };

                try {
                    start = LocalDate.parse(startDateStr, f[0]);
                } catch (Exception exc) {
                    try {
                        start = LocalDate.parse(startDateStr, f[1]);
                    } catch (Exception a) {
                        try {
                            start = LocalDate.parse(startDateStr, f[2]);
                        } catch (Exception b) {
                            try {
                                start = LocalDate.parse(startDateStr, f[3]);
                            } catch (Exception c) {
                                throw new CPSC1181Exception("Start Date: Incorrect format. The correct format is \"DD/MM/YYYY\"");
                            }
                        }
                    }
                }

                try {
                    end = LocalDate.parse(endDateStr, f[0]);
                } catch (Exception exc) {
                    try {
                        end = LocalDate.parse(endDateStr, f[1]);
                    } catch (Exception a) {
                        try {
                            end = LocalDate.parse(endDateStr, f[2]);
                        } catch (Exception b) {
                            try {
                                end = LocalDate.parse(endDateStr, f[3]);
                            } catch (Exception c) {
                                throw new CPSC1181Exception("End Date: Incorrect format. The correct format is \"DD/MM/YYYY\"");
                            }
                        }
                    }
                }
                startDateValidation.startDateValidate(startDate.getText().trim());
                startDateValidation.endDateValidate(endDate.getText().trim());
                long daysBetween = ChronoUnit.DAYS.between(start, end);

                Period periodBetween = Period.between(start, end);
                int years = periodBetween.getYears();
                int months = periodBetween.getMonths();
                int days = periodBetween.getDays();
                showPopUpWindow(days + " days\n" + months + " months\n" + years + " years\n" + daysBetween + " total days");
            } catch (Exception ex) {
                showPopUpWindow(ex.getMessage());
            }
        }

        private void showPopUpWindow(String msg) {
            Stage popUpStage = new Stage(); // We can have as many stages as we need
            popUpStage.initModality(Modality.APPLICATION_MODAL);
            VBox dialogVbox = new VBox(20);
            dialogVbox.getChildren().add(new Text(msg));
            Scene dialogScene = new Scene(dialogVbox, 300, 200);
            popUpStage.setScene(dialogScene);
            popUpStage.show();
        }

        public void main(String[] args) {
            Application.launch(args);
        }

    }
}