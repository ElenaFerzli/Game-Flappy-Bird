
import java.awt.*; 
import java.awt.event.*; 
import java.util.ArrayList; 
import java.util.Random;
import javax.swing.*; 



public class FlappyBird extends JPanel implements ActionListener, KeyListener{

    
    int boardWidth = 360; 
    int boardHeight = 640; 

    //images 
    Image background; 
    Image birdImg; 
    Image topPipeImg; 
    Image bottomPipeImg;

    //Bird 
    int birdX = boardWidth/8;
    int birdY = boardHeight/2;
    int birdWidth = 34; 
    int birdHeight = 24; 

    class Bird { 

        int x = birdX; 
        int y = birdY; 
        int width = birdWidth; 
        int height = birdHeight; 
        Image img; 

        Bird(Image img){
            this.img = img; 
        }
    }

    class Pipe{

        int x = pipeX; 
        int y = pipeY; 
        int width = pipeWidth; 
        int height = pipeHeight; 
        Image img; 
        boolean passed = false; 
        
        Pipe(Image img){
            this.img = img; 
        }
    }

    //Pipe
    int pipeX = boardWidth; 
    int pipeY = 0; 
    int pipeWidth = 64; 
    int pipeHeight = 512; 

    //game logic
    Bird bird; 
    int velocityY = 0; 
    int gravity = 1; 
    int velocityX = -4; 

    ArrayList<Pipe> pipes; 
    Random random = new Random(); 

    Timer gameLoop;
    Timer placePipestimer; 

    boolean gameOver = false; 
    double score = 0; 

    FlappyBird(){
        setPreferredSize(new Dimension(boardWidth,boardHeight));
       // setBackground(Color.blue);

       setFocusable(true);
       addKeyListener(this);

        //laod images
        background = new ImageIcon(getClass().getResource("./flappybirdbg.png")).getImage();
        birdImg = new ImageIcon(getClass().getResource("./flappybird.png")).getImage();
        topPipeImg = new ImageIcon(getClass().getResource("./toppipe.png")).getImage();
        bottomPipeImg = new ImageIcon(getClass().getResource("./bottompipe.png")).getImage();

        bird = new Bird(birdImg);
        pipes = new ArrayList<Pipe>();

        // pipes timer 
        placePipestimer = new Timer(1500, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
            placePipes();}
            
        });

        placePipestimer.start();                   

        //game timer 
        gameLoop = new Timer(1000/60,this);
        gameLoop.start();
    }

    public void placePipes(){

        int randomPipeY = (int)(pipeY - pipeHeight/4 - Math.random()*(pipeHeight/2));
        int opening = boardHeight/4; 


        Pipe topPipe = new Pipe(topPipeImg);
        topPipe.y = randomPipeY; 
        pipes.add(topPipe); 

        Pipe bottomPipe = new Pipe(bottomPipeImg);
        bottomPipe.y = topPipe.y +pipeHeight +opening; 
        pipes.add(bottomPipe); 
    }

    public void paintComponent(Graphics g){
        super.paintComponent(g);
        draw(g); 
    }

    public void draw(Graphics g){

        // background
       g.drawImage(background,0,0,boardWidth,boardHeight,null); 

       //Bird
       g.drawImage(birdImg,bird.x,bird.y,bird.width,bird.height,null); 

       //pipes
       for (int i =0; i<pipes.size(); i++){
        Pipe pipe = pipes.get(i);
        g.drawImage(pipe.img, pipe.x, pipe.y, pipe.width, pipe.height, null);
       }

       //score 
       g.setColor(Color.white); 
       g.setFont(new Font("Arial", Font.PLAIN, 32));
       if(gameOver){
        g.drawString("Game Over: " + String.valueOf((int) score), 10, 35); 
       }
       else{
        g.drawString( String.valueOf((int) score), 10, 35); 

       }


    }

    public void move(){
        //bird
        velocityY += gravity; 
        bird.y += velocityY;
        bird.y = Math.max(bird.y,0);
        
        //pipes
        for (int i =0; i<pipes.size(); i++){
            Pipe pipe = pipes.get(i);
            pipe.x += velocityX;

            if(!pipe.passed && bird.x > pipe.x + pipe.width){

                pipe.passed = true; 
                score += 0.5;
            }

            if(collide(bird, pipe)){
                gameOver = true; 
            }
 
           }

        if(bird.y > boardHeight){
            gameOver = true;
        }
       
    }

    public boolean collide(Bird a, Pipe b){
        return((a.x < b.x + b.width) && (a.x + a.width > b.x) && (a.y < b.y + b.height) && (a.y + a.height > b.y));
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        move(); 
        repaint(); 
        if(gameOver){
            placePipestimer.stop(); 
            gameLoop.stop();  
        }
 
    }

   

    @Override
    public void keyPressed(KeyEvent e) {
    if (e.getKeyCode() == KeyEvent.VK_SPACE){
        velocityY = -9; 
        if(gameOver){
            // reset the game 
            bird.y = birdY; 
            velocityY = 0; 
            pipes.clear(); 
            score = 0; 
            gameOver = false; 
            gameLoop.start(); 
            placePipestimer.start(); 
        }
    }
    }

    @Override
    public void keyTyped(KeyEvent e) {
     
    }

    @Override
    public void keyReleased(KeyEvent e) {
   
    }
    
}
