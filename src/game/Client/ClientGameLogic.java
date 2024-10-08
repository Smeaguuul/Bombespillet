package game.Client;

import game.*;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;

public class ClientGameLogic {
    private static DataOutputStream outToServer;
    public static ArrayList<Player> playerList = new ArrayList<Player>();
    public static ArrayList<Chest> chestArrayList = new ArrayList<>();
    public static ArrayList<Bomb> bombArrayList = new ArrayList<>();
    public static ArrayList<Explosion> explosionArrayList = new ArrayList<>();
    private static BufferedReader bufferedReader;


    private static ClientListenThread clientListenThread = new ClientListenThread(bufferedReader);

    public static void setOutToServer(DataOutputStream outToServer) {
        ClientGameLogic.outToServer = outToServer;
    }

    public static void setObjectLists(ArrayList<Player> newPlayerList, ArrayList<Chest> newChestArrayList, ArrayList<Bomb> newBombArrayList, ArrayList<Explosion> newExplosionArrayList){
        //Remove items
        ArrayList<Item> items = new ArrayList<>();
        items.addAll(playerList);
        items.addAll(chestArrayList);
        items.addAll(bombArrayList);
        items.addAll(explosionArrayList);
        Gui.removeItems(items);

        //Place items
        playerList = new ArrayList<>(newPlayerList);
        chestArrayList = new ArrayList<>(newChestArrayList);
        bombArrayList = new ArrayList<>(newBombArrayList);
        explosionArrayList = new ArrayList<>(newExplosionArrayList);
        ArrayList<Item> newLists = new ArrayList<>();
        newLists.addAll(playerList);
        newLists.addAll(chestArrayList);
        newLists.addAll(bombArrayList);
        newLists.addAll(explosionArrayList);
        Gui.updateGUI(newLists);
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
