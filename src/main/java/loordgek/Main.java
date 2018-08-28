package loordgek;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class Main {
    private static final List<String> fileNames = new ArrayList<>();
    private static final List<String> visitedDirs = new ArrayList<>();
    private static boolean lookInFolders;

    public static void main(String[] args) {
        String path = args[0];
        lookInFolders = Boolean.valueOf(args[1]);

        File folder = new File(path);
        if (folder.isDirectory()){
            searchFolder(folder);
        }
        fileNames.sort(String::compareToIgnoreCase);
        try {
            FileWriter writer = new FileWriter(new File(path + "/out.txt"));
            fileNames.forEach(s -> {
                try {
                    writer.write(s + System.getProperty("line.separator"));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void searchFolder(File folder){
        Arrays.stream(Objects.requireNonNull(folder.listFiles())).forEach(file -> {
            if (file.isDirectory() && !visitedDirs.contains(file.getPath())) {
                if (lookInFolders) {
                    searchFolder(file);
                    visitedDirs.add(file.getPath());
                }
            } else if (file.getName().contains(".")) {
                fileNames.add(file.getName().substring(0, file.getName().indexOf(".")));
            }
        });
    }
}
