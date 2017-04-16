# What is Lunar Lockout?
[Lunar Lockout](http://www.puzzles.com/products/LunarLockout/LunarLockoutStart.htm "Lunar Lockout's Website") is a game played on a 5x5 board with up to 6 space robots. The objective of the game is to move the player robot (red) to the center (the motherships landing grid) at (3, 3).

![Lunar Lockout Game Pic](http://www.jeffbots.com/lockout1.jpg)

#### Rules:
- All robots can only move Up, Right, Down, or Left
- All robots can only move by moving straight into another robot
- The walls of the board do not count as something Robots can move into

# How this program works:
This program attempts to generate and/or solve any Lunar Lockout level. Using the parameters below, it can read off a JSON input file, the generated level, or user input. It can then output that board data and solution and board data into a board file.

## How to use it
- [Download](https://github.com/duecknoah/lunar-lockout-solver/releases) the lunar-lockout-solver.jar file
- Open your command-line (CMD for windows, Terminal for Mac and Linux)
- Change your current directory to the location of the jar file you downloaded by typing:
```
cd /directory/to/jarfile/
```
To get the help screen, type:
```
java -jar LLSolver.jar --help
```
## Now the fun part
### Generating levels
To generate a lunar lockout level with 4 total robots, a minimum of 5 steps to solve, and a maximum of 10 steps to solve. Type:
```
java -jar LLSolver.jar -g -t 4 -min 5 -max 10
``` 
To to generate a board with 3 robots, a minimum of 2 steps, a maximum of 10 steps, and output it to the board file generatedBoard.dat
```
java -jar LLSolver.jar -g -t 3 -min 2 -max 10 -out "generatedBoard.dat"
``` 
### Solving levels
If you want to solve a board off of board data via user input and output the board file, type:
```
java -jar LLSolver.jar -s -out "solvedBoard.dat"
```
If you want to solve a board off of a created board file:
```
java -jar LLSolver.jar -s -in "generatedBoard.dat" -out "solvedBoard.dat"
```
### Generating and Solving
If you want to be fancy, you can generate and solve the generated board all in one go:
```
java -jar LLSolver.jar -g -s -t 5 -min 5 -max 10 -out "solvedBoard.dat"
```


# Board Files:
Board files contain data in this order:
- JSON board file for the program to read off of
- Board layout in a human readable form, showing positions of each robot, and their color.
- The solution (if any)

#### An example of a Board File:
```JSON
[{"isPlayer":true,"color":"Red","pos":{"x":1,"y":3},"posPrev":{"x":1,"y":3}},{"isPlayer":false,"color":"Orange","pos":{"x":4,"y":3},"posPrev":{"x":4,"y":3}},{"isPlayer":false,"color":"Yellow","pos":{"x":1,"y":1},"posPrev":{"x":1,"y":1}},{"isPlayer":false,"color":"Green","pos":{"x":1,"y":4},"posPrev":{"x":1,"y":4}}]

Board data:
Player Robot (Red) at position (1, 3)
Robot (Orange) at position (4, 3)
Robot (Yellow) at position (1, 1)
Robot (Green) at position (1, 4)

Solution:
Total moves: 5
1: Red up
2: Red down
3: Red up
4: Red down
5: Red right
```
