package tw.grinps.container;

import tw.grinps.Container;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

class InstanceGenerator {

    private Container container;

    public InstanceGenerator(Container container) {
        this.container = container;
    }

    public Object generate(Class<?> type) {
        Constructor<?>[] constructors = type.getConstructors();
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
            if (!container.hasComponent(parameterType)){
                return false;
            }
        }
        return true;
    }

    private Object createInstance(Constructor<?> constructor) {
        try {
            Class<?>[] parameterTypes = constructor.getParameterTypes();
            return constructor.newInstance(getParameterValues(parameterTypes));
        } catch (InstantiationException e) {
            throw new CouldNotInitializeInstanceException();
        } catch (IllegalAccessException e) {
            throw new CouldNotInitializeInstanceException();
        } catch (InvocationTargetException e) {
            throw new CouldNotInitializeInstanceException();
        }
    }

    private Object[] getParameterValues(Class<?>[] parameterTypes) {
        Object[] parameters = new Object[parameterTypes.length];

        for (int i = 0; i < parameterTypes.length; i++){
            parameters[i] = container.getComponent(parameterTypes[i]);
        }

        return parameters;
    }
}
