package game;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;

public class ClientGameLogic {
    private static DataOutputStream outToServer;
    private static ArrayList<Player> playerList = new ArrayList<Player>();
    private static BufferedReader bufferedReader;

    private static ClientListenThread clientListenThread = new ClientListenThread(bufferedReader);

    public static void setOutToServer(DataOutputStream outToServer) {
        ClientGameLogic.outToServer = outToServer;
    }

    public static void setPlayerList(ArrayList<Player> newPlayerList){
        playerList = newPlayerList;
    }

    public static void playerMoved(int delta_x, int delta_y, String direction) {
        try {
            outToServer.writeBytes(direction.toUpperCase() + '\n');
            //GameLogic.updatePlayer(delta_x,delta_y,direction);
//        updateScoreTable();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
