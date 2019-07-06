package runnables;

import model.Message.Message;
import model.Message.*;

/**
 * Created by mahdihs76 on 5/21/18.
 */
public interface MessageListener {
    void receive(Message message);
}
