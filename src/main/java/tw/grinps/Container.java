package tw.grinps;

public interface Container {

    <T> T getComponent(Class<T> interfaceType);

    void registerComponent(Class<?> interfaceType, Class<?> instanceType);

    void registerComponent(Class<?> instanceType);
}
