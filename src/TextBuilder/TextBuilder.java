package TextBuilder;

import TextBuilder.handlers.Reader;
import TextBuilder.handlers.State;
import com.google.gson.reflect.TypeToken;

import java.io.File;
import java.rmi.UnexpectedException;
import java.util.ArrayList;
import java.util.Random;

public class TextBuilder {
    private ArrayList<Word> words = new ArrayList<>();
    private final State state;

    public TextBuilder(int depth, String sourceTxtPath) {
        state = new State(depth, sourceTxtPath);
        if (state.isExist()) {
            // Do not delete type argument
            words = state.get(new TypeToken<ArrayList<Word>>() {
            });
        } else {
            parseWordsFromText(depth, Reader.readTxt(sourceTxtPath));
            saveState();
        }
    }

    public TextBuilder(int depth, File sourceTxtFile) {
        this(depth, sourceTxtFile.getPath());
    }

    public TextBuilder(String sourceTxtPath) {
        this(1, sourceTxtPath);
    }

    public TextBuilder(File sourceTxtFile) {
        this(1, sourceTxtFile);
    }

    private static final String[] conditionsOfEnd = {".", "?", "!"};
    private static final String[] conditionsOfNext = {"一", "—", "-"};

    public String getText(int minLength) {
        StringBuilder text = new StringBuilder();
        Word current = getFirstWord();
        outer:
        for (int i = 0; i < Integer.MAX_VALUE; i++) {
            try {
                String word = current.getWord();

                if (word.contains("@")) {
                    text.append("\n").append(word).append("\n");
                    current = findWord(current.getNextWord());
                    if (word.length() > 2 && i > minLength) {
                        break;
                    }
                    continue;
                }

                for (String condition : conditionsOfEnd) {
                    if (word.contains(condition)) {
                        text.append(word).append("\n");
                        current = findWord(current.getNextWord());
                        if (word.length() > 2 && i > minLength) {
                            break outer;
                        }
                        continue outer;
                    }
                }
                for (String condition : conditionsOfNext) {
                    if (word.contains(condition)) {
                        if (text.toString().lastIndexOf("\n") != text.toString().length() - 1) {
                            text.append("\n");
                        }
                        text.append(word).append(" ");
                        current = findWord(current.getNextWord());
                        continue outer;
                    }
                }
                text.append(word).append(" ");
                current = findWord(current.getNextWord());
            } catch (UnexpectedException e) {
                return text.toString();
            }
        }
        return text.toString();
    }

    public void printText(int minLength) {
        System.out.println(getText(minLength));
    }

    private Word getFirstWord() {
        ArrayList<Word> capital = new ArrayList<>();
        for (Word word : words) {
            if (word.getWord().charAt(0) > 'A' &&
                    word.getWord().charAt(0) < 'Я') {
                capital.add(word);
            }
        }
        return capital.get(new Random().nextInt(capital.size()));
    }

    private void parseWordsFromText(int depth, String text) {
        String[] textWords = text.split(" ");
        StringBuilder previousWord = new StringBuilder(textWords[0]);
        for (int i = 1; i < depth; i++) {
            previousWord.append(" ").append(textWords[i]);
        }
        addWord(previousWord.toString());

        for (int i = depth; i < textWords.length - depth; i++) {
            StringBuilder currentWord = new StringBuilder(textWords[i]);
            for (int j = 1; j < depth; j++) {
                currentWord.append(" ").append(textWords[++i]);
            }
            updateWords(previousWord.toString(), currentWord.toString());
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

    private void saveState() {
        state.save(words);
    }

    /**
     * Call this method if changes have been made to the file we are currently working with,
     * but its name has not changed, so the {@link TextBuilder.handlers.State} handler
     * will not notice the difference
     * and will not update the state file.
     * @return an instance of itself so that it can be used after a constructor or any other method
     */
    public TextBuilder invalidateCache() {
        state.invalidate();
        saveState();
        return this;
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

        private String getNextWord() throws UnexpectedException {
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
            throw new UnexpectedException("NO NEXT WORD");
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
