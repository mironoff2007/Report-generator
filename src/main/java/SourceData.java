import java.io.*;
import java.util.ArrayList;

public class SourceData {

    ArrayList<ArrayList<String>> columns;

    public ArrayList<ArrayList<String>> getColumns() {
        return columns;
    }

    String[] split;

    public void readSourceData(ArrayList<Column> columnsList,String path){
        String line;
        columns=new ArrayList<>();
        try {
            BufferedReader buf = new BufferedReader(new InputStreamReader(new FileInputStream(path),"UTF-16"));


            for(int i=0;i<columnsList.size();i++){
                columns.add(new ArrayList<String>());
                columns.get(i).add(columnsList.get(i).title);
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
