package tw.grinps.container;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import tw.sample.multimedia.ColonMovieFinder;
import tw.sample.multimedia.MovieFinder;
import tw.sample.multimedia.MovieLister;

import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertTrue;
import static org.junit.Assert.assertNull;
import static tw.grinps.BeanFetchingType.Singleton;

public class ScopedContainerTest {

    private DefaultContainer parentContainer;
    private DefaultContainer childContainer1;
    private DefaultContainer childContainer2;

    @Before
    public void setUp() throws Exception {
        parentContainer = new DefaultContainer();
        childContainer1 = new DefaultContainer();
        childContainer2 = new DefaultContainer();

        parentContainer.addChild(childContainer1)
                .addChild(childContainer2);
    }

    @Test
    public void should_fetch_bean_from_parent_container() {
        parentContainer.registerBean(MovieFinder.class, ColonMovieFinder.class);

        MovieFinder finder = childContainer1.getBean(MovieFinder.class, Singleton);

        assertTrue(childContainer1.hasBean(MovieFinder.class));
        assertNotNull(finder);
    }

    @Test
    public void should_fetch_bean_that_injected_component_in_constructor_which_is_registered_in_parent_scope() {
        parentContainer.registerBean(MovieFinder.class, ColonMovieFinder.class);
        childContainer1.registerBean(MovieLister.class);

        MovieLister lister = childContainer1.getBean(MovieLister.class, Singleton);

        Assert.assertNotNull(lister);
    }

    @Test
    public void should_NOT_fetch_bean_that_be_registered_in_brother_container() {
        childContainer1.registerBean(MovieFinder.class, ColonMovieFinder.class);

        MovieFinder finder = childContainer2.getBean(MovieFinder.class, Singleton);

        assertNull(finder);
    }

}
