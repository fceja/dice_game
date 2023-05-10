import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.geometry.Insets;
import javafx.scene.layout.HBox;
import javafx.geometry.Pos;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Game {
    int diceAmount = 5, rollCount = 2, roundScore = 0, overAllScore=0;

    Dice diceArr[];

    Button refreshButton, rollButton;
    Label curHand, curHandLabel, rollsRem, rollsRemLabel, overAllScoreLabel;

    HBox diceHbox, overAllScoreHbox, scoreRollBox;
    VBox vboxButtons, mainbox;

    void addEventToRollBtn(Dice[] diceArr) {
        rollButton.setOnAction(event -> {
            // updates labels
            curHandLabel.setText("Current Hand: ");

            // checks if the dice should roll
            if (rollCount != 0) {
                for (Dice dice : diceArr) {
                    dice.checkIfRollDie();
                }
            }

            rollCount -= 1;
            rollsRem.setText(Integer.toString(rollCount));
            roundScore = Dice.findCurrScore(diceArr);

            updateCurrDisplay();

            // end of rolls, calculates and asks to continue or exit
            if (rollCount == 0) {
                rollButton.setVisible(false);
                refreshButton.setVisible(true);

                curHandLabel.setText("Winning Hand --> ");
                curHandLabel.setId("win-color");
                curHand.setId("win-color");

                overAllScore += roundScore;
                overAllScoreLabel.setText(String.format("Overall Score: %d", overAllScore));
            }
        });
    }

    void createDiceObjs() {
        Dice[] array = new Dice[diceAmount];

        for (int i = 0; i < diceAmount; i++) {
            Dice tempDice = new Dice();
            tempDice.createInit();
            tempDice.diceIndex = i + 1;

            array[i] = tempDice;
        }

        diceArr = array;
    }

    void addEvents() {
        rollButton.setOnAction(event -> {
            for (Dice dice : diceArr) {
                dice.diceSlot.setVisible(true);
            }
            // starts game
            playGame(diceArr);
            addEventToRollBtn(diceArr);
        });

        for (Dice dice : diceArr) {
            dice.diceSlot.setOnMousePressed(event -> {
                dice.updatePic();
            });
        }

        refreshButton.setOnAction(event -> {
            // resets game
            rollCount = 2;
            refreshButton.setVisible(false);
            rollButton.setVisible(true);
            curHandLabel.setText("Current Hand: ");

            for (Dice dice : diceArr) {
                dice.held = 0;
            }

            curHandLabel.setId(null);
            curHand.setId(null);

            playGame(diceArr);
        });
    }

    void addStyles() {
        overAllScoreHbox.setAlignment(Pos.CENTER);
        overAllScoreHbox.setPadding(new Insets(20));

        diceHbox.setStyle("-fx-text-fill: red");
        diceHbox.setAlignment(Pos.CENTER);
        diceHbox.setPadding(new Insets(20));

        curHand.setPadding(new Insets(0, 0, 0, 0));
        curHandLabel.setPadding(new Insets(0, 0, 0, 0));

        rollButton.setMinWidth(100);
        rollsRem.setPadding(new Insets(0, 0, 0, 0));
        rollsRemLabel.setPadding(new Insets(0, 0, 0, 150));

        scoreRollBox.setAlignment(Pos.CENTER);
        scoreRollBox.setPadding(new Insets(20));

        refreshButton.setMinWidth(100);
        refreshButton.setVisible(false);

        vboxButtons.setAlignment(Pos.CENTER);

        mainbox.setAlignment(Pos.CENTER);
        mainbox.setId("vbox");
        mainbox.getStylesheets().add("myStyles.css");
    }

    void initContainers() {
        refreshButton = new Button("Click to Play Again!");
        rollButton = new Button("Click to Play!");

        curHand = new Label("0");
        curHandLabel = new Label("Current Hand: ");

        overAllScoreLabel = new Label(String.format("Overall Score: %d", overAllScore));

        rollsRem = new Label("0");
        rollsRemLabel = new Label("Rolls Remaining: ");

        scoreRollBox = new HBox(curHandLabel, curHand, rollsRemLabel, rollsRem);
        overAllScoreHbox = new HBox(overAllScoreLabel);
        diceHbox = new HBox();
        for (Dice dice : diceArr) {
            diceHbox.getChildren().add(dice.diceSlot);
        }

        vboxButtons = new VBox(rollButton, refreshButton);
        mainbox = new VBox(overAllScoreHbox, diceHbox, scoreRollBox, vboxButtons);
    }

    void initGameBoard() {
        // Dice objs
        createDiceObjs();

        // layout
        initContainers();
        addStyles();

        // events
        addEvents();
    }

    void playGame(Dice[] diceArr) {
        // updates rolls remaining
        rollsRem.setText(Integer.toString(rollCount));

        // initializes random dice
        Dice.createRandomStart(diceArr);

        rollButton.setText("Roll");

        roundScore = Dice.findCurrScore(diceArr);
        updateCurrDisplay();
    }

    public void StartGame(Stage primaryStage) {
        // init game variables
        initGameBoard();

        // set scene
        primaryStage.setTitle("Dice Game");
        primaryStage.setScene(new Scene(mainbox, 600, 350));
        primaryStage.show();
    }

    void updateCurrDisplay() {
        switch (roundScore) {
            case 10:
                curHand.setText("5 of a kind - 10 points");
                break;
            case 8:
                curHand.setText("Straight - 8 points");
                break;
            case 7:
                curHand.setText("4 of kind - 7 points");
                break;
            case 6:
                curHand.setText("Full House - 6 points");
                break;
            case 5:
                curHand.setText("3 of a kind - 5 points");
                break;
            case 4:
                curHand.setText("2 pairs - 4 points");
                break;
            case 1:
                curHand.setText("2 of a kind - 1 points");
                break;
            default:
                curHand.setText("No hand - 0 points");
                break;
        }
    }
}