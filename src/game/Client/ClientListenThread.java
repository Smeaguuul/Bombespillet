package game.Client;

import game.Chest;
import game.Player;
import game.pair;

import java.io.BufferedReader;
import java.util.ArrayList;

public class ClientListenThread extends Thread {
    private final BufferedReader inFromServer;
    private String stringFromServer;
    private ArrayList<Player> playerArrayList = new ArrayList<>();
    private ArrayList<Chest> chestArrayList = new ArrayList<>();

    public ClientListenThread(BufferedReader infromServer) {
        this.inFromServer = infromServer;
    }

    @Override
    public void run() {
        try {
            Thread.sleep(3000);
            while (true) {
                stringFromServer = inFromServer.readLine();
                clearObjects();
                String[] stringObjekter = stringFromServer.split("@");

                // Player list
                String[] playerStrings = stringObjekter[0].split("#");
                for (int i = 1; i < playerStrings.length; i++) {
                    String[] playerInfo = playerStrings[i].split(",");
                    pair pair = new pair(Integer.valueOf(playerInfo[1]), Integer.valueOf(playerInfo[2]));
                    Player player = new Player(playerInfo[0], pair, playerInfo[3], Integer.valueOf(playerInfo[4]));
                    playerArrayList.add(player);
                }
                // Chest list
                String[] chestStrings = stringObjekter[1].split("#");
                for (int i = 0; i < chestStrings.length; i++) {
                    String[] chestInfo = chestStrings[i].split(",");
                    Chest chest = new Chest(new pair(Integer.valueOf(chestInfo[0]), Integer.valueOf(chestInfo[1])));
                    chestArrayList.add(chest);
                }

                ClientGameLogic.setPlayerList(playerArrayList, chestArrayList);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void clearObjects(){
        playerArrayList.clear();
        chestArrayList.clear();
    }
}
