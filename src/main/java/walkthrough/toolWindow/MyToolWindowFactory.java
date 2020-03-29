package walkthrough.toolWindow;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.wm.ToolWindow;
import com.intellij.openapi.wm.ToolWindowFactory;
import com.intellij.openapi.wm.ToolWindowManager;
import com.intellij.openapi.wm.ex.ToolWindowManagerListener;
import com.intellij.ui.content.Content;
import com.intellij.ui.content.ContentFactory;
import org.jetbrains.annotations.NotNull;

/**
 * This is the entry point for the plugin.
 * It states that the essential component of the plugin is a tool window
 * and creates it (PluginContainer)
 * The content set comes from the PluginContainer's method getContent()
 *
 *
 * @author Daniela
 */
public class MyToolWindowFactory implements ToolWindowFactory {

    private ToolWindowManager manager;
    private Project project;
    private ToolWindow toolWindow;
    private ContentContainer contentContainer;

    /**
     * register toolWindow to the manager
     * init content of toolWindow
     * set a listener on toolWindow which listens to when toolWindow is minimized/reopened
     *
     * @param project       The project that is opened in the plugin IDE
     * @param toolWindow    ToolWindow with the id "Walkthrough durch IntelliJ"
     */
    @Override
    public void createToolWindowContent(@NotNull Project project, @NotNull ToolWindow toolWindow) {
        this.project = project;
        this.toolWindow = toolWindow;
        initResources();
        register();
    }

    /**
     * TODO: Register the Tool Window to the manager, so we can handle all toolWindow things there
     */
    private void register() {
        manager = ToolWindowManager.getInstance(project);
        manager.getToolWindow(toolWindow.getTitle());

        project.getMessageBus().connect().subscribe(ToolWindowManagerListener.TOPIC, new ToolWindowManagerListener() {
            @Override
            public void stateChanged() {
                if (toolWindow.isVisible()) {
                    initResources();
                }
            }
        });
    }

    /**
     * Create the container in toolWindow in which the the content will be loaded into.
     * TODO find out why it is called 4 times!
     */
    private void initResources() {
        contentContainer = new ContentContainer(project);
        if (toolWindow.getContentManager().getContents().length == 0) {
            setContent();
        }
    }

    private void setContent(){
        Content webViewContent = ContentFactory.SERVICE.getInstance().createContent(contentContainer.getContent(), "Einstieg", false);
        toolWindow.getContentManager().addContent(webViewContent);
    }
}
