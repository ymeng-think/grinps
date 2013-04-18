package tw.grinps;

import org.junit.Before;
import org.junit.Test;
import tw.grinps.container.DefaultContainer;
import tw.sample.multimedia.*;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class DefaultContainerTest {

    private Container container;

    @Before
    public void setUp() throws Exception {
        container = new DefaultContainer();
    }

    @Test
    public void should_fetch_component_created_without_parameters_from_container() {
        container.registerBean(MovieFinder.class, ColonMovieFinder.class);

        MovieFinder finder = container.getBean(MovieFinder.class);

        assertTrue(finder instanceof ColonMovieFinder);
    }

    @Test
    public void should_fetch_correct_component_from_container_that_contains_multi_components() {
        container.registerBean(MovieFinder.class, ColonMovieFinder.class);
        container.registerBean(MusicFinder.class, XmlMusicFinder.class);

        MovieFinder finder = container.getBean(MovieFinder.class);

        assertTrue(finder instanceof ColonMovieFinder);
    }

    @Test
    public void should_fetch_bean_that_injected_component_in_constructor() {
        container.registerBean(MovieFinder.class, ColonMovieFinder.class);
        container.registerBean(MovieLister.class);

        MovieLister lister = container.getBean(MovieLister.class);

        assertNotNull(lister);
    }

    @Test
    public void should_fetch_bean_that_injected_component_in_setter() {
        container.registerBean(MusicFinder.class, XmlMusicFinder.class);
        container.registerBean(MusicLister.class);

        MusicLister lister = container.getBean(MusicLister.class);

        assertNotNull(lister);
        assertNotNull(lister.getFinder());
    }

}
