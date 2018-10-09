import java.util.ArrayList;

/**
 * Created by Юрий on 01.10.2018.
 */
public class ReportWriter {

    String cell;
    StringBuilder stringBuild=new StringBuilder();
    StringBuilder cellStBuild=new StringBuilder();
    StringBuilder cellEnd=new StringBuilder();

    int pageWidth;
    int pageHeight;

    int columnWidth;
    int lineLength;

    int linesCount;

    public ReportWriter(int height,int width){
        this.pageHeight=height;
        this.pageWidth=width;
    }

    ArrayList<ArrayList<StringBuilder>> rowsList= new ArrayList<>();
    ArrayList<StringBuilder> row= new ArrayList<>();
    ArrayList<StringBuilder> addRow= new ArrayList<>();

    public void writeReport( ArrayList<Column> colList,ArrayList<ArrayList<String>> writeList){

        int rows=writeList.get(0).size();
        int columns=writeList.size();
        linesCount=0;
        for(int r=0;r<rows;r++)
        {


            for(int c=0;c<columns;c++) { row.add(new StringBuilder(writeList.get(c).get(r))); }
            boolean splitted=false;

            while(!splitted){
                for(int c=0;c<columns;c++) {
                    cell = row.get(c).toString();
                    columnWidth = colList.get(c).width;
                    if (cell.length() > columnWidth) {
                        String []splt=cell.split("[^а-яА-Яa-zA-Z0-9]");//
                        char simbol=' ';
                        //
                        int freeChars;
                        for(int i=0;i<splt.length;i++){
                            cellStBuild=new StringBuilder(splt[i]);
                                //get delimiter
                                if (i > 0) {
                                    simbol = cell.charAt(cell.indexOf(cellStBuild.toString()) - 1);
                                    cellStBuild.insert(0, simbol);
                                }
                                if(i<2) {
                                    if ((stringBuild.length() + 1) == columnWidth) {
                                        stringBuild.append(simbol);
                                        cellStBuild.delete(0, 1);
                                    }
                                    //next word fits cell or not without delimiter
                                    if ((stringBuild.length() + cellStBuild.length()) <= columnWidth) {
                                        if (cellStBuild.length() <= columnWidth) {
                                            stringBuild.append(cellStBuild);
                                        }
                                    } else {//next word do not fits cell
                                        //next word fits next cell or not
                                        if (cellStBuild.length() <= columnWidth) {
                                            if (cellStBuild.length() != 0 && cellStBuild.charAt(0) == ' ') {
                                                cellStBuild.delete(0, 1);
                                            }
                                            cellEnd.append(cellStBuild);
                                        } else {

                                            //free space at next cell
                                            freeChars = (columnWidth - stringBuild.length()) > 0 ? (columnWidth - stringBuild.length()) : 0;
                                            freeChars = freeChars < 3 ? 0 : freeChars;
                                            if (freeChars == 0 && simbol == ' ') {
                                                cellStBuild.delete(0, 1);
                                            }
                                            //write to cell chars that fits
                                            stringBuild.append(cellStBuild.substring(0, freeChars));
                                            //write remaining chars for next iteration
                                            cellEnd.append(cellStBuild.substring(freeChars, cellStBuild.length()));

                                        }
                                    }
                                    //
                                }
                                else{
                                    cellEnd.append(cellStBuild);
                                }
                        }
                        addRow.add(new StringBuilder(stringBuild));
                        row.set(c,new StringBuilder(cellEnd));
                        stringBuild.setLength(0);
                        cellEnd.setLength(0);

                    }
                    else {
                        addRow.add(new StringBuilder(cell));
                        row.set(c,new StringBuilder());
                    }
                }

                //count
                int emptyCells=0;
                for(int c=0;c<columns;c++) {
                    if(row.get(c).length()==0){emptyCells++;}
                }
                if(emptyCells==row.size()){splitted=true;}
                rowsList.add(new ArrayList<>(addRow));
                addRow.clear();
            }


            addRow.clear();
            row.clear();
            stringBuild.setLength(0);

            //
            for(int rNumber=0;rNumber<rowsList.size();rNumber++)
            {
                row=rowsList.get(rNumber);
                lineLength=0;
                for(int cellNumb=0;cellNumb<row.size();cellNumb++)
                {
                    stringBuild.append("| ");
                    lineLength=lineLength+2;
                    cell=row.get(cellNumb).toString();
                    columnWidth=colList.get(cellNumb).width;
                    stringBuild.append(cell);

                    lineLength=lineLength+colList.get(cellNumb).width;
                    while(stringBuild.length()<lineLength){
                        stringBuild.append(" ");
                    }
                    stringBuild.append(" ");
                    lineLength=lineLength+1;
                }
                stringBuild.append("|");




                for (int i=stringBuild.length();i<pageWidth;i++){
                    stringBuild.append(" ");
                }

                if(linesCount==pageHeight){
                    System.out.println("~");

                    linesCount=0;
                    cellEnd.append("|");
                    lineLength=0;
                    //Add title to StringBuilder
                    for(int c =0;c<colList.size();c++) {
                        cellEnd.append(" ");
                        lineLength++;
                        cellEnd.append(colList.get(c).title);
                        lineLength = lineLength+colList.get(c).width;

                        for (int s = cellEnd.length(); s <=lineLength; s++) {
                            cellEnd.append(" ");
                        }
                        cellEnd.append(" |");
                        lineLength=lineLength+2;
                    }
                    //page width

                    System.out.println(cellEnd.toString());
                    cellEnd.setLength(0);
                    //row delimiter
                    for(int i=0;i<=lineLength;i++){
                        cellEnd.append("-");
                    }
                    for (int i=cellEnd.length();i<pageWidth;i++){
                        cellEnd.append(" ");
                    }
                    System.out.println(cellEnd.toString());
                    linesCount++;
                    cellEnd.setLength(0);

                    System.out.println(stringBuild.toString());
                    linesCount++;

                }
                else {
                    System.out.println(stringBuild.toString());
                    stringBuild.setLength(0);
                    linesCount++;
                }


                stringBuild.setLength(0);

            }

            //row delimiter
            for(int i=0;i<=lineLength;i++){
                stringBuild.append("-");
            }
            System.out.println(stringBuild.toString());
            linesCount++;

            rowsList.clear();
            row.clear();
            stringBuild.setLength(0);

        }
    }

}
