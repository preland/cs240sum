# ♕ BYU CS 240 Chess

This project demonstrates mastery of proper software design, client/server architecture, networking using HTTP and WebSocket, database persistence, unit testing, serialization, and security.

## 10k Architecture Overview

The application implements a multiplayer chess server and a command line chess client.

[Sequence Diagram](https://sequencediagram.org/index.html?presentationMode=readOnly#initialData=IYYwLg9gTgBAwgGwJYFMB2YBQAHYUxIhK4YwDKKUAbpTngUSWDABLBoAmCtu+hx7ZhWqEUdPo0EwAIsDDAAgiBAoAzqswc5wAEbBVKGBx2ZM6MFACeq3ETQBzGAAYAdAE5M9qBACu2AMQALADMABwATG4gMP7I9gAWYDoIPoYASij2SKoWckgQaJiIqKQAtAB85JQ0UABcMADaAAoA8mQAKgC6MAD0PgZQADpoAN4ARP2UaMAAtihjtWMwYwA0y7jqAO7QHAtLq8soM8BICHvLAL6YwjUwFazsXJT145NQ03PnB2MbqttQu0WyzWYyOJzOQLGVzYnG4sHuN1E9SgmWyYEoAAoMlkcpQMgBHVI5ACU12qojulVk8iUKnU9XsKDAAFUBhi3h8UKTqYplGpVJSjDpagAxJCcGCsyg8mA6SwwDmzMQ6FHAADWkoGME2SDA8QVA05MGACFVHHlKAAHmiNDzafy7gjySp6lKoDyySIVI7KjdnjAFKaUMBze11egAKKWlTYAgFT23Ur3YrmeqBJzBYbjObqYCMhbLCNQbx1A1TJXGoMh+XyNXoKFmTiYO189Q+qpelD1NA+BAIBMU+4tumqWogVXot3sgY87nae1t+7GWoKDgcTXS7QD71D+et0fj4PohQ+PUY4Cn+Kz5t7keC5er9cnvUexE7+4wp6l7FovFqXtYJ+cLtn6pavIaSpLPU+wgheertBAdZoFByyXAmlDtimGD1OEThOFmEwQZ8MDQcCyxwfECFISh+xXOgHCmF4vgBNA7CMjEIpwBG0hwAoMAADIQFkhRYcwTrUP6zRtF0vQGOo+RoFmipzGsvz-BwVygYKQH+iMykoKp+h-Ds0KPMB4lUEiMAIEJ4oYoJwkEkSYCkm+hi7jS+71CA8QoCAarPvE07lipMBqTsc6eXeS7CjAjLMBRMDigAZhAMCpbA+mYCqwYam62q6vq+lGBAahoAA5MwVo2jeUUOkmvrOl28C+f5gUZGAPjvLUtQAHItMyZARmk27uRZ-q9RAbpRjGcaFFpDXwMgqYwOm+GjGMOaqHm8zQUWJb1Ho64ooSahgA29HZaqGqJSlaUcKVqgVcwxxgD5tW8tFFlWXArUBZeHVdWgPX9QozLtCwo0gdU-oitAOhIGukbRigsYKeh8LJst2GrU4ACMBFbTtBYTNMl7QEgABeKC7HRTbDvV30ui1fn-XqgPdSu4OQ25goM-SMCHnIKCBcF7raJFn31ZUy4BmuAaXq+TXtjppYOeKGSqABmCq9DElgYRIXzCRqHfBRVH1ibtEY5h2NgDheEEfpNFkWM5uIZbpEXU2njeH4-heCg6AxHEiSB8HDm+FgomCqB9QNNIEb8RG7QRt0PRyaoCnDO7SE2x+Zm6bn6CmbCGFM81Nn2FH56XhbaCucrHlSwLHAoNwx6XrX8Eew3H0LgKMXIkcEA0DAr0+eKjjF4UOXqsade9yVZWVTA1U5Lat6M41nb1MlJoGFDi1xzAk2BfXM2o3N+dYyU9u4wTG1E-me3FtA9Q+GTeoU9TtONgx-NB4V3qBYVIR8C5lzVkJKOmtta62PjDMCmlEG2zvg7Jw-9GJ+wCCidc-hsDig1PxNEMAADiSoNAx3GqWBopCU7p3sEqHOi884LUqKrF4M9S5fljk1eoyAcjkJzN3SivdG6dj5lvAW8U3SchEfXSWA97yxWSuKdcxUJ7xCngvHuec543RYcHB6K8qrWg3v3fcetLLMx7H2cBO8aj1DPoYtAl80bxgWrfFa6ZH7Zn5MTV+B0FRf3iD-Gm3sAFSKAQ4qyxUMRoAKKUWxCAYBUBNKkcewY0ACjScgdcIACgEB7HkAo4jrHuSpFE-haIhFqAxIoyxQ9rIkJNCkxhOYLFfRiczBo4x2koAAJLSG+JsLRk4iLzBBMkUAapZGQRBP03qkELidHsQ8SB9RiGCIobAhAgFC6YwcbpZY-TVAFl6WMfpQyRljJQHMz4UyEAzPuZMk5SolmfAuDAToyD9aoJWrhdaChizAEsAAHl+vyWpAy0CpXKKYS6vtmL+A4AAdjcE4FATgYgRmCHALiAA2eAE5DC1JgEUO2vD9bx1aB0BhTCQn1yzIspUvzEzsIOZwlxawWVzG4eZbpzUhbolqfI3uPL3lKjKYOSpdVpFMheWKpCDTooyxUWoss7wKyaO0TPK6uUdGiKQsvJ6q915gE3nK6JHZyndl7P2XmCDqWnwgOfXubjr6eMqKJNM+NCb+JfoWN+pZP4UTCX-S6gCrGxImTAeJiTkmpPSYYOY7AckmkRoLQp4ofAlL7o62VLdRyCxJaK3lXJOnSyFN5ElcUKw6j1HFJANA0B1rmB8sQUanW2pZuoaFsKIBrI4cSo8KBam7P2ZAqxukrnSDZeXLxONAXDFnZgpF-tLDtxspsEOSAEhgE3X2CAO6ABSEBxRkIrP4aZ-kKV3ypY4xoTRwb0rmMw3R6AszYCeZuqAcAIA2SgBKuYQz53wg5RsmAIwZ6qR-ZQf9gHgODLnTrA5D6rIACtz1oFFTBsKcG-0AegEhoZ0qdyFoHgyBVEylXoBVVW2WqiJQaLkJPBwhr676vnjPE1z015mItZWxcwCYDJLWSfZxH7XEo3cfNFBi0fUP39bmQNYx9rv2CWGqAVNwl00iVa6NzMsoFpkFUk1tSoxogxLO+ji41XeT+klZKMA9SGH6QVRts7+M1X0W2wwxjTWmJql2kT+8ECH0ddQpxEALMyc9fJxd981rKe2qp9TpYjowBOs5CJQnrVuRAVAMBJnAH1Cw+KUV+kSPSFg6C+DRGoC2cHvZys6JYDuYbfqLzkAYAGGYN+urhHAMubSsVJAzmxNcY1AU4btZ0B5cM81MLEWm5RZgO0YMMwwxIQ9ejL1S00G40zE-ANu0g1BJNGaGs4ZkJ6YW92qyoCxCRYg1+QrYD4FrZGGB-5S68JrqYv7LwoLd37uB-KRAwZYDAGwN+wgebyVUKOTQxOydU7p2MPnV7cIXj8vLoK7y3A8D1KPhRrygsidQBJ12lrbcO6GFaZq1QSHmfGglIlLQ8h7sibEy99Zb3RP2tQ1O7tukfsKbtugzBQA)

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
