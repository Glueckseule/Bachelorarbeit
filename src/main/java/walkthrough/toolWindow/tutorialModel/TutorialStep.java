package walkthrough.toolWindow.tutorialModel;

public class TutorialStep {

    public final String TITLE;
    public final String CONTENT;
    public final String POSITION;
    public final String ARROW_DIRECTION;
    public final String TARGET;

    public final int PERCENTAGE_COMPLETED;

    public TutorialStep(String title, int position, String content, String arrowDirection, String target, int totalSteps) {
        this.TITLE = title;
        this.POSITION = position+1 + "/" + totalSteps;
        this.ARROW_DIRECTION = arrowDirection;
        this.TARGET = target;
        this.CONTENT = content;

        this.PERCENTAGE_COMPLETED = ((position+1) * 100) / totalSteps;
    }


}
