package com.gmail.geograf33.logeditor;

import java.io.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LogFileManager {

    public void readAndWrite(String srcFilePath, String dstFilePath){
        try {
            FileInputStream inputStream = new FileInputStream(srcFilePath);
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "Windows-1250");
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(dstFilePath));
            String line;
            String fileName;
            boolean flag = false;
            boolean fileNameFlag = true;


            while((line = bufferedReader.readLine()) != null) {
                if (line.contains("Badanie pliku CSV") && fileNameFlag) {
                    fileName = fileNameCreator(line);
                    System.out.println(fileName);
                    fileNameFlag = false;
                }


                if (line.contains("Użytkownik ")) {
                    flag = true;
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


    public String fileNameCreator (String string) {
        Pattern pattern = Pattern.compile("[a-zA-Z]+_[0-9]+_[0-9]+");
        Matcher matcher = pattern.matcher(string);
        if (matcher.find())
        {
            return matcher.group();
        }
        return null;
    }

}
