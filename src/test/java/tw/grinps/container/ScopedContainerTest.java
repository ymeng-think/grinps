package tw.grinps.container;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import tw.sample.multimedia.ColonMovieFinder;
import tw.sample.multimedia.MovieFinder;
import tw.sample.multimedia.MovieLister;

import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertTrue;

public class ScopedContainerTest {

    private DefaultContainer parentContainer;
    private DefaultContainer childContainer1;

    @Before
    public void setUp() throws Exception {
        parentContainer = new DefaultContainer();
        childContainer1 = new DefaultContainer();

        parentContainer.addChild(childContainer1);
    }

    @Test
    public void should_fetch_bean_from_parent_container() {
        parentContainer.registerBean(MovieFinder.class, ColonMovieFinder.class);

        MovieFinder finder = childContainer1.getSingletonBean(MovieFinder.class);

        assertNotNull(finder);
        assertTrue(childContainer1.hasBean(MovieFinder.class));
    }

}
