package game.Server;

import game.*;

import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ServerGameLogic {
    public static List<Player> players = new ArrayList<Player>();
    public static List<DataOutputStream> clientConnections = new ArrayList<>();
    public static List<Chest> chests = new ArrayList<>();
    public static List<Bomb> bombs = new ArrayList<>();

    public static void addConnection(DataOutputStream clientConnection) {
        clientConnections.add(clientConnection);
    }

    public static void addChest(Chest chest) {
        chests.add(chest);
    }

    public synchronized static void sendData() {
        //Bygger stringen med alt infoen
        StringBuilder outString = new StringBuilder();
        outString.append("@");
        for (Player player : players) {
            //Vi smider alt data om spilleren ind i en string. '#' splitter spillere. ',' splitter attributer.
            outString.append("#" + player);
        }
        outString.append("@");
        System.out.println(chests);
        if (chests.isEmpty()) outString.append(" ");
        else {
            for (Chest chest : chests) {
                outString.append("#" + chest);
            }
        }

        outString.append("@");
        if (bombs.isEmpty()) outString.append(" ");
        else {
            for (Bomb bomb : bombs) {
                outString.append("#" + bomb);
            }
        }
        outString.append('\n');
        System.out.println(outString.toString());
        //Sender stringen til alle clients
        for (DataOutputStream clientConnection : clientConnections) {
            try {
                clientConnection.writeBytes(outString.toString());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public static Player makePlayers(String name) {
        //Sætter spillere til at spawne i modsatte hjørner
        int playerCount = players.size() + 1;
        int x = (playerCount == 1 || playerCount == 2) ? 18 : 1;
        int y = (playerCount % 2 == 0) ? 1 : 18;
        Pair p = new Pair(x, y);
        //Opretter spilleren, og returnere den.
        Player newPlayer = new Player(name, p, "up", 0);
        players.add(newPlayer);

        sendData();
        return newPlayer;
    }

    public static boolean placeBomb (Player player) {
        Pair playerLocation = player.getLocation();
        String direction = player.getDirection();
        Pair bombLocation = null;
        System.out.println(direction);
        switch (direction.toUpperCase()) {
            case "UP":    bombLocation = new Pair(playerLocation.getX() + 0,playerLocation.getY()-1);   break;
            case "DOWN":  bombLocation = new Pair(playerLocation.getX() + 0,playerLocation.getY()+1);;  break;
            case "LEFT":  bombLocation = new Pair(playerLocation.getX() - 1,playerLocation.getY());;  break;
            case "RIGHT": bombLocation = new Pair(playerLocation.getX() + 1,playerLocation.getY());; break;
        }

        if (!isFreeSpot(bombLocation.getX(), bombLocation.getY())) {
            return false;
        }

        Bomb bomb = new Bomb(bombLocation);
        bombs.add(bomb);
        sendData();
        return true;
    }


    //Syncronized??
    public static boolean updatePlayer(Player player, int delta_x, int delta_y, String direction) {
        player.setDirection(direction);
        int x = player.getXpos(), y = player.getYpos();
        boolean returnBol = false;

        System.out.println(direction);

        // collision detection
        if (!isFreeSpot(x + delta_x, y + delta_y)) {
            sendData();
            return returnBol;
        }
        Pair newpos = new Pair(x + delta_x, y + delta_y);
        player.setLocation(newpos);
        returnBol = true;

        //chest detection
        if (chests.stream().anyMatch(chest -> chest.getLocation().equals(newpos))) {
            playerFoundChest(player, newpos);
        }

        sendData();
        return returnBol;
    }

    private static void playerFoundChest(Player player, Pair position) {
        int index = 0;
        boolean found = false;
        Chest chest = null;
        while (index < chests.size() && !found) {
            if (chests.get(index).getLocation().equals(position)) {
                chest = chests.get(index);
                found = true;
            }
            index++;
        }
        chests.remove(chest);
        player.addPoints(chest.getPoints());
    }

    private static boolean isFreeSpot(int x, int y) {
        return Generel.board[y].charAt(x) != 'w' && getPlayerAt(x, y) == null;
    }

    public static Player getPlayerAt(int x, int y) {
        for (Player p : players) {
            if (p.getXpos() == x && p.getYpos() == y) {
                return p;
            }
        }
        return null;
    }

    public static Pair getRandomFreePosition()
    // finds a random new position which is not wall
    // and not occupied by other players
    {
        int x = 1;
        int y = 1;
        boolean foundfreepos = false;
        while (!foundfreepos) {
            Random r = new Random();
            x = Math.abs(r.nextInt() % 18) + 1;
            y = Math.abs(r.nextInt() % 18) + 1;
            if (Generel.board[y].charAt(x) == ' ') // er det gulv ?
            {
                foundfreepos = true;
                for (Player p : players) {
                    if (p.getXpos() == x && p.getYpos() == y) //pladsen optaget af en anden
                        foundfreepos = false;
                }

            }
        }
        Pair p = new Pair(x, y);
        return p;
    }
}
