import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Client {


    /**
     * Client Class
     * to Run in each client
     */

    private BufferedReader in;
    private PrintWriter out;
    private Socket clientSocket;

    /**
     * Constructor
     * @param serverIP
     * @param Port
     */

    public Client(String serverIP, int Port){
        start(serverIP, Port);
    }

    /**
     * Initializes the client program
     * @param serverIP
     * @param port
     */

    private void start(String serverIP, int port) {
        try {

            setupConnection(serverIP, port);
            ExecutorService fixedPool = Executors.newFixedThreadPool(1);
            fixedPool.submit(new ClientSender());
            receiveServerMessages();

        } catch (IOException e) {
            System.err.println("IO Exception" + e.getMessage());
        }
    }

    /**
     * 
     * @param serverIP
     * @param port
     */
    private void setupConnection(String serverIP, int port){
        try {
            clientSocket = new Socket(serverIP, port);
            openStreams(clientSocket);

        } catch (UnknownHostException e) {
            System.err.println("Unknown Host: " + e.getMessage());
            System.exit(2);

        } catch (IOException e) {
            System.err.println("IO Exception " + e.getMessage());
            System.exit(1);
        }
    }

    /**
     * Open the streams between client and server
     *
     * @param clientSocket
     * @throws IOException
     */
    private void openStreams(Socket clientSocket) throws IOException {

        in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        out = new PrintWriter(clientSocket.getOutputStream(), true);
    }

    /**
     * to receive messages from server
     * @throws IOException
     */

    private void receiveServerMessages() throws IOException {
        while(clientSocket.isConnected()){
            String serverMessages = in.readLine(); //**** BLOCKING METHOD *****

            if(serverMessages == null || serverMessages.equals("/quit")){
                clientSocket.close();
                System.exit(1);
                break;
            }
            System.out.println(serverMessages);
        }
    }

    /**
     * CLIENT INNER CLASS: SEND MESSAGES
     */

    private class ClientSender implements Runnable {
        @Override
        public void run() {
            BufferedReader bReader = new BufferedReader(new InputStreamReader(System.in));
            try {
                while (clientSocket.isConnected()) {
                    String messageToSend = bReader.readLine(); // **** BLOCKING METHOD *****
                    out.println(messageToSend);
                }
            } catch (IOException e) {
                System.err.println("IO Exception " + e.getMessage());
                System.exit(1);
            }
        }
    }
}
