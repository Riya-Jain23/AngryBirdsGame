# AngryBirdsGame
A simple LibGDX Java based implementation of the popular Angry Birds game. 
Made by 

Shubhi Jain (2023517)

and 

Riya Jain (2023441)


## Implementation
### Game Structure
AngryBirds class which extends Game, manages main game components and transition between screens.
### Components
Bird Class

Ground Class

Obstacle class

Pig Class

Slingshot Class

### Game logic
The create method in AngryBirds initializes the game components, including the ground, birds, and background music.

The render method updates the game state and redraws all components on each frame.
### Menus
The main menu allows the player to start the game or access settings. This is managed through screens.
### Background Music
Background music is loaded and played in a loop to enhance the gaming experience, using the Music class from LibGDX.

## Instructions to run the code
Java Development Kit (JDK) 8 or higher
Gradle
### Installation
1. Clone the repo, code -> https -> copy url to clipboard -> git clone <url> -> change directory to this folder
2. Import it into Intellij
3. Add LibGDX dependencies, update your build.gradle file with LibGDX dependencies
4. Run
5. Interact with the game using Buttons

## Concepts
Classes: The game consists of multiple classes, such as Bird, Ground, Alien, Obstacle, and Slingshot. Each class encapsulates properties and behaviors related to specific game entities.

Objects: Instances of these classes are created to represent real-world entities in the game.

Encapsulation: Classes like for example Bird class has private fields for its position and texture, providing public methods to access these attributes (getters) and methods to manipulate its state.

Inheritance: (will be used in future) will be implemented by creating a base class  that includes shared properties and methods for all game entities (birds, obstacles, etc.). Derived classes can then inherit from this base class.

Polymorphism: (will be used in future) can be applied through method overriding. For example, different types of birds with specific behaviors, each bird class will override a common method from the base class Bird to define its unique behavior.

Abstraction: The Ground, Bird, Alien, and Obstacle classes abstract the details of rendering and behavior, allowing other parts of the code to interact with them without needing to know their internal workings.

Composition: The AngryBirds class contains instances of Bird, Ground, and other entities, allowing it to manage the game's state and behavior. This design promotes a modular approach where components can be easily reused or replaced.

## References
libGDX documentation

previous year projects

Java documentation

Canva, Figma, https://www.spriters-resource.com, etc for sprites and assets

Course Slides


=======
# Angry-Birds

A [libGDX](https://libgdx.com/) project generated with [gdx-liftoff](https://github.com/libgdx/gdx-liftoff).

This project was generated with a template including simple application launchers and an `ApplicationAdapter` extension that draws libGDX logo.

## Platforms

- `core`: Main module with the application logic shared by all platforms.
- `lwjgl3`: Primary desktop platform using LWJGL3.

## Gradle

This project uses [Gradle](https://gradle.org/) to manage dependencies.
The Gradle wrapper was included, so you can run Gradle tasks using `gradlew.bat` or `./gradlew` commands.
Useful Gradle tasks and flags:

- `--continue`: when using this flag, errors will not stop the tasks from running.
- `--daemon`: thanks to this flag, Gradle daemon will be used to run chosen tasks.
- `--offline`: when using this flag, cached dependency archives will be used.
- `--refresh-dependencies`: this flag forces validation of all dependencies. Useful for snapshot versions.
- `build`: builds sources and archives of every project.
- `cleanEclipse`: removes Eclipse project data.
- `cleanIdea`: removes IntelliJ project data.
- `clean`: removes `build` folders, which store compiled classes and built archives.
- `eclipse`: generates Eclipse project data.
- `idea`: generates IntelliJ project data.
- `lwjgl3:jar`: builds application's runnable jar, which can be found at `lwjgl3/build/libs`.
- `lwjgl3:run`: starts the application.
- `test`: runs unit tests (if any).

Note that most tasks that are not specific to a single project can be run with `name:` prefix, where the `name` should be replaced with the ID of a specific project.
For example, `core:clean` removes `build` folder only from the `core` project.
>>>>>>> a401c12 (Initial commit - Adding libGDX project files)
