package tw.grinps;

public interface Container {

    <T> T getBean(Class<T> interfaceType);

    void registerBean(Class<?> interfaceType, Class<?> instanceType);

    void registerBean(Class<?> instanceType);

    boolean hasBean(Class<?> interfaceType);
}
