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

    ArrayList<ArrayList<StringBuilder>> rowsList= new ArrayList<>();
    ArrayList<StringBuilder> row= new ArrayList<>();
    ArrayList<StringBuilder> addRow= new ArrayList<>();

    public void writeReport( ArrayList<Column> colList,ArrayList<ArrayList<String>> writeList){

        int rows=writeList.get(0).size();
        int columns=writeList.size();

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
                        boolean isSpace=false;
                        int freeChars;
                        for(int i=0;i<splt.length;i++){
                            cellStBuild=new StringBuilder(splt[i]);

                            if(i>0){simbol=cell.charAt(cell.indexOf(cellStBuild.toString())-1);
                                cellStBuild.insert(0,simbol);}

                            isSpace=simbol==' '?true:false;

                            if((cellStBuild.length()+stringBuild.length())<=columnWidth){
                                stringBuild.append(cellStBuild);
                            }
                            else{
                                if(cellStBuild.charAt(0)==' '&&(cellEnd.length()==0)) { cellStBuild.delete(0,1);;}
                                if(cellStBuild.length()<=columnWidth) {
                                    cellEnd.append(cellStBuild); }
                                else{
                                    freeChars=(columnWidth-stringBuild.length())>0?(columnWidth-stringBuild.length()):0;
                                    freeChars =  freeChars==1 ? 0 : freeChars;stringBuild.append(cellStBuild.substring(0,freeChars));
                                    cellEnd.append(cellStBuild.substring(freeChars,cellStBuild.length()));

                                }
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

                System.out.println(stringBuild.toString());


                stringBuild.setLength(0);
                lineLength=0;
            }
            rowsList.clear();
            row.clear();
            stringBuild.setLength(0);

        }
    }

}
