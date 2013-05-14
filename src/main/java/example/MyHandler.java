package example;

import org.ratpackframework.routing.Exchange;
import org.ratpackframework.routing.Handler;

import javax.inject.Inject;

/**
 * A handler implementation that is created via dependency injection.
 *
 * @see MyModule
 * @see RoutingBootstrap
 */
public class MyHandler implements Handler {

    private final MyService myService;

    @Inject
    public MyHandler(MyService myService) {
        this.myService = myService;
    }

    @Override
    public void handle(Exchange exchange) {
        exchange.getResponse().send("service value: " + myService.getValue());
    }
}
