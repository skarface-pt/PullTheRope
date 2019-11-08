package org.academiadecodigo.splicegirls;

import java.util.Collections;
import java.util.LinkedList;
import java.util.ListIterator;

public class PlayersList {

    /**
     * PlayerList Class
     * contains all ServerWorker instance references, which are saved in different lists.
     */

    /**
     * Instance Variables
     */

    private LinkedList<ServerWorker> listAll;
    private LinkedList<ServerWorker> teamA;
    private LinkedList<ServerWorker> teamB;

    /**
     * Constructor of PlayersList Instance
     */

    public PlayersList() {
        this.listAll = new LinkedList<>();
    }


    /**
     * Separates the players in different teams
     */

    public void createTeams() {

        Collections.shuffle(listAll);

        //cloneList created to don't loose the original List of players
        LinkedList <ServerWorker> cloneList = (LinkedList<ServerWorker>) listAll.clone();

        ListIterator <ServerWorker> it = cloneList.listIterator();

        teamA = new LinkedList<>();
        teamB = new LinkedList<>();

        while (it.hasNext()) {
            teamA.add(cloneList.pop());
            if (!it.hasNext()){
                return;
            }
            teamB.add(cloneList.poll());
        }
    }

    /**
     * Show team players
     * @return
     */

    public String showTeams(){

        StringBuilder stringBuilder = new StringBuilder("\n");

        stringBuilder.append("TEAM A" + "\n");

        ListIterator<ServerWorker> iteratorA = teamA.listIterator();

        while (iteratorA.hasNext()) {
            stringBuilder.append(iteratorA.next().getNickName());
            stringBuilder.append("\n");
        }

        stringBuilder.append("\n");
        stringBuilder.append("TEAM B" + "\n");

        ListIterator<ServerWorker> iteratorB = teamB.listIterator();

        while (iteratorB.hasNext()) {
            stringBuilder.append(iteratorB.next().getNickName());
            stringBuilder.append("\n");
        }
        return stringBuilder.toString();
    }

    /**
     * Show team players scores
     * @return
     */

    public String showTeamScores() {

        StringBuilder stringBuilder = new StringBuilder("\n");

        stringBuilder.append("TEAM A" + "\n");

        ListIterator <ServerWorker> iteratorA = teamA.listIterator();

        while (iteratorA.hasNext()) {
            ServerWorker currentServerworker = iteratorA.next();
            stringBuilder.append(currentServerworker.getNickName() + ": ");
            stringBuilder.append(currentServerworker.getPlayerScore() + " words");
            stringBuilder.append("\n");
        }

        stringBuilder.append("\n");
        stringBuilder.append("TEAM B" + "\n");

        ListIterator<ServerWorker> iteratorB = teamB.listIterator();

        while (iteratorB.hasNext()) {
            ServerWorker currentServerworker = iteratorB.next();
            stringBuilder.append(currentServerworker.getNickName() + ": ");
            stringBuilder.append(currentServerworker.getPlayerScore() + " words");
            stringBuilder.append("\n");
        }
        return stringBuilder.toString();
    }

    /**
     * GETTERS
     * @return
     */

    public LinkedList<ServerWorker> getListAll(){
        return listAll;
    }

    public LinkedList<ServerWorker>  getTeamA(){
        return teamA;
    }

    public LinkedList<ServerWorker>  getTeamB(){
        return teamB;
    }
}
