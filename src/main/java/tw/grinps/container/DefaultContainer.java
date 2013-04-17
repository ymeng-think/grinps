package tw.grinps.container;

import tw.grinps.Container;

import java.util.HashMap;
import java.util.Map;

public class DefaultContainer implements Container {

    private Map<Class<?>, Object> instancePool = new HashMap<Class<?>, Object>();

    @Override
    public void registerComponent(Class<?> interfaceType, Class<?> instanceType) {
        InstanceGenerator instanceGenerator = new InstanceGenerator(this);
        Object instance = instanceGenerator.generate(instanceType);

        SetterInjector injector = new SetterInjector(this);
        injector.inject(instance);

        this.instancePool.put(interfaceType, instance);
    }

    @Override
    public void registerComponent(Class<?> instanceType) {
        registerComponent(instanceType, instanceType);
    }

    @Override
    public <T> T getComponent(Class<T> interfaceType) {
        return (T) this.instancePool.get(interfaceType);
    }

    @Override
    public boolean hasComponent(Class<?> interfaceType) {
        return this.instancePool.containsKey(interfaceType);
    }

}
