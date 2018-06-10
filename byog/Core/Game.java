package byog.Core;

import byog.TileEngine.TERenderer;
import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;


import java.io.IOException;
import java.io.PrintWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.BufferedWriter;
import java.util.Scanner;

public class Game {
    TERenderer ter = new TERenderer();
    /* Feel free to change the width and height. */
    public static final int WIDTH = 120;
    public static final int HEIGHT = 60;

    /**
     * Method used for playing a fresh game. The game should start from the main menu.
     */
    public void playWithKeyboard() throws IOException {
        PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter("input.txt")));
        GameSetup b = new GameSetup(40, 30); long seedInput = b.startGame(pw); int level = b.mode;
        TERenderer ter1 = new TERenderer(); ter1.initialize(WIDTH, HEIGHT);
        TETile[][] finalWorldFrame1 = new TETile[WIDTH][HEIGHT]; clearTiles(finalWorldFrame1);
        if (seedInput == -1) {
            Scanner sc = new Scanner(new File("a.txt")); String fileRead = sc.next();
            if (fileRead.length() == 0 || fileRead.equals("$$#%&")) {
                System.out.println("YOU FUCKED UP NO GAME TO LOAD");
            }
            String finalIn = fileRead.toLowerCase();
            int indexsetup = finalIn.indexOf('n'); char levelf = finalIn.charAt(indexsetup - 1);
            pw.write(levelf); char first = finalIn.charAt(indexsetup);
            finalIn = finalIn.substring(indexsetup + 1); int index = finalIn.indexOf('s');
            String copyInput = finalIn.substring(index + 1);
            if (first == 'n') {
                pw.write("n"); String seed = finalIn.substring(0, index); pw.write(seed);
                pw.write('s'); SeededWorldGenerator s = new SeededWorldGenerator(
                        WIDTH, HEIGHT, Long.parseLong(seed));
                switch (levelf) {
                    case 'z':
                        s.numFlowers = 5;
                        s.score = 100;
                        break;
                    case 'h':
                        s.numFlowers = 3;
                        s.score = 50;
                        break;
                    case 'x':
                        s.numFlowers = 1;
                        s.score = 25;
                        break;
                    default:
                        s.numFlowers = 5;
                        s.score = 100;
                        break;
                }
                s.generateWorld(finalWorldFrame1); ter1.renderFrame(finalWorldFrame1);
                boolean ongoing = s.moveload(finalWorldFrame1, copyInput, pw, ter1);
                ter1.renderFrame(finalWorldFrame1); int gameNotover = 1;
                if (ongoing) {
                    while (gameNotover == 1) {
                        gameNotover = s.move(finalWorldFrame1, pw, ter1);
                        if (s.score <= 0) {
                            gameNotover = -1;
                        } else {
                            ter1.renderFrame(finalWorldFrame1);
                        }
                    }
                }
                EndGame last = new EndGame(WIDTH / 3, HEIGHT / 3, s.score);
                switch (gameNotover) {
                    case -2:
                        flushAtxt(); ter1.clearFrame(); last.win(); break;
                    case -1:
                        flushAtxt(); ter1.clearFrame(); last.lose(); break;
                    case 0:
                        pw.close(); Scanner bs = new Scanner(new File("input.txt"));
                        PrintWriter finale = new PrintWriter(
                                new BufferedWriter(new FileWriter("a.txt")));
                        finale.write(bs.next()); finale.close(); System.exit(0); break;
                    default:
                        System.out.println("error");
                }
            }
            System.exit(0);
        } else {
            playKeyboardHelper(seedInput, level, pw, ter1);
        }
    }

    public void playKeyboardHelper(long seedInput, int level,
                                   PrintWriter pw, TERenderer ter1) throws IOException {
        TETile[][] randomTiles = new TETile[WIDTH][HEIGHT]; clearTiles(randomTiles);
        SeededWorldGenerator s = new SeededWorldGenerator(WIDTH, HEIGHT, seedInput);
        switch (level) {
            case 0:
                s.numFlowers = 5;
                s.score = 100;
                break;
            case 1:
                s.numFlowers = 3;
                s.score = 50;
                break;
            case 2:
                s.numFlowers = 1;
                s.score = 25;
                break;
            default:
                s.numFlowers = 5;
                s.score = 100;
                break;
        }
        pw.write(Long.toString(seedInput));
        pw.write('s'); s.generateWorld(randomTiles);
        ter1.renderFrame(randomTiles); int gameNotover = 1;
        while (gameNotover == 1) {
            gameNotover = s.move(randomTiles, pw, ter1);
            if (s.score <= 0) {
                gameNotover = -1;
            } else {
                ter1.renderFrame(randomTiles);
            }
        }
        EndGame last = new EndGame(WIDTH / 3, HEIGHT / 3, s.score);
        switch (gameNotover) {
            case -2:
                flushAtxt(); ter1.clearFrame(); last.win(); break;
            case -1:
                ter1.clearFrame(); last.lose(); flushAtxt(); break;
            case 0:
                pw.close(); Scanner bs = new Scanner(new File("input.txt"));
                PrintWriter finale = new PrintWriter(
                        new BufferedWriter(new FileWriter("a.txt")));
                finale.write(bs.next()); finale.close(); System.exit(0); break;
            default:
                System.out.print("can't go here");
        }
    }

    public void flushAtxt() throws IOException {
        PrintWriter finale = new PrintWriter(
                new BufferedWriter(new FileWriter("a.txt")));
        finale.write("$$#%&");
        finale.close();
    }

    /**
     * Method used for autograding and testing the game code. The input string will be a series
     * of characters (for example, "n123sswwdasdassadwas", "n123sss:q", "lwww". The game should
     * behave exactly as if the user typed these characters into the game after playing
     * playWithKeyboard. If the string ends in ":q", the same world should be returned as if the
     * string did not end with q. For example "n123sss" and "n123sss:q" should return the same
     * world. However, the behavior is slightly different. After playing with "n123sss:q", the game
     * should save, and thus if we then called playWithInputString with the string "l", we'd expect
     * to get the exact same world back again, since this corresponds to loading the saved game.
     *
     * @param input the input string to feed to your program
     * @return the 2D TETile[][] representing the state of the world
     */
    public TETile[][] playWithInputString(String input) {
        //  Fill out this method to run the game using the input passed in,
        // and return a 2D tile representation of the world that would have been
        // drawn if the same inputs had been given to playWithKeyboard().
        try {
            PrintWriter pw2 = new PrintWriter(new BufferedWriter(new FileWriter("input.txt")));
            TETile[][] finalWorldFrame = new TETile[WIDTH][HEIGHT];
            for (int x = 0; x < WIDTH; x += 1) {
                for (int y = 0; y < HEIGHT; y += 1) {
                    finalWorldFrame[x][y] = Tileset.NOTHING;
                }
            }
            input = input.toLowerCase();
            char first = input.charAt(0); //LSWAS
            input = input.substring(1);

            String seed = "0";
            if (first == 'n') {
                pw2.write('n');
                int index = input.indexOf('s');
                String copyInput = input.substring(index + 1);
                seed = input.substring(0, index);
                pw2.write(seed);
                pw2.write('s');
                SeededWorldGenerator s =
                        new SeededWorldGenerator(WIDTH, HEIGHT, Long.parseLong(seed));
                s.generateWorld(finalWorldFrame);
                boolean ongoing = s.moveload(finalWorldFrame, copyInput, pw2);
                if (!ongoing) {
                    pw2.close();
                    Scanner bs = new Scanner(new File("input.txt"));
                    BufferedWriter b = new BufferedWriter(new FileWriter("a.txt"));
                    PrintWriter finale = new PrintWriter(b);
                    finale.write(bs.next());
                    finale.close();
                } else {
                    pw2.flush();
                }
            } else if (first == 'l') {
                Scanner sc = new Scanner(new File("a.txt"));
                String fileRead = sc.next();
                if (fileRead.length() == 0) {
                    System.out.println("YOU FUCKED UP NO GAME TO LOAD");
                }
                String finalIn = fileRead + input;
                return playWithInputString(finalIn);
            }
            return finalWorldFrame;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public void clearTiles(TETile[][] tiles) {
        for (int x = 0; x < tiles.length; x += 1) {
            for (int y = 0; y < tiles[0].length; y += 1) {
                tiles[x][y] = Tileset.NOTHING;
            }
        }
    }

}
