package example;

import org.ratpackframework.handling.Exchange;
import org.ratpackframework.handling.Handler;

/**
 * An example of a handler implicitly set up by a module
 *
 * @see MyModule
 */
public class LoggingHandler implements Handler {

    private final Handler delegate;

    public LoggingHandler(Handler delegate) {
        this.delegate = delegate;
    }

    @Override
    public void handle(Exchange exchange) {
        System.out.println("Received: " + exchange.getRequest().getUri());
        exchange.next(delegate);
    }
}
