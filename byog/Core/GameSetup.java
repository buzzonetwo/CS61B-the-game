package byog.Core;

import edu.princeton.cs.introcs.StdDraw;

import java.awt.Font;
import java.awt.Color;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.BufferedWriter;

import java.util.Random;
import java.util.Scanner;

public class GameSetup {
    private static final char[] CHARACTERS = "abcdefghijklmnopqrstuvwxyz".toCharArray();
    int mode;
    private int width;
    private int height;
    private boolean set;

    public GameSetup(int width, int height) {
        /* Sets up StdDraw so that it has a width by height grid of 16 by 16 squares as its canvas
         * Also sets up the scale so the top left is (0,0) and the bottom right is (width, height)
         */
        this.width = width;
        this.height = height;
        StdDraw.setCanvasSize(this.width * 16, this.height * 16);
        Font font = new Font("Monaco", Font.BOLD, 30);
        StdDraw.setFont(font);
        StdDraw.setXscale(0, this.width);
        StdDraw.setYscale(0, this.height);
        StdDraw.clear(Color.BLACK);
        StdDraw.enableDoubleBuffering();
        mode = 0;

        set = false;
        // Initialize random number generator
    }

    public static void main(String[] args) throws IOException {
        if (args.length < 1) {
            System.out.println("Please enter a seed");
            //    return;
        }

        //int seed = Integer.parseInt(args[0]);
        Random random = new Random(159);
        GameSetup game = new GameSetup(40, 40);
        Scanner sc = new Scanner(new File("input.in"));
        PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter("input.out")));
        //String b= game.generateRandomString(159);
        //game.flashSequence(b);
        //System.out.println(b);
        //game.solicitNCharsInput(b.length());

    }

    public long startGame(PrintWriter p) {
        setup();

        long seed = 0;
        while (true) {
            char userInput = getChar(p);
            userInput = Character.toLowerCase(userInput);
            if (userInput == 'l') {
                System.out.println("loading");
                return -1;
            } else if (userInput == 'n') {
                System.out.println("new game");
                seed = Long.parseLong(seedGetter());
                return seed;
            } else if (userInput == 'q') {
                System.out.println("quitting");
                System.exit(0);
                return -2;
            } else if (userInput == 'r') {
                System.out.println("instructions");

            } else if (userInput == 'z') {
                System.out.println("easy/default mode activated");
                mode = 0;

            } else if (userInput == 'h') {
                System.out.println("harder mode activated");
                mode = 1;

            } else if (userInput == 'x') {
                System.out.println("hardest mode activated");
                mode = 2;

            }

        }
    }

    public void setup() {

        StdDraw.clear(Color.BLACK);
        StdDraw.show();
        Font font = new Font("Monaco", Font.BOLD, 30);
        StdDraw.setFont(font);
        StdDraw.setPenColor(0, 255, 0);
        String s = "CS61B: THE HYPE IS REAL";
        StdDraw.text(this.width / 2, this.height / 2 * 5 / 4, s);
        Font f1 = new Font("Times", Font.BOLD, 15);
        StdDraw.setFont(f1);
        StdDraw.setPenColor(0, 0, 255);
        String b = "New Game (N)";
        String c = "Load Game (L)";
        String d = "Quit (Q)";
        String e = "Instructions (R)";
        String f = "Harder Mode (H)";
        String g = "Hardest Mode (X)";
        String h = "Easy/Default Mode (Z)";


        StdDraw.text(this.width / 2, this.height / 8 * 4, b);
        StdDraw.text(this.width / 2, this.height / 8 * 3.5, c);
        StdDraw.text(this.width / 2, this.height / 8 * 3, d);
        StdDraw.text(this.width / 2, this.height / 8 * 2.5, e);
        StdDraw.text(this.width / 2, this.height / 8 * 2.0, h);

        StdDraw.text(this.width / 2, this.height / 8 * 1.5, f);
        StdDraw.text(this.width / 2, this.height / 8 * 1.0, g);

        StdDraw.show();
        System.out.println("CS61B: FTW");

    }

    public Character getChar(PrintWriter p) {
        char in = '%';
        // StdDraw.clear();
        while (in == '%') {
            if (StdDraw.hasNextKeyTyped()) {
                in = StdDraw.nextKeyTyped();

            }
        }
        if (in == 'z' || in == 'Z'
                || in == 'h' || in == 'H' || in == 'x' || in == 'X') {
            p.write(in);
            set = true;
        } else if (in == 'n' || in == 'N') {
            if (!set) {
                p.write('z');
            }
            p.write(in);
        }
        return in;
    }

    public String seedGetter() {
        StdDraw.clear(Color.BLACK);
        StdDraw.show();
        Font font = new Font("Monaco", Font.BOLD, 30);
        StdDraw.setFont(font);
        StdDraw.setPenColor(0, 255, 0);
        String s = "Enter a seed";
        StdDraw.text(this.width / 2, this.height / 2, s);
        Font f1 = new Font("Times", Font.BOLD, 15);
        StdDraw.setFont(f1);
        StdDraw.setPenColor(0, 0, 255);
        String b = "any integer";
        String c = "When you are done, type S";
        StdDraw.text(this.width / 2, this.height / 8 * 3, b);
        StdDraw.text(this.width / 2, this.height / 8 * 2.5, c);
        StdDraw.show();
        String seedf = "";
        boolean k = true;
        while (k) {
            if (StdDraw.hasNextKeyTyped()) {
                char temp = Character.toLowerCase(StdDraw.nextKeyTyped());

                if (temp != 's') {
                    seedf = seedf + (Character.toString(temp));
                    drawFrame(seedf);
                } else {
                    k = false;
                }

            }
        }

        // Read n letters of player input
        return seedf;
    }

    public void drawFrame(String s) {

        StdDraw.clear(Color.BLACK);
        Font font = new Font("Monaco", Font.BOLD, 20);
        StdDraw.setFont(font);
        StdDraw.setPenColor(0, 255, 0);
        StdDraw.text(this.width / 2, this.height / 8, s);
        //System.out.println("fuck");
        StdDraw.show();

        //: Take the string and display it in the center of the screen
        //: If game is not over, display relevant game information at the top of the screen
    }
}


