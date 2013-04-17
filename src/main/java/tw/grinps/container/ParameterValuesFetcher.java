package tw.grinps.container;

import tw.grinps.Container;

class ParameterValuesFetcher {

    private Container container;

    public ParameterValuesFetcher(Container container) {
        this.container = container;
    }

    public Object[] fetch(Class<?>[] parameterTypes) {
        Object[] parameters = new Object[parameterTypes.length];

        for (int i = 0; i < parameterTypes.length; i++){
            parameters[i] = container.getComponent(parameterTypes[i]);
        }

        return parameters;
    }
}
