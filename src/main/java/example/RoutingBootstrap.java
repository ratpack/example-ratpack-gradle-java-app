package example;

import org.ratpackframework.routing.Exchange;
import org.ratpackframework.routing.Handler;
import org.ratpackframework.routing.Routing;
import org.ratpackframework.routing.RoutingBuilder;

import static org.ratpackframework.guice.Injection.handler;
import static org.ratpackframework.routing.Handlers.exactPath;
import static org.ratpackframework.routing.Handlers.path;

public class RoutingBootstrap implements RoutingBuilder<Routing> {

    @Override
    public void addRoutes(Routing routing) {
        routing.route(path("foo", new Handler() {
            public void handle(Exchange exchange) {
                exchange.getResponse().send("from the foo handler");
            }
        }));

        routing.route(path("bar", new Handler() {
            public void handle(Exchange exchange) {
                exchange.getResponse().send("from the bar handler");
            }
        }));

        routing.route(path("nested", new RoutingBuilder<Routing>() {
            public void addRoutes(Routing routing) {
                routing.route(path(":var", new Handler() {
                    public void handle(Exchange exchange) {
                        exchange.getResponse().send("from the nested handler, var: " + exchange.getPathTokens().get("var"));
                    }
                }));
            }
        }));

        routing.route(path("injected", handler(MyHandler.class)));

        routing.route(new Handler() {
            public void handle(Exchange exchange) {
                exchange.getResponse().send("root handler!");
            }
        });

    }

}
