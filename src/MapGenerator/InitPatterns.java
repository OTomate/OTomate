/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MapGenerator;

import Otomate.Univers;
import java.util.LinkedList;
import java.util.List;

/**
 *
 */
public class InitPatterns {
    
    public List<$Pattern> patterns = new LinkedList<$Pattern>();
    
    public InitPatterns(Univers u) {
        if(u.numero() == 1) { // univers de zombies
            
            VillePattern v1 = new VillePattern(4,4,80);
            VillePattern v2 = new VillePattern(6, 4, 90);
            
            DesertPattern d1 = new DesertPattern(10, 10, 20);
            
            patterns.add(v1);
            patterns.add(v2);
            patterns.add(d1);
            
        } else if(u.numero() == 2) { // les robots
            
        }
    }
    
}