import java.util.Random;
import java.util.Scanner;

public class Game {

    // constants
    private  static int WIDTH;
    private final static String WALL_CHARACTER = "M";
    private final static String EMPTY_CHARACTER = " ";

    private final Scanner console = new Scanner(System.in);
    private Hero hero;
    private Treasure treasure;
    private Treasure treasureTwo;
    private Trap trap;
    private HiddenTreasure hiddenTreasure;
    private Monster monster;
    private boolean isOver;
    private boolean[] treasuresCollected = new boolean[2];
    private boolean monsterDefeated;

    private int[][] coordinates = new int[6][2];

    public void run() {
        setUp();
        while (!isOver) {
            printWorld();
            move();
        }
        printWorld();
    }

    private void setUp() {
        System.out.print("What is the name of your hero?: ");
        String name = console.nextLine();

        System.out.print("Input your hero's symbol: ");
        char symbol = console.nextLine().charAt(0);

        System.out.print("World Size: ");
        WIDTH = Integer.parseInt(console.nextLine());

        Random rand = new Random();
        int x = rand.nextInt(WIDTH);
        int y = rand.nextInt(WIDTH);

        hero = new Hero(name, symbol, x, y, 10);
        coordinates[0][0] = x;
        coordinates[0][1] = y;

        do {
            x = rand.nextInt(WIDTH);
            y = rand.nextInt(WIDTH);
        } while (isOccupied(x, y));

        treasure = new Treasure(x, y);
        coordinates[1][0] = x;
        coordinates[1][1] = y;

        do {
            x = rand.nextInt(WIDTH);
            y = rand.nextInt(WIDTH);
        } while (isOccupied(x, y));

        treasureTwo = new Treasure(x, y);
        coordinates[2][0] = x;
        coordinates[2][1] = y;

        do {
            x = rand.nextInt(WIDTH);
            y = rand.nextInt(WIDTH);
        } while (isOccupied(x, y));

        trap = new Trap(x, y);
        coordinates[3][0] = x;
        coordinates[3][1] = y;

        do {
            x = rand.nextInt(WIDTH);
            y = rand.nextInt(WIDTH);
        } while (isOccupied(x, y));

        hiddenTreasure = new HiddenTreasure(x, y);
        coordinates[4][0] = x;
        coordinates[4][1] = y;

        do {
            x = rand.nextInt(WIDTH);
            y = rand.nextInt(WIDTH);
        } while (isOccupied(x, y));

        monster = new Monster(x, y);
        coordinates[5][0] = x;
        coordinates[5][1] = y;
    }





    private void printWorld() {
        // top wall border
        System.out.println(WALL_CHARACTER.repeat(WIDTH + 2));

        for (int row = 0; row < WIDTH; row++) {
            // left wall border
            System.out.print(WALL_CHARACTER);
            for (int col = 0; col < WIDTH; col++) {
                if (row == hero.getY() && col == hero.getX()) {
                    System.out.print(hero.getSymbol());
                } else if (row == treasure.getY() && col == treasure.getX() && !treasuresCollected[0]) {
                    System.out.print("T");
                } else if (row == treasureTwo.getY() && col == treasureTwo.getX() && !treasuresCollected[1]) {
                    System.out.print("T");
                } else if (row == trap.getY() && col == trap.getX()) {
                    System.out.print("*");
                } else if (row == monster.getY() && col == monster.getX() && !monsterDefeated) {
                    System.out.print("!");
                }
                else {
                    System.out.print(EMPTY_CHARACTER);
                }
            }

            // right wall border
            System.out.println(WALL_CHARACTER);
        }

        // bottom wall border
        System.out.println(WALL_CHARACTER.repeat(WIDTH + 2));
    }

    private void move() {

        System.out.print(hero.getName() + ", move [WASD]: ");
        String move = console.nextLine().trim().toUpperCase();

        if (move.length() != 1) {
            return;
        }

        monster.move(hero);

        switch (move.charAt(0)) {
            case 'W':
                hero.moveUp();
                break;
            case 'A':
                hero.moveLeft();
                break;
            case 'S':
                hero.moveDown();
                break;
            case 'D':
                hero.moveRight();
                break;
        }

        if (hero.getX() < 0 || hero.getX() >= WIDTH
                || hero.getY() < 0 || hero.getY() >= WIDTH) {
            System.out.println(hero.getName() + " touched lava! You lose.");
            isOver = true;
        } else if (hero.getX() == trap.getX() && hero.getY() == trap.getY()) {
            System.out.println(hero.getName() + " stepped on a trap! You lose.");
            isOver = true;
        } else if (hero.getX() == treasure.getX() && hero.getY() == treasure.getY()) {
            System.out.println(hero.getName() + " found the first treasure!");
            treasuresCollected[0] = true;
            isOver = treasuresCollected[0] && treasuresCollected[1];

        } else if (hero.getX() == treasureTwo.getX() && hero.getY() == treasureTwo.getY()) {
            System.out.println(hero.getName() + " found the second treasure!");
            treasuresCollected[1] = true;
            isOver = treasuresCollected[0] && treasuresCollected[1];
        } else if (hero.getX() == hiddenTreasure.getX() && hero.getY() == hiddenTreasure.getY()) {
            System.out.println("You found the hidden treasure!");
        } else if (hero.getX() == monster.getX() && hero.getY() == monster.getY()) {

            int monsterHealth = (int) (2 + Math.random() * 5);
            int damage = 1;
            for (boolean treasure : treasuresCollected) {
                if (treasure) {
                    damage += 2;
                }
            }
            monsterDefeated = doFight(monsterHealth, hero.getHeroHealth(), damage);
            if (!monsterDefeated) {
                isOver = true;
            }
        }
    }

    public boolean isOccupied(int x, int y) {
        for (int i = 0; i < coordinates.length; i++) {
            if (x == coordinates[i][0] && y == coordinates[i][1]) {
                return true;
            }
        }
        return false;
    }

    public static boolean doFight(int monHealth, int heroHealth, int damage){
        String[] words = {
                "apple", "banana", "cherry", "date", "elderberry",
                "fig", "grape", "honeydew", "kiwi", "lemon",
                "mango", "nectarine", "orange", "papaya", "quince",
                "raspberry", "strawberry", "tangerine", "ugli", "vanilla",
                "watermelon", "yellowfruit", "zucchini"
        };
        String monType = words[(int)(Math.random()*words.length)];
        System.out.println("You have encountered a " + monType + " monster! It challenges you to a fight!");
        boolean fighting = true;
        int choice = -1;
        int monCurHealth = monHealth;
        while (fighting){
            System.out.println(" 1. Punch\n 2. Flirt\n 3. Run");

            String[] choices = {"1","2","3"};
            choice =  Integer.parseInt(reqSpecificInput(choices));
            switch (choice){
                case 1:
                    monCurHealth -= damage;
                    System.out.println("You punched the monster! It now has " + monCurHealth + " health!");
                    fighting = (monCurHealth > 0);
                    break;
                case 2:
                    double flirtRate = Math.random();
                    if (flirtRate > 0.5){
                        System.out.println("The monster fell in love with you! You are free to go!");
                        fighting = false;
                    }else {
                        System.out.println("It seems you and the monster have no common ground.");
                    }
                    break;
                case 3:
                    System.out.println("You got away, not safely though.");
                    heroHealth--;
                    System.out.println("The monster hit you, ouch! You are at " +  heroHealth + " health!");
                    fighting = false;
            }
            if (fighting){
                heroHealth--;
                System.out.println("The monster hit you, ouch! You are at " +  heroHealth + " health!");
                if ( heroHealth <= 0){
                    System.out.println("You died to the monster's attack.");
                    fighting = false;
                    return false;
                }
            }
        }
        return true;
    }
    public static String reqSpecificInput(String[] reqs){
        Scanner scan = new Scanner(System.in);

        //continues prompting string, exists when string is of valid input
        while(true){
            String ret = scan.nextLine();
            for(int i = 0; i < reqs.length; i++){
                if (ret.equals(reqs[i])){
                    return ret;
                }
            }
            System.out.println("Invalid Input - Try Again:");
        }
    }


}
