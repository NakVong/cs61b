package main;

import browser.NgordnetQuery;
import browser.NgordnetQueryHandler;
import browser.NgordnetQueryType;
import ngrams.NGramMap;

import java.util.Arrays;
import java.util.List;

public class HyponymsHandler extends NgordnetQueryHandler {
    private WordNet wordNet;
    private NGramMap ngm;
    public HyponymsHandler(WordNet wordNet, NGramMap ngm) {
        this.wordNet = wordNet;
        this.ngm = ngm;
    }

    @Override
    public String handle(NgordnetQuery q) {
        List<String> words = q.words();
        int startYear = q.startYear();
        int endYear = q.endYear();
        int k = q.k();

        if (q.ngordnetQueryType() == NgordnetQueryType.HYPONYMS) {
            return Arrays.toString(wordNet.getHyponyms(words, startYear, endYear, k, ngm));
        } else {
            return Arrays.toString(wordNet.getAncestors(words, startYear, endYear, k, ngm));
        }
    }
}
