import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    static int height = 0;      // Height of the grid
    static int width = 0;       // Width of the grid

    // Initialise grid with chosen criteria
    public static int[][] initState(int h, int w, ArrayList<String> alive) {
        int[][] currentState = new int[h][w];

        for(int x=1;x<alive.size();x+=2) {
            currentState[Integer.parseInt(alive.get(x))][Integer.parseInt(alive.get(x+1))] = 1;
        }
        return currentState;
    }

    // Print current state of the grid
    public static void printState(int[][] grid) {
        for(int x=0;x<height;x++) {
            for(int y=0;y<width;y++) {
                System.out.print(grid[x][y]);
            }
            System.out.println("");
        }
    }

    // Evolve the game by one iteration
    public static int[][] evolution(int[][] grid) {
        int[][] newState = new int[height][width];      // create nextState
        for(int x = 0;x<height;x++) {
            for(int y=0;y<width;y++) {
                // if cell == 1
                if(grid[x][y] == 1) {
                    int count = -1;     // -1 since loop will also check current cell
                    for(int i=-1;i<2;i++) {
                        for(int o=-1;o<2;o++) {
                            // if neighbour cell is not out of bounds and is live, add 1 to count
                            if(x+i >= 0 && x+i < height && y+o >= 0 && y+o < width && grid[x+i][y+o] == 1) {
                                count++;
                            }
                        }
                    }
                    // If cell has less than 2 or more than 3 live neighbours, cell dies
                    if(count < 2 || count > 3) {
                        newState[x][y] = 0;
                    }
                    // if cell has exactly 2 or 3 live neighbours, cell lives on
                    if(count == 2 || count == 3) {
                        newState[x][y] = 1;
                    }
                }

                // if cell == 0
                else {
                    int count = 0;
                    for(int i=-1;i<2;i++) {
                        for(int o=-1;o<2;o++) {
                            // if neighbour cell is not out of bounds and is live, add 1 to count
                            if(x+i >= 0 && x+i < height && y+o >= 0 && y+o < width && grid[x+i][y+o] == 1) {
                                count++;
                            }
                        }
                    }
                    // if cell has 3 live neighbours, cell becomes alive
                    if(count == 3) {
                        newState[x][y] = 1;
                    }
                }
            }
        }
        return newState;
    }
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);

        System.out.println("Welcome to the Game of Life! Please set the size of the grid..");
        System.out.print("Height = ");
        height = scan.nextInt();
        System.out.print("Width = ");
        width = scan.nextInt();

        System.out.println("\nNow select the grids you want to have live cells:");
        System.out.println("eg. type co-ordinates like this \"x,y\" (no quotes) and press enter");
        System.out.println("When you are finished, simply write \"finish\" (no quotes)");
        System.out.print("-> ");

        ArrayList<String> giveLife = new ArrayList<>();

        // while user types input
        while(scan.hasNext()) {
            String s = scan.nextLine();
            String[] split = s.split(",");
            if(s.equals("finish")) {
				System.out.println("\n");
                break;
            }
            for(String toAdd : split) {
                giveLife.add(toAdd);
            }
        }

        // initialise grid with current user requirements
        int[][] current = initState(height, width, giveLife);

        // print current/initial state
        printState(current);

        System.out.println("Type \"evolve\" (no quotes) to evolve the game, type \"finish\" (no quotes) to stop the game");
        // while user types input
        while(scan.hasNext()) {
            String answer = scan.nextLine();
            if(answer.equals("finish")) {
                break;
            }
            else if(answer.equals("evolve")) {
                int[][] newState = evolution(current);
                System.out.println("\n");
                printState(newState);
                current = newState;
                System.out.println("Type \"evolve\" (no quotes) to evolve the game, type \"finish\" (no quotes) to stop the game");
                continue;
            }
            else{
                System.out.println("I didn't get that! Please type \"evolve\" or \"finish\"");
                continue;
            }
        }
        System.out.println("\nThank you for playing!");
    }
}
