package example;

import org.ratpackframework.guice.ModuleRegistry;
import org.ratpackframework.util.Action;

/**
 * An action that registers all of the modules that make up the application.
 */
public class ModuleBootstrap implements Action<ModuleRegistry> {

    public void execute(ModuleRegistry moduleRegistry) {
        moduleRegistry.register(new MyModule());
    }

}
