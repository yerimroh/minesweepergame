# Introduction 
A basic minesweeper game that is played on the terminal.
This program generates minesweeper boards(fields) based on a **seed file** that is supplied along while launching(or running) the program on the terminal.


_Was a cs1302 course project 


## How to Play
The game initially accepts user inputs to process. Each round, the user has five different options to put in as command-line input.

The player wins if and only if when 
**1) all the grids that do not contain a mine are revealed**, 
and 
**2) all the grids that do contain a mine are marked as 'definitly' containing a mine.**

### Valid commands
* Reveal a square on the grid to see if it contains a mine. => **r** or **reveal**
* Mark a square on the grid as 'potentially' containing a mine. => **g** or **guess**
* Maek a square on the grid as 'definitly' containing a mine. => **m** or **mark**
* Ask for help info. **h** or **help**
and
* Quit(Exit) the game. **q** or **quit**

### Syntax

#### Reveal a grid
```
$ r 1 2
```
```
$ reveal 1 2
```
=> Reveal position [1][2] grid:
If the grid **does not** contain a mine, shows the number of mines around the grid.
If the grid **does** contain a mine, shows the <game over> screen and exit the game.
***
  
#### Mark as 'Potentially' containing a mine.
```
$ g 1 2
```
```
$ guess 1 2
```
=> Guess position [1][2] grid as **potentially** containing a mine.:
Marks _?_ symbol on the grid.
***

#### Mark as 'Definitly' containing a mine.
```
$ m 1 2
```
```
$ mark 1 2
```
=> Marks position [1][2] grid as **definitly** containing a mine.
***

#### List the commands that are available.
```
$ h
```
```
$ help
```
=> Shows the command options.
***

#### Exit out the game.
```
$ q
```
```
$ quit
```
=> Exits the game.


## Seed File
A plain text file in which the program generates the minefield based on.

Seed file must have the following format:
* First Token: Determines the number of **rows** of the field.
* Second Token: Determines the number of **columns** of the field.
* Third Token: Determines the number of **mines**.
* Subsequent pairs of tokens: Determines the location(coordinations) of each mines.

##### Examples
```
5 5 3 0 0 1 1 2 2 
```
=> Generates **5x5 grid**, place **3 mines** in positions: **[0][0], [1][1]**, and **[2][2]**.

***
A seed file must be supplied when running this program. 
A command such as below should be used in the terminal.
```
$ java -cp bin cs1302.game.MinesweeperDriver --seed a/relative/path/of/the/seed/file.txt
```
* Note that the keyword **--seed** must be used along.

