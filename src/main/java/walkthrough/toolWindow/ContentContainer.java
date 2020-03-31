package walkthrough.toolWindow;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.wm.WindowManager;
import javafx.embed.swing.JFXPanel;
import walkthrough.toolWindow.browserView.CustomWebView;
import walkthrough.toolWindow.highlighting.HighlightWindow;

import javax.swing.*;
import java.util.Objects;

public class ContentContainer {

    /**
     * This class manages the plugin components:
     * the shadow frame (HighlightWindow)
     * and the content of the tool window (CustomWebView)
     */

    private JFrame root;
    private JFXPanel panel;
    private HighlightWindow shadowFrame;

    public ContentContainer(Project project) {
        root = (JFrame) Objects.requireNonNull(WindowManager.getInstance().getFrame(project)).getRootPane().getParent();
        panel = new CustomWebView().getWebView();

        initShadowing();
    }

    private void initShadowing() {
        shadowFrame = new HighlightWindow(root);
        shadowFrame.setVisible(true);
    }

    public JFXPanel getContent() {
        return panel;
    }
}
