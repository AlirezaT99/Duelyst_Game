package model.Message.SaveCommand;

import model.Deck;
import model.Message.Message;

import java.util.ArrayList;

public class SaveCommand extends Message {
    private boolean isDeckSave = false;
    private boolean success = false;
    String decks = new String();
    String selectedDeck = new String();
    public SaveCommand(boolean isDeckSave, String decks, String selectedDeck, String authCode){ //request
        super(authCode);
        this.isDeckSave = isDeckSave;
        this.decks = decks;
        this.selectedDeck = selectedDeck;
    }

    public SaveCommand(boolean success,String authCode){ //answer
        super(authCode);
        this.success = success;
    }

    public boolean isDeckSave() {
        return isDeckSave;
    }

    public boolean isSuccess() {
        return success;
    }

    public String getDecks() {
        return decks;
    }

    public String getSelectedDeck() {
        return selectedDeck;
    }
}
