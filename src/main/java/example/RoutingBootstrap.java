package example;

import org.ratpackframework.routing.Exchange;
import org.ratpackframework.routing.Handler;
import org.ratpackframework.routing.Routing;
import org.ratpackframework.routing.RoutingBuilder;

import java.util.Map;

import static org.ratpackframework.guice.Injection.handler;
import static org.ratpackframework.routing.Handlers.assetsPath;
import static org.ratpackframework.routing.Handlers.exactPath;
import static org.ratpackframework.routing.Handlers.path;

/**
 * The main application handler.
 *
 * Defines a hierarchical tree of routes.
 *
 * @see Main
 */
public class RoutingBootstrap implements RoutingBuilder<Routing> {

    /**
     * Adds potential routes.
     *
     * After this method completes, a handler chain will be constructed from the specified routes.
     *
     * This method will be called for every request. This makes it possible to dynamically define the routes
     * if necessary.
     */
    public void addRoutes(Routing routing) {

        // Map to /foo
        routing.route(path("foo", new Handler() {
            public void handle(Exchange exchange) {
                exchange.getResponse().send("from the foo handler");
            }
        }));

        // Map to /bar
        routing.route(path("bar", new Handler() {
            public void handle(Exchange exchange) {
                exchange.getResponse().send("from the bar handler");
            }
        }));

        // Set up a nested routing block, where
        routing.route(path("nested", new RoutingBuilder<Routing>() {
            public void addRoutes(Routing routing) {

                // Map to /nested/*/*
                routing.route(exactPath(":var1/:var2", new Handler() {
                    public void handle(Exchange exchange) {
                        // The path tokens are the :var1 and :var2 path components above
                        Map<String, String> pathTokens = exchange.getPathTokens();
                        exchange.getResponse().send("from the nested handler, var1: " + pathTokens.get("var1") + ", " + pathTokens.get("var2"));
                    }
                }));
            }
        }));

        // Map a dependency injected handler to
        routing.route(path("injected", handler(MyHandler.class)));

        // Bind the /static app path to the src/ratpack/assets dir
        // Try /static/logo.png
        routing.route(assetsPath("static", "assets", new Handler() {
            public void handle(Exchange exchange) {
                exchange.getResponse().status(404).send();
            }
        }));

        // If nothing above matched, we'll get to here.
        routing.route(new Handler() {
            public void handle(Exchange exchange) {
                exchange.getResponse().send("root handler!");
            }
        });
    }

}
