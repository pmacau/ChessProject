# My Personal Project



## **Questions/Answers and some Information about the Project** 
- ### ***What will the application do?***
It will show you a chess piece on a board, and then you have a 1-7 seconds depending on difficulty
to memorize the board, and then the board goes blank, and you have to correctly recall what positions and potentially 
what side (black or white), 
and what exact piece is on the square (bishop, knight, etc). Normally, it would start
off with 1 piece, and each time you correctly recall, another  random piece appears which you will have on top of the prior piece to 
memorize. And this will go on and on, till all the squares of the board you choose are taken up (this could be adjusted according to difficulty)
or till you incorrectly recall. 
So it is essentially a memorization chess based game. The game will have an in-game stats page on the main menu, 
to showcase achievements of the player. 

- ### ***Who will use it?***
People who like puzzle memorization games, or people interested in chess such as myself.
Also, there doesn't have to be too much time commitment to play, you could a have a few minutes to spare
and solve a few levels. 

- ### ***Why is this project of interest to you?***
I always liked doing memorization recall games, and I also enjoy playing chess, and I thought
that combining the two would make for a fun, and potentially more applicable game than usual tile recall games, at 
least for me. As remembering chunks of the board in chess is very important when attempting to generate potential lines
(moves) while looking in the future and comparing positions from potential future vs now. However, this might
not be one-to-one to recalling chess chunks (areas on the board), since the positions generated might
be very obscure to what you'll have in a real game, however, this might actually be an advantage, as it is 
more difficult to recall patterns which don't follow any line of logic (as it will be randomly placed), potentially
making board recall more intuitive over time, which could be applied to the real game.


## User Stories  
As a user,
- I want to see my streak on a board in statistics. (completed)
- I want to see my highest streak on a board in statistics. (completed)
- - I want to see my biggest board size played in statistics. (completed)
- I want to see my attempts be added and tracked to statistics. (completed)
- I want to see what difficulty I played in past boards. (completed)
- I want to be able to select what difficulty I want to play from the start. (completed) 
- I want to be able to choose what size board I play. (completed)
- I want to be able to see statistics of what puzzles I've solved (arbitrary listof BoardStats). (completed)
- I want to see what pieces I guess most in statistics. (completed)
- I want the difficulties to give a certain amount of time to recall. (completed)
- I want there to be no duplicate tile assignments to a board. (complete)
- I want there to be random pieces added to the board. (complete)
- I want the pieces added to have a random team either white or black. (complete)
- I want to be able to save my game, so it would save my statistics, my current game, if I so choose. 
- I want to be able to load my game (if I so choose).

### ***Instructions for Grader***
- To generate an action (adding multiple Xs to a Y) which in this case adding a board stat to statistics, you simply 
have to just play a game, in which at the end (either you solve or lose the board) you will see the statistics to the 
right on a JScrollPanel. So simply just press play off of the main menu, and select a dimension e.g. 4, and then a 
difficulty e.g. hard, and then play the game to completion, then you will see the statistic being added. 
- To generate the other action associated to multiple Xs to a Y, when you finish a game, just as in the first 
instruction, you can press the reset statistics button which wipes all the stats, clearing the history of statistics. 
- The visual component is the game itself, as there are images shown board, which are the pieces. 
- You can save in multiple places on the GUI by pressing the save button on the screen, either on the main menu, the end 
of a game, or by pressing the save and quit button while in a game. 
- The user can simply load the state of the application from the main menu, by pressing the load button. 

