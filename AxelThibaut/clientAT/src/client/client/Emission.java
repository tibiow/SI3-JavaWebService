package client.client;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.PrintWriter;
import java.util.Scanner;



public class Emission implements Runnable {

    private PrintWriter out;
    private String message = null;
    private Scanner sc = null;

    public Emission(PrintWriter out) {
        this.out = out;

    }

    private static String addRequest(){
        Scanner sc = new Scanner(System.in);
        JSONObject corps = new JSONObject();

        System.out.println("Veuillez saisir le titre de l'idée:");
        String str = sc.nextLine();
        corps.put("titre",str);
        System.out.println("Veuillez saisir le nom du créateur:");
        str = sc.nextLine();
        corps.put("nom",str);
        System.out.println("Veuillez saisir le mail du créateur:");
        str = sc.nextLine();
        corps.put("mail",str);
        System.out.println("Veuillez saisir la description de l'idée:");
        str = sc.nextLine();
        corps.put("description",str);
        JSONArray tech = new JSONArray();
        boolean bool;
        int i = 0;
        do{
            System.out.println("Voulez vous rajouter une technologie ? (y or n):");
            char ch = sc.nextLine().charAt(0);
            if(ch == 'y'){
                bool = true;
                System.out.println("Quel est le nom de la technologie ?");
                str = sc.nextLine();
                tech.put(i,str);
                i++;
            }
            else if(ch == 'n'){
                bool = false;
            }
            else{
                bool = true;
                System.out.println("erreur mauvaise indication");
            }
        }while(bool);


        corps.put("technologies",tech);
        JSONObject request = new JSONObject();
        request.put("request","ADD");
        request.put("idee",corps);

        return request.toString();
    }

    private static String listRequest(){
        JSONObject request = new JSONObject();
        request.put("request","LIST");
        return request.toString();
    }

    private static String giveInfo(){
        Scanner sc = new Scanner(System.in);

        System.out.println("que voulez vous faire (ADD or LIST)");
        String str = sc.nextLine();
        if(str.equals("ADD")){
            return addRequest();
        }
        else if(str.equals("LIST")){
            return listRequest();
        }
        else
            return null;
    }


    public void run() {

        sc = new Scanner(System.in);
        while(true){
            message = giveInfo();
            out.println(message);
            out.flush();

        }
    }
}