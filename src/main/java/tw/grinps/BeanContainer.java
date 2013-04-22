package tw.grinps;

import tw.grinps.container.DefaultContainer;

public interface BeanContainer {

    <T> T getBean(Class<T> interfaceType, BeanFetchingType fetchingType);

    DefaultContainer registerBean(Class<?> interfaceType, Class<?> instanceType);

    DefaultContainer registerBean(Class<?> instanceType);

    boolean hasBean(Class<?> interfaceType);
}
