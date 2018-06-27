package requetesmodele;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.ArrayList;
import java.util.Scanner;

/**
 * Created by Antoine on 17/05/2016.
 */
public class Idee {

    private String titre;
    private String nom;
    private String mail;
    private String description;
    private ArrayList<String> technos;


    public Idee() {
        technos = new ArrayList<String>();
        saisieInfo();
    }

    public Idee(String titre, String nom, String mail, String description) {
        this.titre = titre;
        this.nom = nom;
        this.mail = mail;
        this.description = description;
        technos = new ArrayList<String>();
    }
    
    

    public void saisieInfo() {
        Scanner scan = new Scanner(System.in);
        System.out.println("----| Création d'une nouvelle idée |----");
        System.out.println("Saisissez le titre de l'idée : ");
        titre = scan.nextLine();
        System.out.println("Saisissez votre nom (Nom Prénom) : ");
        nom = scan.nextLine();
        System.out.println("Saisissez votre mail (toto@titi.com) : ");
        mail = scan.nextLine();
        System.out.println("Saisissez la description de l'idée : ");
        description = scan.nextLine();
        System.out.println("Saisissez les technologies (Valider une techno avec entrée, quitter avec \"quit\") : ");
        String tech = scan.nextLine();
        while(!tech.equals("quit")) {
            technos.add(tech);
            tech = scan.nextLine();
        }
        System.out.println("Idée crée");
    }

    public JSONObject toJSON() {
        JSONObject idee = new JSONObject();
        idee.put("titre",titre);
        idee.put("nom",nom);
        idee.put("mail",mail);
        idee.put("description",description);
        JSONArray techs = new JSONArray();
        for(String tech : technos) {
            techs.add(tech);
        }
        idee.put("technologies",techs);
        return idee;
    }
}
