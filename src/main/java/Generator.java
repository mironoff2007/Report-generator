import java.util.List;

/**
 * Created by Jura on 30.09.2018.
 */
public class Generator {

    List<Column> colList;
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
        System.out.println(totalWidth);

    }


}
