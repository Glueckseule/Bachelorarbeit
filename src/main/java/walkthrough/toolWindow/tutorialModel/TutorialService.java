package walkthrough.toolWindow.tutorialModel;

import com.intellij.openapi.components.ServiceManager;
import walkthrough.toolWindow.utils.Event;
import walkthrough.toolWindow.utils.Observable;

import java.io.File;
import java.util.ArrayList;

public class TutorialService extends Observable {

    private int currentStep = 0;

    private int totalSteps;
    private ArrayList<TutorialStep> tutorialSteps;
    public static TutorialService getInstance() {
        return ServiceManager.getService(TutorialService.class);
    }

    public void initTutorialFromFile(File file) {
        tutorialSteps = new ArrayList<TutorialStep>();
        loadTutorialFromJSON(file);
        notifyAll(new Event("Tutorial loaded"));
    }

    private void loadTutorialFromJSON(File file) {
        // TODO Read JSON
        // TODO Set totalSteps from JSON
        // TODO Create all TutorialSteps from JSON
    }

    public TutorialStep getFirstStep() {
        return tutorialSteps.get(currentStep);
    }

    public TutorialStep onNextStepSelected() {
        //TODO tell view what to render
        currentStep++;
        return tutorialSteps.get(currentStep);
    }

    public TutorialStep onPreviousStepSelected() {
        //TODO tell view what to render
        currentStep--;
        return tutorialSteps.get(currentStep);
    }

    public int getCurrentStep() {
        return currentStep;
    }

}
