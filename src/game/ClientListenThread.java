package game;

import java.io.BufferedReader;

public class ClientListenThread extends Thread{
    private final BufferedReader inFromServer;

    public ClientListenThread(BufferedReader infromServer) {
        this.inFromServer = infromServer;
    }

    @Override
    public void run() {
        try {
            Thread.sleep(3000);



        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
