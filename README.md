# Game of Life
Conway's Game of Life implemented in Java using JavaFX.

![screenshot](images/screenshot.png)

To learn about **Conway's Game of Life** check the [wikipedia article](https://en.wikipedia.org/wiki/Conway%27s_Game_of_Life).

## How to install
### 1. Download JAR
A self-executable **JAR** is available for download through the releases tab.

### 2. Compile Source Code
To compile from source you will need **JDK 11**, other dependencies should be downloaded by Gradle.

1. Clone the repository:

    `git clone https://github.com/sudo-sturbia/Game-of-Life.git`

2. As the project uses **Gradle** as a build tool, to make sure the Gradle version is correct use the provided wrapper:

    `./gradlew build`

3. To run use:

    `./gradlew run`

The above steps can also be performed using an IDE.

## Features

- A minimal design with a simple configuration prompt.
- Dynamic grid size specified by the user at start.
- An iterations counter.
