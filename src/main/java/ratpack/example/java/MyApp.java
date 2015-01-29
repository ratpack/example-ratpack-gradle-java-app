package ratpack.example.java;

import ratpack.server.RatpackServer;
import ratpack.server.ServerConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MyApp {

  private final static Logger LOGGER = LoggerFactory.getLogger(MyApp.class);

  public static void main(String[] args) {
    try {
      RatpackServer.of(b -> {
        b.config(ServerConfig.findBaseDirProps());
        return b.handler(new HandlerFactory()::create);
      }).start();
    } catch (Exception e) {
      LOGGER.error("", e);
      System.exit(1);
    }
  }
}
