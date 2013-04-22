package tw.grinps;

import tw.grinps.container.DefaultContainer;

public interface BeanContainer {

    <T> T getSingletonBean(Class<T> interfaceType);

    <T> T getNewBean(Class<T> interfaceType);

    DefaultContainer registerBean(Class<?> interfaceType, Class<?> instanceType);

    DefaultContainer registerBean(Class<?> instanceType);

    boolean hasBean(Class<?> interfaceType);
}
