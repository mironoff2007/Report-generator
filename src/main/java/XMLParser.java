import java.io.FileInputStream;
import java.util.ArrayList;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class XMLParser {

    private SAXHandler handler;

    public ArrayList<Column> getColumnList() {
        return columnList;
    }

    public Page getPage() {
        return page;
    }

    ArrayList<Column> columnList;
    Page page;
    public void start(String path) throws Exception {
        SAXParserFactory parserFactor = SAXParserFactory.newInstance();
        SAXParser parser = parserFactor.newSAXParser();
        SAXHandler handler = new SAXHandler();
        try {
            parser.parse(new FileInputStream(path),
                    handler);
        }
        catch (IllegalArgumentException e){
            System.out.println("settings file not found");
            e.printStackTrace();}
        page=handler.page;
        columnList=handler.colList;

    }


}
/**
 * The Handler for SAX Events.
 */
class SAXHandler extends DefaultHandler {

    ArrayList<Column> colList = new ArrayList<>();

    boolean isPage=false;
    Column col = null;
    Page page = null;
    String content = null;
    @Override
    //Triggered when the start of tag is found.
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        switch(qName)
        {
            //Create a new column object when the start tag is found
            case "column":
                col = new Column();
                isPage=false;
                break;
            //Create a new page object when the start tag is found
            case "page":
                page = new Page();
                isPage=true;
                break;
        }
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        switch(qName){
            //Add to list once end tag is found
            case "column":
                colList.add(col);
                break;
            case "page":

                break;
            //For all other end tags
            case "width":
                if(isPage){page.width=Integer.parseInt(content);}
                else{col.width = Integer.parseInt(content);}
                break;
            case "height":
                page.height=Integer.parseInt(content);
                break;
            case "title":
                col.title=content;
                break;


        }
    }

    @Override
    public void characters(char[] ch, int start, int length)
            throws SAXException {
        content = String.copyValueOf(ch, start, length).trim();
    }

}

