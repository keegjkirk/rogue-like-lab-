public class Monster {
    private int x;
    private int y;

    public Monster(int y, int x) {
        this.y = y;
        this.x = x;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void move(Hero hero) {
           if (hero.getX() < x ) {
               moveLeft();
           } else if (hero.getX() > x) {
               moveRight();
           } else if (hero.getY() < y) {
               moveUp();
           } else if (hero.getY() > y) {
               moveDown();
           }
    }

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
