import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.geometry.Insets;
import javafx.scene.layout.HBox;
import javafx.geometry.Pos;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Game {
    int diceAmount, rollCount, roundScore;

    Button refreshButton, rollButton;
    Label curHighest, curHighestLabel, rollsRem, overAllLabel, overAllScore;
    Dice diceArr[];

    void initGameVars() {
        diceAmount = 5;
        rollCount = 2;
        roundScore = 0;

        rollsRem = new Label("0");
        curHighest = new Label("0");
        overAllLabel = new Label("Overall Score: ");
        rollButton = new Button("Click to Play!");
        overAllScore = new Label("0");
    }

    void addEventToRollBtn(Dice[] diceArr) {
        rollButton.setOnAction(event -> {
            // updates labels
            curHighestLabel.setText("Current Hand: ");

            // checks if the dice should roll
            if (rollCount != 0) {
                for (Dice dice : diceArr) {
                    dice.checkIfRollDie();
                }
            }

            rollCount = rollCount - 1;
            rollsRem.setText(Integer.toString(rollCount));
            roundScore = diceArr[0].findCurrScore(diceArr);

            updateCurrDisplay(roundScore);

            // end of rolls, calculates and asks to continue or exit
            if (rollCount == 0) {

                int temp = Integer.parseInt(overAllScore.getText());
                overAllScore.setText(Integer.toString(temp + roundScore));

                rollButton.setVisible(false);
                refreshButton.setVisible(true);

                curHighestLabel.setText("Winning Hand --> ");
                curHighestLabel.setId("win-color");
                curHighest.setId("win-color");

                // resets game
                refreshButton.setOnAction(event1 -> {
                    rollCount = 2;
                    refreshButton.setVisible(false);
                    rollButton.setVisible(true);
                    curHighestLabel.setText("Current Hand: ");

                    for (Dice dice : diceArr) {
                        dice.held = 0;
                    }

                    curHighestLabel.setId(null);
                    curHighest.setId(null);

                    playGame(diceArr);
                });
            }
        });
    }

    void createDice(int diceAmount) {
        Dice[] array = new Dice[diceAmount];

        for (int i = 0; i < diceAmount; i++) {
            Dice tempDice = new Dice();
            tempDice.createInit();
            tempDice.diceIndex = i + 1;

            array[i] = tempDice;
        }

        diceArr = array;
    }

    void playGame(Dice[] diceArr) {
        // updates rolls remaining
        rollsRem.setText(Integer.toString(rollCount));

        // initializes random dice
        diceArr[0].createRandomStart(diceArr);
        rollButton.setText("Roll");

        roundScore = diceArr[0].findCurrScore(diceArr);
        updateCurrDisplay(roundScore);
    }

    public void StartGame(Stage primaryStage) {
        // init game variables
        initGameVars();

        HBox overAllHbox = new HBox(overAllLabel, overAllScore);
        overAllHbox.setAlignment(Pos.CENTER);
        overAllHbox.setPadding(new Insets(20));

        // creates container for diceImage, and sets initial die values
        createDice(diceAmount);

        HBox diceHbox = new HBox();
        for (Dice dice : diceArr) {
            diceHbox.getChildren().add(dice.diceSlot);
        }

        diceHbox.setStyle("-fx-text-fill: red");
        diceHbox.setAlignment(Pos.CENTER);
        diceHbox.setPadding(new Insets(20));

        // current score & rolls left container
        curHighestLabel = new Label("Current Hand: ");
        curHighestLabel.setPadding(new Insets(0, 0, 0, 0));

        curHighest.setPadding(new Insets(0, 0, 0, 0));

        Label rollsRemLabel = new Label("Rolls Remaining: ");
        rollsRemLabel.setPadding(new Insets(0, 0, 0, 150));

        rollsRem.setPadding(new Insets(0, 0, 0, 0));
        rollsRem.setId("rollsRem");

        HBox scoreRollBox = new HBox(curHighestLabel, curHighest, rollsRemLabel, rollsRem);
        scoreRollBox.setAlignment(Pos.CENTER);
        scoreRollBox.setPadding(new Insets(20));

        rollButton.setMinWidth(100);
        rollButton.setOnAction(event -> {
            for (Dice dice : diceArr) {
                dice.diceSlot.setVisible(true);
            }
            // starts game
            playGame(diceArr);
            addEventToRollBtn(diceArr);
        });

        refreshButton = new Button("Click to Play Again!");
        refreshButton.setMinWidth(100);
        refreshButton.setVisible(false);

        // updates image to held/not held
        for (Dice dice : diceArr) {
            dice.diceSlot.setOnMousePressed(event -> {
                dice.updatePic();
            });
        }

        VBox vboxButtons = new VBox(rollButton, refreshButton);
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

    void updateCurrDisplay(int roundScore) {
        switch (roundScore) {
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