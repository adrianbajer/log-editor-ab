package com.gmail.geograf33.logeditor;

import com.gmail.geograf33.logeditor.paths.PathCreator;

import java.io.File;
import java.util.List;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in, "ibm852");
        System.out.println("Podaj ścieżkę do katalogu z logami:");
        String mainDirPath = scanner.nextLine();

        LogFileManager logFileManager = new LogFileManager();
        PathCreator pathCreator = new PathCreator();
        List<String> listOfPathsToFiles = pathCreator.getListOfPathsToFilesAndDirs(mainDirPath);

        String fileName = "";
        int srtLogCounter = 0;
        new File(mainDirPath + "\\output").mkdir();
        String dstDirPath = mainDirPath + "\\output\\";

        for (String srcFilePath : listOfPathsToFiles) {
            if (srcFilePath.contains("PLAN_ODBIORÓW")) {
                fileName = logFileManager.extractFileNameFromPoLog(srcFilePath);
                String dstFilePath = dstDirPath + fileName;
                logFileManager.readAndWrite(srcFilePath, dstFilePath);
            } else if (srcFilePath.contains("STAN_REALIZACJI")){
                srtLogCounter++;
                fileName = logFileManager.createFileNameFromSrtLog(srcFilePath, srtLogCounter);
                String dstFilePath = dstDirPath + fileName;
                logFileManager.readAndWrite(srcFilePath, dstFilePath);
            }
        }
        System.out.println("Gotowe!");
    }
}
