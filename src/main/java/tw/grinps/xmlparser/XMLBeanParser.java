package tw.grinps.xmlparser;


import com.google.common.collect.Lists;
import com.google.common.io.Resources;
import org.w3c.dom.*;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.List;

public class XMLBeanParser {

    public List<Bean> getBeansFrom(String fileName) throws IOException {
        URL resource = Resources.getResource(fileName);
        InputStream inputStream = resource.openStream();
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        List<Bean> beans = Lists.newArrayList();
        try {
            DocumentBuilder builder = dbf.newDocumentBuilder();
            Document doc = builder.parse(inputStream);
            Element root = doc.getDocumentElement();
            NodeList childNodes = root.getChildNodes();
            for (int i = 0; i < childNodes.getLength(); i++){
                Node item = childNodes.item(i);
                if (item.getNodeName().equals("bean")){
                    NamedNodeMap attributes = item.getAttributes();
                    String className = attributes.getNamedItem("class").getTextContent();
                    String id = attributes.getNamedItem("id").getTextContent();
                    beans.add(new Bean(className, id));
                }
            }
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        }
        return beans;
    }
}
