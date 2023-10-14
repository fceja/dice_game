package lib;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
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
        Dice[] dicerArr = new Dice[diceAmount];

        for (int i = 0; i < diceAmount; i++) {
            Dice tempDice = new Dice();
            tempDice.setDieContainer();
            tempDice.diceIndex = i + 1;

            dicerArr[i] = tempDice;
        }

        return dicerArr;
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

        int freq1 = map.containsKey(1) ? map.get(1) : 0;
        int freq2 = map.containsKey(2) ? map.get(2) : 0;
        int freq3 = map.containsKey(3) ? map.get(3) : 0;
        int freq4 = map.containsKey(4) ? map.get(4) : 0;
        int freq5 = map.containsKey(5) ? map.get(5) : 0;
        int freq6 = map.containsKey(6) ? map.get(6) : 0;

        // 5 of a kind
        if (freq1 == 5 || freq2 == 5 || freq3 == 5 || freq4 == 5 || freq5 == 5 || freq6 == 5) {
            return 10;
        }

        // Straight
        else if ((freq1 == 1 && freq2 == 1 && freq3 == 1 && freq4 == 1 && freq5 == 1) ||
                (freq2 == 1 && freq3 == 1 && freq4 == 1 && freq5 == 1 && freq6 == 1)) {
            return 8;
        }

        // 4 of a kind
        else if (freq1 == 4 || freq2 == 4 || freq3 == 4 || freq4 == 4 || freq5 == 4 || freq6 == 4) {
            return 7;
        }

        // Full house
        else if ((freq1 == 2 || freq2 == 2 || freq3 == 2 || freq4 == 2 || freq5 == 2 || freq6 == 2) &&
                (freq1 == 3 || freq2 == 3 || freq3 == 3 || freq4 == 3 || freq5 == 3 || freq6 == 3)) {
            return 6;
        }

        // 3 of a kind
        else if (freq1 == 3 || freq2 == 3 || freq3 == 3 || freq4 == 3 || freq5 == 3 || freq6 == 3) {
            return 5;
        }

        // 2 pair
        else if ((freq1 == 2 && freq2 == 2) || (freq1 == 2 && freq3 == 2) || (freq1 == 2 && freq4 == 2)
                || (freq1 == 2 && freq5 == 2) || (freq1 == 2 && freq6 == 2) ||
                ((freq2 == 2 && freq3 == 2) || (freq2 == 2 && freq4 == 2) || (freq2 == 2 && freq5 == 2)
                        || (freq2 == 2 && freq6 == 2))
                ||
                ((freq3 == 2 && freq4 == 2) || (freq3 == 2 && freq5 == 2) || (freq3 == 2 && freq6 == 2)) ||
                ((freq4 == 2 && freq5 == 2) || (freq4 == 2 && freq6 == 2)) ||
                ((freq5 == 2 && freq6 == 2))) {
            return 4;
        }

        // 2 of a kind
        else if (freq1 == 2 || freq2 == 2 || freq3 == 2 || freq4 == 2 || freq5 == 2 || freq6 == 2) {
            return 1;
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
        this.diceImage = new Image(String.format("file:./src/main/java/resources/images/Dice%d.png", this.diceValue));
        this.diceContainer.setImage(this.diceImage);
    }

    void updateDieHeld() {
        if (this.held == 0) {
            diceImage = new Image(
                    String.format("file:./src/main/java/resources/images/Dice%dHeld.png", this.diceValue));
            diceContainer.setImage(diceImage);
            held = 1;
        } else {
            diceImage = new Image(String.format("file:./src/main/java/resources/images/Dice%d.png", this.diceValue));
            diceContainer.setImage(diceImage);
            held = 0;
        }
    }
}