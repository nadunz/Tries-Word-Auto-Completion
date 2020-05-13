package dsacoursework2;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * The Trie data structure
 */
public class Trie {

    TrieNode root;

    public Trie() {
        this.root = new TrieNode();
    }

    public Trie(TrieNode root) {
        this.root = root;
    }

    /**
     * 
     * ----PseudoCode----
     * 
     * function add(key)
     *   
     *      alreadyExists = keep a variable to track the key is already exists
     *      trieNode = current node
     *      
     *      for each character from key:
     *          index = get the relevant index for the character 
     *          get trie node for that index
     *          if node is null
     *              assign a new trie node to the node
     *              mark as already not exists  
     *          end
     *          assign node to the current node
     *      end
     * 
     *      return trieNode
     * end
     * 
     * 
     * @param key the word to be added
     * @return return if added, otherwise false
     */
    public boolean add(String key) {
        char[] characters = key.trim().toCharArray();

        TrieNode trieNode = root; // current trie node

        boolean alreadyExists = true;

        for (char ch : characters) {
            // get the character represent index from the current trie node
            int index = ch - 'a';
            if (trieNode.offsprings[index] == null) {
                trieNode.offsprings[index] = new TrieNode();
                alreadyExists = false;
            }

            trieNode = trieNode.offsprings[index];
        }

        trieNode.isRepresentACompleteWord = true;

        return alreadyExists;
    }

    /**
     * This method check whether the given key contains or not
     * 
     * ----PseudoCode----
     * 
     * function contains(key)
     *   
     *      contains = keep a variable to track the key contains or not
     *      trieNode = current node
     *      
     *      for each character from key:
     *          index = get the relevant index for the character 
     *          get trie node for that index
     *          if the trie node is null
     *              mark as not contain 
     *              exit from the loop
     *          end
     *          assign node to the current node(next node)
     *      end
     * 
     *      return contains
     * end
     * 
     * @param key the lookup key
     * @return true if contains, otherwise false
     */
    public boolean contains(String key) {

        char[] characters = key.trim().toCharArray();

        TrieNode trieNode = root; // current trie node

        boolean contains = true;

        for (char ch : characters) {

            // get the character represent index from the current trie node
            int index = ch - 'a';
            if (trieNode.offsprings[index] == null) {
                contains = false;
                break;
            }

            trieNode = trieNode.offsprings[index];
        }

        if (trieNode != null) {
            contains = trieNode.isRepresentACompleteWord;
        }

        return contains;
    }

    /**
     * 
     * ----PseudoCode----
     * 
     * function outputBreadthFirstSearch()
     * 
     *      output: out
     *      enqueue the root to a queue
     * 
     *      while queue is not empty:
     *          s = dequeue from queue
     *          append s to out (node string value represents all character representation of not-null nodes)
     *          for each children of node s:
     *              if node is not null add to the queue
     *          end 
     *      end
     * end
     * 
     */
    public String outputBreadthFirstSearch() {

        String output = "";

        // create a queue for BFS 
        LinkedList<TrieNode> queue = new LinkedList<>();

        queue.add(root);

        while (queue.size() != 0) {
            // dequeue a node from queue and append to the output
            TrieNode s = queue.poll();

            // append to the output
            output += s;

            // get all children nodes of the dequeued node s
            TrieNode[] children = s.offsprings;
            for (int i = 0; i < 26; i++) {
                TrieNode next = children[i];
                if (next != null) {
                    queue.add(next);
                }

            }
        }
        return output;
    }

    // search Depth First Search recursively
    public String outputDepthFirstSearch() {
        // output string buffer
        StringBuilder output = new StringBuilder();
        outputDepthFirstSearch(root, output);
        return output.toString();
    }

    /**
     * 
     * ----PseudoCode----
     * 
     * function outputDepthFirstSearch(rootNode, output)
     * 
     *      if rootNode is null
     *          return
     * 
     *      for each children of rootNode:
     *          if node is not null:
     *              append the relevant character to the output
     *          end
     *          recursive call: outputDepthFirstSearch(childrenNode, output) 
     *      end 
     * end
     * 
     * @param rootNode the current start node
     * @param output the output string builder
     */
    private void outputDepthFirstSearch(TrieNode rootNode, StringBuilder output) {

        if (rootNode == null) {
            return;
        }
        for (int i = 0; i < 26; i++) {

            TrieNode trieNode = rootNode.offsprings[i];

            if (trieNode != null) {
                char ch = (char) (i + 'a');
                output.append(ch);
            }

            outputDepthFirstSearch(trieNode, output);
        }
    }

    /**
     * 
     * ----PseudoCode----
     * 
     * function getSubTrie(prefix)
     *   
     *      contains = keep a variable to track the prefix contains or not
     *      trieNode = current node
     *      
     *      for each character from prefix:
     *          index = get the relevant index for the character 
     *          get trie node for that index
     *          if the trie node is null
     *              mark as not contain 
     *              exit from the loop
     *          end
     *          assign node to the current node(next node)
     *      end
     * 
     *      if contains: return new trie with current trie node as the root
     *      else: return null
     *          
     * end
     * 
     * @param prefix the query string
     * @return the sub-tree for the prefix
     */
    public Trie getSubTrie(String prefix) {

        char[] characters = prefix.trim().toCharArray();

        TrieNode trieNode = root; // current trie node

        boolean contains = true;

        for (char ch : characters) {

            // get the character represent index from the current trie node
            int index = ch - 'a';
            if (trieNode.offsprings[index] == null) {
                contains = false;
                break;
            }

            trieNode = trieNode.offsprings[index];
        }

        Trie subTrie = null;
        if (contains) {
            subTrie = new Trie(trieNode);
        }

        return subTrie;
    }

    public List<String> getAllWords() {

        // create a list for all words
        List<String> words = new ArrayList<>();
        // get the words to the list
        getAllWords(root, "", 0, words);
        // and return the list of words
        return words;
    }

    /**
     * 
     * 
     * * ----PseudoCode----
     * 
     * function getAllWords(trieNode, word, level, list)
     * 
     *      if trieNode is a leaf of the tree:
     *          add word to the list
     *      end
     * 
     *      for i in range from 0 to 25 :
     *          
     *          get i th childNode
     * 
     *          if childNode is not null:
     * 
     *              get the relevant character for the child node
     *              update the word upto the current level
     *              append the new character of this node to the word
     * 
     *              recursive call: getAllWords(childNode, word, level + 1, list)
     *          end
     *      end
     * end
     * 
     * 
     * @param n the root node
     * @param word current word upto the current level of the tree
     * @param level current level
     * @param list the word list(output list)
     */
    private void getAllWords(TrieNode n, String word, int level, List<String> list) {

        // if the node is completed the word
        if (n.isRepresentACompleteWord) {
            // add the current word
            list.add(word);
        }

        // iterate over all childs
        for (int i = 0; i < 26; i++) {

            // if the child is not null
            if (n.offsprings[i] != null) {
                // get the relavant character
                char c = (char) (i + 'a');
                // update the word upto the current level
                word = word.substring(0, level);
                // add the new character of this node
                word += c;
                // recursively call for the next level of this child node
                getAllWords(n.offsprings[i], word, level + 1, list);
            }
        }
    }

    // Test the trie
    public static void main(String[] args) {
        
        // create trie
        Trie t = new Trie();

        t.add("cheers");
        t.add("cheese");
        t.add("chat");
        t.add("cat");
        t.add("bat");

        // check contains
        System.out.println(t.contains("cheese"));
        System.out.println(t.contains("bat"));
        
        // bread first search
        System.out.println(t.outputBreadthFirstSearch());
        // depth first search
        System.out.println(t.outputDepthFirstSearch());

        // sub trie for 'ch'
        Trie subTrie = t.getSubTrie("ch");

        // sub trie bread first search
        System.out.println(subTrie.outputBreadthFirstSearch());

        List<String> allWords = t.getAllWords();

        for (String w : allWords) {
            System.out.println(w);
        }

    }

}
