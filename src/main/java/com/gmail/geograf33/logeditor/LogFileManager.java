package com.gmail.geograf33.logeditor;

import com.opencsv.CSVWriter;

import java.io.*;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.TimeZone;
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

            String headers[] = new String[20];
            String line1[] = new String[20];
            prepareMapOfDataForCsvFile(new LinkedHashMap<>(), new LinkedHashMap<>()).keySet().toArray(headers);
            prepareMapOfDataForCsvFile(new LinkedHashMap<>(), new LinkedHashMap<>()).values().toArray(line1);

            FileOutputStream fileOutputStream = new FileOutputStream(createCsvFilePathFromDstFilePath(dstFilePath));
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(fileOutputStream, "Windows-1250");
            CSVWriter csvWriter = new CSVWriter(outputStreamWriter);
            csvWriter.writeNext(headers);
            csvWriter.writeNext(line1);

            csvWriter.close();
            outputStreamWriter.close();
            fileOutputStream.close();

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


    public String createCsvFilePathFromDstFilePath(String dstFilePath) {
        String csvFilePath = dstFilePath.substring(0, dstFilePath.lastIndexOf(".")) + "_statystyka.csv";
        return csvFilePath;
    }


    public String getNameOfUserWhoRunScript(String line) {
        int firstIndex = line.indexOf(" ");
        int secondIndex = line.indexOf(" ", firstIndex + 1);
        return line.substring(firstIndex + 1, secondIndex);
    }


    public String getNameOfUserWhoCreatedFile (String srcFilePath){
        return srcFilePath.substring(srcFilePath.lastIndexOf("_") + 1, srcFilePath.lastIndexOf(".") - 20);
    }


    public int getNumberOfErrors(String line) {
        return Integer.parseInt(line.substring(line.lastIndexOf(":") + 2));
    }


    public long getSizeOfSqlFile (String srcFilePath){
        String sqlFilePath = srcFilePath.substring(0, srcFilePath.lastIndexOf(".")) + ".sql";
        return new File(sqlFilePath).length();
    }


    public String getDurationOfImport (String importStartDateTime, String srcFilePath) {
//        parsing String to LocalDateTime
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");
        LocalDateTime startDateTime = LocalDateTime.parse(importStartDateTime, dateTimeFormatter);
//          parsing files' lastModified date to LocalDateTime
        long importEndDateTime = new File (srcFilePath).lastModified();
        LocalDateTime endDateTime = LocalDateTime.ofInstant(Instant.ofEpochMilli(importEndDateTime), TimeZone.getDefault().toZoneId());
//          counting duration of import and formatting it to desired format
        Duration importDuration = Duration.between(startDateTime, endDateTime);
        String formattedDuration = importDuration.toString().replace("PT", "")
                .replace("H", ":").replace("M", ":")
                .replace("S", "");
        return formattedDuration;
    }


    public String outputPoFileNameCreator(String lineWithFileName) {
        Pattern pattern = Pattern.compile("[a-zA-Z]+_[0-9]+_[0-9]+");
        Matcher matcher = pattern.matcher(lineWithFileName);
        if (matcher.find())
        {
            return matcher.group() + "_log.txt";
        }
        return null;
    }


    public Map<String, String> prepareMapOfDataForCsvFile(Map<String, String> dataMap, Map<String, Integer> errorsMap) {

        errorsMap.entrySet().forEach(element -> dataMap.put(element.getKey(), String.valueOf(element.getValue())));

        return dataMap;
    }

}
