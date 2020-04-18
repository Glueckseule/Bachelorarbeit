package walkthrough.toolWindow.tutorialModel;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.intellij.openapi.components.ServiceManager;
import walkthrough.toolWindow.utils.Constants;
import walkthrough.toolWindow.utils.Event;
import walkthrough.toolWindow.utils.Observable;

import java.util.ArrayList;

public class TutorialService extends Observable {

    private int currentStep = 0;

    private String tutorialType;
    private int totalSteps;

    private ArrayList<TutorialStep> tutorialSteps;
    private ArrayList<ArrayList<JsonObject>> targetList;

    public static TutorialService getInstance() {
        return ServiceManager.getService(TutorialService.class);
    }

    public void initTutorialFromFile(String jsonString) {
        tutorialSteps = new ArrayList<>();
        targetList = new ArrayList<>();
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
            JsonArray targetArray = (JsonArray) step.get("targets");

            ArrayList<JsonObject> targetsForStep = new ArrayList<>();

            String title = step.get("title").getAsString();
            int position = step.get("pos").getAsInt();
            String content = step.get("content").getAsString();
            String targetName = "Placeholder - really needed?";

            TutorialStep oneStep = new TutorialStep(title, position, content, targetName, totalSteps);
            tutorialSteps.add(oneStep);

            for (Object targetObject : targetArray) {
                JsonObject target = (JsonObject) targetObject;
                targetsForStep.add(target);
            }

            targetList.add(targetsForStep);
        }
    }

    public TutorialStep getFirstStep() {
        return tutorialSteps.get(currentStep);
    }

    public TutorialStep onNextStepSelected() {
        if (currentStep < totalSteps - 1) {
            currentStep++;
        }
        if (currentStep == 11) {
            notifyAll(new Event(Constants.ADD_CODE));
        }
        if (currentStep == totalSteps - 1) {
            notifyAll(new Event(Constants.TUTORIAL_ENDING));
        }
        return tutorialSteps.get(currentStep);
    }

    public TutorialStep onPreviousStepSelected() {
        if (currentStep > 0) {
            currentStep--;
        }
        if (currentStep == 11) {
            notifyAll(new Event(Constants.ADD_CODE));
        }
        return tutorialSteps.get(currentStep);
    }

    public TutorialStep onTutorialRestarted() {
        currentStep = 0;
        return tutorialSteps.get(currentStep);
    }

    public ArrayList<ArrayList<JsonObject>> getTargetList() {
        return targetList;
    }

    public int getCurrentStep() {
        return currentStep;
    }

    public String getTutorialType() {
        return tutorialType;
    }
}
