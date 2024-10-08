package game;

public class Explosion implements Item {
    Pair location;

    @Override
    public Pair getLocation() {
        return location;
    }

    @Override
    public String getType() {
        return "explosion";
    }

    public Explosion(Pair location) {
        this.location = location;
    }
}
