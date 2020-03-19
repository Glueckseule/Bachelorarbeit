package walkthrough.toolWindow;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.wm.ToolWindow;
import com.intellij.openapi.wm.WindowManager;
import com.intellij.ui.content.ContentManager;
import javafx.embed.swing.JFXPanel;
import walkthrough.toolWindow.browserView.CustomWebView;
import walkthrough.toolWindow.highlighting.HighlightWindow;

import javax.swing.*;
import java.util.Objects;

public class PluginContainer {

    /**
     * This class manages the plugin components:
     * the shadow frame (HighlightWindow)
     * and the content of the tool window (CustomWebView)
     */

    private JFrame root;
    private JFXPanel panel;

    public PluginContainer(Project project) {
        root = (JFrame) Objects.requireNonNull(WindowManager.getInstance().getFrame(project)).getRootPane().getParent();
        panel = new CustomWebView().getWebView();

        initShadowing();
    }

    private void initShadowing() {
        HighlightWindow shadowFrame = new HighlightWindow(root);
        shadowFrame.setVisible(true);
    }

    public JFXPanel getContent() {
        return panel;
    }
}
