package org.academiadecodigo.splicegirls.Actions;

import org.academiadecodigo.splicegirls.Server;
import org.academiadecodigo.splicegirls.ServerWorker;

import java.util.ListIterator;

public class ListTeamA implements Action {

    /**
     * ListTeamA Action
     * specific action for the player after typing the command "/listA"
     * @param server
     */

    @Override
    public void reply(Server server) {

        ListIterator<ServerWorker> iteratorA = server.getPlayersList().getTeamA().listIterator();

        System.out.println("TEAM A");

        while (iteratorA.hasNext()) {
            System.out.println(iteratorA.next().getNickName());
        }
    }
}
