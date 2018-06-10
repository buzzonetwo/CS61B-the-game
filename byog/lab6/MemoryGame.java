package byog.lab6;

import edu.princeton.cs.introcs.StdDraw;

import java.awt.Color;
import java.awt.Font;
import java.util.Random;

public class MemoryGame {
    private int width;
    private int height;
    private int round;
    private Random rand;
    private boolean gameOver;
    private boolean playerTurn;
    private static final char[] CHARACTERS = "abcdefghijklmnopqrstuvwxyz".toCharArray();
    private static final String[] ENCOURAGEMENT = {"You can do this!", "I believe in you!",
                                                   "You got this!", "You're a star!", "Go Bears!",
                                                   "Too easy for you!", "Wow, so impressive!"};

    public static void main(String[] args) {
        if (args.length < 1) {
            System.out.println("Please enter a seed");
        //    return;
        }

       //int seed = Integer.parseInt(args[0]);
        Random random = new Random(159);
        MemoryGame game = new MemoryGame(40, 40, random);
        game.startGame();
        //String b= game.generateRandomString(159);
        //game.flashSequence(b);
        //System.out.println(b);
        //game.solicitNCharsInput(b.length());

    }

    public MemoryGame(int width, int height, Random r) {
        /* Sets up StdDraw so that it has a width by height grid of 16 by 16 squares as its canvas
         * Also sets up the scale so the top left is (0,0) and the bottom right is (width, height)
         */
        this.width = width;
        this.height = height;
        this.rand=r;
        StdDraw.setCanvasSize(this.width * 16, this.height * 16);
        Font font = new Font("Monaco", Font.BOLD, 30);
        StdDraw.setFont(font);
        StdDraw.setXscale(0, this.width);
        StdDraw.setYscale(0, this.height);
        StdDraw.clear(Color.BLACK);
        StdDraw.enableDoubleBuffering();

        //TODO: Initialize random number generator
    }

    public String generateRandomString(int n) {
        //TODO: Generate random string of letters of length n
        int leftLimit = 97; // letter 'a'
        int rightLimit = 122; // letter 'z'
        int targetStringLength = n;
        Random random = new Random();
        StringBuilder buffer = new StringBuilder(targetStringLength);
        for (int i = 0; i < targetStringLength; i++) {
            int randomLimitedInt = leftLimit + (int)
                    (random.nextFloat() * (rightLimit - leftLimit + 1));
            buffer.append((char) randomLimitedInt);
        }
        String generatedString = buffer.toString();

        return generatedString;

    }

    public void drawFrame(String s) {

        StdDraw.clear(Color.BLACK);
        StdDraw.show();
        Font font = new Font("Monaco", Font.BOLD, 30);
        StdDraw.setFont(font);
        StdDraw.setPenColor(0,255,0);
        StdDraw.text(this.width/2,this.height/2,s);
        //System.out.println("fuck");
        StdDraw.show();
        StdDraw.pause(300);

        //TODO: Take the string and display it in the center of the screen
        //TODO: If game is not over, display relevant game information at the top of the screen
    }
    public void drawFrame1(String s) {

        StdDraw.clear(Color.BLACK);
        StdDraw.show();
        StdDraw.pause(2000);
        Font font = new Font("Monaco", Font.BOLD, 30);
        StdDraw.setFont(font);
        StdDraw.setPenColor(0,255,0);
        StdDraw.text(this.width/2,this.height/2,s);
        //System.out.println("fuck");
        StdDraw.show();
        StdDraw.pause(3000);

        //TODO: Take the string and display it in the center of the screen
        //TODO: If game is not over, display relevant game information at the top of the screen
    }

    public void flashSequence(String letters) {
        //TODO: Display each character in letters, making sure to blank the screen between letters
        for (int x=0; x<letters.length();x++){
            drawFrame1(Character.toString(letters.charAt(x)));
        }
    }

    public String solicitNCharsInput(int n) {
       String fin = "";
       while(fin.length()!=n)
       {
            if(StdDraw.hasNextKeyTyped()){
                fin=fin+Character.toString(StdDraw.nextKeyTyped());
                drawFrame(fin);
            }
        }
        //TODO: Read n letters of player input
        return fin;
    }

    public void startGame() {
        int round=1;
        boolean correct=true;
        while(correct)
        {
            drawFrame("Round"+round);
            String ans =generateRandomString(5);
            flashSequence(ans);
            String b= solicitNCharsInput(ans.length());
            if(b.equals(ans)){
                round=2;
            }
            else {
                correct=false;
            }

        }

        //TODO: Set any relevant variables before the game starts

        //TODO: Establish Game loop
    }

}
