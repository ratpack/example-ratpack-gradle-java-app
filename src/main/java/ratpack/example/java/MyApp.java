package ratpack.example.java;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ratpack.server.RatpackServer;
import ratpack.server.ServerConfig;

public class MyApp {

  private final static Logger LOGGER = LoggerFactory.getLogger(MyApp.class);

  public static void main(String[] args) {
    try {
      RatpackServer.of(b -> {
        b.serverConfig(ServerConfig.findBaseDirProps());
        return b.handler(new HandlerFactory()::create);
      }).start();
    } catch (Exception e) {
      LOGGER.error("", e);
      System.exit(1);
    }
  }
}
