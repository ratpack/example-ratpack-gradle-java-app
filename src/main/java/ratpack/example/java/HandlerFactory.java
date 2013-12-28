package ratpack.example.java;

import ratpack.handling.Handler;
import ratpack.launch.LaunchConfig;

import static ratpack.guice.Guice.handler;

public class HandlerFactory implements ratpack.launch.HandlerFactory {

    @Override
    public Handler create(LaunchConfig launchConfig) throws Exception {
        // Defines the Guice modules for our application
        ModuleBootstrap modulesConfigurer = new ModuleBootstrap();

        // A Handler that makes objects bound to Guice by modules available downstream
        return handler(launchConfig, modulesConfigurer, new HandlerBootstrap());
    }
}
