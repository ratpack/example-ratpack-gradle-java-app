package ratpack.example.java;

import ratpack.core.handling.Context;
import ratpack.core.handling.Handler;

/**
 * An example of a handler implicitly set up by a module
 *
 * @see MyModule
 */
public class LoggingHandler implements Handler {

    @Override
    public void handle(Context context) {
        System.out.println("Received: " + context.getRequest().getUri());
        context.next();
    }
}
