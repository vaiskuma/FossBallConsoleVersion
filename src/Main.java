/**
 * Created by Kompas on 2017-04-06.
 */


import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("fossBallGUI.fxml"));
        primaryStage.setTitle("Welcome to our Foosball Tournament system");
        primaryStage.setScene(new Scene(root, 1050, 950));
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
