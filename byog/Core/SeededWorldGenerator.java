package byog.Core;

import byog.TileEngine.TERenderer;
import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;
import edu.princeton.cs.introcs.StdDraw;

import java.io.PrintWriter;
import java.util.Random;

public class SeededWorldGenerator {

    int numFlowers;
    int score;
    private Random RANDOM;
    private int width;
    private int height;
    private int curX;
    private int curY;

    public SeededWorldGenerator(int width, int height, long seed) {
        RANDOM = new Random(seed);
        this.width = width;
        this.height = height;
        score = 100;
        numFlowers = 5;
    }

    public int[] hallwayGenerator(TETile[][] tiles, int xoffset, int yoffset, int length, int dir) {
        if (dir == 0) {
            tiles[xoffset][yoffset] = Tileset.SAND;
            for (int i = 1; i <= length; i++) {
                setWall(tiles, xoffset - 1, yoffset + i);
                setWall(tiles, xoffset + 1, yoffset + i);
                tiles[xoffset][yoffset + i] = Tileset.SAND;
            }
            tiles[xoffset][yoffset + length] = Tileset.SAND;
            setWallIfNothing(tiles, xoffset, yoffset + length + 1);
            return new int[]{xoffset, yoffset + length - 1, 0, length};
        } else if (dir == 3) {
            tiles[xoffset][yoffset] = Tileset.SAND;
            for (int i = 1; i <= length; i++) {
                setWall(tiles, xoffset + i, yoffset - 1);
                setWall(tiles, xoffset + i, yoffset + 1);
                tiles[xoffset + i][yoffset] = Tileset.SAND;
            }
            tiles[xoffset + length][yoffset] = Tileset.SAND;
            setWallIfNothing(tiles, xoffset + length + 1, yoffset);
            return new int[]{xoffset + length - 1, yoffset, 3, length};
        } else if (dir == 2) {
            tiles[xoffset][yoffset] = Tileset.SAND;
            for (int i = 1; i <= length; i++) {
                setWall(tiles, xoffset - 1, yoffset - i);
                setWall(tiles, xoffset + 1, yoffset - i);
                tiles[xoffset][yoffset - i] = Tileset.SAND;
            }
            tiles[xoffset][yoffset - length] = Tileset.SAND;
            setWallIfNothing(tiles, xoffset, yoffset - length - 1);
            return new int[]{xoffset, yoffset - length + 1, 2, length};
        } else if (dir == 1) {
            tiles[xoffset][yoffset] = Tileset.SAND;
            for (int i = 1; i <= length; i++) {
                setWall(tiles, xoffset - i, yoffset - 1);
                setWall(tiles, xoffset - i, yoffset + 1);
                tiles[xoffset - i][yoffset] = Tileset.SAND;
            }
            tiles[xoffset - length][yoffset] = Tileset.SAND;
            setWallIfNothing(tiles, xoffset - length - 1, yoffset);
            return new int[]{xoffset - length + 1, yoffset, 1, length};
        }
        return null;
    }

    public int[] hallwayTurnGenerator(TETile[][] tiles, int xoffset,
                                      int yoffset, int length1, int length2, int dir1, int dir2) {
        int[] tempArray = hallwayGenerator(tiles, xoffset, yoffset, length1, dir1);
        if (dir1 == 0) {
            tempArray[1] = tempArray[1] - 1;
        } else if (dir1 == 1) {
            tempArray[0] = tempArray[0] - 1;
        } else if (dir1 == 2) {
            tempArray[1] = tempArray[1] + 1;
        } else if (dir1 == 3) {
            tempArray[0] = tempArray[0] + 1;
        }
        return hallwayGenerator(tiles, tempArray[0], tempArray[1], length2, dir2);
    }

    public int[] roomGenerator2(TETile[][] tiles,
                                int xoffset, int yoffset, int width1, int height1) {
        for (int x = xoffset - width1 / 2; x <= xoffset + width1 / 2; x += 1) {
            for (int y = yoffset - height1 / 2 + 1; y < yoffset + height1 / 2; y += 1) {
                setFloor(tiles, x, y);
            }
            setWall(tiles, x, yoffset - height1 / 2);
            setWall(tiles, x, yoffset + height1 / 2);
        }
        for (int x = xoffset - width1 / 2 + 1; x < xoffset + width1 / 2; x += 1) {
            tiles[x][yoffset] = Tileset.SAND;
        }
        for (int y = yoffset - height1 / 2 + 1; y < yoffset + height1 / 2; y += 1) {
            setWall(tiles, xoffset - width1 / 2, y);
            tiles[xoffset][y] = Tileset.SAND;
            setWall(tiles, xoffset + width1 / 2, y);
        }
        int dir;
        if (RANDOM.nextDouble() > .75) {
            int rh = RANDOM.nextInt(this.height);
            if (rh > yoffset) {
                dir = 0;
            } else {
                dir = 2;
            }
        } else {
            int rw = RANDOM.nextInt(this.width);
            if (rw < xoffset) {
                dir = 1;
            } else {
                dir = 3;
            }
        }
        if (dir == 0) {
            int[] posArray = new int[]{xoffset + width1 / 2 - 1 - RANDOM.nextInt
                    (width1 - 2), yoffset + height1 / 2, 0};
            setFloor(tiles, posArray[0], posArray[1]);
            return posArray;
        } else if (dir == 1) {
            int[] posArray = new int[]{xoffset - width1 / 2, yoffset + height1 / 2 - 1
                    - RANDOM.nextInt(height1 - 2), 1};
            setFloor(tiles, posArray[0], posArray[1]);
            return posArray;
        } else if (dir == 2) {
            int[] posArray = new int[]{xoffset
                    + width1 / 2 - 1 - RANDOM.nextInt(width1 - 2), yoffset - height1 / 2, 2};
            setFloor(tiles, posArray[0], posArray[1]);
            return posArray;
        } else {
            int[] posArray = new int[]{xoffset + width1 / 2, yoffset + height1 / 2 - 1
                    - RANDOM.nextInt(height1 - 2), 3};
            setFloor(tiles, posArray[0], posArray[1]);
            return posArray;
        }
    }

    public void setWall(TETile[][] tiles, int x, int y) {
        if (tiles[x][y] != Tileset.SAND && tiles[x][y] != Tileset.LOCKED_DOOR) {
            tiles[x][y] = Tileset.WALL;
        }
    }

    public void setWallIfNothing(TETile[][] tiles, int x, int y) {
        if (tiles[x][y] == Tileset.NOTHING) {
            tiles[x][y] = Tileset.WALL;
        }
    }

    public void setFloor(TETile[][] tiles, int x, int y) {
        if (tiles[x][y] != Tileset.SAND && tiles[x][y] != Tileset.LOCKED_DOOR) {
            tiles[x][y] = Tileset.FLOOR;
        }
    }

    public void generateWorld(TETile[][] tiles) {
        try {
            int[] posArray = new int[]{width / 4 + RANDOM.nextInt(10) - 4, height / 2};
            posArray = roomGenerator2(tiles, posArray[0], posArray[1],
                    RANDOM.nextInt(3) + 4, RANDOM.nextInt(3) + 5);
            for (int i = 0; i < 50; i++) {
                posArray = hallwayTurnGenerator(tiles, posArray[0], posArray[1],
                        RANDOM.nextInt(7) + 3, RANDOM.nextInt(4) + 3,
                        posArray[2], (posArray[2] + 1 + (RANDOM.nextInt(2) * 2)) % 4);
                posArray = roomGenerator2(tiles, posArray[0], posArray[1],
                        RANDOM.nextInt(3) + 4, RANDOM.nextInt(3) + 4);
            }
            tiles[posArray[0]][posArray[1]] = Tileset.WATER;
            while (true) {
                int z1 = RANDOM.nextInt(tiles.length);
                int z2 = RANDOM.nextInt(tiles[0].length);
                if (tiles[z1][z2].description().equals(Tileset.FLOOR.description())) {
                    tiles[z1][z2] = Tileset.MOUNTAIN;
                    curX = z1;
                    curY = z2;
                    break;
                }
            }
            int x = 0;
            while (x < numFlowers) {
                int z1 = RANDOM.nextInt(tiles.length);
                int z2 = RANDOM.nextInt(tiles[0].length);
                if (tiles[z1][z2].description().equals(Tileset.FLOOR.description())) {
                    tiles[z1][z2] = Tileset.FLOWER;
                    x++;
                }

            }
            cleanTiles(tiles);
        } catch (ArrayIndexOutOfBoundsException e) {
            clearTiles(tiles);
            generateWorld(tiles);
        }
    }


    private void cleanTiles(TETile[][] tiles) {
        for (int x = 0; x < width; x += 1) {
            for (int y = 0; y < height; y += 1) {
                if (tiles[x][y] == Tileset.SAND || tiles[x][y] == Tileset.LOCKED_DOOR) {
                    tiles[x][y] = Tileset.FLOOR;
                }
            }
        }
    }

    public boolean moveload(TETile[][] tiles, String exp, PrintWriter p) {
        while (exp.length() > 0) {
            char b = exp.charAt(0);
            if (b != ':' && b != 'q') {
                p.write(b);
            }
            if (b == 'w') {
                if (tiles[curX][curY + 1].equals(Tileset.FLOOR)) {
                    tiles[curX][curY] = Tileset.FLOOR;
                    tiles[curX][curY + 1] = Tileset.MOUNTAIN;
                    curY = curY + 1; score--;
                } else if (tiles[curX][curY + 1].equals(Tileset.FLOWER)) {
                    tiles[curX][curY] = Tileset.FLOOR;
                    tiles[curX][curY + 1] = Tileset.MOUNTAIN;
                    curY = curY + 1; score = score + 9;
                } else {
                    score = score - 5;
                }
            } else if (b == 's') {
                if (tiles[curX][curY - 1].equals(Tileset.FLOOR)) {
                    tiles[curX][curY] = Tileset.FLOOR;
                    tiles[curX][curY - 1] = Tileset.MOUNTAIN;
                    curY = curY - 1; score--;
                } else if (tiles[curX][curY - 1].equals(Tileset.FLOWER)) {
                    tiles[curX][curY] = Tileset.FLOOR;
                    tiles[curX][curY - 1] = Tileset.MOUNTAIN;
                    curY = curY - 1; score = score + 9;
                } else {
                    score = score - 5;
                }
            } else if (b == 'a') {
                if (tiles[curX - 1][curY].equals(Tileset.FLOOR)) {
                    tiles[curX][curY] = Tileset.FLOOR;
                    tiles[curX - 1][curY] = Tileset.MOUNTAIN;
                    curX = curX - 1; score--;
                } else if (tiles[curX - 1][curY].equals(Tileset.FLOWER)) {
                    tiles[curX][curY] = Tileset.FLOOR;
                    tiles[curX - 1][curY] = Tileset.MOUNTAIN;
                    curX = curX - 1; score = score + 9;
                } else {
                    score = score - 5;
                }
            } else if (b == 'd') {
                if (tiles[curX + 1][curY].equals(Tileset.FLOOR)) {
                    tiles[curX][curY] = Tileset.FLOOR;
                    tiles[curX + 1][curY] = Tileset.MOUNTAIN;
                    curX = curX + 1;
                    score--;
                } else if (tiles[curX + 1][curY].equals(Tileset.FLOWER)) {
                    tiles[curX][curY] = Tileset.FLOOR;
                    tiles[curX + 1][curY] = Tileset.MOUNTAIN;
                    curX = curX + 1;
                    score = score + 9;
                } else {
                    score = score - 5;
                }
            } else if (b == ':') {
                exp = exp.substring(1);
                char q = exp.charAt(0);
                if (q == 'q') {
                    return false;
                }
            } else if (b == 't') {
                tiles[curX][curY] = Tileset.FLOOR;
                while (true) {
                    int z1 = RANDOM.nextInt(tiles.length);
                    int z2 = RANDOM.nextInt(tiles[0].length);
                    if (tiles[z1][z2].description().equals(Tileset.FLOOR.description())) {
                        tiles[z1][z2] = Tileset.MOUNTAIN;
                        curX = z1;
                        curY = z2;
                        break;
                    }
                }
                score = score - 25;
            }
            exp = exp.substring(1);
        }
        return true;
    }

    public boolean moveload(TETile[][] tiles, String exp, PrintWriter p, TERenderer teRenderer) {
        while (exp.length() > 0) {
            char b = exp.charAt(0);
            if (b != ':' && b != 'q') {
                p.write(b);
            }
            if (b == 'w') {
                if (tiles[curX][curY + 1].equals(Tileset.FLOOR)) {
                    tiles[curX][curY] = Tileset.FLOOR;
                    tiles[curX][curY + 1] = Tileset.MOUNTAIN;
                    curY = curY + 1; score--;
                } else if (tiles[curX][curY + 1].equals(Tileset.FLOWER)) {
                    tiles[curX][curY] = Tileset.FLOOR;
                    tiles[curX][curY + 1] = Tileset.MOUNTAIN;
                    curY = curY + 1; score = score + 9;
                } else {
                    score = score - 5;
                }
            } else if (b == 's') {
                if (tiles[curX][curY - 1].equals(Tileset.FLOOR)) {
                    tiles[curX][curY] = Tileset.FLOOR;
                    tiles[curX][curY - 1] = Tileset.MOUNTAIN;
                    curY = curY - 1; score--;
                } else if (tiles[curX][curY - 1].equals(Tileset.FLOWER)) {
                    tiles[curX][curY] = Tileset.FLOOR;
                    tiles[curX][curY - 1] = Tileset.MOUNTAIN;
                    curY = curY - 1; score = score + 9;
                } else {
                    score = score - 5;
                }
            } else if (b == 'a') {
                if (tiles[curX - 1][curY].equals(Tileset.FLOOR)) {
                    tiles[curX][curY] = Tileset.FLOOR;
                    tiles[curX - 1][curY] = Tileset.MOUNTAIN;
                    curX = curX - 1; score--;
                } else if (tiles[curX - 1][curY].equals(Tileset.FLOWER)) {
                    tiles[curX][curY] = Tileset.FLOOR;
                    tiles[curX - 1][curY] = Tileset.MOUNTAIN;
                    curX = curX - 1; score = score + 9;
                } else {
                    score = score - 5;
                }
            } else if (b == 'd') {
                if (tiles[curX + 1][curY].equals(Tileset.FLOOR)) {
                    tiles[curX][curY] = Tileset.FLOOR;
                    tiles[curX + 1][curY] = Tileset.MOUNTAIN;
                    curX = curX + 1; score--;
                } else if (tiles[curX + 1][curY].equals(Tileset.FLOWER)) {
                    tiles[curX][curY] = Tileset.FLOOR;
                    tiles[curX + 1][curY] = Tileset.MOUNTAIN;
                    curX = curX + 1; score = score + 9;
                } else {
                    score = score - 5;
                }
            } else if (b == 't') {
                tiles[curX][curY] = Tileset.FLOOR;
                while (true) {
                    int z1 = RANDOM.nextInt(tiles.length);
                    int z2 = RANDOM.nextInt(tiles[0].length);
                    if (tiles[z1][z2].description().equals(Tileset.FLOOR.description())) {
                        tiles[z1][z2] = Tileset.MOUNTAIN;
                        curX = z1;
                        curY = z2;
                        break;
                    }
                }
                score = score - 25;
            } else if (b == ':') {
                exp = exp.substring(1);
                char q = exp.charAt(0);
                if (q == 'q') {
                    return false;
                }
            } else {
                moveScreen(b, teRenderer);
            }
            exp = exp.substring(1);
        }
        return true;
    }

    public int move(TETile[][] tiles, PrintWriter p, TERenderer teRenderer) {
        char b = getcharwithrenderer(p, tiles, teRenderer);
        if (b == 'w') {
            if (tiles[curX][curY + 1].equals(Tileset.FLOOR)) {
                tiles[curX][curY] = Tileset.FLOOR;
                tiles[curX][curY + 1] = Tileset.MOUNTAIN;
                curY = curY + 1; score--;
            } else if (tiles[curX][curY + 1].equals(Tileset.WATER)) {
                tiles[curX][curY] = Tileset.FLOOR;
                tiles[curX][curY + 1] = Tileset.MOUNTAIN;
                curY = curY + 1; score--;
                return -2;
            } else if (tiles[curX][curY + 1].equals(Tileset.FLOWER)) {
                tiles[curX][curY] = Tileset.FLOOR;
                tiles[curX][curY + 1] = Tileset.MOUNTAIN;
                curY = curY + 1; score = score + 9;
            } else {
                score = score - 5;
            }
        } else if (b == 's') {
            if (tiles[curX][curY - 1].equals(Tileset.FLOOR)) {
                tiles[curX][curY] = Tileset.FLOOR;
                tiles[curX][curY - 1] = Tileset.MOUNTAIN;
                curY = curY - 1; score--;
            } else if (tiles[curX][curY - 1].equals(Tileset.WATER)) {
                tiles[curX][curY] = Tileset.FLOOR;
                tiles[curX][curY - 1] = Tileset.MOUNTAIN;
                curY = curY - 1; score--; return -2;
            } else if (tiles[curX][curY - 1].equals(Tileset.FLOWER)) {
                tiles[curX][curY] = Tileset.FLOOR;
                tiles[curX][curY - 1] = Tileset.MOUNTAIN;
                curY = curY - 1; score = score + 9;
            } else {
                score = score - 5;
            }
        } else if (b == 'a') {
            if (tiles[curX - 1][curY].equals(Tileset.FLOOR)) {
                tiles[curX][curY] = Tileset.FLOOR;
                tiles[curX - 1][curY] = Tileset.MOUNTAIN;
                curX = curX - 1; score--;
            } else if (tiles[curX - 1][curY].equals(Tileset.WATER)) {
                tiles[curX][curY] = Tileset.FLOOR;
                tiles[curX - 1][curY] = Tileset.MOUNTAIN;
                curX = curX - 1; score--; return -2;
            } else if (tiles[curX - 1][curY].equals(Tileset.FLOWER)) {
                tiles[curX][curY] = Tileset.FLOOR;
                tiles[curX - 1][curY] = Tileset.MOUNTAIN;
                curX = curX - 1; score = score + 9;
            } else {
                score = score - 5;
            }
        } else if (b == 'd') {
            if (tiles[curX + 1][curY].equals(Tileset.FLOOR)) {
                tiles[curX][curY] = Tileset.FLOOR;
                tiles[curX + 1][curY] = Tileset.MOUNTAIN;
                curX = curX + 1; score--;
            } else if (tiles[curX + 1][curY].equals(Tileset.WATER)) {
                tiles[curX][curY] = Tileset.FLOOR;
                tiles[curX + 1][curY] = Tileset.MOUNTAIN;
                curX = curX + 1; score--;
                return -2;
            } else if (tiles[curX + 1][curY].equals(Tileset.FLOWER)) {
                tiles[curX][curY] = Tileset.FLOOR;
                tiles[curX + 1][curY] = Tileset.MOUNTAIN;
                curX = curX + 1; score = score + 9;
            } else {
                score = score - 5;
            }
        } else if (b == ':') {
            char q = getChar(p);
            if (q == 'q') {
                return 0;
            }
        } else if (b == 't') {
            teleport(tiles);
        } else {
            moveScreen(b, teRenderer);
        }
        return 1;
    }

    public void moveScreen(char b, TERenderer teRenderer) {
        if (b == 'j') {
            teRenderer.addyOffset();
        } else if (b == 'u') {
            teRenderer.subyOffset();
        } else if (b == 'h') {
            teRenderer.addxOffset();
        } else if (b == 'k') {
            teRenderer.subxOffset();
        }
    }

    public void teleport(TETile[][] tiles) {
        tiles[curX][curY] = Tileset.FLOOR;
        while (true) {
            int z1 = RANDOM.nextInt(tiles.length);
            int z2 = RANDOM.nextInt(tiles[0].length);
            if (tiles[z1][z2].description().equals(Tileset.FLOOR.description())) {
                tiles[z1][z2] = Tileset.MOUNTAIN;
                curX = z1; curY = z2;
                break;
            }
        }
        score = score - 25;
    }
    public Character getChar(PrintWriter p) {
        char in = '%';
        StdDraw.clear();
        while (in == '%') {
            if (StdDraw.hasNextKeyTyped()) {
                in = StdDraw.nextKeyTyped();

            }
        }
        // Read n letters of player input
        if (in != 'q' && in != ':') {
            p.write(in);
        }
        return in;
    }

    public Character getcharwithrenderer(PrintWriter p, TETile[][] tiles, TERenderer ter) {
        char in = '%';
        StdDraw.clear();
        while (in == '%') {
            if (StdDraw.hasNextKeyTyped()) {
                in = StdDraw.nextKeyTyped();

            }
            ter.drawTileInfo(tiles, score);
            StdDraw.pause(50);
        }
        if (in != 'q' && in != ':') {
            p.write(in);
        }
        return in;
    }

    public void clearTiles(TETile[][] tiles) {
        for (int x = 0; x < tiles.length; x += 1) {
            for (int y = 0; y < tiles[0].length; y += 1) {
                tiles[x][y] = Tileset.NOTHING;
            }
        }
    }
}
