package walkthrough.toolWindow.browserView;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.concurrent.Worker;
import javafx.embed.swing.JFXPanel;
import javafx.scene.Scene;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import org.w3c.dom.Document;

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
    private String currentSite;
    private URL url;

    private WebEngine engine;

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

    /**
     * It provides access  to the document model of the current page,
     * and enables two-way communication between a Java application and JavaScript code of the page.
     */
    private Scene createScene() {
        //Create a WebView
        WebView browser = new WebView();
        //Get WebEngine for this WebView
        engine = browser.getEngine();
        //load the URL
        engine.load(url.toExternalForm());

        engine.getLoadWorker().stateProperty().addListener((observable, oldValue, newValue) -> {
            if (Worker.State.SUCCEEDED.equals(newValue)) {
                Document doc = engine.getDocument();
                engine.setJavaScriptEnabled(true);
                Integer step = (Integer) engine.executeScript("App.getCurrentStep()");
            }
        });

        Scene scene = new Scene(browser);
        return scene;
    }

    /**
     * Create a method to listen to changes in the webview
     * When the site is changed, write the current url to currentSite,
     * so that the status might be recreated after closing of the ToolWindow
     */
    private void listenToStatusChange(){
        String href= engine.locationProperty().toString();
        currentSite = "/browser/"+getFileString(href);
    }

    private String getFileString(String href) {
        String[] parts = href.split("/");
        String site = parts[parts.length-1];
        site = site.substring(0, site.length()-1);

        return site;
    }

    public JFXPanel getWebView() {
        return panel;
    }

}
