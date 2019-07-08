package runnables;

import model.Message.GlobalChatMessage;
import model.Message.LoginBasedCommand;
import model.Message.Message;
import model.Message.SaveCommand.SaveCommand;
import model.Message.ScoreBoardCommand.ScoreBoardCommand;
import model.Message.ShopCommand.Trade.TradeResponse;
import model.Message.ShopCommand.UpdateShop.UpdateCards;
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
                Message message = (Message) inputStream.readObject();
                if (message instanceof LoginBasedCommand) {
                    synchronized (Client.getInstance().getLock()) {
                        Client.getInstance().setLoginBasedCommand((LoginBasedCommand) message);
                        Client.getInstance().getLock().notifyAll();
                    }
                }

//                if(message instanceof UpdateCards){
//                    synchronized (Client.getInstance().getShopLock()){
//                        UpdateCards updateCards = (UpdateCards) message;
//                       String name =  updateCards.getObjectName();
//                       int counter = updateCards.getCount();
//                       if(ShopMenuFX.getCardNumbers().containsKey(name))
//                           ShopMenuFX.getCardNumbers().replace(name,counter);
//                       else {
//                           ShopMenuFX.getCardNumbers().put(name, counter);
//                           ShopMenuFX.getCosts().put(name, updateCards.getCost());
//                           if(updateCards.getCardType() == 0){
//                               ShopMenuFX.getHeroes().add(name);
//                           }if(updateCards.getCardType() == 1)
//                               ShopMenuFX.getMinions().add(name);
//                           if(updateCards.getCardType() == 2)
//                               ShopMenuFX.getSpells().add(name);
//                           if(updateCards.getCardType() == 3)
//                               ShopMenuFX.getItems().add(name);
//                           if(updateCards.getCardType()==0 || updateCards.getCardType() == 1)
//                               Shop.getMovableCardsPowers().put(name,updateCards.getPowers());
//                       }
//                    }
//                }

                if (message instanceof ScoreBoardCommand) {
                    synchronized (Client.getInstance().getLock()) {
                        Client.getInstance().setScoreBoardCommand((ScoreBoardCommand) message);
                        Client.getInstance().getLock().notifyAll();
                    }
                }

                if (message instanceof GlobalChatMessage) {
                    synchronized (Client.getInstance().getLock()) {
                        Client.getInstance().setGlobalChatMessage((GlobalChatMessage) message);
                        Client.getInstance().getLock().notifyAll();
                    }
                }

                if (message instanceof UpdateWholeShop)
                    updateShop((UpdateWholeShop) message);
                if (message instanceof TradeResponse)
                    tradeResponseHandler((TradeResponse) message);


                if(message instanceof SaveCommand){
                    synchronized (Client.getInstance().getLock()) {
                        Client.getInstance().setSaveCommand((SaveCommand) message);
                        Client.getInstance().getLock().notify();
                    }
                }



            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void tradeResponseHandler(TradeResponse message) {
        synchronized (Client.getInstance().getShopLock()) {
            ShopMenuFX.setResponse(message);
            Client.getInstance().getShopLock().notify();
        }
    }

    private void updateShop(UpdateWholeShop message) {
        synchronized (Client.getInstance().getShopLock()) {
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
            ShopMenuFX.setAcccountMoney(message.getMoney());
            ShopMenuFX.setCardCollectionNumbers(message.getCollectionNumbers());
            Client.getInstance().getShopLock().notify();
        }
    }
}
