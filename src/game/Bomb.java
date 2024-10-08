package game;

import java.time.LocalTime;

public class Bomb implements Item {
    private Pair location;
    private LocalTime placed;

    public Bomb(Pair location) {
        this.location = location;
        placed = LocalTime.now();
    }

    public Bomb(Pair location, LocalTime placed) {
        this.location = location;
        this.placed = placed;
    }

    @Override
    public String toString() {
        return location + "," + placed;
    }

    public Pair getLocation() {
        return location;
    }

    @Override
    public String getType() {
        return "bomb";
    }
}
