package serveur;

import java.io.*;
import java.net.*;

/**
 * Auteur : Axel Aiello
 * Class pour accepter la connexion
 */
public class Connexion implements Runnable{

    private ServerSocket socketserver = null;
    private Socket socket = null;
    private int number;
    public Thread thread1;

    public Connexion(ServerSocket ss){
        socketserver = ss;
        number = -1;
    }

    public void run() {
        try {
            while(true){
                number++;
                socket = socketserver.accept();
                System.out.println("L'utilisateur n°" + number + " est connecté.");
                thread1 = new Thread(new Parseur(socket, number));
                thread1.start();
            }
        } catch (IOException e) {
            System.err.println("Erreur de connexion");
        }
    }

}

