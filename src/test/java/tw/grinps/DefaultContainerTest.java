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
        container.registerComponent(MovieFinder.class, ColonMovieFinder.class);

        MovieFinder finder = container.getComponent(MovieFinder.class);

        assertTrue(finder instanceof ColonMovieFinder);
    }

    @Test
    public void should_fetch_correct_component_from_container_that_contains_multi_components() {
        container.registerComponent(MovieFinder.class, ColonMovieFinder.class);
        container.registerComponent(MusicFinder.class, XmlMusicFinder.class);

        MovieFinder finder = container.getComponent(MovieFinder.class);

        assertTrue(finder instanceof ColonMovieFinder);
    }

    @Test
    public void should_fetch_bean_that_injected_component_in_constructor() {
        container.registerComponent(MovieFinder.class, ColonMovieFinder.class);
        container.registerComponent(MovieLister.class);

        MovieLister lister = container.getComponent(MovieLister.class);

        assertNotNull(lister);
    }

    @Test
    public void should_fetch_bean_that_injected_component_in_setter() {
        container.registerComponent(MusicFinder.class, XmlMusicFinder.class);
        container.registerComponent(MusicLister.class);

        MusicLister lister = container.getComponent(MusicLister.class);

        assertNotNull(lister);
        assertNotNull(lister.getFinder());
    }

}
