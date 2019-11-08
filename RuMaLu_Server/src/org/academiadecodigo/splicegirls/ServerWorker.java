package org.academiadecodigo.splicegirls;

import org.academiadecodigo.splicegirls.Actions.Action;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ServerWorker implements Runnable {

    private Socket playerConnection;
    private Server server;
    private String nickName;
    private Commands commands;

    private BufferedReader in;
    private PrintWriter out;

    private int playerScore;

    /**
     * ServerWorker Class Constructor
     */

    public ServerWorker(Socket playerConnection, Server server) {

        this.playerConnection = playerConnection;
        this.server = server;
        this.nickName = "User_" + Randomizer.randomizer();
        this.commands = new Commands();
    }

    /**
     * Task for Threads
     */

    @Override
    public void run() {
        try {
            setupStreams();

            while (!server.gameHasStarted()) {
            }

            setPlayerName();
            receiveMessage();

        } catch (IOException e) {
            System.err.println("IO Exception " + e.getMessage());
        }
    }

    /**
     * Setup the Streams between Player and Server
     */

    private void setupStreams() throws IOException {
        in = new BufferedReader(new InputStreamReader(playerConnection.getInputStream()));
        out = new PrintWriter(playerConnection.getOutputStream(), true);
        out.println("\n\n" + StringRepository.STARTUP_MSG);
    }

    /**
     * set the Player nickname
     */

    public void setPlayerName() {

        try {

            out.println("\n" + StringRepository.NICKNAME_MSG);
            nickName = in.readLine(); // ****BLOCKING METHOD ****
            out.println("\n" + "Hi, " + nickName + "!");

        } catch (IOException e) {
            System.err.println("IO Exception " + e.getMessage());
        }
    }

    /**
     * receive and validate the input of the Player connected
     */

    private void receiveMessage() throws IOException {

        String message = "";

        while (true) {

            message = in.readLine(); //**** BLOCKING METHOD *****

            /*if (commands.getMapCommands().containsKey(message)) {
                Action action = (Action) commands.getMapCommands().get(message);
                action.reply(server);
                continue;
            }
            */ //todo: in case of commands implementation

            System.out.println(nickName +": "+ message);
            validateAnswer(message);
        }
    }

    /**
     * validates the input of the Player
     */
    private void validateAnswer(String message) {
        if (server.gameHasStarted() && message.equals(server.getWord())) {
            out.println(StringRepository.CORRECT_MSG);

            if (server.getPlayersList().getTeamA().contains(this)) {
                server.updatePlayingField(-1);
                playerScore++;
                checkWinner();
                return;
            }
            server.updatePlayingField(1);
            playerScore++;
            checkWinner();
        }
    }

    /**
     * send message for the player
     */

    public void sendMessage(String message) {
        out.println(message);
    }

    /**
     * check which team won the game through player score counter.
     */

    public synchronized void checkWinner(){
        if(server.getPlayingField().getCurrentFlagIndex() == 0){
            server.sendAll(StringRepository.TEAM_A_WINS);
            server.setFinalFlagPosition(0);
            server.setGameHasStarted(false);
        }
        if(server.getPlayingField().getCurrentFlagIndex() == server.getPlayingField().getFieldRepresentation().size() - 1){
            server.sendAll(StringRepository.TEAM_B_WINS);
            server.setFinalFlagPosition(server.getPlayingField().getFieldRepresentation().size() - 1);
            server.setGameHasStarted(false);
        }
    }

    /**
     * GETTERS
     *
     * @return
     */

    public String getNickName() {
        return nickName;
    }

    public int getPlayerScore() {
        return playerScore;
    }

    public boolean checkDefaultUsername(){
        if (!nickName.startsWith("User_")){
            return true;
        }
        return false;
    }
}
