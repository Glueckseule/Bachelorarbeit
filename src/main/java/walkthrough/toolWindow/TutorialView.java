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

    private ActionListener nextClicked = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            TutorialService.getInstance().notifyAll(new Event(Constants.NEXT_STEP));
        }
    };
    private ActionListener backClicked = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            TutorialService.getInstance().notifyAll(new Event(Constants.PREVIOUS_STEP));
        }
    };
    private ActionListener restartClicked = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            TutorialService.getInstance().notifyAll(new Event(Constants.RESTART));
        }
    };
    private ActionListener finishClicked = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            TutorialService.getInstance().notifyAll(new Event(Constants.FINISH));
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
        startButton.addActionListener(nextClicked);
        startButton.addActionListener(e -> TutorialService.getInstance().notifyAll(new Event(Constants.TUTORIAL_STARTED)));
    }

    public void changeUI(String type) {
        if (type.equals(Constants.ASSETS_LOADED)) {
            startButton.setVisible(false);
            nextButton.setVisible(true);
            backButton.setVisible(true);
            manageListeners(true);
        }
        if (type.equals(Constants.RESTART)) {
            nextButton.setText("WEITER");
            backButton.setText("ZURÜCK");
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
            backButton.addActionListener(backClicked);
            nextButton.addActionListener(nextClicked);
            backButton.removeActionListener(restartClicked);
            nextButton.removeActionListener(finishClicked);
        } else {
            backButton.removeActionListener(backClicked);
            nextButton.removeActionListener(nextClicked);
            backButton.addActionListener(restartClicked);
            nextButton.addActionListener(finishClicked);
        }
    }

    public JPanel getContent() {
        return myToolWindowContent;
    }
}
