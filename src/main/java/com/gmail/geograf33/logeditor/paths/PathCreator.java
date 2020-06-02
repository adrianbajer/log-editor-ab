package com.gmail.geograf33.logeditor.paths;

import java.io.File;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class PathCreator {

    public List<String> getListOfPathsToFilesAndDirs(String mainDirPath) {

        List<String> fileAndDirNamesList = Stream.of(new File(mainDirPath).listFiles())
                .map(File::getName)
                .collect(Collectors.toList());


        // loop creates paths to files and dirs
        for (int i = 0; i < fileAndDirNamesList.size(); i++){
            fileAndDirNamesList.set(i, mainDirPath + "\\" + fileAndDirNamesList.get(i));
        }

        return fileAndDirNamesList;

    }
}
