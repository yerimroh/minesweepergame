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

#### Valid commands
* Reveal a square on the grid to see if it contains a mine. => **r** or **reveal**
* Mark a square on the grid as 'potentially' containing a mine. => **g** or **guess**
* Maek a square on the grid as 'definitly' containing a mine. => **m** or **mark**
* Ask for help info. **h** or **help**
and
* Quit(Exit) the game. **q** or **quit**

###### Syntax
```
$ r 1 2
```
or
```
$ reveal 1 2
```
=> Reveal position [1][2] grid:
If the grid **does not** contain a mine, shows the number of mines around the grid.
If the grid **does** contain a mine, shows the <game over> screen and exit the game.
***
  
```
$ g 1 2
```
or
```
$ guess 1 2
```
=> Guess position [1][2] grid as **potentially** containing a mine.:
Marks _?_ symbol on the grid.
***

```
$ m 1 2
```
or
```
$ mark 1 2
```
=> Marks position [1][2] grid as **definitly** containing a mine.
***

```
$ h
```
or
```
$ help
```
=> Shows the command options.
***

```
$ q
```
or
```
$ quit
```
=> Exits the game.


## Seed File

