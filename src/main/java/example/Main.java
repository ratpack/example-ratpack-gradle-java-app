package example;

import org.ratpackframework.bootstrap.RatpackServer;
import org.ratpackframework.bootstrap.RatpackServerBuilder;
import org.ratpackframework.guice.GuiceBackedHandlerFactory;
import org.ratpackframework.guice.internal.DefaultGuiceBackedHandlerFactory;
import org.ratpackframework.routing.Handler;

import java.io.File;

import static org.ratpackframework.routing.Handlers.routes;

public class Main {

    public static void main(String[] args) {
        GuiceBackedHandlerFactory guiceHandler = new DefaultGuiceBackedHandlerFactory();
        Handler handler = guiceHandler.create(new ModuleBootstrap(), routes(new RoutingBootstrap()));
        File baseDir = new File(System.getProperty("user.dir"));
        RatpackServerBuilder ratpackServerBuilder = new RatpackServerBuilder(handler, baseDir);

        RatpackServer ratpackServer = ratpackServerBuilder.build();
        ratpackServer.startAndWait();
    }
}
