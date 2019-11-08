package org.academiadecodigo.splicegirls.Actions;

import org.academiadecodigo.splicegirls.Server;
import org.academiadecodigo.splicegirls.ServerWorker;

import java.util.ListIterator;

public class ListTeamB implements Action {

    /**
     * ListTeamB Action
     * specific action for the player after typing the command "/listB"
     * @param server
     */

    @Override
    public void reply(Server server) {

        ListIterator<ServerWorker> iteratorB = server.getPlayersList().getTeamB().listIterator();

        System.out.println("TEAM B");

        while (iteratorB.hasNext()) {
            System.out.println(iteratorB.next().getNickName());
        }
    }
}
