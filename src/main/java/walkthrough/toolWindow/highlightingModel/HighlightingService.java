package walkthrough.toolWindow.highlightingModel;

import com.intellij.openapi.components.ServiceManager;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.wm.WindowManager;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Objects;

public class HighlightingService {

    private ArrayList<TargetArea> targetAreas;

    public static HighlightingService getInstance() {
        return ServiceManager.getService(HighlightingService.class);
    }

    // TODO: We should be able to update these targets if nesccary
    private void loadTargetAreas() {
        targetAreas = new ArrayList<TargetArea>();
        // Try to find every relevant target and create container
        targetAreas.add(new TargetArea(0,0,50,50,"FILE-MENU"));
    }

    public void initShadowing(Project project) {
        JFrame root = (JFrame) Objects.requireNonNull(WindowManager.getInstance().getFrame(project)).getRootPane().getParent();

        //TODO: reorganize so that the shadowing is created here, not in extra class, but without painting
        HighlightWindow shadowFrame = new HighlightWindow(root);
        shadowFrame.setVisible(true);
    }

    public void setHighlightForArea(int position) {
        System.out.println(position);
        // Check if areaCode is in targetAreas
        // Highlight area on position
        removeCurrentHighlight();
    }

    private void removeCurrentHighlight() {
        //TODO: clear screen so that highlights don't interfere with each other
    }

}
