package tw.grinps.xmlparser;


import com.google.common.base.Predicate;
import com.google.common.collect.Lists;
import com.google.common.io.Resources;
import com.sun.istack.internal.Nullable;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    private List<Bean> getSpecificNodes(NodeList nodeList, Predicate<Node> isASpecificNode){
        List<Bean> beans = Lists.newArrayList();
        for (int i = 0; i < nodeList.getLength(); i++){
            Node item = nodeList.item(i);
            if (item.hasChildNodes()){
                return getSpecificNodes(item.getChildNodes(), isASpecificNode);
            }

            if (isASpecificNode.apply(item)){
                beans.add(new Bean(getAttributesTextByName(item, "bean")));
            }
        }
        return beans;
    }

    private List<Bean> findBeans(Document doc) {
        List<Bean> beans = Lists.newArrayList();
        Element beansEle = doc.getDocumentElement();
        NodeList childNodes = beansEle.getChildNodes();

        for (int i = 0; i < childNodes.getLength(); i++){
            Node item = childNodes.item(i);
            if (isANodeWithName(item, "bean")){
                String className = getAttributesTextByName(item, "class");
                String id = getAttributesTextByName(item, "id");
                Bean newBean = new Bean(className, id);

                newBean.setArguments(getSpecificNodes(item.getChildNodes(), new Predicate<Node>() {
                    @Override
                    public boolean apply(@Nullable Node node) {
                        return isANodeWithName(node.getParentNode(), "constructor-arg") && isANodeWithName(node, "ref");
                    }
                }));
                newBean.setProperties(findProperties(item.getChildNodes()));

                beans.add(newBean);
            }
        }
        return beans;
    }

    private List<Map<String, String>> findProperties(NodeList nodes) {
        List<Map<String, String>> maps = Lists.newArrayList();
        for (int i = 0; i < nodes.getLength(); i++){
            Node item = nodes.item(i);
            if (isANodeWithName(item, "property")){
                HashMap<String, String> aMap = new HashMap<String, String>();
                aMap.put(getAttributesTextByName(item, "name"), getAttributesTextByName(item, "ref"));
                maps.add(aMap);
            }
        }
        return maps;
    }

    private String getAttributesTextByName(Node item, String attributeName) {
        return item.getAttributes().getNamedItem(attributeName).getTextContent();
    }

    private boolean isANodeWithName(Node item, String nodeName) {
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
