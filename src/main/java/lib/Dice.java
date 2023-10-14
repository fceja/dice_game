package lib;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.Collections;
import java.util.Map;
import java.util.HashMap;
import java.util.Random;

public class Dice {

    int diceValue;
    int held = 0; // 1 if held, 0 if not
    int diceIndex;

    Image diceImage;
    ImageView diceContainer;

    static Dice[] createDice(int diceAmount) {
        Dice[] diceArray = new Dice[diceAmount];

        for (int i = 0; i < diceAmount; i++) {
            Dice tempDice = new Dice();
            tempDice.setDieContainer();
            tempDice.diceIndex = i + 1;

            diceArray[i] = tempDice;
        }

        return diceArray;
    }

    static Dice[] restartGame(Dice[] diceArray) {
        for (Dice die : diceArray) {
            die.getRandomDieVal();
            die.setDieImage();
        }

        return diceArray;
    }

    static int findCurrScore(Dice[] diceArr) {
        Map<Integer, Integer> map = findDieFreq(diceArr);

        int maxFrequency = Collections.max(map.values());
        int distinctNumbers = map.size();

        if (maxFrequency == 5) {
            return 10; // 5 of a kind
        } else if (distinctNumbers == 1 && maxFrequency == 1) {
            return 8; // Straight
        } else if (maxFrequency == 4) {
            return 7; // 4 of a kind
        } else if (distinctNumbers == 2 && (map.containsValue(2) && map.containsValue(3))) {
            return 6; // Full house
        } else if (maxFrequency == 3) {
            return 5; // 3 of a kind
        } else if (distinctNumbers == 3 && maxFrequency == 2) {
            return 4; // 2 pair
        } else if (maxFrequency == 2) {
            return 1; // 2 of a kind
        }

        return 0;
    }

    static Map<Integer, Integer> findDieFreq(Dice[] diceArr) {
        Map<Integer, Integer> map = new HashMap<Integer, Integer>();

        for (int i = 0; i < diceArr.length; i++) {
            int key = diceArr[i].diceValue;

            if (map.containsKey(key)) {
                int count = map.get(key);
                map.put(key, count + 1);
            } else {
                map.put(key, 1);
            }
        }

        return map;
    }

    void getRandomDieVal() {
        Random randNum = new Random();
        this.diceValue = 1 + randNum.nextInt(6);
    }

    void rollDieIfNotHeld() {
        // 1 held, 0 not held
        if (held == 0) {
            Random randNum = new Random();
            diceValue = 1 + randNum.nextInt(6);
            held = 1;
            this.updateDieHeld();
        }
    }

    void setDieContainer() {
        this.diceContainer = new ImageView(this.diceImage);

        this.diceContainer.setPreserveRatio(true);
        this.diceContainer.setFitWidth(100);
        this.diceContainer.setVisible(false);

        this.diceContainer.setOnMousePressed(event -> {
            this.updateDieHeld();
        });
    }

    void setDieImage() {
        this.diceImage = new Image(
                String.format("file:./src/main/java/resources/images/dice/Dice%d.png", this.diceValue));
        this.diceContainer.setImage(this.diceImage);
    }

    void updateDieHeld() {
        if (this.held == 0) {
            diceImage = new Image(
                    String.format("file:./src/main/java/resources/images/diceHeld/Dice%dHeld.png", this.diceValue));
            diceContainer.setImage(diceImage);
            held = 1;
        } else {
            diceImage = new Image(
                    String.format("file:./src/main/java/resources/images/dice/Dice%d.png", this.diceValue));
            diceContainer.setImage(diceImage);
            held = 0;
        }
    }
}