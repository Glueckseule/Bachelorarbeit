package walkthrough.toolWindow.tutorialModel;

import com.google.gson.*;
import com.intellij.openapi.components.ServiceManager;
import walkthrough.toolWindow.utils.Constants;
import walkthrough.toolWindow.utils.Event;
import walkthrough.toolWindow.utils.Observable;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;

public class TutorialService extends Observable {

    private int currentStep = 0;

    private String tutorialType;
    private int totalSteps;

    private ArrayList<TutorialStep> tutorialSteps;

    public static TutorialService getInstance() {
        return ServiceManager.getService(TutorialService.class);
    }

    public void initTutorialFromFile(String jsonString) {
        tutorialSteps = new ArrayList<TutorialStep>();
        loadTutorialFromJSON(jsonString);
        notifyAll(new Event(Constants.TUTORIAL_LOADED));
    }

    private void loadTutorialFromJSON(String jsonString) {
        JsonParser parser = new JsonParser();
        JsonObject tutorial = (JsonObject) parser.parse(jsonString);
        JsonArray steps = (JsonArray) tutorial.get("steps");

        totalSteps = tutorial.get("number_of_steps").getAsInt();
        tutorialType = tutorial.get("type").getAsString();

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
        if (currentStep < totalSteps-1) {
            currentStep++;
        }
        return tutorialSteps.get(currentStep);
    }

    public TutorialStep onPreviousStepSelected() {
        if (currentStep > 0) {
            currentStep--;
        }
        return tutorialSteps.get(currentStep);
    }

    public int getCurrentStep() {
        return currentStep;
    }

    public String getTutorialType() {
        return tutorialType;
    }

}
