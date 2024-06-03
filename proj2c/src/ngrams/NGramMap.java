package ngrams;

import edu.princeton.cs.algs4.In;

import java.util.Collection;
import java.util.HashMap;

import static ngrams.TimeSeries.MAX_YEAR;
import static ngrams.TimeSeries.MIN_YEAR;

/**
 * An object that provides utility methods for making queries on the
 * Google NGrams dataset (or a subset thereof).
 *
 * An NGramMap stores pertinent data from a "words file" and a "counts
 * file". It is not a map in the strict sense, but it does provide additional
 * functionality.
 *
 * @author Josh Hug
 */
public class NGramMap {
    HashMap<String, TimeSeries> allWords;
    TimeSeries wordsPerYear;

    /**
     * Constructs an NGramMap from WORDSFILENAME and COUNTSFILENAME.
     */
    public NGramMap(String wordsFilename, String countsFilename) {
        allWords = new HashMap<>();
        wordsPerYear = new TimeSeries();

        In inWord = new In(wordsFilename);
        while (!inWord.isEmpty()) {
            String currLine = inWord.readLine();
            String[] splitLine = currLine.split("\t");
            String word = splitLine[0];
            if (!allWords.containsKey(word)) {
                allWords.put(word, new TimeSeries());
            }
            allWords.get(word).put(Integer.parseInt(splitLine[1]), Double.parseDouble(splitLine[2]));
        }

        In inCount = new In(countsFilename);
        while (!inCount.isEmpty()) {
            String currLine = inCount.readLine();
            String[] splitLine = currLine.split(",");
            Integer year = Integer.parseInt(splitLine[0]);
            wordsPerYear.put(year, Double.parseDouble(splitLine[1]));
        }
    }

    /**
     * Provides the history of WORD between STARTYEAR and ENDYEAR, inclusive of both ends. The
     * returned TimeSeries should be a copy, not a link to this NGramMap's TimeSeries. In other
     * words, changes made to the object returned by this function should not also affect the
     * NGramMap. This is also known as a "defensive copy". If the word is not in the data files,
     * returns an empty TimeSeries.
     */
    public TimeSeries countHistory(String word, int startYear, int endYear) {
        if (allWords.containsKey(word)) {
            return new TimeSeries(allWords.get(word), startYear, endYear);
        } else {
            return new TimeSeries();
        }
    }

    /**
     * Provides the history of WORD. The returned TimeSeries should be a copy, not a link to this
     * NGramMap's TimeSeries. In other words, changes made to the object returned by this function
     * should not also affect the NGramMap. This is also known as a "defensive copy". If the word
     * is not in the data files, returns an empty TimeSeries.
     */
    public TimeSeries countHistory(String word) {
        if (allWords.containsKey(word)) {
            return new TimeSeries(allWords.get(word), MIN_YEAR, MAX_YEAR);
        } else {
            return new TimeSeries();
        }
    }

    /**
     * Returns a defensive copy of the total number of words recorded per year in all volumes.
     */
    public TimeSeries totalCountHistory() {
        return new TimeSeries(wordsPerYear, MIN_YEAR, MAX_YEAR);
    }

    /**
     * Provides a TimeSeries containing the relative frequency per year of WORD between STARTYEAR
     * and ENDYEAR, inclusive of both ends. If the word is not in the data files, returns an empty
     * TimeSeries.
     */
    public TimeSeries weightHistory(String word, int startYear, int endYear) {
        TimeSeries relativeFrequency = new TimeSeries();

        TimeSeries countPerYear = countHistory(word, startYear, endYear);
        TimeSeries newWords = new TimeSeries(wordsPerYear, startYear, endYear);
        for (int year = startYear; year <= endYear; year++) {
            if (countPerYear.containsKey(year)) {
                relativeFrequency.put(year, countPerYear.get(year) / newWords.get(year));
            }
        }
        return relativeFrequency;

    }

    /**
     * Provides a TimeSeries containing the relative frequency per year of WORD compared to all
     * words recorded in that year. If the word is not in the data files, returns an empty
     * TimeSeries.
     */
    public TimeSeries weightHistory(String word) {
        return weightHistory(word, MIN_YEAR, MAX_YEAR);
    }

    /**
     * Provides the summed relative frequency per year of all words in WORDS between STARTYEAR and
     * ENDYEAR, inclusive of both ends. If a word does not exist in this time frame, ignore it
     * rather than throwing an exception.
     */
    public TimeSeries summedWeightHistory(Collection<String> words,
                                          int startYear, int endYear) {
        TimeSeries sum = new TimeSeries();
        for (String word : words) {
            if (this.allWords.containsKey(word)) {
                sum = sum.plus(weightHistory(word, startYear, endYear));
            }
        }
        return sum;
    }

    /**
     * Returns the summed relative frequency per year of all words in WORDS. If a word does not
     * exist in this time frame, ignore it rather than throwing an exception.
     */
    public TimeSeries summedWeightHistory(Collection<String> words) {
        return summedWeightHistory(words, MIN_YEAR, MAX_YEAR);
    }
}
