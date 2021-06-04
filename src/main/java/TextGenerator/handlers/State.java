package TextGenerator.handlers;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import lombok.extern.jbosslog.JBossLog;

import java.io.File;
import java.util.HashMap;

@JBossLog
public class State {
    private final String stateName;

    public State(int depth, @SuppressWarnings("CdiInjectionPointsInspection") String sourceTxtPath) {
        stateName = createStateFileName(depth, sourceTxtPath);
    }

    public boolean isExist() {
        return Files.isFileExist(stateName);
    }

    public <T> HashMap<String,T> get(TypeToken<HashMap<String,T>> containerToken) {
        if (isExist()) {
            String state = Reader.readTxt(Files.getFile(stateName));
            return new Gson().fromJson(state, containerToken.getType());
        }
        throw new IllegalStateException("Saved state does not exist");
    }

    public void save(HashMap<String, ?> state) {
        GsonBuilder builder = new GsonBuilder().setPrettyPrinting();
        String stateString = builder.create().toJson(state);
        log.debug("getStateFile " + getStateFile().getName() + " " + getStateFile().getAbsolutePath());
        Writer.writeTextTo(stateString, getStateFile());
    }

    public void invalidate() {
        getStateFile().delete();
    }

    private File getStateFile() {
        File file;
        if (Files.isFileExist(stateName)) {
            file = Files.getFile(stateName);
        } else {
            file = Files.createFile(stateName, Files.Dir.PROCESSED.get());
        }
        return file;
    }
    /* FIXME: 5/29/21
        Windows -> sourceTxtPath.substring(sourceTxtPath.lastIndexOf('\\') + 1)
        *nix -> sourceTxtPath.substring(sourceTxtPath.lastIndexOf('/') + 1)
     */
    private String createStateFileName(int depth, String sourceTxtPath) {
        return "state-depth-" + depth + "-" + sourceTxtPath.substring(sourceTxtPath.lastIndexOf('/') + 1);
    }
}
