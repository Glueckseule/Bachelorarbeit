package walkthrough.toolWindow;

import walkthrough.toolWindow.tutorialModel.TutorialService;
import walkthrough.toolWindow.tutorialModel.TutorialStep;
import walkthrough.toolWindow.utils.Constants;
import walkthrough.toolWindow.utils.Event;

import javax.swing.*;

public class TutorialView {

    private JButton backButton;
    private JButton nextButton;
    private JPanel myToolWindowContent;
    private JLabel stepHeadline;
    private JLabel tutType;
    private JTextPane stepContent;
    private JProgressBar tutorialProgress;
    private JButton startButton;

    public TutorialView(String type) {
        tutType.setText(type);

        backButton.setVisible(false);
        nextButton.setVisible(false);
    }

    public void setContent(TutorialStep step) {
        stepHeadline.setText(step.TITLE);
        stepContent.setText(step.CONTENT);
        tutorialProgress.setString(step.POSITION);
        tutorialProgress.setValue(step.PERCENTAGE_COMPLETED);
    }

    public void setListener() {
        startButton.addActionListener(e -> TutorialService.getInstance().notifyAll(new Event(Constants.NEXT_STEP)));
        startButton.addActionListener(e -> TutorialService.getInstance().notifyAll(new Event(Constants.TUTORIAL_STARTED)));
    }

    public void changeUI() {
        startButton.setVisible(false);
        nextButton.setVisible(true);
        backButton.setVisible(true);
        backButton.addActionListener(e -> TutorialService.getInstance().notifyAll(new Event(Constants.PREVIOUS_STEP)));
        nextButton.addActionListener(e -> TutorialService.getInstance().notifyAll(new Event(Constants.NEXT_STEP)));
    }

    public void setCodeToEditor() {
        System.out.println("Ellipse ellipse = new Ellipse();");
    }

    public JPanel getContent() {
        return myToolWindowContent;
    }
}
