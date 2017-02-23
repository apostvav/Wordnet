import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.DirectedCycle;

public class WordNet {
    
    private Map<Integer, ArrayList<String>> synsetsMap;
    private Map<String, ArrayList<Integer>> nounsMap;
    private Map<Integer, ArrayList<Integer>> edgesMap;
    private Digraph G;
    private SAP sap;
    
    // constructor takes the name of the two input files
    public WordNet(String synsets, String hypernyms) throws IOException {

        this.synsetsMap = new HashMap<Integer, ArrayList<String>>();
        this.nounsMap = new HashMap<String, ArrayList<Integer>>();
        this.edgesMap = new HashMap<Integer, ArrayList<Integer>>();
        int graphNodes = 0;     
        
        // Process synsets.txt
        BufferedReader br = new BufferedReader(new FileReader(synsets));
        String line;
        while ((line = br.readLine()) != null) {
            String[] items = line.split(",");
            int synsetID = Integer.parseInt(items[0]);
            String[] nouns = items[1].split(" ");
            
            for (String noun : nouns) {
                if (!nounsMap.containsKey(noun)) {
                    nounsMap.put(noun, new ArrayList<Integer>());
                }
                nounsMap.get(noun).add(synsetID);
            }
            
            if (!synsetsMap.containsKey(synsetID)) {
                synsetsMap.put(synsetID, new ArrayList<String>());
            }
            
            for (String noun : nouns) {
                synsetsMap.get(synsetID).add(noun);
            }
            graphNodes++;            
        }
        br.close();

        // Process hypernyms.txt
        br = new BufferedReader(new FileReader(hypernyms));
        while ((line = br.readLine()) != null) {
            String[] items = line.split(",");
            int hypernymID = Integer.parseInt(items[0]);
            
            if (!edgesMap.containsKey(hypernymID)) {
                edgesMap.put(hypernymID, new ArrayList<Integer>());
            }
            
            for (int i = 1; i < items.length; i++) {
                edgesMap.get(hypernymID).add(Integer.parseInt(items[i]));
            }
        }
        br.close();
       
        // Construct Graph
        this.G = new Digraph(graphNodes);
        for (Map.Entry<Integer, ArrayList<Integer>> entry : edgesMap.entrySet()) {
            for (int edge : entry.getValue()) {
                this.G.addEdge(entry.getKey(), edge);
            }
        }
        
        // Check if there's a cycle
        DirectedCycle cycle = new DirectedCycle(this.G);
        if (cycle.hasCycle()) {
            throw new java.lang.IllegalArgumentException();
        }
      
        // Construct SAP 
        this.sap = new SAP(this.G);
    }

    // returns all WordNet nouns
    public Iterable<String> nouns() {
        return nounsMap.keySet();
    }

    // is the word a WordNet noun?
    public boolean isNoun(String word) {
        return nounsMap.containsKey(word);
    }

    // distance between nounA and nounB (defined below)
    public int distance(String nounA, String nounB) {
        if (!nounsMap.containsKey(nounA) || !nounsMap.containsKey(nounB)) {
            throw new java.lang.IllegalArgumentException();
        }
        return sap.length(nounsMap.get(nounA), nounsMap.get(nounB));
    }

    // a synset (second field of synsets.txt) that is the common ancestor of nounA and nounB
    // in a shortest ancestral path (defined below)
    public String sap(String nounA, String nounB) {
        if (!nounsMap.containsKey(nounA) || !nounsMap.containsKey(nounB)) {
            throw new java.lang.IllegalArgumentException();
        }
        int ancestor = sap.ancestor(nounsMap.get(nounA), nounsMap.get(nounA));
        return synsetsMap.get(ancestor).get(0);
    }

    /*
    // do unit testing of this class
    public static void main(String[] args) {

    }
     */
}