package TextBuilder.handlers;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.File;
import java.util.ArrayList;

public class State {
    private final int depth;
    private final String sourceTxtPath;
    private final String stateName;

    public State(int depth, String sourceTxtPath) {
        this.depth = depth;
        this.sourceTxtPath = sourceTxtPath;
        stateName = createStateFileName(depth, sourceTxtPath);
    }

    public boolean isExist() {
        return Files.isFileExist(stateName);
    }

    public <T> ArrayList<T> get(TypeToken<ArrayList<T>> containerToken) {
        if (isExist()) {
            String state = Reader.readTxt(Files.getFile(stateName));
            return new Gson().fromJson(state, containerToken.getType());
        }
        throw new IllegalStateException("Saved state does not exist");
    }

    public void save(ArrayList<?> state) {
        GsonBuilder builder = new GsonBuilder().setPrettyPrinting();
        String stateString = builder.create().toJson(state);
        File file;
        if (Files.isFileExist(stateName)) {
            file = Files.getFile(stateName);
        } else {
            file = Files.createFile(stateName, Files.Dir.PROCESSED.get());
        }
        Writer.writeTextTo(stateString, file);
    }

    private String createStateFileName(int depth, String sourceTxtPath) {
        return "state-depth-" + depth + "-" + sourceTxtPath.substring(sourceTxtPath.lastIndexOf('\\') + 1);
    }
}
