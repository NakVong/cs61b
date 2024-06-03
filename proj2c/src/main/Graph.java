package main;

import java.util.ArrayList;
import java.util.HashMap;

public class Graph {
    // adjacency list representation
    HashMap<Integer, ArrayList<Integer>> oEdges;
    HashMap<Integer, ArrayList<Integer>> rEdges;
    HashMap<Integer, String[]> nodes;
    HashMap<String, ArrayList<Integer>> wToKey;

    public Graph() {
        oEdges = new HashMap<>();
        rEdges = new HashMap<>();
        nodes = new HashMap<>();
        wToKey = new HashMap<>();
    }

    public void addNode(Integer id, String node) {
        String[] nWords = node.split(" ");
        nodes.put(id, nWords);
        for (String word : nWords) {
            if (wToKey.containsKey(word)) {
                wToKey.get(word).add(id);
            } else {
                ArrayList<Integer> keys = new ArrayList<>();
                keys.add(id);
                wToKey.put(word, keys);
            }
        }
    }

    public void addOEdge(Integer synsetID, Integer hyponymID) {
        if (oEdges.containsKey(synsetID)) {
            oEdges.get(synsetID).add(hyponymID);
        } else {
            ArrayList<Integer> hyponyms = new ArrayList<>();
            hyponyms.add(hyponymID);
            oEdges.put(synsetID, hyponyms);
        }
    }

    public void addREdge(Integer synsetID, Integer hyponymID) {
        if (rEdges.containsKey(hyponymID)) {
            rEdges.get(hyponymID).add(synsetID);
        } else {
            ArrayList<Integer> synsets = new ArrayList<>();
            synsets.add(synsetID);
            rEdges.put(hyponymID, synsets);
        }
    }
}
