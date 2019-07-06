package runnables;

import model.Message.Message;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.Scanner;

/**
 * Created by mahdihs76 on 5/21/18.
 */
public class SendDataRunnable implements Runnable {

    private ObjectOutputStream outputStream;
    private String from;
    private String to;

    public SendDataRunnable(ObjectOutputStream outputStream, String from, String to) {
        this.outputStream = outputStream;
        this.from = from;
        this.to = to;
    }

    @Override
    public void run() {
        Scanner scanner = new Scanner(System.in);
        while (true){
            String text = scanner.nextLine();
            Message message = new Message(text);
            try {
                outputStream.writeObject(message);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
