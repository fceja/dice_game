## dice_game

Description
---
A poker style game with Dice.

Screenshot
---

![alt text](src/images/screenshot/screenshot-ui.png "Screenshot of UI")



Installation
---
1.
- Install Java 17
    - https://www.oracle.com/java/technologies/javase/jdk17-archive-downloads.html
- JavaFx 20.0.1
    - https://openjfx.io/
- Maven 3.3.9
    - https://maven.apache.org/download.cgi

2. Clone repository

3. Compile project with maven
- mvn clean install

4. Execute:
- java --module-path /Library/Java/javafx-sdk-20.0.1/lib --add-modules javafx.controls,javafx.fxml -jar target/jar-output/dice_game-1.0.0.jar
