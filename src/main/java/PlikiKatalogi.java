import java.io.*;

public class PlikiKatalogi {
    public static void main(String[] args) {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter("baza.txt"));
            BufferedReader czytacz = new BufferedReader(new FileReader("pytania do Ady.txt"));

            String tresc = "";
            while ((tresc = czytacz.readLine()) != null){
                writer.write(tresc);
                writer.newLine();
            }
            czytacz.close();
            writer.close();
//            File mojPlik = new File("mojPlik.txt");
//
//            if (!mojPlik.exists())
//                mojPlik.createNewFile();
//            BufferedWriter bwriter = new BufferedWriter(new FileWriter("mojPlik.txt", true));
//            bwriter.write("hello");
//            bwriter.newLine();
//            bwriter.close();
//            Writer writer = new BufferedWriter(new FileWriter(mojPlik));
//
//            writer.write("wesoły napis");
//            ((BufferedWriter)writer).newLine();
//            ((BufferedWriter)writer).write("nowa linia");
//
//            writer.close();
            //System.out.println(mojPlik.getName());
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }

    }
}
