import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.BreadthFirstDirectedPaths;

public class SAP {
    
    private Digraph G;

    // constructor takes a digraph (not necessarily a DAG)
    public SAP(Digraph G) {       
        if (G == null) {
            throw new java.lang.NullPointerException();
        }
        
        this.G = new Digraph(G);
    }

    // length of shortest ancestral path between v and w; -1 if no such path
    public int length(int v, int w) {
        checkValues(v, w);
        
        BreadthFirstDirectedPaths bfsv = new BreadthFirstDirectedPaths(G, v);
        BreadthFirstDirectedPaths bfsw = new BreadthFirstDirectedPaths(G, w);
        
        int ancestor = ancestor(v, w);
        if (ancestor != -1) {
            return bfsv.distTo(ancestor) + bfsw.distTo(ancestor);
        }
        else {
            return -1;
        }
    }

    // a common ancestor of v and w that participates in a shortest ancestral path; -1 if no such path
    public int ancestor(int v, int w) {
        checkValues(v, w);
        
        BreadthFirstDirectedPaths bfsv = new BreadthFirstDirectedPaths(G, v);
        BreadthFirstDirectedPaths bfsw = new BreadthFirstDirectedPaths(G, w);
        
        int ancestor = -1;
        int shortestPath = Integer.MAX_VALUE;
        
        for (int i = 0; i < G.V(); i++) {
            if (bfsv.hasPathTo(i) && bfsw.hasPathTo(i)) {
                int distance = bfsv.distTo(i) + bfsw.distTo(i);
                if (distance < shortestPath) {
                    shortestPath = distance;
                    ancestor = i;
                }
            }
        }     
        return ancestor;
    }

    // length of shortest ancestral path between any vertex in v and any vertex in w; -1 if no such path
    public int length(Iterable<Integer> v, Iterable<Integer> w) {
        checkValues(v, w);
        
        BreadthFirstDirectedPaths bfsv = new BreadthFirstDirectedPaths(G, v);
        BreadthFirstDirectedPaths bfsw = new BreadthFirstDirectedPaths(G, w);
        
        int ancestor = ancestor(v, w);
        
        if (ancestor != -1) {
            return bfsv.distTo(ancestor) + bfsw.distTo(ancestor);
        }
        else {
            return -1;
        }
        
    }

    // a common ancestor that participates in shortest ancestral path; -1 if no such path
    public int ancestor(Iterable<Integer> v, Iterable<Integer> w) {
        checkValues(v, w);

        int ancestor = -1;
        int shortestPath = Integer.MAX_VALUE;
        
        BreadthFirstDirectedPaths bfsv = new BreadthFirstDirectedPaths(G, v);
        BreadthFirstDirectedPaths bfsw = new BreadthFirstDirectedPaths(G, w);
        
        for (int i = 0; i < G.V(); i++) {
            if (bfsv.hasPathTo(i) && bfsw.hasPathTo(i)) {
                int distance = bfsv.distTo(i) + bfsw.distTo(i);
                if (distance < shortestPath) {
                    shortestPath = distance;
                    ancestor = i;
                }
            }
        }     
        return ancestor;
    }
    
    // helper methods
    private void checkValues(int v, int w) {
        if (v < 0 || v > G.V()-1 || w < 0 || w > G.V()-1) {
                throw new java.lang.IndexOutOfBoundsException();
        }
    }
    
    private void checkValues(Iterable<Integer> v, Iterable<Integer> w) {
        for (int i : v) {
            if (i < 0 || i > G.V() - 1) {
                throw new java.lang.IndexOutOfBoundsException();
            }
        }
        
        for (int i : w) {
            if (i < 0 || i > G.V() - 1) {
                throw new java.lang.IndexOutOfBoundsException();
            }
        }
    }

    /*
    // do unit testing of this class
    public static void main(String[] args) {
        
    }
     */
}
