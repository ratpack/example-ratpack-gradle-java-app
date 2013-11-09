package ratpack.example.java;

/**
 * The service implementation.
 *
 * @see MyHandler
 */
public class MyServiceImpl implements MyService {

    public String getValue() {
        return "service-value";
    }

}
