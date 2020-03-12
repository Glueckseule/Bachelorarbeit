package walkthrough.toolWindow;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.wm.ToolWindow;
import com.intellij.openapi.wm.ToolWindowFactory;
import com.intellij.ui.content.Content;
import com.intellij.ui.content.ContentFactory;
import org.jetbrains.annotations.NotNull;

public class MyToolWindowFactory implements ToolWindowFactory {

    /**
     * This is the entry point for the plugin.
     * It states that the essential component of the plugin is a tool window
     * and creates it (PluginContainer)
     * The content set comes from the PluginContainer's method getContent()
     */

    public void createToolWindowContent(@NotNull Project project, @NotNull ToolWindow toolWindow) {
        PluginContainer myToolWindow = new PluginContainer(project);
        ContentFactory contentFactory = ContentFactory.SERVICE.getInstance();
        Content content = contentFactory.createContent(myToolWindow.getContent(), "", false);
        toolWindow.getContentManager().addContent(content);
    }

}
