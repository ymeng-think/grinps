package tw.grinps;

public interface Container {

    <T> T getSingletonBean(Class<T> interfaceType);

    <T> T getNewBean(Class<T> interfaceType);

    void registerBean(Class<?> interfaceType, Class<?> instanceType);

    void registerBean(Class<?> instanceType);

    boolean hasBean(Class<?> interfaceType);
}
