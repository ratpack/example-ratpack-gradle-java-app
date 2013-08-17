package example;

import org.ratpackframework.handling.Handler;
import org.ratpackframework.launch.LaunchConfig;

import static org.ratpackframework.guice.Guice.handler;

public class HandlerFactory implements org.ratpackframework.launch.HandlerFactory {

    @Override
    public Handler create(LaunchConfig launchConfig) {
        // Defines the Guice modules for our application
        ModuleBootstrap modulesConfigurer = new ModuleBootstrap();

        // A Handler that makes objects bound to Guice by modules available downstream
        return handler(launchConfig, modulesConfigurer, new HandlerBootstrap());
    }
}
