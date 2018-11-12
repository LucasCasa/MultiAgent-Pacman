package ar.edu.itba.multiagent.pacman;

import com.google.common.collect.ImmutableList;
import com.sun.javafx.collections.ObservableListWrapper;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.TilePane;
import javafx.stage.Stage;


// A GUI program is written as a subclass of Frame - the top-level container
// This subclass inherits all properties from Frame, e.g., title, icon, buttons, content-pane
public class ConfigSetup extends Application {

    private Label ghostNLabel = new Label("Ghost Number:");
    private Label mapLabel = new Label("Map type");
    private Label ghostVisiblityLabel = new Label("Ghost visibility range");

    private TextField ghostVisibilityTF = new TextField("7");
    private ChoiceBox<String> mapName = new ChoiceBox<>();
    private ChoiceBox<Integer> ghostNumber = new ChoiceBox<>();

    public void start(Stage primaryStage) {
        primaryStage.setTitle("Config Manager");
        mapName.setItems(new ObservableListWrapper<>(ImmutableList.of("Standard", "Empty")));
        mapName.setValue("Standard");
        ghostNumber.setItems(new ObservableListWrapper<>(ImmutableList.of(1,2,3,4,5,6,7,8,9,10)));
        ghostNumber.setValue(4);
        GridPane pane = new GridPane();

        pane.add(ghostNLabel,0,0);
        pane.add(ghostNumber,1,0);
        pane.add(mapLabel,0,1);
        pane.add(mapName,1,1);
        pane.add(ghostVisiblityLabel,0,2);
        pane.add(ghostVisibilityTF, 1, 2);
        pane.setVgap(10);
        Scene scene = new Scene(pane, 500, 200);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        Application.launch(args);
    }

}
