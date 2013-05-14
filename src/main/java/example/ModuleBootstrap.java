package example;

import org.ratpackframework.guice.ModuleRegistry;
import org.ratpackframework.util.Action;

/**
 * An action that registers all of the modules that make up the application.
 *
 * This class is not reloadable. The action is only invoked once during application bootstrap.
 *
 * If you change the module configuration of an application, you must restart it.
 */
public class ModuleBootstrap implements Action<ModuleRegistry> {

    public void execute(ModuleRegistry moduleRegistry) {
        moduleRegistry.register(new MyModule());
    }

}
