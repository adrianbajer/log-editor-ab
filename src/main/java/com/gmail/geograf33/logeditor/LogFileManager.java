package com.gmail.geograf33.logeditor;

import java.io.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LogFileManager {

    // TODO: 03.06.2020 skaner do pobierania ścieżki źródłowej od użytkownika
    // TODO: 03.06.2020 sprawdzić czy jak nie ma katalogu output, to aplikacja go stworzy czy muszę napisać do tego kod
    // TODO: 03.06.2020 czy jak aplikacja odpalona z ikony to gdzie wyświetli się żądanie podania ścieżki
    // TODO: 03.06.2020 napisać testy


    public void readAndWrite(String srcFilePath, String dstFilePath){
        try {
            FileInputStream inputStream = new FileInputStream(srcFilePath);
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "Windows-1250");
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(dstFilePath));
            String line;
            boolean flag = false;

            while((line = bufferedReader.readLine()) != null) {

                if (line.contains("Użytkownik ")) {
                    flag = !flag;
                    continue;
                }

                if (flag) {
                    bufferedWriter.write(line);
                    bufferedWriter.newLine();
                }
            }

            inputStream.close();
            inputStreamReader.close();
            bufferedReader.close();
            bufferedWriter.close();

        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }


    public String extractFileNameFromPoLog(String srcFilePath) {
        try {
            FileInputStream inputStream = new FileInputStream(srcFilePath);
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "Windows-1250");
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

            String line;
            String fileName ="";

            while((line = bufferedReader.readLine()) != null) {
                if (line.contains("Badanie pliku CSV")) {
                    fileName = outputFileNameCreator(line);

                    inputStream.close();
                    inputStreamReader.close();
                    bufferedReader.close();

                    return fileName;
                }
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return null;
    }


    public String createFileNameFromSrtLog (String srcFilePath, int counter) {
        int lastIndexOfSlash = srcFilePath.lastIndexOf("\\");
        int lastIndexOfUnderscore = srcFilePath.lastIndexOf("_");
        String fileName = srcFilePath.substring(lastIndexOfSlash + 1, lastIndexOfUnderscore + 1) + "log" + counter + ".txt";

        return fileName;
    }


    public String outputFileNameCreator(String lineWithFileName) {
        Pattern pattern = Pattern.compile("[a-zA-Z]+_[0-9]+_[0-9]+");
        Matcher matcher = pattern.matcher(lineWithFileName);
        if (matcher.find())
        {
            return matcher.group() + "_log.txt";
        }
        return null;
    }

}
