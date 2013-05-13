package example;

import org.ratpackframework.routing.Exchange;
import org.ratpackframework.routing.Handler;

import javax.inject.Inject;

public class MyHandler implements Handler {

    private final MyServiceImpl myService;

    @Inject
    public MyHandler(MyServiceImpl myService) {
        this.myService = myService;
    }

    @Override
    public void handle(Exchange exchange) {
        exchange.getResponse().send("service value: " + myService.getValue());
    }
}
