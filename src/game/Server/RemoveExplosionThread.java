package game.Server;

import game.Explosion;

import java.time.LocalTime;
import java.util.ArrayList;

public class RemoveExplosionThread extends Thread {
    private ArrayList<Explosion> explosions = new ArrayList<>();
    private LocalTime blowTime;

    public RemoveExplosionThread(ArrayList<Explosion> explosions) {
        this.explosions = explosions;
        this.blowTime = LocalTime.now();
    }

    @Override
    public void run() {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            ServerGameLogic.removeExplosions(explosions);
    }
}
