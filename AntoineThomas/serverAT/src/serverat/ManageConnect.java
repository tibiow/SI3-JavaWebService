/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package serverat;

import data.BaseDeDonnee;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 *
 * @author chronos
 */
public class ManageConnect implements Runnable{
    private final ServerSocket socks_serv;
    private Socket socks;
    ServerSocket serv;
    BaseDeDonnee bdd = new BaseDeDonnee();
    private int nbclient;    
    
    public ManageConnect(ServerSocket ss, BaseDeDonnee bdd){
        socks_serv = ss;
        this.bdd = bdd;
        nbclient = 0;
    }

    @Override
    public void run() {
        try {
            
            while(true){                
                socks = socks_serv.accept();
                nbclient++;
                System.out.println("Il y a actuellement " + nbclient + " clients connectés.");

                Thread newclient = new Thread(new Worker(socks, bdd, this));
                newclient.start();
            }
        } catch (IOException e) {
            System.err.println("Erreur de connexion");
        }
    }
    
    
    public int getNbclient() {
        return nbclient;
    }

    public void OneClientTakeTheDoor() {
        this.nbclient--;
        System.out.println("Un client a arrété la connection.\nIl y a donc " + nbclient + " clients connectés.");
        
    }
    
}
