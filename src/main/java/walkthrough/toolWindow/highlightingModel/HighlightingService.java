package walkthrough.toolWindow.highlightingModel;

import com.google.gson.JsonObject;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.wm.WindowManager;
import walkthrough.toolWindow.utils.Constants;
import walkthrough.toolWindow.utils.Event;
import walkthrough.toolWindow.utils.Observable;

import javax.swing.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.util.ArrayList;
import java.util.Objects;

public class HighlightingService extends Observable {

    private ArrayList<TargetArea> targetAreas;
    private ArrayList<Arrow> arrows;
    private JFrame ideaFrame;
    private Canvas shadowFrame;

    public void setupHighlighting(Project project, ArrayList<JsonObject> targets) {
        ideaFrame = (JFrame) Objects.requireNonNull(WindowManager.getInstance().getFrame(project)).getRootPane().getParent();
        shadowFrame = new Canvas(ideaFrame.getSize());

        setShadowBehaviour();
        loadAssets(targets);

        notifyAll(new Event(Constants.ASSETS_LOADED));
    }

    private void setShadowBehaviour() {
        ideaFrame.addComponentListener(new ComponentAdapter() {
            public void componentResized(ComponentEvent e) {
                onBasisResized();
            }

            public void componentMoved(ComponentEvent e) {
                onBasisMoved();
            }
        });
    }

    // TODO: We should be able to update these targets if nesccary
    private void loadAssets(ArrayList<JsonObject> targets) {
        targetAreas = new ArrayList<>();
        arrows = new ArrayList<>();

        for (JsonObject target : targets) {
            int x = target.get("target-x").getAsInt();
            int y = target.get("target-y").getAsInt();
            int width = target.get("target-width").getAsInt();
            int height = target.get("target-height").getAsInt();
            String arrowDirection = target.get("arrow").getAsString();
            String name = target.get("target-name").getAsString();

            TargetArea oneArea = new TargetArea(x, y, width, height, name);
            Arrow oneArrow = new Arrow(x, y, arrowDirection);
            targetAreas.add(oneArea);
            arrows.add(oneArrow);
        }
    }

    public void setHighlightForArea(int position) {
        shadowFrame.highlightElements(targetAreas.get(position), arrows.get(position));
    }

    public void startShadowing(boolean isStarted) {
        shadowFrame.setVisible(isStarted);
    }

    private void onBasisMoved() {
        shadowFrame.setLocation(ideaFrame.getLocation());
    }

    private void onBasisResized() {
        shadowFrame.setSize(ideaFrame.getSize());
    }

}
