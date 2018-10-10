import java.io.*;
import java.util.ArrayList;


public class ReportWriter {

    private String path="D:/";
    private String reportName="report.txt";
    private String cell;
    private StringBuilder stringBuild=new StringBuilder();
    private StringBuilder cellStBuild=new StringBuilder();
    private StringBuilder cellEnd=new StringBuilder();

    private int pageWidth;
    private int pageHeight;

    private int columnWidth;
    private int lineLength;

    private int linesCount;



    public ReportWriter(int height,int width,String reportName){
        this.pageHeight=height;
        this.pageWidth=width;
        this.reportName=reportName;
        }

    public void setPath(String path) {
        this.path = path;
    }

    private ArrayList<ArrayList<StringBuilder>> rowsList= new ArrayList<>();
    private ArrayList<StringBuilder> row= new ArrayList<>();
    private  ArrayList<StringBuilder> addRow= new ArrayList<>();

    public void writeReport( ArrayList<Column> colList,ArrayList<ArrayList<String>> writeList){
        try {
            Writer fileWriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(path+"\\"+reportName), "UTF-16"));

            int rows=writeList.get(0).size();
            int columns=writeList.size();
            linesCount=0;
            for(int r=0;r<rows;r++)
            {


                for (ArrayList<String> aWriteList : writeList) {
                    row.add(new StringBuilder(aWriteList.get(r)));
                }
                boolean isSplitted=false;

                while(!isSplitted){
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
                                    //next word fits cell or not withfileWriter delimiter
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
                    if(emptyCells==row.size()){isSplitted=true;}
                    rowsList.add(new ArrayList<>(addRow));
                    addRow.clear();
                }


                addRow.clear();
                row.clear();
                stringBuild.setLength(0);

                //
                for (ArrayList<StringBuilder> aRowsList : rowsList) {
                    row = aRowsList;
                    lineLength = 0;
                    for (int cellNumb = 0; cellNumb < row.size(); cellNumb++) {
                        stringBuild.append("| ");
                        lineLength = lineLength + 2;
                        cell = row.get(cellNumb).toString();
                        columnWidth = colList.get(cellNumb).width;
                        stringBuild.append(cell);

                        lineLength = lineLength + colList.get(cellNumb).width;
                        while (stringBuild.length() < lineLength) {
                            stringBuild.append(" ");
                        }
                        stringBuild.append(" ");
                        lineLength = lineLength + 1;
                    }
                    stringBuild.append("|");


                    for (int i = stringBuild.length(); i < pageWidth; i++) {
                        stringBuild.append(" ");
                    }

                    if (linesCount == pageHeight) {
                        fileWriter.write("~");
                        fileWriter.write("\n");

                        linesCount = 0;
                        cellEnd.append("|");
                        lineLength = 0;
                        //Add title to StringBuilder
                        for (Column aColList : colList) {
                            cellEnd.append(" ");
                            lineLength++;
                            cellEnd.append(aColList.title);
                            lineLength = lineLength + aColList.width;

                            for (int s = cellEnd.length(); s <= lineLength; s++) {
                                cellEnd.append(" ");
                            }
                            cellEnd.append(" |");
                            lineLength = lineLength + 2;
                        }
                        //page width
                        fillBySpaces(cellEnd);
                        fileWriter.write(cellEnd.toString());
                        fileWriter.write("\n");
                        cellEnd.setLength(0);
                        //row delimiter
                        for (int i = 0; i <= lineLength; i++) {
                            cellEnd.append("-");
                        }


                        fillBySpaces(cellEnd);
                        fileWriter.write(cellEnd.toString());
                        fileWriter.write("\n");
                        linesCount++;
                        cellEnd.setLength(0);

                        fillBySpaces(stringBuild);
                        fileWriter.write(stringBuild.toString());
                        fileWriter.write("\n");
                        linesCount++;

                    } else {
                        fillBySpaces(stringBuild);
                        fileWriter.write(stringBuild.toString());
                        fileWriter.write("\n");
                        stringBuild.setLength(0);
                        linesCount++;
                    }


                    stringBuild.setLength(0);

                }

                //row delimiter
                for(int i=0;i<=lineLength;i++){
                    stringBuild.append("-");
                }
                fillBySpaces(stringBuild);
                fileWriter.write(stringBuild.toString());
                fileWriter.write("\n");
                linesCount++;

                rowsList.clear();
                row.clear();
                stringBuild.setLength(0);
                fileWriter.flush();

            }



        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    public void fillBySpaces(StringBuilder strBuild)
    {
        for (int i = strBuild.length(); i < pageWidth; i++) {
            strBuild.append(" ");
        }
    }
}
