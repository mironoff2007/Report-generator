import java.util.ArrayList;

/**
 * Created by Jura on 30.09.2018.
 */
public class Generator {

    ArrayList<Column> colList;
    Page page;
    XMLParser parser;
    public Generator()
    {
        try {
            parser = new XMLParser();
            parser.start();
            page=parser.getPage();
            colList=parser.getColumnList();

        } catch (Exception e) {
            e.printStackTrace();
        }

        int totalWidth=0;
        for ( Column col : colList){
            totalWidth=col.width+totalWidth;
        }

        SourceData data=new SourceData();
        data.readSourceData( colList,"task/source-data.tsv");
        new ReportWriter(page.height,page.width).writeReport(colList,data.getColumns());

    }


}
