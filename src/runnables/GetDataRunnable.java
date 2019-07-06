package runnables;

import model.Message.LoginBasedCommand;
import model.Message.Message;
import model.client.Client;
import view.Login;

import java.io.InputStream;
import java.io.ObjectInputStream;

/**
 * Created by mahdihs76 on 5/21/18.
 */
public class GetDataRunnable implements Runnable {

    private ObjectInputStream inputStream;
    private runnables.MessageListener listener;

    public GetDataRunnable(ObjectInputStream inputStream, runnables.MessageListener listener) {
        this.inputStream = inputStream;
        this.listener = listener;
    }

    @Override
    public void run() {

        try {

            while (true) {
                if (inputStream == null) {
                    System.out.println("duck");
                    continue;
                }
                Message message = (Message) inputStream.readObject();
                if (message instanceof LoginBasedCommand) {
                    synchronized (Client.getInstance().getLock()) {
                        Client.getInstance().setLoginBasedCommand((LoginBasedCommand) message);
                        Client.getInstance().getLock().notifyAll();
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
