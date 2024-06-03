package main;

import edu.princeton.cs.algs4.In;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.ArrayList;
import java.util.HashSet;

public class WordNet {
    // wrapper for a graph
    private Graph g;
    public WordNet(String synsetFile, String hyponymFile) {
        // empty graph
        g = new Graph();

        // add all the synsets into the graph
        In synsets = new In(synsetFile);
        while (!synsets.isEmpty()) {
            String currLine = synsets.readLine();
            String[] splitLine = currLine.split(",");
            g.addNode(Integer.parseInt(splitLine[0]), splitLine[1]);
        }

        // add all the edges into the graph + reversed
        In hyponyms = new In(hyponymFile);
        while (!hyponyms.isEmpty()) {
            String currLine = hyponyms.readLine();
            String[] splitLine = currLine.split(",");
            for (int i = 1; i < splitLine.length; i++) {
                g.addOEdge(Integer.parseInt(splitLine[0]), Integer.parseInt(splitLine[i]));
                g.addREdge(Integer.parseInt(splitLine[0]), Integer.parseInt(splitLine[i]));
            }
        }
    }

    private class DFS {
        private boolean[] marked;
        private HashSet<Integer> path;

        public DFS() {
            marked = new boolean[g.nodes.size()];
            path = new HashSet<>();
        }

        private void oDfs(Integer v) {
            marked[v] = true;
            path.add(v);
            if (g.oEdges.get(v) == null) {
                return;
            } else {
                for (int w : g.oEdges.get(v)) {
                    if (!marked[w]) {
                        oDfs(w);
                    }
                }
            }
        }

        private void rDfs(Integer v) {
            marked[v] = true;
            path.add(v);
            if (g.rEdges.get(v) == null) {
                return;
            } else {
                for (int w : g.rEdges.get(v)) {
                    if (!marked[w]) {
                        rDfs(w);
                    }
                }
            }
        }

        public ArrayList<String> getPath() {
            ArrayList<String> words = new ArrayList<>();
            for (Integer id : path) {
                words.addAll(Arrays.asList(g.nodes.get(id)));
            }
            Collections.sort(words);
            return words;
        }
    }

    public String[] getHyponyms(List<String> words) {
        ArrayList<HashSet<String>> paths = new ArrayList<>();
        for (String word : words) {
            ArrayList<Integer> ids = g.wToKey.get(word);
            if (ids == null) {
                return new String[0];
            }
            HashSet<String> path = new HashSet<>();
            for (Integer id : ids) {
                DFS dfs = new DFS();
                dfs.oDfs(id);
                path.addAll(dfs.getPath());
            }
            paths.add(path);
        }

        for (int i = 1; i < paths.size(); i++) {
            paths.get(0).retainAll(paths.get(i));
        }
        ArrayList<String> response = new ArrayList<>(paths.get(0));
        Collections.sort(response);
        return response.toArray(new String[0]);
    }
}
