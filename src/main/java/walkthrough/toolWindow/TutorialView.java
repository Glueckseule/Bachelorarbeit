package walkthrough.toolWindow;

import walkthrough.toolWindow.tutorialModel.TutorialService;
import walkthrough.toolWindow.tutorialModel.TutorialStep;
import walkthrough.toolWindow.utils.Constants;
import walkthrough.toolWindow.utils.Event;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TutorialView {

    private JButton backButton;
    private JButton nextButton;
    private JPanel myToolWindowContent;
    private JLabel stepHeadline;
    private JLabel tutType;
    private JTextPane stepContent;
    private JProgressBar tutorialProgress;
    private JButton startButton;

    private final ActionListener NEXT_CLICKED = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            TutorialService.getInstance().notifyAll(new Event(Constants.NEXT_STEP));
        }
    };
    private final ActionListener BACK_CLICKED = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            TutorialService.getInstance().notifyAll(new Event(Constants.PREVIOUS_STEP));
        }
    };
    private final ActionListener RESTART_CLICKED = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            TutorialService.getInstance().notifyAll(new Event(Constants.RESTART));
        }
    };
    private final ActionListener FINISH_CLICKED = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            TutorialService.getInstance().notifyAll(new Event(Constants.FINISH));
        }
    };
    private final ActionListener START_CLICKED = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            TutorialService.getInstance().notifyAll(new Event(Constants.TUTORIAL_STARTED));
            TutorialService.getInstance().notifyAll(new Event(Constants.NEXT_STEP));
        }
    };

    public TutorialView(String type) {
        tutType.setText(type);

        backButton.setVisible(false);
        nextButton.setVisible(false);

        setInitialListener();
    }

    public void setContent(TutorialStep step) {
        stepHeadline.setText(step.TITLE);
        stepContent.setText(step.CONTENT);
        tutorialProgress.setString(step.POSITION);
        tutorialProgress.setValue(step.PERCENTAGE_COMPLETED);
    }

    private void setInitialListener() {
        startButton.addActionListener(START_CLICKED);
    }

    public void changeUI(String type) {
        if (type.equals(Constants.ASSETS_LOADED)) {
            startButton.setVisible(false);
            nextButton.setVisible(true);
            backButton.setVisible(true);
            manageListeners(true);
        }
        if (type.equals(Constants.RESTART)) {
            nextButton.setText(Constants.NEXT_BUTTON);
            backButton.setText(Constants.BACK_BUTTON);
            manageListeners(true);
        }
        if (type.equals(Constants.TUTORIAL_ENDING)) {
            nextButton.setText(Constants.FINISH_TUTORIAL_BUTTON);
            backButton.setText(Constants.RESTART_TUTORIAL_BUTTON);
            manageListeners(false);
        }
    }

    private void manageListeners(boolean isRunning) {
        if (isRunning) {
            backButton.addActionListener(BACK_CLICKED);
            nextButton.addActionListener(NEXT_CLICKED);
            backButton.removeActionListener(RESTART_CLICKED);
            nextButton.removeActionListener(FINISH_CLICKED);
        } else {
            backButton.removeActionListener(BACK_CLICKED);
            nextButton.removeActionListener(NEXT_CLICKED);
            backButton.addActionListener(RESTART_CLICKED);
            nextButton.addActionListener(FINISH_CLICKED);
        }
    }

    public JPanel getContent() {
        return myToolWindowContent;
    }
}
