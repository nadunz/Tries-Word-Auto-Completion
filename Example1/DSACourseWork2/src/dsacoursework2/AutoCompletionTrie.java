package dsacoursework2;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

/**
 * Part 3 Implementation
 */
public class AutoCompletionTrie {


    /**
     * Reads all the words in a comma separated text document into an Array
     *
     * @param f
     */
    public static ArrayList<String> readWordsFromCSV(String file) throws FileNotFoundException {
        Scanner sc = new Scanner(new File(file));
        sc.useDelimiter(" |,|\\n");
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

    public static void saveCollectionToFile(Collection<?> c, String file) throws IOException {
        FileWriter fileWriter = new FileWriter(file);
        PrintWriter printWriter = new PrintWriter(fileWriter);
        for (Object w : c) {
            printWriter.println(w.toString());
        }
        printWriter.close();
    }

    public static HashMap<String, Double> getThreeMostFrequentWords(HashMap<String, Double> hm) {
        
        // Create a list from elements of HashMap 
        List<Map.Entry<String, Double>> list = new LinkedList<>(hm.entrySet());

        // Sort the list 
        Collections.sort(list,
                (Map.Entry<String, Double> o1, Map.Entry<String, Double> o2)
                -> (o2.getValue()).compareTo(o1.getValue()));

        // put data from sorted list to hashmap  
        HashMap<String, Double> temp = new LinkedHashMap<>();
        
        int count = 0; // to count the most threes
        for (Map.Entry<String, Double> aa : list) {
            temp.put(aa.getKey(), aa.getValue());
            count++;
            
            if(count >= 3) {
                // stop after added top 3
                break;
            }
        }
        return temp;
    }

    
    /**
     * 
     * 
     * ----PseudoCode----
     * 
     * in: read the words from the file
     * dictionary: parse the word list to create the dictionary
     * 
     * get the word set from dictionary and sort them
     * 
     * trie: create new trie to store words
     * 
     * for each word:
     *      add word to created trie
     * end
     * 
     * queries: read the queries from the file
     * allMatches: create empty list to collect all matches for all queries
     * 
     * for each query:
     * 
     *      mostThreeMatches: define a string to append the three most matches for each query
     *      append query to mostThreeMatches
     * 
     *      subTrie: get the sub tree for the query
      
     *      if the sub tree is not null:
     *          
     *          allWords: get the all words of the sub-tree
     * 
     *          sumOfFreq: calculate the sum of frequencies
     * 
     *          probabilityTable: construct an empty probability table
     * 
     *          for each word of allWords: 
     *              append the query prefix for the word to get the completed word
     *              frequency: f = get the frequency
     *              probability: p = f / sumOfFreq
     *              insert (word, p) to the map table
     *          end
     *          
     *          sort the probability table and get three most frequent words
     *          
     *          append these words with probabilities to the mostThreeMatches line
     * 
     *       end if
     * 
     *       add the mostThreeMatches line to the collection
     * end
     * 
     * finally save the matches to a file
     * 
     * @param args
     * @throws Exception 
     */
    public static void main(String[] args) throws Exception {

        //--------------- Read the lotr file and save the dictionary -----------
        DictionaryFinder df = new DictionaryFinder();

        ArrayList<String> in = readWordsFromCSV("TextFiles/lotr.csv");

        // parse the word list to create the dictionary
        df.formDictionary(in);
        // save the dictionary to a file with frequencies 
        df.saveToFile("lotrDictionary.csv");

        //------------- Create A Trie from the dictioanry ----------------------
        Trie trie = new Trie();

        // get the dictinary map
        Map<String, Integer> dictionary = df.getDictionary();

        // get the word set
        ArrayList<String> keyList = new ArrayList<>(dictionary.keySet());

        // sort the word set
        Collections.sort(keyList);

        // iterate over the dictionary and add the words to the trie
        for (String word : keyList) {
            trie.add(word);
        }

        //---------- Load queries ----------
        ArrayList<String> queries = readWordsFromCSV("TextFiles/lotrQueries.csv");

        ArrayList<String> allMatches = new ArrayList<>();

        // take each queries
        for (String query : queries) {

            // define a string to append the three most matches for each query
            String mostThreeMatches = query;

            // get the sub tree for the query
            Trie subTrie = trie.getSubTrie(query.trim());

            // if the sub tree is not null
            if (subTrie != null) {
                
                // get the all words of the sub-tree
                List<String> allWords = subTrie.getAllWords();

                // calculate the sum of frequencies
                int sumOfFreq = 0;
                for (String word : allWords) {
                    word = query + word;
                    sumOfFreq += dictionary.get(word);
                }

                // ---------- Eacg query create probability Table ----------
                
                // construct the probabilty table
                HashMap<String, Double> probabilityTable = new HashMap<>();

                // create the probability table
                for (String word : allWords) {
                    // add the query prfix for the word to get the completed word
                    word = query + word;
                    // get the frequency
                    int f = dictionary.get(word);
                    // get the probability
                    double p = f / (sumOfFreq * 1d);
                    // insert to the map table
                    probabilityTable.put(word, p);
                }
                
                // sort the probability table and get three most frequent words
                probabilityTable = getThreeMostFrequentWords(probabilityTable);
                
                // append these words with probabilities to the mostThreeMatches line
                for (Map.Entry<String, Double> entrySet : probabilityTable.entrySet()) {
                    String word = entrySet.getKey();
                    Double prob = entrySet.getValue(); // probability
                    mostThreeMatches += "," + word + "," + prob;
                }

            }

            // add the mostThreeMatches line to the collection
            allMatches.add(mostThreeMatches);

        }
        
        // finally save the matches to a file
        saveCollectionToFile(allMatches, "lotrMatches.csv");

    }
}
