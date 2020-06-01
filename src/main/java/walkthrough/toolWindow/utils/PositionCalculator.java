package walkthrough.toolWindow.utils;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.wm.ToolWindow;
import com.intellij.openapi.wm.ToolWindowManager;
import com.intellij.openapi.wm.WindowManager;
import com.intellij.ui.content.ContentManager;

import javax.swing.*;
import java.awt.*;
import java.util.Objects;

public class PositionCalculator {

    //values for getter
    private final Project project;
    private int x;
    private int y;
    private int width;
    private int height;

    //Basic values to be used
    private static final int DEFAULT_HEIGHT_OF_BAR = 28;
    private static final int DEFAULT_HEIGHT_OF_SMALLER_BAR = 20;
    private static final int EDITOR_OFFSET = 55;
    private int basicX;

    //Components needed for other calculations
    private Rectangle ideContentDimensions;
    private ToolWindowManager manager;
    private JComponent projectToolWindow;
    private JComponent walkthroughWindow;
    private JRootPane projectRoot;

    public PositionCalculator(Project project) {
        this.project = project;

        manager = ToolWindowManager.getInstance(project);
        projectRoot = Objects.requireNonNull(WindowManager.getInstance().getFrame(project)).getRootPane();
        basicX = projectRoot.getX();

        walkthroughWindow = manager.getToolWindow("Walkthrough durch IntelliJ").getComponent();
    }

    //a switch-case for all possible values currently used in the JSON - highlighting areas can be expanded here
    public void updateDimensions(String name) {
        switch (name) {
            case "PROJECT_TOOLWINDOW":
                findProjectToolWindow();
                break;
            case "CODE":
                findEditor();
                break;
            case "PROJECT_DROPDOWN":
                findProjectDropdown();
                break;
            case "PROJECT_DROPDOWN_POPUP":
                findProjectDropdownPopup();
                break;
            case "RUN_BUTTON":
                findRunButton();
                break;
            case "GRAPHICS_APP":
            case "SELECT_IMPORT":
                noHighlighting();
                break;
            case "IMPORT_STATEMENT":
                selectImportStatement();
                break;
            default:
                defaultHighlighting();
                break;
        }
    }

    private void noHighlighting() {
        setDimensions(0, 0, 0, 0);
    }

    private void defaultHighlighting() {
        ideContentDimensions = Objects.requireNonNull(WindowManager.getInstance().getFrame(project)).getContentPane().getBounds();

        setDimensions(
                basicX + ideContentDimensions.x,
                ideContentDimensions.y,
                ideContentDimensions.width,
                ideContentDimensions.height
        );
    }

    private void findProjectToolWindow() {
        ToolWindow projectWindow = manager.getToolWindow("Project");
        projectToolWindow = projectWindow.getComponent();

        //check if ProjectToolWindow is visible - if not: make visible
        if (!projectWindow.isVisible()) {
            projectWindow.show(null);
        }

        setDimensions(
                basicX,
                ideContentDimensions.y + (2 * DEFAULT_HEIGHT_OF_BAR),
                projectToolWindow.getWidth(),
                projectToolWindow.getHeight()
        );
    }

    private void findEditor() {
        //mark height of projectToolWindow and full width - PTW-width - Walkthrough-width
        int editorWidth = (int) (ideContentDimensions.getWidth() - projectToolWindow.getWidth() - walkthroughWindow.getWidth());

        setDimensions(
                basicX + projectToolWindow.getWidth(),
                ideContentDimensions.y + DEFAULT_HEIGHT_OF_BAR,
                editorWidth,
                projectToolWindow.getHeight() + DEFAULT_HEIGHT_OF_BAR
        );
    }

    private void findRunButton() {
        Component[] moreComps = projectRoot.getContentPane().getComponents();
        Component actionToolBar = moreComps[0];
        Component navBar = actionToolBar.getComponentAt(0, 0);
        Component firstNavBarComp = navBar.getComponentAt(0, 0);
        Component secondNavBarComp = navBar.getComponentAt(firstNavBarComp.getWidth(), 0);

        setDimensions(
                basicX + secondNavBarComp.getX(),
                ideContentDimensions.y,
                secondNavBarComp.getWidth(),
                secondNavBarComp.getHeight()
        );
    }

    private void findProjectDropdown() {
        setDimensions(
                basicX + projectToolWindow.getX(),
                ideContentDimensions.y + DEFAULT_HEIGHT_OF_BAR,
                projectToolWindow.getWidth() / 3,
                DEFAULT_HEIGHT_OF_BAR
        );
    }

    private void findProjectDropdownPopup() {
        ContentManager cm = ToolWindowManager.getInstance(project).getToolWindow("Project").getContentManager();
        int x = cm.getContents().length;

        setDimensions(
                basicX + projectToolWindow.getX(),
                ideContentDimensions.y + 2 * DEFAULT_HEIGHT_OF_BAR,
                projectToolWindow.getWidth() / 3,
                x * DEFAULT_HEIGHT_OF_SMALLER_BAR
        );
    }

    //<editor-fold desc="assumption: import statement is in line one and always the same length">
    private void selectImportStatement() {
        setDimensions(
                basicX + projectToolWindow.getWidth() + EDITOR_OFFSET,
                ideContentDimensions.y + (2 * DEFAULT_HEIGHT_OF_BAR),
                250,
                DEFAULT_HEIGHT_OF_BAR
        );
    }
    //</editor-fold>

    //<editor-fold desc="Getter">
    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }
    //</editor-fold>K

    private void setDimensions(int x, int y, int w, int h) {
        this.x = x;
        this.y = y;
        this.width = w;
        this.height = h;
    }

}
