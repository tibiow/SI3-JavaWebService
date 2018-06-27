/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package data;

import java.util.ArrayList;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author Thom
 */
public class IdeeTest {
    Idee id;
    public IdeeTest() {
      
    }
    
    @Before
    public void setUp() {
        ArrayList<String> technos = new ArrayList<>();
        technos.add("JAVA");
        technos.add("JSON");
        ArrayList<Participant> participants = new ArrayList<>();
        participants.add(new Participant("Thomas Jalabert", "thom.jalabert@gmail", 0));
        participants.add(new Participant("Antoine Steyer", "antoine.steyer@gmail", 1));
        id = new Idee(0, "Server Java", "Petit projet client-serveur en java.", technos, participants);
    }

    @Test
    public void testToString() {
        System.out.println(id.toJSON().toJSONString());
    }
    
}
