package ratpack.example.java;

import static org.junit.Assert.*;

import com.google.common.io.CharStreams;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URI;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.junit.Test;
import ratpack.test.ApplicationUnderTest;
import ratpack.test.RatpackMainApplicationUnderTest;

@RunWith(JUnit4.class)
public class SiteTest {

    ApplicationUnderTest aut = new RatpackMainApplicationUnderTest();

    @Test
    public void fooHandler() {
        assertEquals("from the foo handler", get("/foo"));
    }

    @Test
    public void barHandler() {
        assertEquals("from the bar handler", get("/bar"));
    }

    @Test
    public void nestedHandler() {
        assertEquals("from the nested handler, var1: x, var2: null", get("/nested/x"));
        assertEquals("from the nested handler, var1: x, var2: y", get("/nested/x/y"));
    }

    @Test
    public void injectedHandler() {
        assertEquals("service value: service-value", get("/injected"));
    }

    @Test
    public void staticHandler() {
        assertEquals("text asset\n", get("/static/test.txt"));
    }

    @Test
    public void rootHandler() {
        assertEquals("root handler!", get("/"));
        assertEquals("root handler!", get("/unknown-path"));
    }

    private String get(String path) {
        URI uri = aut.getAddress().resolve(path);
        try(InputStream in = uri.toURL().openStream()) {
            try (Reader r = new InputStreamReader(in)) {
                return CharStreams.toString(r);
            }
        } catch (Exception ex) {
            fail(ex.toString());
            return ""; // unreachable
        }
    }

}
