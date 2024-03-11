import javax.swing.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;


public class ReseauLocal {


    public static void main(String[] args) {
        startClient();
    }

    public static void startServer(Main main) {
        try (ServerSocket serverSocket = new ServerSocket(5000)) {
            System.out.println("Serveur démarré, en attente de la connexion du client...");
            try (Socket clientSocket = serverSocket.accept();
                 PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
                 BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()))) {
                System.out.println("Client connecté");

                out.println("EtatInitial, vous venez de demarrer une nouvelle partie:");


                String inputLine;
                while ((inputLine = in.readLine()) != null) {
                    System.out.println("Message reçu du client: " + inputLine);
                    processMessage(main, out, main.getChamp());
                }
                out.println("Fin de la partie:");
            }
        } catch (IOException e) {
            System.out.println("Exception dans le serveur: " + e.getMessage());
            e.printStackTrace();
        }
    }


    public static void startClient() {
        try (Socket socket = new Socket("localhost", 5000);
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in))) {
            System.out.println("Connecté au serveur. Prêt à envoyer des commandes.");


            new Thread(() -> {
                try {
                    String fromServer;
                    while ((fromServer = in.readLine()) != null) {
                        System.out.println("Message du Serveur: " + fromServer);

                    }
                } catch (IOException e) {
                    System.out.println("Connexion perdue avec le serveur");
                }
            }).start();


            String fromUser;
            while ((fromUser = stdIn.readLine()) != null) {
                out.println(fromUser);
            }
        } catch (IOException e) {
            System.out.println("Exception dans le client: " + e.getMessage());
            e.printStackTrace();
        }
    }


    private static void processMessage(Main main, PrintWriter out, Champ champ) {
        System.out.println("Message envoyé par Client:...");

        out.println(" voici les informations de la partie:" +
                main.getDifficulty() + ":"
                + main.getScore() + ":"
                + main.getElapsedTime()
        );

        if (champ.hasWon()) {
            out.println("vous avez gagné");
        } else {
            out.println("vous avez gagné");
        }
        out.println("fin de la partie...");

    }
}