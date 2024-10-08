package game;

import javafx.scene.image.ImageView;

public class Player implements Item {
    String name;
    Pair location;
    int point;
    String direction;

    public Player(String name, Pair loc, String direction, int score) {
        this.name = name;
        this.location = loc;
        this.direction = direction;
        this.point = score;
    }

    public String getName() {
        return name;
    }

    public int getPoint() {
        return point;
    }

    public Pair getLocation() {
        return this.location;
    }

    @Override
    public String getType() {
        if (direction.equals("right")) {
            return "heroRight";
        };
        if (direction.equals("left")) {
            return "heroLeft";
        };
        if (direction.equals("up")) {
            return "heroUp";
        };
        if (direction.equals("down")) {
            return "heroDown";
        };
        return "heroUp"; //Default type
    }

    public void setLocation(Pair p) {
        this.location = p;
    }

    public int getXpos() {
        return location.x;
    }

    public void setXpos(int xpos) {
        this.location.x = xpos;
    }

    public int getYpos() {
        return location.y;
    }

    public void setYpos(int ypos) {
        this.location.y = ypos;
    }

    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }

    public void addPoints(int p) {
        point += p;
    }

    public String toString() {
        return name + "," + location + "," + direction + "," + point;
    }
}
