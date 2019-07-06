package model.Server;

import com.google.gson.Gson;
import model.Account;
import model.Message.LoginBasedCommand;
import model.Message.Message;
import model.Reader;
import presenter.LoginMenuProcess;
import sun.security.krb5.internal.TGSRep;

import javax.print.DocFlavor;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.HashMap;

public class ClientManager extends Thread {

    public ClientManager(Server server, Socket socketOnServerSide) {
        this.socketOnServerSide = socketOnServerSide;
        this.server = server;
    }

    private static HashMap<String, ClientManager> clientManagers = new HashMap<>();
    private Server server;
    private Socket socketOnServerSide;

    //    //command manager
    @Override
    public void run() {
        try {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(socketOnServerSide.getOutputStream());
            objectOutputStream.flush();
            ObjectInputStream objectInputStream = new ObjectInputStream(socketOnServerSide.getInputStream());
            while (!interrupted()) {
                Message message = (Message) objectInputStream.readObject();
                if (message instanceof LoginBasedCommand) {
                    System.out.println("-------------");
                    System.out.println(message.getUserName());
                    System.out.println("--------------");
                    handleLoginBasedCommands(objectOutputStream, (LoginBasedCommand) message);
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }


    //login based

    private void handleLoginBasedCommands(ObjectOutputStream objectOutputStream, LoginBasedCommand message) throws IOException {
        LoginBasedCommand loginBasedCommand = message;
        String userName = loginBasedCommand.getUserName();
        String password = loginBasedCommand.getPassword();
        if (loginBasedCommand.isCreateAccount())
            createAccountServerSide(objectOutputStream, userName,password);
        else {
            loginOnServerSide(objectOutputStream, userName, password);
        }
    }

    private void loginOnServerSide(ObjectOutputStream objectOutputStream, String userName, String password) throws IOException {
        int loginErrorNumber = server.getLoginErrorNumber(userName, password);
        if (server.isLoginValid(userName, password)) {
            objectOutputStream.writeObject(new LoginBasedCommand(userName, password, true, server.generateAuthCode(userName, this), true, loginErrorNumber, Account.getAccountByUserName(userName)));
        } else {
            objectOutputStream.writeObject(new LoginBasedCommand("", "", false, "", true, loginErrorNumber, Account.getAccountByUserName(userName)));
        }
    }

    private void createAccountServerSide(ObjectOutputStream objectOutputStream, String userName, String password ) throws IOException {
        if (server.isAccountAvailabale(userName))
            objectOutputStream.writeObject(new LoginBasedCommand("", "", false, "", false, 0, null));
        else {
            LoginMenuProcess.createAccount(userName,password);
            objectOutputStream.writeObject(new LoginBasedCommand(userName, password, true, "", false, 0, Account.getAccountByUserName(userName)));
        }
    }

    //login based

}

