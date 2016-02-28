package com.juliakram.core;

import model.constants.Complexity;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import static com.juliakram.core.WordCloud.Constants.delimiters;
import static com.juliakram.core.WordCloud.Constants.minLength;
import static com.juliakram.core.WordCloud.Constants.names;

/**
 * Created by juliakram on 15/02/16.
 */
public interface WordCloud {

    Map<String, Integer> buildCloud(String text);

    /**
     * Created by juliakram on 15/02/16.
     */
    abstract class Abstract extends AbstractAlgorithm<String, Map<String, Integer>> implements WordCloud {

        Abstract(Complexity oTime, Complexity oSpace) {
            super(oTime, oSpace);
        }

        @Override
        public Map<String, Integer> solve(String input) {
            return buildCloud(input);
        }
    }

    class Constants {
        static Set<Character> delimiters = new HashSet<>();
        static Set<String> names = new HashSet<>();

        static int minLength = 3;

        static {
            delimiters.add('.');
            delimiters.add(',');
            delimiters.add('/');
            //        delimiters.add('&');
            delimiters.add('(');
            delimiters.add(')');
            delimiters.add('\'');
        }

        static {
            names.add("Bill");
        }
    }

    /**
     * Created by juliakram on 15/02/16.
     */
    class Naive extends Abstract {

        public Naive() {
            super(Complexity.LINEAR, Complexity.LINEAR);
        }

        public Map<String, Integer> buildCloud(String text) {
            return easyCloud(text);
        }

        private static void addNotEmptyWord(Map<String, Integer> result, String word) {
            if (word.length() >= minLength) {

                word = names.contains(word) ? word : word.toLowerCase();

                Integer count = result.get(word);

                result.put(word, count == null ? 1 : count + 1);
            }
        }

        private Map<String, Integer> easyCloud(String text) {
            Map<String, Integer> result = new HashMap<>();

            StringBuilder delim = new StringBuilder();

            delimiters.forEach(delim::append);

            text = text.replaceAll("[" + delim.toString() + "]", "");

            String[] split = text.split(" ");

            for (String word : split) {
                addNotEmptyWord(result, word);
            }

            return result;
        }
    }

    /**
     * Created by juliakram on 15/02/16.
     */
    class Linear extends Abstract {

        public Linear() {
            super(Complexity.LINEAR, Complexity.LINEAR);
        }

        public Map<String, Integer> buildCloud(String text) {
            Map<String, Integer> result = new HashMap<>();

            StringBuilder word = new StringBuilder();

            for (int i = 0; i < text.length(); i++) {
                char c = text.charAt(i);

                if (isLetter(c)) {
                    word.append(c);
                    if (i == text.length() - 1) {
                        addNotEmptyWord(result, word.toString());
                    }
                } else {
                    addNotEmptyWord(result, word.toString());
                    word = new StringBuilder();
                }
            }

            return result;
        }

        private boolean isLetter(char c) {
            return !(delimiters.contains(c) || c == ' ');
            //        return "qwertyuiopasdfghjklzxcvbnmQWERTYUIOPASDFGHJKLZXCVBNM".contains("" + c);
        }

        private void addNotEmptyWord(Map<String, Integer> result, String word) {
            if (word.length() >= minLength) {

                word = names.contains(word) ? word : word.toLowerCase();

                Integer count = result.get(word);

                result.put(word, count == null ? 1 : count + 1);
            }
        }
    }
}