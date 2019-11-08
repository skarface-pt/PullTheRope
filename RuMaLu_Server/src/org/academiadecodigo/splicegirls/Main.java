package org.academiadecodigo.splicegirls;

import org.academiadecodigo.splicegirls.WordsGenerator.WordGenerator;

public class Main {

    public static void main(java.lang.String[] args) {

        //****** SETUP GAME *******
        WordGenerator gameWord = new WordGenerator("resources/insultos.txt");
        PlayersList playersList = new PlayersList();
        PlayingField playingField = new PlayingField(13);

        //***** CREATE SERVER AND START ******
        Server server = new Server(gameWord, playersList, playingField, 8);
        server.serverInit();
    }
}
