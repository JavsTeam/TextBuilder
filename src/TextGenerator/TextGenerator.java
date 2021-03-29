package TextGenerator;

import TextGenerator.handlers.Reader;

import java.io.File;
import java.util.ArrayList;
import java.util.Random;

public class TextGenerator {
    ArrayList<Word> words = new ArrayList<>();
    private final String sourceText;

    public TextGenerator(String sourceTxtPath) {
        sourceText = Reader.readTxt(sourceTxtPath);
        words = parseWordsFromTxt();
    }

    public TextGenerator(File sourceTxtFile) {
        this(sourceTxtFile.getPath());
    }

    // TODO
    public String getText(int length) {
        return null;
    }

    // TODO
    private ArrayList<Word> parseWordsFromTxt() {
        ArrayList<Word> words = new ArrayList<>();
        return words;
    }

    static class Word {
        private String word;
        private final ArrayList<NextWord> nextWords = new ArrayList<>();

        public Word(String word) {
            this.word = word;
        }

        void addNextWord(String word) {
            for(NextWord nextWord : nextWords) {
                // word already exists
                if(nextWord.getNextWord().equals(word)) {
                    nextWord.incrementCounter();
                    return;
                }
            }

            // word not found
            NextWord newNextWord = new NextWord(word);
            newNextWord.incrementCounter();
            nextWords.add(newNextWord);
        }

        String getNextWord() {
            int total = 0;
            // counting total weight
            for(NextWord nextWord : nextWords) {
                total += nextWord.getCounter();
            }
            // probability distribution depends on frequency of word occurrence
            int result = new Random().nextInt(total) + 1;
            // getting randomly chosen word
            for(NextWord nextWord : nextWords) {
                result -= nextWord.getCounter();
                if(result <= 0) {
                    return nextWord.getNextWord();
                }
            }
            // only if something goes wrong
            return null;
        }

        @Override
        public String toString() {
            return "Word{" +
                    "word='" + word + '\'' +
                    ", nextWords=" + nextWords +
                    '}';
        }

        private class NextWord {
            private final String nextWord;
            private int counter;

            public NextWord(String nextWord) {
                this.nextWord = nextWord;
            }

            void incrementCounter() {
                counter++;
            }

            public String getNextWord() {
                return nextWord;
            }

            public int getCounter() {
                return counter;
            }

            @Override
            public String toString() {
                return "\n\tNextWord{" +
                        "nextWord='" + nextWord + '\'' +
                        ", counter=" + counter +
                        '}';
            }
        }
    }

}
