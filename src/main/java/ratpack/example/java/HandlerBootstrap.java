package ratpack.example.java;

import ratpack.handling.Chain;
import ratpack.handling.Context;
import ratpack.handling.Handler;
import ratpack.func.Action;

import java.util.Map;

/**
 * The main application handler.
 * <p/>
 * Defines a hierarchical tree of handlers.
 *
 * @see HandlerFactory
 */
public class HandlerBootstrap implements Action<Chain> {

    /**
     * Adds potential routes.
     * <p/>
     * After this method completes, a handler chain will be constructed from the specified routes.
     * <p/>
     * This method will be called for every request. This makes it possible to dynamically define the routes
     * if necessary.
     */
    public void execute(Chain handlers) throws Exception {
        handlers

                // Map to /foo
                .handler("foo", context -> context.render("from the foo handler"))

                // Map to /bar
                .handler("bar", context -> context.render("from the bar handler"))

                // Set up a nested routing block, where
                .prefix("nested", this::nestedHandlers)

                // Map a dependency injected handler to
                .handler("injected", handlers.getRegistry().get(MyHandler.class))

                // Bind the /static app path to the src/ratpack/assets/images dir
                // Try /static/logo.png
                .prefix("static", (Chain nested) -> nested.assets("assets/images"))

                // If nothing above matched, we'll get to here.
                .handler(context -> context.render("root handler!"));
    }

    private void nestedHandlers(Chain nested) {
        // Map to /nested/*/*
        nested.handler(":var1/:var2?", context -> {
            // The path tokens are the :var1 and :var2 path components above
            Map<String, String> pathTokens = context.getPathTokens();
            context.render("from the nested handler, var1: " + pathTokens.get("var1") + ", var2: " + pathTokens.get("var2"));
        });
    }

}
