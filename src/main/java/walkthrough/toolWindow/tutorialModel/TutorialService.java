package walkthrough.toolWindow.tutorialModel;

import com.google.gson.*;
import com.intellij.openapi.components.ServiceManager;
import walkthrough.toolWindow.utils.Event;
import walkthrough.toolWindow.utils.Observable;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;

public class TutorialService extends Observable {

    private int currentStep = 0;

    private ArrayList<TutorialStep> tutorialSteps;

    public static TutorialService getInstance() {
        return ServiceManager.getService(TutorialService.class);
    }

    public void initTutorialFromFile(File file) throws FileNotFoundException {
        tutorialSteps = new ArrayList<TutorialStep>();
        loadTutorialFromJSON(file);
        notifyAll(new Event("Tutorial loaded"));
    }

    private void loadTutorialFromJSON(File file) throws FileNotFoundException {
        JsonParser parser = new JsonParser();
        JsonObject tutorial = (JsonObject) parser.parse(new FileReader(file));
        JsonArray steps = (JsonArray) tutorial.get("steps");

        int totalSteps = tutorial.get("number_of_steps").getAsInt();

        for (Object o : steps) {
            JsonObject step = (JsonObject) o;
            String title = step.get("title").getAsString();
            int position = step.get("pos").getAsInt();
            String content = step.get("content").getAsString();
            String arrowDirection = step.get("arrow").getAsString();
            String target = step.get("target").getAsString();

            TutorialStep oneStep = new TutorialStep(title, position, content, arrowDirection, target, totalSteps);
            tutorialSteps.add(oneStep);
        }
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
