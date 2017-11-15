# WebCheckers

An online Checkers game system built in Java 8 and Spark, a web microframework.

## Team

- Dalal, Niharika
- DiStasi, Andrew
- Kesari, Ashok Sudheer
- Pujols, Virginia A.



## Prerequisites

- Java 8
- Maven

## Enhancements Implemented
Beyond the requirements of the MVP, the following enhancements were implemented:
- Players are able to spectate a Game in progress as it occurs
- Players are able to see the Win/Loss record of all other players currently logged in to the application.  (Win/Loss record is not maintained between sessions)
- The Board is rotated for each player so that their pieces are located at the bottom of the screen (simulating sitting in front of an actual checkerboard)

## How to run it

1. Clone the repository and go to the root directory.
2. Execute `mvn compile exec:java`
3. Open in your browser `http://localhost:4567/`
4. Start a game and begin playing.
5. Note that you must open tabs in multiple browsers if you want to have two instances running on one computer

## Code Metrics Overview
This section shows package level screenshots for Code Coverage and Code Complexity

### Code Coverage
![Project Code Coverage](http://andydistasi.com/dev/610Models/610CodeCoverage.PNG)

### Code Complexity
![Project Comlexity](http://andydistasi.com/dev/610Models/610PackageComplexity.PNG)


## License
MIT License.
See LICENSE for details.
