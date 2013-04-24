package tw.grinps.xmlparser;

import com.google.common.collect.Lists;

import java.util.List;
import java.util.Map;

public class Bean {
    private String className;
    private String id;
    private List<Bean> arguments;
    private List<Map<String, String>> properties;

    public Bean(String className, String id) {
        this.className = className;
        this.id = id;
        this.arguments = Lists.newArrayList();
        this.properties = Lists.newArrayList();
    }

    public Bean(String id){
        this.id = id;
        this.className = null;
        this.arguments = Lists.newArrayList();
        this.properties = Lists.newArrayList();
    }

    public String getClassName() {
        return className;
    }

    public String getId() {
        return id;
    }

    public List<Bean> getArguments() {
        return arguments;
    }

    public void setArguments(List<Bean> arguments) {
        this.arguments = arguments;
    }

    public List<Map<String, String>> getProperties() {
        return this.properties;
    }

    public void setProperties(List<Map<String, String>> properties) {
        this.properties = properties;
    }
}
