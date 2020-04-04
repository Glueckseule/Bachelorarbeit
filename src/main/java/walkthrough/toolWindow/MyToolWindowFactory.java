package walkthrough.toolWindow;

import com.intellij.openapi.components.ServiceManager;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.wm.ToolWindow;
import com.intellij.openapi.wm.ToolWindowFactory;
import com.intellij.openapi.wm.WindowManager;
import com.intellij.ui.content.Content;
import com.intellij.ui.content.ContentFactory;
import org.jetbrains.annotations.NotNull;
import walkthrough.toolWindow.highlighting.HighlightWindow;
import walkthrough.toolWindow.tutorial.TutorialService;
import walkthrough.toolWindow.tutorial.TutorialViewManager;
import walkthrough.toolWindow.utils.Event;
import walkthrough.toolWindow.utils.Observer;

import javax.swing.*;
import java.util.Objects;

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

    private Project project;
    private ToolWindow toolWindow;

    /**
     * register toolWindow to the manager
     * init content of tutorial
     * init highlighting windows
     * set content to toolWindow
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
    }

    private void initResources() {
        TutorialViewManager viewManager = new TutorialViewManager(toolWindow);
        ContentFactory contentFactory = ContentFactory.SERVICE.getInstance();
        Content content = contentFactory.createContent(viewManager.getContent(), "", false);

        toolWindow.getContentManager().addContent(content);
    }

    @Override
    public void onEvent(Event event) {
        System.out.println("Event occured: " + event.msg);

        if (event.msg.equals("Tutorial loaded")) {
            //init toolWindow with first step of tutorial
        }
        if (event.msg.equals("Tutorial started")) {
            initHighlighting();
        }
    }

    private void initHighlighting() {
        JFrame root = (JFrame) Objects.requireNonNull(WindowManager.getInstance().getFrame(project)).getRootPane().getParent();
        HighlightWindow shadowFrame = new HighlightWindow(root);
        shadowFrame.setVisible(true);
    }
}
