package org.academiadecodigo.splicegirls;

import org.academiadecodigo.splicegirls.Actions.*;

import java.util.HashMap;

public class Commands {

    /**
     * Commands Class
     * map with key/value pairs: key - specific commands / values - constructor of the specific Action class
     * @param server
     */

    private HashMap <String, Action> mapCommands;

    /**
     * Constructor
     */

    public Commands(){

        mapCommands = new HashMap<>();

        mapCommands.put("/start", new Start());
        mapCommands.put("/quit", new Quit());
        mapCommands.put("/chatAll", new SendToAll());
        mapCommands.put("/chat", new SendToTeam());
        mapCommands.put("/listAll", new ListAll());
        mapCommands.put("/listA", new ListTeamA());
        mapCommands.put("/listB", new ListTeamB());

    }

    /**
     * GETTER
     */

    public HashMap getMapCommands(){
        return mapCommands;
    }

}
