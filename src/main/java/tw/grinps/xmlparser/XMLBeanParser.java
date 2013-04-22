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
        try {
            Document doc = getDocumentFromXML(fileName);
            return findBeans(doc);
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        }
        return null;
    }

    private List<Bean> findBeans(Document doc) {
        List<Bean> beans = Lists.newArrayList();
        Element bean = doc.getDocumentElement();
        NodeList childNodes = bean.getChildNodes();
        for (int i = 0; i < childNodes.getLength(); i++){
            Node item = childNodes.item(i);
            if (isABean(item)){
                String className = getAttributesTextByName(item, "class");
                String id = getAttributesTextByName(item, "id");
                beans.add(new Bean(className, id));
            }
        }
        return beans;
    }

    private String getAttributesTextByName(Node item, String attributeName) {
        return item.getAttributes().getNamedItem(attributeName).getTextContent();
    }

    private boolean isABean(Node item) {
        return item.getNodeName().equals("bean");
    }

    private Document getDocumentFromXML(String fileName) throws IOException, ParserConfigurationException, SAXException {
        URL resource = Resources.getResource(fileName);
        InputStream inputStream = resource.openStream();
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = dbf.newDocumentBuilder();
        return builder.parse(inputStream);
    }
}
