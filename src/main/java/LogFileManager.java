import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class LogFileManager {

    public void readAndWrite(String srcFilePath, String dstFilePath){
        try {
//            BufferedReader myReader = new BufferedReader(new FileReader(srcFilePath));
            FileInputStream input = new FileInputStream(srcFilePath);
            InputStreamReader reader = new InputStreamReader(input, "Windows-1250");
            BufferedReader myReader = new BufferedReader(reader);
            BufferedWriter myWriter = new BufferedWriter(new FileWriter(dstFilePath));
//            StringBuilder fileContent = new StringBuilder();
            String line;
            boolean flag = false;


            while((line = myReader.readLine()) != null) {
//                String lineUTF8 = new String(line.getBytes("CP1250"));
//                System.out.println(lineUTF8);
//                System.out.println(line);
                if (line.contains("Użytkownik ")) {
                    flag = !flag;
                    continue;
                }

                if (flag) {
                    myWriter.write(line);
                    myWriter.newLine();
//                    fileContent.append(line);
//                    fileContent.append(System.lineSeparator());
                }
            }

            myReader.close();
            myWriter.close();

//            System.out.println(fileContent.toString());

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
