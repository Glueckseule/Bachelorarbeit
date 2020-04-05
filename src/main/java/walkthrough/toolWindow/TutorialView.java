package walkthrough.toolWindow;

import com.intellij.openapi.wm.ToolWindow;
import com.intellij.ui.content.Content;
import com.intellij.ui.content.ContentFactory;
import walkthrough.toolWindow.tutorialModel.TutorialService;
import walkthrough.toolWindow.tutorialModel.TutorialStep;
import walkthrough.toolWindow.utils.Constants;
import walkthrough.toolWindow.utils.Event;

import javax.swing.*;

public class TutorialView {

    private JButton back;
    private JButton nextButton;
    private JPanel myToolWindowContent;
    private JLabel stepHeadline;
    private JLabel tutType;
    private JTextPane stepContent;
    private JProgressBar tutorialProgress;

    public TutorialView(String type) {
        tutType.setText(type);
    }

    public void setContent(TutorialStep step) {
        stepHeadline.setText(step.TITLE);
        stepContent.setText(step.CONTENT);
        tutorialProgress.setString(step.POSITION);
        tutorialProgress.setValue(step.PERCENTAGE_COMPLETED);
    }

    public void setListener() {
        back.addActionListener(e -> TutorialService.getInstance().notifyAll(new Event(Constants.PREVIOUS_STEP)));
        nextButton.addActionListener(e -> TutorialService.getInstance().notifyAll(new Event(Constants.NEXT_STEP)));
    }

    public JPanel getContent() {
        return myToolWindowContent;
    }
}
