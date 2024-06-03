package main;

import browser.NgordnetQuery;
import browser.NgordnetQueryHandler;

import java.util.Arrays;
import java.util.List;

public class HyponymsHandler extends NgordnetQueryHandler {
    private WordNet wordNet;
    public HyponymsHandler(WordNet wordNet) {
        this.wordNet = wordNet;
    }

    @Override
    public String handle(NgordnetQuery q) {
        List<String> words = q.words();
        String[] hyponyms = wordNet.getHyponyms(words);

        return Arrays.toString(hyponyms);
    }
}
