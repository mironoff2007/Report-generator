import java.io.*;
import java.util.ArrayList;

public class SourceData {

    ArrayList<ArrayList<String>> columns;

    public ArrayList<ArrayList<String>> getColumns() {
        return columns;
    }

    String[] split;

    public void readSourceData(){
        String line;
        columns=new ArrayList<>();
        try {
            BufferedReader buf = new BufferedReader(new InputStreamReader(new FileInputStream("task/source-data.tsv"),"UTF-16"));
            line=buf.readLine();
            split=line.split("\t");
            for(int i=0;i<split.length;i++){
                columns.add(new ArrayList<String>());
                columns.get(i).add(split[i]);
            }
            while((line=buf.readLine())!=null){

                split=line.split("\t");
                for(int i=0;i<split.length;i++){
                    columns.get(i).add(split[i]);
                }
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        finally {

        }
    }
}
