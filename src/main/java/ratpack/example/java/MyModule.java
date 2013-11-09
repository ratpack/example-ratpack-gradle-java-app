package ratpack.example.java;

import com.google.inject.AbstractModule;
import com.google.inject.Injector;
import ratpack.guice.HandlerDecoratingModule;
import ratpack.handling.Handler;

import static java.util.Arrays.asList;
import static ratpack.handling.Handlers.chain;

/**
 * An example Guice module.
 */
public class MyModule extends AbstractModule implements HandlerDecoratingModule {

    /**
     * Adds a service impl to the application.
     *
     * @see MyHandler
     */
    protected void configure() {
        bind(MyService.class).to(MyServiceImpl.class);
    }

    /**
     * Modules that implement {@link HandlerDecoratingModule} are able to decorate the application handler in some way.
     * <p/>
     * In this case, we are wrapping that app handler in a logging handler so that all requests are logged
     *
     * @see HandlerDecoratingModule
     */
    public Handler decorate(Injector injector, Handler handler) {
        return chain(asList(new LoggingHandler(), handler));
    }
}
