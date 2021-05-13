package TextBuilder;

import TextBuilder.handlers.Reader;
import TextBuilder.handlers.State;
import com.google.gson.reflect.TypeToken;

import java.io.File;
import java.rmi.UnexpectedException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class TextBuilder {

    private final State state;

    private HashMap<String, Word> words = new HashMap<>();

    public TextBuilder(int depth, String sourceTxtPath) {
        state = new State(depth, sourceTxtPath);
        if (state.isExist()) {
            // Do not delete type argument
            words = state.get(new TypeToken<HashMap<String, Word>>() {
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

    public String getText(int minLength) {
        StringBuilder text = new StringBuilder();
        String word = getFirstWord();
        int counter = 0;

        while(true) {
            if (counter++ > minLength && isEnding(word)) {
                text.append(word);
                break;
            }
            text.append(word).append(" ");
            word = findWord(words.get(word).getNextWord());
        }
        return text.toString();
    }

    private static final String[] endMarks = {".", "?", "!", "...", ")"};

    private boolean isEnding(String word) {
        if(word.length() > 1) {
            for (String mark : endMarks) {
                if (word.contains(mark)) {
                    return true;
                }
            }
        }
        return false;
    }

    public void printText(int minLength) {
        System.out.println(getText(minLength));
    }

    private String getFirstWord() {
        ArrayList<String> capital = new ArrayList<>();
        for (Map.Entry<String, Word> word : words.entrySet()) {
            if (word.getKey().length() > 0 &&
                    word.getKey().charAt(0) > 'A' &&
                    word.getKey().charAt(0) < 'Я') {
                capital.add(word.getKey());
            }
        }
        return capital.get(new Random().nextInt(capital.size()));
    }


    // TODO: Better handling of empty words
    private void parseWordsFromText(int depth, String text) {
        String[] textWords = text.split(" ");
        StringBuilder word = new StringBuilder(textWords[0]);
        int index = 0;

        for (int i = 0; i < textWords.length; i++) { // pick first word
            if (!textWords[i].equals("")) {
                word = new StringBuilder(textWords[i]);
                index = i;
                break;
            }
        }

        for (int i = index + 1; i < depth; i++) {
            if (!textWords[i].equals("")) {
                word.append(" ").append(textWords[i]);
            }
        }

        addWord(word.toString());

        for (int i = depth; i < textWords.length - depth; i++) {
            if (textWords[i].equals("")) {
                continue;
            }
            StringBuilder currentWord = new StringBuilder(textWords[i]);
            for (int j = 1; j < depth; j++) {
                currentWord.append(" ").append(textWords[++i]);
            }
            updateWords(word.toString(), currentWord.toString());
            word = currentWord;
        }
    }

    private void updateWords(String previous, String current) {
        words.get(findWord(previous)).addNextWord(current);
        addWord(current);
    }

    // returns link to word in the list
    private String findWord(String word) {
        if (!words.containsKey(word)) {
            // word not found
            Word newWord = new Word();
            words.put(word, newWord);
        }
        return word;
    }

    private void addWord(String word) {
        if (!words.containsKey(word)) {
            // word not found
            Word newWord = new Word();
            words.put(word, newWord);
        }
    }

    /**
     * Call this method if changes have been made to the file we are currently working with,
     * but its name has not changed, so the {@link TextBuilder.handlers.State} handler
     * will not notice the difference
     * and will not update the state file.
     *
     * @return an instance of itself so that it can be used after a constructor or any other method
     */
    public TextBuilder invalidateCache() {
        state.invalidate();
        saveState();
        return this;
    }

    private void saveState() {
        state.save(words);
    }

    @Override
    public String toString() {
        return "TextGenerator{" +
                "words=" + words.toString() +
                '}';
    }

    private static class Word {

        private final HashMap<String, Integer> nextWords = new HashMap<>();

        private Word() {
        }

        public String toString() {
            return "next words=" + nextWords.toString() + '}';
        }

        private void addNextWord(String word) {
            if (nextWords.containsKey(word)) {
                Integer i = nextWords.get(word);
                nextWords.put(word, i + 1);
            }
            // word not found
            nextWords.put(word, 1);
        }


        // TODO: Better exception handling
        private String getNextWord() {
            try {
                int total = 0;
                // counting total weight
                if (!nextWords.isEmpty()) {
                    for (Map.Entry<String, Integer> nextWord : nextWords.entrySet()) {
                        total += nextWord.getValue();
                    }
                } else throw new UnexpectedException("NO NEXT WORD");
                // probability distribution depends on frequency of word occurrence
                int result = new Random().nextInt(total) + 1;
                // getting randomly chosen word
                for (Map.Entry<String, Integer> nextWord : nextWords.entrySet()) {
                    result -= nextWord.getValue();
                    if (result <= 0) {
                        return nextWord.getKey();
                    }
                }
                // only if something goes wrong
                throw new UnexpectedException("NO NEXT WORD");
            } catch (UnexpectedException e) {
                e.printStackTrace();
            }
            return null;
        }
    }
}