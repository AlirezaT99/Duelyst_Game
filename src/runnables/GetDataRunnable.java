package runnables;

import model.Message.LoginBasedCommand;
import model.Message.Message;
import model.Message.ShopCommand.UpdateShop.UpdateWholeShop;
import model.Shop;
import model.client.Client;
import view.Login;
import view.ShopMenu;
import view.ShopMenuFX;

import java.io.InputStream;
import java.io.ObjectInputStream;

/**
 * Created by mahdihs76 on 5/21/18.
 */
public class GetDataRunnable implements Runnable {

    private ObjectInputStream inputStream;

    public GetDataRunnable(ObjectInputStream inputStream) {
        this.inputStream = inputStream;
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
                if(message instanceof UpdateWholeShop)
                    updateShop((UpdateWholeShop) message);

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void updateShop(UpdateWholeShop message) {
        synchronized (Client.getInstance().getShopLock()){
            ShopMenuFX.setHeroes(message.getHeroes());
            ShopMenuFX.setMinions(message.getMinions());
            ShopMenuFX.setSpells(message.getSpells());
            ShopMenuFX.setItems(message.getItems());
            ShopMenuFX.setCollectionHeroes(message.getCollectionHeroes());
            ShopMenuFX.setCollectionMinions(message.getCollectionMinions());
            ShopMenuFX.setCollectionSpells(message.getCollectionSpells());
            ShopMenuFX.setCollectionItems(message.getCollectionItems());
            ShopMenuFX.setMovableCardsPowers(message.getPowers());
            ShopMenuFX.setCosts(message.getCosts());
            ShopMenuFX.setCardNumbers(message.getNumbers());
            ShopMenuFX.setCostumeCards(message.getCostumes());
            Client.getInstance().getShopLock().notify();
        }
    }
}
