package org.academiadecodigo.splicegirls;

import java.util.ArrayList;

public class Randomizer {

    /**
     * Randomizer Class
     * @return
     */

    public static int randomizer() {
        return (int) Math.floor((Math.random() * 1000) + 1);
    }

    public static int RandomWordIndex(ArrayList<String> gameWordsList){
        return (int) Math.floor(Math.random() * (gameWordsList.size()+1));
    }
}
