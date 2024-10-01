package game.Server;
import game.Server.ServerGameLogic;
import game.Player;

import java.net.*;
import java.io.*;

import static game.Server.ServerGameLogic.updatePlayer;

public class ServerThread extends Thread{
	Socket connSocket;
	private Player client;

	public ServerThread(Socket connSocket) {
		this.connSocket = connSocket;
	}
	public void run() {
		try {
			//Opretter forbindelserne
			BufferedReader inFromClient = new BufferedReader(new InputStreamReader(connSocket.getInputStream()));
			DataOutputStream outToClient = new DataOutputStream(connSocket.getOutputStream());
			ServerGameLogic.addConnection(outToClient);

			//Opretter spilleren
			String clientNavn = inFromClient.readLine();
			client = ServerGameLogic.makePlayers(clientNavn);
			System.out.println(clientNavn);

			while (true) {
				String clientMessage = inFromClient.readLine();
				receiveMove(clientMessage);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void receiveMove(String clientMessage) {
		switch (clientMessage) {
			case "UP":    updatePlayer(client, 0,-1,"up");   break;
			case "DOWN":  updatePlayer(client,0,+1,"down");  break;
			case "LEFT":  updatePlayer(client,-1,0,"left");  break;
			case "RIGHT": updatePlayer(client,+1,0,"right"); break;
			default: break;
		}
	}


}
