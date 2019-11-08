package org.academiadecodigo.splicegirls;

import org.academiadecodigo.splicegirls.WordsGenerator.WordGenerator;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Collections;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {

    /**
     * Server Class
     * class which listen for new connections and knows all players
     */


    /**
     * Instance Variables
     */

    private static final int DEFAULT_PORT = 9000;
    private volatile boolean gameHasStarted;

    private ServerSocket serverSocket;
    private volatile PlayersList playersList;
    private PlayingField playingField;
    private WordGenerator gameWord;
    private String word;

    private int numberOfPlayers;
    private int maxPlayers;
    private int finalFlagPosition;

    /**
     * Constructor of Server Instance
     */

    public Server(WordGenerator gameWord, PlayersList playersList, PlayingField playingField, int maxPlayers) {
        this.playersList = playersList;
        this.playingField = playingField;
        this.maxPlayers = maxPlayers;
        this.gameWord = gameWord;
    }

    /**
     * Initialize the server
     */

    public void serverInit() {

        try {
            setupConnection(DEFAULT_PORT);
            startGame();

        } catch (InterruptedException e) {
            System.err.println("InterruptedException " + e.getMessage());

        } catch (IOException e) {
            System.err.println("IO Exception " + e.getMessage());
        }
    }

    /**
     * Setup the connection with any Player
     *
     * @throws IOException
     */

    private void setupConnection(int portNumber) throws IOException {
        serverSocket = new ServerSocket(portNumber);

        while (numberOfPlayers < maxPlayers) {

            Socket playerConnection = serverSocket.accept(); //**** BLOCKING METHOD *****
            numberOfPlayers++;

            ServerWorker serverWorker = new ServerWorker(playerConnection, this);

            playersList.getListAll().add(serverWorker);

            ExecutorService cachedThreadPool = Executors.newCachedThreadPool();
            cachedThreadPool.submit(serverWorker);
        }
    }

    /**
     * Starts the game in all players
     * @throws InterruptedException
     */
    public void startGame() throws InterruptedException, IOException {

        synchronized (this) {

            try {

                //getNickNames();
                gameHasStarted = true;

                while(!checkAllNickNames()){
                    continue;
                }

                playersList.createTeams();

                sendAll("\n\n" + StringRepository.INSTRUCTIONS_MSG);
                Thread.sleep(8000);

                sendAll("\n" + StringRepository.TEAMS_MSG + "\n" + playersList.showTeams());
                Thread.sleep(4000);

                sendAll(StringRepository.BEGIN_MSG + "\n\n" + playingField.showField());
                Thread.sleep(2000);

            } catch (InterruptedException e) {
                System.err.println("Interrupted Exception " + e.getMessage());
            }
        }

        while (gameHasStarted) {

            try {

                word = gameWord.getOneWord();
                sendAll("\n" + StringRepository.NEWWORD_MSG + word + "\n");
                Thread.sleep(5000);

                if (!gameHasStarted) {
                    break;
                }

                sendAll("\n" + playingField.showField());

            } catch (InterruptedException e) {
                System.err.println("InterruptedException" + e.getMessage());
            }
        }

        sendAll(showFinalPlayingField(finalFlagPosition));
        Thread.sleep(3000);
        sendAll("\n" + StringRepository.FINAL_SCORES_MSG + "\n" + playersList.showTeamScores());
        sendAll("\n" + StringRepository.FINAL_MSG);

        serverSocket.close();
        System.exit(1);
    }

    /**
     * send message for all players
     * @param message
     */

    public void sendAll(String message) {
        for (int i = 0; i < playersList.getListAll().size(); i++) {
            playersList.getListAll().get(i).sendMessage(message);
        }
    }

    /**
     * updates the rope and flag state before each round
     * @param roundScore
     */
    public synchronized void updatePlayingField(int roundScore) {
        Collections.swap(playingField.getFieldRepresentation(), playingField.getCurrentFlagIndex(), playingField.getCurrentFlagIndex() + roundScore);
        playingField.setCurrentFlagIndex(roundScore);
    }

    /**
     * show the final flag and rope result
     * @param finalFlagPosition
     * @return
     */

    public synchronized String showFinalPlayingField(int finalFlagPosition) {
        Collections.swap(playingField.getFieldRepresentation(), playingField.getCurrentFlagIndex(), finalFlagPosition);
        playingField.setCurrentFlagIndex(finalFlagPosition);
        return playingField.showField();
    }

    /**
     * get all players names
     */

    public void getNickNames() {
        for (int i = 0; i < playersList.getListAll().size(); i++) {
            playersList.getListAll().get(i).setPlayerName();
        }
    }

    /**
     * GETTERS
     */

    public boolean gameHasStarted() {
        return gameHasStarted;
    }

    public PlayersList getPlayersList() {
        return playersList;
    }

    public String getWord() {
        return word;
    }

    public PlayingField getPlayingField() {
        return playingField;
    }

    /**
     * SETTERS
     * @param state
     */

    public void setGameHasStarted(boolean state) {
        gameHasStarted = state;
    }

    public void setFinalFlagPosition(int finalFlagPosition) {
        this.finalFlagPosition = finalFlagPosition;
    }

    public boolean checkAllNickNames() {

        int counter = 0;

        for (int i = 0; i < playersList.getListAll().size(); i++) {
            boolean newNickName = playersList.getListAll().get(i).checkDefaultUsername();

            if (newNickName){
               counter++;
            }
        }

        if (counter == numberOfPlayers){
            return true;
        }
        return false;
    }

}
