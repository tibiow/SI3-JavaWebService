package client.client;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;


public class Reception implements Runnable {

    private BufferedReader in;
    private String message = null;

    public Reception(BufferedReader in){

        this.in = in;
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

    public void run() {

        while(true){
            try {

                message = in.readLine();

            } catch (IOException e) {

                e.printStackTrace();
            }

            JSONObject answer =  new JSONObject(message);

            String status = answer.getString("status");

            if(status.equals("OK")){
                if(answer.has("list")) {//check if list exist
                    JSONArray list = answer.getJSONArray("list");
                    for (int i = 0; i < list.length(); i++) {
                        JSONObject idea = list.getJSONObject(i).getJSONObject("idee");
                        //System.out.println(idea);
                        System.out.println("IDEE N°" + idea.getInt("identifiant"));
                        System.out.println("   - Titre : " + idea.getString("titre"));
                        System.out.println("   - Description : " + idea.getString("description"));
                        //System.out.println("   - Nom de l'auteur : " + idea.getString("nom"));
                        //System.out.println("   - Mail de l'auteur : " + idea.getString("mail"));
                        if (idea.getJSONArray("technologies").length() != 0) {
                            System.out.println("   - Technologie(s) : ");
                            for (int g = 0; g < idea.getJSONArray("technologies").length(); g++) {
                                System.out.println("         - " + idea.getJSONArray("technologies").get(g));
                            }
                        }
                        if (idea.getJSONArray("interesses").length() != 0) {
                            System.out.println("   - Intéressé(s) : ");
                            JSONArray participants = idea.getJSONArray("interesses");
                            for (int g = 0; g < participants.length(); g++) {
                                JSONObject part  = participants.getJSONObject(g).getJSONObject("participant");
                                System.out.println("         - identifiant : " + part.getInt("identifiant"));
                                System.out.println("         - nom : " + part.getString("nom"));
                                System.out.println("         - mail : " + part.getString("mail")+"\n");

                            }
                        }
                        System.out.println();
                    }
                }
            }

            System.out.println(status);

        }
    }

}