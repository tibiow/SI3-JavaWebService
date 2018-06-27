package serverat;

import data.BaseDeDonnee;
import java.io.*;
import java.net.ServerSocket;
import org.json.simple.parser.JSONParser;

public class ServerAT {

    public static void main(String[] zero) {
        ServerSocket serv;
        BaseDeDonnee bdd = new BaseDeDonnee();
        try {
            serv = new ServerSocket(9999);
            Thread t = new Thread(new ManageConnect(serv, bdd));
            t.start();
            System.out.println("SERVER LAUNCH !");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
