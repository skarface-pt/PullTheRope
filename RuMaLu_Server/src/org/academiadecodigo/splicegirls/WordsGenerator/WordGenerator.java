package org.academiadecodigo.splicegirls.WordsGenerator;

import org.academiadecodigo.splicegirls.Randomizer;

import java.util.ArrayList;

public class WordGenerator {

    /**
     * GameWord Class
     * contains all the words of the game and give one word at a time
     */


    /**
     * Instance Variables
     */

    private WordReader wordReader;
    private ArrayList <String> gameWords;

    /**
     * Constructor
     */

    public WordGenerator(String filename){
        this.wordReader = new WordReader(filename);
        gameWords = new ArrayList();
        loadAllWords();
    }

    /**
     * Load the container for all words of the File
     */
    private void loadAllWords(){

        for (String word: wordReader) {
            if (word.length() > 3) {
                gameWords.add(word);
            }
        }
    }

    /**
     * Give a random word from the game words repository
     * @return
     */

    public String getOneWord(){
        return gameWords.get(Randomizer.RandomWordIndex(gameWords));
    }
}

