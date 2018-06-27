package data;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;
import serverat.ServerAT;

/**
 * Created by Antoine on 03/05/2016.
 */
public class BaseDeDonnee {

    private final ArrayList<Idee> idees;
    private final ArrayList<Participant> participants;
    private int idIdee;
    private int idParticipant;

    public BaseDeDonnee() {
        super();
        this.idees = new ArrayList<>();
        this.participants = new ArrayList<>();
        idIdee = 0;
        idParticipant = 0;
    }


    public ArrayList<Participant> getParticipantsInteresses(int idIdee) {
        return idees.get(idIdee).getInteresses();
    }

    public ArrayList<Idee> getIdees() {
        return idees;
    }

    public JSONObject addIdee(JSONObject idee) {
        JSONObject tosend = new JSONObject(); 
        idees.add(new Idee(idee, idIdee));
        idIdee++;
        return JSONStatus(tosend, true);
    }

    public JSONObject participe(JSONObject o) throws Exception {
        //Participant p, int id
        int id = ((Long) o.get("identifiant")).intValue();
        String mail = (String) o.get("mail");
        
        for (Participant parti : participants) {
            if (parti.getEmail().equalsIgnoreCase(mail)) {
                return addInteresse(parti, id);
            }
        }
        
        // participant non trouvé
        Participant p = new Participant(mail, idParticipant++);
        this.participants.add(p);
        return addInteresse(p, id);
        
        
    }

    private JSONObject addInteresse(Participant p, int id) throws Exception {

        
        for (Idee idee : idees) {
            if (idee.getIdentifiant() == id) {
                if (idee.getInteresses().contains(p)) {
                    throw new Exception("Participant déja interessé !");
                } else {
                    idee.addInteresse(p);
                    
                    return JSONStatus( new JSONObject(), true);
                }
            }
        }
      
        throw new Exception("Idée Non trouvée");
        
    }

    public JSONObject getJSONList() {
        JSONObject tosent = new JSONObject();
        JSONArray array = new JSONArray();
        idees.stream().forEach((idee) -> {
            array.add(idee.toJSON());
        });
        tosent.put("list", array);
        return JSONStatus(tosent, true);
    }

    public JSONObject getJSONList(int id) throws Exception {
        JSONObject tosend = new JSONObject();

        idees.stream().filter((idee) -> (idee.getIdentifiant() == id)).forEach((idee) -> {
            tosend.put("list", idee.toJSON());
        });
        if (tosend.isEmpty()) {
            throw new Exception("Idée Non trouvée");
        } else {
            JSONStatus(tosend, true);
        }
        return tosend;
    }

    public JSONObject JSONStatus(JSONObject o, boolean ok) {
        if (ok) {
            o.put("status", "OK");
        } else {
            o.put("status", "BAD REQUEST");
        }
        return o;
    }

}
