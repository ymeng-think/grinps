package tw.grinps.container;

import tw.grinps.BeanContainer;

import static tw.grinps.BeanFetchingType.Singleton;

class ParameterValuesFetcher {

    private BeanContainer container;

    public ParameterValuesFetcher(BeanContainer container) {
        this.container = container;
    }

    public Object[] fetch(Class<?>[] parameterTypes) {
        Object[] parameters = new Object[parameterTypes.length];

        for (int i = 0; i < parameterTypes.length; i++){
            parameters[i] = container.getBean(parameterTypes[i], Singleton);
        }

        return parameters;
    }
}
