# Description
A poker style game with Dice.

<br/>

## Installation and Execution

1. Requirements

   - Java 17
     - https://www.oracle.com/java/technologies/javase/jdk17-archive-downloads.html
   - JavaFx 21
     - https://openjfx.io/
   - Maven 3.9.5
     - https://maven.apache.org/download.cgi

2. Clone repository

3. Compile project with maven

   - Note: may need to update paths in `pom.xml` to project paths.
   - ```
     mvn clean install
     ```

4. Execute:
   - ```
     java --module-path {path_to_javafx_lib} --add-modules javafx.controls,javafx.fxml -jar target/jar-output/dice_game-1.0.0.jar
     ```

<br/>

## Screenshot
![alt text](src/main/java/resources/images/screenshot/screenshot-ui.png "Screenshot of UI")

<br/>

## Tools and Technologies
<p>
  <a href="https://developer.mozilla.org/en-US/docs/Web/JavaScript" target="_blank" rel="noreferrer">
    <img 
      src="https://cdn.jsdelivr.net/gh/devicons/devicon/icons/javascript/javascript-plain.svg"
      alt="javascript"
      width="40"
      height="40"
    /></a>
</p>
