package Control;
import java.nio.file.Path;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.List;

import Ultility.Ulti;
import Ultility.Word;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;

public class Dict {
    
    private final Ulti dict = new Ulti();
    
    
    public Ulti getDict(){
        return dict;
    }

    public void insert(){
        try {
            Path path = Path.of("Dictionary.txt");
            Files.readAllLines(path);
            List<String> dictionary = Files.readAllLines(path);
            for(String line : dictionary)
            {   
                String input = "";
                String output = "";
                String[] split = line.split("\t");
                input += split[0];
                output += split[1] + "\n";
                for(int i = 2; i < split.length; i++)
                {
                    output += "\n" + " - " + split[i];
                }
               dict.addWord(new Word(input, output)); 
            }
        } catch (IOException e) {System.out.print("Toác");}
    }

    public void add(String input, String output){
        try {
            BufferedWriter file = new BufferedWriter(
                new OutputStreamWriter(
                new FileOutputStream("Dictionary.txt", true), StandardCharsets.UTF_8));

            file.append(input + "\t" + output);
            file.flush();
            file.close();
        } catch (IOException e) {}
    }
    
    public void remove(String input, String output) {

        
        
        try {
            File inputFile = new File("Dictionary.txt");
            File tempFile = new File("TempFile.txt");

            BufferedReader reader = new BufferedReader(new FileReader(inputFile));
            BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile));

            //String lineToRemove = input + "\t" + output;
            String currentLine;

            while((currentLine = reader.readLine()) != null) {
                String[] split = currentLine.split("\t");
                if(split[0].toLowerCase().equals(input.toLowerCase())) {
                    continue;
                }
                writer.append(currentLine + "\n");
            }
            dict.removeWord(input, output);
            writer.close(); 
            reader.close();

            inputFile.delete();
            tempFile.renameTo(inputFile);
            
        } catch (IOException e) {System.out.print("Toác");}
    }

    public void edit (String input, String output, String newouput) {
        remove(input, output);
        add(input, newouput + "\n");
        
    }
}
