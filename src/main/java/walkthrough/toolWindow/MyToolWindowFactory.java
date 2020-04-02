package walkthrough.toolWindow;

import com.intellij.openapi.components.ServiceManager;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.wm.ToolWindow;
import com.intellij.openapi.wm.ToolWindowFactory;
import com.intellij.openapi.wm.ToolWindowManager;
import com.intellij.openapi.wm.ex.ToolWindowManagerListener;
import com.intellij.ui.content.Content;
import com.intellij.ui.content.ContentFactory;
import org.jetbrains.annotations.NotNull;
import walkthrough.toolWindow.tutorial.TutorialService;
import walkthrough.toolWindow.utils.Event;
import walkthrough.toolWindow.utils.Observer;

/**
 * This is the entry point for the plugin.
 * It states that the essential component of the plugin is a tool window
 * and creates it (PluginContainer)
 * The content set comes from the PluginContainer's method getContent()
 *
 *
 * @author Daniela
 */
public class MyToolWindowFactory implements ToolWindowFactory, Observer {

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
        ServiceManager.getService(TutorialService.class).addListener(this);
        ServiceManager.getService(TutorialService.class).initTutorialFromFile(null);

        initResources();
        register();
    }

    /**
     * TODO: Register listener on toolWindow shown/hidden
     */
    private void register() {
        project.getMessageBus().connect().subscribe(ToolWindowManagerListener.TOPIC, new ToolWindowManagerListener() {
            @Override
            public void stateChanged() {
                if (toolWindow.isVisible()) {
                    updateResources();
                }
            }
        });
    }

    /**
     * Create the container in toolWindow which the the content will be loaded into.
     */
    private void initResources() {
        contentContainer = new ContentContainer(project);
        if (toolWindow.getContentManager().getContents().length == 0) {
            setContent();
        }
    }

    private void updateResources() {
        //TODO: find out how to load content back, DON'T CREATE AGAIN!
    }

    private void setContent(){
        Content webViewContent = ContentFactory.SERVICE.getInstance().createContent(contentContainer.getContent(), "Einstieg", false);
        toolWindow.getContentManager().addContent(webViewContent);
    }

    @Override
    public void onEvent(Event event) {
        System.out.println("Event occured: " + event.msg);
    }
}
