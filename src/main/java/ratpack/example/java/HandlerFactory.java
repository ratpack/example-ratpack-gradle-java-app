package ratpack.example.java;

import java.util.Map;
import ratpack.guice.ModuleRegistry;
import ratpack.handling.Chain;
import ratpack.handling.ChainAction;
import ratpack.handling.Handler;
import ratpack.launch.LaunchConfig;

import static ratpack.guice.Guice.handler;

public class HandlerFactory implements ratpack.launch.HandlerFactory {

    @Override
    public Handler create(LaunchConfig launchConfig) throws Exception {
        // A Handler that makes objects bound to Guice by modules available downstream
        return handler(launchConfig, this::registerModules, new Routes());
    }

    /**
     * Registers all of the Guice modules that make up the application.
     *
     * This is only invoked once during application bootstrap. If you change the
     * module configuration of an application, you must restart it.
     */
    private void registerModules(ModuleRegistry moduleRegistry) {
        moduleRegistry.register(new MyModule());
    }

    private class Routes extends ChainAction {
        /**
         * Adds potential routes.
         *
         * After this method completes, a handler chain will be constructed from
         * the specified routes.
         *
         * This method will be called for every request. This makes it possible
         * to dynamically define the routes if necessary.
         */
        protected void execute() throws Exception {
            // Map to /foo
            handler("foo", context -> context.render("from the foo handler"));

            // Map to /bar
            handler("bar", context -> context.render("from the bar handler"));

            // Set up a nested routing block, which is delegated to `nestedHandler`
            prefix("nested", this::nestedHandler);

            // Map to a dependency injected handler
            handler("injected", getRegistry().get(MyHandler.class));

            // Bind the /static app path to the src/ratpack/assets/images dir
            // Try /static/logo.png
            prefix("static", (Chain nested) -> nested.assets("assets/images"));

            // If nothing above matched, we'll get to here.
            handler(context -> context.render("root handler!"));
        }

        private void nestedHandler(Chain nested) {
            // Map to /nested/*/*
            nested.handler(":var1/:var2?", context -> {
                // The path tokens are the :var1 and :var2 path components above
                Map<String, String> pathTokens = context.getPathTokens();
                context.render(
                    "from the nested handler, var1: " + pathTokens.get("var1") +
                    ", var2: " + pathTokens.get("var2")
                );
            });
        }
    }

}
