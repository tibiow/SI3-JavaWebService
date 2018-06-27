package serveur;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.*;
import java.util.Scanner;
import java.io.*;

/**
 * Auteur : Axel Aiello
 * Class pour parser les commandes
 */
public class Parseur implements Runnable {

    private Socket socket;
    private PrintWriter out = null;
    private BufferedReader in = null;
    private String order = "";
    private int number;
    public Thread thread1;

    public Parseur(Socket s, int number){
        socket = s;
        this.number = number;
    }

    public void run() {
        try {
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream());

            while(true){
                order = in.readLine();
                Validation(order, out);
            }

        } catch (IOException e) {
            System.err.println("L'utilisateur n°" + number + " est déconnecté.");
        }
    }

    private void Validation(String order, PrintWriter out) {
        // On cherche quel est le type de la commande demandé
        try {
            JSONObject json = new JSONObject(order);
            String request;
            request = json.getString("request");

            /**
             * On rajout une idée
             * Exemple de demande : {"request":"ADD","idee":{}}
             * Exemples de retour : {"status":"OK"} ou {"status":"BAD REQUEST"}
             */
            if (request.equals("ADD")) {
                thread1 = new Thread(new CommandeAdd(json, out));
                thread1.start();
                return;
            }



            // On participe
            else if (request.equals("PARTICIPE")) {}



            // On veut une liste
            else if (request.equals("LIST")) {
                // Des participant d'un idée
                try {
                    String idee;
                    idee = json.getString("idee");
                }
                /**
                 * On liste toutes les idées
                 * Exemple de demande : {"request":"LIST"}
                 * Exemples de retour : {"idee":["server java","server java2","server java3"],"status":"OK"} ou {"status":"BAD REQUEST"}
                 */
                catch (JSONException e) {
                    thread1 = new Thread(new CommandeList(json, out));
                    thread1.start();
                    return;
                }

            }
            // Erreur mauvaise commande
            else {
                printerBadRequest();
                return;
            }
        }
        // Pas compris
        catch (JSONException e) {
            printerBadRequest();
            return;
        }
        // Envoie vide
        catch (NullPointerException e) {
            printerBadRequest();
            return;
        }
        printerBadRequest();
        return;
    }

    private void printerBadRequest() {
        out.println("{\"status\":\"BAD REQUEST\"}");
        out.flush();
    }


}
