import java.io.IOException;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

public class Outcast {
    
    private WordNet wordnet;
    
    // constructor takes a WordNet object
    public Outcast(WordNet wordnet) {
        this.wordnet = wordnet;
    }
    
    // given an array of WordNet nouns, return an outcast
    public String outcast(String[] nouns) {
        String outcast = null;
        int maxdistance = -1;
        
        for (String noun1 : nouns) {
            int distance = 0;
            for (String noun2 : nouns) {
                if (noun1.equals(noun2)) {
                    distance += wordnet.distance(noun1, noun2);
                }
            }
            if (distance > maxdistance) {
                maxdistance = distance;
                outcast = noun1;
            }
        }
        return outcast;
    }
    
    public static void main(String[] args) throws IOException {
        WordNet wordnet;
        wordnet = new WordNet(args[0], args[1]);
        Outcast outcast = new Outcast(wordnet);
        for (int t = 2; t < args.length; t++) {
            In in = new In(args[t]);
            String[] nouns = in.readAllStrings();
            StdOut.println(args[t] + ": " + outcast.outcast(nouns));
        }
    }
}