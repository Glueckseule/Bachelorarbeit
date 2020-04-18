package walkthrough.toolWindow.highlightingModel;

import com.google.gson.JsonObject;
import com.intellij.openapi.command.WriteCommandAction;
import com.intellij.openapi.editor.Document;
import com.intellij.openapi.fileEditor.FileEditorManager;
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

                int x = posCalc.getX();
                int y = posCalc.getY();
                int width = posCalc.getWidth();
                int height = posCalc.getHeight();

                TargetArea oneArea = new TargetArea(x, y, width, height, name);
                Arrow oneArrow = new Arrow(x, y, arrowDirection);
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

    private void updateArrows() {
        System.out.println("Resized");
        //TODO: updateDimensions via PositionCalculator for all arrows in arrows<>
    }

    private void updateTargetAreas() {
        System.out.println("Me too");
        //TODO: updateDimensions via PositionCalculator for all areas in targetAreas<>
    }

    //Step 13: delete all in file and set custom
    public void setCodeToEditor() {
        Document documentOpened = FileEditorManager.getInstance(project).getSelectedTextEditor().getDocument();
        int availableLines = documentOpened.getLineCount();
        int offset;
        if (availableLines != 0) {
            offset = documentOpened.getLineEndOffset(documentOpened.getLineCount() - 1);
        } else {
            offset = 0;
        }

        WriteCommandAction.runWriteCommandAction(project, new Runnable() {
            @Override
            public void run() {
                documentOpened.deleteString(0, offset);
                documentOpened.setText(Constants.CODE_FOR_TUTORIAL);
            }
        });
    }

    //<editor-fold desc="Shadowing">
    public void startShadowing(boolean isStarted) {
        shadowFrame.setVisible(isStarted);
    }

    private void setShadowBehaviour() {
        ideaFrame.addComponentListener(new ComponentAdapter() {
            public void componentResized(ComponentEvent e) {
                onBasisResized();
                updateTargetAreas();
                updateArrows();
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
