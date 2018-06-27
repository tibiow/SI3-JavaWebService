package data;

import org.json.simple.JSONObject;

/**
 * Created by Antoine on 03/05/2016.
 */
public class Participant {
    private final int identifiant;
    private final String nom;
    private final String email;

    public Participant(String nom, String email, int identifiant) {
        this.nom = nom;
        this.identifiant = identifiant;
        this.email = email;
    }
    
    public Participant(String email, int identifiant) {
        this("", email, identifiant);
    }
    
    public Participant(JSONObject o, int id) {
        this.identifiant = id;
        this.nom = (String) o.get("nom");
        this.email = (String) o.get("mail");
    }

    public String getEmail() {
        return email;
    }   

    public JSONObject toJSON() {
        JSONObject part = new JSONObject();
        JSONObject data = new JSONObject();
        data.put("identifiant", identifiant);
        data.put("nom", nom);
        data.put("mail", email);
        part.put("participant", data);
        return part;
    }
    
}
