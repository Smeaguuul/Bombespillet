package game.Client;

import java.io.*;
import java.net.Socket;

import javafx.application.Application;

public class Client {

	public static void main(String[] args) throws Exception {
		BufferedReader inFromUser = new BufferedReader(new InputStreamReader(System.in));
		System.out.println("Indtast spillernavn");
		String navn = inFromUser.readLine();
		Socket clientSocket= new Socket("localhost",42069);

		//Opretter forbindelsen til/fra server. Og vidergiver det til logic klassen
		DataOutputStream outToServer = new DataOutputStream(clientSocket.getOutputStream());
		ClientGameLogic.setOutToServer(outToServer);

		BufferedReader inFromServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
		ClientListenThread clientThread = new ClientListenThread(inFromServer);
		clientThread.start();

		//Sender vores navn og åbner gui'en
		outToServer.writeBytes(navn + '\n');
		String response = inFromServer.readLine();
//		System.out.println(response);'
		Application.launch(Gui.class);
	}
};