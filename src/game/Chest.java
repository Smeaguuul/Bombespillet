package game;

public class Chest {
    int points;
    pair location;

    public Chest(pair location) {
        this.points = 20;
        this.location = location;
    }

    public int getPoints() {
        return points;
    }

    public pair getLocation() {
        return location;
    }
}
