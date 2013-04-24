package tw.grinps.container;

import org.junit.Before;
import org.junit.Test;
import tw.grinps.BeanContainer;
import tw.sample.multimedia.*;

import static org.junit.Assert.*;
import static tw.grinps.BeanFetchingType.New;
import static tw.grinps.BeanFetchingType.Singleton;

public class BeanContainerTest {

    private BeanContainer container;

    @Before
    public void setUp() throws Exception {
        container = new DefaultContainer();
    }

    @Test
    public void should_fetch_bean_created_without_parameters_from_container() {
        container.registerBean(MovieFinder.class, ColonMovieFinder.class);

        MovieFinder finder = container.getBean(MovieFinder.class, Singleton);

        assertTrue(finder instanceof ColonMovieFinder);
    }

    @Test
    public void should_fetch_bean_from_container_that_contains_multi_components() {
        container.registerBean(MovieFinder.class, ColonMovieFinder.class)
                 .registerBean(MusicFinder.class, XmlMusicFinder.class);

        MovieFinder finder = container.getBean(MovieFinder.class, Singleton);

        assertTrue(finder instanceof ColonMovieFinder);
    }

    @Test
    public void should_fetch_bean_that_injected_component_in_constructor() {
        container.registerBean(MovieFinder.class, ColonMovieFinder.class)
                 .registerBean(MovieLister.class);

        MovieLister lister = container.getBean(MovieLister.class, Singleton);

        assertNotNull(lister);
    }

    @Test
    public void should_fetch_bean_that_injected_component_in_setter() {
        container.registerBean(MusicFinder.class, XmlMusicFinder.class)
                 .registerBean(MusicLister.class);

        MusicLister lister = container.getBean(MusicLister.class, Singleton);

        assertNotNull(lister);
        assertNotNull(lister.getFinder());
    }

    @Test
    public void should_fetch_singleton_bean() {
        container.registerBean(MovieFinder.class, ColonMovieFinder.class);

        MovieFinder finder1 = container.getBean(MovieFinder.class, Singleton);
        MovieFinder finder2 = container.getBean(MovieFinder.class, Singleton);

        assertSame(finder1, finder2);
    }

    @Test
    public void should_fetch_brand_new_bean() {
        container.registerBean(MovieFinder.class, ColonMovieFinder.class);

        MovieFinder finder1 = container.getBean(MovieFinder.class, New);
        MovieFinder finder2 = container.getBean(MovieFinder.class, New);

        assertNotNull(finder1);
        assertNotNull(finder2);
        assertNotSame(finder1, finder2);
    }

    @Test
    public void should_fetch_null_if_bean_is_NOT_registered() {
        MovieFinder finder = container.getBean(MovieFinder.class, Singleton);

        assertNull(finder);
    }

    @Test
    public void should_override_injected_bean_when_same_interface_is_registered_multi_times() {
        container.registerBean(MovieFinder.class, ColonMovieFinder.class)
                 .registerBean(MovieFinder.class, CommaMovieFinder.class);

        MovieFinder finder = container.getBean(MovieFinder.class, Singleton);

        assertNotNull(finder);
        assertTrue(finder instanceof CommaMovieFinder);
    }

    @Test(expected = NotMatchedInterfaceException.class)
    public void should_throw_error_when_interface_of_injected_bean_is_not_same_as_its_declaration() {
        container.registerBean(MusicFinder.class, ColonMovieFinder.class);
    }

    @Test
    public void should_fetch_bean_with_auto_interface_identification() {
        container.registerBean(ColonMovieFinder.class);

        MovieFinder finder1 = container.getBean(MovieFinder.class, Singleton);
        MovieFinder finder2 = container.getBean(ColonMovieFinder.class, Singleton);

        assertTrue(finder1 instanceof ColonMovieFinder);
        assertSame(finder1, finder2);
    }

}
