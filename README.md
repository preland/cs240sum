# ♕ BYU CS 240 Chess

This project demonstrates mastery of proper software design, client/server architecture, networking using HTTP and WebSocket, database persistence, unit testing, serialization, and security.

## 10k Architecture Overview

The application implements a multiplayer chess server and a command line chess client.

[Sequence Diagram](https://sequencediagram.org/index.html?presentationMode=readOnly#initialData=IYYwLg9gTgBAwgGwJYFMB2YBQAHYUxIhK4YwDKKUAbpTngUSWDABLBoAmCtu+hx7ZhWqEUdPo0EwAIsDDAAgiBAoAzqswc5wAEbBVKGBx2ZM6MFACeq3ETQBzGAAYAdAE5M9qBACu2AMQALADMABwATG4gMP7I9gAWYDoIPoYASij2SKoWckgQaJiIqKQAtAB85JQ0UABcMADaAAoA8mQAKgC6MAD0PgZQADpoAN4ARP2UaMAAtihjtWMwYwA0y7jqAO7QHAtLq8soM8BICHvLAL6YwjUwFazsXJT145NQ03PnB2MbqttQu0WyzWYyOJzOQLGVzYnG4sHuN1E9SgmWyYEoAAoMlkcpQMgBHVI5ACU12qojulVk8iUKnU9XsKDAAFUBhi3h8UKTqYplGpVJSjDpagAxJCcGCsyg8mA6SwwDmzMQ6FHAADWkoGME2SDA8QVA05MGACFVHHlKAAHmiNDzafy7gjySp6lKoDyySIVI7KjdnjAFKaUMBze11egAKKWlTYAgFT23Ur3YrmeqBJzBYbjObqYCMhbLCNQbx1A1TJXGoMh+XyNXoKFmTiYO189Q+qpelD1NA+BAIBMU+4tumqWogVXot3sgY87nae1t+7GWoKDgcTXS7QD71D+et0fj4PohQ+PUY4Cn+Kz5t7keC5er9cnvUexE7+4wp6l7FovFqXtYJ+cLtn6pavIaSpLPU+wgheertBAdZoFByyXAmlDtimGD1OEThOFmEwQZ8MDQcCyxwfECFISh+xXOgHCmF4vgBNA7CMjEIpwBG0hwAoMAADIQFkhRYcwTrUP6zRtF0vQGOo+RoFmipzGsvz-BwVygYKQH+iMykoKp+h-Ds0KPMB4lUEiMAIEJ4oYoJwkEkSYCkm+hi7jS+71CA8QoCAarPvE07lipMBqTsc6eXeS7CjAjLMBRMDigAZhAMCpbA+mYCqwYam62q6vq+lGBAahoAA5MwVo2jeUUOkmvrOl2MA9n227uRZ-oAHIQG6UYxnGhRaQ18DIKmMDpvhoxjDmqh5vM0FFiW9R6OuKKEmoYANvR2WqhqiUpWlHClaoFXMMcYA+bVvLRRZVnJSaBjtSB1T+iK0A6Ega6RtGKCxgp6HwsmY3YRNTgAIwEbN80FhM0yXtASAAF4oLsdFNsO9V3S6MAWKkz0eTd-LeROKCBcF7raJFROLpUy4BmuAaXq+TXtjppYOeKGSqABmDsy9ElgYRIXzCRqHfBRVH1mLtGA5hINgDheEEfpNFkWMkuIdLpHbU2njeH4-heCg6AxHEiTG6bDm+FgomCqB9QNNIEb8RG7QRt0PRyaoCnDJrSFyx+Zm6f76CmbCGHY81Nn2Db56XlLaCuazhMLqORgoNwx6XvH8Fa0n11p-esUojMEA0DAF0+eKjih4UOXqsaCf5yVZWVTA1U5Lat5Y41nb1A9CBPW5As1PUPWBYn-V-YNgfAyUitg5D03Q-mi3FtA9Q+PDeqIyjaONgxmOLlH9R42II8jez9TW6e3O8-zI0Oy1vb9sN8-jbhTiH4xBsBCi65-DYHFBqfiaIYAAHElQaDtp1UsDQIFu09vYJUftm4B2GpUa+MARh13Dl+e2TV6jIByFAnMudKL52Tp2QUx907xTdJyChidqZFxigPcU65ipV3iDXJuecA4N32ug02x025VWtF3Qu+5R5WVam-FOcDx4QEnvnae-14zv0qKJNMEMob8hhuvZaCod7xD3qjXWR8e4nz7pZHGWVL5UmsenEhYAyFqAppyVhMj2HWXASgnM6VoCVzkNXBwZZ3hKmkbdWxVkFDFmAJYAAPHAXy6h3EAEk0CpXKATIOEcOZoncaoe+CBALByBrY3S8SoCJJSWk1QmTskQHKJpV6lTRoLyVlNGpdTUn8iaTk0wO19bMX8BwAA7G4JwKAnAxAjMEOAXEABs8BSaQIrEUBWhDBaO1aB0ZBqDTGJyzAElAXUlRtMFtpCpLw65rDORcuY+DzKxJxoeOQKB3HMPzg8pUTyuQEycXVekcUmSMKVD8pC3jop01islThESjQ8L4XXXauV+GUKQq3U67dO5gG7iCgUT8iEvzapfJRMAJ4iLQOo2eWjOnjXTMvbMBi16Fg3qWbeFFzEHx2nQ2R9iiIXxTsCmm6cPnom+fpGF9U4UkyPIYM5BU9RIorPoMKCBEm0AFSS-u8AGmDIgHkrBty1mKvcaU8pBTR66TORk6QVzEwf1Bl-YY9rpA-1GYbSwmcbKbDNkgBIYBfV9ggAGgAUhAcUGy5gxGSKANUWyF47LHo0JozIZI9DOWggR6AszYC1b6qAcAIA2SgH8uYDqnWR1NQUu5NLVJFsoKW8tlaUDVr5hU1NVkABW0a0DfPuZq7VJay3QHbQ66hdj3JirTgycFwqoXoFlbTIUHCJTcNCbw8JaKhGYsTjis6HdJEEuib3DsM7uyvxNZe7qKiaV0oBgynRS99G5nZWMJam8TE8qgMjCx6MrFEsFc1BxoqZDOPqP28U0rhWTukE20drboCruJfKys6JYDKp1Kqj1uM0oGGYIW5D47YCQDVXGpAyUyX9n3cqsRuKJE1V1afdKj0RU0L1Wmnq7in2aPafLLpYMpqso-QtDlxjVowHWs5Sx6LG4gDI7jcMhRWNvOaoPYeiiqmlnaMGGYYYkL8aGoJkar70yZhXmyiTX7OX1BNGaGsqn5PqcvVZc+t7sGecfpS+Rc9tEK26V6pihsvCJMDcG8L8pEDBlgMAbAhbCB5AKDAZN5hU2SWdq7d2ntjCBzrV+a9bVH4ae8twPAGJp2DjnV5GAIAKtQCq+etd9MOCZyZIYE0CAImqHbX140EpEpaHkC14lbH-MUsK3CYr-ZfO6dmwFxlrq8I-yAA)

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
