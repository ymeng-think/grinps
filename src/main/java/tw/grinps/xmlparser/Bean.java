package tw.grinps.xmlparser;

import com.google.common.collect.Lists;

import java.util.List;

public class Bean {
    private String className;
    private String id;
    private List<Bean> arguments;

    public Bean(String className, String id) {
        this.className = className;
        this.id = id;
        arguments = Lists.newArrayList();
    }

    public Bean(String id){
        this.id = id;
        this.className = null;
        this.arguments = Lists.newArrayList();
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
}
