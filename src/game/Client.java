package game;

import java.io.*;
import java.net.Socket;

import javafx.application.Application;

public class Client {
	public static void main(String[] args) throws Exception{
		BufferedReader inFromUser = new BufferedReader(new InputStreamReader(System.in));
		System.out.println("Indtast spillernavn");
		String navn = inFromUser.readLine();
		GameLogic.makePlayers(navn);
		Socket clientSocket= new Socket("localhost",42069);
		DataOutputStream outToServer = new DataOutputStream(clientSocket.getOutputStream());
		BufferedReader inFromServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
		outToServer.writeBytes(navn + '\n');
		String response = inFromServer.readLine();
		System.out.println(response);
		clientSocket.close();
		Application.launch(Gui.class);
	}
};