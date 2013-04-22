package tw.grinps.container;

import tw.grinps.BeanContainer;
import tw.grinps.BeanFetchingType;
import tw.grinps.NotMatchedInterfaceException;
import tw.grinps.ScopedContainer;

import java.util.HashMap;
import java.util.Map;

import static tw.grinps.BeanFetchingType.New;
import static tw.grinps.BeanFetchingType.Singleton;

public class DefaultContainer implements BeanContainer, ScopedContainer {

    private Map<Class<?>, Object> instancePool = new HashMap<Class<?>, Object>();
    private DefaultContainer parent;

    @Override
    public DefaultContainer registerBean(Class<?> interfaceType, Class<?> instanceType) {
        if (!isDeclaredInterface(instanceType, interfaceType)) {
            throw new NotMatchedInterfaceException();
        }

        InstanceGenerator instanceGenerator = new InstanceGenerator(this);
        Object instance = instanceGenerator.generate(instanceType);

        SetterInjector injector = new SetterInjector(this);
        injector.inject(instance);

        this.instancePool.put(interfaceType, instance);

        return this;
    }

    @Override
    public DefaultContainer registerBean(Class<?> instanceType) {
        return registerBean(instanceType, instanceType);
    }

    @Override
    public <T> T getBean(Class<T> interfaceType, BeanFetchingType fetchingType) {
        if (fetchingType == Singleton) {
            return getSingletonBean(interfaceType);
        }

        if (fetchingType == New) {
            return getNewBean(interfaceType);
        }

        throw new IllegalArgumentException("Unknown bean fetching type");
    }

    @Override
    public boolean hasBean(Class<?> interfaceType) {
        if (hasBeanInCurrentScope(interfaceType)) {
            return true;
        }

        if (this.parent != null) {
            return this.parent.hasBean(interfaceType);
        }

        return false;
    }

    @Override
    public ScopedContainer addChild(ScopedContainer container) {
        ((DefaultContainer) container).setParent(this);
        return this;
    }

    private boolean hasBeanInCurrentScope(Class<?> interfaceType) {
        return this.instancePool.containsKey(interfaceType);
    }

    private <T> T getSingletonBeanFromCurrentScope(Class<T> interfaceType) {
        return (T) this.instancePool.get(interfaceType);
    }

    private static boolean isDeclaredInterface(Class<?> instanceType, Class<?> interfaceType) {
        return interfaceType.isAssignableFrom(instanceType);
    }

    private void setParent(DefaultContainer parent) {
        this.parent = parent;
    }

    private <T> T getSingletonBean(Class<T> interfaceType) {
        if (hasBeanInCurrentScope(interfaceType)) {
            return getSingletonBeanFromCurrentScope(interfaceType);
        }

        if (this.parent != null) {
            return this.parent.getSingletonBean(interfaceType);
        }

        return null;
    }

    private <T> T getNewBean(Class<T> interfaceType) {
        T singletonInstance = getSingletonBean(interfaceType);
        Class<?> instanceType = singletonInstance.getClass();

        InstanceGenerator instanceGenerator = new InstanceGenerator(this);
        return (T) instanceGenerator.generate(instanceType);
    }
}
