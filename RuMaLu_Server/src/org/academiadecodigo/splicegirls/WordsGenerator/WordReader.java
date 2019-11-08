package org.academiadecodigo.splicegirls.WordsGenerator;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Word Reader reads a text file using a buffered (character) reader
 */
public class WordReader implements Iterable<String> {

    private final String filename;

    /**
     * Constructor
     */

    public WordReader(String filename) {
        this.filename = filename;
    }

    /**
     * @see Iterator
     */
    @Override
    public Iterator<String> iterator() {
        return new WordReaderIterator();
    }


    /**
     * INNER CLASS
     * WordReaderIterator - iterates for each word of the file
     */


    private class WordReaderIterator implements Iterator<String> {

        private BufferedReader inputBufferedReader;

        private String[] words;
        private String currentLine;
        private int wordsIndex;

        public WordReaderIterator() {

            try {

                inputBufferedReader = new BufferedReader(new FileReader(filename));

                // read the first line of the file
                currentLine = readLineOfText();
                words = getLineWords(currentLine);

            } catch (FileNotFoundException e) {

                throw new IllegalArgumentException(e);
            }
        }

        private String readLineOfText() {

            String line = null;

            try {

                line = inputBufferedReader.readLine();

                // end of file
                if (line == null) {
                    inputBufferedReader.close();
                    return null;
                }

                // line contains no words or only contains non-word characters, fetch a new one
                if (line.equals("") || line.matches("\\W+")) {
                    return readLineOfText();
                }
            } catch (IOException e) {
                // Make sure this iterator does not return any more elements
                // in case of a file error
                currentLine = null;
            }

            return line;

        }

        private String[] getLineWords(String line) {

            return line != null ? line.split("[, \\/]") : new String[0];
        }



        /**
         * @see Iterator#hasNext()
         */
        @Override
        public boolean hasNext() {
            // currentLine is null when stream is over
            return currentLine != null;
        }

        /**
         * @see Iterator#next()
         */
        @Override
        public String next() {

            if (!hasNext()) {
                throw new NoSuchElementException();
            }

            String result = words[wordsIndex];
            wordsIndex++;

            if (wordsIndex == words.length) {

                // reads new line if no more words in current line
                currentLine = readLineOfText();
                words = getLineWords(currentLine);
                wordsIndex = 0;

            }

            return result;

        }

        @Override
        public void remove() {
            // Java 7 does not have default implementation
            // this operation is labelled as optional and as such,
            // should return UnsupportedOperationException if it
            // does not make sense for this Iterator
            throw new UnsupportedOperationException("remove");
        }
    }
}