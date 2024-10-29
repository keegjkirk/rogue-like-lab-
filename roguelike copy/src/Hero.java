public class Hero {

    private final String name;
    private char symbol = '@';
    private int x;
    private int y;
    private int heroHealth;

    // Create a hero with a name and an initial position.
    public Hero(String name, char symbol, int x, int y, int heroHealth) {
        this.name = name;
        this.symbol = symbol;
        this.x = x;
        this.y = y;
        this.heroHealth = heroHealth;
    }

    // getters
    public String getName() {
        return name;
    }

    public char getSymbol() {
        return symbol;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getHeroHealth() {return  heroHealth;}
    // movement
    public void moveLeft() {
        x--;
    }

    public void moveRight() {
        x++;
    }

    public void moveUp() {
        y--;
    }

    public void moveDown() {
        y++;
    }
}
