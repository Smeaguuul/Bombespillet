package game.Server;

import game.Bomb;

import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.Date;

import static java.time.temporal.ChronoUnit.SECONDS;

public class BombThread extends Thread {

    private Bomb bomb;

    public BombThread(Bomb bomb) {
        this.bomb = bomb;
    }

    @Override
    public void run() {
        boolean exploded = false;
        while (!exploded) {
            if (bomb.getPlaced().until(LocalTime.now(), SECONDS) >= 5) {
                ServerGameLogic.bombExploded(bomb);
                exploded = true;
            }
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
