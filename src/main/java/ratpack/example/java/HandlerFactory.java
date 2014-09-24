package ratpack.example.java;

import ratpack.guice.BindingsSpec;
import ratpack.handling.Chain;
import ratpack.handling.Handler;
import ratpack.launch.LaunchConfig;

import java.util.Map;

import static ratpack.guice.Guice.chain;

public class HandlerFactory implements ratpack.launch.HandlerFactory {

    @Override
    public Handler create(LaunchConfig launchConfig) throws Exception {
        // A Handler that makes objects bound to Guice by modules available downstream
        return chain(launchConfig, (BindingsSpec moduleRegistry) -> {
            /*
             * Registers all of the Guice modules that make up the application.
             * <p>
             * This is only invoked once during application bootstrap. If you change the
             * module configuration of an application, you must restart it.
             */
            moduleRegistry.add(new MyModule());
        }, (Chain chain) -> {
            /*
             * Adds potential routes.
             * <p>
             * After this method completes, a handler chain will be constructed from
             * the specified routes.
             * <p>
             * This method will be called for every request. This makes it possible
             * to dynamically define the routes if necessary.
             */
            chain
                    .handler("foo", context -> context.render("from the foo handler")) // Map to /foo

                    .handler("bar", context -> context.render("from the bar handler")) // Map to /bar

                    .prefix("nested", nested -> { // Set up a nested routing block, which is delegated to `nestedHandler`
                        nested.handler(":var1/:var2?", context -> { // The path tokens are the :var1 and :var2 path components above
                            Map<String, String> pathTokens = context.getPathTokens();
                            context.render(
                                    "from the nested handler, var1: " + pathTokens.get("var1") +
                                            ", var2: " + pathTokens.get("var2")
                            );
                        });
                    })

                    .handler("injected", chain.getRegistry().get(MyHandler.class)) // Map to a dependency injected handler

                    .prefix("static", nested -> nested.assets("assets/images")) // Bind the /static app path to the src/ratpack/assets/images dir

                    .handler(context -> context.render("root handler!")); // If nothing above matched, we'll get to here.
        });
    }
}
