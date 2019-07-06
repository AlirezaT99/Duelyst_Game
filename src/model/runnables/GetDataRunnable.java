package runnables;

import model.Message.Message;

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
        while (true) {
            try {
                Message message = (Message) inputStream.readObject();
                listener.receive(message);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
