import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.geometry.Insets;
import javafx.scene.layout.HBox;
import javafx.geometry.Pos;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Game {
    int diceAmount = 5, rollCount = 2, rollsRem = 0, roundScore = 0, overAllScore = 0;
    String currHand = "";

    Dice[] diceArr;

    Button restartButton, rollButton;
    Label curHandLabel, rollsRemLabel, overAllScoreLabel;

    HBox diceHbox, overAllScoreHbox, scoreRollBox;
    VBox vboxButtons, mainbox;

    void addEvents() {
        rollButton.setOnAction(event -> {
            for (Dice dice : diceArr) {
                dice.diceSlot.setVisible(true);
            }
            // starts game
            playGame(diceArr);
            addEventToRollBtn(diceArr);
        });

        // resets game
        restartButton.setOnAction(event -> {
            rollCount = 2;
            restartButton.setVisible(false);
            rollButton.setVisible(true);
            curHandLabel.setText("Current Hand: ");

            for (Dice dice : diceArr) {
                dice.held = 0;
            }

            curHandLabel.setId(null);

            playGame(diceArr);
        });
    }

    void addEventToRollBtn(Dice[] diceArr) {
        rollButton.setOnAction(event -> {
            // roll die if not held
            for (Dice dice : diceArr) {
                dice.rollDieIfNotHeld();
            }
            rollCount -= 1;

            // update game board
            updateRoundScore();
            updateRollsRemaining();
            updateCurrHand();

            // check if rolls remaining
            checkIfGameEnd();

        });
    }

    void addStyles() {
        overAllScoreHbox.setAlignment(Pos.CENTER);
        overAllScoreHbox.setPadding(new Insets(20));

        diceHbox.setStyle("-fx-text-fill: red");
        diceHbox.setAlignment(Pos.CENTER);
        diceHbox.setPadding(new Insets(20));

        curHandLabel.setPadding(new Insets(0, 0, 0, 0));

        rollButton.setMinWidth(100);
        rollsRemLabel.setPadding(new Insets(0, 0, 0, 150));

        scoreRollBox.setAlignment(Pos.CENTER);
        scoreRollBox.setPadding(new Insets(20));

        restartButton.setMinWidth(100);
        restartButton.setVisible(false);

        vboxButtons.setAlignment(Pos.CENTER);

        mainbox.setAlignment(Pos.CENTER);
        mainbox.setId("vbox");
        mainbox.getStylesheets().add("myStyles.css");
    }

    void checkIfGameEnd() {
        if (rollCount == 0) {
            rollButton.setVisible(false);
            restartButton.setVisible(true);

            curHandLabel.setText(String.format("Winning Hand --> %s", currHand));
            curHandLabel.setId("win-color");

            overAllScore += roundScore;
            overAllScoreLabel.setText(String.format("Overall Score: %d", overAllScore));
        }
    }

    void initBtnLabelContainers() {
        // init buttons
        restartButton = new Button("Click to Play Again!");
        rollButton = new Button("Click to Play!");

        // init Labels
        curHandLabel = new Label(String.format("Current Hand: %s", currHand));
        overAllScoreLabel = new Label(String.format("Overall Score: %d", overAllScore));
        rollsRemLabel = new Label(String.format("Rolls Remaining: %d", rollsRem));

        // init horizontal box containers
        scoreRollBox = new HBox(curHandLabel, rollsRemLabel);
        overAllScoreHbox = new HBox(overAllScoreLabel);
        diceHbox = new HBox();
        for (Dice dice : diceArr) {
            diceHbox.getChildren().add(dice.diceSlot);
        }

        // init vertical box containers
        vboxButtons = new VBox(rollButton, restartButton);
        mainbox = new VBox(overAllScoreHbox, diceHbox, scoreRollBox, vboxButtons);
    }

    void initGameBoard() {
        // create Dice objs
        diceArr = new Dice[diceAmount];
        diceArr = Dice.createDice(diceAmount);

        // game board layout
        initBtnLabelContainers();
        addStyles();

        // events
        addEvents();
    }

    void playGame(Dice[] diceArr) {
        // updates rolls remaining
        updateRollsRemaining();

        // initializes random dice
        Dice.createRandomStart(diceArr);

        rollButton.setText("Roll");

        updateRoundScore();
        updateCurrHand();
    }

    public void StartGame(Stage primaryStage) {
        // init game variables
        initGameBoard();

        // set scene
        primaryStage.setTitle("Dice Game");
        primaryStage.setScene(new Scene(mainbox, 600, 350));
        primaryStage.show();
    }

    void updateCurrHand() {
        switch (roundScore) {
            case 10:
                currHand = "5 of a kind - 10 points";
                break;
            case 8:
                currHand = "Straight - 8 points";
                break;
            case 7:
                currHand = "4 of kind - 7 points";
                break;
            case 6:
                currHand = "Full House - 6 points";
                break;
            case 5:
                currHand = "3 of a kind - 5 points";
                break;
            case 4:
                currHand = "2 pairs - 4 points";
                break;
            case 1:
                currHand = "1 pair - 1 point";
                break;
            default:
                currHand = "No hand - 0 points";
                break;
        }

        curHandLabel.setText(String.format("Current Hand: %s", currHand));
    }

    void updateRollsRemaining() {
        rollsRemLabel.setText(String.format("Rolls Remaining: %d", rollCount));
    }

    void updateRoundScore() {
        roundScore = Dice.findCurrScore(diceArr);
    }
}