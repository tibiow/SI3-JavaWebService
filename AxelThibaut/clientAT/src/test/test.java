package test;


import org.json.JSONArray;
import org.json.JSONObject;

import java.net.SocketPermission;
import java.util.Scanner;

/**
 * Created by tibo on 06/05/16.
 */
public class test {

    public static String addRequest(){
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
            System.out.println(str);
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

    public static String listRequest(){
        JSONObject request = new JSONObject();
        request.put("request","LIST");
        return request.toString();
    }

    public static String giveInfo(){
        Scanner sc = new Scanner(System.in);

        System.out.println("que voulez vous faire (ADD or LIST)");
        String str = sc.nextLine();
        if(str.compareTo("ADD")==0){
            return addRequest();
        }
        else if(str=="LIST"){
            return listRequest();
        }
        else
            return null;
    }

    public static void displayList(String answer){
        JSONObject ans =  new JSONObject(answer);
        String status = ans.getString("status");
        if(status.compareTo("OK")==0){
            JSONArray array = ans.getJSONArray("list");
            System.out.println("liste des personnes interressées : ");
            for (int i = 0; i < array.length(); i++) {
                System.out.println(array.get(i));
            }
        }
        System.out.println(status);
    }

    public static void main(String[] args){

        System.out.println();
    }

}
