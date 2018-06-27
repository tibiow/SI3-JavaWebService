package serveur;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.*;
import java.util.Scanner;
import java.io.*;

/**
 * Auteur : Axel Aiello
 * Class pour la commande LIST
 */
public class CommandeList implements Runnable {

    private JSONObject json;
    private PrintWriter out = null;

    public CommandeList(JSONObject json, PrintWriter out){
        this.json = json;
        this.out = out;
    }

    public void run() {
        try {
            int i = 0;
            String message = "";
            Scanner sc = new Scanner(new File("serverAT/ressources/idea.txt"));
            while (sc.hasNext()) {
                String msg = sc.nextLine();
                msg = msg.substring(0, msg.length() - 1);
                msg = msg + ",\"identifiant\":" + i;
                message = message + "{\"idee\":" + msg + "}},";
                i++;
            }
            if (!message.equals("{\"list\":[")) {message = message.substring(0, message.length() - 1);}
            printerMessaget(message);
        } catch (FileNotFoundException f) {
            printerBadRequest();
        }
    }

    private void printerBadRequest() {
        out.println("{\"status\":\"BAD REQUEST\"}");
        out.flush();
    }

    private void printerMessaget(String message) {
        out.println("{\"list\":[" + message + "],\"status\":\"OK\"}");
        out.flush();
    }

}
