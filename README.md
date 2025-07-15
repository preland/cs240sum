# ♕ BYU CS 240 Chess

This project demonstrates mastery of proper software design, client/server architecture, networking using HTTP and WebSocket, database persistence, unit testing, serialization, and security.

## 10k Architecture Overview

The application implements a multiplayer chess server and a command line chess client.

[Sequence Diagram](https://sequencediagram.org/index.html?presentationMode=readOnly#initialData=IYYwLg9gTgBAwgGwJYFMB2YBQAHYUxIhK4YwDKKUAbpTngUSWDABLBoAmCtu+hx7ZhWqEUdPo0EwAIsDDAAgiBAoAzqswc5wAEbBVKGBx2ZM6MFACeq3ETQBzGAAYAdAE5M9qBACu2AMQALADMABwATG4gMP7I9gAWYDoIPoYASij2SKoWckgQaJiIqKQAtAB85JQ0UABcMADaAAoA8mQAKgC6MAD0PgZQADpoAN4ARP2UaMAAtihjtWMwYwA0y7jqAO7QHAtLq8soM8BICHvLAL6YwjUwFazsXJT145NQ03PnB2MbqttQu0WyzWYyOJzOQLGVzYnG4sHuN1E9SgmWyYEoAAoMlkcpQMgBHVI5ACU12qojulVk8iUKnU9XsKDAAFUBhi3h8UKTqYplGpVJSjDpagAxJCcGCsyg8mA6SwwDmzMQ6FHAADWkoGME2SDA8QVA05MGACFVHHlKAAHmiNDzafy7gjySp6lKoDyySIVI7KjdnjAFKaUMBze11egAKKWlTYAgFT23Ur3YrmeqBJzBYbjObqYCMhbLCNQbx1A1TJXGoMh+XyNXoKFmTiYO189Q+qpelD1NA+BAIBMU+4tumqWogVXot3sgY87nae1t+7GWoKDgcTXS7QD71D+et0fj4PohQ+PUY4Cn+Kz5t7keC5er9cnvUexE7+4wp6l7FovFqXtYJ+cLtn6pavIaSpLPU+wgheertBAdZoFByyXAmlDtimGD1OEThOFmEwQZ8MDQcCyxwfECFISh+xXOgHCmF4vgBNA7CMjEIpwBG0hwAoMAADIQFkhRYcwTrUP6zRtF0vQGOo+RoFmipzGsvz-BwVygYKQH+iMykoKp+h-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-v9gB1jknwPVF2bB8tIAA)

## Modules

The application has three modules.

- **Client**: The command line program used to play a game of chess over the network.
- **Server**: The command line program that listens for network requests from the client and manages users and games.
- **Shared**: Code that is used by both the client and the server. This includes the rules of chess and tracking the state of a game.

## Starter Code

As you create your chess application you will move through specific phases of development. This starts with implementing the moves of chess and finishes with sending game moves over the network between your client and server. You will start each phase by copying course provided [starter-code](starter-code/) for that phase into the source code of the project. Do not copy a phases' starter code before you are ready to begin work on that phase.

## IntelliJ Support

Open the project directory in IntelliJ in order to develop, run, and debug your code using an IDE.

## Maven Support

You can use the following commands to build, test, package, and run your code.

| Command                    | Description                                     |
| -------------------------- | ----------------------------------------------- |
| `mvn compile`              | Builds the code                                 |
| `mvn package`              | Run the tests and build an Uber jar file        |
| `mvn package -DskipTests`  | Build an Uber jar file                          |
| `mvn install`              | Installs the packages into the local repository |
| `mvn test`                 | Run all the tests                               |
| `mvn -pl shared test`      | Run all the shared tests                        |
| `mvn -pl client exec:java` | Build and run the client `Main`                 |
| `mvn -pl server exec:java` | Build and run the server `Main`                 |

These commands are configured by the `pom.xml` (Project Object Model) files. There is a POM file in the root of the project, and one in each of the modules. The root POM defines any global dependencies and references the module POM files.

## Running the program using Java

Once you have compiled your project into an uber jar, you can execute it with the following command.

```sh
java -jar client/target/client-jar-with-dependencies.jar

♕ 240 Chess Client: chess.ChessPiece@7852e922
```
