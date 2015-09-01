package ratpack.example.java;

import ratpack.guice.Guice;
import ratpack.handling.Chain;
import ratpack.server.BaseDir;
import ratpack.server.RatpackServer;

import java.util.Map;

public class MyApp {

  public static void main(String[] args) throws Exception {
    RatpackServer.start(s -> s
        .serverConfig(c -> c.baseDir(BaseDir.find()))
        .registry(Guice.registry(b -> b.module(MyModule.class)))
        .handlers(chain -> chain
            .path("foo", ctx -> ctx.render("from the foo handler")) // Map to /foo
            .path("bar", ctx -> ctx.render("from the bar handler")) // Map to /bar
            .prefix("nested", nested -> { // Set up a nested routing block, which is delegated to `nestedHandler`
              nested.path(":var1/:var2?", ctx -> { // The path tokens are the :var1 and :var2 path components above
                Map<String, String> pathTokens = ctx.getPathTokens();
                ctx.render(
                  "from the nested handler, var1: " + pathTokens.get("var1") +
                    ", var2: " + pathTokens.get("var2")
                );
              });
            })
            .path("injected", MyHandler.class) // Map to a dependency injected handler
            .prefix("static", nested -> nested.fileSystem("assets/images", Chain::files)) // Bind the /static app path to the src/ratpack/assets/images dir
            .all(ctx -> ctx.render("root handler!"))
        )
    );
  }
}
