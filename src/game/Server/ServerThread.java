package game.Server;
import game.Server.ServerGameLogic;
import game.Player;

import java.net.*;
import java.io.*;

import static game.Server.ServerGameLogic.updatePlayer;

public class ServerThread extends Thread{
	Socket connSocket;

	public ServerThread(Socket connSocket) {
		this.connSocket = connSocket;
	}
	public void run() {
		try {
			BufferedReader inFromClient = new BufferedReader(new InputStreamReader(connSocket.getInputStream()));
			DataOutputStream outToClient = new DataOutputStream(connSocket.getOutputStream());

			// Do the work and the communication with the client here
			// The following two lines are only an example
			String clientNavn = inFromClient.readLine();
			Player client = ServerGameLogic.makePlayers(clientNavn);
			outToClient.writeBytes("Velkommen" +  '\n' );
			System.out.println(clientNavn);

			while (true) {
				String clientMessage = inFromClient.readLine();
				switch (clientMessage) {
					case "UP":    updatePlayer(client, 0,-1,"up");   break;
					case "DOWN":  updatePlayer(client,0,+1,"down");  break;
					case "LEFT":  updatePlayer(client,-1,0,"left");  break;
					case "RIGHT": updatePlayer(client,+1,0,"right"); break;
					default: break;
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		// do the work here
	}


}
