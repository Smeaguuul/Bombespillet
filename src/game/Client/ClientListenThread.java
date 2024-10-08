package game.Client;

import game.*;

import java.io.BufferedReader;
import java.time.LocalTime;
import java.util.ArrayList;

public class ClientListenThread extends Thread {
    private final BufferedReader inFromServer;
    private String stringFromServer;
    private ArrayList<Player> playerArrayList = new ArrayList<>();
    private ArrayList<Chest> chestArrayList = new ArrayList<>();
    private ArrayList<Bomb> bombArrayList = new ArrayList<>();

    public ClientListenThread(BufferedReader infromServer) {
        this.inFromServer = infromServer;
    }

    @Override
    public void run() {
        try {
            Thread.sleep(3000);
            while (true) {
                stringFromServer = inFromServer.readLine();
                System.out.println(stringFromServer);
                clearObjects();
                String[] stringObjekter = stringFromServer.split("@");
                // Player list
                String[] playerStrings = stringObjekter[1].split("#");
                for (int i = 1; i < playerStrings.length; i++) {
                    String[] playerInfo = playerStrings[i].split(",");
                    pair pair = new pair(Integer.valueOf(playerInfo[1]), Integer.valueOf(playerInfo[2]));
                    Player player = new Player(playerInfo[0], pair, playerInfo[3], Integer.valueOf(playerInfo[4]));
                    playerArrayList.add(player);
                }
                // Chest list
                String[] chestStrings = stringObjekter[2].split("#");
                for (int i = 1; i < chestStrings.length; i++) {
                    String[] chestInfo = chestStrings[i].split(",");
                    Chest chest = new Chest(new pair(Integer.valueOf(chestInfo[0]), Integer.valueOf(chestInfo[1])));
                    chestArrayList.add(chest);
                }

                // Bomb list
                String[] bombStrings = stringObjekter[3].split("#");
                for (int i = 1; i < bombStrings.length; i++) {
                    String[] bombInfo = bombStrings[i].split(",");
                    Bomb bomb = new Bomb(new pair(Integer.valueOf(bombInfo[0]), Integer.valueOf(bombInfo[1])), LocalTime.parse(bombInfo[2]));
                    bombArrayList.add(bomb);
                }
                ClientGameLogic.setObjectLists(playerArrayList, chestArrayList, bombArrayList);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void clearObjects(){
        playerArrayList.clear();
        chestArrayList.clear();
        bombArrayList.clear();
    }
}
