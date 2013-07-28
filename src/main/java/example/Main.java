package example;

import org.ratpackframework.server.DefaultRatpackServerSettings;
import org.ratpackframework.server.RatpackServer;
import org.ratpackframework.server.RatpackServerBuilder;
import org.ratpackframework.handling.Handler;
import org.ratpackframework.server.RatpackServerSettings;

import java.io.File;

import static org.ratpackframework.guice.Guice.handler;
import static org.ratpackframework.handling.Handlers.chain;

public class Main {

    public static void main(String[] args) throws Exception {
        // The root of all file system paths in the application (will be src/ratpack)
        File baseDir = new File(System.getProperty("user.dir"));

        // Is the app in dev mode? Where code/templates can change?
        boolean reloadable = true;

        RatpackServerSettings serverSettings = new DefaultRatpackServerSettings(baseDir, reloadable);

        // Defines the Guice modules for our application
        ModuleBootstrap modulesConfigurer = new ModuleBootstrap();

        // The start of our application routing logic
        Handler appHandler = chain(new HandlerBootstrap());

        // A Handler that makes objects bound to Guice by modules available downstream
        Handler guiceHandler = handler(serverSettings, modulesConfigurer, appHandler);

        // The server builder allows configuring the port etc.
        RatpackServerBuilder ratpackServerBuilder = new RatpackServerBuilder(serverSettings, guiceHandler);

        // Start the server and block
        RatpackServer ratpackServer = ratpackServerBuilder.build();
        ratpackServer.start();
    }
}
