package tw.grinps.xmlparser;

import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;


public class XMLParserTest {

    private XMLBeanParser beanParser;

    @Before
    public void setup(){
        this.beanParser = new XMLBeanParser();
    }

    @Test(expected = IllegalArgumentException.class)
    public void should_not_read_a_nonexist_xml_file() throws IOException {
        beanParser.getBeansFrom("notexist.xml");
    }

    @Test
    public void should_read_an_existed_file() throws IOException {
        beanParser.getBeansFrom("test.xml");
    }

    @Test
    public void should_return_a_bean_class_name() throws IOException {
        List<Bean> beanName = beanParser.getBeansFrom("test.xml");
        assertThat(beanName.get(1).getClassName(), is("tw.sample.multimedia.Movie"));
    }

    @Test
    public void should_return_a_bean_id() throws IOException {
        List<Bean> beanName = beanParser.getBeansFrom("test.xml");
        assertThat(beanName.get(1).getId(), is("movie"));
    }

    @Test
    public void should_return_multiple_bean_class_names() throws IOException {
        List<Bean> beans = beanParser.getBeansFrom("test.xml");
        assertThat(beans.size(), is(3));
        assertThat(beans.get(0).getClassName(), is("tw.sample.multimedia.MovieLister"));
        assertThat(beans.get(1).getClassName(), is("tw.sample.multimedia.Movie"));
        assertThat(beans.get(2).getClassName(), is("tw.sample.multimedia.ColonMovieFinder"));
    }
}
