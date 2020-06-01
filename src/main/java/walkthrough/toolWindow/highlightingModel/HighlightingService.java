package walkthrough.toolWindow.highlightingModel;

import com.google.gson.JsonObject;
import com.intellij.openapi.command.WriteCommandAction;
import com.intellij.openapi.editor.Document;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.fileEditor.FileEditorManager;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.wm.WindowManager;
import walkthrough.toolWindow.utils.Constants;
import walkthrough.toolWindow.utils.Event;
import walkthrough.toolWindow.utils.Observable;
import walkthrough.toolWindow.utils.PositionCalculator;

import javax.swing.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.util.ArrayList;
import java.util.Objects;

public class HighlightingService extends Observable {

    private Project project;
    private ArrayList<ArrayList<TargetArea>> targetAreas;
    private ArrayList<ArrayList<Arrow>> arrows;
    private JFrame ideaFrame;
    private Canvas shadowFrame;
    private PositionCalculator posCalc;
    private ArrayList<ArrayList<JsonObject>> targetJson;

    public void setupHighlighting(Project project, ArrayList<ArrayList<JsonObject>> targets) {
        this.project = project;
        posCalc = new PositionCalculator(project);
        targetJson = targets;
        ideaFrame = (JFrame) Objects.requireNonNull(WindowManager.getInstance().getFrame(project)).getRootPane().getParent();
        shadowFrame = new Canvas(ideaFrame.getSize());

        setShadowBehaviour();
        onBasisMoved();
    }

    public void loadAssets() {
        targetAreas = new ArrayList<>();
        arrows = new ArrayList<>();

        for (ArrayList<JsonObject> targetObjects : targetJson) {
            ArrayList<TargetArea> areasForStep = new ArrayList<>();
            ArrayList<Arrow> arrowsForStep = new ArrayList<>();

            for (JsonObject target : targetObjects) {

                String arrowDirection = target.get("arrow").getAsString();
                String name = target.get("target-name").getAsString();
                posCalc.updateDimensions(name);

                TargetArea oneArea = new TargetArea(posCalc.getX(), posCalc.getY(), posCalc.getWidth(), posCalc.getHeight(), name, arrowDirection);
                Arrow oneArrow = new Arrow(posCalc.getX(), posCalc.getY(), arrowDirection);
                areasForStep.add(oneArea);
                arrowsForStep.add(oneArrow);
            }
            targetAreas.add(areasForStep);
            arrows.add(arrowsForStep);
        }
        notifyAll(new Event(Constants.ASSETS_LOADED));
    }

    public void setHighlightForArea(int position) {
        shadowFrame.highlightElements(targetAreas.get(position), arrows.get(position));
    }

    // Method can be used for updating the assets - currently only used when tutorial is restarted
    // Unfortunately not usable for case when IDE Dimensions are changed as change event is fired too often
    public void updateAssets(boolean isRestarted) {
        if (isRestarted) {
            ArrayList<ArrayList<TargetArea>> temporaryAreasForUpdate = new ArrayList<>();
            ArrayList<ArrayList<Arrow>> temporaryArrowsForUpdate = new ArrayList<>();
            for (ArrayList<TargetArea> currentAreasForStep : targetAreas) {
                ArrayList<TargetArea> areasForStep = new ArrayList<>();
                ArrayList<Arrow> arrowsForStep = new ArrayList<>();
                for (TargetArea area : currentAreasForStep) {
                    posCalc.updateDimensions(area.name);

                    TargetArea oneArea = new TargetArea(posCalc.getX(), posCalc.getY(), posCalc.getWidth(), posCalc.getHeight(), area.name, area.arrow);
                    Arrow oneArrow = new Arrow(posCalc.getX(), posCalc.getY(), area.arrow);

                    areasForStep.add(oneArea);
                    arrowsForStep.add(oneArrow);
                }
                temporaryAreasForUpdate.add(areasForStep);
                temporaryArrowsForUpdate.add(arrowsForStep);
            }
            targetAreas = temporaryAreasForUpdate;
            arrows = temporaryArrowsForUpdate;
        } else {
            shadowFrame.clearCanvas();
        }
    }

    //Step 13: if file opened, delete all in file and set custom
    public void setCodeToEditor() {
        Editor selectedEditor = FileEditorManager.getInstance(project).getSelectedTextEditor();
        if (selectedEditor != null) {
            Document documentOpened = selectedEditor.getDocument();
            int availableLines = documentOpened.getLineCount();
            int offset;
            if (availableLines != 0) {
                offset = documentOpened.getLineEndOffset(documentOpened.getLineCount() - 1);
            } else {
                offset = 0;
            }

            WriteCommandAction.runWriteCommandAction(project, () -> {
                documentOpened.deleteString(0, offset);
                documentOpened.setText(Constants.CODE_FOR_TUTORIAL);
            });
        }
    }

    //<editor-fold desc="Shadowing">
    public void isShadowingRunning(boolean isRunning) {
        shadowFrame.setVisible(isRunning);
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

    private void onBasisMoved() {
        shadowFrame.setLocation(ideaFrame.getLocation());
    }

    private void onBasisResized() {
        shadowFrame.setSize(ideaFrame.getSize());
    }
    //</editor-fold>

}
