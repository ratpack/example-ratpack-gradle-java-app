package ratpack.example.java;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ratpack.guice.Guice;
import ratpack.server.RatpackServer;
import ratpack.server.ServerConfig;

import java.util.Map;

public class MyApp {

  private final static Logger LOGGER = LoggerFactory.getLogger(MyApp.class);

  public static void main(String[] args) {
    try {
      RatpackServer.of(b -> b
          .serverConfig(ServerConfig.findBaseDirProps())
          .registry(Guice.registry(bindingSpec -> bindingSpec.add(new MyModule())))
          .handlers(chain -> chain
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
              .handler(context -> context.render("root handler!")))).start();
    } catch (Exception e) {
      LOGGER.error("", e);
      System.exit(1);
    }
  }
}
