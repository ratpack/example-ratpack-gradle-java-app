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
                .handler("foo", new Handler() {
                    public void handle(Context context) {
                        context.render("from the foo handler");
                    }
                })

                        // Map to /bar
                .handler("bar", new Handler() {
                    public void handle(Context context) {
                        context.render("from the bar handler");
                    }
                })

                        // Set up a nested routing block, where
                .prefix("nested", new Action<Chain>() {
                    public void execute(Chain nested) {
                        // Map to /nested/*/*
                        nested.handler(":var1/:var2?", new Handler() {
                            public void handle(Context context) {
                                // The path tokens are the :var1 and :var2 path components above
                                Map<String, String> pathTokens = context.getPathTokens();
                                context.render("from the nested handler, var1: " + pathTokens.get("var1") + ", var2: " + pathTokens.get("var2"));
                            }
                        });
                    }
                })

                        // Map a dependency injected handler to
                .handler("injected", handlers.getRegistry().get(MyHandler.class))

                        // Bind the /static app path to the src/ratpack/assets/images dir
                        // Try /static/logo.png
                .prefix("static", new Action<Chain>() {
                    public void execute(Chain handlers) {
                        handlers.assets("assets/images");
                    }
                })

                        // If nothing above matched, we'll get to here.
                .handler(new Handler() {
                    public void handle(Context context) {
                        context.render("root handler!");
                    }
                });
    }

}
