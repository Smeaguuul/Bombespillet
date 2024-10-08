package game;

import java.time.LocalTime;

public class Bomb {
    private pair location;
    private LocalTime placed;

    public Bomb(pair location) {
        this.location = location;
        placed = LocalTime.now();
    }

    public Bomb(pair location, LocalTime placed) {
        this.location = location;
        this.placed = placed;
    }

    @Override
    public String toString() {
        return location + "," + placed;
    }

    public pair getLocation() {
        return location;
    }
}
