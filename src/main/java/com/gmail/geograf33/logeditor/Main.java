package com.gmail.geograf33.logeditor;

import com.gmail.geograf33.logeditor.LogFileManager;
import com.gmail.geograf33.logeditor.paths.PathCreator;

import java.util.List;

public class Main {

    public static void main(String[] args) {

//        LogFileManager logFileManager = new LogFileManager();
//        logFileManager.readAndWrite("C:\\Users\\Adrian\\Documents\\Nauka programowania\\BGO pliki testowe\\logi oryginały\\U2019D_PLAN_ODBIORÓW_jbajer.2020-04-06.15-07-05.txt",
//                "C:\\Users\\Adrian\\Documents\\Nauka programowania\\" +
//                "BGO pliki testowe\\output.txt");

        PathCreator pathCreator = new PathCreator();
        List<String> listOfPathsToFiles = pathCreator.getListOfPathsToFilesAndDirs("C:\\Users\\Adrian\\Documents\\Nauka programowania\\BGO pliki testowe\\logi oryginały");
        listOfPathsToFiles.forEach(System.out::println);
    }
}
