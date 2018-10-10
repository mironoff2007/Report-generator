import java.util.ArrayList;

public class Generator {

    private ArrayList<Column> colList;
    private Page page;
    private XMLParser parser;


    public Generator(String settingsName, String sourceName,String reportName)
    {
        String workingDir = System.getProperty("user.dir");
        //workingDir="D:\\";
        try {
            parser = new XMLParser();
            parser.start(workingDir+"\\"+settingsName);
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
        data.readSourceData( colList,workingDir+"\\"+sourceName);
        ReportWriter reportWriter = new ReportWriter(page.height,page.width,reportName);
        reportWriter.setPath(workingDir);
        reportWriter.writeReport(colList,data.getColumns());

    }


}
