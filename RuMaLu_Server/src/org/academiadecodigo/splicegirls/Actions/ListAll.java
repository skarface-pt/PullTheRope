package org.academiadecodigo.splicegirls.Actions;

import org.academiadecodigo.splicegirls.Server;
import org.academiadecodigo.splicegirls.ServerWorker;

import java.util.ListIterator;

public class ListAll implements Action {

    /**
     * ListAll Action
     * specific action for the player after typing the command "/list"
     * @param server
     */

    @Override
    public void reply(Server server) {

        ListIterator<ServerWorker> it = server.getPlayersList().getListAll().listIterator();

        System.out.println("ALL PLAYERS");

        while (it.hasNext()) {
            server.sendAll(it.next().getNickName());
        }
    }
}
