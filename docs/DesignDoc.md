# PROJECT Design Documentation

## Executive Summary
This is a summary of the project.

### Purpose
The purpose of this project is to create a web application using Java Spark to allow for two Users to play an online game of American Rules Checkers. 

### Glossary and Acronyms

| Term | Definition |
|------|------------|
| VO | Value Object |
| Board | The game surface that contains all the pieces, comprised of 8 rows of 8 Spaces |
| Coordinate | A representation of a Space’s location on a Board |
| Game | The occurrence of two players actually competing against each other at checkers | 
| Message | A text message with a corresponding message type (“error” or “info”) that provides feedback to a player |
| Move | Two coordinates that represent taking a piece from one space and putting it in the other |
| Piece | A token used to play the game.  Each player starts with 12 | 
| Player | Synonymous with User, someone who plays a game on the application |
| Row | A set of 8 Spaces, represented as one horizontal row on the game board | 
| Space | Each colored square on the game board, these can hold pieces on them |


## Requirements
This section describes the features of the application.


### Definition of MVP
A player can begin a game of American Rules Checkers against another Player through a Web Application.

### MVP Features
- As a Player, I want to start a game so I can challenge other online Players.
- As a Player, I want to log in to the application so I an access the functionality.
  - Usernames must be unique and are not maintained when a user signs off.
- As a Player, I want to see a list of Players I can challenge so I can challenge them to a game.
- As a Player, I want to move pieces on my client so that I can take my turn and see the state of the game.
  - Players can also undo moves before submitting them
- As a System, I want to validate the legality of player moves and enforce American Checkers Rules so that the game can be played properly.
- As a Player, I want to jumpo over the opponenet's piece so that I can capture it.
- As a Player, I want to reach the end of the board with my pieces so that they become kings and are more powerful.
- As a Player, I want to be alerted when I have won a game so that I can know the game is over

### Roadmap of Enhancements
Time permitting, the following enhancements will be implemented:
  - A player can spectate other players' matches.
  - A player can accept or deny another player's challenge to a game

## Application Domain
This section describes the application domain.

### Overview of Major Domain Areas
Specific definitions of each domain area are available in the glossary section.  Our domain model is below, which shows the relationships between the various components of the domain.  At its core, two Players play a Game (which is made of a Board, Rows, Spaces, & Pieces) by changing the positions of Pieces with Moves.

![Domain Model](http://andydistasi.com/dev/610Models/610DomainModel.png)

#### Player Details
A player is a User of the Application, represented by a username, who plays games against other Players

#### Game Details
A game is an instance of two Player's playing checkers against each other.  The game is played on a board, which is comprised of 8 Rows of 8 Squares each.  Squares contain information about their color, location, and whether or not they have a piece on them.  Some of these Squares on either side of the Board contain 1 of the Player's 12 starting pieces.  Players complete the game by making moves, moving a Piece from one starting coordinate to an ending coordinate.  Moves can only be one row forward, and msut be diagonal.  Players can also capture their opponent's pieces by jumping over them.  If a Player can make a jump, they must take it and are allowed to make multiple jumps in one turn.  A Game ends when a Player has lost all their pieces or cannot make any moves/



## Architecture
This section describes the application architecture.

### Summary
The application uses the Java Spark Framework with Freemarker Templates to render and serve web pages to a User.  JavaScript and JSON are used to facilitate communication between the client and the server.  The project itself is split into three tiers: Appl (Application), Model, & UI (User Interface).  The application tier contains functionality to manage the state of the application and session data.  The Model tier handles the creation, validation, and management of gameplay and of Players.  The UI tier handles passing requests and input from the Client to the Server and returning the Server’s response to the Client.  Outside of the application itself, there is a set of resources (Freemarker templates, JavaScript & CSS files, and image files) that are used to render the views shown to the user.

### Overview of User Interface
Accessing the application brings the user to a home page.  If they are not logged in, they will see a link that takes them to the login page.  The login page prompts the user to log in by supplying a username.  If the username already exists in the system, an error message is displayed prompting the user to input a different name.  A successful login returns the user to the home page.  When a logged in user accesses the home page, they see a list of users who are logged in but not currently in a game.  Clicking on a username starts a game between that user and the player who has the username that they clicked on.  At any point, the user can click “sign out,” which will end any game they are in and sign them out of the application.

Once in a game, a user is presented with a game board, an indicator of whose turn it is, and a set of 4 controls: “Backup one move,” “Submit turn,” “Reset turn,” and “Resign from game.”  Users can complete a move by clicking and dragging a piece of their color to a new (valid) square.  If the move is valid, the piece will be displayed in the new square.  If it is invalid, the piece will remain in its original square and an error message will be displayed.  After completing a valid move, the “Controls” links will be enabled.  Selecting the “Backup one move” will undo the most recent move and return the board to its previous state.  Selecting “Reset turn” will undo all moves completed on that turn.  Selecting “Submit turn” will submit that user’s turn, saving it to the game (meaning it can no longer be undone).  After a turn is submitted, the game will automatically update the other player’s board to reflect the new board state.  Clicking “Resign from game” will cause that player to forfeit and end the game, taking both players back to the home page.

![State Diagram](http://andydistasi.com/dev/610Models/610StateDiagram.png)

### Application Tier
The application tier is comprised of the GameCenter class that manages state functionality.  It handles validating usernames, creating and removing user accounts and games, starting games between two players, and getting a list of all available players.

### Model Tier
The model tier handles representing the domain objects, completing each player’s turn, and validating in-game actions.  It contains a series of Entity Objects: Board, Game, Message, Piece, Player, Row, and Space.  These are supplemented by Value Objects: Coordinate and Move.  These classes work together to represent a game between two players and to provide logic that governs how players can move their pieces.

### UI Tier
The UI tier is composed of the WebServer class that initializes the TemplateEngine and GameCenter, in addition to binding various Route Handling Classes to specific URLs.  It is also composed of a series of Route Handling classes that handle HTTP GET and POST requests.  These classes are used to accept input from the user, perform any required input validation, convert the data into the appropriate format, and either pass information to the Model and Application Tier or render the appropriate template view.  These classes also contain a series of static constant strings used throughout the UI Tier.


## Game Model Sub-System
This section describes the detail design of The Game Model Sub-System.

### Purpose of the sub-system
A Game Object stores the players playing, the board being played on (and piece position), who's turn it is, the moves made on that turn, and any pieces captured on that turn.  A Game object tracks the state of the game being played and implements functionality to undo moves.  The Board object referenced by the game is where all movement validation takes place (determined by standard American Checkers gameplay rules).  Valid moves cause the board object to be updated and reflected to the Users through Messages returned to them.

### Static models
![State Diagram](http://andydistasi.com/dev/610Models/610ClassDiagram.png)
### Dynamic models
![Sequence Diagram](http://andydistasi.com/dev/610Models/610SequenceDiagram.png)
