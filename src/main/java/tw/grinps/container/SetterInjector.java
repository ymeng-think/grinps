package tw.grinps.container;

import tw.grinps.Container;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

class SetterInjector {

    private Container container;

    public SetterInjector(Container container) {
        this.container = container;
    }

    public void inject(Object obj) {
        Method[] methods = obj.getClass().getMethods();
        for (Method method : methods) {
            if (!isSetter(method)) {
                continue;
            }
            injectSetter(obj, method);
        }
    }

    private boolean isSetter(Method method) {
        return method.getName().startsWith("set");
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

    private Object[] getParameterValues(Class<?>[] parameterTypes) {
        Object[] parameters = new Object[parameterTypes.length];

        for (int i = 0; i < parameterTypes.length; i++){
            parameters[i] = container.getComponent(parameterTypes[i]);
        }

        return parameters;
    }
}
