SnakeGame -> 
    GameFrame ( JFRAME) ->
        GamePanel(JPanel,ActionListener)


1. SnakeGame constructs the Game frame 
2. Game frame add screen attributes and add the gamepanel 
3. Jpanel controls the game and defines the below 
   1. screen size 
   2. unit of each grid 
   3. initial snake size 
   4. key press delay 
   5. arrays to hold snake body after eating apple 
   6. key system based on QWES
   7. GamePanel constructs below 
      1. init random
      2. init keyadapter inner class 
      3. background
      4. start game 
   

start game - > adds new apple in random x,y 
sets running to true 
starts timer for action listener 

new apple will randomly set apple at x, y

paintcomponent -> calls super paint component and is responsible for drawing the components 
draws new apple on x,y 
draws body of snake 
draws score 

move() 
will shift the array value one value over 
x[i] = x[i-1] 
also adds a switch case for direction ( QWES) 

checkapple 
if head of snake is at apple x,y, then eat apple 
create new random apple 

check collisons 
check if array is colliding inside itself 
or at the edges and set running as false 
stop the timer 

game over -> add graphics to show strings 

in overridden action Performed method 
check if running 
then move , cehck apples , check collison 
or game over -> repaint() ( super) 

keypanel is added t listen to the keys and manage direction as specified above in QWES 
based on VK arrow keys 


