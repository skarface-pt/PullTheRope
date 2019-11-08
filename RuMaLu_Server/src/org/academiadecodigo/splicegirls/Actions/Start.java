package org.academiadecodigo.splicegirls.Actions;

import org.academiadecodigo.splicegirls.Server;

public class Start implements Action {

    /**
     * Start Action
     * specific action for the player after typing the command "/start"
     * @param server
     */

    @Override
    public void reply(Server server) {
        server.setGameHasStarted(true);
    }
}
