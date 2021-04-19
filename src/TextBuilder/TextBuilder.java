package TextBuilder;

import TextBuilder.handlers.Files;
import TextBuilder.handlers.Reader;
import TextBuilder.handlers.State;
import TextBuilder.handlers.Writer;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
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

    private String getStateFileName(int depth, String sourcePath) {
        return "state-depth-" + depth + "-" + sourcePath.substring(sourcePath.lastIndexOf('\\') + 1);
    }

    private static final TypeToken<HashMap<String, Word>> STATE_TYPE = new TypeToken<HashMap<String, Word>>() {
    };

    private void loadSavedState(String stateName) {
        String state = Reader.readTxt(Files.getFile(stateName));
        words = new Gson().fromJson(state, STATE_TYPE.getType());
    }

    private boolean isSavedStateExist(String fileName) {
        return Files.isFileExist(fileName);
    }

    private void saveStateTo(String fileName) {
        GsonBuilder builder = new GsonBuilder().setPrettyPrinting();
        String state = builder.create().toJson(words);
        File file;
        if (Files.isFileExist(fileName)) {
            file = Files.getFile(fileName);
        } else {
            file = Files.createFile(fileName, Files.Dir.PROCESSED.get());
        }
        Writer.writeTextTo(state, file);
    }

    private static final String[] conditionsOfEnd = {".", "?", "!"};
    private static final String[] conditionsOfNext = {"一", "—", "-"};

    public String getText(int minLength) {
        StringBuilder text = new StringBuilder();
        String current = getFirstWord();
        outer:
        for (int i = 0; i < Integer.MAX_VALUE; i++) {
            try {

                String word = current;
                for (String condition : conditionsOfEnd) {
                    if (word.contains(condition)) {
                        text.append(word + "\n");
                        current = findWord(words.get(current).getNextWord());
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

                        text.append(word + " ");
                        current = findWord(words.get(current).getNextWord());
                        continue outer;
                    }
                }
                text.append(word + " ");
                current = findWord(words.get(current).getNextWord());
            } catch (UnexpectedException e) {
                return text.toString();
            }
        }
        return text.toString();
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
        words.get(findWord(previous)).addNextWord(current);
        addWord(current);
    }

    // returns link to word in the list
    private String findWord(String word) {
        if (words.containsKey(word)) {
            return word;
        } else {
            // word not found
            Word newWord = new Word();
            words.put(word, newWord);
            return word;
        }
    }

    private void addWord(String word) {
        if (!words.containsKey(word)) {
            // word not found
            Word newWord = new Word();
            words.put(word, newWord);
        }
    }

    private void saveState() {
        state.save(words);
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

        private String getNextWord() throws UnexpectedException {
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
        }
    }
}
