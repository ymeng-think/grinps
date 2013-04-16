package tw.grinps.container;

import tw.grinps.Container;

import java.util.HashMap;
import java.util.Map;

public class DefaultContainer implements Container {

    private Map<Class<?>, Object> instancePool = new HashMap<Class<?>, Object>();

    @Override
    public void registerComponent(Class<?> interfaceType, Class<?> instanceType) {
        this.instancePool.put(interfaceType, createInstance(instanceType));
    }

    @Override
    public <T> T getComponent(Class<T> interfaceType) {
        return (T)this.instancePool.get(interfaceType);
    }

    private Object createInstance(Class<?> instanceType) {
        try {
            return instanceType.newInstance();
        } catch (InstantiationException e) {
            throw new CouldNotInitializeInstanceException("");
        } catch (IllegalAccessException e) {
            throw new CouldNotInitializeInstanceException("");
        }
    }
}
