import java.util.ArrayList;

/**
 * Created by Юрий on 01.10.2018.
 */
public class ReportWriter {
    String line;
    String cell;
    StringBuilder stringBuild=new StringBuilder();
    StringBuilder cellStBuild=new StringBuilder();
    StringBuilder cellEnd=new StringBuilder();

    int pageWidth;
    int pageHeight;

    int columnWidth;
    int lineLength;

    int cLines;


    ArrayList< ArrayList<StringBuilder>> rows= new ArrayList<>();
    ArrayList<StringBuilder> row= new ArrayList<>();
    ArrayList<StringBuilder> addRow= new ArrayList<>();

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
            //split
            boolean splitted=false;
            while(!splitted)
            {
                int colNumb=0;
                for(int cellNumb=0;cellNumb<row.size();cellNumb++){
                StringBuilder currentCell=row.get(cellNumb);
                    int colW=colList.get(colNumb).width;
                    if(currentCell.length()>colW) {
                        int ind=0;
                        while (ind<currentCell.length() ) {
                            if(Character.isLetter(currentCell.charAt(ind)) && ind <colW){
                            cellStBuild.append(currentCell.charAt(ind));}
                            else{
                                if(!(currentCell.charAt(ind)==' ') ){
                                cellEnd.append(currentCell.charAt(ind));}
                            }
                            ind++;
                        }
                        addRow.add(new StringBuilder(cellStBuild));
                        cellStBuild.setLength(0);
                        //row set cell -cellStBuild
                        row.set(cellNumb,new StringBuilder(cellEnd));
                        cellEnd.setLength(0);

                    }
                    else{
                        addRow.add(new StringBuilder(currentCell.toString()));
                        //row set cell ""
                        row.set(cellNumb,new StringBuilder(""));
                    }
                    colNumb++;
                }
                rows.add(addRow);
                addRow=new ArrayList<>();
                int emptyStrings=0;
                for (StringBuilder currentCell: row){
                    if(currentCell.length()==0){emptyStrings++;}
                }
                if (emptyStrings==row.size()){splitted=true;}
            }
            //


            for (ArrayList<StringBuilder>correntRow:rows )
            {
                int colNumb=0;
                for (StringBuilder currentCell: correntRow) {
                    stringBuild.append("| ");
                    lineLength = lineLength + 2;
                    cell = currentCell.toString();
                    columnWidth = colList.get(colNumb).width;


                    stringBuild.append(cell + " ");

                    lineLength = lineLength+1 + colList.get(colNumb).width;
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
            rows.clear();
            row.clear();

        }
    }
}
