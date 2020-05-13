package dsacoursework2;

/**
 * A Node of the Trie
 */
public class TrieNode {

    TrieNode[] offsprings = new TrieNode[26];

    boolean isRepresentACompleteWord;

    public TrieNode() {
        isRepresentACompleteWord = false;
        for (int i = 0; i < 26; i++) {
            offsprings[i] = null;
        }
    }

    /**
     * Return all the letters in this level
     * 
     * 
     * function toString()
     * 
     *      Output: out
     * 
     *      for i in range 0 to 25 :
     *          node = get i th node
     *          if node is not null
     *              c = character value relevant to the i 
     *              append c to out 
     *          end
     *          write line to the file with new line
     *      end
     * 
     *      return out
     * end
     * 
     * 
     * @return 
     */
    @Override
    public String toString() {
        String out = "";
        for (int i = 0; i < 26; i++) {
            if (offsprings[i] != null) {
                out += (char) (i + 'a');
            }
        }
        return out;
    }

}
