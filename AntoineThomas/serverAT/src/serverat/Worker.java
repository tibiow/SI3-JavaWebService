/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package serverat;

import data.BaseDeDonnee;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import static java.lang.System.in;
import static java.lang.System.out;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 *
 * @author chronos
 */
public class Worker implements Runnable {

    private final BaseDeDonnee bdd;
    private final JSONParser parser;
    private final Socket socks;
    private final ManageConnect parent;
    private final int id;
    private BufferedReader in;
    private PrintWriter out;

    public Worker(Socket s, BaseDeDonnee bdd, ManageConnect mg) {
        socks = s;
        this.bdd = bdd;
        this.parent = mg;
        id = mg.getNbclient()+1000;
        parser = new JSONParser();
    }


    @Override
    public void run() {
        try {
            while (true) {                
                in = new BufferedReader(new InputStreamReader(socks.getInputStream()));
                String recu = in.readLine();
                if (recu == null || recu.equalsIgnoreCase("quit")) {
                    parent.OneClientTakeTheDoor();
                    break;
                }
                
                JSONObject a = new JSONObject();
                a.put("request", "list");
                JSONObject reponse = managecall(recu);
                System.out.println("Le client numero " + id + " a recu : " + recu);
                System.out.println("Le client numero " + id + " repond : " + reponse.toJSONString());

                out = new PrintWriter(socks.getOutputStream());
                out.println(reponse.toJSONString());
                out.flush();                
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private JSONObject managecall(String in) {
        try {

            JSONObject jsonObject = (JSONObject) parser.parse(in);
            if (!jsonObject.containsKey("request")) {
                return bdd.JSONStatus(new JSONObject(), false);

            } else {
                String action = (String) jsonObject.get("request");
                if (action.equalsIgnoreCase("list")) {
                    return bdd.JSONStatus(bdd.getJSONList(), true);

                } else if (action.equalsIgnoreCase("participe")) {
                    JSONObject p = (JSONObject) jsonObject.get("idee");
                    return bdd.participe(p);

                } else if (action.equalsIgnoreCase("add")) {
                    JSONObject p = (JSONObject) jsonObject.get("idee");
                    return bdd.addIdee(p);
                } else {
                    return bdd.JSONStatus(new JSONObject(), false);
                }
            }
        } catch (ParseException ex) {
            Logger.getLogger(ServerAT.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            return bdd.JSONStatus(new JSONObject(), false);
        }
        return bdd.JSONStatus(new JSONObject(), true);
    }

}
