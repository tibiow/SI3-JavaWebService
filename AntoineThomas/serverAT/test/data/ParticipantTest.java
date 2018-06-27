/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package data;

import org.json.simple.JSONObject;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author Thom
 */
public class ParticipantTest {
    Participant part;
    public ParticipantTest() {
    }

    @Before
    public void setUp() {
        part = new Participant("Not Sure", "toto@gmail.col", 0);
    }

    /**
     * Test of tojson method, of class Participant.
     */
    @Test
    public void testTojson() {
        JSONObject a = part.toJSON();
        System.out.println(a.toJSONString());
    }
    
}
