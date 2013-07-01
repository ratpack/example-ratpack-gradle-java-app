package example;

import org.ratpackframework.handling.Chain;
import org.ratpackframework.handling.Exchange;
import org.ratpackframework.handling.Handler;
import org.ratpackframework.util.Action;

import java.util.Map;

import static org.ratpackframework.guice.Guice.handler;
import static org.ratpackframework.handling.Handlers.*;

/**
 * The main application handler.
 * <p/>
 * Defines a hierarchical tree of handlers.
 *
 * @see Main
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
    public void execute(Chain handlers) {

        // Map to /foo
        handlers.add(path("foo", new Handler() {
            public void handle(Exchange exchange) {
                exchange.getResponse().send("from the foo handler");
            }
        }));

        // Map to /bar
        handlers.add(path("bar", new Handler() {
            public void handle(Exchange exchange) {
                exchange.getResponse().send("from the bar handler");
            }
        }));

        // Set up a nested routing block, where
        handlers.add(prefix("nested", new Action<Chain>() {
            public void execute(Chain chain) {

                // Map to /nested/*/*
                chain.add(path(":var1/:var2", new Handler() {
                    public void handle(Exchange exchange) {
                        // The path tokens are the :var1 and :var2 path components above
                        Map<String, String> pathTokens = exchange.getPathTokens();
                        exchange.getResponse().send("from the nested handler, var1: " + pathTokens.get("var1") + ", " + pathTokens.get("var2"));
                    }
                }));
            }
        }));

        // Map a dependency injected handler to
        handlers.add(path("injected", handler(MyHandler.class)));

        // Bind the /static app path to the src/ratpack/assets dir
        // Try /static/logo.png
        handlers.add(prefix("static", new Action<Chain>() {
            public void execute(Chain handlers) {
                handlers.add(assets("assets", new Handler() {
                    public void handle(Exchange exchange) {
                        exchange.getResponse().status(404).send();
                    }
                }));
            }
        }));

        // If nothing above matched, we'll get to here.
        handlers.add(new Handler() {
            public void handle(Exchange exchange) {
                exchange.getResponse().send("root handler!");
            }
        });
    }

}
