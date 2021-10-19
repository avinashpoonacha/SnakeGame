import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Random;

public class GamePanel extends JPanel implements ActionListener {
    static final int SCREEN_WIDTH = 600;
    static final int SCREEN_HEIGHT = 600;
    static final int UNIT_SIZE=25; // single unit of game
    static final int GAME_UNITS=(SCREEN_WIDTH *SCREEN_HEIGHT)/UNIT_SIZE; // possible amt of blocks
    static final int DELAY=75; // higher the number , slower the game
    // arrays will hold all co ordinates
    final int x[]=new int[GAME_UNITS];
    final int y[] = new int[GAME_UNITS];
    int bodyParts = 6;
    int applesEaten = 0;
    int appleX;
    int appleY;
    char direction='E'; // run right on init
    boolean running=false;
    Timer timer;
    Random random;

    GamePanel(){
        // create instance of random
        random=new Random();
        this.setPreferredSize(new Dimension(SCREEN_WIDTH,SCREEN_HEIGHT));
        this.setBackground(Color.black);
        this.setFocusable(true);
        this.addKeyListener(new MyKeyAdapter());
        startGame();
    }

    public void startGame(){
        newApple(); // to create a new apple
        running=true;
        timer = new Timer(DELAY,this); // this is cus of actionlistener IF
        timer.start();
    }
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        draw(g);

    }
    public void draw(Graphics g) {
//create a grid
        if(running) {
            // g.setColor(Color.white);
            for (int i = 0; i < SCREEN_HEIGHT / UNIT_SIZE; i++) {
                g.drawLine(i * UNIT_SIZE, 0, i * UNIT_SIZE, SCREEN_HEIGHT); // vertical
                g.drawLine(0, i * UNIT_SIZE, SCREEN_WIDTH, i * UNIT_SIZE); // horizontal
            }
            g.setColor(Color.red);
            g.fillOval(appleX, appleY, UNIT_SIZE, UNIT_SIZE);

            // head of snake and body
            for (int i = 0; i < bodyParts; i++) {
                //head
                if (i == 0) {
                    g.setColor(Color.BLUE);
                    g.fillRect(x[i], y[i], UNIT_SIZE, UNIT_SIZE);
                } else {
                    g.setColor(Color.green);
                    g.setColor(new Color(random.nextInt(255),random.nextInt(255),random.nextInt(255)));
                    g.fillRect(x[i], y[i], UNIT_SIZE, UNIT_SIZE);
                }
            }
            g.setColor(Color.GREEN);
            g.setFont(new Font("Ink Free", Font.BOLD,40));
            FontMetrics metrics = getFontMetrics(g.getFont());
            g.drawString("SCORE :" + applesEaten,(SCREEN_WIDTH - metrics.stringWidth("SCORE :" + applesEaten))/2,g.getFont().getSize());
        } else {
            gameOver(g);
        }
    }
    public void newApple(){
        // generate co ordinates of new apple by x,y
        appleX=random.nextInt((int)(SCREEN_WIDTH/UNIT_SIZE))*UNIT_SIZE;
        appleY=random.nextInt((int)(SCREEN_HEIGHT/UNIT_SIZE))*UNIT_SIZE;

    }
    public void move(){
// move the snake
        for(int i=bodyParts;i>0;i--){
            // shifting body of snake
            x[i]=x[i-1];
            y[i]=y[i-1];
        }
        //switch for directionof snake
        switch(direction){
            case 'W':
                y[0]=y[0] - UNIT_SIZE;
                break;
            case 'S':
                y[0]=y[0] + UNIT_SIZE;
                break;
            case 'E':
                x[0]=x[0]+UNIT_SIZE;
                break;
            case 'Q':
                x[0]=x[0]-UNIT_SIZE;
                break;

        }
    }
    public void checkApple(){
if((x[0]==appleX) && (y[0]==appleY)){
    // if head is at apple
    bodyParts++;
    applesEaten++;
    newApple();
}

    }
    public void checkCollision(){
        for(int i = bodyParts;i>0;i--){
            if((x[0]==x[i] && y[0]==y[i])){
                // head collided with body
                running = false;
            }
        }
        // head collided with left edges
        if(x[0] <0){
            running = false;
        }
        // head collided with right edges
        if(x[0] > SCREEN_WIDTH){
            running = false;
        }
        // head collided with top edges
        if(y[0] <0){
            running = false;
        }
        // head collided with bottom edges
        if(y[0] > SCREEN_HEIGHT){
            running = false;
        }
        if(!running) {
            timer.stop();
        }
    }
    public void gameOver(Graphics g){
        g.setColor(Color.RED);
        g.setFont(new Font("Arial", Font.BOLD,75));
        FontMetrics metrics = getFontMetrics(g.getFont());
        g.drawString("GAME OVER !!",(SCREEN_WIDTH - metrics.stringWidth("GAME OVER !!"))/2,SCREEN_HEIGHT/2);
        g.setColor(Color.RED);
        g.setFont(new Font("Arial", Font.BOLD,75));
       // FontMetrics metrics = getFontMetrics(g.getFont());
        g.drawString("SCORE :" + applesEaten,(SCREEN_WIDTH - metrics.stringWidth("SCORE :" + applesEaten))/2,SCREEN_HEIGHT/3);

    }
    @Override
    public void actionPerformed(ActionEvent e) {

        if(running){
            move();
            checkApple();
            checkCollision();
        }
        repaint();
    }

    public class MyKeyAdapter extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e){
// controller of snake
            switch(e.getKeyCode()){
                case KeyEvent.VK_LEFT:
                    if(direction != 'E') { // only turn at right angles
                        direction ='Q';
                    }
                    break;
                case KeyEvent.VK_RIGHT:
                    if(direction != 'Q') {
                        direction ='E';
                    }
                    break;
                case KeyEvent.VK_UP:
                    if(direction != 'S') {
                        direction ='W';
                    }
                    break;
                case KeyEvent.VK_DOWN:
                    if(direction != 'W') {
                        direction ='S';
                    }
                    break;

            }
        }
    }
}
