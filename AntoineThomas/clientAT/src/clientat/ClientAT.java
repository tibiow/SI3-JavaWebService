/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clientat;

import java.io.*;
import java.net.*;
import java.util.Scanner;

import org.json.simple.JSONObject;
import requetesmodele.Idee;

/**
 * @author chronos
 */
public class ClientAT {

    private static JSONObject commande;

    public static void main(String[] zero) {
        ClientAT client = new ClientAT();
        Socket socks;
        BufferedReader in = null;
        PrintWriter out;

        try {
            socks = new Socket("10.212.121.147", 9999);
            System.out.println("Connecté");
            out = new PrintWriter(socks.getOutputStream());

            while (client.getSaisie()) {
                if(commande != null) {
                    out.println(client.getCommande().toString());
                    out.flush();
                    confirm(in, socks);
                }
            }
            out.println("quit");
            out.flush();
            socks.close();

        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void confirm(BufferedReader in, Socket socks) throws IOException {
        in = new BufferedReader(new InputStreamReader(socks.getInputStream()));
        String message = in.readLine();
        System.out.println(message);

    }

    private void printhelp() {
        System.out.println();
        System.out.println("Commandes disponibles : ");
        System.out.println("\t- add : ajouter une idée");
        System.out.println("\t- participe : participer à une idée");
        System.out.println("\t- list : affiche la liste des idées");
        System.out.println("\t- quit : quitter le serveur");
        System.out.println();
    }

    private void printBadResquest() {
        System.out.println("Parseur non disponible");
        System.out.println();
    }

    private boolean getSaisie() {
        printhelp();
        Scanner scan = new Scanner(System.in);

        System.out.println("Entrez votre commande : ");
        switch (scan.nextLine()) {
            case "add":
                commande = creerIdee();
                break;
            case "participe":
                commande = creerParticipation();
                break;
            case "list":
                commande = liste();
                break;
            case "quit":
                return false;
            default :
                printBadResquest();
                commande = null;
                break;
        }
        return true;
    }

    private JSONObject creerIdee() {
        Idee idee = new Idee();
        JSONObject result = new JSONObject();
        result.put("idee",idee.toJSON());
        result.put("request","ADD");
        return result;
    }

    private JSONObject creerParticipation() {
        Scanner scan = new Scanner(System.in);
        System.out.println("----| Participation |----");
        System.out.println("Saisissez l'identifiant de l'idée : ");
        String id = scan.nextLine();
        System.out.println("Saisissez votre adresse email : ");
        String mail = scan.nextLine();

        JSONObject result = new JSONObject();
        JSONObject idee = new JSONObject();
        idee.put("identifiant", Integer.valueOf(id));
        idee.put("mail", mail);
        result.put("idee", idee);
        result.put("request", "PARTICIPE");

        return result;
    }

    private JSONObject liste() {
        JSONObject result = new JSONObject();
        result.put("request","LIST");
        return result;
    }

    private JSONObject getCommande() {
        return commande;

    }

}
