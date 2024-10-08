package game.Server;

import game.Bomb;

import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.Date;

import static java.time.temporal.ChronoUnit.SECONDS;

public class BombThread extends Thread{

    private Bomb bomb;

    public BombThread(Bomb bomb) {
        this.bomb = bomb;
    }

    @Override
    public void run() {
        if (bomb.getPlaced().until(LocalTime.now(), SECONDS) >= 5){
            ServerGameLogic.bombExploded(bomb.getLocation());

        }
    }
}
