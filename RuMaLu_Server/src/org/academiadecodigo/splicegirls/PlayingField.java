package org.academiadecodigo.splicegirls;

import java.util.*;

public class PlayingField {

    /** PlayingField Class
     * abstraction of flag and rope view
     */

    /**
     * Instance Variables
     */

    private LinkedList<String> fieldRepresentation;
    private int ropeSize;
    private volatile int currentFlagIndex; //todo:FLAG POSITION - shared and muttable property

    /**
     * Constructor
     */

    public PlayingField(int ropeSize) {

        fieldRepresentation = new LinkedList<>();
        this.ropeSize = ropeSize;
        this.currentFlagIndex = ropeSize /2;
        createPlayingField();

    }

    /**
     * createPlayingField method
     * Save each position of the playingfield in a linkedList for future manipulation
     */

    public void createPlayingField() {

        int counter = 0;

        while(counter < ropeSize){
            if(counter == ropeSize /2){
                fieldRepresentation.add("P");
                counter++;
                continue;
            }
            fieldRepresentation.add("-");
            counter++;
        }
    }

    /**
     * Show the playingfield as a String
     */

    public String showField() {

        StringBuilder builder = new StringBuilder();
        ListIterator<String> it = fieldRepresentation.listIterator();

        while (it.hasNext()) {
            builder.append(it.next());
        }
        return "\n" + "               TEAM A |" + builder.toString() + "| TEAM B";
    }

    /**
     * SETTERS
     * @param playerScore
     */

    public void setCurrentFlagIndex(int playerScore) {
        currentFlagIndex = currentFlagIndex + playerScore;
    }

    /**
     * GETTERS
     * @return
     */

    public int getCurrentFlagIndex() {
        return currentFlagIndex;
    }

    public LinkedList <String> getFieldRepresentation(){
        return fieldRepresentation;
    }
}