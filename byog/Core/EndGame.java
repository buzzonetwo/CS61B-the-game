package byog.Core;

import edu.princeton.cs.introcs.StdDraw;

import java.awt.Color;
import java.awt.Font;

public class EndGame {
    private int width;
    private int height;
    private int score;






    public EndGame(int width, int height, int score1) {
        /* Sets up StdDraw so that it has a width by height grid of 16 by 16 squares as its canvas
         * Also sets up the scale so the top left is (0,0) and the bottom right is (width, height)
         */
        this.width = width;
        this.height = height;
        this.score = score1;
        StdDraw.setCanvasSize(this.width * 16, this.height * 16);
        Font font = new Font("Monaco", Font.BOLD, 30);
        StdDraw.setFont(font);
        StdDraw.setXscale(0, this.width);
        StdDraw.setYscale(0, this.height);
        StdDraw.clear(Color.BLACK);
        StdDraw.enableDoubleBuffering();

    }


    public void lose() {

        StdDraw.clear(Color.BLACK);
        Font font = new Font("Monaco", Font.BOLD, 40);
        StdDraw.setFont(font);
        StdDraw.setPenColor(0, 0, 255);
        String s = "YOU LOSE";
        StdDraw.text(this.width / 2, this.height / 2, s);
        Font f1 = new Font("Times", Font.BOLD, 25);
        StdDraw.setFont(f1);
        StdDraw.setPenColor(255, 0, 0);
        String b = "TRY AGAIN";
        StdDraw.text(this.width / 2, this.height / 8 * 2.75, b);
        StdDraw.show();
        StdDraw.pause(10000);




    }

    public void win() {

        StdDraw.clear(Color.BLACK);
        Font font = new Font("Monaco", Font.BOLD, 40);
        StdDraw.setFont(font);
        StdDraw.setPenColor(0, 0, 255);
        String s = "YOU WIN";
        StdDraw.text(this.width / 2, this.height / 2, s);
        Font f1 = new Font("Times", Font.BOLD, 25);
        StdDraw.setFont(f1);
        StdDraw.setPenColor(255, 0, 0);
        String b = "Score: " + this.score;
        StdDraw.text(this.width / 2, this.height / 8 * 2.75, b);
        StdDraw.show();
        StdDraw.pause(10000);



    }








}


