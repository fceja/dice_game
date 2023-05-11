import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.Random;

public class Dice {

    int diceValue;
    int held = 0; // 1 if held, 0 if not
    int diceIndex;

    Image diceImage;
    ImageView diceSlot;

    static Dice[] createDice(int diceAmount) {
        Dice[] dicerArr = new Dice[diceAmount];

        for (int i = 0; i < diceAmount; i++){
            Dice tempDice = new Dice();
            tempDice.diceImage = new Image("file:./images/Dice1.png");
            tempDice.diceSlot = new ImageView(tempDice.diceImage);

            tempDice.diceSlot.setPreserveRatio(true);
            tempDice.diceSlot.setFitWidth(100);
            tempDice.diceSlot.setVisible(false);

            tempDice.diceSlot.setOnMousePressed(event -> {
                tempDice.updatePic();
            });

            tempDice.diceIndex = i+1;

            dicerArr[i] = tempDice;
        }

        return dicerArr;
    }

    void setDiePic() {
        if (diceValue == 1) {
            diceImage = new Image("file:./images/Dice1.png");
            diceSlot.setImage(diceImage);
        } else if (diceValue == 2) {
            diceImage = new Image("file:./images/Dice2.png");
            diceSlot.setImage(diceImage);
        } else if (diceValue == 3) {
            diceImage = new Image("file:./images/Dice3.png");
            diceSlot.setImage(diceImage);
        } else if (diceValue == 4) {
            diceImage = new Image("file:./images/Dice4.png");
            diceSlot.setImage(diceImage);
        } else if (diceValue == 5) {
            diceImage = new Image("file:./images/Dice5.png");
            diceSlot.setImage(diceImage);
        } else {
            diceImage = new Image("file:./images/Dice6.png");
            diceSlot.setImage(diceImage);
        }
    }

    void updatePic() {
        switch (diceValue) {
            case 1:
                if (held == 0) {
                    diceImage = new Image("file:./images/Dice1Held.png");
                    diceSlot.setImage(diceImage);
                    held = 1;
                } else {
                    diceImage = new Image("file:./images/Dice1.png");
                    diceSlot.setImage(diceImage);
                    held = 0;
                }
                break;
            case 2:
                if (held == 0) {
                    diceImage = new Image("file:./images/Dice2Held.png");
                    diceSlot.setImage(diceImage);
                    held = 1;
                } else {
                    diceImage = new Image("file:./images/Dice2.png");
                    diceSlot.setImage(diceImage);
                    held = 0;
                }
                break;
            case 3:
                if (held == 0) {
                    diceImage = new Image("file:./images/Dice3Held.png");
                    diceSlot.setImage(diceImage);
                    held = 1;
                } else {
                    diceImage = new Image("file:./images/Dice3.png");
                    diceSlot.setImage(diceImage);
                    held = 0;
                }
                break;
            case 4:
                if (held == 0) {
                    diceImage = new Image("file:./images/Dice4Held.png");
                    diceSlot.setImage(diceImage);
                    held = 1;
                } else {
                    diceImage = new Image("file:./images/Dice4.png");
                    diceSlot.setImage(diceImage);
                    held = 0;
                }
                break;
            case 5:
                if (held == 0) {
                    diceImage = new Image("file:./images/Dice5Held.png");
                    diceSlot.setImage(diceImage);
                    held = 1;
                } else {
                    diceImage = new Image("file:./images/Dice5.png");
                    diceSlot.setImage(diceImage);
                    held = 0;
                }
                break;
            case 6:
                if (held == 0) {
                    diceImage = new Image("file:./images/Dice6Held.png");
                    diceSlot.setImage(diceImage);
                    held = 1;
                } else {
                    diceImage = new Image("file:./images/Dice6.png");
                    diceSlot.setImage(diceImage);
                    held = 0;
                }
                break;
            default:
                System.out.println("DEBUG-ERROR");
                break;
        }
    }

    static void createRandomStart(Dice[] diceArr) {
        Random randNum = new Random();

        for (Dice dice : diceArr) {
            dice.diceValue = 1 + randNum.nextInt(6);
            dice.setDiePic();
        }
    }

    void rollDieIfNotHeld() {
        // 1 held, 0 not held
        if (held == 0) {
            Random randNum = new Random();
            diceValue = 1 + randNum.nextInt(6);
            held = 1;
            this.updatePic();
        }
    }

    static int findCurrScore(Dice[] diceArr) {
        int[] array = { 0, 0, 0, 0, 0, 0 };

        for (Dice dice : diceArr) {
            array = dice.findTally(array);
        }

        int found1 = array[0];
        int found2 = array[1];
        int found3 = array[2];
        int found4 = array[3];
        int found5 = array[4];
        int found6 = array[5];

        // 5 of a kind
        if (found1 == 5 || found2 == 5 || found3 == 5 || found4 == 5 || found5 == 5 || found6 == 5) {
            return 10;
        }

        // Straight
        else if ((found1 == 1 && found2 == 1 && found3 == 1 && found4 == 1 && found5 == 1) ||
                (found2 == 1 && found3 == 1 && found4 == 1 && found5 == 1 && found6 == 1)) {
            return 8;
        }

        // 4 of a kind
        else if (found1 == 4 || found2 == 4 || found3 == 4 || found4 == 4 || found5 == 4 || found6 == 4) {
            return 7;
        }

        // Full house
        else if ((found1 == 2 || found2 == 2 || found3 == 2 || found4 == 2 || found5 == 2 || found6 == 2) &&
                (found1 == 3 || found2 == 3 || found3 == 3 || found4 == 3 || found5 == 3 || found6 == 3)) {
            return 6;
        }

        // 3 of a kind
        else if (found1 == 3 || found2 == 3 || found3 == 3 || found4 == 3 || found5 == 3 || found6 == 3) {
            return 5;
        }

        // 2 pair
        else if ((found1 == 2 && found2 == 2) || (found1 == 2 && found3 == 2) || (found1 == 2 && found4 == 2)
                || (found1 == 2 && found5 == 2) || (found1 == 2 && found6 == 2) ||
                ((found2 == 2 && found3 == 2) || (found2 == 2 && found4 == 2) || (found2 == 2 && found5 == 2)
                        || (found2 == 2 && found6 == 2))
                ||
                ((found3 == 2 && found4 == 2) || (found3 == 2 && found5 == 2) || (found3 == 2 && found6 == 2)) ||
                ((found4 == 2 && found5 == 2) || (found4 == 2 && found6 == 2)) ||
                ((found5 == 2 && found6 == 2))) {
            return 4;
        }

        // 2 of a kind
        else if (found1 == 2 || found2 == 2 || found3 == 2 || found4 == 2 || found5 == 2 || found6 == 2) {
            return 1;
        }

        return 0;
    }

    int[] findTally(int[] array) {
        switch (diceValue) {
            case 1:
                array[0] = array[0] + 1;
                break;
            case 2:
                array[1] = array[1] + 1;
                break;
            case 3:
                array[2] = array[2] + 1;
                break;
            case 4:
                array[3] = array[3] + 1;
                break;
            case 5:
                array[4] = array[4] + 1;
                break;
            case 6:
                array[5] = array[5] + 1;
                break;

            default:
                System.out.println("DEBUG-ERROR");
                break;
        }
        return array;
    }
}