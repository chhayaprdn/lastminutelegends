# Last Minute Legends (Group 19)
A 2D maze game built with Java and Swing.

Play as a student trying to escape deadlines by collecting all
school supplies and reaching the exit before the enemies catch you.

A short video demonstrating the gameplay and key features of our game 
can be found at the following link:
https://youtu.be/klDdKuBAhUM?si=KlGU-rjbJwbXQcKO

### Requirements
- Java 21+
- Maven 3.9+

### Build
To build the project run `mvn compile`

### Run
To run the game run `mvn compile exec:java`

### Run from JAR
To build and run the JAR:
```
mvn clean package
java -jar target/last-minute-legends.jar
```

### Test
To run the test suite run `mvn test`

To generate a coverage report run `mvn verify`. The coverage report will be at `target/site/jacoco/index.html`

### Javadocs
To generate the Javadocs run `mvn javadoc:javadoc`. The documentation will be at `target/javadoc/index.html`
