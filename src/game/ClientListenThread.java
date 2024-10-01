package game;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;

public class ClientListenThread extends Thread {
    private final BufferedReader inFromServer;
    private String stringFromServer;
    private ArrayList<Player> playerArrayList = new ArrayList<>();
    ;

    public ClientListenThread(BufferedReader infromServer) {
        this.inFromServer = infromServer;
    }

    @Override
    public void run() {
        try {
            Thread.sleep(3000);
            while (true) {
                stringFromServer = inFromServer.readLine();
                playerArrayList.clear();
                String[] playerStrings = stringFromServer.split("#");
                for (int i = 1; i < playerStrings.length; i++) {
                    String[] playerInfo = playerStrings[i].split(",");
                    pair pair = new pair(Integer.valueOf(playerInfo[1]), Integer.valueOf(playerInfo[2]));
                    Player player = new Player(playerInfo[0], pair, playerInfo[3], Integer.valueOf(playerInfo[4]));
                    playerArrayList.add(player);
                }

                ClientGameLogic.setPlayerList(playerArrayList);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
