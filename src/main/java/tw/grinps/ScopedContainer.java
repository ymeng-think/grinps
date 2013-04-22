package tw.grinps;

import tw.grinps.container.DefaultContainer;

public interface ScopedContainer {

    void addChild(ScopedContainer container);
}
