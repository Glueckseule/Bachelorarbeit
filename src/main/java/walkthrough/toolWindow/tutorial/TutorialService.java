package walkthrough.toolWindow.tutorial;

import com.intellij.openapi.components.ServiceManager;
import walkthrough.toolWindow.highlighting.HighlightingService;
import walkthrough.toolWindow.utils.Event;
import walkthrough.toolWindow.utils.Observable;

import java.io.File;

public class TutorialService extends Observable {

    private HighlightingService highlightingService = ServiceManager.getService(HighlightingService.class);


    public static TutorialService getInstance() {
        return ServiceManager.getService(TutorialService.class);
    }

    public void initTutorialFromFile(File file) {
        // Read JSON
        // Create Steps
        notifyAll(new Event("Tutorial loaded"));
    }

    public void onNextStepSelected(TutorialStep step) {
    }
}
