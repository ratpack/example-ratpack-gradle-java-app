package ratpack.example.java;

import ratpack.handling.Context;
import ratpack.handling.Handler;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * A handler implementation that is created via dependency injection.
 *
 * @see MyModule
 * @see HandlerBootstrap
 */
@Singleton
public class MyHandler implements Handler {

    private final MyService myService;

    @Inject
    public MyHandler(MyService myService) {
        this.myService = myService;
    }

    @Override
    public void handle(Context context) {
        context.getResponse().send("service value: " + myService.getValue());
    }
}
