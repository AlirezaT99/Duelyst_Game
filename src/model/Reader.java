package model;

import java.io.InputStream;
import java.util.LinkedList;
import java.util.Scanner;

public class Reader extends Thread {
    private InputStream input;

    private LinkedList<model.OnMessageReceivedListener> listeners = new LinkedList<>();

    public Reader(InputStream input) {
        this.input = input;
    }

    @Override
    public void run() {
        Scanner scanner = new Scanner(input);
        while (scanner.hasNextLine()) {
            try {
                callOnMessageReceived(scanner.nextLine());
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    public void addListener(model.OnMessageReceivedListener listener) {
        listeners.add(listener);
    }

    private void callOnMessageReceived(String message) throws ClassNotFoundException {
        for (model.OnMessageReceivedListener listener : listeners) {
            listener.onMessageReceived(message);
        }
    }




}
