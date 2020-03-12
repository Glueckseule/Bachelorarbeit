package walkthrough.toolWindow.browserView;

import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.scene.Scene;
import javafx.scene.web.WebView;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;

public class CustomWebView extends JFXPanel {

    /**
     * Create a new panel to set inside the tool window
     * The panel is of type JFXPanel, because this allows to display a JS-App
     * The JS-App displayed here is located in the browse-folder
     * Use as content the files from the PATH defined below
     *
     * TODO:   Complete html-files with explanations and extra wizard library
     */

    private JFXPanel panel;
    private static final String PATH = "/browser/index.html";
    private URL url;

    public CustomWebView() {
        panel = new JFXPanel();
        url = getClass().getResource(PATH);

        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                initFX();
            }
        });
    }

    private void initFX() {
        Scene scene = createScene();
        panel.setScene(scene);
    }

    private Scene createScene() {
        WebView browser = new WebView();
        browser.getEngine().load(url.toExternalForm());
        return new Scene(browser);
    }

    public JFXPanel getWebView() {
        return panel;
    }

}
