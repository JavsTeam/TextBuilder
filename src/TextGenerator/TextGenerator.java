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
        parseWordsFromTxt();
    }

    public TextGenerator(File sourceTxtFile) {
        this(sourceTxtFile.getPath());
    }

    public String getText(int length) {
        StringBuilder text = new StringBuilder();
        Word current = getFirstWord();
        for (int i = 0; i < length; i++) {
            text.append(current.getWord() + " ");
            current = findWord(current.getNextWord());
        }
        return text.toString();
    }

    private Word getFirstWord() {
        ArrayList<Word> capital = new ArrayList<>();
        for (Word word : words) {
            if (word.getWord().length() > 0 && word.getWord().charAt(0) > 'A' && word.getWord().charAt(0) < 'Я') {
                capital.add(word);
            }
        }
        return capital.get(new Random().nextInt(capital.size()));
    }

    private void parseWordsFromTxt() {
        String[] textWords = sourceText.split(" ");

        String previousWord = textWords[0];
        addWord(previousWord);
        for (int i = 1; i < textWords.length; i++) {
            String currentWord = textWords[i];
            updateWords(previousWord, currentWord);
            previousWord = currentWord;
        }
    }

    private void updateWords(String previous, String current) {
        findWord(previous).addNextWord(current);
        addWord(current);
    }

    // returns link to word in the list
    private Word findWord(String word) {
        for (Word w : words) {
            // word already exists
            if (w.getWord().equals(word)) {
                return w;
            }
        }
        // word not found
        Word newWord = new Word(word);
        words.add(newWord);
        return newWord;
    }

    private void addWord(String word) {
        for (Word w : words) {
            // word already exists
            if (w.getWord().equals(word)) {
                return;
            }
        }
        // word not found
        Word newWord = new Word(word);
        words.add(newWord);
    }

    @Override
    public String toString() {
        return "TextGenerator{" +
                "words=" + words +
                '}';
    }

    private static class Word {
        private final String word;
        private final ArrayList<NextWord> nextWords = new ArrayList<>();

        private Word(String word) {
            this.word = word;
        }

        private void addNextWord(String word) {
            for (NextWord nextWord : nextWords) {
                // word already exists
                if (nextWord.getNextWord().equals(word)) {
                    nextWord.incrementCounter();
                    return;
                }
            }
            // word not found
            NextWord newNextWord = new NextWord(word);
            newNextWord.incrementCounter();
            nextWords.add(newNextWord);
        }

        private String getNextWord() {
            int total = 0;
            // counting total weight
            for (NextWord nextWord : nextWords) {
                total += nextWord.getCounter();
            }
            // probability distribution depends on frequency of word occurrence
            int result = new Random().nextInt(total) + 1;
            // getting randomly chosen word
            for (NextWord nextWord : nextWords) {
                result -= nextWord.getCounter();
                if (result <= 0) {
                    return nextWord.getNextWord();
                }
            }
            // only if something goes wrong
            return null;
        }

        private String getWord() {
            return word;
        }

        @Override
        public String toString() {
            return "\nWord{" +
                    "word='" + word + '\'' +
                    ", nextWords=" + nextWords +
                    '}';
        }

        private static class NextWord {
            private final String nextWord;
            private int counter;

            private NextWord(String nextWord) {
                this.nextWord = nextWord;
            }

            private void incrementCounter() {
                counter++;
            }

            private String getNextWord() {
                return nextWord;
            }

            private int getCounter() {
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
