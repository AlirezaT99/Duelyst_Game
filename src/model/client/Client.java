package model.client;

import model.Message.GlobalChatMessage;
import model.Message.LoginBasedCommand;
import model.Message.Message;
import model.Message.ScoreBoardCommand.ScoreBoardCommand;
import model.MyConstants;
import model.Server.Lock;

import java.io.*;
import java.net.Socket;

public class Client implements runnables.MessageListener {
    private String authCode;
    private static Client instance = new Client();
    private Socket clientSocket;
    private ObjectOutputStream outputStream;
    private ObjectInputStream inputStream;
    private LoginBasedCommand loginBasedCommand = new LoginBasedCommand("", "", true);
    private ScoreBoardCommand scoreBoardCommand = new ScoreBoardCommand("",false,false,false);


    private final  Lock loginLock = new Lock();
    private final Lock shopLock = new Lock();
    private GlobalChatMessage globalChatMessage = new GlobalChatMessage("", "");
    private final Lock lock = new Lock();

    public void start() {
        try {
            connect2Server();
            initIOStreams();
            startThreads();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private void connect2Server() throws IOException {
        System.out.println("connecting to server...");
        clientSocket = new Socket(MyConstants.SERVER_ADDRESS, MyConstants.SERVER_PORT);
        System.out.println("client connected ...");
    }

    private void initIOStreams() throws IOException {
        outputStream = new ObjectOutputStream(clientSocket.getOutputStream());
        outputStream.flush();
        inputStream = new ObjectInputStream(clientSocket.getInputStream());
    }

    private void startThreads() {
        new Thread(new runnables.GetDataRunnable(inputStream)).start();

    }

    public void sendData(Message text) {
        try {
            outputStream.writeObject(text);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void receive(Message message) {
    }

    public static Client getInstance() {
        return instance;
    }

    //getter & setter

    public LoginBasedCommand getLoginBasedCommand() {
        return loginBasedCommand;
    }

    public void setLoginBasedCommand(LoginBasedCommand loginBasedCommand) {
        this.loginBasedCommand = loginBasedCommand;
    }

    public void setScoreBoardCommand(ScoreBoardCommand scoreBoardCommand) {
        this.scoreBoardCommand = scoreBoardCommand;
    }

    public ScoreBoardCommand getScoreBoardCommand() {
        return scoreBoardCommand;
    }

    public GlobalChatMessage getGlobalChatMessage() {
        return globalChatMessage;
    }

    public void setGlobalChatMessage(GlobalChatMessage globalChatMessage) {
        this.globalChatMessage = globalChatMessage;
    }


    public String getAuthCode() {
        return authCode;
    }

    public void setAuthCode(String authCode) {
        this.authCode = authCode;
    }

    public Lock getLock() {
        return loginLock;
    }

    public Lock getShopLock() {
        return shopLock;
    }

    //getter & setter
}













