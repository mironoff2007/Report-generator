import java.util.ArrayList;

/**
 * Created by Юрий on 01.10.2018.
 */
public class ReportWriter {
    String line;
    String cell;
    StringBuilder stringBuild=new StringBuilder();
    StringBuilder cellStBuild=new StringBuilder();

    int pageWidth;
    int pageHeight;

    int columnWidth;
    int lineLength;

    int cLines;

    boolean addRow;
    ArrayList< ArrayList<String>> addLine= new ArrayList<>();
    ArrayList<String> titleList = new ArrayList<>();

    public void writeReport( ArrayList<Column> colList,ArrayList<ArrayList<String>> writeList){

        int rows=writeList.get(0).size();
        int columns=writeList.size();

        for(int r=0;r<rows;r++)
        {

            for(int c=0;c<columns;c++)
            {
                stringBuild.append("| ");
                cellStBuild.append("| ");
                lineLength=lineLength+2;
                cell=writeList.get(c).get(r);
                columnWidth=colList.get(c).width;


                stringBuild.append(cell+" ");

                lineLength=lineLength+colList.get(c).width;
                while(stringBuild.length()<lineLength){
                    stringBuild.append(" ");

                }
            }
            stringBuild.append("|");
            System.out.println(stringBuild.toString());


            stringBuild.setLength(0);
            lineLength=0;
        }
    }
}
