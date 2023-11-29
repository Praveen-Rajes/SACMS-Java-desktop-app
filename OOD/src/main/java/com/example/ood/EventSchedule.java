package com.example.ood;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.time.ZonedDateTime;
import java.util.*;

public class EventSchedule implements Initializable {
    private Stage stage;
    private Scene scene;
    private Parent root;

    ZonedDateTime dateFocus;
    ZonedDateTime today;

    @FXML
    private Text year;

    @FXML
    private Text month;

    @FXML
    private FlowPane calendar;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        dateFocus = ZonedDateTime.now();
        today = ZonedDateTime.now();
        drawCalendar();
    }

    @FXML
    void backOneMonth(ActionEvent event) {
        dateFocus = dateFocus.minusMonths(1);
        calendar.getChildren().clear();
        drawCalendar();
    }

    @FXML
    void forwardOneMonth(ActionEvent event) {
        dateFocus = dateFocus.plusMonths(1);
        calendar.getChildren().clear();
        drawCalendar();
    }

    private void drawCalendar() {
        year.setText(String.valueOf(dateFocus.getYear()));
        month.setText(String.valueOf(dateFocus.getMonth()));

        double calendarWidth = calendar.getPrefWidth();
        double calendarHeight = calendar.getPrefHeight();
        double strokeWidth = 1;
        double spacingH = calendar.getHgap();
        double spacingV = calendar.getVgap();

        //List of activities for a given month
        Map<Integer, List<CalendarActivity>> calendarActivityMap = getCalendarActivitiesMonth(dateFocus);

        int monthMaxDate = dateFocus.getMonth().maxLength();
        //Check for leap year
        if (dateFocus.getYear() % 4 != 0 && monthMaxDate == 29) {
            monthMaxDate = 28;
        }
        int dateOffset = ZonedDateTime.of(dateFocus.getYear(), dateFocus.getMonthValue(), 1, 0, 0, 0, 0, dateFocus.getZone()).getDayOfWeek().getValue();

        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 7; j++) {
                StackPane stackPane = new StackPane();

                Rectangle rectangle = new Rectangle();
                rectangle.setFill(Color.TRANSPARENT);
                rectangle.setStroke(Color.BLACK);
                rectangle.setStrokeWidth(strokeWidth);
                double rectangleWidth = (calendarWidth / 7) - strokeWidth - spacingH;
                rectangle.setWidth(rectangleWidth);
                double rectangleHeight = (calendarHeight / 6) - strokeWidth - spacingV;
                rectangle.setHeight(rectangleHeight);
                stackPane.getChildren().add(rectangle);

                int calculatedDate = (j + 1) + (7 * i);
                if (calculatedDate > dateOffset) {
                    int currentDate = calculatedDate - dateOffset;
                    if (currentDate <= monthMaxDate) {
                        Text date = new Text(String.valueOf(currentDate));
                        double textTranslationY = -(rectangleHeight / 2) * 0.75;
                        date.setTranslateY(textTranslationY);
                        stackPane.getChildren().add(date);

                        List<CalendarActivity> calendarActivities = calendarActivityMap.get(currentDate);
                        if (calendarActivities != null) {
                            createCalendarActivity(calendarActivities, rectangleHeight, rectangleWidth, stackPane);
                        }
                    }
                    if (today.getYear() == dateFocus.getYear() && today.getMonth() == dateFocus.getMonth() && today.getDayOfMonth() == currentDate) {
                        rectangle.setStroke(Color.BLUE);
                    }
                }
                calendar.getChildren().add(stackPane);
            }
        }
    }

    private void createCalendarActivity(List<CalendarActivity> calendarActivities, double rectangleHeight, double rectangleWidth, StackPane stackPane) {
        VBox calendarActivityBox = new VBox();
        for (int k = 0; k < calendarActivities.size(); k++) {
            if (k >= 2) {
                Text moreActivities = new Text("...");
                calendarActivityBox.getChildren().add(moreActivities);
                moreActivities.setOnMouseClicked(mouseEvent -> {
                    //On ... click print all activities for given date
                    System.out.println(calendarActivities);
                });
                break;
            }
            Text text = new Text( "( "+calendarActivities.get(k).getClientName() + ") " );
            calendarActivityBox.getChildren().add(text);
            text.setOnMouseClicked(mouseEvent -> {
                //On Text clicked
                System.out.println(text.getText());
            });
        }
        calendarActivityBox.setTranslateY((rectangleHeight / 2) * 0.20);
        calendarActivityBox.setMaxWidth(rectangleWidth * 0.8);
        calendarActivityBox.setMaxHeight(rectangleHeight * 0.65);
        calendarActivityBox.setStyle("-fx-background-color:GRAY");
        stackPane.getChildren().add(calendarActivityBox);
    }

    private Map<Integer, List<CalendarActivity>> createCalendarMap(List<CalendarActivity> calendarActivities) {
        Map<Integer, List<CalendarActivity>> calendarActivityMap = new HashMap<>();

        for (CalendarActivity activity : calendarActivities) {
            int activityDate = activity.getDate().getDayOfMonth();
            if (!calendarActivityMap.containsKey(activityDate)) {
                calendarActivityMap.put(activityDate, List.of(activity));
            } else {
                List<CalendarActivity> OldListByDate = calendarActivityMap.get(activityDate);

                List<CalendarActivity> newList = new ArrayList<>(OldListByDate);
                newList.add(activity);
                calendarActivityMap.put(activityDate, newList);
            }
        }
        return calendarActivityMap;
    }

    private Map<Integer, List<CalendarActivity>> getCalendarActivitiesMonth(ZonedDateTime dateFocus) {
        List<CalendarActivity> calendarActivities = new ArrayList<>();
        DBQuery dbQuery = new DBQuery();
        String[][] eventDataArray = dbQuery.retrieveAllEventData();

        int year = dateFocus.getYear();
        int month = dateFocus.getMonth().getValue();
        int eyear;
        int emonth;
        int day;
        int Hour;
        int min;

//        Random random = new Random();
//        for (int i = 0; i < 50; i++) {
//            ZonedDateTime time = ZonedDateTime.of(year, month, random.nextInt(27)+1, 16,0,0,0,dateFocus.getZone());
//            calendarActivities.add(new CalendarActivity(time, "Hans", 111111));
//        }
        if (eventDataArray != null) {
            for (String[] row : eventDataArray) {
                for (String value : row) {
                    System.out.print(value + "\t");
                }
                System.out.println();
            }
        }
//        for (String[] row : eventDataArray) {
//            eyear = Integer.parseInt(row[4].substring(0, 4));
//            emonth = Integer.parseInt(row[4].substring(5, 7));
//            day = Integer.parseInt(row[4].substring(8, 10));
//            Hour = Integer.parseInt(row[5].substring(0, 2));
//            min =  Integer.parseInt(row[5].substring(2, 4));
//            //System.out.println(day);
//            //System.out.println(min);
//
//
//
//        }
//        for (String[] row : eventDataArray) {
//            eyear = Integer.parseInt(row[4].substring(0, 4));
//            emonth = Integer.parseInt(row[4].substring(5, 7));
//            day = Integer.parseInt(row[4].substring(8, 10));
//            Hour = Integer.parseInt(row[5].substring(0, 2));
//            min =  Integer.parseInt(row[5].substring(2, 4));
//
//            ZonedDateTime time = ZonedDateTime.of(eyear, emonth, day, 00, 0, 0, 0, dateFocus.getZone());
//            String clientName = row[1]; // Assuming the client name is in the second column, adjust accordingly
//            String eventID = row[0]; // Assuming the event ID is in the first column, adjust accordingly
//
//            calendarActivities.add(new CalendarActivity(time, clientName, eventID));
//        }
        String EventID = "";
        String EventName = "";
        int date;
        int evmonth;
        int evyear;



//
//
        for (String[] row : eventDataArray) {
            date = Integer.parseInt(row[4].substring(8, 10));
            evmonth = Integer.parseInt((row[4].substring(5, 7)));
            EventName = (row[2]);
            EventID = (row[0]);
            System.out.println( "d" +date);
            System.out.println(evmonth);



            ZonedDateTime time = ZonedDateTime.of(year, evmonth, date, 00, 0, 0, 0, dateFocus.getZone());
            calendarActivities.add(new CalendarActivity(time, EventName + EventID, EventID));

        }
            return createCalendarMap(calendarActivities);
        }


    public void addEvent(ActionEvent actionEvent) throws IOException {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("addevent.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 1200, 750);
            Stage stage = new Stage();
            stage.setTitle("Club Details");
            stage.setScene(scene);

            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}


