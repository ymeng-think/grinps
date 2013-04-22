package tw.grinps.container;

import tw.grinps.BeanContainer;
import tw.grinps.CouldNotInitializeInstanceException;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

class InstanceGenerator {

    private BeanContainer container;

    public InstanceGenerator(BeanContainer container) {
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
            if (!container.hasBean(parameterType)){
                return false;
            }
        }
        return true;
    }

    private Object createInstance(Constructor<?> constructor) {
        Class<?>[] parameterTypes = constructor.getParameterTypes();
        ParameterValuesFetcher parameterValuesFetcher = new ParameterValuesFetcher(container);
        Object[] parameterValues = parameterValuesFetcher.fetch(parameterTypes);

        try {
            return constructor.newInstance(parameterValues);
        } catch (InstantiationException e) {
            throw new CouldNotInitializeInstanceException();
        } catch (IllegalAccessException e) {
            throw new CouldNotInitializeInstanceException();
        } catch (InvocationTargetException e) {
            throw new CouldNotInitializeInstanceException();
        }
    }

}
