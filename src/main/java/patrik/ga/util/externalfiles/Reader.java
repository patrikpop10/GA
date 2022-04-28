package patrik.ga.util.externalfiles;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class Reader {
     private BufferedReader reader;
     private String path;

     public Reader(String path){
          this.path = path;
     }

    public double[] read(){

        double[] values = new double[numberOfLines()];
        try{
            reader = new BufferedReader(new FileReader(path));

            String line = "";
            int count = 0;

            while((line = reader.readLine()) != null && count < values.length){
                values[count] = Double.parseDouble(line.trim());
                count++;
            }

            reader.close();
            return values;
        }
        catch(FileNotFoundException e){
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


        return null;
    }

    private int numberOfLines(){
        int lines = 0;
        try{
            reader = new BufferedReader(new FileReader(path));

            while (reader.readLine() != null) lines++;
            reader.close();
            return lines;
        }
        catch (FileNotFoundException e){
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return -1;
    }


}
