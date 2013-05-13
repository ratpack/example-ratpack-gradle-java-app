package example;

import com.google.inject.AbstractModule;
import com.google.inject.Injector;
import org.ratpackframework.guice.HandlerDecoratingModule;
import org.ratpackframework.routing.Handler;

public class MyModule extends AbstractModule implements HandlerDecoratingModule {

    @Override
    protected void configure() {
        bind(MyService.class).to(MyServiceImpl.class);
    }

    @Override
    public Handler decorate(Injector injector, Handler handler) {
        return new LoggingHandler(handler);
    }
}
