package TextGenerator.handlers;

import java.io.File;
import java.io.IOException;

public class FilesHandler {
    private static final File SRC_DIR = new File("src");
    private static final File FILES_DIR = new File("src/TextGenerator/data/files");

    public static File getFile(String fileName) {
        File file;
        if ((file = recursiveSearch(SRC_DIR, fileName)) != null) {
            return file;
        } else {
            return create(fileName);
        }
    }

    public static String getPath(String fileName) {
        File file;
        if ((file = recursiveSearch(SRC_DIR, fileName)) != null) {
            return file.getPath();
        } else {
            return create(fileName).getPath();
        }
    }

    public static File create(String fileName, String pathToDirectory) {
        File newFile = new File(pathToDirectory + "/" + fileName);
        try {
            return newFile.createNewFile() ? newFile : null;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static File create(String fileName, File directory) {
        return create(fileName, directory.getPath());
    }

    public static File create(String fileName) {
        return create(fileName, FILES_DIR.getPath());
    }

    public static boolean isExist(String fileName) {
        return recursiveSearch(SRC_DIR, fileName) != null;
    }

    private static File recursiveSearch(File current, String fileName) {
        if (current.isDirectory()) {
            for (File content : current.listFiles()) {
                File result = recursiveSearch(content, fileName);
                if (result != null) {
                    return result;
                }
            }
            return null;
        } else {
            return current.getName().equals(fileName) ? current : null;
        }
    }
}
