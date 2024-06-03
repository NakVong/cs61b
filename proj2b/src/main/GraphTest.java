package main;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static com.google.common.truth.Truth.assertThat;

public class GraphTest {
    @Test
    public void test() {
        Graph g = new Graph();
        g.addNode(645, "Water Ice Salt");
        String[] expected = {"Water", "Ice", "Salt"};
        assertThat(g.nodes.get(645)).isEqualTo(expected);

        ArrayList<Integer> hyponyms = new ArrayList<>();
        ArrayList<Integer> rHyponyms6 = new ArrayList<>();
        ArrayList<Integer> rHyponyms10 = new ArrayList<>();

        g.addOEdge(745, 6);
        hyponyms.add(6);
        assertThat(g.oEdges.get(745)).isEqualTo(hyponyms);

        g.addOEdge(745, 10);
        hyponyms.add(10);
        assertThat(g.oEdges.get(745)).isEqualTo(hyponyms);

        g.addREdge(745, 6);
        rHyponyms6.add(745);
        assertThat(g.rEdges.get(6)).isEqualTo(rHyponyms6);

        g.addREdge(745, 10);
        rHyponyms10.add(745);
        assertThat(g.rEdges.get(10)).isEqualTo(rHyponyms10);
    }
}
