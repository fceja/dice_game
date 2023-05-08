import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import javafx.scene.control.Label;
import javafx.geometry.Insets;
import javafx.scene.layout.HBox;
import javafx.geometry.Pos;
import javafx.scene.layout.VBox;

public class App extends Application {

    public static void main(String[] args) {
        launch(args);
    }


    Label overAllScore, curHighest, curHighestLabel, rollsRem;
    Button rollButton, refreshButton;

    int rollCount = 2;
    int roundScore = 0;

    @Override
    public void start(Stage primaryStage){

        Game game = new Game();

        // overall score labels container
        Label overAllLabel = new Label("Overall Score: ");
        overAllScore = new Label("0");

        HBox overAllHbox = new HBox(overAllLabel, overAllScore);
        overAllHbox.setAlignment(Pos.CENTER);
        overAllHbox.setPadding(new Insets(20));

        // creates container for diceImage, and sets initial die values
        Dice dice1 = new Dice();
        dice1.createInit();
        dice1.diceIndex = 1;

        Dice dice2 = new Dice();
        dice2.createInit();
        dice2.diceIndex = 2;

        Dice dice3 = new Dice();
        dice3.createInit();
        dice3.diceIndex = 3;

        Dice dice4 = new Dice();
        dice4.createInit();
        dice4.diceIndex = 4;

        Dice dice5 = new Dice();
        dice5.createInit();
        dice5.diceIndex = 5;

        HBox diceHbox = new HBox(dice1.diceSlot,dice2.diceSlot, dice3.diceSlot, dice4.diceSlot, dice5.diceSlot);
        diceHbox.setStyle("-fx-text-fill: red");
        diceHbox.setAlignment(Pos.CENTER);
        diceHbox.setPadding(new Insets(20));

        // current score & rolls left container
        curHighestLabel = new Label ("Current Hand: ");
        curHighestLabel.setPadding(new Insets(0,0,0,0));

        curHighest = new Label ("0");
        curHighest.setPadding(new Insets(0,0,0,0));

        Label rollsRemLabel = new Label ("Rolls Remaining: ");
        rollsRemLabel.setPadding(new Insets(0,0,0,150));

        rollsRem = new Label ("0");
        rollsRem.setPadding(new Insets(0,0,0,0));
        rollsRem.setId("rollsRem");

        HBox scoreRollBox = new HBox(curHighestLabel, curHighest, rollsRemLabel, rollsRem);
        scoreRollBox.setAlignment(Pos.CENTER);
        scoreRollBox.setPadding(new Insets(20));

        // roll button
        rollButton = new Button("Click to Play!");
        rollButton.setMinWidth(100);
        rollButton.setOnAction(event -> {
            dice1.diceSlot.setVisible(true);
            dice2.diceSlot.setVisible(true);
            dice3.diceSlot.setVisible(true);
            dice4.diceSlot.setVisible(true);
            dice5.diceSlot.setVisible(true);
            rollButton.setText("Roll");

            // starts game
            game.playGame(dice1, dice2, dice3, dice4, dice5);
        });

        refreshButton = new Button("Click to Play Again!");
        refreshButton.setMinWidth(100);
        refreshButton.setVisible(false);

        // updates image to held/not held
        dice1.diceSlot.setOnMousePressed(event -> {dice1.updatePic();});
        dice2.diceSlot.setOnMousePressed(event -> {dice2.updatePic();});
        dice3.diceSlot.setOnMousePressed(event -> {dice3.updatePic();});
        dice4.diceSlot.setOnMousePressed(event -> {dice4.updatePic();});
        dice5.diceSlot.setOnMousePressed(event -> {dice5.updatePic();});

        VBox vboxButtons = new VBox (rollButton, refreshButton);
        vboxButtons.setAlignment(Pos.CENTER);

        // all hbox into vbox container
        VBox vbox = new VBox(overAllHbox, diceHbox, scoreRollBox, vboxButtons);
        vbox.setAlignment(Pos.CENTER);

        primaryStage.setTitle("Dice Game");
        primaryStage.setScene(new Scene(vbox, 600, 350));
        vbox.setId("vbox");
        vbox.getStylesheets().add("myStyles.css");
        primaryStage.show();
    }


    public class Game {

        void playGame(Dice dice1, Dice dice2, Dice dice3, Dice dice4, Dice dice5){

            // updates rolls remaining
            rollsRem.setText(Integer.toString(rollCount));

            // initializes random dice
            dice1.createRandomStart(dice1, dice2, dice3, dice4, dice5);
            roundScore = dice1.findCurrScore(dice1, dice2, dice3, dice4, dice5);
            updateCurrDisplay(roundScore);

            // if roll button is clicked
            rollButton.setOnAction(event -> {
                //updates labels
                rollButton.setText("Roll");
                curHighestLabel.setText("Current Hand: ");

                // checks if the dice should roll
                if (rollCount != 0){
                    dice1.checkIfRollDie();
                    dice2.checkIfRollDie();
                    dice3.checkIfRollDie();
                    dice4.checkIfRollDie();
                    dice5.checkIfRollDie();

                }

                rollCount = rollCount - 1;
                rollsRem.setText(Integer.toString(rollCount));
                roundScore = dice1.findCurrScore(dice1, dice2, dice3, dice4, dice5);

                updateCurrDisplay(roundScore);

                // end of rolls, calculates and asks to continue or exit
                if(rollCount == 0){

                    int temp = Integer.parseInt(overAllScore.getText());
                    overAllScore.setText(Integer.toString(temp + roundScore));

                    rollButton.setVisible(false);
                    refreshButton.setVisible(true);

                    curHighestLabel.setText("Winning Hand --> ");
                    curHighestLabel.setId("win-color");
                    curHighest.setId("win-color");

                    // resets game
                    refreshButton.setOnAction(event1 -> {
                        rollCount = 3;
                        refreshButton.setVisible(false);
                        rollButton.setVisible(true);
                        curHighestLabel.setText("Current Hand: ");

                        dice1.held = 0;
                        dice2.held = 0;
                        dice3.held = 0;
                        dice4.held = 0;
                        dice5.held = 0;

                        curHighestLabel.setId(null);
                        curHighest.setId(null);

                        playGame(dice1, dice2, dice3, dice4, dice5);
                    });
                }
            });

        }
        void updateCurrDisplay(int roundScore){

            switch(roundScore){
                case 10:
                    curHighest.setText("5 of a kind - 10 points");
                    break;
                case 8:
                    curHighest.setText("Straight - 8 points");
                    break;
                case 7:
                    curHighest.setText("4 of kind - 7 points");
                    break;
                case 6:
                    curHighest.setText("Full House - 6 points");
                    break;
                case 5:
                    curHighest.setText("3 of a kind - 5 points");
                    break;
                case 4:
                    curHighest.setText("2 pairs - 4 points");
                    break;
                case 1:
                    curHighest.setText("2 of a kind - 1 points");
                    break;
                default:
                    curHighest.setText("No hand - 0 points");
                    break;

            }

        }

    }
}