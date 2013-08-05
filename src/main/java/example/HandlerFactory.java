package example;

import org.ratpackframework.handling.Handler;
import org.ratpackframework.launch.LaunchConfig;

import static org.ratpackframework.guice.Guice.handler;
import static org.ratpackframework.handling.Handlers.chain;

public class HandlerFactory implements org.ratpackframework.launch.HandlerFactory {

    @Override
    public Handler create(LaunchConfig launchConfig) {
        // Defines the Guice modules for our application
        ModuleBootstrap modulesConfigurer = new ModuleBootstrap();

        // The start of our application routing logic
        Handler appHandler = chain(new HandlerBootstrap());

        // A Handler that makes objects bound to Guice by modules available downstream
        return handler(launchConfig, modulesConfigurer, appHandler);
    }
}
