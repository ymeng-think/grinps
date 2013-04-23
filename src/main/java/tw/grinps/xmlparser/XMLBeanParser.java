package tw.grinps.xmlparser;


import com.google.common.collect.Lists;
import com.google.common.io.Resources;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
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
            if (isANodeWith(item, "bean")){
                String className = getAttributesTextByName(item, "class");
                String id = getAttributesTextByName(item, "id");
                Bean newBean = new Bean(className, id);

                newBean.setArguments(setBeanConstructorArguments(item));

                beans.add(newBean);
            }
        }
        return beans;
    }

    private List<Bean> setBeanConstructorArguments(Node item) {
        List<Bean> beans = Lists.newArrayList();
        if(item.hasChildNodes()){
            NodeList childs = item.getChildNodes();
            for (int j = 0; j < childs.getLength(); j++){
                Node childItem = childs.item(j);
                if (isANodeWith(childItem, "constructor-arg")){
                    NodeList refs = childItem.getChildNodes();
                    for (int k = 0; k < refs.getLength(); k++){
                        Node ref = refs.item(k);
                        if (isANodeWith(ref, "ref")){
                            beans.add(new Bean(getAttributesTextByName(ref, "bean")));
                        }
                    }
                }
            }
        }
        return beans;
    }

    private String getAttributesTextByName(Node item, String attributeName) {
        return item.getAttributes().getNamedItem(attributeName).getTextContent();
    }

    private boolean isANodeWith(Node item, String nodeName) {
        return item.getNodeName().equals(nodeName);
    }

    private Document getDocumentFromXML(String fileName) throws IOException, ParserConfigurationException, SAXException {
        URL resource = Resources.getResource(fileName);
        InputStream inputStream = resource.openStream();
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = dbf.newDocumentBuilder();
        return builder.parse(inputStream);
    }
}
