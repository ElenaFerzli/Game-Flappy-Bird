
import javax.swing.*;

public class App {
    public static void main(String[] args) throws Exception {
      
        int boardWidth = 360; 
        int boardHeight = 640; 

        JFrame frame = new JFrame("Flappy Bird"); 


        ImageIcon image = new ImageIcon(App.class.getResource("/flappybird.png")); 
		frame.setIconImage(image.getImage());
        
        frame.setSize(boardWidth,boardHeight);


        frame.setResizable(false);
     
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        FlappyBird flappyBird = new FlappyBird();
        frame.add(flappyBird);
        frame.pack();

        
        frame.setLocationRelativeTo(null);

        flappyBird.requestFocus(); 

        frame.setVisible(true);

    


    }
}
