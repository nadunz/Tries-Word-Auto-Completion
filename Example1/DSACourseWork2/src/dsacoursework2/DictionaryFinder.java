package dsacoursework2;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * Dictionary Finder
 */
public class DictionaryFinder {

    // create hash map for the dictionary
    private Map<String, Integer> dictionary;

    public DictionaryFinder() {
        this.dictionary = new HashMap<>();
    }

    /**
     * Reads all the words in a comma separated text document into an Array
     *
     * @param f
     */
    public static ArrayList<String> readWordsFromCSV(String file) throws FileNotFoundException {
        Scanner sc = new Scanner(new File(file));
        sc.useDelimiter(" |,");
        ArrayList<String> words = new ArrayList<>();
        String str;
        while (sc.hasNext()) {
            str = sc.next();
            str = str.trim();
            str = str.toLowerCase();
            words.add(str);
        }
        return words;
    }

    /**
     * This method constructs the dictionary with frequencies and save it to a hash map
     * 
     * ----PseudoCode----
     * 
     * function formDictionary(list of words)
     *      for each word:
     *          if dictionary contains the key word
     *              increase the frequency by 1
     *          else
     *              insert the new word with frequency 1
     *          end
     *      end
     * end
     * 
     * @param words given the word list
     */
    public void formDictionary(ArrayList<String> words) {
        // for each word of the list
        for (String word : words) {
            if (dictionary.containsKey(word)) {
                int f = dictionary.get(word); // get the current frequency
                // update the frequency
                dictionary.replace(word, f + 1);
            } else {
                dictionary.put(word, 1);
            }
        }
    }

    /**
     * This method save the dictionary to the given file
     * 
     * ----PseudoCode----
     * 
     * function saveToFile(file name)
     * 
     *      S = take the set of words in the dictionary
     *      sort(S)
     *      
     *      for each word from sorted S:
     *          f = get the frequency for word
     *          line = word, f
     *          write line to the file with new line
     *      end
     * end
     * 
     * 
     * @param fileName the file name to be saved
     * @throws IOException if any error when writing to the file
     */
    public void saveToFile(String fileName) throws IOException {

        FileWriter fileWriter = new FileWriter(fileName);
        PrintWriter printWriter = new PrintWriter(fileWriter);

        ArrayList<String> keyList = new ArrayList<>(dictionary.keySet());

        // sort the word set
        Collections.sort(keyList);

        // iterate over the dictionary
        for (String word : keyList) {
            int freq = dictionary.get(word); // get the frequency
            printWriter.println(word + "," + freq);
        }

        printWriter.close();

    }

    public Map<String, Integer> getDictionary() {
        return dictionary;
    }

    public static void main(String[] args) throws Exception {

        DictionaryFinder df = new DictionaryFinder();
        ArrayList<String> in = readWordsFromCSV("TextFiles/testDocument.csv");

        // parse the word list to create the dictionary
        df.formDictionary(in);
        // save the dictionary to a file with frequencies 
        df.saveToFile("Dictionary.csv");

    }

}
