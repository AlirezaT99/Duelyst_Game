package model;
import java.util.*;
public class Account {
    private String userName;
    private String passWord;
    private long money;
    private Collection collection;
    private ArrayList<Account> friends;
    //getters

    public String getUserName() {
        return userName;
    }

    public String getPassWord() {
        return passWord;
    }
    //getters
}
