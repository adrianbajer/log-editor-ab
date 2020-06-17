package com.gmail.geograf33.logeditor;

import com.gmail.geograf33.logeditor.paths.PathCreator;

import java.io.File;
import java.net.URISyntaxException;
import java.util.List;

public class Main {

    public static void main(String[] args) {

//        ------------------------------------------------
        long startTime = System.nanoTime();
//        ------------------------------------------------


        LogFileManager logFileManager = new LogFileManager();
        PathCreator pathCreator = new PathCreator();

//        extract path to dir where the application has been started
        String mainDirPath = null;
        try {
            mainDirPath = pathCreator.extractMainDirPath(new File(Main.class.getProtectionDomain().getCodeSource().getLocation()
                    .toURI()).getPath());
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

//        create list of files and dirs
        List<String> listOfPathsToFiles = pathCreator.getListOfPathsToFilesAndDirs(mainDirPath);

//        create destination dir and path to it
        String fileName = "";
        int srtLogCounter = 0;
        new File(mainDirPath + "\\output").mkdir();
        String dstDirPath = mainDirPath + "\\output\\";

//        search of log files intended to edit
        for (String srcFilePath : listOfPathsToFiles) {
            if (srcFilePath.contains("PLAN_ODBIORÓW") && srcFilePath.contains(".txt")) {
                fileName = logFileManager.extractFileNameFromPoLog(srcFilePath);
                String dstFilePath = dstDirPath + fileName;
                logFileManager.readAndWrite(srcFilePath, dstFilePath);
            } else if (srcFilePath.contains("STAN_REALIZACJI") && srcFilePath.contains(".txt")){
                srtLogCounter++;
                fileName = logFileManager.createFileNameFromSrtLog(srcFilePath, srtLogCounter);
                String dstFilePath = dstDirPath + fileName;
                logFileManager.readAndWrite(srcFilePath, dstFilePath);
            }
        }
        System.out.println("Done!");

//        ------------------------------------------------
        long endTime = System.nanoTime();
        long totalTime = (endTime - startTime) / 1000000;
        System.out.println("Total work time (milis): " + totalTime);
//        ------------------------------------------------
    }
}
