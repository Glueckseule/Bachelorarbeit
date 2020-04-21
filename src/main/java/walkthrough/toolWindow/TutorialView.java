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

    private static TutorialService service = TutorialService.getInstance();

    //<editor-fold desc="ActionListeners">
    private static final ActionListener NEXT_CLICKED = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            service.notifyAll(new Event(Constants.NEXT_STEP));
        }
    };
    private static final ActionListener BACK_CLICKED = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            service.notifyAll(new Event(Constants.PREVIOUS_STEP));
        }
    };
    private static final ActionListener RESTART_CLICKED = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            service.notifyAll(new Event(Constants.RESTART));
        }
    };
    private static final ActionListener FINISH_CLICKED = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            service.notifyAll(new Event(Constants.FINISH));
        }
    };
    private static final ActionListener START_CLICKED = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            service.notifyAll(new Event(Constants.TUTORIAL_STARTED));
        }
    };
    //</editor-fold>

    public TutorialView(String type) {
        tutType.setText(type);

        backButton.setVisible(false);
        nextButton.setVisible(false);

        startButton.addActionListener(START_CLICKED);
    }

    //Called in from MyToolWindowFactory on event - every time tutorial step changes
    public void setContent(TutorialStep step) {
        stepHeadline.setText(step.TITLE);
        stepContent.setText(step.CONTENT);
        tutorialProgress.setString(step.POSITION);
        tutorialProgress.setValue(step.PERCENTAGE_COMPLETED);

        stepContent.setCaretPosition(0);
    }

    //Called in from MyToolWindowFactory on event - manages text and listeners on buttons depending on tutorial state (started/running/ended)
    public void changeUI(String type) {
        if (type.equals(Constants.ASSETS_LOADED)) {
            startButton.setVisible(false);
            nextButton.setVisible(true);
            backButton.setVisible(true);
            manageListeners(true);
        }
        if (type.equals(Constants.TUTORIAL_ENDING)) {
            nextButton.setText(Constants.FINISH_TUTORIAL_BUTTON);
            backButton.setText(Constants.RESTART_TUTORIAL_BUTTON);
            manageListeners(false);
        }
        if (type.equals(Constants.RESTART)) {
            nextButton.setText(Constants.NEXT_BUTTON);
            backButton.setText(Constants.BACK_BUTTON);
            manageListeners(true);
        }
        if (type.equals(Constants.FINISH)) {
            nextButton.setText(Constants.NEXT_BUTTON);
            nextButton.setVisible(false);
            backButton.setText(Constants.BACK_BUTTON);
            backButton.setVisible(false);
            startButton.setVisible(true);
        }
    }

    private void manageListeners(boolean isRunning) {
        backButton.removeActionListener(RESTART_CLICKED);
        backButton.removeActionListener(BACK_CLICKED);
        nextButton.removeActionListener(FINISH_CLICKED);
        nextButton.removeActionListener(NEXT_CLICKED);

        if (isRunning) {
            backButton.addActionListener(BACK_CLICKED);
            nextButton.addActionListener(NEXT_CLICKED);
        } else {
            backButton.addActionListener(RESTART_CLICKED);
            nextButton.addActionListener(FINISH_CLICKED);
        }
    }

    public JPanel getContent() {
        return myToolWindowContent;
    }
}
