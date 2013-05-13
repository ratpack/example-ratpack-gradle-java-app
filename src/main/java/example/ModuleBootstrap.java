package example;

import org.ratpackframework.guice.ModuleRegistry;
import org.ratpackframework.util.Action;

public class ModuleBootstrap implements Action<ModuleRegistry> {

    @Override
    public void execute(ModuleRegistry moduleRegistry) {
        moduleRegistry.register(new MyModule());
    }

}
