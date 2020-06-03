package com.gmail.geograf33.logeditor;

import com.gmail.geograf33.logeditor.paths.PathCreator;

import java.util.List;

public class Main {

    public static void main(String[] args) {

        LogFileManager logFileManager = new LogFileManager();
        PathCreator pathCreator = new PathCreator();
        List<String> listOfPathsToFiles = pathCreator.getListOfPathsToFilesAndDirs("C:\\Users\\Adrian\\Documents\\Nauka programowania\\BGO pliki testowe\\logi oryginały");

        String fileName = "";
        int srtLogCounter = 0;
        String dstDirPath = "C:\\Users\\Adrian\\Documents\\Nauka programowania\\BGO pliki testowe\\output\\";

//        listOfPathsToFiles.forEach(System.out::println);

        for (String srcFilePath : listOfPathsToFiles) {
            if (srcFilePath.contains("PLAN_ODBIORÓW")) {
                fileName = logFileManager.extractFileNameFromPoLog(srcFilePath);
                String dstFilePath = dstDirPath + fileName;
                logFileManager.readAndWrite(srcFilePath, dstFilePath);
//                System.out.println(fileName);
//                System.out.println(dstFilePath);
            } else if (srcFilePath.contains("STAN_REALIZACJI")){
                srtLogCounter++;
                fileName = logFileManager.createFileNameFromSrtLog(srcFilePath, srtLogCounter);
                String dstFilePath = dstDirPath + fileName;
                logFileManager.readAndWrite(srcFilePath, dstFilePath);
//                System.out.println(fileName);
//                System.out.println(dstFilePath);
            }
        }
    }
}
