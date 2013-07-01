package example;

import org.ratpackframework.bootstrap.RatpackServer;
import org.ratpackframework.bootstrap.RatpackServerBuilder;
import org.ratpackframework.handling.Handler;

import java.io.File;

import static org.ratpackframework.guice.Guice.handler;
import static org.ratpackframework.handling.Handlers.chain;

public class Main {

    public static void main(String[] args) throws Exception {
        // Defines the Guice modules for our application
        ModuleBootstrap modulesConfigurer = new ModuleBootstrap();

        // The start of our application routing logic
        Handler appHandler = chain(new HandlerBootstrap());

        // A Handler that makes objects bound to Guice by modules available downstream
        Handler guiceHandler = handler(modulesConfigurer, appHandler);

        // The root of all file system paths in the application (will be src/ratpack)
        File baseDir = new File(System.getProperty("user.dir"));

        // The server builder allows configuring the port etc.
        RatpackServerBuilder ratpackServerBuilder = new RatpackServerBuilder(guiceHandler, baseDir);

        // Start the server and block
        RatpackServer ratpackServer = ratpackServerBuilder.build();
        ratpackServer.start();
    }
}
