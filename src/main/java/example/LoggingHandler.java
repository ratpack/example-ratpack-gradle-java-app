package example;

import org.ratpackframework.handling.Context;
import org.ratpackframework.handling.Handler;

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
