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
    ArrayList< ArrayList<StringBuilder>> rows= new ArrayList<>();
    ArrayList<StringBuilder> row= new ArrayList<>();

    ArrayList<String> titleList = new ArrayList<>();

    public void writeReport( ArrayList<Column> colList,ArrayList<ArrayList<String>> writeList){

        int rowsNumber=writeList.get(0).size();
        int columns=writeList.size();

        for(int r=0;r<rowsNumber;r++)
        {

            for(int c=0;c<columns;c++)
            {
                row.add(new StringBuilder(writeList.get(c).get(r)));
            }
            rows.add(row);
            int colNumb=0;
            for (ArrayList<StringBuilder>correntRow:rows ){
                for (StringBuilder currentCell: correntRow) {
                    stringBuild.append("| ");
                    cellStBuild.append("| ");
                    lineLength = lineLength + 2;
                    cell = currentCell.toString();
                    columnWidth = colList.get(colNumb).width;


                    stringBuild.append(cell + " ");

                    lineLength = lineLength + colList.get(colNumb).width;
                    while (stringBuild.length() < lineLength) {
                        stringBuild.append(" ");
                    }
                    colNumb++;
                }
                    stringBuild.append("|");
                    System.out.println(stringBuild.toString());


                    stringBuild.setLength(0);
                    lineLength=0;
            }
            rows=new ArrayList<>();
            row=new ArrayList<>();

        }
    }
}
