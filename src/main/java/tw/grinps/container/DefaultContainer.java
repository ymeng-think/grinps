package tw.grinps.container;

import tw.grinps.Container;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public class DefaultContainer implements Container {

    private Map<Class<?>, Object> instancePool = new HashMap<Class<?>, Object>();

    @Override
    public void registerComponent(Class<?> interfaceType, Class<?> instanceType) {
        InstanceGenerator instanceGenerator = new InstanceGenerator(this);
        this.instancePool.put(interfaceType, instanceGenerator.generate(instanceType));
    }

    @Override
    public void registerComponent(Class<?> instanceType) {
        registerComponent(instanceType, instanceType);
    }

    @Override
    public <T> T getComponent(Class<T> interfaceType) {
        T instance = (T) this.instancePool.get(interfaceType);
        injectSetter(instance);
        return instance;
    }

    @Override
    public boolean hasComponent(Class<?> interfaceType) {
        return this.instancePool.containsKey(interfaceType);
    }

    private <T> void injectSetter(T instance) {
        Method[] methods = instance.getClass().getMethods();
        for (Method method : methods) {
            if (!isSetter(method)) {
                continue;
            }
            injectSetter(instance, method);
        }
    }

    private <T> void injectSetter(T instance, Method method) {
        Class<?>[] parameterTypes = method.getParameterTypes();
        try {
            method.invoke(instance, getParameterValues(parameterTypes));
        } catch (IllegalAccessException e) {
            throw new CouldNotSetPropertyException();
        } catch (InvocationTargetException e) {
            throw new CouldNotSetPropertyException();
        }
    }

    private boolean isSetter(Method method) {
        return method.getName().startsWith("set");
    }

    private Object[] getParameterValues(Class<?>[] parameterTypes) {
        Object[] parameters = new Object[parameterTypes.length];

        for (int i = 0; i < parameterTypes.length; i++){
            parameters[i] = getComponent(parameterTypes[i]);
        }

        return parameters;
    }
}
