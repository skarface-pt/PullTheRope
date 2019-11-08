package org.academiadecodigo.splicegirls.Actions;

import org.academiadecodigo.splicegirls.Server;

public interface Action {

    /**
     * Action Interface
     * defines the reply action for the player after typing some specific commands.
     * @param server
     */

    void reply(Server server);
}
