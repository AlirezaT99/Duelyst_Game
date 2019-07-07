package model;

public interface OnMessageReceivedListener
{
    void onMessageReceived(String message) throws ClassNotFoundException;
}
