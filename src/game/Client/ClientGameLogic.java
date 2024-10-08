package game.Client;

import game.Bomb;
import game.Chest;
import game.Player;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;

public class ClientGameLogic {
    private static DataOutputStream outToServer;
    public static ArrayList<Player> playerList = new ArrayList<Player>();
    public static ArrayList<Chest> chestArrayList = new ArrayList<>();
    public static ArrayList<Bomb> bombArrayList = new ArrayList<>();
    private static BufferedReader bufferedReader;

    private static ClientListenThread clientListenThread = new ClientListenThread(bufferedReader);

    public static void setOutToServer(DataOutputStream outToServer) {
        ClientGameLogic.outToServer = outToServer;
    }

    public static void setObjectLists(ArrayList<Player> newPlayerList, ArrayList<Chest> newChestArrayList, ArrayList<Bomb> newBombArrayList){
        Gui.removePlayers(new ArrayList<>(playerList));
        playerList = new ArrayList<>(newPlayerList);

        Gui.removeChests(new ArrayList<>(chestArrayList));
        chestArrayList = new ArrayList<>(newChestArrayList);

        Gui.removeBombs(new ArrayList<>(bombArrayList));
        bombArrayList = new ArrayList<>(newBombArrayList);

        Gui.updateGUI(playerList, chestArrayList, bombArrayList);
    }

    public static void placeBomb () {
        try {
            outToServer.writeBytes("BOMB" + '\n');
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void playerMoved(int delta_x, int delta_y, String direction) {
        try {
            outToServer.writeBytes(direction.toUpperCase() + '\n');
            //GameLogic.updatePlayer(delta_x,delta_y,direction);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
