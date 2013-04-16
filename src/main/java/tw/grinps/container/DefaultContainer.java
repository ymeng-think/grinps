package tw.grinps.container;

import tw.grinps.Container;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

public class DefaultContainer implements Container {

    private Map<Class<?>, Object> instancePool = new HashMap<Class<?>, Object>();

    @Override
    public void registerComponent(Class<?> interfaceType, Class<?> instanceType) {
        this.instancePool.put(interfaceType, createInstance(instanceType));
    }

    @Override
    public void registerComponent(Class<?> instanceType) {
        registerComponent(instanceType, instanceType);
    }

    @Override
    public <T> T getComponent(Class<T> interfaceType) {
        return (T)this.instancePool.get(interfaceType);
    }

    private Object createInstance(Class<?> instanceType) {
        Constructor<?>[] constructors = instanceType.getConstructors();
        for (Constructor<?> constructor : constructors) {
            if (!canCreate(constructor)) {
                continue;
            }

            return createInstance(constructor);
        }

        return null;
    }

    private boolean canCreate(Constructor<?> constructor) {
        Class<?>[] parameterTypes = constructor.getParameterTypes();
        for (Class<?> parameterType : parameterTypes) {
            if (!hasComponent(parameterType)){
                return false;
            }
        }
        return true;
    }

    private boolean hasComponent(Class<?> interfaceType) {
        return this.instancePool.containsKey(interfaceType);
    }

    private Object createInstance(Constructor<?> constructor) {
        try {
            return constructor.newInstance(getConstructorParameterValues(constructor));
        } catch (InstantiationException e) {
            throw new CouldNotInitializeInstanceException();
        } catch (IllegalAccessException e) {
            throw new CouldNotInitializeInstanceException();
        } catch (InvocationTargetException e) {
            throw new CouldNotInitializeInstanceException();
        }
    }

    private Object[] getConstructorParameterValues(Constructor<?> constructor) {
        Class<?>[] parameterTypes = constructor.getParameterTypes();
        Object[] parameters = new Object[parameterTypes.length];

        for (int i = 0; i < parameterTypes.length; i++){
            parameters[i] = getComponent(parameterTypes[i]);
        }

        return parameters;
    }
}
