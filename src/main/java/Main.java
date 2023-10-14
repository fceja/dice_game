import javafx.application.Application;
import javafx.stage.Stage;

import lib.Game;

public class Main extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        Game diceGame = new Game();
        diceGame.StartGame(primaryStage);
    }
}
