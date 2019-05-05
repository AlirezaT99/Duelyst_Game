package model;

public class Coordination {
    private int x;
    private int y;

    public Coordination(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public boolean equals(Coordination coordination) {
        return this.x == coordination.getX() && this.y == coordination.getY();
    }

    //getters
    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
    //getters
}
