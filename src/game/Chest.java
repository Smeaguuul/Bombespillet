package game;

public class Chest implements Item {
    int points;
    Pair location;

    public Chest(Pair location) {
        this.points = 20;
        this.location = location;
    }

    public int getPoints() {
        return points;
    }

    public Pair getLocation() {
        return location;
    }

    @Override
    public String getType() {
        return "chest";
    }

    @Override
    public String toString() {
        return location + "," + points;
    }
}
