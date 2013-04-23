package tw.grinps.container;

import tw.grinps.BeanContainer;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

class SetterInjector {

    private BeanContainer container;

    public SetterInjector(BeanContainer container) {
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
        ParameterValuesFetcher parameterValuesFetcher = new ParameterValuesFetcher(container);
        Object[] parameterValues = parameterValuesFetcher.fetch(parameterTypes);

        try {
            method.invoke(instance, parameterValues);
        } catch (IllegalAccessException e) {
            throw new CouldNotSetPropertyException();
        } catch (InvocationTargetException e) {
            throw new CouldNotSetPropertyException();
        }
    }

}
