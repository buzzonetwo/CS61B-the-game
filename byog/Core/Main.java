package byog.Core;

import java.io.IOException;

/** This is the main entry point for the program. This class simply parses
 *  the command line inputs, and lets the byog.Core.Game class take over
 *  in either keyboard or input string mode.
 */
public class Main {
    public static void main(String[] args) throws IOException {
        if (args.length > 1) {
            System.out.println("Can only have one argument - the input string");
            System.exit(0);
        } else if (args.length == 1) {
            Game game = new Game();
            game.playWithInputString(args[0]);
            System.out.println(game.toString());
        } else {
            Game game = new Game();
            game.playWithKeyboard();
        }
        /**Game g = new Game();
        TETile[][] t = g.playWithInputString("n223swwwww");
        Game h = new Game();
        h.playWithInputString("n223swww:q");
        TETile[][] k = h.playWithInputString("lww");
        System.out.println(Arrays.deepEquals(t[60], k[60]));
        for (int i = 0; i < t.length; i++) {
            if (!Arrays.deepEquals(t[i],k[i])) {
                System.out.println(i);
            }
        }
      //  System.out.println(Arrays.toString(t[56]));
        //System.out.println(Arrays.toString(k[56]));

        for (int i = 0; i < t.length; i++) {
            if (!(t[56][i].equals(k[56][i]))) {
                System.out.println(i);
                System.out.println(t[56][i].description() + " " + k[56][i].description());
            }
        }
        System.out.println(Arrays.deepEquals(t, k));
    **/
    }
}
