package model;

import java.io.InputStream;
import java.util.LinkedList;
import java.util.Scanner;

public class Reader extends Thread {
    private InputStream input;

    private LinkedList<sample.OnMessageReceivedListener> listeners = new LinkedList<>();

    public Reader(InputStream input) {
        this.input = input;
    }

    @Override
    public void run() {
        Scanner scanner = new Scanner(input);
        while (scanner.hasNextLine()) {
            callOnMessageReceived(scanner.nextLine());
        }
    }

    public void addListener(sample.OnMessageReceivedListener listener) {
        listeners.add(listener);
    }

    private void callOnMessageReceived(String message) {
        for (sample.OnMessageReceivedListener listener : listeners) {
            listener.onMessageReceived(message);
        }
    }




}
