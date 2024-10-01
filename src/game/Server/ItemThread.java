package game.Server;

import game.Chest;

public class ItemThread extends Thread {
    public ItemThread() {
    }

    @Override
    public void run() {
        try {
            Thread.sleep(3000);
            while (true) {
                Thread.sleep(5000);
                if (Math.random() > 0.5) {
                    Chest chest = new Chest(ServerGameLogic.getRandomFreePosition());
                    ServerGameLogic.addChest(chest);
                }
            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
