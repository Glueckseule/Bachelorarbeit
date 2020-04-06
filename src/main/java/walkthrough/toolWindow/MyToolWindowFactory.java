package walkthrough.toolWindow;

import com.intellij.openapi.components.ServiceManager;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.wm.ToolWindow;
import com.intellij.openapi.wm.ToolWindowFactory;
import com.intellij.ui.content.Content;
import com.intellij.ui.content.ContentFactory;
import com.sun.xml.bind.v2.runtime.reflect.opt.Const;
import org.jetbrains.annotations.NotNull;
import walkthrough.toolWindow.highlightingModel.HighlightingService;
import walkthrough.toolWindow.tutorialModel.TutorialService;
import walkthrough.toolWindow.tutorialModel.TutorialStep;
import walkthrough.toolWindow.utils.Constants;
import walkthrough.toolWindow.utils.Event;
import walkthrough.toolWindow.utils.Observer;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;

/**
 * This is the entry point for the plugin.
 * It states that the essential component of the plugin is a tool window
 * and creates it (PluginContainer)
 * The content set comes from the PluginContainer's method getContent()
 *
 * @author Daniela
 */
public class MyToolWindowFactory implements ToolWindowFactory, Observer {

    private Project project;
    private ToolWindow toolWindow;
    private HighlightingService highlightingService = ServiceManager.getService(HighlightingService.class);
    private TutorialService tutorialService = ServiceManager.getService(TutorialService.class);
    private TutorialView tutorialView;
    private TutorialStep step;

    /**
     * register toolWindow to the manager
     * init content of tutorial
     * init highlighting windows
     * set content to toolWindow
     *
     * @param project    The project that is opened in the plugin IDE
     * @param toolWindow ToolWindow with the id "Walkthrough durch IntelliJ"
     */
    @Override
    public void createToolWindowContent(@NotNull Project project, @NotNull ToolWindow toolWindow) {
        this.project = project;
        this.toolWindow = toolWindow;

        initView();
        initServices();
    }

    private void initServices() {
        tutorialService.addListener(this);
        try {
            InputStream inputStream = getClass().getResourceAsStream(Constants.BASIC_TUTORIAL);
            BufferedReader br = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
            StringBuilder stringBuilder = new StringBuilder();
            String inputString;
            while ((inputString = br.readLine()) != null) {
                stringBuilder.append(inputString);
            }

            tutorialService.initTutorialFromFile(stringBuilder.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //TODO: replace type by value "type" from JSON - get value from tutorialService
    private void initView() {
        tutorialView = new TutorialView(tutorialService.getTutorialType());
        ContentFactory contentFactory = ContentFactory.SERVICE.getInstance();
        Content content = contentFactory.createContent(tutorialView.getContent(), "", false);
        toolWindow.getContentManager().addContent(content);

        tutorialView.setListener();
    }

    @Override
    public void onEvent(Event event) {
        if (event.msg.equals(Constants.TUTORIAL_LOADED)) {
            step = tutorialService.getFirstStep();
        }
        if (event.msg.equals(Constants.NEXT_STEP)) {
            step = tutorialService.onNextStepSelected();
        }
        if (event.msg.equals(Constants.PREVIOUS_STEP)) {
            step = tutorialService.onPreviousStepSelected();
        }

        highlightingService.setHighlightForArea(tutorialService.getCurrentStep());
        tutorialView.setContent(step);
    }
}
