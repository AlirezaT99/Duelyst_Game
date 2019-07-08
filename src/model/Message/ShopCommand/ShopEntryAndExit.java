package model.Message.ShopCommand;

public class ShopEntryAndExit extends ShopCommand {
    private boolean enter;
    private boolean exit;

    public ShopEntryAndExit(String authCode, boolean enter, boolean exit) {
        super(authCode);
        this.enter = enter;
        this.exit = exit;
    }
}
